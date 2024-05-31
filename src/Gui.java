import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;

public class Gui extends JFrame {
    private ChatBox chatBox;
    private String username;
    private String csvContent;  // Field to store CSV content


    public Gui() {
        chatBox = new ChatBox(new ArrayList<>());
    }

    public void bootWelcomeScreen() {
        setTitle("AI Study Help Assistant (A.I.S.H.A.)");
        setSize(1000, 600);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/background.jpg"));
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        contentPane.add(panel, BorderLayout.CENTER);

        setVisible(true);

        BackgroundPanel mainPanel = new BackgroundPanel("/img/background.jpg");
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(true);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = new JLabel("Chat met A.I.S.H.A.");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionLabel = new JLabel("Verbeter je leerervaring met Aisha!");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(titleLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(descriptionLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        mainPanel.add(leftPanel, gbc);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.insets = new Insets(10, 10, 10, 10);
        rightGbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel quickStartLabel = new JLabel("Laten we beginnen!");
        quickStartLabel.setFont(new Font("Arial", Font.BOLD, 20));
        rightGbc.gridx = 0;
        rightGbc.gridy = 0;
        rightGbc.gridwidth = 2;
        rightPanel.add(quickStartLabel, rightGbc);

        JLabel usernameLabel = new JLabel("Gebruikersnaam/Email");
        rightGbc.gridx = 0;
        rightGbc.gridy = 1;
        rightGbc.gridwidth = 1;
        rightPanel.add(usernameLabel, rightGbc);

        JTextField usernameField = new JTextField(20);
        rightGbc.gridx = 1;
        rightGbc.gridy = 1;
        rightPanel.add(usernameField, rightGbc);

        JLabel passwordLabel = new JLabel("Wachtwoord");
        rightGbc.gridx = 0;
        rightGbc.gridy = 2;
        rightPanel.add(passwordLabel, rightGbc);

        JPasswordField passwordField = new JPasswordField(20);
        rightGbc.gridx = 1;
        rightGbc.gridy = 2;
        rightPanel.add(passwordField, rightGbc);

        JButton loginButton = new JButton("Inloggen");
        loginButton.setBackground(new Color(52, 152, 219));
        loginButton.setForeground(Color.BLACK);
        rightGbc.gridx = 1;
        rightGbc.gridy = 3;
        rightGbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(loginButton, rightGbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        mainPanel.add(rightPanel, gbc);

        add(mainPanel);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usernameOrEmail = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (authenticate(usernameOrEmail, password, Gui.this)) {
                    String profileUsernameenEmail = usernameOrEmail;
                    bootHomeScreen();
                } else {
                    JOptionPane.showMessageDialog(Gui.this, "Invalid username or password");
                }
            }
        });
    }

    private static boolean authenticate(String usernameOrEmail, String password, Gui gui) {
        CSVReader reader = new CSVReader("accounts.csv");
        Map<String, String[]> accounts = reader.readAccounts();

        for (Map.Entry<String, String[]> entry : accounts.entrySet()) {
            String[] accountInfo = entry.getValue();
            if ((accountInfo[0].equals(usernameOrEmail) || accountInfo[2].equals(usernameOrEmail)) && accountInfo[1].equals(password)) {
                String username = accountInfo[0];
                String email = accountInfo[2];
                gui.setCsvContent("Username: " + username + "\nEmail: " + email);
                return true;
            }
        }
        return false;
    }

    private void setCsvContent(String content) {
        this.csvContent = content;
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
        setSize(1000, 600);
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
            String usernameFormatted = "<b><font face=\"Arial\">" + username + ":</font></b> ";
            appendToChat(chatPane, usernameFormatted + message + "<br>");

            String response = chatBox.generateResponse(message);
            String responseFormatted = "<font color=\"#0080FF\"><b><font face=\"Arial\">Aisha:</font></b></font> ";
            appendToChat(chatPane, responseFormatted + response + "<br>");

            inputField.setText("");
            // SoundPlayer.playSound("src/resources/mp3/msn-sound_1.wav"); // werkt niet
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
