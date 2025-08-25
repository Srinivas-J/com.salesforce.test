package base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.Objects;

/**
 * Utility class for common Selenium operations.
 */
public class SeleniumLib extends BaseTest {

    /**
     * Waits for the given element to be visible on the page.
     * @param element WebElement to wait for
     */
    public void waitForElementToBeVisible(WebElement element) {
        getWait(null).until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for the element located by the given By locator to be visible.
     * @param by By locator
     * @return WebElement if found, otherwise null
     */
    public WebElement waitForElementToBeVisible(By by) {
        try {
            getWait(null).until(ExpectedConditions.visibilityOfElementLocated(by));
            return webDriver.findElement(by);
        } catch (Exception e) {
            extentTest.warning("Exception in waitForElementToBeVisible: " + e.getMessage());
            return null;
        }
    }

    /**
     * Returns a FluentWait instance with the specified timeout.
     * @param maxTime Maximum wait time in seconds (if null, uses default from config)
     * @return Wait<WebDriver>
     */
    private Wait<WebDriver> getWait(Integer maxTime) {
        int timeout = (maxTime == null) ? Integer.parseInt(configProperties.getProperty("maxWait")) : maxTime;
        return new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofMillis(1000))
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementClickInterceptedException.class)
                .ignoring(InvalidElementStateException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    /**
     * Returns the JavascriptExecutor instance.
     * @return JavascriptExecutor
     */
    public JavascriptExecutor getJavascriptExecutor() {
        return (JavascriptExecutor) webDriver;
    }

    /**
     * Scrolls the page to bring the given element into view.
     * @param element WebElement to scroll to
     */
    public void scrollToElement(WebElement element) {
        getJavascriptExecutor().executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    /**
     * Performs a JavaScript click on the given element.
     * @param element WebElement to click
     */
    public void javascriptClick(WebElement element) {
        try {
            getJavascriptExecutor().executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            extentTest.fail("Error performing JavaScript click: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Sets the value of the given element using JavaScript.
     * @param element WebElement to set value for
     * @param value Value to set
     */
    public void javascriptType(WebElement element, String value) {
        try {
            getJavascriptExecutor().executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
        } catch (Exception e) {
            extentTest.fail("Error setting value using JavaScript: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Waits for the element located by the given By locator to become invisible.
     * @param by By locator
     */
    public void waitForElementToBeInvisible(By by) {
        try {
            int maxTime = Integer.parseInt(configProperties.getProperty("maxWait"));
            getWait(maxTime).until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (Exception e) {
            extentTest.warning("Exception in waitForElementToBeInvisible: " + e.getMessage());
        }
    }

    /**
     * Waits for the given element to be clickable.
     * @param element WebElement to wait for
     * @return WebElement if clickable, otherwise null
     */
    public WebElement waitForElementToBeClickable(WebElement element) {
        try {
            return getWait(null).until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            extentTest.warning("Exception in waitForElementToBeClickable: " + e.getMessage());
            return null;
        }
    }

    /**
     * Waits for the page to be fully loaded.
     */
    public void waitForPageToLoad() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            ExpectedCondition<Boolean> jsLoad = webDriver -> Objects.requireNonNull(
                    ((JavascriptExecutor) webDriver).executeScript("return document.readyState"))
                    .toString().equals("complete");

            boolean jsReady = Objects.requireNonNull(js.executeScript("return document.readyState"))
                    .toString().equals("complete");
            if (!jsReady) {
                getWait(null).until(jsLoad);
            } else {
                extentTest.info("Page is already loaded.");
            }
        } catch (NoSuchWindowException nsw) {
            extentTest.warning(nsw.getMessage());
        } catch (Exception e) {
            extentTest.warning("Exception while waiting for the page to load: " + e.getMessage());
        }
    }
}