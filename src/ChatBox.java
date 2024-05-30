import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatBox {
    private List<ChatMessage> messages;

    public ChatBox(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public static ChatBox readFromCsvFile(String filePath) throws IOException {
        List<ChatMessage> messages = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length == 2) {
                    messages.add(new ChatMessage(fields[0], fields[1]));
                }
            }
        }
        return new ChatBox(messages);
    }

    public String generateResponse(String message) {
        Map<String, List<String>> data = readDataFromCSV("src/resources/data/data.csv");
        StringBuilder chatMessage = new StringBuilder();
        for (String keyword : data.keySet()) {
            if (message.toLowerCase().contains(keyword.toLowerCase())) {
                List<String> responses = data.get(keyword);
                for (String resp : responses) {
                    chatMessage.append(resp).append("\n");
                }
            }
        }
        if (chatMessage.length() == 0) {
            return "Ik begreep je niet, kun je het in andere woorden vragen? 😓";
        } else {
            return chatMessage.toString();
        }
    }

    private Map<String, List<String>> readDataFromCSV(String filePath) {
        Map<String, List<String>> dataMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values.length == 2) {
                    String key = values[0].trim();
                    String value = values[1].trim();
                    if (!dataMap.containsKey(key)) {
                        dataMap.put(key, new ArrayList<>());
                    }
                    dataMap.get(key).add(value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }
}
