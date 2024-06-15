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
    private String username;
    private String email;
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

    private void loadResourceBundle(String languageCode) {

        Locale locale = new Locale(languageCode);
        bundle = ResourceBundle.getBundle("messages", locale);

    }

    public void display() {
        frame.setTitle(bundle.getString("home.chat"));
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.WHITE);

        JTextPane chatPane = new JTextPane();
        chatPane.setContentType("text/html");
        chatPane.setEditable(false);
        chatPane.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(chatPane);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField inputField = new JTextField();
        JButton sendButton = new JButton(bundle.getString("home.send"));
        gui.styleButton(sendButton);

        sendButton.addActionListener(e -> sendMessage(inputField, chatPane));
        inputField.addActionListener(e -> sendMessage(inputField, chatPane));

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        contentPane.add(inputPanel, BorderLayout.SOUTH);

        JPanel navbar = createNavbar();
        contentPane.add(navbar, BorderLayout.NORTH);

        chatListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chatListPanel.setBackground(new Color(240, 240, 240));
        contentPane.add(chatListPanel, BorderLayout.WEST);

        populateChatListPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(contentPane);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);

        List<String[]> chatMessages = csvReader.readChatMessages(currentChatNumber);
        for (String[] message : chatMessages) {
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
    }

    private JPanel createNavbar() {
        JPanel navbar = new JPanel(new GridLayout(1, 4));
        navbar.setBackground(new Color(52, 152, 219));

        JButton chatsButton = new JButton(bundle.getString("home.chats"));
        JButton profileButton = new JButton(bundle.getString("home.profile"));
        JButton infoButton = new JButton(bundle.getString("home.info"));
        JButton logoutButton = new JButton(bundle.getString("home.logout"));

        gui.styleButton(chatsButton);
        gui.styleButton(profileButton);
        gui.styleButton(infoButton);
        gui.styleLogoutButton(logoutButton);

        chatsButton.addActionListener(e -> display());
        profileButton.addActionListener(e -> gui.showProfileScreen());
        infoButton.addActionListener(e -> gui.showInfoScreen());
        logoutButton.addActionListener(e -> gui.bootWelcomeScreen());

        navbar.add(chatsButton);
        navbar.add(profileButton);
        navbar.add(infoButton);
        navbar.add(logoutButton);

        return navbar;
    }

    public void populateChatListPanel() {
        chatListPanel.removeAll();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));

        // Simulated chat list; replace with actual logic to fetch user-specific chats
        List<String> chatNames = List.of("Chat 1", "Chat 2", "Chat 3");

        for (String chatName : chatNames) {
            JButton chatButton = new JButton(chatName);
            gui.styleChatButton(chatButton);
            chatButton.addActionListener(e -> {
                currentChatNumber = chatName.substring(5);
                display();
                String chatIdentifier = chatName;  // Adjust this to get the chat identifier
                displayChatHistory(chatIdentifier);  // Method to display chat history
            });
            chatButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, chatButton.getPreferredSize().height));
            chatListPanel.add(chatButton); // Add button to the panel
        }
        chatListPanel.revalidate();
        chatListPanel.repaint();
    }

    private void displayChatHistory(String chatIdentifier) {
        JTextPane chatPane = new JTextPane();
        chatPane.setContentType("text/html");
        chatPane.setEditable(false);
        chatPane.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(chatPane);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        List<String[]> chatMessages = csvReader.readChatMessages(chatIdentifier);

        for (String[] message : chatMessages) {
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
    }

    private void sendMessage(JTextField inputField, JTextPane chatPane) {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            LocalDateTime timestamp = LocalDateTime.now();
            csvWriter.logChatMessage(username, message, currentChatNumber, timestamp);

            String usernameFormatted = "<b><font face=\"Arial\">" + username + ":</font></b> ";
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
