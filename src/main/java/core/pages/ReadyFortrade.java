package core.pages;

import core.actions.ElementActions;
import core.base.BasePage;
import core.waitsManagement.WaitUtil;
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
                Assert.assertEquals(actualText, "Broker: Fortrade (Mauritius) Ltd (FSC)");
                //"Broker Fortrade Mauritius Ltd FSC - successfully registered demo account", regulationMsg);
            }
            break;
        }
    }

    public void assertURL(String url) {
        WaitUtil.waitForUrlContains(url);
        Assert.assertTrue(driver.getCurrentUrl().contains(url));
    }
}