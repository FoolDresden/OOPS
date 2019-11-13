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
            //MongoClientURI uri = new MongoClientURI(
    //"mongodb+srv://varundb:"+password+"@cluster0-oi5zy.mongodb.net/test?retryWrites=true&w=majority");
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
                             .append("trip_end", 0);
            customers.insertOne(entry);
            return true; 
        }
        else
        {
            return false;
        }
    }

    public boolean createNewUser(Driver d)
    {
        MongoCollection<Document> drivers = db.getCollection("drivers");
        Document cursor = drivers.find(eq("name", d.username)).first();
        if(cursor == null)
        {
            //Document cust_details = new Document("name", d.assignedCustomer.username)
            //                        .append("location", d.assignedCustomer.loc);

            Document entry = new Document("name", d.username)
                             .append("rating", d.rating)
                             .append("location", d.loc)
                             .append("in_trip", d.isInTrip);
                             //.append("assigned_customer", cust_details);
            drivers.insertOne(entry);
            return true; 
        }
        else
        {
            return false;
        }
    }

    public boolean getTripStatus(String driverName)
    {
        MongoCollection<Document> drivers = db.getCollection("drivers");
        Document cursor = drivers.find(eq("name", driverName)).first();
        if(cursor == null)
        {
            return false;
        }
        else
        {
            return Boolean.parseBoolean((String)cursor.get("in_trip"));
        }
    }

    public boolean getTripStatus(String customerName, boolean customer)
    {
        MongoCollection<Document> customers = db.getCollection("customers");
        Document cursor = customers.find(eq("name", customerName)).first();
        if(cursor == null)
        {
            return false;
        }
        else
        {
            return Boolean.parseBoolean((String)cursor.get("in_trip"));
        }
    }

    public boolean endTrip(Driver d, double price, String loc)
    {
        if(d.isInTrip == true)
        {
            d.assignedCustomer.w.removeMoney(price);
            d.isInTrip = false;
            d.assignedCustomer.isInTrip = false;
            d.assignedCustomer.loc = loc;
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
                    customers.updateOne(eq("name", d.assignedCustomer.username), 
                                        combine(set("in_trip", false), 
                                        set("amount_in_wallet", d.assignedCustomer.w.money),
                                        set("trip_start", 0),
                                        set("trip_end", 0)));
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
                                      set("rating", d.rating), set("location", loc)));
                }
                d.assignedCustomer.assignedDriver = null;
                d.assignedCustomer = null;
                d.loc = loc;
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

    public boolean startTrip(Driver d, double price, long trip_time)
    {
        if(d.isInTrip == false)
        {
            d.isInTrip = true;
            d.assignedCustomer.isInTrip = true;
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
                            set("trip_end", start+trip_time)));
                }
                MongoCollection<Document> drivers = db.getCollection("drivers");
                cursor = drivers.find(eq("name", d.username)).first();
                if(cursor == null)
                {
                    return false;
                }
                else
                {
                    drivers.updateOne(eq("name", d.username), set("in_trip", true));
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
        try
        {
            MongoCollection<Document> customers = db.getCollection("customers");
            Document cursor = customers.find(and(eq("name", username), eq("password", password))).first();
            System.out.println(cursor);
            if(cursor == null)
            {
                return null;
            }
            else
            {
                System.out.println("reached here");
                Customer c = new Customer(username, password);
                System.out.println("done here");
                String s=""+cursor.get("amount_in_wallet");
                Double d = Double.valueOf(s);
                System.out.println(s);
////                c.username = username;
////                c.password = password;
////                //System.out.println(" yeetus dat fetus");
////                Object d = cursor.get("amount_in_wallet");
////                Double obj = (Double)d;
////                System.out.println("hi");
////               // System.out.println(cursor.get("amount_in_wallet").getClass().getName());
////               // System.out.println(cursor.get("amount_in_wallet").toString());
////                //System.out.println(Double.valueOf(cursor.get("amount_in_wallet").toString()));
                c.w.money = d;
////                System.out.println("fetus deletus");
                c.loc =  (String)cursor.get("location");
                c.isInTrip = Boolean.parseBoolean(""+cursor.get("in_trip"));
//                return null;
                
                return c;
            }
        }
        catch(Exception e)
        {
            System.out.println("ERRRRRRR");
            System.out.println(e);
            return null;
        }
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
                double money = Double.parseDouble((String)cursor.get("amount_in_wallet"));
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
        try
        {
            MongoCollection<Document> drivers = db.getCollection("drivers");
            MongoCursor<Document> cursor = drivers.find(and(eq("location", loc), eq("in_trip", false))).iterator();
            int rating = 0;
            while(cursor.hasNext())
            {
                if( Integer.parseInt((String)cursor.next().get("rating")) >= rating)
                {
                    d = new Driver();
                    d.name = (String)cursor.next().get("name");
                    d.rating = Integer.parseInt((String)cursor.next().get("rating"));
                    d.loc = loc;
                }
            }
            cursor.close();
        }
        catch(Exception e)
        {
            System.out.println("Database not accessible : " + e);
            return null;
        }
        return d;
    }
}