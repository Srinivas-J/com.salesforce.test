package pages;

import base.BaseTest;
import base.Common;
import base.SeleniumLib;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.ShadowDomHandler;

import java.time.Duration;

/**
 * Handles product selection actions on the product selection page.
 */
public class ProductSelection extends BaseTest {

    private Common common;
    private SeleniumLib seleniumLib;

    /**
     * Initializes page elements and utility classes.
     */
    public ProductSelection() {
        super();
        PageFactory.initElements(webDriver, this);
        this.seleniumLib = new SeleniumLib();
        this.common = new Common(seleniumLib);
    }

    /**
     * Selects a product by its name using Shadow DOM handler.
     *
     * @param productName the name of the product to select
     * @throws InterruptedException if thread is interrupted during wait
     */
    public void selectProductByName(String productName) throws InterruptedException {
        WebDriver iframeDriver = common.switchToAccessibilityTitleIframe();
        ShadowDomHandler shadowDomHandler = new ShadowDomHandler(iframeDriver, 30);
        shadowDomHandler.selectProductByName(iframeDriver, productName);
        extentTest.info("Selected product by name " + productName);
        iframeDriver.switchTo().defaultContent();
    }

    /**
     * Clicks the select button after ensuring it is available.
     *
     * @throws InterruptedException if thread is interrupted during wait
     */
    public void clickSelectButton() throws InterruptedException {
        WebDriver iframeDriver = common.switchToAccessibilityTitleIframe();
        findSelectButton(iframeDriver).click();
        extentTest.info("Clicked on Select button to save the selected product(s)");
        webDriver = iframeDriver.switchTo().defaultContent();
        common.waitForLoadingSpinnerToBeInvisible();
    }

    /**
     * Finds the select button inside the Shadow DOM.
     *
     * @param driver the WebDriver instance for the current iframe
     * @return the select button WebElement, or null if not found
     */
    public WebElement findSelectButton(WebDriver driver) {
        return (WebElement) ((JavascriptExecutor) driver).executeScript(
            "return document.querySelector('#sbPageContainer')" +
            ".shadowRoot.querySelector('#content > sb-product-lookup')" +
            ".shadowRoot.querySelector('#plSelect')"
        );
    }
}