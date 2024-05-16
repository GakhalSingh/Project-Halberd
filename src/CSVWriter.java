import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {
    private String bestandsnaam;

    public CSVWriter(String bestandsnaam) {
        this.bestandsnaam = bestandsnaam;
    }

    public void CSVAccountadder(String gebruikersnaam, String wachtwoord) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.bestandsnaam, true))) {
            writer.write(gebruikersnaam + "," + wachtwoord + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
