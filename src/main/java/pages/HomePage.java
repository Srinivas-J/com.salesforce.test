package pages;

import base.BaseTest;
import base.Common;
import base.SeleniumLib;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Represents the Home Page and provides methods to interact with its elements.
 */
public class HomePage extends BaseTest {

    private Common commonUtil;
    private SeleniumLib seleniumLib;

    /**
     * Initializes the HomePage with required utilities and page elements.
     */
    public HomePage() {
        super();
        PageFactory.initElements(webDriver, this);
        this.seleniumLib = new SeleniumLib();
        this.commonUtil = new Common(seleniumLib);
    }

    // Web element for the Home tab in the navigation bar
    @FindBy(xpath = "//one-app-nav-bar-item-root[@data-id='home']/a")
    private WebElement homeTabElement;

    // Web element for the search button
    @FindBy(xpath = "//button[@aria-label='Search']")
    private WebElement searchButtonElement;

    // Web element for the search input field
    @FindBy(xpath = "//input[@placeholder='Search...' and @type='search']")
    private WebElement searchInputElement;

    /**
     * Clicks on the Home tab and waits for the loading spinner to disappear.
     */
    public void clickHomeTab() {
        seleniumLib.javascriptClick(homeTabElement);
        extentTest.info("Clicked on Home tab");
        commonUtil.waitForLoadingSpinnerToBeInvisible();
    }

    /**
     * Verifies if the Home page is displayed.
     * Throws RuntimeException if not displayed.
     */
    public void verifyHomePageIsDisplayed() {
        seleniumLib.waitForElementToBeVisible(homeTabElement);
        if (homeTabElement.isDisplayed()) {
            extentTest.info("Home page is displayed successfully.");
        } else {
            extentTest.fail("Home page is not displayed.");
            throw new RuntimeException("Home page is not displayed.");
        }
    }

    /**
     * Searches for the given text using the search functionality and waits for the results.
     * @param searchText The text to search for.
     */
    public void searchAndOpen(String searchText) {
        commonUtil.waitForLoadingSpinnerToBeInvisible();
        seleniumLib.waitForElementToBeClickable(searchButtonElement);
        searchButtonElement.click();
        extentTest.info("Clicked on Search button");
        seleniumLib.waitForElementToBeVisible(searchInputElement);
        searchInputElement.sendKeys(searchText);
        extentTest.info("Entered search text: " + searchText);
        searchInputElement.sendKeys(Keys.ENTER);
        extentTest.info("Pressed Enter to initiate search");
        commonUtil.waitForLoadingSpinnerToBeInvisible();
    }
}