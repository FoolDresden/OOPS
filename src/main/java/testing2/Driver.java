package testing2;
class Driver extends User 
{
    double rating;
    String loc;
    boolean isInTrip;
    Customer assignedCustomer;
    String name;
    Driver()
    {
        rating = 3.0;
        isInTrip = false;
        assignedCustomer = null;
    }
    public boolean AssignCustomer(Customer c1)
    {
        if(!isInTrip)
        {
            assignedCustomer = c1;
            isInTrip = c1.isInTrip = true;
            return true;
        }
        return false;
    } 

  
}