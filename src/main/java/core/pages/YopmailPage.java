package core.pages;

import core.actions.ElementActions;
import core.base.BasePage;
import core.waitsManagement.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;

import static core.actions.ElementActions.getText;

public class YopmailPage extends BasePage {

    @FindBy(xpath = "//input[@class='ycptinput']")
    public WebElement search;

    @FindBy(xpath = "//div[@id='refreshbut']")
    public WebElement goBtn;

    @FindBy(xpath = "//iframe[@id='ifinbox']")
    public WebElement inboxFrame;

    @FindBy(xpath = "//iframe[@id='ifmail']")
    public WebElement mailFrame;

    @FindBy(xpath = "//div[@class='lmfd']/span[contains(text(), 'Fortrade')]")
    public WebElement emailMessage;

    @FindBy(xpath = "//button[@id='refresh']")
    public WebElement refreshEmailBtn;

    @FindBy(xpath = "//span[contains(text(), 'Fortrade <ftadmin@fortrade.com>')]")
    public WebElement fortradeEmail;

    @FindBy(xpath = "//body//header//div[contains(text(),'Your Fortrade Demo Account Is Ready')]")
    public WebElement emailTitle;

    @FindBy(xpath = "//tr/td/b[contains(text(), 'Testq')]")
    public WebElement fortradeTestqName;

    @FindBy(xpath = "//div[@class='lmfd']/span[contains(text(), 'KapitalRS')]")
    public WebElement emailMessageKRS;

    @FindBy(xpath = "//span[contains(text(), 'KapitalRS <podrska@kapitalrs.com>')]")
    public WebElement kapitalRSEmail;

    @FindBy(xpath = "//div[contains(text(),'Your Demo Account is Ready')]")
    public WebElement emailTitleKRS;

    @FindBy(xpath = "//td/p[contains(text(), 'Welcome, Testq!')]")
    public WebElement kapitalRSTestqName;

/*    public void findEmail(String emailValue){
        ElementActions.type(search,emailValue,"search input");
        ElementActions.click(goBtn,"go button");
        for (int i = 0; i < 10; i++) {
            try {
                driver.switchTo().frame(inboxFrame);
                if (emailMessage.isDisplayed()) {
                    break; // Exit loop when message appears
                }
            } catch (NoSuchElementException e) {
                // Do nothing — element not yet found
                if (i==9){
                    Assert.fail("An email is not found after 9 tries!!!");
                }
            }

            driver.switchTo().defaultContent();
            ElementActions.click(refreshEmailBtn, "refresh email inbox");
        }
        ElementActions.click(emailMessage,"received message in mailbox");
        driver.switchTo().defaultContent();
        driver.switchTo().frame(mailFrame);
        Assert.assertEquals(getText(fortradeEmail, "getting fortrade email"), "Fortrade <ftadmin@fortrade.com>");
        Assert.assertEquals(getText(fortradeTestqName, "getting Testq name"), "Testq");
    }*/

    public void findEmail(String emailValue) {
        ElementActions.type(search, emailValue, "search input");
        ElementActions.click(goBtn, "go button");
        WaitUtil.waitForCondition(driver -> {
                    try {
                        driver.switchTo().defaultContent();
                        driver.switchTo().frame(inboxFrame);

                        if (emailMessage.isDisplayed()) {
                            return true;
                        }

                    } catch (Exception ignored) {
                    }

                    driver.switchTo().defaultContent();
                    ElementActions.click(refreshEmailBtn, "refresh inbox");
                    return false;
                },
                90, 5, "Email was not received within 90 seconds!");
        driver.switchTo().defaultContent();
        driver.switchTo().frame(inboxFrame);
        ElementActions.click(emailMessage, "open email");
        driver.switchTo().defaultContent();
        driver.switchTo().frame(mailFrame);
        Assert.assertEquals(getText(fortradeEmail, ""), "Fortrade <ftadmin@fortrade.com>");
    }

    public void findEmailKRS(String emailValue) {
        ElementActions.type(search, emailValue, "search input");
        ElementActions.click(goBtn, "go button");

        for (int i = 0; i < 10; i++) {
            try {
                driver.switchTo().frame(inboxFrame);
                if (emailMessageKRS.isDisplayed()) {
                    break; // Exit loop when message appears
                }
            } catch (NoSuchElementException e) {
                // Do nothing — element not yet found
                if (i == 9) {
                    Assert.fail("An email is not found after 9 tries!!!");
                }
            }
            driver.switchTo().defaultContent();
            ElementActions.click(refreshEmailBtn, "refresh email inbox");
        }
        ElementActions.click(emailMessageKRS, "received message in mailbox");
        driver.switchTo().defaultContent();
        driver.switchTo().frame(mailFrame);
        Assert.assertEquals(getText(kapitalRSEmail, "kapitalRS email"), "KapitalRS <podrska@kapitalrs.com>");
        Assert.assertTrue(getText(kapitalRSTestqName, "Testq name").contains("Welcome, Testq!"));
        Assert.assertEquals(getText(emailTitleKRS, "email title"), "Your Demo Account is Ready");
    }
}
