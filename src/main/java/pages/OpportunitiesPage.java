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
 * Page Object Model for Opportunities Page.
 */
public class OpportunitiesPage extends BaseTest {

    private Common common;
    private SeleniumLib seleniumLib;

    public OpportunitiesPage() {
        super();
        PageFactory.initElements(webDriver, this);
        this.seleniumLib = new SeleniumLib();
        this.common = new Common(seleniumLib);
    }

    // WebElements
    @FindBy(xpath = "//one-app-nav-bar-item-root[@data-id='Opportunity']/a")
    private WebElement opportunitiesTab;

    @FindBy(xpath = "//one-app-nav-bar-item-root[@data-id='Opportunity']//a[@role='button']")
    private WebElement opportunitiesTabDropdownButton;

    @FindBy(xpath = "//div[@class='menuItemsWrapper']//span[text()='New Opportunity']")
    private WebElement newOpportunityLink;

    @FindBy(xpath = "//input[@name='Name']")
    private WebElement opportunityNameInput;

    @FindBy(xpath = "//*[@aria-label='Stage']//span[@part='input-button-value']")
    private WebElement stageDropdown;

    @FindBy(xpath = "//label[text()='Account Name']//following-sibling::div//input[@role='combobox']")
    private WebElement accountNameSearchInput;

    @FindBy(xpath = "//*[@aria-label='Deal Type']//span[@part='input-button-value']")
    private WebElement dealTypeDropdown;

    @FindBy(xpath = "//*[@aria-label='Opportunity Currency']//span[@part='input-button-value']")
    private WebElement opportunityCurrencyDropdown;

    @FindBy(xpath = "//*[@aria-label='Renewal?']//span[@part='input-button-value']")
    private WebElement renewalDropdown;

    @FindBy(xpath = "//input[@name='CloseDate']")
    private WebElement closeDateInput;

    @FindBy(xpath = "//*[@aria-label='Quote Pricing Model']//span[@part='input-button-value']")
    private WebElement quotePricingModelDropdown;

    @FindBy(xpath = "//input[@name='Opportunity-search-input']")
    private WebElement opportunitySearchInput;

    @FindBy(xpath = "//ul[@class='slds-button-group-list']//*[@title='Create Quote']//button")
    private WebElement createQuoteButton;

    @FindBy(xpath = "//slot[contains(text(),'Quotes')]/../parent::a[@id='window']")
    private WebElement quotesLink;

    @FindBy(xpath = "//*[@data-label='Quote Number']//a[@id='window']")
    private WebElement quoteNumberLink;

    /**
     * Clicks the Opportunities tab.
     */
    public void clickOpportunitiesTab() {
        seleniumLib.javascriptClick(opportunitiesTab);
        extentTest.info("Clicked on Opportunities tab");
        common.waitForLoadingSpinnerToBeInvisible();
    }

    /**
     * Searches for an opportunity by name.
     * @param opportunityName Name of the opportunity to search.
     */
    public void searchOpportunity(String opportunityName) {
        seleniumLib.javascriptType(opportunitySearchInput, opportunityName);
        opportunitySearchInput.sendKeys(Keys.ENTER);
        extentTest.info("Searched for Opportunity: " + opportunityName);
        common.waitForLoadingSpinnerToBeInvisible();
    }

    /**
     * Waits until the Opportunities tab is visible.
     */
    public void waitUntilOpportunitiesTabVisible() {
        common.waitForLoadingSpinnerToBeInvisible();
        seleniumLib.waitForElementToBeVisible(opportunitiesTabDropdownButton);
        extentTest.info("Opportunities tab is now visible on the page.");
    }

    /**
     * Clicks the New Opportunity link from the dropdown.
     */
    public void clickNewOpportunityLinkFromDropdown() {
        opportunitiesTabDropdownButton.click();
        extentTest.info("Clicked on Opportunities tab dropdown button");
        seleniumLib.javascriptClick(newOpportunityLink);
        extentTest.info("Clicked on New Opportunity link from dropdown");
    }

