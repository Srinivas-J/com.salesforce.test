package testcases;

import base.BaseTest;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * TestSetup class handles the setup and teardown for the test suite.
 */
public class TestSetup extends BaseTest {

    /**
     * Initializes resources before the entire test suite runs.
     */
    @BeforeSuite
    public void setUpSuite() {
        setUp();
    }

    /**
     * Cleans up resources after the entire test suite has run.
     */
    @AfterSuite
    public void tearDownSuite() throws IOException {
        tearDown();

        try {
            String reportHtmlFile = System.getProperty("user.dir") + configProperties.getProperty("reportPath") + configProperties.getProperty("reportFileName");
            File htmlFile = new File(reportHtmlFile);
            if (htmlFile.exists()) {
                Desktop.getDesktop().browse(htmlFile.toURI());
            } else {
                System.out.println("Extent Report not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}