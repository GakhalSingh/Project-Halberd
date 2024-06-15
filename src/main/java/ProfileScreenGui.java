import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class ProfileScreenGui {
    private String username;
    private String email;
    private JFrame frame;
    private ResourceBundle bundle;
    private Login login;
    private Gui gui;
    private CSVWriter csvWriter;
    private CSVReader csvReader;

    private NieuwAccountGui nieuwAccountGui;

    private String currentLanguage = "nl";


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
        modifyButton.addActionListener(e -> gui.showModifyDialog());
        contentPane.add(modifyButton, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(contentPane);
        frame.setVisible(true);
    }
}
