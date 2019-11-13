package testing2;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.Random;

class Customer extends User 
{
    String username;
    String password; //to be hashed 
    Wallet w;
    String loc;
    boolean isInTrip;
    Driver assignedDriver;
    
    Customer()
    {
        
    }
    Customer(String user, String pass)
    {
        username = user;
        //password = pass;
        try
        {
          password = HashPassword(pass);  
        }
        catch(Exception e)
        {
            System.out.println("No such algorithm");
        }
        w = new Wallet(0);
        loc = "A";
        isInTrip = false;
        assignedDriver = null;
    }
   

  
    public boolean journeyStatus(Object time)
    {
        /*
        // Typecast time object here
        Thread t = new Thread(this.username + "_trip_status");
        try
        {
            t.sleep(time.interval); // Change this later (maybe)
            return true;
        }
        catch(InterruptedException ie)
        {
            String messages[] = {"WASTED", "YOU DIED", "BUSTED", "HESITATION IS DEFEAT", "ALL WE HAD TO DO WAS FOLLOW THE DAMN TRAIN, CJ!"};
            Random rand = new Random();
            int n = rand.nextInt(5);
            System.out.println(messages[n]);
            return false;
        }
        */
        return true;
    }

}
