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
        FileService.unApprovedBlood();
    }

    public static void approveBlood() {
        int unApprovedBloodCount = FileService.unApprovedBlood();
        if (unApprovedBloodCount != 0) {
            try {
                System.out.println("Approve Blood");
                System.out.println("Enter the Blood id to approve: ");
                int bloodId = scanner.nextInt();
                scanner.nextLine();
                FileService.approveBlood(bloodId);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
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
            FileService.addRecord(blood, user, "donated");
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
        Blood blood = new Blood();
        System.out.println("Request Blood");
        blood.takeInput(scanner);
        checkBlood(blood);
    }

    public static void checkBlood(Blood blood) {
        int availableQ = FileService.checkBloodQuantity(blood, 20);
        if (availableQ < blood.quantity) {
            System.out.println("we have no enough blood. Please wait for some time.");
        } else {
            User user = authService.getUser();
            if (availableQ <= 20) {
                System.out.println("we have less blood,do you want blood urgently? (yes/no):(1/0)");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        FileService.giveBloodToUser(user, blood);
                        FileService.addRecord(blood, user, "gave");
                        System.out.println("Blood given successfully");
                        break;
                    case 0:
                        FileService.addBloodWaiting(blood, user);
                        System.out.println("You were added you to the waiting List.");
                        break;
                    default:
                        break;
                }
            } else {
                FileService.giveBloodToUser(user, blood);
                FileService.addRecord(blood, user, "gave");
                System.out.println("Blood given successfully");
            }
        }
    }
}
