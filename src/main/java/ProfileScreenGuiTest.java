import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class ProfileScreenGuiTest {

    private ProfileScreenGui profileScreenGui;
    private JFrame frame;
    private ResourceBundle bundle;
    private Login login;
    private Gui gui;

    @Before
    public void setUp() {
        frame = new JFrame();
        bundle = ResourceBundle.getBundle("messages", new Locale("nl"));
        login = new Login("src/main/resources/data/accounts.csv");
        gui = new Gui();

        profileScreenGui = new ProfileScreenGui(frame, bundle, login, gui);
        profileScreenGui.setUsername("testuser");
        profileScreenGui.setEmail("testuser@example.com");
    }

    @Test
    public void testShowProfileScreen() {
        profileScreenGui.showProfileScreen();

        // Check the frame title
        assertEquals("Over testuser", frame.getTitle());

        // Check the JTextArea content
        JPanel contentPane = (JPanel) frame.getContentPane();
        JScrollPane scrollPane = (JScrollPane) contentPane.getComponent(0);
        JTextArea infoText = (JTextArea) scrollPane.getViewport().getView();

        String expectedText = "Hallo testuser\n\n" +
                "hier heb je wat informatie over je zelf XD: \n" +
                "testuser\n" +
                "testuser@example.com\n\n" +
                "Informatie wijzigen? ";
        assertEquals(expectedText, infoText.getText());

        // Ensure email is correctly displayed
        String actualText = infoText.getText();
        boolean emailDisplayed = actualText.contains("testuser@example.com");
        assertEquals(true, emailDisplayed);
    }
}
