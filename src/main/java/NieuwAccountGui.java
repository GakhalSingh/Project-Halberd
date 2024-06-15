import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class NieuwAccountGui {
    private JFrame frame;
    private ResourceBundle bundle;
    private Login login;
    private Gui gui;
    private String currentLanguage = "nl";

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton createAccountButton;

    public NieuwAccountGui(JFrame frame, ResourceBundle bundle, Login login, Gui gui) {
        this.frame = frame;
        this.bundle = bundle;
        this.login = login;
        this.gui = gui;
    }

    public void bootNewAccountScreen() {
        frame.setTitle(bundle.getString("welcome.newAccount"));

        JPanel contentPane = createContentPane();
        JPanel formPanel = createFormPanel();

        addFormFields(formPanel);

        createAccountButton = new JButton(bundle.getString("welcome.newAccount"));
        gui.styleButton(createAccountButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(createAccountButton, gbc);

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCreateAccount();
            }
        });

        contentPane.add(formPanel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(contentPane);
        frame.setVisible(true);
    }

    private JPanel createContentPane() {
        return new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/background.jpg"));
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        formPanel.setOpaque(false);
        return formPanel;
    }

    private void addFormFields(JPanel formPanel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addField(formPanel, gbc, 0, bundle.getString("welcome.username"), nameField = new JTextField(20));
        addField(formPanel, gbc, 1, "Email:", emailField = new JTextField(20));
        addField(formPanel, gbc, 2, bundle.getString("welcome.password"), passwordField = new JPasswordField(20));
        addField(formPanel, gbc, 3, bundle.getString("welcome.password"), confirmPasswordField = new JPasswordField(20));
    }

    private void addField(JPanel formPanel, GridBagConstraints gbc, int yPos, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);

        gbc.gridx = 0;
        gbc.gridy = yPos;
        formPanel.add(label, gbc);

        gbc.gridx = 1;
        formPanel.add(textField, gbc);
    }

    private void handleCreateAccount() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String result = login.nieuwAccount(name, email, password, confirmPassword);
        JOptionPane.showMessageDialog(frame, result);

        if (result.equals(bundle.getString("account.created") + ": " + name)) {
            gui.bootWelcomeScreen();
        }
    }

    private void NewAccountScreenUsername(JPanel formPanel, GridBagConstraints gbc) {
        JLabel nameLabel = new JLabel(bundle.getString("welcome.username"));
        nameField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
    }

    private void NewAccountScreenEmail(JPanel formPanel, GridBagConstraints gbc) {
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(emailField, gbc);
    }

    private void NewAccountScreenPassword(JPanel formPanel, GridBagConstraints gbc) {
        JLabel passwordLabel = new JLabel(bundle.getString("welcome.password"));
        passwordField = new JPasswordField(20);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);
    }

    private void NewAccountScreenCPassword(JPanel formPanel, GridBagConstraints gbc) {
        JLabel confirmPasswordLabel = new JLabel(bundle.getString("welcome.password"));
        confirmPasswordField = new JPasswordField(20);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(confirmPasswordField, gbc);
    }
}
