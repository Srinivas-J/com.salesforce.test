package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import util.JsonUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;

/**
 * BaseTest class provides setup and teardown for WebDriver and reporting.
 */
public class BaseTest {

    protected static Properties configProperties;
    protected static WebDriver webDriver;

    protected static ExtentSparkReporter extentSparkReporter;
    protected static ExtentReports extentReports;
    protected static ExtentTest extentTest;

    protected static Map<String, Object> testData;
    protected static String uniqueTestDataFilePath;

    /**
     * Initializes the WebDriver based on the browser specified in config.
     */
    public void initializeWebDriver() {
        String browser = configProperties.getProperty("browser");
        if ("chrome".equalsIgnoreCase(browser)) {
            ChromeOptions options = new ChromeOptions();
            if (configProperties.getProperty("isIncognitoModeEnabled").equalsIgnoreCase("true")) {
                options.addArguments("--incognito");
                options.addArguments("profile.default_content_setting_values.geolocation", "2");
            }
            webDriver = new ChromeDriver(options);
        } else if ("firefox".equalsIgnoreCase(browser)) {
            webDriver = new org.openqa.selenium.firefox.FirefoxDriver();
        } else if ("edge".equalsIgnoreCase(browser)) {
            webDriver = new EdgeDriver();
        } else {
            throw new RuntimeException("Browser not supported: " + browser);
        }

        if (webDriver != null) {
            webDriver.get(configProperties.getProperty("url"));
            webDriver.manage().timeouts().pageLoadTimeout(
                    Duration.ofSeconds(Integer.parseInt(configProperties.getProperty("explicitWait"))));
            webDriver.manage().timeouts().implicitlyWait(
                    Duration.ofSeconds(Integer.parseInt(configProperties.getProperty("implicitWait"))));
            webDriver.manage().window().maximize();
            webDriver.manage().deleteAllCookies();
        }
    }

    /**
     * Loads configuration properties from the config file.
     */
    public void loadConfigProperties() {
        configProperties = new Properties();
        String configFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
                + File.separator + "resources" + File.separator + "config.properties";
        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            configProperties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the WebDriver and flushes the report.
     */
    public void tearDown() {
        if (extentReports != null) {
            extentReports.flush();
        }
        if (webDriver != null) {
           // webDriver.quit();
        }
    }

    /**
     * Initializes test data files from JSON.
     */
    public void initializeTestData() {
        testData = JsonUtil.readJsonFileToMap(System.getProperty("user.dir") + configProperties.getProperty("testDataFile"));
        uniqueTestDataFilePath = System.getProperty("user.dir") + configProperties.getProperty("uniqueTestDataFile");
    }

    /**
     * Sets up the test environment: loads config, initializes WebDriver, reporting, and test data.
     */
    public void setUp() {
        loadConfigProperties();
        initializeWebDriver();
        initializeExtentReport();
        initializeTestData();
    }

    /**
     * Initializes the Extent report for test reporting.
     */
    public void initializeExtentReport() {
        String reportPath = System.getProperty("user.dir")
                + configProperties.getProperty("reportPath")
                + configProperties.getProperty("reportFileName");
        extentSparkReporter = new ExtentSparkReporter(reportPath);
        extentSparkReporter.config().setReportName(configProperties.getProperty("reportName"));
        extentSparkReporter.config().setDocumentTitle(configProperties.getProperty("reportTitle"));
        extentSparkReporter.config().setTheme(Theme.valueOf(configProperties.getProperty("reportTheme").toUpperCase()));
        extentSparkReporter.config().setEncoding("utf-8");
        extentSparkReporter.config().setTimeStampFormat(configProperties.getProperty("timeStampFormat"));

        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Browser", configProperties.getProperty("browser"));
        extentReports.setSystemInfo("User", System.getProperty("user.name"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("Java VM Version", System.getProperty("java.vm.version"));
        extentReports.setSystemInfo("Test Environment", configProperties.getProperty("environment"));
    }
}