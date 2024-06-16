import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class HomeScreenGui {
    private ChatBox chatBox;
    private JFrame frame;
    private Gui gui;
    private ResourceBundle bundle;
    private CSVReader csvReader;
    private JPanel chatListPanel;
    private String currentChatNumber;
    private CSVWriter csvWriter;
    private Login login;

    public HomeScreenGui(JFrame frame, Gui gui, ResourceBundle bundle, CSVReader csvReader, String currentChatNumber) {
        csvWriter = new CSVWriter("src/main/resources/data/chat's.csv");
        csvReader = new CSVReader("src/main/resources/data/chat's.csv");
        login = new Login("src/main/resources/data/accounts.csv");

        this.frame = frame;
        this.gui = gui;
        this.bundle = bundle;
        this.csvReader = csvReader;
        this.currentChatNumber = currentChatNumber;
        chatBox = new ChatBox(new ArrayList<>());

    }

    public void display() {
        setupFrame();
        JPanel contentPane = createContentPane();
        JTextPane chatPane = createChatPane();
        JPanel inputPanel = createInputPanel(chatPane);
        JPanel navbar = gui.createNavbar();

        contentPane.add(new JScrollPane(chatPane), BorderLayout.CENTER);
        contentPane.add(inputPanel, BorderLayout.SOUTH);
        contentPane.add(navbar, BorderLayout.NORTH);

        chatListPanel = createChatListPanel();
        contentPane.add(chatListPanel, BorderLayout.WEST);

        frame.setContentPane(contentPane);
        frame.setVisible(true);

        populateChatPane(chatPane);
        populateChatListPanel();
    }

    private void setupFrame() {
        frame.setTitle(bundle.getString("home.chat"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
    }

    private JPanel createContentPane() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.WHITE);
        return contentPane;
    }

    private JTextPane createChatPane() {
        JTextPane chatPane = new JTextPane();
        chatPane.setContentType("text/html");
        chatPane.setEditable(false);
        chatPane.setFont(new Font("Arial", Font.PLAIN, 14));
        return chatPane;
    }

    private JPanel createInputPanel(JTextPane chatPane) {
        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField inputField = new JTextField();
        JButton sendButton = new JButton(bundle.getString("home.send"));
        gui.styleButton(sendButton);

        sendButton.addActionListener(e -> sendMessage(inputField, chatPane));
        inputField.addActionListener(e -> sendMessage(inputField, chatPane));

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        return inputPanel;
    }

    private JPanel createChatListPanel() {
        JPanel chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));
        chatListPanel.setBackground(new Color(240, 240, 240));
        return chatListPanel;
    }

    private void populateChatPane(JTextPane chatPane) {
        List<String[]> chatMessages = csvReader.readChatMessages(currentChatNumber);
        for (String[] message : chatMessages) {
            appendMessageToChat(chatPane, message);
        }
    }

    private void appendMessageToChat(JTextPane chatPane, String[] message) {
        String sender = message[0];
        String content = message[1];
        String timestamp = message[3];

        LocalDateTime dateTime = LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String formattedTimestamp = dateTime.format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"));

            String nameColor = sender.equals("Aisha") ? "#0080FF" : "black";
            String formattedMessage = "<b><font face=\"Arial\" color=\"" + nameColor + "\">" + sender + ":</font></b> " +
                    "<font face=\"Arial\">" + content + "  </font>" +
                    "<font color=\"#808080\" size=\"-2\">" + formattedTimestamp + "</font><br>";

        appendToChat(chatPane, formattedMessage);
    }

    public void populateChatListPanel() {
        chatListPanel.removeAll();
        List<String> chatNames = List.of("Chat 1", "Chat 2", "Chat 3");
        for (String chatName : chatNames) {
            addButtonToChatListPanel(chatName);
        }
        chatListPanel.revalidate();
        chatListPanel.repaint();
    }

    private void addButtonToChatListPanel(String chatName) {
        JButton chatButton = new JButton(chatName);
        gui.styleChatButton(chatButton);
        chatButton.addActionListener(e -> switchChat(chatName));
        chatButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, chatButton.getPreferredSize().height));
        chatListPanel.add(chatButton);
    }

    private void switchChat(String chatName) {
        currentChatNumber = chatName.substring(5);
        display();
    }

    private void sendMessage(JTextField inputField, JTextPane chatPane) {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            LocalDateTime timestamp = LocalDateTime.now();
            csvWriter.logChatMessage(gui.username, message, currentChatNumber, timestamp);

            String usernameFormatted = "<b><font face=\"Arial\">" + gui.username + ":</font></b> ";
            appendToChat(chatPane, usernameFormatted + "<font face=\"Arial\">" + message + "</font><br>");

            String response = chatBox.generateResponse(message);
            String responseFormatted = "<font color=\"#0080FF\"><b><font face=\"Arial\">Aisha:</font></b></font> ";
            appendToChat(chatPane, responseFormatted + "<font face=\"Arial\">" + response + "</font><br>");

            csvWriter.logChatMessage("Aisha", response, currentChatNumber, LocalDateTime.now());
            inputField.setText("");
        }
    }

    private void appendToChat(JTextPane chatPane, String message) {
        HTMLDocument doc = (HTMLDocument) chatPane.getDocument();
        HTMLEditorKit editorKit = (HTMLEditorKit) chatPane.getEditorKit();
        try {
            editorKit.insertHTML(doc, doc.getLength(), message, 0, 0, null);
            chatPane.setCaretPosition(doc.getLength());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
