import java.util.Map;
import java.util.Scanner;

public class Login extends Account {
    private Scanner scanner;
    private CSVWriter csvWriter;
    private CSVReader csvReader;

    public Login(String userName, String wachtWoord, String csvFilePath) {
        super(userName, wachtWoord);
        this.scanner = new Scanner(System.in);
        this.csvWriter = new CSVWriter(csvFilePath);
        this.csvReader = new CSVReader(csvFilePath);
    }

    public void loginScreen() {
        String gebruikersnaam = scanner.nextLine();
        String wachtwoord = scanner.nextLine();

        Account account = new Account(gebruikersnaam, wachtwoord);

        if (isValidUser(account)) {
            System.out.println("Login succesvol!");
        } else {
            System.out.println("Sorry, uw wachtwoord/gebruikersnaam is verkeerd.");
        }
    }

    private boolean isValidUser(Account account) {
        Map<String, String> accounts = csvReader.readAccounts();
        return accounts.containsKey(account.getUserName()) && accounts.get(account.getUserName()).equals(account.getWachtwoord());
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
