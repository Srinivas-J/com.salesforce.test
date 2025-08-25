package testcases;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.OpportunitiesPage;
import util.DateUtil;
import util.JsonUtil;

import java.util.Map;

/**
 * Test class for Opportunity related test cases.
 */
public class OpportunitiesTest extends BaseTest {

    /**
     * Test to verify the opportunity creation functionality.
     */
    @Test
    public void createOpportunityTest() {
        extentTest = extentReports.createTest(
                "Create Opportunity Test",
                "Test to verify opportunity creation functionality"
        );

        // Retrieve test data for opportunities
        Map<String, Object> opportunityData = (Map<String, Object>) testData.get("opportunities");
        String accountName = JsonUtil.readValueByKeyFromJsonFile(uniqueTestDataFilePath, "accountName");
        String opportunityName = JsonUtil.readValueByKeyFromJsonFile(uniqueTestDataFilePath, "opportunityName");

        String headerText = opportunityData.get("h2_NewOpportunity").toString();
        String recordType = opportunityData.get("recordType").toString();

        // Determine close date
        String closeDate;
        int monthsToAdd = Integer.parseInt(configProperties.getProperty("monthsToAddToCurrentDate"));
        boolean isCloseDateEmpty = opportunityData.get("closeDate") == null
                || opportunityData.get("closeDate").toString().isEmpty();
        if (isCloseDateEmpty) {
            closeDate = new DateUtil().getDateAfterAddingMonths(
                    configProperties.getProperty("dateFormat"),
                    monthsToAdd
            );
        } else {
            closeDate = opportunityData.get("closeDate").toString();
        }

        // Interact with Opportunities page
        OpportunitiesPage opportunitiesPage = new OpportunitiesPage();
        opportunitiesPage.waitUntilOpportunitiesTabVisible();
        opportunitiesPage.clickNewOpportunityLinkFromDropdown();
        opportunitiesPage.verifyOpportunitiesDialogHeaderText(headerText);
        opportunitiesPage.selectOpportunityRecordType(recordType);
        opportunitiesPage.clickNextButton();

        opportunitiesPage.verifyOpportunitiesDialogHeaderText(headerText + ": " + recordType);
        opportunitiesPage.typeOpportunityName(opportunityName);
        opportunitiesPage.selectOpportunityCurrency(opportunityData.get("opportunityCurrency").toString());
        opportunitiesPage.selectStage(opportunityData.get("stage").toString());
        opportunitiesPage.chooseAccountName(accountName);
        opportunitiesPage.selectDealType(opportunityData.get("dealType").toString());
        opportunitiesPage.selectRenewal(opportunityData.get("renewal").toString());
        opportunitiesPage.typeCloseDate(closeDate);
        opportunitiesPage.selectQuotePricingModel(opportunityData.get("pricingModel").toString());
        opportunitiesPage.clickSaveButton();

        // Assert and log result
        boolean isOpportunityDisplayed = opportunitiesPage.isOpportunityNameDisplayed(
                opportunityData.get("entityLabel").toString(),
                accountName
        );
        assert isOpportunityDisplayed : "Opportunity account name is not displayed after creation: " + accountName;
        extentTest.pass("Opportunity created successfully for Account: " + accountName);
    }
}