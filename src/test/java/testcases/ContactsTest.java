package testcases;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.AccountsPage;
import pages.ContactsPage;
import util.JsonUtil;
import util.TestUtil;

import java.util.Map;

/**
 * Test class for verifying contact creation functionality.
 */
public class ContactsTest extends BaseTest {

    /**
     * Test to verify the creation of a new contact.
     */
    @Test
    public void createContactTest() {
        // Initialize the test report
        extentTest = extentReports.createTest("Create Contact Test", "Test to verify contact creation functionality");

        // Retrieve test data for contacts
        Map<String, Object> contactsTestData = (Map<String, Object>) testData.get("contacts");
        String accountName = JsonUtil.readValueByKeyFromJsonFile(uniqueTestDataFilePath, "accountName");
        String firstName = JsonUtil.readValueByKeyFromJsonFile(uniqueTestDataFilePath, "firstName");
        String lastName = JsonUtil.readValueByKeyFromJsonFile(uniqueTestDataFilePath, "lastName");
        String contactName = JsonUtil.readValueByKeyFromJsonFile(uniqueTestDataFilePath, "contactName");
        String email = JsonUtil.readValueByKeyFromJsonFile(uniqueTestDataFilePath, "email");

        // Create ContactsPage object and perform actions to create a new contact
        ContactsPage contactsPage = new ContactsPage();
        contactsPage.waitForContactsTabVisible();
        contactsPage.clickNewContactFromDropdown();
        contactsPage.isContactsDialogHeaderTextCorrect(contactsTestData.get("h2_NewAccount").toString());
        contactsPage.selectSalutation(contactsTestData.get("salutationText").toString());
        contactsPage.enterFirstName(firstName);
        contactsPage.enterLastName(lastName);
        contactsPage.selectAccountName(accountName);
        contactsPage.enterEmail(email);
        contactsPage.selectPrimaryLanguage(contactsTestData.get("language").toString());
        contactsPage.selectLeadSource(contactsTestData.get("leadSource").toString());
        contactsPage.clickSaveButton();

        // Assert that the contact name is displayed after creation
        assert contactsPage.isContactNameDisplayed(contactsTestData.get("entityLabel").toString(), lastName)
                : "Contact name is not displayed after creation: " + contactName;

        // Log the successful creation of the contact
        extentTest.info("Contact created successfully with Name: " + contactName + ", Account: " + accountName);
    }
}