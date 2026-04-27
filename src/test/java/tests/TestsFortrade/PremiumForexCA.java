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

    @Test(description = "TC 1.2.1 - Verify the logo is not clickable with left click")
    @Parameters({"regulation"})
    public void logoClickability(String regulation){
        ScreenshotUtil.setCustomName("Logo is not clickable - " + regulation);
        Allure.step("Tried to click on Fortrade iiroc logo");
        fortradePage.checkLogoClickability();
        fortradePage.assertURL("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en");
    }

    @Test(description = "TC 1.5 - Verify text under the form for iiroc regulation.")
    @Parameters({"regulation"})
    public void verifyIirocText(String regulation){
        ScreenshotUtil.setCustomName("Text is displayed correctly under the form for iiroc regulation." + regulation);
        Allure.step("Checked for text under the form for iiroc regulation");
        fortradePage.assertText(fortradePage.textUnderFormIiroc, TestData.textForIiroc);
    }

    @Test(description = "TC 4.1 - Verify that the invalid data for the First Name field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForFirstName(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for First Name." + regulation);
        Allure.step("Verified error message and border color for First Name field");
        ElementActions.type(fortradePage.firstName,"123", "first name");
        fortradePage.assertBorderColor(fortradePage.borderColorForFirstName, "border-color", TestData.redBorderColor);
        fortradePage.assertFirstStepErrorMessage(TestData.firstNameErrorMessage);
    }

    @Test(description = "TC 4.2 - Verify that the invalid data for the Last Name field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForLastName(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Last Name." + regulation);
        Allure.step("Verified error message and border color for Last Name field");
        ElementActions.type(fortradePage.lastName,"456", "last name");
        fortradePage.assertBorderColor(fortradePage.borderColorForLastName, "border-color", TestData.redBorderColor);
        fortradePage.assertFirstStepErrorMessage(TestData.lastNameErrorMessage);
    }

    @Test(description = "TC 4.3 - Verify that the invalid data for the Email field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForEmail(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Email." + regulation);
        Allure.step("Verified error message and border color for Email field");
        ElementActions.type(fortradePage.email,"dsv124234/=", "email");
        fortradePage.assertBorderColor(fortradePage.borderColorForEmail, "border-color", TestData.redBorderColor);
        fortradePage.assertFirstStepErrorMessage(TestData.emailErrorMessage);
    }

    @Test(description = "TC 4.5 - Verify that the invalid data for Phone field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForPhone(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Phone." + regulation);
        Allure.step("Verified error message and border color for Phone field");
        ElementActions.type(fortradePage.phoneNumber,"0034334424558200", "phone");
        fortradePage.assertBorderColor(fortradePage.borderColorForPhone, "border-color", TestData.redBorderColor);
        fortradePage.assertFirstStepErrorMessage(TestData.wrongPhoneErrorMessage);
    }

    //@Story("Verify that the Last Name cannot be the same as First name")
    @Test(description = "TC 4.6 - Verify that the Last Name cannot be the same as First name.")
    @Parameters({"regulation"})
    public void sameFNameAndLName(String regulation) {
        ScreenshotUtil.setCustomName("Your first name must be different from your last name - " + regulation);
        Allure.step("Check for error message for the same first and last name.");
        fortradePage.insertFirstName("Test");
        fortradePage.insertLastName("Test");
        ElementActions.click(fortradePage.email, "email address");
        fortradePage.assertFirstStepErrorMessage(TestData.sameFirstNameErrorMessage);
        fortradePage.assertFirstStepErrorMessage(TestData.sameLastNameErrorMessage);
    }

    //@Story("Verify that the First Name cannot be the same as Last name")
    @Test(description = "TC 4.6 - Verify that the First Name cannot be the same as Last name.")
    @Parameters({"regulation"})
    public void sameLNameAndFName(String regulation) {
        ScreenshotUtil.setCustomName("Your last name must be different from your first name - " + regulation);
        Allure.step("Check for error message for the same last and first name.");
        fortradePage.insertLastName("Test");
        fortradePage.insertFirstName("Test");
        ElementActions.click(fortradePage.email, "email address");
        fortradePage.assertFirstStepErrorMessage(TestData.sameLastNameErrorMessage);
        fortradePage.assertFirstStepErrorMessage(TestData.sameFirstNameErrorMessage);
    }

    @Test(description = "TC 7.1 - Verify the Privacy Policy link works with left click")
    @Parameters({"regulation"})
    public void checkHeaderPrivacyPolicyLinkWithLeftClick(String regulation){
        ScreenshotUtil.setCustomName("Header privacy policy link - " + regulation);
        Allure.step("Left click on the header privacy policy link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.headerPrivacyPolicyLink, "header privacy policy link", TestData.privacyPolicyUrl);
    }

    @Test(description = "TC 7.1.1 - Verify the Privacy Policy link works with right click")
    public void checkHeaderPrivacyPolicyLinkWithRightClick(){
        Allure.step("Right click on the header privacy policy link.");
        fortradePage.checkLinksWithRightClick(fortradePage.headerPrivacyPolicyLink, "header privacy policy link", TestData.privacyPolicyUrl);
    }

    @Test(description = "TC 7.2 - Verify the Terms and Conditions link works with left click")
    @Parameters({"regulation"})
    public void checkHeaderTermsAndConditionsLinkWithLeftClick(String regulation){
        ScreenshotUtil.setCustomName("Header terms and conditions link - " + regulation);
        Allure.step("Left click on the terms and conditions link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.headerTermsAndConditionsLink, "header terms and conditions link", TestData.termsAndConditionsUrl);
    }

    @Test(description = "TC 7.2.1 - Verify the Terms and Conditions link works with right click")
    public void checkHeaderTermsAndConditionsLinkWithRightClick(){
        Allure.step("Right click on the terms and conditions link.");
        fortradePage.checkLinksWithRightClick(fortradePage.headerTermsAndConditionsLink, "header terms and conditions link", TestData.termsAndConditionsUrl);
    }

    @Test(description = "TC 7.4 - Verify the Already have an account? link works with left click")
    @Parameters({"regulation"})
    public void checkAlreadyHaveAnAccountLinkWithLeftClick(String regulation){
        ScreenshotUtil.setCustomName("Already have an account link - " + regulation);
        Allure.step("Left click on the already have an account link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.alreadyHaveAnAccountLink, "already have an account link", TestData.alreadyHaveAnAccountUrl);
    }

    @Test(description = "TC 7.4.1 - Verify the Already have an account? link works with right click")
    public void checkAlreadyHaveAnAccountLinkWithRightClick(){
        Allure.step("Right click on the already have an account link.");
        fortradePage.checkLinksWithRightClick(fortradePage.alreadyHaveAnAccountLink, "already have an account link", TestData.alreadyHaveAnAccountUrl);
    }

    @Test(description = "TC 7.5 - Verify the click on Contact us link opens new mail window")
    @Parameters({"regulation"})
    public void checkContactUsLink(String regulation){
        ScreenshotUtil.setCustomName("Contact Us link - " + regulation);
        Allure.step("Left click on the contact us link.");
        fortradePage.checkMailLinks(fortradePage.contactUsLink, "href", TestData.contactUsUrl);
    }

    @Test(description = "TC 7.6 - Verify the click on support@fortrade.com link opens email window")
    @Parameters({"regulation"})
    public void checkSupportLink(String regulation){
        ScreenshotUtil.setCustomName("Support link - " + regulation);
        Allure.step("Right click on the contact us link.");
        fortradePage.checkMailLinks(fortradePage.supportLink, "href", TestData.supportUrl);
    }

    @Test(description = "TC 7.7 - Verify the Risk warning link works with left click")
    @Parameters({"regulation"})
    public void checkRiskWarningLinkWithLeftClick(String regulation){
        ScreenshotUtil.setCustomName("Risk warning link - " + regulation);
        Allure.step("Left click on the risk warning link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerRiskWarningLink, "risk warning link", TestData.riskWarningUrl);
    }

    @Test(description = "TC 7.7.1 - Verify the Risk warning link works with right click")
    public void checkRiskWarningLinkWithRightClick(){
        Allure.step("Right click on the risk warning link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerRiskWarningLink, "risk warning link", TestData.riskWarningUrl);
    }

    @Test(description = "TC 7.8 - Verify the Privacy policy link (in footer) works with left click")
    @Parameters({"regulation"})
    public void checkFooterPrivacyPolicyLinkWithLeftClick(String regulation){
        ScreenshotUtil.setCustomName("Footer privacy policy link - " + regulation);
        Allure.step("Left click on the footer privacy policy link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerPrivacyPolicyLink, "footer privacy policy link", TestData.privacyPolicyUrl);
    }

    @Test(description = "TC 7.8.1 - Verify the Privacy policy (in footer) link works with right click")
    public void checkFooterPrivacyPolicyLinkWithRightClick(){
        Allure.step("Right click on the footer privacy policy link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerPrivacyPolicyLink, "footer privacy policy link", TestData.privacyPolicyUrl);
    }

    @Test(description = "TC 8.1 - Verify the FRN: 609970 (FCA) link works with left click")
    @Parameters({"regulation"})
    public void checkFcaLinkWithLeftClick(String regulation){
        ScreenshotUtil.setCustomName("Fca link - " + regulation);
        Allure.step("Left click on the FCA link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerFCALink, "fca link", TestData.fcaUrl);
    }

    @Test(description = "TC 8.1.1 - Verify the FRN: 609970 (FCA) link works with right click")
    public void checkFcaLinkWithRightClick(){
        Allure.step("Right click on the FCA link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerFCALink, "fca link", TestData.fcaUrl);
    }

    @Test(description = "TC 8.2 - Verify the CRN: BC1148613 (IIROC) link works with left click")
    @Parameters({"regulation"})
    public void checkIirocLinkWithLeftClick(String regulation){
        ScreenshotUtil.setCustomName("Iiroc link - " + regulation);
        Allure.step("Left click on the IIROC link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerIIROCLink, "iiroc link", TestData.iirocUrl);
    }

    @Test(description = "TC 8.2.1 - Verify the CRN: BC1148613 (IIROC) link works with right click")
    public void checkIirocLinkWithRightClick(){
        Allure.step("Right click on the IIROC link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerIIROCLink, "iiroc link", TestData.iirocUrl);
    }

    @Test(description = "TC 8.3 - Verify the ABN: 33 614 683 831 | AFSL: 493520 (ASIC) link works with left click")
    @Parameters({"regulation"})
    public void checkAsicLinkWithLeftClick(String regulation){
        ScreenshotUtil.setCustomName("Asic link - " + regulation);
        Allure.step("Left click on the ASIC link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerASICLink, "asic link", TestData.asicUrl);
    }

    @Test(description = "TC 8.3.1 - Verify the ABN: 33 614 683 831 | AFSL: 493520 (ASIC) link works with right click")
    public void checkAsicLinkWithRightClick(){
        Allure.step("Right click on the ASIC link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerASICLink, "asic link", TestData.asicUrl);
    }

    @Test(description = "TC 8.4 - Verify the CIF license number 385/20 (CYSEC) link works with left click")
    @Parameters({"regulation"})
    public void checkCysecLinkWithLeftClick(String regulation){
        ScreenshotUtil.setCustomName("Cysec link - " + regulation);
        Allure.step("Left click on the CYSEC link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerCYSECLink, "cysec link", TestData.cysecUrl);
    }

    @Test(description = "TC 8.4.1 - Verify the CIF license number 385/20 (CYSEC) link works with right click")
    public void checkCysecLinkWithRightClick(){
        Allure.step("Right click on the CYSEC link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerCYSECLink, "cysec link", TestData.cysecUrl);
    }

    @Test(description = "TC 8.5 - Verify the GB21026472 (FSC) link works with left click")
    @Parameters({"regulation"})
    public void checkFscLinkWithLeftClick(String regulation){
        ScreenshotUtil.setCustomName("Fsc link - " + regulation);
        Allure.step("Left click on the FSC link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerFSCLink, "fsc link", TestData.fscUrl);
    }

    @Test(description = "TC 8.5.1 - Verify the GB21026472 (FSC) link works with right click")
    public void checkFscLinkWithRightClick(){
        Allure.step("Right click on the FSC link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerFSCLink, "fsc link", TestData.fscUrl);
    }

    @Test(description = "TC 8.6 - Verify the F009856 (DFSA) link works with left click")
    @Parameters({"regulation"})
    public void checkDfsaLinkWithLeftClick(String regulation){
        ScreenshotUtil.setCustomName("Dfsa link - " + regulation);
        Allure.step("Left click on the DFSA link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerDFSALink, "dfsa link", TestData.dfsaUrl);
    }

    @Test(description = "TC 8.6.1 - Verify the F009856 (DFSA) link works with right click")
    public void checkDfsaLinkWithRightClick(){
        Allure.step("Right click on the DFSA link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerDFSALink, "dfsa link", TestData.dfsaUrl);
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

    @Test(description = "12.5. Verify that message 'We sent you the code again' is received")
    @Parameters({"regulation","countryCode"})
    public void iDidntReceiveTheCodeTest(String regulation,String countryCode){
        ScreenshotUtil.setCustomName("I didn't received the code link " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=sms-age-annual-saving-knowledge-plang:all&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        fortradePage.checkIDidntReceiveTheCodeLink();
    }

    @Test(description = "12.6. Verify that wrong code cannot be submitted (negative test case)")
    @Parameters({"regulation","countryCode"})
    public void wrongCodeCannotBeSubmittedTest(String regulation,String countryCode){
        ScreenshotUtil.setCustomName("Wrong code cannot be submitted " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=sms-age-annual-saving-knowledge-plang:all&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.fillTheFormOnTheSecondStepWithWrongSmsCode(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(),
                "25-34", "$15,000-$50,000", "$50,000-$100,000", "All the above","English",
                "1", "1", "1", "1");
        fortradePage.assertErrorMessageForWrongSmsCode();
    }

    @Test(description = "12.9. Verify if user clicks pencil icon the same is returned to the 1st widget")
    @Parameters({"regulation","countryCode"})
    public void editPencilButtonTest(String regulation,String countryCode){
        ScreenshotUtil.setCustomName("Edit pencil button redirects to the first step " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=sms-age-annual-saving-knowledge-plang:all&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        fortradePage.checkEditPencilButton();
    }
}

