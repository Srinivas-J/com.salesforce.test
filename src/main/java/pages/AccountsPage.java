package pages;

import base.BaseTest;
import base.Common;
import base.SeleniumLib;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object Model for the Accounts page.
 */
public class AccountsPage extends BaseTest {

    private Common common;
    private SeleniumLib seleniumLib;

    /**
     * Constructor initializes page elements and utility classes.
     */
    public AccountsPage() {
        super();
        PageFactory.initElements(webDriver, this);
        this.seleniumLib = new SeleniumLib();
        this.common = new Common(seleniumLib);
    }

    // Web elements on the Accounts page
    @FindBy(xpath = "//one-app-nav-bar-item-root[@data-id='Account']//a[@role='button']")
    private WebElement accountsTabDropdownBtn;

    @FindBy(xpath = "//div[@class='menuItemsWrapper']//span[text()='New Account']")
    private WebElement newAccountLink;

    @FindBy(xpath = "//input[@name='Name']")
    private WebElement accountNameInput;

    @FindBy(xpath = "//*[@field-label='Account Status']//span[@part='input-button-value']")
    private WebElement accountStatusDropdown;

    @FindBy(xpath = "//*[@field-label='Account Currency']//span[@part='input-button-value']")
    private WebElement accountCurrencyDropdown;

    @FindBy(xpath = "//*[@field-label='Account Source']//span[@part='input-button-value']")
    private WebElement accountSourceDropdown;

    /**
     * Waits until the Accounts tab is visible.
     */
    public void waitUntilAccountsTabIsVisible() {
        By accountsTabLocator = By.xpath("//one-app-nav-bar-item-root[@data-id='Account']//a[@role='button']");
        common.waitForLoadingSpinnerToBeInvisible();
        seleniumLib.waitForElementToBeVisible(accountsTabLocator);
        extentTest.info("Accounts tab is now visible on the page.");
    }

    /**
     * Clicks the New Account link from the Accounts tab dropdown.
     */
    public void clickNewAccountFromDropdown() {
        accountsTabDropdownBtn.click();
        extentTest.info("Clicked on Accounts tab dropdown button");
        seleniumLib.javascriptClick(newAccountLink);
        extentTest.info("Clicked on New Account link from dropdown");
    }

    /**
     * Verifies the Accounts dialog header text.
     * @param expectedHeader Expected header text
     * @return true if header matches, false otherwise
     */
    public boolean isAccountsDialogHeaderTextCorrect(String expectedHeader) {
        String actualHeader = common.getActionBodyHeaderText();
        extentTest.info("Expected header: " + expectedHeader + ", Actual header: " + actualHeader);
        return actualHeader.equals(expectedHeader);
    }

    /**
     * Selects the specified record type for Accounts.
     * @param recordType Record type to select
     */
    public void selectAccountsRecordType(String recordType) {
        By recordTypeLocator = By.xpath("//div[@class='changeRecordTypeOptionRightColumn']/span[text()='" + recordType + "']/../../div/input[@type='radio']");
        WebElement recordTypeRadioBtn = webDriver.findElement(recordTypeLocator);
        seleniumLib.javascriptClick(recordTypeRadioBtn);
        extentTest.info("Selected record type: " + recordType);
    }

    /**
     * Clicks the Next button.
     */
    public void clickNext() {
        common.clickNextButton();
    }

    /**
     * Types the account name in the input field.
     * @param accountName Name to enter
     */
    public void enterAccountName(String accountName) {
        accountNameInput.sendKeys(accountName);
        extentTest.info("Entered account name: " + accountName);
    }

    /**
     * Selects the specified account status from the dropdown.
     * @param accountStatus Status to select
     */
    public void selectAccountStatus(String accountStatus) {
        accountStatusDropdown.click();
        By accountStatusOptionLocator = By.xpath("//*[@field-label='Account Status']//div[@part='dropdown overlay']//span[text()='" + accountStatus + "']");
        WebElement accountStatusOption = webDriver.findElement(accountStatusOptionLocator);
        seleniumLib.javascriptClick(accountStatusOption);
        extentTest.info("Selected account status: " + accountStatus);
    }

    /**
     * Selects the specified account currency from the dropdown.
     * @param currency Currency to select
     */
    public void selectAccountCurrency(String currency) {
        seleniumLib.scrollToElement(accountCurrencyDropdown);
        accountCurrencyDropdown.click();
        By accountCurrencyOptionLocator = By.xpath("//*[@field-label='Account Currency']//div[@part='dropdown overlay']//span[text()='" + currency + "']");
        WebElement accountCurrencyOption = webDriver.findElement(accountCurrencyOptionLocator);
        seleniumLib.javascriptClick(accountCurrencyOption);
        extentTest.info("Selected account currency: " + currency);
    }

    /**
     * Selects the specified account source from the dropdown.
     * @param accountSource Source to select
     */
    public void selectAccountSource(String accountSource) {
        seleniumLib.scrollToElement(accountSourceDropdown);
        accountSourceDropdown.click();
        By accountSourceOptionLocator = By.xpath("//*[@field-label='Account Source']//div[@part='dropdown overlay']//span[text()='" + accountSource + "']");
        WebElement accountSourceOption = webDriver.findElement(accountSourceOptionLocator);
        accountSourceOption.click();
        extentTest.info("Selected account source: " + accountSource);
    }

    /**
     * Clicks the Save button.
     */
    public void clickSave() {
        common.clickSaveButton();
    }

    /**
     * Checks if the account name is displayed as expected.
     * @param entityLabel Label of the entity
     * @param expectedAccountName Expected account name
     * @return true if displayed name matches, false otherwise
     */
    public boolean isAccountNameDisplayed(String entityLabel, String expectedAccountName) {
        String actualAccountName = common.getPrimaryFieldText(entityLabel);
        extentTest.info("Expected account name: " + expectedAccountName + ", Actual account name: " + actualAccountName);
        return actualAccountName.equals(expectedAccountName);
    }
}