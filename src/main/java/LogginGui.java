import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class LogginGui {
    private JFrame frame;
    private ResourceBundle bundle;
    private Login login;
    private Gui gui;

    private String currentLanguage = "nl";


    public LogginGui(JFrame frame, ResourceBundle bundle, Login login, Gui gui) {
        this.frame = frame;
        this.bundle = bundle;
        this.login = login;
        this.gui = gui;
    }

    public void display() {
        frame.setTitle(bundle.getString("welcome.title"));
        frame.setSize(1000, 600);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel contentPane = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/background.jpg"));
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        frame.setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        contentPane.add(panel, BorderLayout.CENTER);

        BackgroundPanel mainPanel = new BackgroundPanel("/img/background.jpg");
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(true);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = new JLabel(bundle.getString("home.chat"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionLabel = new JLabel(bundle.getString("info.about"));
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

        JLabel quickStartLabel = new JLabel(bundle.getString("welcome.login"));
        quickStartLabel.setFont(new Font("Arial", Font.BOLD, 20));
        rightGbc.gridx = 0;
        rightGbc.gridy = 0;
        rightGbc.gridwidth = 2;
        rightPanel.add(quickStartLabel, rightGbc);

        JLabel usernameLabel = new JLabel(bundle.getString("welcome.username"));
        rightGbc.gridx = 0;
        rightGbc.gridy = 1;
        rightGbc.gridwidth = 1;
        rightPanel.add(usernameLabel, rightGbc);

        JTextField usernameField = new JTextField(20);
        rightGbc.gridx = 1;
        rightGbc.gridy = 1;
        rightPanel.add(usernameField, rightGbc);

        JLabel passwordLabel = new JLabel(bundle.getString("welcome.password"));
        rightGbc.gridx = 0;
        rightGbc.gridy = 2;
        rightPanel.add(passwordLabel, rightGbc);

        JPasswordField passwordField = new JPasswordField(20);
        rightGbc.gridx = 1;
        rightGbc.gridy = 2;
        rightPanel.add(passwordField, rightGbc);

        JButton loginButton = new JButton(bundle.getString("welcome.login"));
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

        frame.add(mainPanel);

        loginButton.addActionListener(e -> {
            String usernameOrEmail = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (login.authenticate(usernameOrEmail, password)) {
                String username = login.getUsername(usernameOrEmail);
                gui.setUsername(usernameOrEmail);
                gui.setEmail(login.getEmail(usernameOrEmail));
                gui.bootHomeScreen();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password");
            }
        });

        JButton newAccountButton = new JButton(bundle.getString("welcome.newAccount"));
        gui.styleButton(newAccountButton);
        rightGbc.gridx = 1;
        rightGbc.gridy = 4;
        rightGbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(newAccountButton, rightGbc);

        newAccountButton.addActionListener(e -> gui.bootNieuwAccount());

        JButton languageButton = new JButton("Taal / Language");
        languageButton.setBackground(new Color(52, 152, 219));
        languageButton.setForeground(Color.WHITE);
        GridBagConstraints langGbc = new GridBagConstraints();
        langGbc.gridx = 1;
        langGbc.gridy = 0;
        langGbc.anchor = GridBagConstraints.NORTHEAST;
        langGbc.insets = new Insets(10, 10, 0, 10);
        mainPanel.add(languageButton, langGbc);

        languageButton.addActionListener(e -> showLanguageSelector());

        contentPane.add(mainPanel, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }



    public void changeLanguage(String languageCode) {
        currentLanguage = languageCode;
        loadResourceBundle(languageCode);
        display();
    }

    private void loadResourceBundle(String languageCode) {
        Locale locale = new Locale(languageCode);
        bundle = ResourceBundle.getBundle("messages", locale);

    }

    private void showLanguageSelector() {
        JDialog languageDialog = new JDialog(frame, bundle.getString("language.title"), true);
        languageDialog.setLayout(new GridLayout(4, 1));

        JButton nederlandsButton = new JButton("Nederlands");
        JButton englishButton = new JButton("English");
        JButton spaansButton = new JButton("Español");
        JButton duitsButton = new JButton("Deutsch");

        nederlandsButton.addActionListener(e -> {
            changeLanguage("nl");
            languageDialog.dispose();
        });

        englishButton.addActionListener(e -> {
            changeLanguage("en");
            languageDialog.dispose();
        });

        spaansButton.addActionListener(e -> {
            changeLanguage("es");
            languageDialog.dispose();
        });

        duitsButton.addActionListener(e -> {
            changeLanguage("de");
            languageDialog.dispose();
        });

        languageDialog.add(nederlandsButton);
        languageDialog.add(englishButton);
        languageDialog.add(spaansButton);
        languageDialog.add(duitsButton);

        languageDialog.pack();
        languageDialog.setLocationRelativeTo(this.frame);
        languageDialog.setVisible(true);
    }
}
