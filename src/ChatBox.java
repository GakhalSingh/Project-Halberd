import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChatBox {
    private List<ChatMessage> messages;

    public ChatBox(List<ChatMessage> messages) {
        this.messages = messages;
    }
    public List<ChatMessage> getMessages() { return messages; }
    public void setMessages(List<ChatMessage> messages) { this.messages = messages; }


    public static ChatBox readFromCsvFile(String filePath) throws IOException {
        List<ChatMessage> messages = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            String sender = fields[0];
            String message = fields[1];
            messages.add(new ChatMessage(sender, message));
        }
        reader.close();
        return new ChatBox(messages);
    }

    public void writeToCsvFile(String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

        for (ChatMessage msg : this.messages) {
            writer.write(msg.getSender() + "," + msg.getMessage());
            writer.newLine();
        }
        writer.close();
    }
}