    /**
     * Verifies the header text of the Opportunities dialog.
     * @param expectedHeader Expected header text.
     * @return true if header matches, false otherwise.
     */
    public boolean verifyOpportunitiesDialogHeaderText(String expectedHeader) {
        String actualHeader = common.getActionBodyHeaderText();
        extentTest.info("Expected header: " + expectedHeader + ", Actual header: " + actualHeader);
        return actualHeader.equals(expectedHeader);
    }

    /**
     * Selects the opportunity record type.
     * @param recordType Record type to select.
     */
    public void selectOpportunityRecordType(String recordType) {
        By recordTypeLocator = By.xpath("//div[@class='changeRecordTypeOptionRightColumn']/span[text()='" + recordType + "']/../../div/input[@type='radio']");
        WebElement recordTypeRadioButton = webDriver.findElement(recordTypeLocator);
        seleniumLib.javascriptClick(recordTypeRadioButton);
        extentTest.info("Selected record type: " + recordType);
    }

    /**
     * Clicks the Next button.
     */
    public void clickNextButton() {
        common.clickNextButton();
    }

    /**
     * Types the opportunity name.
     * @param opportunityName Name to type.
     */
    public void typeOpportunityName(String opportunityName) {
        seleniumLib.waitForElementToBeVisible(opportunityNameInput);
        opportunityNameInput.sendKeys(opportunityName);
        extentTest.info("Typed Opportunity Name: " + opportunityName);
    }

    /**
     * Selects the stage from the dropdown.
     * @param stageText Stage to select.
     */
    public void selectStage(String stageText) {
        seleniumLib.javascriptClick(stageDropdown);
        extentTest.info("Clicked on Stage dropdown");
        By stageOptionLocator = By.xpath("//label[text()='Stage']/..//div[@part='dropdown overlay']//span[text()='" + stageText + "']");
        WebElement stageOption = webDriver.findElement(stageOptionLocator);
        seleniumLib.javascriptClick(stageOption);
        extentTest.info("Selected stage: " + stageText);
    }

    /**
     * Chooses an account name from the dropdown.
     * @param accountName Account name to select.
     */
    public void chooseAccountName(String accountName) {
        seleniumLib.javascriptClick(accountNameSearchInput);
        seleniumLib.javascriptType(accountNameSearchInput, accountName);
        extentTest.info("Entered Account Name: " + accountName);
        By accountNameOptionLocator = By.xpath("//label[text()='Account Name']/..//div[@part='dropdown overlay']//span[text()='" + accountName + "']");
        WebElement accountNameOption = webDriver.findElement(accountNameOptionLocator);
        seleniumLib.javascriptClick(accountNameOption);
        extentTest.info("Selected Account Name from choose list");
    }

    /**
     * Selects the deal type from the dropdown.
     * @param dealType Deal type to select.
     */
    public void selectDealType(String dealType) {
        seleniumLib.javascriptClick(dealTypeDropdown);
        extentTest.info("Clicked on Deal Type dropdown");
        By dealTypeOptionLocator = By.xpath("//label[text()='Deal Type']/..//div[@part='dropdown overlay']//span[text()='" + dealType + "']");
        WebElement dealTypeOption = webDriver.findElement(dealTypeOptionLocator);
        seleniumLib.javascriptClick(dealTypeOption);
        extentTest.info("Selected Deal Type: " + dealType);
    }

    /**
     * Selects the opportunity currency.
     * @param currency Currency to select.
     */
    public void selectOpportunityCurrency(String currency) {
        seleniumLib.scrollToElement(opportunityCurrencyDropdown);
        opportunityCurrencyDropdown.click();
        By currencyListLocator = By.xpath("//*[@aria-label='Opportunity Currency']//span[@title='" + currency + "']");
        WebElement currencyElement = webDriver.findElement(currencyListLocator);
        seleniumLib.javascriptClick(currencyElement);
        extentTest.info("Selected Opportunity Currency: " + currency);
    }

