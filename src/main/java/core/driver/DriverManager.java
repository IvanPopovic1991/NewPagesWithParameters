package core.driver;

import core.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private static final Logger log =
            LoggerUtil.getLogger(DriverManager.class);

    public static void initDriver() {

        log.info("Initializing WebDriver...");

        WebDriver webDriver = DriverFactory.createDriver();

        driver.set(webDriver);

        log.info("WebDriver initialized successfully");
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {

        if (driver.get() != null) {

            log.info("Closing WebDriver");

            driver.get().quit();
            driver.remove();

            log.info("WebDriver closed successfully");

        } else {

            log.warn("Attempted to quit WebDriver but driver was null");
        }
    }
}
