import java.text.DecimalFormat;

public class Apartment extends House {
    private String hasLift;
    DecimalFormat df = new DecimalFormat("0.00");
    
    public Apartment(String type, String a, int bed, int bath, double br, String lift) {
        super(type, a, bed, bath, br);
        hasLift = lift;
    }
    
    public double calculateRent() {
        // Apartment charge with lift for monthly maintenance
        double addCharge = 0.00;
        if(hasLift.equalsIgnoreCase("Has Lift"))
            addCharge = 50;
    
        else if(hasLift.equalsIgnoreCase("No Lift"))
            addCharge = 0;
        double rent = getBaseRent() + addCharge;
        return rent;
    }
    
    public String toString() {
        return df.format(calculateRent());
    }
}