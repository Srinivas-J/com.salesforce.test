package base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Common page actions and utilities for web automation.
 */
public class Common extends BaseTest {

    private final SeleniumLib seleniumLib;

    /**
     * Constructor to initialize Common page object.
     * @param seleniumLib SeleniumLib instance for web actions
     */
    public Common(SeleniumLib seleniumLib) {
        super();
        PageFactory.initElements(webDriver, this);
        this.seleniumLib = seleniumLib;
    }

    @FindBy(xpath = "//*[@slot='primaryField']")
    private WebElement primaryField;

    @FindBy(xpath = "//div[@class='actionBody']//h2")
    private WebElement actionBodyHeader;

    @FindBy(xpath = "//div[@class='footer-full-width']//button[text()='Save']")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@class='inlineFooter']//span[text()='Next']")
    private WebElement nextButton;

    /**
     * Waits for the loading spinner to become invisible.
     */
    public void waitForLoadingSpinnerToBeInvisible() {
        By spinnerLocator = By.xpath("//*[@class='indicatorContainer forceInlineSpinner darkened']");
        seleniumLib.waitForElementToBeInvisible(spinnerLocator);
    }

    /**
     * Gets the header text from the action body.
     * @return The header text as String
     */
    public String getActionBodyHeaderText() {
        waitForLoadingSpinnerToBeInvisible();
        seleniumLib.waitForElementToBeVisible(actionBodyHeader);
        return actionBodyHeader.getText();
    }

    /**
     * Checks if the dialog header text matches the expected value.
     * @param expectedHeaderText The expected header text
     * @return true if matched, false otherwise
     */
    public boolean isDialogHeaderTextMatched(String expectedHeaderText) {
        String actualHeader = getActionBodyHeaderText();
        extentTest.info("Expected header: " + expectedHeaderText + ", Actual header: " + actualHeader);
        return actualHeader.equals(expectedHeaderText);
    }

    /**
     * Clicks the Save button and waits for the loading spinner to disappear.
     */
    public void clickSaveButton() {
        seleniumLib.javascriptClick(saveButton);
        extentTest.info("Clicked on Save button and waited for loading spinner to be invisible");
        waitForLoadingSpinnerToBeInvisible();
    }

    /**
     * Gets the primary field text for a given entity label.
     * @param entityLabel The entity label to search for
     * @return The primary field text as String
     */
    public String getPrimaryFieldText(String entityLabel) {
        By primaryFieldLocator = By.xpath(
            "//*[@slot='entityLabel' and text()='" + entityLabel + "']/../../following-sibling::slot//*[@slot='primaryField']"
        );
        seleniumLib.waitForElementToBeVisible(primaryFieldLocator);
        return webDriver.findElement(primaryFieldLocator).getText();
    }

    /**
     * Clicks the Next button and waits for the loading spinner to disappear.
     */
    public void clickNextButton() {
        seleniumLib.javascriptClick(nextButton);
        extentTest.info("Clicked on Next button");
        waitForLoadingSpinnerToBeInvisible();
    }


    /**
     * Switches the WebDriver context to the iframe with the accessibility title.
     * @return The WebDriver instance focused on the iframe
     */
    public WebDriver switchToAccessibilityTitleIframe() {
        waitForLoadingSpinnerToBeInvisible();
        By iframeLocator = By.xpath("//iframe[@title='accessibility title']");
        seleniumLib.waitForElementToBeVisible(iframeLocator);
        WebElement iframeElement = webDriver.findElement(iframeLocator);
        return webDriver.switchTo().frame(iframeElement);
    }

    public WebElement getShadowSpinnerElement(WebDriver driver) {
        String script = "return document.querySelector('#sbPageContainer')"
                + ".shadowRoot.querySelector('#spinner')"
                + ".shadowRoot.querySelector('#mask > div.slds-spinner--brand.slds-spinner.large > div.slds-spinner__dot-a');";
        return (WebElement) ((JavascriptExecutor) driver).executeScript(script);
    }

}