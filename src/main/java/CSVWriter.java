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
        try (FileWriter writer = new FileWriter("src/main/resources/data/accounts.csv", true)) {
            writer.append(userName).append(",").append(wachtwoord).append(",").append(email).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
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
