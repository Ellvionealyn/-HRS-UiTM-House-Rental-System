import java.io.BufferedWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class NRstudent {
    private String NRname;
    private String NRpnum;
    private String NRid;
    private String NRic;
    
    public NRstudent() {
        NRname = "";
        NRpnum = "";
        NRid = "";
        NRic = "";
    }

    public NRstudent(String nrName, String nrPnum, String nrID, String nrIC) {
        NRname = nrName;
        NRpnum = nrPnum;
        NRid = nrID;
        NRic = nrIC;
    }

    public String getNRname() {
        return NRname;
    }

    public String getNRpnum() {
        return NRpnum;
    }

    public String getStudentId() {
        return NRid;
    }
    
    public String getICnum() {
        return NRic;
    }
    
    public void setNRname(String nrName) {
        NRname = nrName;
    }
    
    public void setNRpnum(String nrPnum) {
        NRpnum = nrPnum;
    }
    
    public void setNRid(String nrID) {
        NRid = nrID;
    }
    
    public void setNRic(String nrIC) {
        NRic = nrIC;
    }
    
    public static void searchHouses(String keyword) {
        BufferedReader reader = null;
        String houseType = "";
        String place = "";
        int bedrooms = 0;
        int bathrooms = 0;
        String detail = "";
        double rent = 0.0;
        double deposit = 0.0;

        boolean found = false;
        try {
            reader = new BufferedReader(new FileReader("houses.txt"));
            String line;
            int count = 1; // Counter for numbering the matching houses

            System.out.println("\n+---------------------------------------------------------------------------------------------------------------+");
            System.out.println("|                                                Search House                                                   |");
            System.out.println("+---------------------------------------------------------------------------------------------------------------+");
            System.out.printf("| %-3s | %-12s | %-14s | %-8s | %-9s | %-14s | %-16s | %-12s |%n", "No.", "House Type", "Place", "Bedrooms",
                            "Bathrooms", "Details", "Monthly Rent(RM)", "Deposit(RM)");
            System.out.println("+---------------------------------------------------------------------------------------------------------------+");
        
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                if (tokens[0].equalsIgnoreCase(keyword)) {
                    found = true;
                    houseType = tokens[0].trim();
                    place = tokens[1].trim();
                    bedrooms = Integer.parseInt(tokens[2].trim());
                    bathrooms = Integer.parseInt(tokens[3].trim());
                    detail = tokens[4].trim();
                    rent = Double.parseDouble(tokens[5].trim());
                    deposit = Double.parseDouble(tokens[6].trim());

                    System.out.printf("| %-3s | %-12s | %-14s | %-8s | %-9s | %-14s | %-16s | %-12s |%n",
                                        count, houseType, place, bedrooms, bathrooms, detail, rent, deposit);
    
                    count++; // Increment the counter
                }
            }
    
            System.out.println("+---------------------------------------------------------------------------------------------------------------+");
    
            if (!found) {
                cs();
                System.out.println("[NR-STUDENT MENU]");
                System.out.println("1. House Booking");
                System.out.println("\nNo matching houses found.");
                return;
            }

            String input = JOptionPane.showInputDialog(null, "Enter number of the house you want to choose (1-" + (count - 1) + "):");

            // Check if the user clicked "Cancel"
            if (input == null) {
                JOptionPane.showMessageDialog(null, "House booking canceled.");
                return;
            }

            int choice = Integer.parseInt(input);

            if (choice < 1 || choice >= count) {
                JOptionPane.showMessageDialog(null, "Invalid choice. Exiting.");
                return;
            }

            // Reset the reader to read from the beginning of the file
            reader.close();
            reader = new BufferedReader(new FileReader("houses.txt"));

            int chosenHouse = 0;
            cs();
            System.out.println("[NR-STUDENT MENU]");
            System.out.println("1. House Booking");
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                if (tokens[0].trim().equalsIgnoreCase(keyword)) {
                    chosenHouse++;
                    if (chosenHouse == choice) {
                        houseType = tokens[0].trim();
                        place = tokens[1].trim();
                        bedrooms = Integer.parseInt(tokens[2].trim());
                        bathrooms = Integer.parseInt(tokens[3].trim());
                        detail = tokens[4].trim();
                        rent = Double.parseDouble(tokens[5].trim());
                        deposit = Double.parseDouble(tokens[6].trim());

                        String houseDetails = "House Type: " + houseType + "\n" +
                                            "Place: " + place + "\n" +
                                            "Number of Bedrooms: " + bedrooms + "\n" +
                                            "Number of Bathrooms: " + bathrooms + "\n" +
                                            "Details: " + detail + "\n" +
                                            "Monthly Rent: RM" + rent + "\n" +
                                            "Deposit: RM" + deposit;
    
                        System.out.println("\n-------------- Chosen House ---------------");
                        System.out.println(houseDetails);
                        System.out.println("-------------------------------------------");
                    
                        if (found) {
                            System.out.println("             NR-Student Details            ");
                            System.out.println("-------------------------------------------");
                            bookHouse(houseType, place, bedrooms, bathrooms, detail, rent, deposit);
                        }
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void bookHouse(String houseType, String place, int bedrooms, int bathrooms, String detail, double rent, double deposit) {
        String bookingID = bookingID();
        String name = getInput("Enter your name:");
        String studentID = getInput("Enter your student ID:");
        String icNum = getInput("Enter your IC number:");
        String phoneNumber = getInput("Enter your phone number:");

        if (name == null || studentID == null || icNum == null || phoneNumber == null) {
            JOptionPane.showMessageDialog(null, "Booking canceled. Missing information.");
            return;
        }

        String bookingDetails = "Booking ID: " + bookingID + "\n" +
                                "Name: " + name + "\n" +
                                "Student ID: " + studentID + "\n" +
                                "IC Number: " + icNum + "\n" +
                                "Phone Number: " + phoneNumber + "\n" +
                                "House Type: " + houseType + "\n" +
                                "Place: " + place + "\n" +
                                "Number of Bedrooms: " + bedrooms + "\n" +
                                "Number of Bathrooms: " + bathrooms + "\n" +
                                "Detail: " + detail + "\n" +
                                "Monthly Rent: RM" + rent + "\n" +
                                "Deposit: RM" + deposit;

        System.out.println(bookingDetails);

        int choice = JOptionPane.showConfirmDialog(null,
                "Do you want to pay the deposit?", "Pay Deposit",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            writeBookingDetails(bookingID, name, studentID, icNum, phoneNumber, houseType, place, bedrooms, bathrooms, detail, rent, deposit);
            JOptionPane.showMessageDialog(null, "Booking successful! Details saved.");
            System.out.print('\u000C');
        } else {
            JOptionPane.showMessageDialog(null, "Booking canceled.");
        }
    }

    public static String getInput(String message) {
        String input = JOptionPane.showInputDialog(null, message);
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        return input;
    }

    public static void writeBookingDetails(String bookingID, String name, String studentID, String icNum, String phoneNumber,
                                           String houseType, String place, int bedrooms, int bathrooms, String detail,
                                           double rent, double deposit) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("booking.txt", true))) {
            String bookingDetails = bookingID + ";" + name + ";" + studentID + ";" + icNum + ";" + phoneNumber + ";" +
                    houseType + ";" + place + ";" + bedrooms + ";" + bathrooms + ";" + detail + ";" + rent + ";" + deposit;
            writer.write(bookingDetails);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to file: " + e.getMessage());
        }
    }
    
    public static void viewBooking() {
        System.out.print('\u000C');
        System.out.println("+-----------------------------------------+");
        System.out.println("|     HOUSE RENTAL SYSTEM (HRS-UiTM)      |");
        System.out.println("+-----------------------------------------+");
        System.out.println("[NR-STUDENT MENU]");
        System.out.println("2. View Booked House");
        Scanner sc = new Scanner(System.in);
        String bookingID = JOptionPane.showInputDialog(null, "Enter your booking ID:");

        if (bookingID == null) {
            JOptionPane.showMessageDialog(null, "Booking ID input canceled.");
            return;
        }

        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("booking.txt"))) {
            String line;

            // Read the first line to check if the file is not empty
            while ((line = reader.readLine()) != null) {
                // Display the table header
                String[] tokens = line.split(";");
                if (tokens[0].equalsIgnoreCase(bookingID)) {
                    found = true;
                    bookingID = tokens[0];
                    String name = tokens[1];
                    String studentID = tokens[2];
                    String icNum = tokens[3];
                    String phoneNumber = tokens[4];
                    String houseType = tokens[5];
                    String place = tokens[6];
                    int bedrooms = Integer.parseInt(tokens[7]);
                    int bathrooms = Integer.parseInt(tokens[8]);
                    String detail = tokens[9];
                    double rent = Double.parseDouble(tokens[10]);
                    double deposit = Double.parseDouble(tokens[11]);

                    System.out.println("\n+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                    System.out.println("|                                                                           Booked House                                                                                    |");
                    System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                    System.out.printf("| %-6s | %-19s | %-10s | %-12s | %-12s | %-12s | %-14s | %-8s | %-9s | %-13s | %-10s | %-11s |%n", "ID",
                        "Name", "Student ID", "IC Number", "Phone number", "House Type", "Place", "Bedroom", "Bathroom", "Detail", "Rent (RM)", "Deposit(RM)");
                    System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");

                    System.out.printf("| %-6s | %-19s | %-10s | %-12s | %-12s | %-12s | %-14s | %-8s | %-9s | %-13s | %-10s | %-11s |%n",
                        bookingID, name, studentID, icNum, phoneNumber, houseType, place, bedrooms, bathrooms, detail, rent, deposit);
                    System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");

                    System.out.println("                                                                        Press enter to go to menu");
                    sc.nextLine();
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "Booking ID not found");
        }
    }

    public static String bookingID(){
        // Create a new instance of the Random class
        Random random = new Random();
        
        int randomNumber = random.nextInt(1000);    // Generate a random number between 0 and 999
        
        // Generate a random character from A to Z
        char randomChar1 = (char)(random.nextInt(26) + 'A');
        char randomChar2 = (char)(random.nextInt(26) + 'A');

        // Use String.format() to add leading zeroes to the number
        String formattedNumber = String.format("%03d", randomNumber);

        // Concatenate the random characters and the formatted number to create the final ID
        String bookingId = ""+randomChar1+randomChar2 + formattedNumber;
        return bookingId;
    }
    
    public static void cs(){
        System.out.print('\u000C'); //to clear screen
        System.out.println("+-----------------------------------------+");
        System.out.println("|     HOUSE RENTAL SYSTEM (HRS-UiTM)      |");
        System.out.println("+-----------------------------------------+");
    }
}