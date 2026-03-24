package core.driver;

import core.config.BrowserType;
import core.config.ConfigReader;
import core.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    private static final Logger log =
            LoggerUtil.getLogger(DriverFactory.class);

    public static WebDriver createDriver() {

        BrowserType browser =
                BrowserType.from(
                        ConfigReader.getBrowser()
                );

        log.info("Creating WebDriver for browser: {}", browser);

        switch (browser) {

            case CHROME:

                log.info("Launching Chrome browser");

                //System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");

                return new ChromeDriver(
                        BrowserOptions.chrome()
                );

            case FIREFOX:

                log.info("Launching Firefox browser");

                return new FirefoxDriver(
                        BrowserOptions.firefox()
                );

            case EDGE:

                log.info("Launching Edge browser");

                return new EdgeDriver(
                        BrowserOptions.edge()
                );

            default:

                log.error("Browser not supported: {}", browser);

                throw new RuntimeException("Browser not supported");
        }
    }
}
