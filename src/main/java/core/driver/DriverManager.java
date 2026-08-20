package core.driver;

import core.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private static final Logger log =
            LoggerUtil.getLogger(DriverManager.class);

    public static void initDriver() {

        log.info("Initializing WebDriver...");

        //killChromeProcesses();

        WebDriver webDriver = DriverFactory.createDriver();

        driver.set(webDriver);

        log.info("WebDriver initialized successfully");
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    /*public static void quitDriver() {

        if (driver.get() != null) {

            log.info("Closing WebDriver");

            driver.get().quit();
            driver.remove();

            log.info("WebDriver closed successfully");

        } else {

            log.warn("Attempted to quit WebDriver but driver was null");
        }
    }*/

    public static void quitDriver() {
        WebDriver webDriver = getDriver();

        if (webDriver == null) {
            log.warn("Attempted to quit WebDriver but driver was null");
            return;
        }

        try {
            log.info("Closing WebDriver");
            webDriver.quit();
            log.info("WebDriver closed successfully");
        } catch (Exception e) {
            log.error("Error while closing WebDriver", e);
        } finally {
            driver.remove();
        }
    }

    private static void killChromeProcesses() {
        try {
            log.info("Killing leftover Chrome processes...");

            /*Process p1 = Runtime.getRuntime().exec("taskkill /F /IM chrome.exe /T");
            Process p2 = Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");*/

            Process p1 = Runtime.getRuntime().exec("taskkill /F /IM chrome.exe /T");
            p1.waitFor();

            Process p2 = Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
            p2.waitFor();

            // opcionalno: ispis output-a (debug)
            printProcessOutput(p1);
            printProcessOutput(p2);

        } catch (Exception e) {
            log.error("Failed to kill Chrome processes", e);
        }
    }

    private static void printProcessOutput(Process process) {
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(process.getInputStream()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                log.debug(line);
            }

        } catch (Exception ignored) {}
    }

}
