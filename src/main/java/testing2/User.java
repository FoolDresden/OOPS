package testing2;
import java.math.BigInteger;  
import java.nio.charset.StandardCharsets; 
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;
abstract class User
{
    String username;
    String password; 
    boolean quit = false;
    User()
    {
        username = "default";
        password = "abc";
        /*
        try
        {
            HashPassword();
        }
        catch(NoSuchAlgorithmException nsae)
        {
            System.out.println("Exception whilst hashing, Password not hashed");
        }
        */
    }
    public static String HashPassword(String password) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bArray = md.digest(password.getBytes(StandardCharsets.UTF_8));
        BigInteger b1 = new BigInteger(1,bArray);
        StringBuilder hexString = new StringBuilder(b1.toString(16));
        while(hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }
        password = hexString.toString();
        return password;
    }
    //abstract void StoreInDB(); //Override
}