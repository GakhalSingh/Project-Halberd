import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProfileScreenGuiTest {
    private Login login;
    private ProfileScreenGui profileScreenGui;

    @BeforeEach
    public void setUp() {
        Login login = new Login("src/test/resources/test_accounts.csv");
        profileScreenGui = new ProfileScreenGui(null, null, login, null);
        login.nieuwAccount("existingUser", "jojo@example.com", "password1", "password1");
    }

    // Equivalentieklassen en Randwaarden Tests voor updateAccountInfo
    @Test
    public void testUpdateAccountInfoValidNewUsernameAndPassword() {
        boolean result = profileScreenGui.updateAccountInfo("existingUser", "newUsername", "newPassword");
        assertTrue(result, "Expected successful update of both username and password.");
    }

    @Test
    public void testUpdateAccountInfoEmptyNewUsername() {
        boolean result = profileScreenGui.updateAccountInfo("existingUser", "", "newPassword");
        assertTrue(result, "Expected successful update of password only.");
    }

    @Test
    public void testUpdateAccountInfoSameOldAndNewUsername() {
        boolean result = profileScreenGui.updateAccountInfo("existingUser", "existingUser", "newPassword");
        assertTrue(result, "Expected successful update of password only.");
    }

    @Test
    public void testUpdateAccountInfoEmptyNewPassword() {
        boolean result = profileScreenGui.updateAccountInfo("existingUser", "newUsername", "");
        assertTrue(result, "Expected successful update of username only.");
    }

    @Test
    public void testUpdateAccountInfoMinLengthUsername() {
        boolean result = profileScreenGui.updateAccountInfo("existingUser", "a", "newPassword");
        assertTrue(result, "Expected successful update with minimum length username.");
    }

    @Test
    public void testUpdateAccountInfoMaxLengthUsername() {
        String maxLengthUsername = "a".repeat(255); // assuming 255 is the maximum length
        boolean result = profileScreenGui.updateAccountInfo("existingUser", maxLengthUsername, "newPassword");
        assertTrue(result, "Expected successful update with maximum length username.");
    }
}
