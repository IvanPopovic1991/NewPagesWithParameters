package core.base;

import core.config.ConfigReader;
import core.driver.DriverManager;
import core.utils.ScreenshotUtil;
import org.openqa.selenium.Dimension;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        String env =
                System.getProperty("env", "local");

        String browser =
                ConfigReader.getBrowser();

        System.setProperty("env", env);
        System.setProperty("browser", browser);

        System.out.println("ENVIRONMENT: " + env);
        System.out.println("BROWSER: " + browser);

        System.out.println("THREAD: " + Thread.currentThread().getId());
        System.out.println("DRIVER: " + DriverManager.getDriver());

        DriverManager.initDriver();

        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {

            DriverManager.getDriver()
                    .manage()
                    .window()
                    .maximize();

            System.out.println("WINDOW MODE: MAXIMIZED (WINDOWS)");

        } else {

            DriverManager.getDriver()
                    .manage()
                    .window()
                    .setSize(new Dimension(1920, 1080));

            System.out.println("WINDOW MODE: 1920x1080 (LINUX)");
        }

        System.out.println(
                "WINDOW SIZE: "
                        + DriverManager.getDriver()
                        .manage()
                        .window()
                        .getSize()
        );

        System.out.println("Cookies before: "
                + DriverManager.getDriver()
                .manage()
                .getCookies()
                .size());

        DriverManager.getDriver()
                .manage()
                .deleteAllCookies();

        System.out.println("Cookies after: "
                + DriverManager.getDriver()
                .manage()
                .getCookies()
                .size());

        System.out.println("Driver started");
    }

    protected void openUrl(String url) {

        System.out.println("THREAD: " + Thread.currentThread().getId());
        System.out.println("DRIVER: " + DriverManager.getDriver());

        DriverManager.getDriver().get(url);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        String status;

        switch (result.getStatus()) {

            case ITestResult.FAILURE:
                status = "FAILED";
                break;

            case ITestResult.SUCCESS:
                status = "PASSED";
                break;

            case ITestResult.SKIP:
                status = "SKIPPED";
                break;

            default:
                status = "UNKNOWN";
        }

        String path = ScreenshotUtil.captureScreenshot(
                result.getName() + "_" + status,
                status
        );

        result.setAttribute("screenshotPath", path);

        DriverManager.quitDriver();
    }
}