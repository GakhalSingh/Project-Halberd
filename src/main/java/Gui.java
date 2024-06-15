import javax.swing.*;
import java.util.*;
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
    private String currentLanguage = "nl";
    private ResourceBundle bundle;
    private CSVWriter csvWriter;
    private CSVReader csvReader;
    private Login login;
    private String currentChatNumber;


    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton createAccountButton;
    private HomeScreenGui homeScreenGui;


    public Gui() {
        chatBox = new ChatBox(new ArrayList<>());
        csvWriter = new CSVWriter("src/main/resources/data/chat's.csv");
        csvReader = new CSVReader("src/main/resources/data/chat's.csv");
        login = new Login("src/main/resources/data/accounts.csv");
        loadResourceBundle("nl");
        bootWelcomeScreen();
    }

    public void loadResourceBundle(String languageCode) {
        Locale locale = new Locale(languageCode);
        bundle = ResourceBundle.getBundle("messages", locale);

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui().bootWelcomeScreen());
    }

    public void bootWelcomeScreen() {
        LogginGui logginGui = new LogginGui(this, bundle, login, this);
        logginGui.display();
    }

    public  void bootNieuwAccount(){
        NieuwAccountGui nieuwAccountGui = new NieuwAccountGui(this, bundle, login, this);
        nieuwAccountGui.bootNewAccountScreen();
    }

    public void bootProfileScrean(){
        ProfileScreenGui profileScreenGui = new ProfileScreenGui(this, bundle, login, this);
        profileScreenGui.showProfileScreen();
    }


    public String getEmailByUsernameOrEmail(String usernameOrEmail) {
        Map<String, String[]> accounts = csvReader.readAccounts();

        for (String[] accountInfo : accounts.values()) {
            if (accountInfo[0].equals(usernameOrEmail) || accountInfo[2].equals(usernameOrEmail)) {
                return accountInfo[2];
            }
        }
        return null;
    }

    public void bootHomeScreen() {
        HomeScreenGui homeScreenGui = new HomeScreenGui(this, this, bundle, csvReader, currentChatNumber);
        homeScreenGui.display();
    }



    public JPanel createNavbar() {
        JPanel navbar = new JPanel(new GridLayout(1, 4));
        navbar.setBackground(new Color(52, 152, 219));

        JButton chatsButton = new JButton(bundle.getString("home.chats"));
        JButton profileButton = new JButton(bundle.getString("home.profile"));
        JButton infoButton = new JButton(bundle.getString("home.info"));
        JButton logoutButton = new JButton(bundle.getString("home.logout"));

        styleButton(chatsButton);
        styleButton(profileButton);
        styleButton(infoButton);
        styleLogoutButton(logoutButton);

        chatsButton.addActionListener(e -> bootHomeScreen());
        profileButton.addActionListener(e -> bootProfileScrean());
        infoButton.addActionListener(e -> showInfoScreen());
        logoutButton.addActionListener(e -> bootWelcomeScreen());

        navbar.add(chatsButton);
        navbar.add(profileButton);
        navbar.add(infoButton);
        navbar.add(logoutButton);

        return navbar;
    }




    public void styleChatButton(JButton button) {
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void toggleChatListPanel() {
        chatListPanel.setVisible(!chatListPanel.isVisible());
        if (chatListPanel.isVisible()) {
            homeScreenGui.populateChatListPanel();
        }

    }

    public void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public void styleLogoutButton(JButton button) {
        button.setBackground(new Color(222, 102, 90));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
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
            bootProfileScrean();
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

    public void showInfoScreen() {
        setTitle(bundle.getString("home.info"));
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

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ChatBox) {
            String message = (String) arg;
            // Handle the update (e.g., append the new message to the chat pane)
            System.out.println("New message: " + message);
            // Update the GUI (e.g., append the new message to the chat pane)
        }
    }

}
