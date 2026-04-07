package tests.TestsFortrade;

import core.actions.ElementActions;
import core.base.BaseTest;
import core.config.ConfigReader;
import core.pages.CrmPage;
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
    private CrmPage crmPage;

    @BeforeMethod
    public void initPages(){
        fortradePage = new FortradePage();
        readyFortrade = new ReadyFortrade();
        crmPage = new CrmPage();
        openUrl(ConfigReader.getBaseUrl("base.url"));
    }

    @Test(description = "TC 2.1. Verify the demo account is registered successfully with valid data")
    @Parameters({"regulation"})
    public void demoAccountRegistration(String regulation,String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Demo account is successfully registered - " + regulation);
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),countryCode,TestData.canadaPhoneNumber());
        readyFortrade.assertURL("https://ready.fortrade.com/");
        readyFortrade.clickUsePassBtn();
        readyFortrade.clickMenuBtn();
        readyFortrade.checkRegulation(regulation);
    }

    @Test(description = "TC 3.1. Verify that the account cannot be registered with already registered email address")
    @Parameters({"regulation"})
    public void alreadyRegisteredEmailAddress(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Already registered email address " + regulation);
        Allure.step("Redirected to https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en");
        String email = TestData.generateEmail();
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,countryCode,email,TestData.canadaPhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        openUrl(ConfigReader.getBaseUrl("base.url"));
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,countryCode,email,TestData.canadaPhoneNumber());
        fortradePage.assertAlrRegEmailErrorMsg();
    }

    @Test(description="TC 3.2 Verify the demo account is not registered successfully with invalid data")
    @Parameters({"regulation"})
    public void nonValidData(String regulation,String countryCode){
        ScreenshotUtil.setCustomName("Non valid data " + regulation);
        Allure.step("Redirected to https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en");
        fortradePage.registerDemoAccount("123","574","abcd134324",countryCode,"0198798");
        fortradePage.assertErrorMessages();
    }

    @Test(description = "TC 3.3. Verify that the account cannot be registered with already registered phone number")
    @Parameters({"regulation"})
    public void alreadyRegisteredPhoneNumber(String regulation,String countryCode){
        ScreenshotUtil.setCustomName("Already registered phone number " + regulation);
        Allure.step("Redirected to https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en");
        String phoneNumber = TestData.canadaPhoneNumber();
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),countryCode,phoneNumber);
        readyFortrade.assertURL(TestData.appUrl);
        openUrl(ConfigReader.getBaseUrl("base.url"));
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),countryCode,phoneNumber);
        fortradePage.assertErrMsgForAlreadyRegisteredAccount();
    }

    @Test(description = "TC 3.4. Verify that the account cannot be registered with already registered email address and phone number")
    @Parameters({"regulation"})
    public void alreadyRegisteredEmailAndPhone(String regulation, String countryCode){
    ScreenshotUtil.setCustomName("Already registered email address and phone number " + regulation);
    Allure.step("Redirected to https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en");
    String email = TestData.generateEmail();
    String phone = TestData.canadaPhoneNumber();
    fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,phone);
    readyFortrade.assertURL(TestData.appUrl);
    openUrl(ConfigReader.getBaseUrl("base.url"));
    fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,phone);
    fortradePage.assertAlrRegEmailErrorMsg();
    }

    @Test(description = "TC 3.5. Verify the demo account is not registered successfully with empty fields")
    @Parameters({"regulation"})
    public void emptyDataRegistration(String regulation){
        ScreenshotUtil.setCustomName("Unsuccessfully account registration with empty data " + regulation);
        Allure.step("Redirected to https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en");
        fortradePage.clickGetStartedBtn();
        //missing assertation
    }

    @Test(description = "TC 4.6. Verify that the Last Name cannot be the same as First name")
    @Parameters({"regulation"})
    public void sameFNameAndLName(String regulation) {
        ScreenshotUtil.setCustomName("Your first name must be different from your last name - " + regulation);
        Allure.step("Redirected to the https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en");
        fortradePage.insertFirstName(TestData.sameNameAndSurname);
        fortradePage.insertLastName(TestData.sameNameAndSurname);
        ElementActions.click(fortradePage.email, "email address");
    }

    @Test(description = "9.1. Verify the border color in the CRM for regulation")
    @Parameters({"regulation","countryCode"})
    public void checkAccountRegulation(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Account regulation in CRM - Fortrade - " + regulation);
        String email = TestData.generateEmail();
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,"Testq Testa",regulation);
    }

    @Test(description = "9.2. Verify the account details is displayed correctly in the CRM")
    @Parameters({"regulation","countryCode"})
    public void checkAccountDetailsInCrm(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Account details in CRM - Fortrade - " + regulation);
        String email = TestData.generateEmail();
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,"Testq Testa",regulation);
    }


    @Test(description = "9.3. Verify the tags are displayed correctly in CRM")
    @Parameters({"regulation","countryCode"})
    public void checkTagsInCrm(String regulation,String countryCode){
        ScreenshotUtil.setCustomName("Marketing tags Fortrade page " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=age-annual-saving-knowledge-plang:all&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(),
                "25-34", "$15,000-$50,000", "$50,000-$100,000", "All the above","English");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,"Testq Testa",regulation);
        crmPage.checkCrmTags();
    }
}
