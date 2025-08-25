package pages;

import base.BaseTest;
import base.Common;
import base.SeleniumLib;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.ShadowDomHandler;

import java.time.Duration;

/**
 * Page Object Model for the Quote Page.
 * Encapsulates all interactions and verifications for the Quote page.
 */
public class QuotePage extends BaseTest {

    private Common common;
    private SeleniumLib seleniumLib;

    public QuotePage() {
        super();
        PageFactory.initElements(webDriver, this);
        this.seleniumLib = new SeleniumLib();
        this.common = new Common(seleniumLib);
    }

    // WebElements
    @FindBy(xpath = "//div[contains(@class,'modal-footer')]//button[text()='Proceed']")
    private WebElement proceedButton;

    @FindBy(xpath = "//input[@name='SBQQ__StartDate__c']")
    private WebElement startDateInput;

    @FindBy(xpath = "//label[text()='Sold To Contact']//following-sibling::div//input[@role='combobox']")
    private WebElement soldToContactInput;

    @FindBy(xpath = "//label[text()='Bill To Contact']//following-sibling::div//input[@role='combobox']")
    private WebElement billToContactInput;

    @FindBy(xpath = "//input[@name='SBQQ__SubscriptionTerm__c']")
    private WebElement subscriptionTermInput;

    @FindBy(xpath = "//*[@aria-label='Payment Options']//span[@part='input-button-value']")
    private WebElement paymentOptionsDropdown;

    @FindBy(xpath = "//*[@aria-label='Payment Terms']//span[@part='input-button-value']")
    private WebElement paymentTermsDropdown;

    @FindBy(xpath = "//*[@aria-label='Billing Period']//span[@part='input-button-value']")
    private WebElement billingPeriodDropdown;

    @FindBy(xpath = "//ul[@class='slds-button-group-list']//*[@title='Edit Lines']//button")
    private WebElement editLinesButton;

    /**
     * Clicks the Proceed button on the Quote page.
     */
    public void clickProceedButton() {
        common.waitForLoadingSpinnerToBeInvisible();
        seleniumLib.javascriptClick(proceedButton);
        extentTest.info("Clicked on Proceed button");
    }

    /**
     * Verifies the header text of the quote dialog.
     * @param expectedHeader Expected header text.
     * @return true if header matches, false otherwise.
     */
    public boolean isQuoteDialogHeaderTextCorrect(String expectedHeader) {
        String actualHeader = common.getActionBodyHeaderText();
        extentTest.info("Expected header: " + expectedHeader + ", Actual header: " + actualHeader);
        return actualHeader.equals(expectedHeader);
    }

    /**
     * Types the start date in the Start Date input field.
     * @param startDate The start date to enter.
     */
    public void enterStartDate(String startDate) {
        seleniumLib.scrollToElement(startDateInput);
        startDateInput.sendKeys(startDate);
        extentTest.info("Typed Start Date: " + startDate);
    }

    /**
     * Selects a Sold To Contact by name.
     * @param contactName The contact name to select.
     */
    public void selectSoldToContact(String contactName) {
        seleniumLib.javascriptClick(soldToContactInput);
        seleniumLib.javascriptType(soldToContactInput, contactName);

        By contactOptionLocator = By.xpath("//label[text()='Sold To Contact']/..//div[@part='dropdown overlay']//span[contains(text(),'" + contactName + "')]");
        WebElement contactOption = webDriver.findElement(contactOptionLocator);
        seleniumLib.javascriptClick(contactOption);
        extentTest.info("Selected Sold To Contact: " + contactName);
    }

    /**
     * Selects a Bill To Contact by name.
     * @param contactName The contact name to select.
     */
    public void selectBillToContact(String contactName) {
        seleniumLib.javascriptClick(billToContactInput);
        seleniumLib.javascriptType(billToContactInput, contactName);

        By contactOptionLocator = By.xpath("//label[text()='Bill To Contact']/..//div[@part='dropdown overlay']//span[contains(text(),'" + contactName + "')]");
        WebElement contactOption = webDriver.findElement(contactOptionLocator);
        seleniumLib.javascriptClick(contactOption);
        extentTest.info("Selected Bill To Contact: " + contactName);
    }

    /**
     * Types the subscription term.
     * @param term The subscription term to enter.
     */
    public void enterSubscriptionTerm(String term) {
        seleniumLib.scrollToElement(subscriptionTermInput);
        subscriptionTermInput.sendKeys(term);
        extentTest.info("Typed Subscription Term: " + term);
    }

    /**
     * Selects a payment option from the dropdown.
     * @param option The payment option to select.
     */
    public void selectPaymentOption(String option) {
        seleniumLib.javascriptClick(paymentOptionsDropdown);
        By optionLocator = By.xpath("//label[text()='Payment Options']/..//div[@part='dropdown overlay']//span[text()='" + option + "']");
        WebElement paymentOption = webDriver.findElement(optionLocator);
        seleniumLib.javascriptClick(paymentOption);
        extentTest.info("Selected Payment Option: " + option);
    }

    /**
     * Selects payment terms from the dropdown.
     * @param terms The payment terms to select.
     */
    public void selectPaymentTerms(String terms) {
        seleniumLib.javascriptClick(paymentTermsDropdown);
        By termsLocator = By.xpath("//label[text()='Payment Terms']/..//div[@part='dropdown overlay']//span[text()='" + terms + "']");
        WebElement paymentTermsOption = webDriver.findElement(termsLocator);
        seleniumLib.javascriptClick(paymentTermsOption);
        extentTest.info("Selected Payment Terms: " + terms);
    }

