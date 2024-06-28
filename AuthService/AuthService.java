package AuthService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import BloodBank.BloodBank;
import FileService.FileService;

public class AuthService {
    public static boolean isLogged = false;
    public static User user = new User();
    public static Admin admin = new Admin();
    public static Scanner scanner = new Scanner(System.in);
    public static boolean isAdmin;
    private static String USERS_FILE = "\\files\\users.txt";

    public static void addSpace() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public static void service() {
        while (true) {
            if (isLogged) {
                System.out.println("1. Logout");
                if (isAdmin) {
                    System.out.println("2. Admin Panel");
                } else {
                    System.out.println("2. User Panel");
                }

                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        isLogged = false;
                        user = new User();
                        service();
                        break;
                    case 2:
                        if (isAdmin) {
                            adminPanel();
                        } else {
                            userPanel();
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }

            } else {
                try {
                    addSpace();
                    System.out.println(
                            "Welcome to the authentication service. Please choose one of the following options to proceed:");
                    System.out.println();
                    System.out.println("1. User Panel");
                    System.out.println("2. Admin Panel");
                    System.out.println("3. Exit");
                    System.out.print("Enter your choice: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            userPanel();
                            break;
                        case 2:
                            adminPanel();
                            break;
                        case 3:
                            System.out.println("Exiting...");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Error: " + e.getMessage());
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    public static void adminPanel() {
        addSpace();
        System.out.println("Welcome to the user panel. Please choose one of the following options to proceed:");
        while (true) {
            System.out.println();
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Go back");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    adminRegister();
                    break;
                case 3:
                    service();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public static void adminLogin() {
        addSpace();
        System.out.println("Welcome to the admin panel. Please login to continue.");
        System.out.println();
        Admin newAdmin = new Admin();
        newAdmin.takeAdminDetails(scanner);

        Admin adminExists = FileService.searchAdmin(newAdmin.username, newAdmin.password);

        if (adminExists != null && adminExists.isApproved) {
            isAdmin = true;
            isLogged = true;
            System.out.println("Logged in successfully.");
            BloodBank.adminView();
            return;
        }

        if (!isLogged && !isAdmin) {
            System.out.println("Invalid credentials. Please try again.");
            while (true) {
                System.out.println("1. Try again");
                System.out.println("2. Go back");
                System.out.println("3. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        adminLogin();
                        break;
                    case 2:
                        adminPanel();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }

            }
        }
    }

    public static void adminRegister() {
        addSpace();
        isAdmin = false;
        System.out.println("Welcome to the admin Register. Please enter your credentials to continue.");
        System.out.println();

        Admin newAdmin = new Admin();
        newAdmin.setUnApproved();
        newAdmin.takeAdminDetails(scanner);
        Admin adminExists = FileService.searchAdmin(newAdmin.username, newAdmin.password);

        if (adminExists == null) {
            FileService.addAdmin(newAdmin);
            System.out
                    .println("Admin registered successfully and added to the waiting list. Please wait for approval.");
            adminPanel();
            return;
        }

        if (!isLogged && !isAdmin) {
            System.out.println("Invalid credentials. Please try again.");
            while (true) {
                System.out.println("1. Try again");
                System.out.println("2. Go back");
                System.out.println("3. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        adminRegister();
                        break;
                    case 2:
                        adminPanel();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }

            }
        }
    }

    public static void userPanel() {
        addSpace();
        System.out.println("Welcome to the user panel. Please choose one of the following options to proceed:");
        while (true) {
            System.out.println();
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Go back");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userLogin();
                    break;
                case 2:
                    userRegister();
                    break;
                case 3:
                    service();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public static void userLogin() {
        addSpace();
        isAdmin = false;
        System.out.println("Welcome to the user login. Please enter your credentials to continue.");
        System.out.println();

        System.out.println("Enter Username : ");
        String username = scanner.nextLine();
        System.out.println("Enter Password : ");
        String password = scanner.nextLine();

        User userExists = FileService.searchUser(username, password);

        if (userExists != null) {
            isLogged = true;
            isAdmin = false;
            user.setUserDetails(userExists);
            System.out.println(user);
            System.out.println("Logged in successfully.");
            BloodBank.userView();
        }

        if (!isLogged && !isAdmin) {
            System.out.println("Invalid credentials. Please try again.");
            while (true) {
                System.out.println("1. Try again");
                System.out.println("2. Go back");
                System.out.println("3. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        userLogin();
                        break;
                    case 2:
                        userPanel();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }

            }
        }
    }

    public static void userRegister() {
        addSpace();
        isAdmin = false;
        System.out.println("Welcome to the user Register. Please enter your credentials to continue.");
        System.out.println();

        User newUser = new User();

        newUser.takeUserDetails(scanner);

        checkUserData(newUser);

        User userExists = FileService.searchUserByAdhaar(newUser.adhaar_number);
        System.out.println(userExists);
        if (userExists == null) {
            FileService.addUser(newUser);
            isLogged = true;
            isAdmin = false;
            user.setUserDetails(newUser);
            System.out.println("User registered successfully.");
        }

        if (!isLogged) {
            System.out.println("Invalid credentials. Please try again.");
            while (true) {
                System.out.println("1. Try again");
                System.out.println("2. Go back");
                System.out.println("3. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        userLogin();
                        break;
                    case 2:
                        userPanel();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }

            }
        }
    }

    public static void logout() {
        isLogged = false;
        isAdmin = false;
        user = new User();
        admin = new Admin();
        service();
    }

    public static void checkUserData(User user) {

        if (!isAadharValid(user.adhaar_number)) {
            System.out.println("Invalid Aadhar Number. Please try again.");
        } else {
            if (user.age < 18) {
                System.out.println("you are not eligible to do any operation");
            } else {
                if (!isPhoneValid(user.phone_number)) {
                    System.out.println("Invalid Phone Number. Please try again.");
                } else {
                    return;
                }
            }
        }

        while (true) {
            System.out.println("1. Try again");
            System.out.println("2. Go back");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userRegister();
                    break;
                case 2:
                    userPanel();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        }
    }

    private static boolean isAadharValid(String adharNumber) {
        if (adharNumber.matches("\\d{12}")) {
            if (new File(USERS_FILE).exists()) {
                try (
                        BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length >= 6 && parts[3].equals(adharNumber)) {
                            return false;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private static boolean isPhoneValid(String phoneNumber) {
        if (phoneNumber.matches("\\d{10}")) {
            return true;
        } else {
            return false;
        }
    }

    public User getUser() {
        System.out.println(user.first_name);
        return user;
    }
}
