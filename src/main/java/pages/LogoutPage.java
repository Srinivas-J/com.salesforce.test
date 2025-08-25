package pages;

import base.BaseTest;
import base.SeleniumLib;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object representing the Logout functionality.
 */
public class LogoutPage extends BaseTest {

    // Button to open the user profile menu
    @FindBy(xpath = "//div[@class='forceHeaderButton']//span[@class='uiImage']")
    private WebElement profileMenuButton;

    // Link to perform logout action
    @FindBy(xpath = "//div[@class='profile-card-toplinks']//a[2]")
    private WebElement logoutButton;

    // Title element to confirm successful logout
    @FindBy(xpath = "//title[contains(text(),'Salesforce: The Customer Company')]")
    private WebElement logoutConfirmationTitleElement;

    private final SeleniumLib seleniumLib;

    /**
     * Constructor initializes web elements and Selenium library.
     */
    public LogoutPage() {
        super();
        this.seleniumLib = new SeleniumLib();
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Clicks the profile menu button to open user options.
     */
    public void openProfileMenu() {
        profileMenuButton.click();
        extentTest.info("Profile menu button clicked");
    }

    /**
     * Clicks the logout button to log out the user.
     */
    public void clickLogout() {
        logoutButton.click();
        extentTest.info("Logout button clicked");
    }

    /**
     * Performs the complete logout process.
     */
    public void performLogout() {
        openProfileMenu();
        clickLogout();
        extentTest.info("Logout process initiated");
    }

    /**
     * Checks if logout was successful by verifying the page title.
     * @return true if logout confirmation title is present, false otherwise
     */
    public boolean isLogoutSuccessful() {
        seleniumLib.waitForPageToLoad();
        return webDriver.getTitle().contains(logoutConfirmationTitleElement.getText());
    }
}