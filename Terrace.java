import java.text.DecimalFormat;

public class Terrace extends House {
    private String storey;
    DecimalFormat df = new DecimalFormat("0.00");
    
    public Terrace(String type, String a, int bed, int bath, double br, String s) {
        super(type, a, bed, bath, br);
        storey = s;
    }
    
    public double calculateRent() {
        // Terrace charge with storey
        double addCharge = 0.00;
        if(storey.equalsIgnoreCase("single-storey")) //single storey
            addCharge= 50;
        else if(storey.equalsIgnoreCase("double-storey")) //double storey
            addCharge= 100;
        double rent = getBaseRent() + addCharge;
        return rent;
    }
    
    public String toString() {
        return df.format(calculateRent());
    }
}
