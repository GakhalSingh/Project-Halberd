import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Gui extends JFrame {
    private ChatBox chatBox;
    private String username;

    public Gui() {
        chatBox = new ChatBox(new ArrayList<>());
    }

    public void bootWelcomeScreen() {
        setTitle("AI Study Help Assistant (A.I.S.H.A.)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(240, 240, 240));

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(255, 255, 255));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 20, 0);

        JLabel titleLabel = new JLabel("Welkom bij A.I.S.H.A.");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        loginPanel.add(titleLabel, gbc);

        gbc.gridy++;
        JLabel usernameLabel = new JLabel("Gebruikersnaam/Email:");
        loginPanel.add(usernameLabel, gbc);

        gbc.gridy++;
        JTextField usernameField = new JTextField(20);
        loginPanel.add(usernameField, gbc);

        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Wachtwoord:");
        loginPanel.add(passwordLabel, gbc);

        gbc.gridy++;
        JPasswordField passwordField = new JPasswordField(20);
        loginPanel.add(passwordField, gbc);

        gbc.gridy++;
        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (authenticate(username, password)) {
                    Gui.this.username = username;
                    bootHomeScreen();
                } else {
                    JOptionPane.showMessageDialog(Gui.this, "Ongeldige gebruikersnaam of wachtwoord");
                }
            }
        });
        loginPanel.add(loginButton, gbc);

        contentPane.add(loginPanel, BorderLayout.CENTER);
        setContentPane(contentPane);
        setVisible(true);
    }

    private boolean authenticate(String username, String password) {
        return true; // Gaan we dit doen?
    }

    public void bootHomeScreen() {
        setTitle("Chat met A.I.S.H.A.");
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.WHITE);

        JTextPane chatPane = new JTextPane();
        chatPane.setContentType("text/html");
        chatPane.setEditable(false);
        chatPane.setFont(new Font("Arial", Font.PLAIN, 14)); // beste lettertypo imo

        JScrollPane scrollPane = new JScrollPane(chatPane);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField inputField = new JTextField();
        JButton sendButton = new JButton("Verzenden");
        styleButton(sendButton);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage(inputField, chatPane);
            }
        });

        inputField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage(inputField, chatPane);
                }
            }
        });

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        contentPane.add(inputPanel, BorderLayout.SOUTH);

        JPanel navbar = createNavbar();
        contentPane.add(navbar, BorderLayout.NORTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setVisible(true);
    }

    private JPanel createNavbar() {
        JPanel navbar = new JPanel(new GridLayout(1, 4));
        navbar.setBackground(new Color(52, 152, 219));

        JButton chatsButton = new JButton("Chats");
        JButton profileButton = new JButton("Profiel");
        JButton infoButton = new JButton("Info");
        JButton logoutButton = new JButton("Logout");

        styleButton(chatsButton);
        styleButton(profileButton);
        styleButton(infoButton);
        styleButton(logoutButton);
        styleLogoutButton(logoutButton);

        chatsButton.addActionListener(e -> showChatsScreen());
        profileButton.addActionListener(e -> showProfileScreen());
        infoButton.addActionListener(e -> showInfoScreen());
        logoutButton.addActionListener(e -> bootWelcomeScreen());

        navbar.add(chatsButton);
        navbar.add(profileButton);
        navbar.add(infoButton);
        navbar.add(logoutButton);


        return navbar;
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void styleLogoutButton(JButton button) {
        button.setBackground(new Color(231, 76, 60));
    }

    private void showChatsScreen() {
        // eerst moeten we alle chats apart weten op te slaan - jin (ik werk hieraan)
    }

    private void showProfileScreen() {
        // we moeten bij de gebruiker classe nog de optie email toevoegen
    }

    private void showInfoScreen() {
        setTitle("Over A.I.S.H.A.");
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.WHITE);

        JTextArea infoText = new JTextArea();
        infoText.setEditable(false);
        infoText.setFont(new Font("Arial", Font.PLAIN, 14));
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        infoText.setText("A.I.S.H.A. (AI Study Help Assistant) is een virtuele assistent ontworpen om studenten te helpen bij hun studie. "
                + "Deze chatbot kan vragen beantwoorden, uitleg geven over verschillende onderwerpen en interactief leren stimuleren.\n\n"
                + "Ontwikkeld door:\n"
                + "- [Jin]\n"
                + "- (Li)\n"
                + "- |Joris|)\n"
                + "- {Brian}\n");

        JScrollPane scrollPane = new JScrollPane(infoText);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel navbar = createNavbar();
        contentPane.add(navbar, BorderLayout.NORTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setVisible(true);
    }


    private void sendMessage(JTextField inputField, JTextPane chatPane) {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            appendToChat(chatPane, "<b>" + username + ":</b> " + message + "<br>");
            String response = chatBox.generateResponse(message);
            appendToChat(chatPane, "<font color=\"#0080FF\"><b>Aisha:</b></font> " + response + "<br>");
            inputField.setText("");
            // SoundPlayer.playSound("src/resources/mp3/msn-sound_1.wav"); // Geluid afspelen, indien gewenst
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui().bootWelcomeScreen());
    }
}
