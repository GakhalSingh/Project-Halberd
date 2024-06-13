import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class Gui extends JFrame implements Observer {
    private ChatBox chatBox;
    private String username;
    private String email;
    private JPanel chatListPanel;
    private CSVWriter csvWriter;
    private CSVReader csvReader;
    private Login login;
    private String currentChatNumber;

    public Gui() {
        chatBox = new ChatBox(new ArrayList<>());
        chatBox.addObserver(this);
        login = new Login("data\\accounts.csv");
        csvWriter = new CSVWriter("src/main/resources/data/chat's.csv");
        csvReader = new CSVReader("src/main/resources/data/chat's.csv");

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
                ImageIcon icon = new ImageIcon(getClass().getResource("\\img\\background.jpg"));
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
        loginButton.setForeground(Color.WHITE);
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

        loginButton.addActionListener(e -> {
            String usernameOrEmail = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (login.authenticate(usernameOrEmail, password)) {
                this.username = usernameOrEmail;
                this.email = getEmailByUsernameOrEmail(usernameOrEmail); // Set email based on the authenticated user
                bootHomeScreen();
            } else {
                JOptionPane.showMessageDialog(Gui.this, "Invalid username or password");
            }
        });

        JButton nieuwAccountButton = new JButton("Nieuw account");
        nieuwAccountButton.setBackground(new Color(52, 152, 219));
        nieuwAccountButton.setForeground(Color.WHITE);
        rightGbc.gridx = 1;
        rightGbc.gridy = 4;
        rightGbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(nieuwAccountButton, rightGbc);

        nieuwAccountButton.addActionListener(e -> bootNewAccountScreen());
        revalidate();
        repaint();
    }

    private String getEmailByUsernameOrEmail(String usernameOrEmail) {
        CSVReader reader = new CSVReader("src\\main\\resources\\data\\accounts.csv");
        Map<String, String[]> accounts = reader.readAccounts();

        for (String[] accountInfo : accounts.values()) {
            if (accountInfo[0].equals(usernameOrEmail) || accountInfo[2].equals(usernameOrEmail)) {
                return accountInfo[2];
            }
        }
        return null;
    }

    public void bootHomeScreen() {
        setTitle("Chat met A.I.S.H.A.");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        JButton sendButton = new JButton("Verzenden");
        styleButton(sendButton);

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

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        revalidate();
        repaint();
        setVisible(true);


        List<String[]> chatMessages = csvReader.readChatMessages(currentChatNumber);
        for (String[] message : chatMessages) {
            String sender = message[0];
            String content = message[1];
            String chatNumber = message[2];
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

        String[] buttonLabels = {"Chats", "Profiel", "Info", "Logout"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            styleButton(button);

            switch (label) {
                case "Chats":
                    button.addActionListener(e -> bootHomeScreen());
                    break;
                case "Profiel":
                    button.addActionListener(e -> showProfileScreen());
                    break;
                case "Info":
                    button.addActionListener(e -> showInfoScreen());
                    break;
                case "Logout":
                    styleLogoutButton(button);
                    button.addActionListener(e -> bootWelcomeScreen());
                    break;
                default:
                    break;
            }

            navbar.add(button);
        }

        return navbar;
    }

    private void populateChatListPanel() {
        chatListPanel.removeAll();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));

        // Simulated chat list; replace with actual logic to fetch user-specific chats
        List<String> chatNames = new ArrayList<>();
        chatNames.add("Chat 1");
        chatNames.add("Chat 2");
        chatNames.add("Chat 3");

        for (String chatName : chatNames) {

            JButton chatButton = new JButton(chatName);
            styleButton(chatButton);
            chatButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    currentChatNumber = chatName.substring(5);
                    System.out.println(currentChatNumber);
                    bootHomeScreen();
                }
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
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Read chat history based on chatIdentifier from CSV
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


    private void styleChatButton(JButton button) {
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void toggleChatListPanel() {
        chatListPanel.setVisible(!chatListPanel.isVisible());
        if (chatListPanel.isVisible()) {
            populateChatListPanel();
        }

    }
    private void showProfileScreen() {
        setTitle("Over " + username);
        JPanel contentPane = new JPanel(new BorderLayout());
        JTextArea infoText = new JTextArea();
        infoText.setEditable(false);
        infoText.setFont(new Font("Arial", Font.PLAIN, 14));
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        infoText.setText("Hallo " + username + "\n\n"
                + "hier heb je wat informatie over je zelf XD: \n"
                + username + "\n"
                + email + "\n\n"
                + "Informatie wijzigen? ");

        JScrollPane scrollPane = new JScrollPane(infoText);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel navbar = createNavbar();
        contentPane.add(navbar, BorderLayout.NORTH);

        JButton modifyButton = new JButton("Wijzig");
        styleButton(modifyButton);
        modifyButton.addActionListener(e -> showModifyDialog());
        contentPane.add(modifyButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setVisible(true);
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
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setVisible(true);
    }
    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void styleLogoutButton(JButton button) {
        button.setBackground(new Color(222, 102, 90));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void showModifyDialog() {
        JTextField newUsernameField = new JTextField(username, 20);
        JPasswordField newPasswordField = new JPasswordField(20);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Nieuwe gebruikersnaam:"));
        panel.add(newUsernameField);
        panel.add(new JLabel("Nieuw wachtwoord:"));
        panel.add(newPasswordField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Wijzig gebruikersinformatie", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String newUsername = newUsernameField.getText().trim();
            String newPassword = new String(newPasswordField.getPassword()).trim();

            if (newUsername.isEmpty() && newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Gebruikersnaam en wachtwoord mogen niet allebei leeg zijn");
                return;
            }

            if (!newUsername.isEmpty()) {
                if (!isUsernameAvailable(newUsername)) {
                    JOptionPane.showMessageDialog(this, "Gebruikersnaam bestaat al, kies een andere.");
                    return;
                }
                if (!updateAccountInfo(username, newUsername, newPassword.isEmpty() ? null : newPassword)) {
                    JOptionPane.showMessageDialog(this, "Fout bij het bijwerken van de gebruikersinformatie");
                    return;
                }
                username = newUsername;
            } else {
                if (!updateAccountInfo(username, null, newPassword)) {
                    JOptionPane.showMessageDialog(this, "Fout bij het bijwerken");
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Gebruikersinformatie bijgewerkt");
            showProfileScreen();
        }
    }

    private boolean isUsernameAvailable(String newUsername) {
        CSVReader reader = new CSVReader("data/accounts.csv");
        Map<String, String[]> accounts = reader.readAccounts();
        return !accounts.containsKey(newUsername);
    }

    private boolean updateAccountInfo(String oldUsername, String newUsername, String newPassword) {
        CSVReader reader = new CSVReader("data/accounts.csv");
        Map<String, String[]> accounts = reader.readAccounts();

        if (accounts.containsKey(oldUsername)) {
            String[] accountInfo = accounts.get(oldUsername);
            if (newUsername != null && !newUsername.isEmpty()) {
                accountInfo[0] = newUsername;
            }
            if (newPassword != null && !newPassword.isEmpty()) {
                accountInfo[1] = newPassword;
            }
            accounts.remove(oldUsername);
            accounts.put(newUsername != null && !newUsername.isEmpty() ? newUsername : oldUsername, accountInfo);
            return saveAccountsToCSV(accounts);
        }
        return false;
    }

    private boolean saveAccountsToCSV(Map<String, String[]> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getClass().getClassLoader().getResource("data/accounts.csv").getPath()))) {
            for (Map.Entry<String, String[]> entry : accounts.entrySet()) {
                String[] accountInfo = entry.getValue();
                writer.write(String.join(",", accountInfo));
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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


    private void saveMessageToCsv(String sender, String message) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\data\\chat.csv", true))) {
            writer.write(sender + ";" + message + "\n");
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

    public void bootNewAccountScreen() {
        setTitle("Nieuw account aanmaken");
        JPanel contentPane = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/background.jpg"));
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Naam:");
        JTextField nameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Wachtwoord:");
        JPasswordField passwordField = new JPasswordField(20);
        JLabel confirmPasswordLabel = new JLabel("Bevestig Wachtwoord:");
        JPasswordField confirmPasswordField = new JPasswordField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(confirmPasswordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(confirmPasswordField, gbc);

        JButton createAccountButton = new JButton("Account aanmaken");
        styleButton(createAccountButton);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(createAccountButton, gbc);

        contentPane.add(formPanel, BorderLayout.CENTER);

        createAccountButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String result = Login.nieuwAccount(name, email, password, confirmPassword);
            JOptionPane.showMessageDialog(this, result);

            if (result.equals("Account aangemaakt voor: " + name)) {
                bootWelcomeScreen();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setVisible(true);
    }
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ChatBox) {
            String message = (String) arg;
            // Handle the update (e.g., append the new message to the chat pane)
            System.out.println("New message: " + message);
            // Update the GUI (e.g., append the new message to the chat pane)
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui().bootWelcomeScreen());
    }
}
