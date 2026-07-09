package core.waitsManagement;

import core.driver.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.function.Function;

public class WaitUtil {

    private static final int TIMEOUT = 40;

    public static WebDriverWait getWait() {
        return new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(TIMEOUT));
    }

    public static WebDriverWait getWait(int timeoutSeconds) {
        return new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(timeoutSeconds)
        );
    }

    public static WebDriverWait getWait(int timeoutSeconds, int pollingMillis) {
        WebDriverWait wait = new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(timeoutSeconds)
        );

        wait.pollingEvery(Duration.ofMillis(pollingMillis));
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);

        return wait;
    }

    public static void waitForClickable(WebElement element, int timeoutSeconds) {
        getWait(timeoutSeconds).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForClickable(By locator, int timeoutSeconds) {
        return getWait(timeoutSeconds).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForClickableOptional(By locator, int timeoutSeconds) {
        try {
            return getWait(timeoutSeconds, 200)
                    .until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            return null;
        }
    }

    public static void waitForVisible(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForVisible(By locator){
        getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
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

    public static boolean waitForCondition(Function<WebDriver, Boolean> condition,
                                           int timeoutSeconds,
                                           int pollingSeconds,
                                           String failureMessage) {

        WebDriverWait wait = new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(timeoutSeconds)
        );

        wait.pollingEvery(Duration.ofSeconds(pollingSeconds));
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);

        try {
            return wait.until(driver -> condition.apply(driver));
        } catch (TimeoutException e) {
            throw new AssertionError(failureMessage);
        }
    }

    public static void threadSleep (int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}