import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ChatBox {
    private List<ChatMessage> messages;

    public ChatBox(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public List<ChatMessage> getMessages() { return messages; }
    public void setMessages(List<ChatMessage> messages) { this.messages = messages; }

    public static ChatBox readFromCsvFile(String filePath) throws IOException {
        List<ChatMessage> messages = new ArrayList<>();
        CSVReader csvReader = new CSVReader(filePath);
        List<String[]> lines = csvReader.readAll();

        for (String[] fields : lines) {
            String sender = fields[0];
            String message = fields[1];
            messages.add(new ChatMessage(sender, message));
        }

        return new ChatBox(messages);
    }

    public void writeToCsvFile(String filePath) throws IOException {
        CSVWriter csvWriter = new CSVWriter(filePath);
        List<String[]> data = new ArrayList<>();

        for (ChatMessage msg : this.messages) {
            data.add(new String[]{msg.getSender(), msg.getMessage()});
        }

        csvWriter.writeAll(data);
    }

    public String generateResponse(String message) {
        Map<String, String> data = readDataFromCSV("src/resources/data/data.csv");
        for (String keyword : data.keySet()) {
            if (message.toLowerCase().contains(keyword.toLowerCase())) {
                return data.get(keyword);
            }
        }
        return "Geen relevante informatie gevonden.";
    }

    private Map<String, String> readDataFromCSV(String filePath) {
        Map<String, String> dataMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    dataMap.put(values[0].trim(), values[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }
}
