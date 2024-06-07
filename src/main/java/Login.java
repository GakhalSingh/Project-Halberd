import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;

public class Login extends Account {
    private Scanner scanner;
    private static CSVWriter csvWriter = new CSVWriter("resources\\data\\accounts.csv");
    private static CSVReader csvReader = new CSVReader("resources\\data\\accounts.csv");

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
            System.out.println("Sorry, uw wachtwoord/gebruikersnaam combinatie is onjuist.");
        }
    }

    private boolean isValidUser(Account account) {
        Map<String, String[]> accounts = csvReader.readAccounts();

        // Check if the input is an email or username and validate accordingly
        if (account.getEmail() != null) {
            return accounts.containsKey(account.getEmail()) && accounts.get(account.getEmail()).equals(account.getWachtwoord());
        } else {
            return accounts.containsKey(account.getUserName()) && accounts.get(account.getUserName()).equals(account.getWachtwoord());
        }
    }

        public static String nieuwAccount(String username, String email, String password, String confirmPassword){


            Map<String, String[]> accounts = csvReader.readAccounts();

            if (accounts.containsKey(username) || accounts.containsKey(email)) {
                return "Gebruikersnaam of email bestaat al, probeer een andere.";
            } else if (!password.equals(confirmPassword)) {
                return "Wachtwoorden komen niet overeen, probeer opnieuw.";
            } else {
                csvWriter.CSVAccountadder(username, email, password);
                return "Account aangemaakt voor: " + username;
            }
        }
    }

