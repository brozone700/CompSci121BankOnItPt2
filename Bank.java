import java.io.*;

public class Bank implements HasMenu {

    private static final String FILE_NAME = "customers.dat";

    private Admin admin;
    private CustomerList customers;

    public Bank() {
        admin = new Admin();
        customers = new CustomerList();
	
	//clears data and adds sample customers
        //this.loadSampleCustomers();
        //this.saveCustomers();

        this.loadCustomers();
        this.start();
        this.saveCustomers();
    }

    public static void main(String[] args) {
        new Bank();
    }

    public void loadSampleCustomers() {
        customers.clear();
        customers.add(new Customer("Alice", "0000", 1000, 1000));
        customers.add(new Customer("Bob", "1111", 0, 0));
        customers.add(new Customer("Cindy", "2222", 0, 0));
    }

    public void loadCustomers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;   // first run: nothing saved yet, keep the empty list
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            customers = (CustomerList) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load customers: " + e.getMessage());
        }
    }

    public void saveCustomers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(customers);
        } catch (IOException e) {
            System.out.println("Could not save customers: " + e.getMessage());
        }
    }
	
    public void fullCustomerReport() {
        System.out.println("Full customer report");
        for (Customer c : customers) {
            System.out.println(c.getReport());
        }
    }

    public void addUser() {
        System.out.println("Add User");
        System.out.print("Name: ");
        String name = CheckingAccount.input.nextLine().trim();
        System.out.print("PIN: ");
        String pin = CheckingAccount.input.nextLine().trim();
        customers.add(new Customer(name, pin));
    }

    public void applyInterest() {
        System.out.println("Apply interest");
        for (Customer c : customers) {
            c.getSavings().calcInterest();
            System.out.println("New balance: " + c.getSavings().getBalanceString());
        }
    }

    public void loginAsCustomer() {
        System.out.println("Customer login");
        System.out.print("User name: ");
        String name = CheckingAccount.input.nextLine().trim();
        System.out.print("PIN: ");
        String pin = CheckingAccount.input.nextLine().trim();

        Customer currentCustomer = null;
        for (Customer c : customers) {
            if (c.login(name, pin)) {
                currentCustomer = c;
                break;
            }
        }

        if (currentCustomer != null) {
            System.out.println("Login Successful");
            currentCustomer.start();
        } else {
            System.out.println("Login failed.");
        }
    }

    @Override
    public String menu() {
    	System.out.println("\nBank Menu\n");
		System.out.println("0) Exit system\n1) Login as admin\n2) Login as customer\n");
		System.out.print("Action: ");
		return CheckingAccount.input.nextLine().trim();
    }

    @Override
    public void start() {
		boolean KeepGoing = true;
		while (KeepGoing) {
			String choice = menu();
			switch (choice) {
				case "0" -> KeepGoing = false;
				case "1" -> {
					System.out.println("\nAdmin login\n");
					if (admin.login()) {
						startAdmin();
					} else {
                    	System.out.println("Login failed.");
                	}
				}
				case "2" -> loginAsCustomer();
				default -> System.out.println("Invalid choice.");
			}
		}
    }

    public void startAdmin() {
		boolean KeepGoing = true;
		while (KeepGoing) { 
			String choice = admin.menu();
			switch (choice) {
				case "0" -> KeepGoing = false;
				case "1" -> fullCustomerReport();
				case "2" -> addUser();
				case "3" -> applyInterest();
				default -> System.out.println("Invalid choice.");
			}
		}
    }
}
