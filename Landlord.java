import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;

public class Landlord {
    private String LRDname;
    private String LRDpnum;
    private static String[] addedHouses = new String[100];
    private static int numAddedHouses = 0;
    public static DecimalFormat df = new DecimalFormat("0.00");
    
    public Landlord() {
        LRDname = "";
        LRDpnum = "";
    }
    
    public Landlord(String name, String pnum) {
        LRDname = name;
        LRDpnum = pnum;
    }
    
    public String getLRDname() {
        return LRDname;
    }
    
    public String getLRDpnum() {
        return LRDpnum;
    }
    
    public void setLRDname(String name) {
        LRDname = name;
    }
    
    public void setLRDpnum(String pnum) {
        LRDname = pnum;
    }
    
    public static void editDeposit(String find) {
        boolean continueProcessing = true;
        Scanner sc = new Scanner(System.in);
        BufferedReader reader = null;
        String houseType = "";
        String place = "";
        int bedrooms = 0;
        int bathrooms = 0;
        String detail = "";
        double rent = 0.0;
        double deposit = 0.0;

        try {
            reader = new BufferedReader(new FileReader("houses.txt"));
            String line;
            boolean found = false;
            int count = 1; // Counter for numbering the matching houses

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                if (tokens[0].trim().equalsIgnoreCase(find)) {
                    found = true;
                    houseType = tokens[0].trim();
                    place = tokens[1].trim();
                    bedrooms = Integer.parseInt(tokens[2].trim());
                    bathrooms = Integer.parseInt(tokens[3].trim());
                    detail = tokens[4].trim();
                    rent = Double.parseDouble(tokens[5].trim());
                    deposit = Double.parseDouble(tokens[6].trim());

                    // Display the table header only when the first matching house is found
                    if (count == 1) {
                        System.out.println("\n+---------------------------------------------------------------------------------------------------------------+");
                        System.out.println("|                                                Search House                                                   |");
                        System.out.println("+---------------------------------------------------------------------------------------------------------------+");
                        System.out.printf("| %-3s | %-12s | %-14s | %-8s | %-9s | %-14s | %-16s | %-12s |%n", "No.", "House Type", "Place",
                            "Bedrooms", "Bathrooms", "Details", "Monthly Rent(RM)", "Deposit(RM)");
                        System.out.println("+---------------------------------------------------------------------------------------------------------------+");
                    }   

                    System.out.printf("| %-3s | %-12s | %-14s | %-8s | %-9s | %-14s | %-16s | %-12s |%n", count, houseType, place,
                                        bedrooms, bathrooms, detail, rent, deposit);

                    count++; // Increment the counter
                }
            }

            // Display table footer if at least one house is found
            if (found) {
                System.out.println("+---------------------------------------------------------------------------------------------------------------+");
            }

            if (!found) {
                System.out.println("\nNo matching houses found.");
                return;
            }

            String input = JOptionPane.showInputDialog(null, "Enter number of the house you want to choose (1-" + (count - 1) + "):");
    
            // Check if the user clicked "Cancel"
            if (input == null) {
                JOptionPane.showMessageDialog(null, "Edit deposit canceled.");
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
            ArrayList<String> updatedContent = new ArrayList<>();

            System.out.print('\u000C');
            System.out.println("+-----------------------------------------+");
            System.out.println("|     HOUSE RENTAL SYSTEM (HRS-UiTM)      |");
            System.out.println("+-----------------------------------------+");
            System.out.println("[LANDLORD MENU]");
            System.out.println("3. Edit Deposit");

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                if (tokens[0].trim().equalsIgnoreCase(find)) {
                    chosenHouse++;
                    if (chosenHouse == choice) {
                        houseType = tokens[0].trim();
                        place = tokens[1].trim();
                        bedrooms = Integer.parseInt(tokens[2].trim());
                        bathrooms = Integer.parseInt(tokens[3].trim());
                        detail = tokens[4].trim();
                        rent = Double.parseDouble(tokens[5].trim());
                        deposit = Double.parseDouble(tokens[6].trim());

                        System.out.println("\n+-------------------------------------------------------------------------------------------------------------+");
                        System.out.println("|                                                Chosen House                                                 |");
                        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
                        System.out.printf("| %-12s | %-14s | %-8s | %-9s | %-14s | %-16s | %-16s |%n", "House Type", "Place",
                                            "Bedrooms", "Bathrooms", "Details", "Monthly Rent(RM)", "Old deposit(RM)");
                        System.out.println("+-------------------------------------------------------------------------------------------------------------+");

                        System.out.printf("| %-12s | %-14s | %-8s | %-9s | %-14s | %-16s | %-16s |%n", houseType, place,
                                            bedrooms, bathrooms, detail, rent, deposit);
                        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
                        count++; // Increment the counter

                        // Calculate the new deposit
                        System.out.println("\nChoose which type of house deposit would you like to use: ");
                        System.out.println("+-------+------------------------------------------------------+");
                        System.out.println("|Formula|             Explanation                              |");
                        System.out.println("+-------+------------------------------------------------------+");
                        System.out.println("| 1+1   | full month rent + first month rent                   |");
                        System.out.println("| 2+U   | full month rent + first month rent + utility deposit |");
                        System.out.println("| 2+1   | (full month rent)x2 + first month rent               |");
                        System.out.println("+-------+------------------------------------------------------+");
                        
                        String[] depositOptions = { "1+1", "2+U", "2+1" };
                        int depositChoice = JOptionPane.showOptionDialog(null, "Select a deposit formula:",
                                            "Deposit Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, depositOptions, null);

                        double newDeposit = 0.0;
                        if (depositChoice == JOptionPane.CLOSED_OPTION) {
                            JOptionPane.showMessageDialog(null, "Deposit selection canceled.");
                            return;
                        } else if (depositChoice == 0) {
                            System.out.println("\nYou've chosen 1+1 deposit formula");
                            newDeposit = rent * 2;
                        } else if (depositChoice == 1) {
                            System.out.println("\nYou've chosen 2+U deposit formula");
                            String utilityInput = JOptionPane.showInputDialog(null, "Enter utility: ");
                            if (utilityInput == null) {
                                JOptionPane.showMessageDialog(null, "Utility input canceled. Exiting.");
                                return;
                            }
                            double utility = Double.parseDouble(utilityInput);
                            newDeposit = (rent * 2) + utility;
                        } else if (depositChoice == 2) {
                            System.out.println("\nYou've chosen 2+1 deposit formula");
                            newDeposit = rent * 3;
                        }

                         tokens[6] = String.valueOf(newDeposit);
                        line = String.join(";", tokens);
                        System.out.println("New deposit: RM" + newDeposit);

                        // Append the updated line to the updatedContent list
                        updatedContent.add(line);
                    } else {
                        // For non-matching houses, add the original line to the updatedContent list
                        updatedContent.add(line);
                    }
                } else {
                    // For non-matching houses, add the original line to the updatedContent list
                    updatedContent.add(line);
                }
            }

            // Write the updated content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("houses.txt"))) {
                for (String updatedLine : updatedContent) {
                    writer.write(updatedLine + "\n");
                }
                JOptionPane.showMessageDialog(null, "Deposit updated");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void addHouse() {
        Scanner sc = new Scanner(System.in);
        System.out.print('\u000C');
        System.out.println("+-----------------------------------------+");
        System.out.println("|     HOUSE RENTAL SYSTEM (HRS-UiTM)      |");
        System.out.println("+-----------------------------------------+");
        System.out.println("[LANDLORD MENU]");
        System.out.println("1. Add House");
        System.out.println("--------------- Add House -----------------\n");
        int choice;
        String s = JOptionPane.showInputDialog(null, "How many houses to add?");
        int size = 0;
        if (s == null) {
            JOptionPane.showMessageDialog(null, "Input canceled");
            return;
        } else {
            try {
                size = Integer.parseInt(s);
                System.out.println("---> Number of houses to add: " + size + "\n");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
        }

        if (size <= 0) {
            JOptionPane.showMessageDialog(null, "Number of houses must be a positive integer.");
            addHouse();
        } else {
            House[] houses = new House[size];
            
            for (int i = 0; i < houses.length; i++) {
                System.out.println("No. " + (i + 1));
                String[] houseTypes = {"Terrace", "Apartment", "Condominium"};
                choice = JOptionPane.showOptionDialog(null, "Select type of house:", "House Type",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, houseTypes, null);

                String hType = "";
                if (choice == 0) {
                    hType = "Terrace";
                    System.out.println("House type selected: Terrace");
                } else if (choice == 1) {
                    hType = "Apartment";
                    System.out.println("House type selected: Apartment");
                } else if (choice == 2) {
                    hType = "Condominium";
                    System.out.println("House type selected: Condominium");
                } else {
                    return; // Exit the method if the user cancels the selection
                }

                String place = JOptionPane.showInputDialog(null, "Enter place: ");
                if (place != null) {
                    System.out.println("Place: " + place);
                } else {
                    JOptionPane.showMessageDialog(null, "Input canceled !");
                    return; // Exit the method if the user cancels the input
                }

                String b = JOptionPane.showInputDialog(null, "Enter the number of bedrooms: ");
                int bedroom = 0;
                if (b != null) {
                    try {
                        bedroom = Integer.parseInt(b);
                        System.out.println("Number of bedrooms: " + bedroom);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid input");
                        return; // Exit the method if the input is invalid
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Input canceled");
                    return; // Exit the method if the user cancels the input
                }

                String bath = JOptionPane.showInputDialog(null, "Enter the number of bathrooms: ");
                int bathroom = 0;
                if (bath != null) {
                    try {
                        bathroom = Integer.parseInt(bath);
                        System.out.println("Number of bathrooms: " + bathroom);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid input");
                        return; // Exit the method if the input is invalid
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Input canceled");
                    return; // Exit the method if the user cancels the input
                }

                String br = JOptionPane.showInputDialog(null, "Enter monthly base rent: ");
                double baseRent = 0.00;
                if (br != null) {
                    try {
                        baseRent = Double.parseDouble(br);
                        System.out.println("Monthly base rent: RM" + df.format(baseRent));
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid input");
                        return; // Exit the method if the input is invalid
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Input canceled");
                    return; // Exit the method if the user cancels the input
                }   
                String lift = "";
                double deposit = 0.0;
                if (hType.equalsIgnoreCase("apartment")) {
                    Object[] opt = {"Yes", "No"};
                    choice = JOptionPane.showOptionDialog(null, "Choose lift availability", "Lift Availability", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, opt, null);
                    if (choice == 0)
                        lift = "Has Lift";
                    else if (choice == 1)
                        lift = "No Lift";
                    else {
                        return; // Exit the method if the user cancels the selection
                    }

                    System.out.println("Lift availability: " + lift);
                    System.out.println("+-----------------------------------------+");
                    System.out.println("| Apartment additional charge:            |");
                    System.out.println("| Has Lift: RM50                          |");
                    System.out.println("| No Lift : No charge                     |");
                    System.out.println("+-----------------------------------------+");
                    
                    houses[i] = new Apartment(hType, place, bedroom, bathroom, baseRent, lift);
                    
                    if (houses[i] instanceof Apartment) {
                        System.out.println("Monthly rent: RM" + houses[i].calculateRent());
                        deposit = houses[i].calculateRent() * 2;
                    }

                    try (FileWriter writer = new FileWriter("houses.txt", true)) {
                        String houseInfo = hType + ";" + place + ";" + bedroom + ";" + bathroom + ";" + lift + ";" + houses[i].calculateRent() + ";" + deposit;
                        writer.write(houseInfo);
                        writer.write("\n");
                        
                        // Add the newly added house details to the array
                        addedHouses[numAddedHouses] = houseInfo;
                        numAddedHouses++;
                    } catch (IOException e) {
                        System.out.println("An error occurred while writing to the file: " + e.getMessage());
                    }

                } else if (hType.equalsIgnoreCase("condominium")) {
                    Object[] opt = {"Yes", "No"};
                    choice = JOptionPane.showOptionDialog(null, "Choose lift availability", "Lift Availability", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, opt, null);
                    if (choice == 0)
                        lift = "Has Lift";
                    else if (choice == 1)
                        lift = "No Lift";
                    else {
                        return; // Exit the method if the user cancels the selection
                    }

                    System.out.println("Lift availability: " + lift);
                    System.out.println("+-----------------------------------------+");
                    System.out.println("| Condominium additional charge:          |");
                    System.out.println("| Has Lift: RM80                          |");
                    System.out.println("| No Lift : No charge                     |");
                    System.out.println("+-----------------------------------------+");
                    houses[i] = new Condominium(hType, place, bedroom, bathroom, baseRent, lift);
                    if (houses[i] instanceof Condominium) {
                        System.out.println("Monthly rent: RM" + houses[i].calculateRent());
                        deposit = houses[i].calculateRent() * 2; //calculate deposit using 2+1 deposit formula (collateral + first month rent + 
                    }

                    try (FileWriter writer = new FileWriter("houses.txt", true)) {
                        String houseInfo = hType + ";" + place + ";" + bedroom + ";" + bathroom + ";" + lift + ";" + houses[i].calculateRent() + ";" + deposit;
                        writer.write(houseInfo);
                        writer.write("\n");
                        
                        // Add the newly added house details to the array
                        addedHouses[numAddedHouses] = houseInfo;
                        numAddedHouses++;
                    } catch (IOException e) {
                        System.out.println("An error occurred while writing to the file: " + e.getMessage());
                    }

                } else if (hType.equalsIgnoreCase("terrace")) {
                    Object[] opt = {"Single-Storey", "Double-Storey"};
                    choice = JOptionPane.showOptionDialog(null, "Choose type of storey", "Storey type", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, opt, null);
                    String storey = "";

                    if (choice == 0)
                        storey = "Single-Storey";
                    else if (choice == 1)
                        storey = "Double-Storey";
                    else {
                        return; // Exit the method if the user cancels the selection
                    }

                    System.out.println("Type of storey building: " + storey);
                    System.out.println("+-----------------------------------------+");
                    System.out.println("| Terrace additional charge:              |");
                    System.out.println("| Single-Storey: RM50                     |");
                    System.out.println("| Double-Storey: RM100                    |");
                    System.out.println("+-----------------------------------------+");
                    houses[i] = new Terrace(hType, place, bedroom, bathroom, baseRent, storey);
                    if (houses[i] instanceof Terrace) {
                        System.out.println("Monthly rent: RM" + houses[i].calculateRent());
                        deposit = houses[i].calculateRent() * 2;
                    }

                    try (FileWriter writer = new FileWriter("houses.txt", true)) {
                        String houseInfo = hType + ";" + place + ";" + bedroom + ";" + bathroom + ";" + storey + ";" + houses[i].calculateRent() + ";" + deposit;
                        writer.write(houseInfo);
                        writer.write("\n");
                        
                        addedHouses[numAddedHouses] = houseInfo;
                        numAddedHouses++;
                    } catch (IOException e) {
                        System.out.println("An error occurred while writing to the file: " + e.getMessage());
                    }
                }
                System.out.println("-------------------------------------------\n");
            }
            JOptionPane.showMessageDialog(null, "House added");
            System.out.print('\u000C');
            System.out.println("+-----------------------------------------+");
            System.out.println("|     HOUSE RENTAL SYSTEM (HRS-UiTM)      |");
            System.out.println("+-----------------------------------------+");
            System.out.println("[LANDLORD MENU]");
            System.out.println("1. Add House");
            System.out.println("\n+---------------------------------------------------------------------------------------------------------------+");
            System.out.println("|                                                Newly Added Houses                                             |");
            System.out.println("+---------------------------------------------------------------------------------------------------------------+");
            System.out.printf("| %-3s | %-12s | %-14s | %-8s | %-9s | %-14s | %-16s | %-12s |%n", "No.", "House Type", "Place", "Bedrooms",
                                        "Bathrooms", "Details", "Monthly Rent(RM)", "Deposit(RM)");
            System.out.println("+---------------------------------------------------------------------------------------------------------------+");
            
            // Display the newly added houses
            for (int i = 0; i < numAddedHouses; i++) {
                String houseDetails = addedHouses[i];
                String[] tokens = houseDetails.split(";");
                String houseType = tokens[0].trim();
                String place = tokens[1].trim();
                int bedrooms = Integer.parseInt(tokens[2].trim());
                int bathrooms = Integer.parseInt(tokens[3].trim());
                String details = "";
                if (houseType.equalsIgnoreCase("apartment")) {
                    details = tokens[4].trim();
                } else if (houseType.equalsIgnoreCase("terrace")) {
                    details = tokens[4].trim();
                } else if (houseType.equalsIgnoreCase("condominium")) {
                    details = tokens[4].trim();
                }   
                double rent = Double.parseDouble(tokens[5].trim());
                double deposit = Double.parseDouble(tokens[6].trim());

                System.out.printf("| %-3s | %-12s | %-14s | %-8s | %-9s | %-14s | %-16s | %-12s |%n",i+1, houseType, place,
                    bedrooms, bathrooms, details, df.format(rent), df.format(deposit));
            }
            System.out.println("+---------------------------------------------------------------------------------------------------------------+");
            System.out.println("                                           Press enter to go to menu");
            sc.nextLine();

            // If no newly added houses, display a message
            if (numAddedHouses == 0) {
                System.out.println("No newly added houses to display");
            }
        }
    }
    
    public static boolean Exit(){
        boolean system = true;
        int exit = JOptionPane.showConfirmDialog(null, "Are you sure to exit the program?", "EXIT", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if(exit == JOptionPane.YES_OPTION){
            system = false;
            System.out.print('\u000C');
            System.out.println("+-----------------------------------------+");
            System.out.println("|      THANK YOU FOR USING HRS-UiTM       |");
            System.out.println("+-----------------------------------------+");
        }
        return system;
    }
}