package testcases;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.LogoutPage;

/**
 * Test class to verify the logout functionality of the application.
 */
public class LogoutTest extends BaseTest {

    /**
     * Tests the logout process and verifies if the user is redirected to the login page.
     */
    @Test
    public void logoutApplication() {
        // Create a test entry in the extent report
        extentTest = extentReports.createTest("Logout Test", "Verifies the logout functionality");

        // Instantiate the LogoutPage and perform logout
        LogoutPage logoutPage = new LogoutPage();
        logoutPage.performLogout();

        // Assert that logout was successful
        assert logoutPage.isLogoutSuccessful() : "Logout was not successful";

        // Log the result in the extent report
        extentTest.info("Logout successful, redirected to login page.");
        extentTest.pass("Logout was successful");
    }
}