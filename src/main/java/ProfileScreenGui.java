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

    public void showProfileScreen() {
        frame.setTitle("Over " + username);
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

        JPanel navbar = gui.createNavbar();
        contentPane.add(navbar, BorderLayout.NORTH);

        JButton modifyButton = new JButton(bundle.getString("profile.modify"));
        gui.styleButton(modifyButton);
        modifyButton.addActionListener(e ->showModifyDialog());
        contentPane.add(modifyButton, BorderLayout.SOUTH);

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
                gui.bootProfileScrean();
            } else {
                JOptionPane.showMessageDialog(this, bundle.getString("error.update"));
            }
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
