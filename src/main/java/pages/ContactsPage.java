package pages;

import base.BaseTest;
import base.Common;
import base.SeleniumLib;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object Model for the Contacts page.
 */
public class ContactsPage extends BaseTest {

    private Common common;
    private SeleniumLib seleniumLib;

    public ContactsPage() {
        super();
        PageFactory.initElements(webDriver, this);
        this.seleniumLib = new SeleniumLib();
        this.common = new Common(seleniumLib);
    }

    @FindBy(xpath = "//one-app-nav-bar-item-root[@data-id='Contact']//a[@role='button']")
    private WebElement contactsTabDropdownBtn;

    @FindBy(xpath = "//div[@class='menuItemsWrapper']//span[text()='New Contact']")
    private WebElement newContactMenuItem;

    @FindBy(xpath = "//*[@aria-label='Salutation']//span[@part='input-button-value']")
    private WebElement salutationDropdownBtn;

    @FindBy(xpath = "//input[@name='firstName']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@name='lastName']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//label[text()='Account Name']//following-sibling::div//input[@role='combobox']")
    private WebElement accountNameInput;

    @FindBy(xpath = "//input[@name='Email']")
    private WebElement emailInput;

    @FindBy(xpath = "//*[@aria-label='Primary Language']//span[@part='input-button-value']")
    private WebElement primaryLanguageDropdownBtn;

    @FindBy(xpath = "//*[@aria-label='Lead Source']//span[@part='input-button-value']")
    private WebElement leadSourceDropdownBtn;

    /**
     * Waits until the Contacts tab is visible.
     */
    public void waitForContactsTabVisible() {
        common.waitForLoadingSpinnerToBeInvisible();
        seleniumLib.waitForElementToBeVisible(contactsTabDropdownBtn);
        extentTest.info("Contacts tab is now visible on the page.");
    }

    /**
     * Clicks the New Contact link from the Contacts tab dropdown.
     */
    public void clickNewContactFromDropdown() {
        contactsTabDropdownBtn.click();
        extentTest.info("Clicked on Contacts tab dropdown button");
        seleniumLib.javascriptClick(newContactMenuItem);
        extentTest.info("Clicked on New Contact link from dropdown");
    }

    /**
     * Verifies the header text of the Contacts dialog.
     * @param expectedHeader Expected header text
     * @return true if header matches, false otherwise
     */
    public boolean isContactsDialogHeaderTextCorrect(String expectedHeader) {
        String actualHeader = common.getActionBodyHeaderText();
        extentTest.info("Expected header: " + expectedHeader + ", Actual header: " + actualHeader);
        return actualHeader.equals(expectedHeader);
    }

    /**
     * Selects a salutation from the dropdown.
     * @param salutationText Salutation to select
     */
    public void selectSalutation(String salutationText) {
        seleniumLib.scrollToElement(salutationDropdownBtn);
        seleniumLib.javascriptClick(salutationDropdownBtn);
        extentTest.info("Clicked on Salutation dropdown");

        By salutationOptionLocator = By.xpath("//div[@part='dropdown overlay']//span[text()='" + salutationText + "']");
        WebElement salutationOption = webDriver.findElement(salutationOptionLocator);

        seleniumLib.javascriptClick(salutationOption);
        extentTest.info("Selected salutation: " + salutationText);
    }

    /**
     * Types the first name in the input field.
     * @param firstName First name to enter
     */
    public void enterFirstName(String firstName) {
        seleniumLib.waitForElementToBeVisible(firstNameInput);
        firstNameInput.sendKeys(firstName);
        extentTest.info("Entered First Name: " + firstName);
    }

    /**
     * Types the last name in the input field.
     * @param lastName Last name to enter
     */
    public void enterLastName(String lastName) {
        seleniumLib.waitForElementToBeVisible(lastNameInput);
        lastNameInput.sendKeys(lastName);
        extentTest.info("Entered Last Name: " + lastName);
    }

    /**
     * Selects an account name from the dropdown.
     * @param accountName Account name to select
     */
    public void selectAccountName(String accountName) {
        seleniumLib.scrollToElement(accountNameInput);
        seleniumLib.waitForElementToBeVisible(accountNameInput);
        seleniumLib.javascriptClick(accountNameInput);
        seleniumLib.javascriptType(accountNameInput, accountName);
        extentTest.info("Entered Account Name: " + accountName);

        By accountNameOptionLocator = By.xpath("//label[text()='Account Name']/..//div[@part='dropdown overlay']//span[text()='" + accountName + "']");
        WebElement accountNameOption = webDriver.findElement(accountNameOptionLocator);
        seleniumLib.javascriptClick(accountNameOption);
        extentTest.info("Selected Account Name from choose list");
    }

    /**
     * Types the email in the input field.
     * @param email Email to enter
     */
    public void enterEmail(String email) {
        seleniumLib.scrollToElement(emailInput);
        emailInput.sendKeys(email);
        extentTest.info("Entered Email: " + email);
    }

    /**
     * Selects a primary language from the dropdown.
     * @param language Language to select
     */
    public void selectPrimaryLanguage(String language) {
        seleniumLib.scrollToElement(primaryLanguageDropdownBtn);
        seleniumLib.javascriptClick(primaryLanguageDropdownBtn);
        extentTest.info("Clicked on Primary Language dropdown");

        By languageOptionLocator = By.xpath("//div[@aria-label='Primary Language']//*[text()='" + language + "']");
        WebElement languageOption = webDriver.findElement(languageOptionLocator);
        seleniumLib.javascriptClick(languageOption);
        extentTest.info("Selected Primary Language: " + language);
    }

    /**
     * Selects a lead source from the dropdown.
     * @param leadSource Lead source to select
     */
    public void selectLeadSource(String leadSource) {
        seleniumLib.scrollToElement(leadSourceDropdownBtn);
        seleniumLib.javascriptClick(leadSourceDropdownBtn);
        extentTest.info("Clicked on Lead Source dropdown");

        By leadSourceOptionLocator = By.xpath("//label[text()='Lead Source']/..//div[@part='dropdown overlay']//span[text()='" + leadSource + "']");
        WebElement leadSourceOption = webDriver.findElement(leadSourceOptionLocator);
        seleniumLib.javascriptClick(leadSourceOption);
        extentTest.info("Selected Lead Source: " + leadSource);
    }

    /**
     * Clicks the Save button on the page.
     */
    public void clickSaveButton() {
        common.clickSaveButton();
    }

    /**
     * Checks if the contact name is displayed as expected.
     * @param entityLabel Label of the entity
     * @param expectedContactName Expected contact name
     * @return true if contact name is displayed, false otherwise
     */
    public boolean isContactNameDisplayed(String entityLabel, String expectedContactName) {
        String actualContactName = common.getPrimaryFieldText(entityLabel);
        extentTest.info("Expected contact name: " + expectedContactName + ", Actual contact name: " + actualContactName);
        return actualContactName.contains(expectedContactName);
    }
}