import java.util.Scanner;

public class Display extends Account {
    private Scanner scanner;

    public Display(String userName, String wachtWoord, Scanner scanner) {
        super(userName, wachtWoord);
        this.scanner = scanner;
    }

    public Display() {
        super("", "");
        this.scanner = new Scanner(System.in);
    }

    public void displayScreen() {
        System.out.println("Gebruikersnaam?");
        String gebruikersnaam = scanner.nextLine();
        System.out.println("Wachtwoord?");
        String wachtwoord = scanner.nextLine();

        Account account = new Account(gebruikersnaam, wachtwoord);

        if (isValidUser(account)) {
            System.out.println("Welkom, " + account.getUserName() + "!");
        } else {
            System.out.println("Sorry, uw wachtwoord/gebruikersnaam is verkeerd.");
        }
    }

    private boolean isValidUser(Account account) {
        return account.getUserName().equals("brian") && account.getWachtWoord().equals("hot");
    }
}
