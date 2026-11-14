public class Admin extends User {

    public Admin() {
        setUserName("admin");
        setPIN("0000");
    }

    @Override
    public String menu() {
		System.out.println("\nAdmin Menu\n");
		System.out.println("0) Exit this menu\n1) Full customer report\n2) Add user\n3) Apply interest to savings accounts\n");
		System.out.print("Please enter a number 0-3: ");
        return CheckingAccount.input.nextLine().trim();
    }

    @Override
    public void start() {
    }

    @Override
    public String getReport() {
        return "Admin: " + getUserName() + ", PIN: " + getPIN();
    }
}
