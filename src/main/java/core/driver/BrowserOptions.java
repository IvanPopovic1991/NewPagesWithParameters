package core.driver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserOptions {

    public static ChromeOptions chrome() {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--user-data-dir=C:/temp/chrome-profile");

        return options;
    }

    public static FirefoxOptions firefox() {

        FirefoxOptions options = new FirefoxOptions();

        if (isHeadless()) {
            options.addArguments("--headless");
        }

        return options;
    }

    public static EdgeOptions edge() {

        EdgeOptions options = new EdgeOptions();

        if (isHeadless()) {
            options.addArguments("--headless=new");
        }

        return options;
    }

    private static boolean isHeadless() {
        return Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );
    }
}
