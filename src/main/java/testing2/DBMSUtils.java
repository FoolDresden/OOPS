package testing2;
import java.lang.Long;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

public class DBMSUtils
{
    private String username, password;
    MongoClient mc;
    MongoDatabase db;

    public DBMSUtils()
    {
        username = "varundb";
        password = "varsha123";
        try
        {
            mc = MongoClients.create("mongodb+srv://varundb:"+password+"@cluster0-oi5zy.mongodb.net/test?retryWrites=true&w=majority"); // Create database at localhost:27017
            db = mc.getDatabase("users");
        }
        catch(Exception e)
        {
            System.out.println("Failed to instantiate database");
        }
        
    }

    public boolean createNewUser(Customer c)
    {
        /*
        Parameters - 
            Customer c -
                Object of type Customer

        Returns - 
            Boolean - 
                True if creation of new user succeeded
                False if creation of new user failed or if user already exists
                in database

        This function creates a new user in the database if it doesn't exist already.
        */
        MongoCollection<Document> customers = db.getCollection("customers");
        Document cursor = customers.find(eq("name", c.username)).first();
        if(cursor == null)
        {
            Document entry = new Document("name", c.username)
                             .append("password", c.password)
                             .append("amount_in_wallet", c.w.money)
                             .append("location", c.loc)
                             .append("in_trip", false)
                             .append("trip_start", 0)
                             .append("trip_end", 0)
                             .append("assigned_driver", "")
                             .append("trip_price", 0);
            customers.insertOne(entry);
            return true; 
        }
        else
        {
            return false;
        }
    }

    public Customer getCustomerDetails(String name)
    {
        /*
        Parameters - 
            String name -
                Name of the customer whose details are to be obtained

        Returns - 
            Customer c -
                A Customer object. Will be null if name doesn't exist

        This function gets Customer details from the database.
        */
        Customer c = null;
        MongoCollection<Document> customers = db.getCollection("customers");
        Document cursor = customers.find(eq("name", name)).first();
        if(cursor == null)
        {
            return c;
        }
        else
        {
            c = new Customer();
            c.username = (String)cursor.get("name");
            c.password = (String)cursor.get("password");
            c.w.money = Double.valueOf(""+cursor.get("amount_in_wallet"));
            c.loc = (String)cursor.get("location");
            c.isInTrip = Boolean.parseBoolean((String)cursor.get("in_trip"));
            c.assignedDriver = getDriverDetails((String)cursor.get("assigned_driver"));
            c.assignedDriver.assignedCustomer = c;

            return c;
        }
    }

    public Driver getDriverDetails(String name)
    {
        Driver d = null;
        MongoCollection<Document> drivers = db.getCollection("drivers");
        Document cursor = drivers.find(eq("name", name)).first();
        if(cursor == null)
        {
            return d;
        }
        else
        {
            d = new Driver();
            d.username = (String)cursor.get("name");
            d.rating = Double.valueOf(""+cursor.get("rating"));
            d.loc = (String)cursor.get("location");
            String isInTrip=""+cursor.get("in_trip");
            d.isInTrip = Boolean.parseBoolean(isInTrip);
            d.assignedCustomer = null;
            d.n_rides = Integer.parseInt(""+cursor.get("number_of_rides"));

            return d;
        }
    }