    /**
     * Selects a billing period from the dropdown.
     * @param period The billing period to select.
     */
    public void selectBillingPeriod(String period) {
        seleniumLib.javascriptClick(billingPeriodDropdown);
        By periodLocator = By.xpath("//label[text()='Billing Period']/..//div[@part='dropdown overlay']//span[text()='" + period + "']");
        WebElement billingPeriodOption = webDriver.findElement(periodLocator);
        seleniumLib.javascriptClick(billingPeriodOption);
        extentTest.info("Selected Billing Period: " + period);
    }

    /**
     * Clicks the Save button using the common utility.
     */
    public void clickSave() {
        common.clickSaveButton();
    }

    /**
     * Clicks the Edit Lines button.
     */
    public void clickEditLinesButton() {
        seleniumLib.javascriptClick(editLinesButton);
        extentTest.info("Clicked on Edit Lines button");
    }

    /**
     * Checks if the quote name is displayed and contains the opportunity name.
     * @param entityLabel The entity label.
     * @param opportunityName The opportunity name.
     * @return true if quote name contains opportunity name, false otherwise.
     */
    public boolean isQuoteNameDisplayed(String entityLabel, String opportunityName) {
        String primaryFieldText = common.getPrimaryFieldText(entityLabel);
        extentTest.info("Quote primary field text: " + primaryFieldText);
        extentTest.info("Checking if Quote Name contains Opportunity Name: " + opportunityName);
        return primaryFieldText.contains(opportunityName);
    }

    //****************** Add/Edit Products Shadow DOM Methods **********************//

    /**
     * Clicks the Add Products button inside the iframe.
     */
    public void clickAddProductsButton() {
        common.waitForLoadingSpinnerToBeInvisible();
        common.switchToAccessibilityTitleIframe();
        getAddProductsButton().click();
        extentTest.info("Clicked on Add Products button");
        webDriver.switchTo().defaultContent();
    }

    /**
     * Edits the quantity for a given product.
     * @param productName Product name.
     * @param quantity Quantity to set.
     * @param isHoverRequired Whether hover is required.
     */
    public void editProductQuantity(String productName, String quantity, boolean isHoverRequired) throws InterruptedException {
        WebDriver iframeDriver = common.switchToAccessibilityTitleIframe();
        ShadowDomHandler shadowDomHandler = new ShadowDomHandler(iframeDriver, 60);
        shadowDomHandler.editProductQuantity(iframeDriver, productName, quantity, isHoverRequired);
        extentTest.info("Edited product quantity for " + productName + " to " + quantity);
        iframeDriver.switchTo().defaultContent();
    }

    /**
     * Clicks the Save button inside the iframe.
     */
    public void clickSaveButtonInIframe() throws InterruptedException {
        WebDriver iframeDriver = common.switchToAccessibilityTitleIframe();
        WebDriverWait wait = new WebDriverWait(iframeDriver, Duration.ofSeconds(60));
        wait.until(d -> getSaveButton() != null);
        getSaveButton().click();
        if(getErrorInfoCloseButton() != null) {
            seleniumLib.javascriptClick(getErrorInfoCloseButton());
            getSaveButton().click();
        }
        extentTest.info("Clicked on Save button to save the added/edited product(s)");
        webDriver.switchTo().defaultContent();
    }

    /**
     * Clicks the Close button in the alert dialog inside the iframe.
     */
    public void clickCloseButtonInAlert() throws InterruptedException {
        WebDriver iframeDriver = common.switchToAccessibilityTitleIframe();
        seleniumLib.waitForPageToLoad();
        seleniumLib.javascriptClick(getCloseButtonInAlert());
        extentTest.info("Clicked on Close button in the alert dialog");
        common.waitForLoadingSpinnerToBeInvisible();
        iframeDriver.switchTo().defaultContent();
    }

    //***************** Private Helper Methods **********************//

    /**
     * Gets the Close button in the alert dialog using JavaScript.
     * @return The Close button WebElement.
     */
    private WebElement getCloseButtonInAlert() {
        String script =
                "return document.querySelector('#sbPageContainer')"
                        + ".shadowRoot.querySelector('sb-line-editor')"
                        + ".shadowRoot.querySelector('#productAlertModal')"
                        + ".shadowRoot.querySelector('#dialog')"
                        + ".shadowRoot.querySelector('paper-button');";
        return (WebElement) ((JavascriptExecutor) webDriver).executeScript(script);
    }

    /**
     * Gets the Save button inside the iframe using JavaScript.
     * @return The Save button WebElement.
     */
    private WebElement getSaveButton() {
        String script = "return document.querySelector('#sbPageContainer')"
                + ".shadowRoot.querySelector('sb-line-editor')"
                + ".shadowRoot.querySelector('#lineEditorPageHeader > #actions sb-custom-action[name=\"Save\"]')"
                + ".shadowRoot.querySelector('#mainButton');";
        return (WebElement) ((JavascriptExecutor) webDriver).executeScript(script);
    }

    /**
     * Gets the Add Products button inside the iframe using JavaScript.
     * @return The Add Products button WebElement.
     */
    private WebElement getAddProductsButton() {
        String script =
                "return document.querySelector('#sbPageContainer')" +
                        ".shadowRoot.querySelector('#content > sb-line-editor')" +
                        ".shadowRoot.querySelector('#actions > sb-custom-action[name=\"Add Products\"]')" +
                        ".shadowRoot.querySelector('#mainButton')";
        return (WebElement) ((JavascriptExecutor) webDriver).executeScript(script);
    }

    private WebElement getErrorInfoCloseButton() {
        String script = "return document.querySelector('#sbPageContainer')"
                + ".shadowRoot.querySelector('#content > sb-line-editor')"
                + ".shadowRoot.querySelector('#messages > sb-toast')"
                + ".shadowRoot.querySelector('#error_0 > button > i');";
        return (WebElement) ((JavascriptExecutor) webDriver).executeScript(script);
    }

}