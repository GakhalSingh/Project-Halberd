import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;

public class Login {
    private static CSVWriter csvWriter = new CSVWriter("src\\main\\resources\\data\\accounts.csv");
    private static CSVReader csvReader = new CSVReader("src\\main\\resources\\data\\accounts.csv");






    public Login(String csvFilePath) {
        this.csvWriter = new CSVWriter(csvFilePath);
        this.csvReader = new CSVReader(csvFilePath);

    }

    public boolean authenticate(String usernameOrEmail, String password) {
        CSVReader reader = new CSVReader("data\\accounts.csv");

        Map<String, String[]> accounts = reader.readAccounts();

        for (Map.Entry<String, String[]> entry : accounts.entrySet()) {
            String[] accountInfo = entry.getValue();
            if ((accountInfo[0].equals(usernameOrEmail) || accountInfo[2].equals(usernameOrEmail)) && accountInfo[1].equals(password)) {
                return true;
            }
        }
        return false;
    }


    public String getUsername(String usernameOrEmail) {
        Map<String, String[]> accounts = csvReader.readAccounts();

        for (Map.Entry<String, String[]> entry : accounts.entrySet()) {
            String[] accountInfo = entry.getValue();
            if (accountInfo[0].equals(usernameOrEmail) || accountInfo[2].equals(usernameOrEmail)) {
                return accountInfo[0]; // Assuming the first field is the username
            }
        }
        return null;
    }
    public static String nieuwAccount(String username, String email, String password, String confirmPassword) {
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
