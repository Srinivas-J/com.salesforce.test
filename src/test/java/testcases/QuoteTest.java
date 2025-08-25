package testcases;

import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.OpportunitiesPage;
import pages.QuotePage;
import pages.SearchPage;
import util.DateUtil;
import util.JsonUtil;

import java.util.Map;

/**
 * Test class for Quote related test cases.
 */
public class QuoteTest extends BaseTest {

    /**
     * Navigates to the Quote page before each test.
     * Uncomment @BeforeMethod to enable this as a setup method.
     */
    // @BeforeMethod
    public void navigateToQuotePage() {
        extentTest = extentReports.createTest("Navigate to Quote Page", "Test to navigate to the Quotes page");

        HomePage homePage = new HomePage();
        SearchPage searchPage = new SearchPage();
        Map<String, Object> searchPageTestData = (Map<String, Object>) testData.get("searchPage");
        Map<String, Object> quoteTestData = (Map<String, Object>) testData.get("quote");
        String accountName = JsonUtil.readValueByKeyFromJsonFile(uniqueTestDataFilePath, "accountName");

        homePage.verifyHomePageIsDisplayed();
        homePage.searchAndOpen(accountName);
        searchPage.clickLinkFromSearchResults(searchPageTestData.get("TH_Quotes").toString(), null);
        assert searchPage.isPageDisplayed(quoteTestData.get("entityLabel").toString()) : "Quotes page is not displayed";
    }

    /**
     * Test to verify quote creation functionality.
     */
    @Test
    public void createQuoteTest() {
        extentTest = extentReports.createTest("Create Quote Test", "Test to verify quote creation functionality");

        Map<String, Object> quoteTestData = (Map<String, Object>) testData.get("quote");
        String accountName = JsonUtil.readValueByKeyFromJsonFile(uniqueTestDataFilePath, "accountName");
        String firstName = JsonUtil.readValueByKeyFromJsonFile(uniqueTestDataFilePath, "firstName");
        String lastName = JsonUtil.readValueByKeyFromJsonFile(uniqueTestDataFilePath, "lastName");
        String contactName = firstName + " " + lastName;

        String headerText = quoteTestData.get("h2_NewQuote").toString();
        String recordType = quoteTestData.get("recordType").toString();

        // Determine start date
        String startDate;
        boolean isStartDateEmpty = quoteTestData.get("startDate") == null || quoteTestData.get("startDate").toString().isEmpty();
        if (isStartDateEmpty) {
            startDate = new DateUtil().getCurrentDateFormatted(configProperties.getProperty("dateFormat"));
        } else {
            startDate = quoteTestData.get("startDate").toString();
        }

        QuotePage quotePage = new QuotePage();
        OpportunitiesPage opportunitiesPage = new OpportunitiesPage();

        // Steps to create a new quote
        opportunitiesPage.clickCreateQuoteButton();
        quotePage.clickProceedButton();
        quotePage.isQuoteDialogHeaderTextCorrect(headerText + ": " + recordType);
        quotePage.enterStartDate(startDate);
        quotePage.selectSoldToContact(contactName);
        quotePage.selectBillToContact(contactName);
        quotePage.enterSubscriptionTerm(quoteTestData.get("term").toString());
        quotePage.selectPaymentOption(quoteTestData.get("paymentOption").toString());
        quotePage.selectPaymentTerms(quoteTestData.get("paymentTerms").toString());
        quotePage.selectBillingPeriod(quoteTestData.get("billingPeriod").toString());
        quotePage.clickSave();

        // Assertion to verify quote creation
        assert quotePage.isQuoteNameDisplayed(quoteTestData.get("entityLabel").toString(), accountName)
                : "Quote account name is not displayed after creation: " + accountName;
        extentTest.info("Quote created successfully for Account: " + accountName + ", Contact: " + contactName);
        extentTest.pass("Quote created successfully for Account: " + accountName);
    }
}