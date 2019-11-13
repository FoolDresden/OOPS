package testing2;
class Location
{
    double x;
    double y;
    public double getDistance(double x1, double y1)
    {
        return Math.sqrt((x-x1)*(x-x1) + (y-y1)*(y-y1));
    }
    public double getDistance(Location l2)
    {
        double x1 = l2.x;
        double y1 = l2.y;
        return Math.sqrt((x-x1)*(x-x1) + (y-y1)*(y-y1));
    }
    public static double getEstimate(double distance, double pricePerKM)
    {
        return distance*pricePerKM;
    }
}