    /**
     * Selects the renewal option.
     * @param renewalOption Renewal option to select.
     */
    public void selectRenewal(String renewalOption) {
        seleniumLib.scrollToElement(renewalDropdown);
        seleniumLib.javascriptClick(renewalDropdown);
        extentTest.info("Clicked on Renewal dropdown");
        By renewalOptionLocator = By.xpath("//label[text()='Renewal?']/..//div[@part='dropdown overlay']//span[text()='" + renewalOption + "']");
        WebElement renewalOptionElement = webDriver.findElement(renewalOptionLocator);
        seleniumLib.javascriptClick(renewalOptionElement);
        extentTest.info("Selected Renewal option: " + renewalOption);
    }

    /**
     * Types the close date.
     * @param closeDate Close date to type.
     */
    public void typeCloseDate(String closeDate) {
        seleniumLib.scrollToElement(closeDateInput);
        seleniumLib.waitForElementToBeVisible(closeDateInput);
        closeDateInput.sendKeys(closeDate);
        extentTest.info("Typed Close Date: " + closeDate);
    }

    /**
     * Selects the quote pricing model.
     * @param pricingModel Pricing model to select.
     */
    public void selectQuotePricingModel(String pricingModel) {
        seleniumLib.javascriptClick(quotePricingModelDropdown);
        extentTest.info("Clicked on Quote Pricing Model dropdown");
        By pricingModelOptionLocator = By.xpath("//label[text()='Quote Pricing Model']/..//div[@part='dropdown overlay']//span[text()='" + pricingModel + "']");
        WebElement pricingModelOption = webDriver.findElement(pricingModelOptionLocator);
        seleniumLib.javascriptClick(pricingModelOption);
        extentTest.info("Selected Quote Pricing Model: " + pricingModel);
    }

    /**
     * Clicks the Save button.
     */
    public void clickSaveButton() {
        common.clickSaveButton();
    }

    /**
     * Checks if the opportunity name is displayed.
     * @param entityLabel Label of the entity.
     * @param expectedName Expected opportunity name.
     * @return true if displayed, false otherwise.
     */
    public boolean isOpportunityNameDisplayed(String entityLabel, String expectedName) {
        String actualName = common.getPrimaryFieldText(entityLabel);
        extentTest.info("Expected Opportunity Name: " + expectedName + ", Actual Opportunity Name: " + actualName);
        return actualName.contains(expectedName);
    }

    /**
     * Checks if the searched opportunity name is displayed in the table.
     * @param opportunityName Opportunity name to check.
     * @return true if displayed, false otherwise.
     */
    public boolean isSearchedOpportunityNameDisplayed(String opportunityName) {
        By opportunityNameLocator = By.xpath("//th[@data-label='Opportunity Name']//span[contains(text(),'" + opportunityName + "')]");
        WebElement opportunityNameElement = webDriver.findElement(opportunityNameLocator);
        boolean isDisplayed = opportunityNameElement.isDisplayed();
        extentTest.info("Is Opportunity Name '" + opportunityName + "' displayed? " + isDisplayed);
        return isDisplayed;
    }

    /**
     * Clicks on the opportunity name from the searched table.
     * @param opportunityName Opportunity name to click.
     */
    public void clickOpportunityNameFromSearchedTable(String opportunityName) {
        boolean isSearched = isSearchedOpportunityNameDisplayed(opportunityName);
        common.waitForLoadingSpinnerToBeInvisible();
        if (isSearched) {
            By opportunityNameLocator = By.xpath("//th[@data-label='Opportunity Name']//span[contains(text(),'" + opportunityName + "')]");
            WebElement opportunityNameElement = webDriver.findElement(opportunityNameLocator);
            seleniumLib.javascriptClick(opportunityNameElement);
        }
    }

    /**
     * Clicks the Create Quote button.
     */
    public void clickCreateQuoteButton() {
        seleniumLib.javascriptClick(createQuoteButton);
        extentTest.info("Clicked on Create Quote button");
    }

    /**
     * Clicks the Quotes link.
     */
    public void clickQuotesLink() {
        seleniumLib.javascriptClick(quotesLink);
        extentTest.info("Clicked on Quotes link");
    }

    /**
     * Clicks the Quote Number link.
     */
    public void clickQuoteNumberLink() {
        seleniumLib.javascriptClick(quoteNumberLink);
        extentTest.info("Clicked on Quote Number link");
    }
}