package tests.TestsFortrade;

import core.actions.ElementActions;
import core.base.BaseTest;
import core.config.ConfigReader;
import core.pages.FortradePage;
import core.pages.ReadyFortrade;
import core.utils.ScreenshotUtil;
import io.qameta.allure.Allure;
import io.qameta.allure.Story;
import jdk.jfr.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import testdata.TestData;

import java.awt.*;
import java.io.IOException;

public class PremiumForexCA extends BaseTest {

    private FortradePage fortradePage;
    private ReadyFortrade readyFortrade;

    @BeforeMethod
    public void initPages(){
        fortradePage = new FortradePage();
        readyFortrade = new ReadyFortrade();
        openUrl(ConfigReader.getBaseUrl("base.url"));
    }

    @Story("Successfully demo account registration")
    @Test
    @Parameters({"regulation"})
    @Description("Verify the demo account is registered successfully with valid data")
    public void demoAccountRegistration(String regulation) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Demo account is successfully registered - " + regulation);
        openUrl(ConfigReader.getBaseUrl("base.url"));
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),TestData.canadaPhoneNumber());
        readyFortrade.assertURL("https://ready.fortrade.com/");
        readyFortrade.clickUsePassBtn();
        readyFortrade.clickMenuBtn();
        readyFortrade.checkRegulation(regulation);
    }

    //@Story("Verify that the Last Name cannot be the same as First name")
    @Test(description = "Verify that the Last Name cannot be the same as First name")
    @Parameters({"regulation"})
    public void sameFNameAndLName(String regulation) {
        ScreenshotUtil.setCustomName("Your first name must be different from your last name - " + regulation);
        Allure.step("Redirected to the https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en");
        fortradePage.insertFirstName("Test");
        fortradePage.insertLastName("Test");
        ElementActions.click(fortradePage.email, "email address");
    }
}
