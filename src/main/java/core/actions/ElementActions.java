package core.actions;

import core.driver.DriverManager;
import core.utils.LoggerUtil;
import core.waitsManagement.WaitUtil;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class ElementActions {

    private static final Logger log =
            LoggerUtil.getLogger(ElementActions.class);

    private static final int MAX_RETRY = 2;

    private static WebDriver driver() {
        return DriverManager.getDriver();
    }

    private static Actions actions() {
        return new Actions(driver());
    }

    /* ================= CLICK ================= */

    @Step("Click element: {elementName}")
    public static void click(WebElement element, String elementName) {

        int attempts = 0;

        while (attempts <= MAX_RETRY) {

            try {

                WaitUtil.waitForClickable(element);

                element.click();

                log.info("Clicked element: {}", elementName);

                Allure.step("Clicked " + elementName);

                return;

            } catch (StaleElementReferenceException e) {

                log.warn("Stale element for '{}', retrying...", elementName);

            } catch (ElementNotInteractableException e) {

                log.warn("Element not interactable '{}', retrying...", elementName);

            } catch (WebDriverException e) {

                log.warn("WebDriver exception on '{}', retrying...", elementName);
            }

            sleep(500);
            attempts++;
        }

        log.warn("Fallback to JS click for element: {}", elementName);
        jsClick(element, elementName);
    }

    @Step("JS click on element: {elementName}")
    public static void jsClick(WebElement element, String elementName) {

        WaitUtil.waitForVisible(element);

        ((JavascriptExecutor) driver())
                .executeScript("arguments[0].click();", element);

        log.info("JS clicked element: {}", elementName);

        Allure.step("Clicked " + elementName);
    }

    /* ================= TYPE ================= */

    @Step("Type '{text}' in {elementName}")
    public static void type(WebElement element,
                            String text,
                            String elementName) {

        WaitUtil.waitForVisible(element);

        element.clear();
        element.sendKeys(text);

        log.info("Typed '{}' in element {}", text, elementName);

        Allure.step("Typed " + text + " into " + elementName);
    }

    /* ================= GET TEXT ================= */

    @Step("Get text from {elementName}")
    public static String getText(WebElement element,
                                 String elementName) {

        WaitUtil.waitForVisible(element);

        String text = element.getText();

        log.info("Retrieved text from {} : {}", elementName, text);

        Allure.step("Get " + text + " from " + elementName);

        return text;
    }

    /*
        public String getTextBy(By by, String log) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, waitTime);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));

            System.out.println("Got text from " + log + " element.");
            return driver.findElement(by).getText();
        } catch (StaleElementReferenceException e) {
            System.out.println("Got text from " + log + " element.");
            return driver.findElement(by).getText();
        }
    }
     */

    @Step("Get text from {elementName}")
    public static String getText(By by, String elementName){

        String text = driver().findElement(by).getText();

        log.info("Retrieved text from {} : {}", elementName, text);

        Allure.step("Get text from " + elementName);

        return driver().findElement(by).getText();
    }

    /* ================= DROPDOWN ================= */

    @Step("Select '{text}' from dropdown {elementName}")
    public static void selectByText(WebElement element,
                                    String text,
                                    String elementName) {

        WaitUtil.waitForVisible(element);

        new Select(element)
                .selectByVisibleText(text);

        log.info("Selected '{}' from dropdown {}", text, elementName);

        Allure.step("Selected " + text + " from " + elementName);
    }

    /* ================= MOUSE ================= */

    @Step("Double click on {elementName}")
    public static void doubleClick(WebElement element,
                                   String elementName) {

        WaitUtil.waitForClickable(element);

        actions()
                .doubleClick(element)
                .perform();

        log.info("Double clicked element {}", elementName);

        Allure.step("Double clicked " + elementName);
    }

    @Step("Right click on {elementName}")
    public static void rightClick(WebElement element,
                                  String elementName) {

        WaitUtil.waitForClickable(element);

        actions()
                .contextClick(element)
                .perform();

        log.info("Right clicked element {}", elementName);

        Allure.step("Right clicked " + elementName);
    }

    @Step("Hover over element: {elementName}")
    public static void hover(WebElement element,
                             String elementName) {

        WaitUtil.waitForVisible(element);

        actions()
                .moveToElement(element)
                .perform();

        log.info("Hovered over element {}", elementName);

        Allure.step("Hovered over " + elementName);
    }

    /* ================= DRAG & DROP ================= */

    @Step("Drag {sourceName} to {targetName}")
    public static void dragAndDrop(WebElement source,
                                   WebElement target,
                                   String sourceName,
                                   String targetName) {

        actions()
                .dragAndDrop(source, target)
                .perform();

        log.info("Dragged {} to {}", sourceName, targetName);
    }

    /* ================= SCROLL ================= */

    @Step("Scroll to element: {elementName}")
    public static void scrollTo(WebElement element,
                                String elementName) {

        ((JavascriptExecutor) driver())
                .executeScript(
                        "arguments[0].scrollIntoView(true);",
                        element
                );

        log.info("Scrolled to element {}", elementName);

        Allure.step("Scrolled to " + elementName);
    }

    /* ================= HELPER ================= */

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}