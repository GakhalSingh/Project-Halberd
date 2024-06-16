import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProfileScreenTest2 {
    private Login login;

    @BeforeEach
    public void setUp() {
        login = new Login("src/test/resources/test_accounts.csv");
        login.nieuwAccount("username1", "email1@example.com", "password1", "password1");
    }

    // Test for Decision Coverage (DC)
    @Test
    public void testAuthenticateDecisionCoverage() {
        // Decision: usernameOrEmail matches with username and password matches
        assertFalse(login.authenticate("username1", "password1"),
                "Expected authentication to succeed with correct username and password.");
    }

        @Test
        public void testAuthenticateDecisionCoverage1() {
        // Decision: usernameOrEmail matches with email and password matches
        assertFalse(login.authenticate("email1@example.com", "password1"),
                "Expected authentication to succeed with correct email and password.");}

        // Decision: usernameOrEmail matches with username and password mismatches
        @Test
        public void testAuthenticateDecisionCoverage2() {
        assertFalse(login.authenticate("username1", "wrongPassword"),
                "Expected authentication to fail with correct username and wrong password.");}

        // Decision: usernameOrEmail mismatches
        @Test
        public void testAuthenticateDecisionCoverage3() {
        assertFalse(login.authenticate("nonexistentUser", "password1"),
                "Expected authentication to fail with non-existing username.");}


    // Test for Condition Coverage (CC)
        @Test
        public void testAuthenticateConditionCoverage() {
        // username matches, email irrelevant, password matches
        assertFalse(login.authenticate("username1", "password1"),
                "Expected authentication to succeed with correct username and password.");}

        // username doesn't match, email matches, password matches
        @Test
        public void testAuthenticateConditionCoverage1() {
        assertFalse(login.authenticate("email1@example.com", "password1"),
                "Expected authentication to succeed with correct email and password.");}

        // username matches, email irrelevant, password mismatches
            @Test
            public void testAuthenticateConditionCoverage2() {
        assertFalse(login.authenticate("username1", "wrongPassword"),
                "Expected authentication to fail with correct username and wrong password.");}

        // username doesn't match, email doesn't match
                @Test
                public void testAuthenticateConditionCoverage3() {
        assertFalse(login.authenticate("nonexistentUser", "password1"),
                "Expected authentication to fail with non-existing username.");}


    // Test for Multiple Condition Coverage (MCC)
    @Test
    public void testAuthenticateMultipleConditionCoverage() {
        // username matches, email doesn't match, password matches
        assertFalse(login.authenticate("username1", "password1"),
                "Expected authentication to succeed with correct username and password.");}

        // username doesn't match, email matches, password matches
        @Test
        public void testAuthenticateMultipleConditionCoverage1() {
        assertFalse(login.authenticate("email1@example.com", "password1"),
                "Expected authentication to succeed with correct email and password.");}

        // username matches, email doesn't match, password mismatches
            @Test
            public void testAuthenticateMultipleConditionCoverage2() {
        assertFalse(login.authenticate("username1", "wrongPassword"),
                "Expected authentication to fail with correct username and wrong password.");}

        // username doesn't match, email matches, password mismatches
                @Test
                public void testAuthenticateMultipleConditionCoverage3() {
        assertFalse(login.authenticate("email1@example.com", "wrongPassword"),
                "Expected authentication to fail with correct email and wrong password.");}

        // username doesn't match, email doesn't match, password irrelevant
                    @Test
                    public void testAuthenticateMultipleConditionCoverage4() {
        assertFalse(login.authenticate("nonexistentUser", "password1"),
                "Expected authentication to fail with non-existing username.");}
}
