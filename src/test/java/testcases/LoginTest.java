package testcases;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.LoginPage;

/**
 * Test class to verify login functionality.
 */
public class LoginTest extends BaseTest {

    /**
     * Test to verify that a user can log in successfully.
     */
    @Test
    public void loginApplication() {
        // Create a test entry in the extent report
        extentTest = extentReports.createTest("Login Test", "Test to verify login functionality");
        extentTest.info("Application URL: "+ webDriver.getCurrentUrl());

        // Initialize the LoginPage object
        LoginPage loginPage = new LoginPage();

        // Perform login using credentials from configuration properties
        String username = configProperties.getProperty("username");
        String password = configProperties.getProperty("password");
        loginPage.performLogin(username, password);

        // Assert that login was successful
        assert loginPage.isLoginSuccessful() : "Login was not successful";

        // Log the result in the extent report
        extentTest.info("User redirected to home page after successful login.");
    }
}