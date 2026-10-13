import javax.swing.JOptionPane;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;

class Admin {
    private Landlord[] landlords;
    private int landlordCount;
    private final int lrdMax = 100;

    public Admin() {
        landlords = new Landlord[lrdMax];
        landlordCount = 0;
    }

    public static void addLandlord() {
        LinkedList<Landlord> landlordList = new LinkedList();
        Scanner sc = new Scanner(System.in);
        System.out.print('\u000C');
        System.out.println("+-----------------------------------------+");
        System.out.println("|     HOUSE RENTAL SYSTEM (HRS-UiTM)      |");
        System.out.println("+-----------------------------------------+");
        System.out.println("[ADMIN MENU]");
        System.out.println("1. Add Landlord");
        System.out.println("+------------- Add Landlord --------------+");
        
        //landlord's name input
        String lrdName = JOptionPane.showInputDialog(null, "Enter landlord's name: ");
        
        //handle cancel choice
        if(lrdName==null){
            JOptionPane.showMessageDialog(null, "Adding landlord cancelled");
            return;
        } else {
            //Check if name already exists
            boolean nameExists = false;
            try (BufferedReader reader = new BufferedReader(new FileReader("login.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] login = line.split(";");
                    if(login.length >= 1 && login[0].equals(lrdName)) {
                        nameExists = true;
                        break;
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to read landlord's information");
                return;
            }
        
            if(nameExists) {
                JOptionPane.showMessageDialog(null, "Name already exists");
                addLandlord();
                return;
            }
        }
            
        //landlord's pnum input
        String lrdPnum = JOptionPane.showInputDialog(null, "Enter landlord's phone number: ");
        
        //handle cancel choice
        if(lrdPnum == null){
            JOptionPane.showMessageDialog(null, "Adding landlord cancelled");
            return;
        } else {
            //Check if name already exists
            boolean pnumExists = false;
            try (BufferedReader reader = new BufferedReader(new FileReader("login.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] login = line.split(";");
                    if(login.length >= 1 && login[1].equals(lrdPnum)) {
                        pnumExists = true;
                        break;
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to read landlord's information");
                return;
            }
        
            if(pnumExists) {
                JOptionPane.showMessageDialog(null, "Phone number already exists");
                addLandlord();
                return;
            }
        
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("login.txt", true))) {
                writer.write(lrdName + ";" + lrdPnum + ";" + "landlord\n");
                landlordList.add(new Landlord(lrdName, lrdPnum));
                JOptionPane.showMessageDialog(null, "Landlord added");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to write information");
            }
        }
        
        System.out.println("Landlord's name: " + lrdName);
        System.out.println("Landlord's phone number: " + lrdPnum);
    }

    public void viewLandlordReport() {
        Scanner sc = new Scanner(System.in);
        System.out.print('\u000C');
        System.out.println("+-----------------------------------------+");
        System.out.println("|     HOUSE RENTAL SYSTEM (HRS-UiTM)      |");
        System.out.println("+-----------------------------------------+");
        System.out.println("[ADMIN MENU]");
        System.out.println("2. Landlord Report");
    
        boolean landlordsFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("login.txt"))) {
            String line;
            int count = 1;
    
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                if (tokens.length >= 3 && tokens[2].equalsIgnoreCase("landlord")) {
                    landlordsFound = true;

                    // display table header
                    if (count == 1) {
                        System.out.println("\n+-----------------------------------------+");
                        System.out.println("|             Landlords Report            |");
                        System.out.println("+-----------------------------------------+");
                        System.out.printf("| %-4s | %-15s | %-14s |%n", "No.", "Name", "Phone number");
                        System.out.println("+-----------------------------------------+");
                    }

                    System.out.printf("| %-4s | %-15s | %-14s |%n", count, tokens[0], tokens[1]);
                    count++;
                }
            }

            if (!landlordsFound) {
                System.out.println("\nNo landlords found.");
            } else {
                System.out.println("+-----------------------------------------+");
                System.out.println("       Press enter to go to menu");
                sc.nextLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading existing landlords: " + e.getMessage());
        }
    }
    
    public static void editLogin() {
        // Read login data from file
        System.out.print('\u000C');
        System.out.println("+-----------------------------------------+");
        System.out.println("|     HOUSE RENTAL SYSTEM (HRS-UiTM)      |");
        System.out.println("+-----------------------------------------+");
        System.out.println("[ADMIN MENU]");
        System.out.println("3. Edit Login");
        String[] loginData;
        int totalLines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("login.txt"))) {
            String line;
            while (reader.readLine() != null) {
                totalLines++;
            }
            loginData = new String[totalLines];
        } catch (IOException e) {
            System.out.println("Error reading login data: " + e.getMessage());
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("login.txt"))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                loginData[index] = line;
                index++;
            }
        } catch (IOException e) {
            System.out.println("Error reading login data: " + e.getMessage());
            return;
        }

        // Prompt for existing username and password
        String existingUsername = JOptionPane.showInputDialog(null, "Enter existing username:");
        String existingPassword = JOptionPane.showInputDialog(null, "Enter existing password:");

        // Find matching login data
        boolean found = false;
        int index = -1;
        for (int i = 0; i < totalLines; i++) {
            String[] loginInfo = loginData[i].split(";");
            if (loginInfo[0].equals(existingUsername) && loginInfo[1].equals(existingPassword)) {
                found = true;
                index = i;
                break;
            }
        }

        if (!found) {
            System.out.println("Invalid username or password");
            return;
        }

        // Prompt for new login information
        String newUsername = JOptionPane.showInputDialog(null, "Enter new username (leave blank to keep old username):");
        String newPassword = JOptionPane.showInputDialog(null, "Enter new password (leave blank to keep old password):");

        // Update login data
        String[] loginInfo = loginData[index].split(";");
        if (!newUsername.isEmpty()) {
            loginInfo[0] = newUsername;
        }
        if (!newPassword.isEmpty()) {
            loginInfo[1] = newPassword;
        }

        // Write updated login data to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("login.txt"))) {
            for (int i = 0; i < totalLines; i++) {
                if (i == index) {
                    writer.write(loginInfo[0] + ";" + loginInfo[1] + ";" + loginInfo[2] + "\n");
                } else {
                    writer.write(loginData[i] + "\n");
                }
            }
            System.out.println("Login information updated!");
        } catch (IOException e) {
            System.out.println("Error writing login data: " + e.getMessage());
        }
    }

    public void start() {
        boolean system=true;
        Landlord lrd = new Landlord();
        boolean exit = false;
        while (!exit) {
            Object[] options = {"Add Landlords", "View Landlords", "Edit Login", "Exit"};
            int choice = JOptionPane.showOptionDialog(null,"Choose an option:","Admin Panel",JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,null,options,null);

            if (choice == 0) {
                addLandlord();
            } else if (choice == 1) {
                viewLandlordReport();
            } else if (choice == 2) {
                editLogin();
            }else if (choice == 3) {
                exit = true;
                return;
            } else { 
                system=lrd.Exit();
                break;
            }
        }
    }
}