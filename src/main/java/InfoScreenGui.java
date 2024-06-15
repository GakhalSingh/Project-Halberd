import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class InfoScreenGui {
    private JFrame frame;
    private Gui gui;
    private ResourceBundle bundle;

    public InfoScreenGui(JFrame frame, Gui gui, ResourceBundle bundle) {
        this.frame = frame;
        this.gui = gui;
        this.bundle = bundle;
    }

    private void loadResourceBundle(String languageCode) {
        Locale locale = new Locale(languageCode);
        bundle = ResourceBundle.getBundle("messages", locale);

    }

    public void display() {
        frame.setTitle(bundle.getString("home.info"));
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("A.I.S.H.A. (AI Study Help Assistant)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        JTextArea infoText = new JTextArea();
        infoText.setEditable(false);
        infoText.setFont(new Font("Arial", Font.PLAIN, 16));
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        infoText.setText("A.I.S.H.A. (AI Study Help Assistant) is een virtuele assistent ontworpen om studenten te helpen bij hun studie. "
                + "Deze chatbot kan vragen beantwoorden, uitleg geven over verschillende onderwerpen en interactief leren stimuleren.\n\n"
                + "Ontwikkeld door:\n"
                + "- Jin\n"
                + "- Li\n"
                + "- Joris\n"
                + "- Brian\n");
        infoText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(infoText);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel navbar = gui.createNavbar();
        contentPane.add(navbar, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(contentPane);
        frame.setVisible(true);
    }


}
