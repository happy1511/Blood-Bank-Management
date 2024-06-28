package AuthService;

import java.util.Scanner;

public class User {
    public String username;
    public String password;
    public String adhaar_number;
    public String phone_number;
    public String first_name;
    public String last_name;
    public int age;

    public void setUserDetails(String username, String password, int age, String adhaar_number,
            String phone_number, String first_name, String last_name) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.adhaar_number = adhaar_number;
        this.phone_number = phone_number;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public void setUserDetails(User user) {
        this.username = user.username;
        this.password = user.password;
        this.age = user.age;
        this.adhaar_number = user.adhaar_number;
        this.phone_number = user.phone_number;
        this.first_name = user.first_name;
        this.last_name = user.last_name;
    }

    public void takeUserDetails(Scanner scanner) {
        System.out.println("Enter Username   : ");
        this.username = scanner.nextLine();
        System.out.println("Enter Password   : ");
        this.password = scanner.nextLine();
        System.out.println("Enter First Name : ");
        this.first_name = scanner.nextLine();
        System.out.println("Enter Last Name : ");
        this.last_name = scanner.nextLine();
        System.out.println("Enter Adhaar No : ");
        this.adhaar_number = scanner.nextLine();
        System.out.println("Enter Phone No  : ");
        this.phone_number = scanner.nextLine();
        System.out.println("Enter Your Age  : ");
        this.age = scanner.nextInt();
    }

    public User isUser() {
        if (this.username != null && !this.username.isEmpty()) {
            return this;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return this.username + "," + this.password + "," + this.age + "," + this.adhaar_number
                + ","
                + this.phone_number + "," + this.first_name + "," + this.last_name;

    }

    public String getRecordString() {
        return this.first_name + "," + this.last_name + "," + this.age + "," + this.adhaar_number
                + ","
                + this.phone_number;
    }

}
