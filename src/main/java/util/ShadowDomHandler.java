package util;

import base.BaseTest;
import base.SeleniumLib;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


/**
 * Utility class for handling interactions with Shadow DOM elements using Selenium WebDriver.
 */
public class ShadowDomHandler extends BaseTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private SeleniumLib seleniumLib;

    /**
     * Constructor to initialize ShadowDomHandler.
     *
     * @param driver           Selenium WebDriver instance
     * @param timeoutInSeconds Timeout for explicit waits in seconds
     */
    public ShadowDomHandler(WebDriver driver, int timeoutInSeconds) {
        this.driver = driver;
        this.seleniumLib = new SeleniumLib();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    /**
     * Selects a product by its name in a Shadow DOM-based product lookup table.
     *
     * @param iDriver     Selenium WebDriver instance
     * @param productName Name of the product to select
     */
    public void selectProductByName(WebDriver iDriver, String productName) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) iDriver;

        String script = "return document.querySelector('#sbPageContainer')"
                + ".shadowRoot.querySelector('sb-product-lookup')"
                + ".shadowRoot.querySelector('#lookupLayout')"
                + ".shadowRoot.querySelector('#tbody > #list > [name=\"" + productName + "\"]')"
                + ".shadowRoot.querySelector('sb-swipe-container #selection')"
                + ".shadowRoot.querySelector('sb-table-cell-select')"
                + ".shadowRoot.querySelector('#checkbox')"
                + ".shadowRoot.querySelector('#checkboxContainer')";

        // Click the checkbox for the specified product
        WebElement checkbox = (WebElement) jsExecutor.executeScript(script);
        if (checkbox != null) {
            seleniumLib.scrollToElement(checkbox);
            seleniumLib.javascriptClick(checkbox);
            extentTest.info("Product selected: " + productName);
        } else {
            extentTest.fail("Product not found: " + productName);
            throw new NoSuchElementException("Product not found: " + productName);
        }
    }


    /**
     * Edits the quantity of a product in a Shadow DOM-based table.
     *
     * @param driver        Selenium WebDriver instance
     * @param productName   Name of the product to edit
     * @param newQuantity   New quantity value to set
     * @param hoverRequired Whether hover is required before editing
     * @throws InterruptedException if thread sleep is interrupted
     */
    public void editProductQuantity(WebDriver driver, String productName, String newQuantity, boolean hoverRequired) throws InterruptedException {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        // Traverse Shadow DOM to reach the standard lines table
        WebElement pageContainer = driver.findElement(By.cssSelector("#sbPageContainer"));
        WebElement lineEditor = getShadowElement(driver, pageContainer, "sb-line-editor");
        WebElement groupLayout = getShadowElement(driver, lineEditor, "#groupLayout");
        WebElement group = getShadowElement(driver, groupLayout, "#Group_");
        WebElement standardLines = getShadowElement(driver, group, "#standardLines");

        // Get all product rows
        List<WebElement> productRows = (List<WebElement>) jsExecutor.executeScript(
                "return arguments[0].shadowRoot.querySelectorAll('sf-le-table-row')", standardLines);

        for (WebElement row : productRows) {
            WebElement productNameCell = getShadowElement(driver, row, "div[field='SBQQ__ProductName__c']");
            String foundProductName = productNameCell.getText().trim();

            if (foundProductName.equalsIgnoreCase(productName)) {
                if (hoverRequired) {
                    hoverAndFocusElement(driver, productNameCell);
                }
                // Click the pencil icon to edit quantity
                WebElement pencilIcon = getShadowElement(driver, row, "div[field='yext_Display_Quantity__c'] span.pencil");
                jsExecutor.executeScript("arguments[0].click();", pencilIcon);

                // Wait for the quantity input to appear inside sb-input
                WebElement quantityInput = new WebDriverWait(driver, Duration.ofSeconds(30))
                        .until(d -> {
                            WebElement sbInput = getShadowElement(d, row, "div[field='yext_Display_Quantity__c'] sb-input");
                            if (sbInput != null) {
                                WebElement input = null;
                                try {
                                    input = getShadowElement(d, sbInput, "iron-input > input");
                                } catch (Exception ignore) {
                                }
                                if (input == null) {
                                    try {
                                        input = getShadowElement(d, sbInput, "input");
                                    } catch (Exception ignore) {
                                    }
                                }
                                return input;
                            }
                            return null;
                        });

                // Update the quantity value
                quantityInput.clear();
                quantityInput.sendKeys(newQuantity);
                Thread.sleep(1000);
                quantityInput.sendKeys(Keys.ENTER);

                // Press Enter
                Thread.sleep(1000);
                productNameCell.click();
                System.out.println("Updated quantity for product: " + foundProductName);
                break;
            }
        }
        Thread.sleep(2000);
        System.out.println("Finished editing product quantity for: " + productName);
    }

    /**
     * Helper method to retrieve a child element inside a Shadow DOM root.
     *
     * @param driver   Selenium WebDriver instance
     * @param root     Root WebElement containing the shadow root
     * @param selector CSS selector for the child element
     * @return The found WebElement, or null if not found
     */
    public static WebElement getShadowElement(WebDriver driver, WebElement root, String selector) {
        return (WebElement) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].shadowRoot.querySelector(arguments[1])", root, selector);
    }

    /**
     * Performs hover and focus actions on a given element, with optional tabbing.
     *
     * @param driver  Selenium WebDriver instance
     * @param element WebElement to hover and focus
     * @throws InterruptedException if thread sleep is interrupted
     */
    private void hoverAndFocusElement(WebDriver driver, WebElement element) throws InterruptedException {

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        Actions actions = new Actions(driver);

        // Hover over the element
        actions.moveToElement(element).perform();
        Thread.sleep(1000);

        // Focus the element
        jsExecutor.executeScript("arguments[0].focus();", element);

        // Optionally, tab through elements for up to 6 seconds
        long endTime = System.currentTimeMillis() + 6_000;
        while (System.currentTimeMillis() < endTime) {
            jsExecutor.executeScript("arguments[0].focus();", element);
            for (int i = 0; i < 10; i++) {
                actions.sendKeys(Keys.TAB).perform();
                Thread.sleep(200);

                WebElement activeElement = driver.switchTo().activeElement();
                try {
                    actions.moveToElement(activeElement).perform();
                    Thread.sleep(200);
                } catch (Exception e) {
                    System.out.println("Could not hover on current element: " + e.getMessage());
                }
                Thread.sleep(300);
            }
        }
        System.out.println("Finished hover, focus, and tabbing.");
    }
}