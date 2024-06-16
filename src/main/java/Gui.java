import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class Gui extends JFrame implements Observer {
    private ChatBox chatBox;
    public String username;
    public String email;
    private ResourceBundle bundle;
    private CSVWriter csvWriter;
    private CSVReader csvReader;
    private Login login;
    private String currentChatNumber;

    private ProfileScreenGui profileScreenGui;

    public Gui() {
        chatBox = new ChatBox(new ArrayList<>());
        chatBox.addObserver(this); // Registering Gui as an observer
        csvWriter = new CSVWriter("src/main/resources/data/chat's.csv");
        csvReader = new CSVReader("src/main/resources/data/chat's.csv");
        login = new Login("data\\accounts.csv");
        loadResourceBundle("nl");
        profileScreenGui = new ProfileScreenGui(this, bundle, login, this);
        bootWelcomeScreen();
    }

    public void loadResourceBundle(String languageCode) {
        Locale locale = new Locale(languageCode);
        bundle = ResourceBundle.getBundle("messages", locale);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui().bootWelcomeScreen());
    }

    public void bootWelcomeScreen() {
        LogginGui logginGui = new LogginGui(this, bundle, login, this);
        logginGui.display();
    }

    public void bootNieuwAccount() {
        NieuwAccountGui nieuwAccountGui = new NieuwAccountGui(this, bundle, login, this);
        nieuwAccountGui.bootNewAccountScreen();
    }

    public void bootHomeScreen() {
        HomeScreenGui homeScreenGui = new HomeScreenGui(this, this, bundle, csvReader, currentChatNumber);
        homeScreenGui.display();
    }

    public void bootProfileScreen() {
        System.out.println("Booting profile screen with username: " + username + " and email: " + email); // Debug statement
        profileScreenGui.setUsername(username);
        profileScreenGui.setEmail(email);
        profileScreenGui.showProfileScreen();
    }

    public void bootInfoScreen() {
        InfoScreenGui infoScreenGui = new InfoScreenGui(this, this, bundle);
        infoScreenGui.display();
    }

    public String getEmailByUsernameOrEmail(String usernameOrEmail) {
    Map<String, String[]> accounts = csvReader.readAccounts();
    for (String[] accountInfo : accounts.values()) {
        System.out.println("Checking account: " + accountInfo[0] + " with email: " + accountInfo[2]); // Debug statement
        if (accountInfo[0].equals(usernameOrEmail) || accountInfo[2].equals(usernameOrEmail)) {
            return accountInfo[2];
        }
    }
    return null;
}


    public void setUsername(String username) {
        this.username = username;
        System.out.println("Username set to: " + username); // Debug statement
        if (profileScreenGui != null) {
            profileScreenGui.setUsername(username);
        }
    }

    public void setEmail(String email) {
        this.email = email;
        System.out.println("Email set to: " + email); // Debug statement
        if (profileScreenGui != null) {
            profileScreenGui.setEmail(email);
        }
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
        profileButton.addActionListener(e -> bootProfileScreen());
        infoButton.addActionListener(e -> bootInfoScreen());
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

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ChatBox) {
            String message = (String) arg;
            System.out.println("New message: " + message);
        }
    }
}
