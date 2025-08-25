package listeners;

import base.BaseTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import util.TestUtil;

/**
 * Custom listener for reporting test execution results using ExtentReports.
 */
public class CustomReportingListener extends BaseTest implements ITestListener {

    /**
     * Invoked each time before a test will be invoked.
     */
    @Override
    public void onTestStart(ITestResult testResult) {
        ITestListener.super.onTestStart(testResult);
    }

    /**
     * Invoked each time a test succeeds.
     */
    @Override
    public void onTestSuccess(ITestResult testResult) {
        ITestListener.super.onTestSuccess(testResult);
        String logMessage = "<b>" + testResult.getMethod().getMethodName() + " passed successfully." + "</b>";
        Markup markup = MarkupHelper.createLabel(logMessage, ExtentColor.GREEN);
        extentTest.log(Status.PASS, markup);
    }

    /**
     * Invoked each time a test fails.
     */
    @Override
    public void onTestFailure(ITestResult testResult) {
        ITestListener.super.onTestFailure(testResult);
        extentTest.log(Status.FAIL, "Test case failed: " + testResult.getMethod().getMethodName());
        String screenshotPath = TestUtil.captureScreenshot();

        extentTest.fail(testResult.getThrowable().getMessage(),
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
    }

    /**
     * Invoked each time a test is skipped.
     */
    @Override
    public void onTestSkipped(ITestResult testResult) {
        ITestListener.super.onTestSkipped(testResult);
    }

    /**
     * Invoked each time a test fails but is within success percentage.
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult testResult) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(testResult);
    }

    /**
     * Invoked each time a test fails due to a timeout.
     */
    @Override
    public void onTestFailedWithTimeout(ITestResult testResult) {
        ITestListener.super.onTestFailedWithTimeout(testResult);
    }

    /**
     * Invoked before any test method belonging to the classes inside the test tag is run.
     */
    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    /**
     * Invoked after all the tests have run.
     */
    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
    }
}