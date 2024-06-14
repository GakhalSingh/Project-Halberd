import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVWriter {
    private String bestandsnaam;

    public CSVWriter(String bestandsnaam) {
        this.bestandsnaam = bestandsnaam;
    }

    public void CSVAccountadder(String userName, String email, String wachtwoord) {
        try (FileWriter writer = new FileWriter(bestandsnaam, true)) {
            writer.append(userName).append(",").append(wachtwoord).append(",").append(email).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CSVAccountWijzige(String userName, String newEmail, String newWachtwoord) {
        File inputFile = new File(bestandsnaam);
        File tempFile = new File("temp_" + bestandsnaam);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            boolean accountFound = false;

            while ((currentLine = reader.readLine()) != null) {
                String[] accountData = currentLine.split(",");
                if (accountData[0].equals(userName)) {
                    accountData[1] = newWachtwoord;
                    accountData[2] = newEmail;
                    accountFound = true;
                }
                writer.write(String.join(",", accountData));
                writer.newLine();
            }

            if (!accountFound) {
                System.out.println("Account met gebruikersnaam " + userName + " niet gevonden.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Vervang het originele bestand met het tijdelijke bestand
        if (!inputFile.delete()) {
            System.out.println("Kon het originele bestand niet verwijderen.");
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Kon het tijdelijke bestand niet hernoemen.");
        }
    }

    public void logChatMessage(String sender, String message, String currentChatNumber, LocalDateTime timestamp) {
        try (FileWriter writer = new FileWriter(bestandsnaam, true)) {
            String formattedTimestamp = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.append(sender).append(";").append(message).append(";").append(currentChatNumber).append(";").append(formattedTimestamp).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
