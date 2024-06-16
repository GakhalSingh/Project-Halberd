import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
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
        setupFrame();
        JPanel contentPane = createContentPane();
        JPanel mainPanel = createMainPanel();

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        addPanelsToMainPanel(mainPanel, leftPanel, rightPanel);
        addLanguageButton(mainPanel);
        addMainPanelToContentPane(contentPane, mainPanel);

        frame.revalidate();
        frame.repaint();
    }

    private void setupFrame() {
        frame.setTitle(bundle.getString("welcome.title"));
        frame.setSize(1000, 600);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createContentPane() {
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
        return contentPane;
    }


    private JPanel createMainPanel() {
        BackgroundPanel mainPanel = new BackgroundPanel("/img/background.jpg");
        mainPanel.setLayout(new GridBagLayout());
        return mainPanel;
    }


    private JPanel createLeftPanel() {
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
        return leftPanel;
    }


    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        GridBagConstraints gbc = createGbc();

        addComponentsToRightPanel(rightPanel, gbc);
        return rightPanel;
    }

    private GridBagConstraints createGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void addComponentsToRightPanel(JPanel rightPanel, GridBagConstraints gbc) {
        JLabel quickStartLabel = new JLabel(bundle.getString("welcome.login"));
        quickStartLabel.setFont(new Font("Arial", Font.BOLD, 20));
        addToPanel(rightPanel, quickStartLabel, gbc, 0, 0, 2);

        JLabel usernameLabel = new JLabel(bundle.getString("welcome.username"));
        addToPanel(rightPanel, usernameLabel, gbc, 0, 1, 1);

        JTextField usernameField = new JTextField(20);
        addToPanel(rightPanel, usernameField, gbc, 1, 1, 1);

        JLabel passwordLabel = new JLabel(bundle.getString("welcome.password"));
        addToPanel(rightPanel, passwordLabel, gbc, 0, 2, 1);

        JPasswordField passwordField = new JPasswordField(20);
        addToPanel(rightPanel, passwordField, gbc, 1, 2, 1);

        JButton loginButton = createLoginButton(usernameField, passwordField);
        addToPanel(rightPanel, loginButton, gbc, 1, 3, 1);

        JButton newAccountButton = createNewAccountButton();
        addToPanel(rightPanel, newAccountButton, gbc, 1, 4, 1);
    }

    private void addPanelsToMainPanel(JPanel mainPanel, JPanel leftPanel, JPanel rightPanel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        mainPanel.add(leftPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        mainPanel.add(rightPanel, gbc);
    }

    private void addMainPanelToContentPane(JPanel contentPane, JPanel mainPanel) {
        contentPane.add(mainPanel, BorderLayout.CENTER);
    }

    private void addToPanel(JPanel panel, Component component, GridBagConstraints gbc, int x, int y, int width) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        panel.add(component, gbc);
    }

    private void addLanguageButton(JPanel mainPanel) {
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
    }

    private JButton createLoginButton(JTextField usernameField, JPasswordField passwordField) {
        JButton loginButton = new JButton(bundle.getString("welcome.login"));
        loginButton.setBackground(new Color(52, 152, 219));
        loginButton.setForeground(Color.WHITE);

        loginButton.addActionListener(e -> {
            String usernameOrEmail = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (login.authenticate(usernameOrEmail, password)) {
                gui.setUsername(usernameOrEmail);
                gui.setEmail(login.getEmail(usernameOrEmail));
                gui.bootHomeScreen();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password");
            }
        });

        return loginButton;
    }

    private JButton createNewAccountButton() {
        JButton newAccountButton = new JButton(bundle.getString("welcome.newAccount"));
        gui.styleButton(newAccountButton);

        newAccountButton.addActionListener(e -> gui.bootNieuwAccount());
        return newAccountButton;
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

        addLanguageButton(languageDialog, "Nederlands", "nl");
        addLanguageButton(languageDialog, "English", "en");
        addLanguageButton(languageDialog, "Español", "es");
        addLanguageButton(languageDialog, "Deutsch", "de");

        languageDialog.pack();
        languageDialog.setLocationRelativeTo(this.frame);
        languageDialog.setVisible(true);
    }

    private void addLanguageButton(JDialog dialog, String languageName, String languageCode) {
        JButton languageButton = new JButton(languageName);
        languageButton.addActionListener(e -> {
            changeLanguage(languageCode);
            dialog.dispose();
        });
        dialog.add(languageButton);
    }
}
