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

    private InfoScreenGui infoScreenGui;


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
    public void bootInfoScreen(){
        InfoScreenGui infoScreenGui = new InfoScreenGui(this,this,bundle);
        infoScreenGui.display();
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
            // Handle the update (e.g., append the new message to the chat pane)
            System.out.println("New message: " + message);
            // Update the GUI (e.g., append the new message to the chat pane)
        }
    }

}
