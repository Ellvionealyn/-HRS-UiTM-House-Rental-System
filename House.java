import java.text.*;

public abstract class House {
    public String Htype;
    public String place;
    public int bedroom;
    public int bathroom;
    public double baseRent;
    DecimalFormat df = new DecimalFormat("0.00");
    
    public House() {
        Htype = "";
        place = "";
        bedroom = 0;
        bathroom = 0;
        baseRent = 0.00;
    }

    public House(String type, String a, int bed, int bath, double br) {
        Htype = type;
        place = a;
        bedroom = bed;
        bathroom = bath;
        baseRent = br;
    }

    public abstract double calculateRent();

    public String getType() {
        return Htype;
    }
    
    public void setType(String type) {
        Htype = type;
    }
    
    public String getPlace() {
        return place;
    }
    
    public void setPlace(String a) {
        place = a;
    }
    
    public int getBedroom() {
        return bedroom;
    }
    
    public void setBedroom(int bed) {
        bedroom = bed;
    }
    
    public int getBathroom() {
        return bathroom;
    }
    
    public void setBathroom(int bath) {
        bathroom = bath;
    }
    
    public double getBaseRent() {
        return baseRent;
    }
    
    public void setBaseRent(double br) {
        baseRent=br;
    }
}
