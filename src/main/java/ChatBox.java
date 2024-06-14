import halberd.ai.AI;
import halberd.ai.OllamaModel;
import halberd.ai.OnlinellamaModel;
import halberd.sound.audio;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class ChatBox extends Observable {
    private List<ChatMessage> messages;
    private final AI model;
    private final audio notificationSound;


    private static AI aiFactory() {
        boolean netAccess = false;
        try {
            Socket socket = new Socket("www.google.com", 80);
            netAccess = socket.isConnected();
            socket.close();
        } catch (IOException ignore) {

        }
        if (netAccess) {
            return new OnlinellamaModel();
        } else {
            return new OllamaModel();
        }
    }

    public ChatBox(List<ChatMessage> messages) {
        this.messages = messages;
        this.model = aiFactory();
        this.notificationSound = new audio();
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
        setChanged();
        notifyObservers(message);
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
        String response = model.chat(message);
        notificationSound.playSound();
        return response;
    }

    private Map<String, List<String>> readDataFromCSV(String filePath) {
        Map<String, List<String>> dataMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(filePath)))) {
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
