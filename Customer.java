import java.util.Scanner;

public class Customer extends User {

    private static Scanner input = CheckingAccount.input;

    private CheckingAccount checking;
    private SavingsAccount savings;

    public Customer() {
        this("Alice", "0000");
    }

    public Customer(String userName, String PIN) {
        setUserName(userName);
        setPIN(PIN);
        checking = new CheckingAccount();
        savings = new SavingsAccount();
    }

    public Customer(String userName, String PIN, double checkingBalance, double savingsBalance) {
        this(userName, PIN);
        checking.setBalance(checkingBalance);
        savings.setBalance(savingsBalance);
    }

    public static void main(String[] args) {
        Customer customer = new Customer();
        if (customer.login()) {
            System.out.println("Login Successful");
            customer.start();
        } else {
            System.out.println("Login Failed");
        }
    }

    public SavingsAccount getSavings() {
        return savings;
    }

    @Override
    public String menu() {
        System.out.println("\nCustomer Menu\n");
        System.out.println("0) Exit\n1) Manage Checking Account\n2) Manage Savings Account\n3) Change PIN");
        System.out.print("Please enter a number 0-3: ");
        return input.nextLine().trim();
    }

    @Override
    public void start() {
        boolean keepGoing = true;
        while (keepGoing) {
            String choice = menu();
            switch (choice) {
                case "0" -> keepGoing = false;
                case "1" -> {
                    System.out.println("Checking Account");
                    checking.start();
                }
                case "2" -> {
                    System.out.println("Savings Account");
                    savings.start();
                }
                case "3" -> changePIN();
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public void changePIN() {
        System.out.print("Enter new PIN: ");
        String newPIN = input.nextLine().trim();
        setPIN(newPIN);
        System.out.println("PIN changed.");
    }

    @Override
    public String getReport() {
        return "User: " + getUserName() + ", Checking: " + checking.getBalanceString()
                + ", Savings: " + savings.getBalanceString();
    }
}
