package testcases;

import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import util.JsonUtil;

import java.util.Map;

/**
 * Test class for verifying product selection and editing in Quotes.
 */
public class QuoteProductsTest extends BaseTest {

    /**
     * Navigates to the Quotes page before each test.
     */
    @BeforeMethod
    public void navigateToQuotesPage() {
        extentTest = extentReports.createTest("Navigate to Quotes Page", "Test to navigate to the Quotes page from Home");
        HomePage homePage = new HomePage();
        SearchPage searchPage = new SearchPage();

        // Retrieve test data for search and quote pages
        Map<String, Object> searchPageData = (Map<String, Object>) testData.get("searchPage");
        Map<String, Object> quoteData = (Map<String, Object>) testData.get("quote");
        String accountName = JsonUtil.readValueByKeyFromJsonFile(uniqueTestDataFilePath, "accountName");

        homePage.clickHomeTab();
        homePage.verifyHomePageIsDisplayed();
        homePage.searchAndOpen(accountName);

        // Navigate to Quotes page from search results
        searchPage.clickLinkFromSearchResults(searchPageData.get("TH_Quotes").toString(), null);
        assert searchPage.isPageDisplayed(quoteData.get("entityLabel").toString()) : "Quotes page is not displayed";
        extentTest.pass("Navigated to Quotes page successfully.");
    }

    /**
     * Test to verify product selection and editing in a Quote.
     */
    @Test
    public void selectAndEditProductsTest() throws InterruptedException {
        extentTest = extentReports.createTest("Select and Edit Products Test", "Test to select and edit products in a Quote");

        // Retrieve product and quantity data from test data
        Map<String, Object> productsData = (Map<String, Object>) testData.get("products");
        String productReviewMonitoring = productsData.get("product_Review_Monitoring").toString();
        String productReviewResponse = productsData.get("product_Review_Response").toString();
        String productReviewGeneration = productsData.get("product_Review_Generation").toString();
        String quantityReviewMonitoring = productsData.get("quantity_Review_Monitoring").toString();
        String quantityReviewResponse = productsData.get("quantity_Review_Response").toString();
        String quantityReviewGeneration = productsData.get("quantity_Review_Generation").toString();

        QuotePage quotePage = new QuotePage();
        ProductSelection productSelection = new ProductSelection();

        // Begin editing products in the quote
        quotePage.clickEditLinesButton();

        // Add products to the quote
        quotePage.clickAddProductsButton();

        // Select products to add
        productSelection.selectProductByName(productReviewMonitoring);
        productSelection.selectProductByName(productReviewResponse);
        productSelection.selectProductByName(productReviewGeneration);
        productSelection.clickSelectButton();

        // Edit product quantities
        quotePage.editProductQuantity(productReviewMonitoring, quantityReviewMonitoring, true);
        quotePage.editProductQuantity(productReviewResponse, quantityReviewResponse, true);
        quotePage.editProductQuantity(productReviewGeneration, quantityReviewGeneration, false);

        // Save changes and close alert
        quotePage.clickSaveButtonInIframe();
        quotePage.clickCloseButtonInAlert();

        extentTest.pass("Products selected and quantities edited successfully.");
    }
}