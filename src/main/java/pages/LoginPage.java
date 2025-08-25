package pages;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object Model for the Login Page.
 */
public class LoginPage extends BaseTest {

    // Web elements for login page fields and buttons
    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "Login")
    private WebElement loginBtn;

    @FindBy(xpath = "//a[@title='Home']")
    private WebElement homeLink;

    /**
     * Constructor initializes web elements using PageFactory.
     */
    public LoginPage() {
        super();
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Enters the username into the username input field.
     * @param username the username to enter
     */
    public void enterUsername(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
        extentTest.info("Entered username: " + username);
    }

    /**
     * Enters the password into the password input field.
     * @param password the password to enter
     */
    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
        extentTest.info("Entered password: [PROTECTED]");
    }

    /**
     * Performs the login action using the provided username and password.
     * @param username the username to use
     * @param password the password to use
     */
    public void performLogin(String username, String password) {
        webDriver.get(configProperties.getProperty("url"));
        enterUsername(username);
        enterPassword(password);
        loginBtn.click();
        extentTest.info("Clicked login button");
    }

    /**
     * Checks if the login was successful by verifying the presence of the home link.
     * @return true if login is successful, false otherwise
     */
    public boolean isLoginSuccessful() {
        extentTest.info("Verifying successful login by checking home link visibility.");
        return homeLink.isDisplayed();
    }
}