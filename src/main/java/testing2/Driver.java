package testing2;
class Driver extends User 
{
    double rating;
    String loc;
    boolean isInTrip;
    Customer assignedCustomer;
    String name;
    int n_rides;
    Driver()
    {
        rating = 3.0;
        isInTrip = false;
        assignedCustomer = null;
        n_rides = 0;
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

    public boolean updateRating(int new_rating)
    {
        if(n_rides != 0)
        {
            rating = (rating + new_rating)/n_rides;
            return true;
        }
        else
            return false;
    } 

  
}