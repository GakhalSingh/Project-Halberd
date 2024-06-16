import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    private Login login;
    private ProfileScreenGui profileScreenGui;

    @BeforeEach
    public void setUp() {
        login = new Login("src/test/resources/test_accounts.csv");
        profileScreenGui = new ProfileScreenGui(null, null, login, null);
    }

    // MC/DC Tests voor authenticate
    @Test
    public void testAuthenticateMC_DC_TT_T() {
        boolean result = login.authenticate("admin", "admin");
        assertTrue(result, "Expected authentication to succeed.");
    }

    @Test
    public void testAuthenticateMC_DC_FT_T() {
        boolean result = login.authenticate("admin@admin.com", "admin");
        assertTrue(result, "Expected authentication to succeed.");
    }

    @Test
    public void testAuthenticateMC_DC_FF_T() {
        boolean result = login.authenticate("nonexistentUser", "password1");
        assertFalse(result, "Expected authentication to fail.");
    }

    @Test
    public void testAuthenticateMC_DC_TT_F() {
        boolean result = login.authenticate("username1", "wrongPassword");
        assertFalse(result, "Expected authentication to fail.");
    }

    @Test
    public void testAuthenticateMC_DC_FT_F() {
        boolean result = login.authenticate("email1@example.com", "wrongPassword");
        assertFalse(result, "Expected authentication to fail.");
    }

    // MC/DC Tests voor updateAccountInfo
    @Test
    public void testUpdateAccountInfoSuccessChangeBoth() {
        boolean result = profileScreenGui.updateAccountInfo("existingUser", "newUsername", "newPassword");
        assertTrue(result, "Expected successful update of both username and password.");
    }

    @Test
    public void testUpdateAccountInfoSuccessChangeUsername() {
        boolean result = profileScreenGui.updateAccountInfo("existingUser", "newUsername", "");
        assertTrue(result, "Expected successful update of username.");
    }

    @Test
    public void testUpdateAccountInfoSuccessChangePassword() {
        boolean result = profileScreenGui.updateAccountInfo("existingUser", "", "newPassword");
        assertTrue(result, "Expected successful update of password.");
    }

    @Test
    public void testUpdateAccountInfoNoChanges() {
        boolean result = profileScreenGui.updateAccountInfo("existingUser", "", "");
        assertFalse(result, "Expected failure due to no changes.");
    }

    @Test
    public void testUpdateAccountInfoUserNotFound() {
        boolean result = profileScreenGui.updateAccountInfo("nonExistingUser", "newUsername", "newPassword");
        assertFalse(result, "Expected failure due to non-existing user.");
    }
}
