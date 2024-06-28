package BloodBank;

import java.util.Scanner;

import AuthService.AuthService;
import AuthService.User;
import FileService.FileService;

public class BloodBank {

    static Blood[] bloods = new Blood[8];
    public static Scanner scanner = new Scanner(System.in);

    public static AuthService authService = new AuthService();

    public BloodBank() {
        Blood[] bloodRecords = FileService.readBloodRecord();

        if (bloodRecords != null) {
            for (int i = 0; i < bloodRecords.length; i++) {
                bloods[i] = bloodRecords[i];
            }
        }
        authService.service();
    }

    public static void adminView() {
        System.out.println("Blood Bank");
        System.out.println("Blood Type\tQuantity");
        while (true) {
            System.out.println("1. Check Blood Records");
            System.out.println("2. Check Donation Records");
            System.out.println("3. Check Blood Waiting Records");
            System.out.println("4. Approve Blood");
            System.out.println("5. Approve Admin");
            System.out.println("6. Logout");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    checkBloodRecords();
                    break;
                case 2:
                    fullHistory();
                    break;
                case 3:
                    checkWaitingBloodRecords();
                    break;
                case 4:
                    approveBlood();
                    break;
                case 5:
                    approveAdmin();
                    break;
                case 6:
                    authService.logout();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        }
    }

    public static void userView() {
        System.out.println("Blood Bank");
        while (true) {
            System.out.println("1. donate blood");
            System.out.println("2. request blood");
            System.out.println("3. history");
            System.out.println("4. logout");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    donateBlood();
                    break;
                case 2:
                    requestBlood();
                    break;
                case 3:
                    history();
                    break;
                case 4:
                    authService.logout();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        }
    }

    public static void checkBloodRecords() {
        System.out.println("Blood Records");
        FileService.checkBloodDetails();
    }

    public static void checkWaitingBloodRecords() {
        System.out.println("checkWaitingBloodRecords");
    }

    public static void approveBlood() {
        System.out.println("Approve Blood");
    }

    public static void approveAdmin() {
        int unApprovedAdminCount = FileService.unApprovedAdmins();
        if (unApprovedAdminCount != 0) {
            try {
                System.out.println("Approve Admin");
                System.out.println("Enter the admin id to approve: ");
                int adminId = scanner.nextInt();
                scanner.nextLine();
                FileService.approveAdmin(adminId);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void donateBlood() {
        try {
            Blood blood = new Blood();
            scanner.nextLine();
            blood.takeInput(scanner);
            FileService.addBlood(blood);
            User user = authService.getUser();
            System.out.println(user);
            FileService.addRecord(blood, user);
            System.out.println("Blood added successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void history() {
        User user = authService.getUser();
        FileService.printUserRecord(user);
    }

    public static void fullHistory() {
        FileService.printAllRecords();
    }

    public static void requestBlood() {
        System.out.println("Request Blood");
    }
}
