import java.util.Scanner;

public class Login extends Account {
    private Scanner scanner;
    private CSVWriter csvWriter;

    public Login(String userName, String wachtWoord) {
        super(userName, wachtWoord);
        this.scanner = new Scanner(System.in);
        this.csvWriter = new CSVWriter("C:\\Users\\brian\\IdeaProjects\\Project-Halberd\\accounts.csv");
    }

    public void loginScreen() {
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
        return account.getUserName().equals("brian") && account.getWachtwoord().equals("hot");
    }

    public void nieuwAccount() {
        System.out.println("Maak een gebruikersnaam");
        String nieuweNaam = scanner.nextLine();
        System.out.println("Je nieuwe wachtwoord");
        String nieuwWachtwoord = scanner.nextLine();
        System.out.println("Herhaal het wachtwoord");
        String nieuwWachtwoord2 = scanner.nextLine();

        if (nieuwWachtwoord.equals(nieuwWachtwoord2)) {
            csvWriter.CSVAccountadder(nieuweNaam, nieuwWachtwoord);
            System.out.println("Account aangemaakt voor: " + nieuweNaam);
        } else {
            System.out.println("Wachtwoorden komen niet overeen, probeer opnieuw.");
            nieuwAccount();
        }
    }
}
