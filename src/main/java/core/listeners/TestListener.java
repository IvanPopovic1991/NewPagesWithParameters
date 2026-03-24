package core.listeners;

import core.driver.DriverManager;
import core.utils.LoggerUtil;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class TestListener implements ITestListener {

    private static final Logger log =
            LoggerUtil.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {

        log.info("STARTING TEST: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        log.info("TEST PASSED: {}", result.getName());

        attachScreenshot();
    }

    @Override
    public void onTestFailure(ITestResult result) {

        log.error("TEST FAILED: {}", result.getName());

        attachScreenshot();
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        log.warn("TEST SKIPPED: {}", result.getName());
    }

    private void attachScreenshot() {

        try {

            byte[] screenshot =
                    ((TakesScreenshot) DriverManager.getDriver())
                            .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(
                    "Screenshot",
                    new ByteArrayInputStream(screenshot)
            );

        } catch (Exception e) {

            log.error("Failed to attach screenshot to Allure report");
        }
    }
}
