import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

public class ProfileScreenGui extends Component {
    private String username;
    private String email;
    private JFrame frame;
    private ResourceBundle bundle;
    private Login login;
    private Gui gui;

    public ProfileScreenGui(JFrame frame, ResourceBundle bundle, Login login, Gui gui) {
        this.frame = frame;
        this.bundle = bundle;
        this.login = login;
        this.gui = gui;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void showProfileScreen() {
    System.out.println("Displaying profile screen for username: " + username + " and email: " + email);
    frame.setTitle("Profiel van " + username);
    JPanel contentPane = new JPanel(new BorderLayout());
    contentPane.setBackground(Color.WHITE);

    JPanel infoPanel = new JPanel(new BorderLayout());
    infoPanel.setBackground(Color.WHITE);

    String profileText = ("<html><div style='text-align: center;'>"
            + "<h2>Hallo " + username + "</h2>"
            + "<p>Gevonden gegevens:</p>"
            + "<p><b>Gebruikersnaam:</b> " + username + "</p>"
            + "<p><b>Email:</b> " + email + "</p>"
            + "<p>Klik op de knop onderaan het scherm om uw informatie te wijzigen.</p><hr>"
            + "</div>"
            + "<p>░░░░░░░█▐▓▓░████▄▄▄█▀▄▓▓▓▌█</p>"
            + "<p>░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█</p>"
            + "<p>░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█</p>"
            + "<p>░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█▌</p>"
            + "<p>░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█</p>"
            + "<p>▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▌█▌</p>"
            + "<p>█▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█</p>"
            + "<p>█▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█</p>"
            + "<p>▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌</p>"
            + "<p>▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌</p>"
            + "<p>█▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█</p>"
            + "</html>");

    JLabel infoText = new JLabel(profileText);
    JScrollPane scrollPane = new JScrollPane(infoText);
        infoText.setFont(new Font("Arial", Font.PLAIN, 16));
        infoText.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        infoPanel.add(infoText, BorderLayout.CENTER);
        contentPane.add(infoPanel, BorderLayout.CENTER);

    JPanel navbar = gui.createNavbar();
    contentPane.add(navbar, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JButton modifyButton = new JButton(bundle.getString("profile.modify"));
    gui.styleButton(modifyButton);
        modifyButton.addActionListener(e -> showModifyDialog());
        buttonPanel.add(modifyButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 600);
    frame.setLocationRelativeTo(null);
    frame.setContentPane(contentPane);
    frame.setVisible(true);
}


    public void showModifyDialog() {
        JTextField newUsernameField = new JTextField(username, 20);
        JPasswordField newPasswordField = new JPasswordField(20);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel(bundle.getString("profile.modify") + " " + bundle.getString("welcome.username")));
        panel.add(newUsernameField);
        panel.add(new JLabel(bundle.getString("profile.modify") + " " + bundle.getString("welcome.password")));
        panel.add(newPasswordField);

        int result = JOptionPane.showConfirmDialog(this, panel, bundle.getString("profile.modify"), JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String newUsername = newUsernameField.getText().trim();
            String newPassword = new String(newPasswordField.getPassword()).trim();

            if (newUsername.isEmpty() && newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, bundle.getString("error.notempty"));
                return;
            }

            if (!newUsername.isEmpty()) {
                if (!isUsernameAvailable(newUsername)) {
                    JOptionPane.showMessageDialog(this, bundle.getString("error.usernameexists"));
                    return;
                }
            }

            if (updateAccountInfo(username, newUsername, newPassword)) {
                username = newUsername.isEmpty() ? username : newUsername;
                JOptionPane.showMessageDialog(this, bundle.getString("success.update"));
                gui.bootProfileScreen();
            } else {
                JOptionPane.showMessageDialog(this, bundle.getString("error.update"));
            }
        }
    }

    private boolean isUsernameAvailable(String newUsername) {
        CSVReader reader = new CSVReader("src/main/resources/data/accounts.csv");
        Map<String, String[]> accounts = reader.readAccounts();
        return !accounts.containsKey(newUsername);
    }

    private boolean updateAccountInfo(String oldUsername, String newUsername, String newPassword) {
        CSVReader reader = new CSVReader("src/main/resources/data/accounts.csv");
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/data/accounts.csv"))) {
            for (String[] accountInfo : accounts.values()) {
                writer.write(String.join(",", accountInfo));
                writer.newLine();
            }
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}