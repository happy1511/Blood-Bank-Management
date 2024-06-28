package BloodBank;

import java.util.Scanner;

public class Blood {
    public String type;
    public int quantity;

    public void takeInput(Scanner scanner) {
        System.out.println("Enter the blood type: ");
        this.type = scanner.nextLine();
        System.out.println("Enter the quantity: ");
        this.quantity = scanner.nextInt();
    }

    public void setDetails(String type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public void addBlood(int quantity) {
        this.quantity += quantity;
    }

    public void removeBlood(int quantity) {
        this.quantity -= quantity;
    }

    @Override
    public String toString() {
        return type + "," + quantity;
    }

}
