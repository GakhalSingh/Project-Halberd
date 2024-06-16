import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest2 {
    private Login login;

    @BeforeEach
    public void setUp() {
        login = new Login("src/test/resources/test_accounts.csv");
    }

    @Test
    public void testAuthenticateCorrectUsernameAndPassword() {
        login.nieuwAccount("username1", "jojo@example.com", "password1", "password1");

        boolean result = login.authenticate("username1", "password1");
        assertTrue(result, "Expected authentication to succeed.");
    }

    @Test
    public void testAuthenticateIncorrectPassword() {
        boolean result = login.authenticate("username1", "wrongPassword");
        assertFalse(result, "Expected authentication to fail with wrong password.");
    }

    @Test
    public void testAuthenticateNonExistingUsername() {
        boolean result = login.authenticate("nonexistentUser", "password1");
        assertFalse(result, "Expected authentication to fail with non-existing username.");
    }


    @Test
    public void testAuthenticateEmptyUsername() {
        boolean result = login.authenticate("", "password1");
        assertFalse(result, "Expected authentication to fail with empty username.");
    }

    @Test
    public void testAuthenticateEmptyPassword() {
        boolean result = login.authenticate("username1", "");
        assertFalse(result, "Expected authentication to fail with empty password.");
    }

    @Test
    public void testAuthenticateMinLengthUsername() {
        boolean result = login.authenticate("a", "password1");
        assertFalse(result, "Expected authentication to fail with minimum length username.");
    }

    @Test
    public void testAuthenticateMaxLengthUsername() {
        String maxLengthUsername = "a".repeat(255); // assuming 255 is the maximum length
        boolean result = login.authenticate(maxLengthUsername, "password1");
        assertFalse(result, "Expected authentication to fail with maximum length username.");
    }
}
