package pages;

import base.BaseTest;
import base.Common;
import base.SeleniumLib;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object representing the Search Page and its actions.
 */
public class SearchPage extends BaseTest {

    private final Common common;
    private final SeleniumLib seleniumLib;

    /**
     * Initializes the SearchPage with required utilities.
     */
    public SearchPage() {
        super();
        PageFactory.initElements(webDriver, this);
        this.seleniumLib = new SeleniumLib();
        this.common = new Common(seleniumLib);
    }

    /**
     * Clicks the required link from the search results based on the table header and row number.
     *
     * @param tableHeader   The header text to identify the result section.
     * @param rowNumber     The row number of the result to click (1-based index). Defaults to 1 if null or empty.
     */
    public void clickLinkFromSearchResults(String tableHeader, String rowNumber) {
        common.waitForLoadingSpinnerToBeInvisible();
        String targetRow = (rowNumber == null || rowNumber.isEmpty()) ? "1" : rowNumber;

        By linkLocator = By.xpath("(//h2/a[text()='" + tableHeader + "']/ancestor::div[contains(@class,'resultsItem')]//th[@scope='row']//a)[" + targetRow + "]");
        WebElement linkElement = seleniumLib.waitForElementToBeVisible(linkLocator);

        if (linkElement != null) {
            seleniumLib.scrollToElement(linkElement);
            linkElement.click();
            extentTest.info("Clicked on link: " + tableHeader + " from search results row " + targetRow);
            common.waitForLoadingSpinnerToBeInvisible();
        } else {
            extentTest.fail("Link not found for: " + tableHeader + " in search results row " + targetRow);
            throw new RuntimeException("Link not found for: " + tableHeader + " in search results row " + targetRow);
        }
    }

    /**
     * Checks if the required page is displayed by verifying the presence of the page header.
     *
     * @param pageName The name of the page to verify.
     * @return true if the page is displayed, false otherwise.
     */
    public boolean isPageDisplayed(String pageName) {
        common.waitForLoadingSpinnerToBeInvisible();
        By pageHeaderLocator = By.xpath("//*[@slot='entityLabel' and text()='" + pageName + "']");
        WebElement pageHeader = seleniumLib.waitForElementToBeVisible(pageHeaderLocator);

        if (pageHeader != null && pageHeader.isDisplayed()) {
            extentTest.info(pageName + " page is displayed successfully.");
            return true;
        } else {
            extentTest.fail(pageName + " page is not displayed.");
            return false;
        }
    }
}