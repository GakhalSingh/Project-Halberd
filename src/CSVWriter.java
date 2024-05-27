import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    private String bestandsnaam;

    public CSVWriter(String bestandsnaam) {
        this.bestandsnaam = bestandsnaam;
    }

    public void CSVAccountadder(String userName, String email, String wachtwoord) {
        try (FileWriter writer = new FileWriter(bestandsnaam, true)) {
            writer.append(userName).append(",").append(wachtwoord).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
