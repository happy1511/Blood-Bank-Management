package AuthService;

import java.util.Scanner;

public class Admin {
    String username;
    String password;
    boolean isApproved;

    public void setAdminDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUnApproved() {
        this.isApproved = false;
    }

    public void setApproved() {
        this.isApproved = true;
    }

    public void takeAdminDetails(Scanner scanner) {
        System.out.println("Enter Username   : ");
        this.username = scanner.nextLine();
        System.out.println("Enter Password   : ");
        this.password = scanner.nextLine();
    }

    public Admin isAdmin() {
        if (this.username != null && !this.username.isEmpty()) {
            return this;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return this.username + "," + this.password + "," + this.isApproved;
    }
}
