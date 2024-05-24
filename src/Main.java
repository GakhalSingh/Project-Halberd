import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;

public class Main extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Gui gui = new Gui();
                gui.guiStartScherm();
                new Main();
            }
        });
        try{
            ChatMessage msg1 = new ChatMessage("Alice", "Hello!");
            ChatMessage msg2 = new ChatMessage("Bob", "Hi Alice!");
        ChatBox chatBox = new ChatBox(Arrays.asList(msg1, msg2));

        chatBox.writeToCsvFile("chatbox.csv");

        ChatBox loadedChatBox = ChatBox.readFromCsvFile("chatbox.csv");

        for (ChatMessage message : loadedChatBox.getMessages()) {
            System.out.println(message.getSender() + ": " + message.getMessage());
        }
    } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

