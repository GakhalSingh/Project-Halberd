import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Gui extends JFrame {
    private String csvContent;  // Field to store CSV content


    public void bootWelcomeScreen() {
        setTitle("AI Study Help Assistant (A.I.S.H.A.)");
        setSize(1250, 750);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/resources/background.jpg"));
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

        BackgroundPanel mainPanel = new BackgroundPanel("src/resources/background.jpg");
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(true);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = new JLabel("Chat met A.I.S.H.A.!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionLabel = new JLabel("Verbeter je leerervaring met Aisha (Artificial Intelligence Study Help Assistent!");
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

        for (String[] accountInfo : accounts.values()) {
            if ((accountInfo[0].equals(usernameOrEmail) || accountInfo[2].equals(usernameOrEmail)) && accountInfo[1].equals(password)) {
                gui.setCsvContent(reader.readAllContent());
                return true;
            }
        }
        return false;
    }

    private void setCsvContent(String content) {
        this.csvContent = content;
    }

    public void bootHomeScreen() {
        JFrame mainFrame = new JFrame("Main Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1250, 750);
        mainFrame.setLayout(new BorderLayout());

        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(new Color(230, 230, 230));
        navPanel.setPreferredSize(new Dimension(200, mainFrame.getHeight()));
        JButton profileButton = new JButton("Profiel");
        JButton chatsButton = new JButton("Chats");
        JButton logoutButton = new JButton("Logout");

        profileButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        chatsButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        logoutButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        navPanel.add(profileButton);
        navPanel.add(chatsButton);
        navPanel.add(logoutButton);

        mainFrame.add(navPanel, BorderLayout.WEST);

        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBackground(Color.WHITE);

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField inputField = new JTextField();
        JButton sendButton = new JButton("Send");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        mainFrame.add(chatPanel, BorderLayout.CENTER);

        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, csvContent, "Profiel Informatie", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        chatsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, "Lijst van chats");
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                bootWelcomeScreen();
            }
        });

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                if (!message.isEmpty()) {
                    chatArea.append("You: " + message + "\n");
                    inputField.setText("");
                    // hier code van ed handlen van berichten
                }
            }
        });

        mainFrame.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Gui().bootWelcomeScreen();
            }
        });
    }
}
