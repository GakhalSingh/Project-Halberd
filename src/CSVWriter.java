import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    private String bestandsnaam;

    public CSVWriter(String bestandsnaam) {
        this.bestandsnaam = bestandsnaam;
    }

    public void CSVAccountadder(String gebruikersnaam, String email, String wachtwoord) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.bestandsnaam, true))) {
            writer.write(gebruikersnaam + "," + email+ "," + wachtwoord + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
