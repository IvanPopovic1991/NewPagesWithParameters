package core.pages;

import core.actions.ElementActions;
import core.base.BasePage;
import core.waitsManagement.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.awt.*;
import java.io.IOException;

public class ReadyFortrade extends BasePage {

    @FindBy(xpath = "//div[@id='platformRegulation']")
    public WebElement txtRegulation;

    @FindBy(xpath = "//div[@class='startTradingButton']")
    protected WebElement btnUsePass;

    @FindBy(xpath = "//div[@data-cmd='menu']")
    protected WebElement btnMenu;

    @FindBy(xpath = "//div[@id='supportMenuItem']")
    protected WebElement settingBtn;

    @FindBy(xpath = "//div[@id='languagesMenuItem']")
    protected WebElement languageBtn;

    @FindBy(xpath = "//div[@class='exitButton']")
    protected WebElement iAmNotSerbianRes;

    public void clickUsePassBtn(){
        ElementActions.click(btnUsePass,"Use password button");
    }

    public void clickMenuBtn(){
        ElementActions.click(btnMenu,"Menu button");
    }

    public void checkRegulation(String regulation) throws IOException, AWTException {
        String actualText = ElementActions.getText(txtRegulation,"regulation text");
        switch (regulation) {
            case "FCA": {
                Assert.assertEquals(actualText, "Broker: Fortrade Ltd. (FCA)");
                //"Broker Fortrade Ltd FCA - successfully registered demo account", regulationMsg);
            }
            break;
            case "cyses": {
                Assert.assertEquals(actualText, "Broker: Fortrade Cyprus Ltd. (CySec)");
                //"Broker Fortrade Cyprus Ltd CySec - successfully registered demo account", regulationMsg);
            }
            break;
            case "Asic": {
                Assert.assertEquals(actualText, "Broker: Fort Securities Australia Pty Ltd. (ASIC)");
                //"Broker Fort Securities Australia Pty Ltd ASIC - successfully registered demo account", regulationMsg);
            }
            break;
            case "iiroc": {
                Assert.assertEquals(actualText, "Broker: Fortrade Canada Limited (CIRO)");
                //"Broker Fortrade Canada Limited CIRO - successfully registered demo account", regulationMsg);
            }
            break;
            case "FSC":
            default: {
                Assert.assertEquals(actualText, "Broker: Fortrade (Mauritius) Ltd (FSC) testq testa");
                //"Broker Fortrade Mauritius Ltd FSC - successfully registered demo account", regulationMsg);
            }
            break;
        }
    }

    public void assertURL(String url) {
        WaitUtil.waitForUrlContains(url);
        Assert.assertTrue(driver.getCurrentUrl().contains(url));
    }

    public void assertDisplayedLanguage(String language){
        ElementActions.click(btnMenu, "menu btn");
        ElementActions.click(settingBtn, "settings btn");
        ElementActions.click(languageBtn, "language btn");
        WebElement displayedLanguage = driver.findElement(By.xpath("//div[@id='settingsLanguage{language}']//*[name()='svg' and contains(@class,'tickSvg')]".replace("{language}", language)));
        WaitUtil.waitForVisible(displayedLanguage);
        Assert.assertTrue(displayedLanguage.isDisplayed());
    }

    public void clickNotSerbResBtn(){
        ElementActions.click(iAmNotSerbianRes,"I am not Serbian resident button");
    }
}