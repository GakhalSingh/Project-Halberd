import java.io.*;
import java.util.*;

public class CSVReader {
    private String filePath;

    public CSVReader(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, String[]> readAccounts() {
        Map<String, String[]> accounts = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File("src/main/resources/data/accounts.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    accounts.put(values[0].trim(), new String[]{values[0].trim(), values[1].trim(), values[2].trim()});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public List<String[]> readChatMessages(String chatIdentifier) {
        List<String[]> chatMessages = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 4 && data[2].equals(chatIdentifier)) { // Filter by chat identifier
                    chatMessages.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chatMessages;
    }
}
