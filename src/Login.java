import java.util.Map;
import java.util.Scanner;

public class Login extends Account {
    private Scanner scanner;
    private CSVWriter csvWriter;
    private CSVReader csvReader;

    public Login(String userName, String email, String wachtWoord, String csvFilePath) {
        super(userName, email, wachtWoord);
        this.scanner = new Scanner(System.in);
        this.csvWriter = new CSVWriter(csvFilePath);
        this.csvReader = new CSVReader(csvFilePath);
    }

    public void loginScreen() {
        System.out.print("Gebruikersnaam of e-mail: ");
        String gebruikersnaamOfEmail = scanner.nextLine();
        System.out.print("Wachtwoord: ");
        String wachtwoord = scanner.nextLine();

        Account account = new Account(gebruikersnaamOfEmail, gebruikersnaamOfEmail.contains("@") ? gebruikersnaamOfEmail : null, wachtwoord);

        if (isValidUser(account)) {
            System.out.println("Login succesvol!");
        } else {
            System.out.println("Sorry, uw wachtwoord/gebruikersnaam is verkeerd.");
        }
    }

    private boolean isValidUser(Account account) {
        Map<String, String> accounts = csvReader.readAccounts();

        // Check if the input is an email or username and validate accordingly
        if (account.getEmail() != null) {
            return accounts.containsKey(account.getEmail()) && accounts.get(account.getEmail()).equals(account.getWachtwoord());
        } else {
            return accounts.containsKey(account.getUserName()) && accounts.get(account.getUserName()).equals(account.getWachtwoord());
        }
    }

    public void nieuwAccount() {
        System.out.println("Maak een gebruikersnaam");
        String nieuweNaam = scanner.nextLine();
        System.out.println("wat is je email");
        String email = scanner.nextLine();
        System.out.println("Je nieuwe wachtwoord");
        String nieuwWachtwoord = scanner.nextLine();
        System.out.println("Herhaal het wachtwoord");
        String nieuwWachtwoord2 = scanner.nextLine();

        if (nieuwWachtwoord.equals(nieuwWachtwoord2)) {
            csvWriter.CSVAccountadder(nieuweNaam, email, nieuwWachtwoord);
            System.out.println("Account aangemaakt voor: " + nieuweNaam);
        } else {
            System.out.println("Wachtwoorden komen niet overeen, probeer opnieuw.");
            nieuwAccount();
        }
    }
}
