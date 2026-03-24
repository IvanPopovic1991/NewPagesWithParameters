package core.listeners;

import io.qameta.allure.Allure;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.FileInputStream;
import java.io.File;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {
        attachScreenshot(result, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        attachScreenshot(result, "FAILED");
    }

    private void attachScreenshot(ITestResult result, String status) {

        String path = (String) result.getAttribute("screenshotPath");

        if (path != null) {
            File file = new File(path);

            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {

                    Allure.addAttachment(
                            "Screenshot - " + status,
                            fis
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
