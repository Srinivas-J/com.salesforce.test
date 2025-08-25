package testcases;

import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AccountsPage;
import util.JsonUtil;
import util.TestUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Test class for Accounts related test cases.
 */
public class AccountsTest extends BaseTest {

    /**
     * Generates unique test data before each test method.
     */
    @BeforeMethod
    public void generateUniqueTestData() {
        extentTest = extentReports.createTest(
                "Generate Unique Test Data",
                "Test to generate unique test data for Accounts"
        );

        // Generate unique test data
        String accountName = TestUtil.generateUniqueString("AN");
        String firstName = TestUtil.generateUniqueString("FN");
        String lastName = TestUtil.generateUniqueString("LN");
        String opportunityName = TestUtil.generateUniqueString("ON");
        String email = accountName + "@yext.com";

        // Prepare test data map
        Map<String, String> testDataMap = new HashMap<>();
        testDataMap.put("accountName", accountName);
        testDataMap.put("firstName", firstName);
        testDataMap.put("lastName", lastName);
        testDataMap.put("contactName", firstName + " " + lastName);
        testDataMap.put("opportunityName", opportunityName);
        testDataMap.put("email", email);

        // Write test data to JSON file
        JsonUtil.writeMapToJsonFile(uniqueTestDataFilePath, testDataMap);

        extentTest.pass("Unique test data generated and written to file: " + uniqueTestDataFilePath);
    }

    /**
     * Test to verify account creation functionality.
     */
    @Test
    public void createAccountTest() {
        extentTest = extentReports.createTest(
                "Create Account Test",
                "Test to verify account creation functionality"
        );

        // Retrieve test data for accounts
        Map<String, Object> accountsTestData = (Map<String, Object>) testData.get("accounts");
        String accountName = JsonUtil.readValueByKeyFromJsonFile(uniqueTestDataFilePath, "accountName");
        String headerText = accountsTestData.get("h2_NewAccount").toString();
        String recordType = accountsTestData.get("recordType").toString();

        AccountsPage accountsPage = new AccountsPage();

        // Perform account creation steps
        accountsPage.waitUntilAccountsTabIsVisible();
        accountsPage.clickNewAccountFromDropdown();
        accountsPage.isAccountsDialogHeaderTextCorrect(headerText);
        accountsPage.selectAccountsRecordType(recordType);
        accountsPage.clickNext();

        accountsPage.isAccountsDialogHeaderTextCorrect(headerText + ": " + recordType);
        accountsPage.enterAccountName(accountName);
        accountsPage.selectAccountCurrency(accountsTestData.get("accountCurrency").toString());
        accountsPage.selectAccountSource(accountsTestData.get("accountSource").toString());
        accountsPage.clickSave();

        // Assert account creation
        assert accountsPage.isAccountNameDisplayed(
                accountsTestData.get("entityLabel").toString(),
                accountName
        ) : "Account name is not displayed after creation: " + accountName;

        extentTest.pass("Account created successfully with name: " + accountName);
    }
}