    public boolean createNewUser(Driver d)
    {
        /*
        Parameters - 
            Driver d -
                Object of type Customer

        Returns - 
            Boolean - 
                True if creation of new driver succeeded
                False if creation of new driver failed or if driver already exists
                in database

        This function creates a new driver in the database if it doesn't exist already.
        */
        MongoCollection<Document> drivers = db.getCollection("drivers");
        Document cursor = drivers.find(eq("name", d.username)).first();
        if(cursor == null)
        {
            Document entry = new Document("name", d.username)
                             .append("rating", d.rating)
                             .append("location", d.loc)
                             .append("in_trip", d.isInTrip)
                             .append("assigned_customer", "")
                             .append("number_of_rides", 0);
            drivers.insertOne(entry);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean getTripStatus(Customer c)
    {
        MongoCollection<Document> customers = db.getCollection("customers");
        Document cursor = customers.find(eq("name", c.username)).first();
        boolean final_status = false;
        if(cursor == null)
        {
            return false;
        }
        else
        {
            boolean currentStatus = Boolean.parseBoolean(""+cursor.get("in_trip"));
            if(currentStatus)
            {
                long end_time = getEndTime(c);
                long curr_time = System.currentTimeMillis();
                if(curr_time >= end_time)
                {
                    Driver d = c.assignedDriver;
                    final_status = endTrip(d);
                }
            }
        }
        if(final_status)
            return false;
        else
            return true;
    }

    public boolean endTrip(Driver d)
    {
        if(d.isInTrip == true)
        {
            d.isInTrip = false;
            d.assignedCustomer.isInTrip = false;
            d.n_rides += 1;
            try
            {
                MongoCollection<Document> customers = db.getCollection("customers");
                Document cursor = customers.find(eq("name", d.assignedCustomer.username)).first();
                if(cursor == null)
                {
                    return false;
                }
                else
                {
                    double price = Double.valueOf(""+cursor.get("trip_price"));
                    d.assignedCustomer.w.removeMoney(price);
                    customers.updateOne(eq("name", d.assignedCustomer.username), 
                                        combine(set("in_trip", false), 
                                        set("amount_in_wallet", d.assignedCustomer.w.money),
                                        set("trip_start", 0),
                                        set("trip_end", 0),
                                        set("assigned_driver", ""),
                                        set("trip_price", 0)));
                }
                MongoCollection<Document> drivers = db.getCollection("drivers");
                cursor = drivers.find(eq("name", d.username)).first();
                if(cursor == null)
                {
                    return false;
                }
                else
                {
                    drivers.updateOne(eq("name", d.username), combine(set("in_trip", false), 
                                      set("rating", d.rating),
                                      set("assigned_customer", ""), set("number_of_rides", d.n_rides)));
                }
                d.assignedCustomer.assignedDriver = null;
                d.assignedCustomer = null;
                return true;
            }
            catch(Exception e)
            {
                System.out.println("Database not accessible : " + e);
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean startTrip(Driver d, double price, long trip_time, String loc)
    {
        if(d.isInTrip == false)
        {
            d.isInTrip = true;
            d.assignedCustomer.isInTrip = true;
            d.assignedCustomer.assignedDriver = d;
            d.assignedCustomer.loc = loc;
            d.loc = loc;
            try
            {
                MongoCollection<Document> customers = db.getCollection("customers");
                Document cursor = customers.find(eq("name", d.assignedCustomer.username)).first();
                if(cursor == null)
                {
                    return false;
                }
                else
                {
                    long start = System.currentTimeMillis();
                    customers.updateOne(eq("name", d.assignedCustomer.username), 
                        combine(set("in_trip", true), set("trip_start", start),
                            set("trip_end", start+trip_time), set("assigned_driver", d.username),
                            set("trip_end", start+trip_time), set("assigned_driver", d.username),
                            set("location", loc), set("trip_price", price)));
                }
                MongoCollection<Document> drivers = db.getCollection("drivers");
                cursor = drivers.find(eq("name", d.username)).first();
                if(cursor == null)
                {
                    return false;
                }
                else
                {
                    drivers.updateOne(eq("name", d.username), combine(set("in_trip", true),
                                      set("assigned_customer", d.assignedCustomer.username),
                                      set("location", loc)));
                }
                return true;
            }
            catch(Exception e)
            {
                System.out.println("Database not accessible : " + e);
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public long getEndTime(Customer c)
    {
        try
        {
            MongoCollection<Document> customers = db.getCollection("customers");
            Document cursor = customers.find(eq("name", c.username)).first();
            if(cursor == null)
            {
                System.out.println("This guy doesn't exist in the DB");
                return -1;
            }
            else
            {
                return Long.parseLong((String)cursor.get("trip_end"));
            }
        }
        catch(Exception e)
        {
            System.out.println("Database not accessible : " + e);
            return -1;
        }
    }

    public Customer loginUser(String username, String password)
    {
//        try
//        {
            MongoCollection<Document> customers = db.getCollection("customers");
            Document cursor = customers.find(and(eq("name", username), eq("password", password))).first();
            System.out.println(cursor);
            if(cursor == null)
            {
                return null;
            }
            else
            {
                Customer c = new Customer();
                c.username = username;
                c.password = password;
                String s=""+cursor.get("amount_in_wallet");
                Double d = Double.valueOf(s);
                c.w.money = d;
                c.loc =  (String)cursor.get("location");
                c.isInTrip = Boolean.parseBoolean(""+cursor.get("in_trip"));
                c.assignedDriver = getDriverDetails((String)cursor.get("assigned_driver"));
                
                return c;
            }
//        }
//        catch(Exception e)
//        {
//            System.out.println("ERRRRRRR");
//            System.out.println(e);
//            return null;
//        }
    }
    public boolean addMoney(Customer c, double amount)
    {
        try
        {
            MongoCollection<Document> customers = db.getCollection("customers");
            Document cursor = customers.find(eq("name", c.username)).first();
            if(cursor == null)
            {
                return false;
            }
            else
            {
                double money = Double.valueOf(""+cursor.get("amount_in_wallet"));
                customers.updateOne(eq("name", c.username), set("amount_in_wallet", (money+amount)));
            }
        }
        catch(Exception e)
        {
            System.out.println("Database not accessible : " + e);
            return false;
        }
        boolean add_money = c.w.addMoney(amount);
        if(add_money == false)
            return false;
        else
            return true;
    }

    public Driver getBestDriver(String loc)
    {
        Driver d = null;
//        try
//        {
            MongoCollection<Document> drivers = db.getCollection("drivers");
            MongoCursor<Document> cursor = drivers.find(and(eq("location", loc), eq("in_trip", false))).iterator();
            double rating = 0.0;
            while(cursor.hasNext())
            {
                Document temp = cursor.next();
                if(Double.valueOf(""+temp.get("rating")) >= rating)
                {
                    d = getDriverDetails((String)temp.get("name"));
                }
            }
            cursor.close();

            long start = System.currentTimeMillis();
            MongoCollection<Document> customers = db.getCollection("customers");
            MongoCursor<Document> cursor1 = customers.find(and(eq("in_trip", true), gte("trip_end", start))).iterator();
            while(cursor1.hasNext())
            {
                Document temp = cursor1.next();
                Customer c = getCustomerDetails((String)temp.get("name"));
                Driver x = c.assignedDriver;
                endTrip(x);
                if(x.rating >= rating)
                {
                    d = getDriverDetails(x.username);
                }
            }
            cursor1.close();
//        }
//        catch(Exception e)
//        {
//            System.out.println("Couldn't get a driver but got sn error");
//            System.out.println("Database not accessible : " + e);
//            return null;
//        }
        return d;
    }
}