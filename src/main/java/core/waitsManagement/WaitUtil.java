package core.waitsManagement;

import core.driver.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class WaitUtil {

    private static final int TIMEOUT = 15;

    private static WebDriverWait getWait() {
        return new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(TIMEOUT));
    }

    public static void waitForVisible(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForClickable(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForPresence(By locator) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitForInvisibility(WebElement element) {
        getWait().until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForText(WebElement element, String text) {
        getWait().until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static void waitForUrlContains(String partialUrl) {
        getWait().until(ExpectedConditions.urlContains(partialUrl));
    }

    public static void waitForPageLoad() {
        getWait().until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }
}