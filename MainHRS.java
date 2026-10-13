import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainHRS {
    public static int choice;
    public static Admin adm = new Admin();
    public static Landlord lrd = new Landlord();
    public static NRstudent nr = new NRstudent();

    public static void main(String[] args) {
        Greeting();
        JOptionPane.showMessageDialog(null, "Welcome to House Rental System for NR Student in UiTM Sabah (HRS-UiTM)");
        showMenu(choice);
    }
    
    public static void showMenu(int choice) {
        // Display menu options
        Greeting();
        System.out.println("1. NR-Student");
        System.out.println("2. Landlord");
        System.out.println("3. Admin\n");
        String[] options = { "NR-Student", "Landlord", "Admin"};
        choice = JOptionPane.showOptionDialog(null,"Please choose your role:",
                "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
        if (choice == 0) {
            showNR();
        } else if (choice == 1 ) {
            showLoginMenu(choice);
        } else if (choice == 2) {
            showLoginMenu(choice);
        } else {
            lrd.Exit();
        }
    }
    
    public static void showLoginMenu(int choice) {
        String username = JOptionPane.showInputDialog(null, "Username:");
        String password = JOptionPane.showInputDialog(null, "Password:");

        boolean loginSuccessful = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("login.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] login = line.split(";");
                if (login.length == 3 && login[0].equals(username) && login[1].equals(password)) {
                    String userRole = login[2];
                    if (choice == 1) {
                        loginSuccessful = true;
                        Greeting();
                        System.out.println("[LANDLORD MENU]");
                        System.out.println("1. Add House");
                        System.out.println("2. Booked Houses Report");
                        System.out.println("3. Edit Login");
                        System.out.println("4. Exit\n");
                        showLandlord();
                    } else if (choice == 2) {
                        loginSuccessful = true;
                        Greeting();
                        System.out.println("[ADMIN MENU]");
                        System.out.println("1. Add Landlord");
                        System.out.println("2. View Landlords");
                        System.out.println("3. Edit Login");
                        System.out.println("4. Exit\n");
                        showAdmin();
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to read login information.");
            showLoginMenu(choice);
        }   

        if (!loginSuccessful) {
            String[] againOptions = {"Yes", "No"};
            int again = JOptionPane.showOptionDialog(null, "Invalid username, password, or role. Do you want to try again?", null, 
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, againOptions, null);
            if (again == 0) {
                showLoginMenu(choice);
            }
            else if (again == 1) {
                ending();
                return;
            }
        }
    }
    
    public static void showNR() {
        int bookedHouses = 0;
        String bookID;
        
        Greeting();
        System.out.println("[NR-STUDENT MENU]");
        System.out.println("1. House Booking");
        System.out.println("2. View Booked House");
        System.out.println("3. Exit\n");
        String[] options = {"House Booking", "View Booked House", "Exit"};
        int nrChoice = JOptionPane.showOptionDialog(null, "Non-Resident Student Main Menu\n" + "Please select your desired action:", "HRS-UiTM",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
    
        // House booking
        if (nrChoice == 0) {
            // House booking
            Greeting();
            System.out.println("[NR-STUDENT MENU]");
            System.out.println("1. House Booking");
            String[] houses = {"Terrace", "Condominium", "Apartment"};
            int choice = JOptionPane.showOptionDialog(null, "Select a house:", "House Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, houses, null);
            String keyword="";
            if(choice==0)
                keyword="Terrace";
            else if(choice==1)
                keyword="Condominium";
            else if(choice==2)
                keyword="Apartment";
                
            if(choice != 0 && choice != 1 && choice != 2) {
                showNR();
            }
            
            nr.searchHouses(keyword);
            endingNR();
        }
        // View booked houses
        else if (nrChoice == 1) { 
            nr.viewBooking();
            endingNR();
        }
        else if (nrChoice == 2) {
            endingNR();
        }
    }

    public static void showLandlord() {
        Scanner sc = new Scanner(System.in);
        
        String[] options = {"Add House", "Booked Houses Report", "Edit Deposit", "Exit"};
        int choice = JOptionPane.showOptionDialog(null, "Landlord Main Menu", "Landlord", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

        if (choice == 0) {
            lrd.addHouse();
            endinglrd();
        
        } else if (choice == 1) {
            Greeting();
            System.out.println("[LANDLORD MENU]");
            System.out.println("2. Booked Houses Report");
        
            try (BufferedReader reader = new BufferedReader(new FileReader("booking.txt"))) {
                String line;
                boolean found = false; // Flag to track if any booking is found
              
                if((line = reader.readLine()) != null){
                    found=true;
                    
                    //Display table header
                    System.out.println("\n+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                    System.out.println("|                                                                           Booked Houses Report                                                                                          |");
                    System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                    System.out.printf("| %-6s | %-19s | %-16s | %-16s | %-16s | %-12s | %-14s | %-8s | %-9s | %-13s | %-10s | %-11s |%n", "ID", "Name", "Student ID", "IC Number",
                                      "Phone number", "House Type", "Place", "Bedroom", "Bathroom", "Detail", "Rent (RM)", "Deposit(RM)");
                    System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                    
                    do{
                        String[] bookingDetails = line.split(";");
                        String bookingID = bookingDetails[0];
                        String name = bookingDetails[1];
                        String studentID = bookingDetails[2];
                        String icNum = bookingDetails[3];
                        String phoneNumber = bookingDetails[4];
                        String houseType = bookingDetails[5];
                        String place = bookingDetails[6];
                        int bedrooms = Integer.parseInt(bookingDetails[7]);
                        int bathrooms = Integer.parseInt(bookingDetails[8]);
                        String detail = bookingDetails[9];
                        Double rent = Double.parseDouble(bookingDetails[10]);
                        Double deposit = Double.parseDouble(bookingDetails[11]);
                        System.out.printf("| %-6s | %-19s | %-16s | %-16s | %-16s | %-12s | %-14s | %-8s | %-9s | %-13s | %-10s | %-11s |%n",
                                        bookingID, name, studentID, icNum, phoneNumber, houseType, place, bedrooms, bathrooms, detail, rent, deposit);
                    } while((line=reader.readLine()) != null);
                    
                    System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                }
                
                if (!found) {
                    System.out.println("\nNo booked houses found.");
                } else {
                    System.out.println("                                                                        Press enter to go to menu");
                    sc.nextLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            endinglrd();
        
        } else if(choice == 2) {
            Greeting();
            System.out.println("[LANDLORD MENU]");
            System.out.println("3. Edit Deposit");
            String[] houses = {"Terrace", "Condominium", "Apartment"};
            choice = JOptionPane.showOptionDialog(null, "Select a house:", "House Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, houses, null);
            String find="";
            if(choice==0)
                find="Terrace";
            else if(choice==1)
                find="Condominium";
            else if(choice==2)
                find="Apartment";
            lrd.editDeposit(find);
            endinglrd();
        }
        else if (choice == 3) {
            endinglrd();
        }
    }

   //Admin interface
    public static void showAdmin() {
        adm.start();
        endingAdm();
    }
    
    public static void endingNR() {
        String[] menuOptions = {"Main Menu", "NR-Student Main Menu", "Exit"};
        int select = JOptionPane.showOptionDialog(null, "You're going to exit. Select your desired menu:", "Exit Menu", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, menuOptions, null);

        if (select == 0) {
            showMenu(choice);
        } else if (select == 1) {
            showNR();
        } else if (select == 2) {
            JOptionPane.showMessageDialog(null, "Thank you for using HRS-UiTM!");
            Goodbye();
        }
    }
    
    public static void endinglrd() {
        String[] menuOptions = {"Main Menu", "Landlord Main Menu", "Exit"};
        int select = JOptionPane.showOptionDialog(null, "You're going to exit. Select your desired menu:", "Exit Menu", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, menuOptions, null);

        if (select == 0) {
            showMenu(choice);
        } else if (select == 1) {
            showLandlord();
        } else if (select == 2) {
            JOptionPane.showMessageDialog(null, "Thank you for using HRS-UiTM!");
            Goodbye();
        }
    }
    
    public static void endingAdm() {
        String[] menuOptions = {"Main Menu", "Admin Main Menu", "Exit"};
        int select = JOptionPane.showOptionDialog(null, "You're going to exit. Select your desired menu:", "Exit Menu", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, menuOptions, null);

        if (select == 0) {
            showMenu(choice);
        } else if (select == 1) {
            showAdmin();
        } else if (select == 2) {
            JOptionPane.showMessageDialog(null, "Thank you for using HRS-UiTM!");
            Goodbye();
        }
    }
    
    public static void ending() {
        String[] menuOptions = {"Main Menu", "Exit"};
        int select = JOptionPane.showOptionDialog(null, "You're going to exit. Select your desired menu:", "Exit Menu", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, menuOptions, null);

        if (select == 0) {
            showMenu(choice);
        } else if (select == 1) {
            JOptionPane.showMessageDialog(null, "Thank you for using HRS-UiTM!");
            Goodbye();
        }
    }
    
    public static void Greeting(){
        System.out.print('\u000C');
        System.out.println("+-----------------------------------------+");
        System.out.println("|     HOUSE RENTAL SYSTEM (HRS-UiTM)      |");
        System.out.println("+-----------------------------------------+");
    }
    
    public static void Goodbye(){
        System.out.print('\u000C');
        System.out.println("+-----------------------------------------+");
        System.out.println("|      THANK YOU FOR USING HRS-UiTM       |");
        System.out.println("+-----------------------------------------+");
    }
}
