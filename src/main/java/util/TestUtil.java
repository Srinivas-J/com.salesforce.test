package util;

import base.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.time.Instant;
import java.util.UUID;

/**
 * Utility class for test-related helper methods.
 */
public class TestUtil extends BaseTest {

    /**
     * Generates a unique string using a prefix, a random UUID, and the current timestamp.
     *
     * @param prefix the prefix to prepend to the unique string
     * @return a unique string
     */
    public static String generateUniqueString(String prefix) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String timestamp = String.valueOf(Instant.now().toEpochMilli()).substring(8);
        return prefix + uuid.substring(0, 4) + timestamp;
    }

    /**
     * Captures a screenshot and saves it to the configured screenshot path.
     *
     * @return the path to the saved screenshot
     */
    public static String captureScreenshot() {
        String currentDate = String.valueOf(System.currentTimeMillis());
        // Assumes 'config' is a Properties object defined in BaseTest
        String screenshotDirectory = System.getProperty("user.dir")
                .concat(configProperties.getProperty("screenshotPath"));
        String screenshotPath = screenshotDirectory + currentDate + ".png";
        File screenshotFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

        // Assumes 'test' is an ExtentTest object defined in BaseTest
        extentTest.info("Taking screenshot: " + screenshotPath);
        try {
            File destinationFile = new File(screenshotPath);
            FileHandler.copy(screenshotFile, destinationFile);
            extentTest.info("Screenshot taken: " + screenshotPath);
        } catch (Exception e) {
            extentTest.fail("Failed to take screenshot: " + e.getMessage());
        }

        return screenshotPath;
    }
}