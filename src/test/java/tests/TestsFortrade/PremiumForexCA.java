package tests.TestsFortrade;

import core.actions.ElementActions;
import core.base.BaseTest;
import core.config.ConfigReader;
import core.pages.CrmPage;
import core.pages.FortradePage;
import core.pages.ReadyFortrade;
import core.pages.YopmailPage;
import core.utils.ScreenshotUtil;
import core.waitsManagement.WaitUtil;
import io.qameta.allure.Allure;
import io.qameta.allure.Story;
import jdk.jfr.Description;
import org.testng.Assert;
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
    @Parameters({"regulation","countryCode"})
    public void demoAccountRegistrationTest(String regulation,String countryCode) throws IOException, AWTException {

        ScreenshotUtil.setCustomName("Demo account is successfully registered - " + regulation);
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),countryCode,TestData.canadaPhoneNumber());
        readyFortrade.assertURL("https://ready.fortrade.com/");
        readyFortrade.clickUsePassBtn();
        readyFortrade.clickMenuBtn();
        readyFortrade.checkRegulation(regulation);
    }

    @Test(description = "TC 3.1. Verify that the account cannot be registered with already registered email address")
    @Parameters({"regulation","countryCode"})
    public void alreadyRegisteredEmailAddressTest(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Already registered email address " + regulation);
        Allure.step("Redirected to https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en");
        String email = TestData.generateEmail();
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        openUrl(ConfigReader.getBaseUrl("base.url"));
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        fortradePage.assertAlrRegEmailErrorMsg();
    }

    @Test(description="TC 3.2 Verify the demo account is not registered successfully with invalid data")
    @Parameters({"regulation","countryCode"})
    public void nonValidDataTest(String regulation,String countryCode){
        ScreenshotUtil.setCustomName("Non valid data " + regulation);
        Allure.step("Redirected to https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en");
        fortradePage.registerDemoAccount("123","574","abcd134324",countryCode,"0198798");
        fortradePage.assertErrorMessages();
    }

    @Test(description = "TC 3.3. Verify that the account cannot be registered with already registered phone number")
    @Parameters({"regulation","countryCode"})
    public void alreadyRegisteredPhoneNumberTest(String regulation,String countryCode){
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
    @Parameters({"regulation","countryCode"})
    public void alreadyRegisteredEmailAndPhoneTest(String regulation, String countryCode){
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
    public void emptyDataRegistrationTest(String regulation){
        ScreenshotUtil.setCustomName("Unsuccessfully account registration with empty data " + regulation);
        Allure.step("Redirected to https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en");
        fortradePage.clickGetStartedBtn();
        //missing assertation
    }

    @Test(description = "TC 1.2.1 - Verify the logo is not clickable with left click")
    @Parameters({"regulation"})
    public void logoClickabilityTest(String regulation){
        ScreenshotUtil.setCustomName("Logo is not clickable - " + regulation);
        Allure.step("Tried to click on Fortrade iiroc logo");
        fortradePage.checkLogoClickability();
        fortradePage.assertURL("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en");
    }

    @Test(description = "TC 1.5 - Verify text under the form for iiroc regulation.")
    @Parameters({"regulation"})
    public void verifyIirocTextTest(String regulation){
        ScreenshotUtil.setCustomName("Text is displayed correctly under the form for iiroc regulation." + regulation);
        Allure.step("Checked for text under the form for iiroc regulation");
        fortradePage.assertText(fortradePage.textUnderFormIiroc, TestData.textForIiroc);
    }

    @Test(description = "TC 4.1 - Verify that the invalid data for the First Name field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForFirstNameTest(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for First Name." + regulation);
        Allure.step("Verified error message and border color for First Name field");
        ElementActions.type(fortradePage.firstName,"123", "first name");
        fortradePage.assertBorderColor(fortradePage.borderColorForFirstName, "border-color", TestData.redBorderColor);
        fortradePage.assertFirstStepErrorMessage(TestData.firstNameErrorMessage);
    }

    @Test(description = "TC 4.2 - Verify that the invalid data for the Last Name field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForLastNameTest(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Last Name." + regulation);
        Allure.step("Verified error message and border color for Last Name field");
        ElementActions.type(fortradePage.lastName,"456", "last name");
        fortradePage.assertBorderColor(fortradePage.borderColorForLastName, "border-color", TestData.redBorderColor);
        fortradePage.assertFirstStepErrorMessage(TestData.lastNameErrorMessage);
    }

    @Test(description = "TC 4.3 - Verify that the invalid data for the Email field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForEmailTest(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Email." + regulation);
        Allure.step("Verified error message and border color for Email field");
        ElementActions.type(fortradePage.email,"dsv124234/=", "email");
        fortradePage.assertBorderColor(fortradePage.borderColorForEmail, "border-color", TestData.redBorderColor);
        fortradePage.assertFirstStepErrorMessage(TestData.emailErrorMessage);
    }

    @Test(description = "TC 4.5 - Verify that the invalid data for Phone field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForPhoneTest(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Phone." + regulation);
        Allure.step("Verified error message and border color for Phone field");
        ElementActions.type(fortradePage.phoneNumber,"0034334424558200", "phone");
        fortradePage.assertBorderColor(fortradePage.borderColorForPhone, "border-color", TestData.redBorderColor);
        fortradePage.assertFirstStepErrorMessage(TestData.wrongPhoneErrorMessage);
    }

    //@Story("Verify that the Last Name cannot be the same as First name")
    @Test(description = "TC 4.6 - Verify that the Last Name cannot be the same as First name.")
    @Parameters({"regulation"})
    public void sameFNameAndLNameTest(String regulation) {
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
    public void sameLNameAndFNameTest(String regulation) {
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
    public void checkHeaderPrivacyPolicyLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Header privacy policy link - " + regulation);
        Allure.step("Left click on the header privacy policy link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.headerPrivacyPolicyLink, "header privacy policy link", TestData.privacyPolicyUrl);
    }

    @Test(description = "TC 7.1.1 - Verify the Privacy Policy link works with right click")
    public void checkHeaderPrivacyPolicyLinkWithRightClickTest(){
        Allure.step("Right click on the header privacy policy link.");
        fortradePage.checkLinksWithRightClick(fortradePage.headerPrivacyPolicyLink, "header privacy policy link", TestData.privacyPolicyUrl);
    }

    @Test(description = "TC 7.2 - Verify the Terms and Conditions link works with left click")
    @Parameters({"regulation"})
    public void checkHeaderTermsAndConditionsLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Header terms and conditions link - " + regulation);
        Allure.step("Left click on the terms and conditions link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.headerTermsAndConditionsLink, "header terms and conditions link", TestData.termsAndConditionsUrl);
    }

    @Test(description = "TC 7.2.1 - Verify the Terms and Conditions link works with right click")
    public void checkHeaderTermsAndConditionsLinkWithRightClickTest(){
        Allure.step("Right click on the terms and conditions link.");
        fortradePage.checkLinksWithRightClick(fortradePage.headerTermsAndConditionsLink, "header terms and conditions link", TestData.termsAndConditionsUrl);
    }

    @Test(description = "TC 7.4 - Verify the Already have an account? link works with left click")
    @Parameters({"regulation"})
    public void checkAlreadyHaveAnAccountLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Already have an account link - " + regulation);
        Allure.step("Left click on the already have an account link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.alreadyHaveAnAccountLink, "already have an account link", TestData.alreadyHaveAnAccountUrl);
    }

    @Test(description = "TC 7.4.1 - Verify the Already have an account? link works with right click")
    public void checkAlreadyHaveAnAccountLinkWithRightClickTest(){
        Allure.step("Right click on the already have an account link.");
        fortradePage.checkLinksWithRightClick(fortradePage.alreadyHaveAnAccountLink, "already have an account link", TestData.alreadyHaveAnAccountUrl);
    }

    @Test(description = "TC 7.5 - Verify the click on Contact us link opens new mail window")
    @Parameters({"regulation"})
    public void checkContactUsLinkTest(String regulation){
        ScreenshotUtil.setCustomName("Contact Us link - " + regulation);
        Allure.step("Left click on the contact us link.");
        fortradePage.checkMailLinks(fortradePage.contactUsLink, "href", TestData.contactUsUrl);
    }

    @Test(description = "TC 7.6 - Verify the click on support@fortrade.com link opens email window")
    @Parameters({"regulation"})
    public void checkSupportLinkTest(String regulation){
        ScreenshotUtil.setCustomName("Support link - " + regulation);
        Allure.step("Right click on the contact us link.");
        fortradePage.checkMailLinks(fortradePage.supportLink, "href", TestData.supportUrl);
    }

    @Test(description = "TC 7.7 - Verify the Risk warning link works with left click")
    @Parameters({"regulation"})
    public void checkRiskWarningLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Risk warning link - " + regulation);
        Allure.step("Left click on the risk warning link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerRiskWarningLink, "risk warning link", TestData.riskWarningUrl);
    }

    @Test(description = "TC 7.7.1 - Verify the Risk warning link works with right click")
    public void checkRiskWarningLinkWithRightClickTest(){
        Allure.step("Right click on the risk warning link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerRiskWarningLink, "risk warning link", TestData.riskWarningUrl);
    }

    @Test(description = "TC 7.8 - Verify the Privacy policy link (in footer) works with left click")
    @Parameters({"regulation"})
    public void checkFooterPrivacyPolicyLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Footer privacy policy link - " + regulation);
        Allure.step("Left click on the footer privacy policy link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerPrivacyPolicyLink, "footer privacy policy link", TestData.privacyPolicyUrl);
    }

    @Test(description = "TC 7.8.1 - Verify the Privacy policy (in footer) link works with right click")
    public void checkFooterPrivacyPolicyLinkWithRightClickTest(){
        Allure.step("Right click on the footer privacy policy link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerPrivacyPolicyLink, "footer privacy policy link", TestData.privacyPolicyUrl);
    }

    @Test(description = "TC 8.1 - Verify the FRN: 609970 (FCA) link works with left click")
    @Parameters({"regulation"})
    public void checkFcaLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Fca link - " + regulation);
        Allure.step("Left click on the FCA link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerFCALink, "fca link", TestData.fcaUrl);
    }

    @Test(description = "TC 8.1.1 - Verify the FRN: 609970 (FCA) link works with right click")
    public void checkFcaLinkWithRightClickTest(){
        Allure.step("Right click on the FCA link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerFCALink, "fca link", TestData.fcaUrl);
    }

    @Test(description = "TC 8.2 - Verify the CRN: BC1148613 (IIROC) link works with left click")
    @Parameters({"regulation"})
    public void checkIirocLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Iiroc link - " + regulation);
        Allure.step("Left click on the IIROC link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerIIROCLink, "iiroc link", TestData.iirocUrl);
    }

    @Test(description = "TC 8.2.1 - Verify the CRN: BC1148613 (IIROC) link works with right click")
    public void checkIirocLinkWithRightClickTest(){
        Allure.step("Right click on the IIROC link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerIIROCLink, "iiroc link", TestData.iirocUrl);
    }

    @Test(description = "TC 8.3 - Verify the ABN: 33 614 683 831 | AFSL: 493520 (ASIC) link works with left click")
    @Parameters({"regulation"})
    public void checkAsicLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Asic link - " + regulation);
        Allure.step("Left click on the ASIC link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerASICLink, "asic link", TestData.asicUrl);
    }

    @Test(description = "TC 8.3.1 - Verify the ABN: 33 614 683 831 | AFSL: 493520 (ASIC) link works with right click")
    public void checkAsicLinkWithRightClickTest(){
        Allure.step("Right click on the ASIC link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerASICLink, "asic link", TestData.asicUrl);
    }

    @Test(description = "TC 8.4 - Verify the CIF license number 385/20 (CYSEC) link works with left click")
    @Parameters({"regulation"})
    public void checkCysecLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Cysec link - " + regulation);
        Allure.step("Left click on the CYSEC link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerCYSECLink, "cysec link", TestData.cysecUrl);
    }

    @Test(description = "TC 8.4.1 - Verify the CIF license number 385/20 (CYSEC) link works with right click")
    public void checkCysecLinkWithRightClickTest(){
        Allure.step("Right click on the CYSEC link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerCYSECLink, "cysec link", TestData.cysecUrl);
    }

    @Test(description = "TC 8.5 - Verify the GB21026472 (FSC) link works with left click")
    @Parameters({"regulation"})
    public void checkFscLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Fsc link - " + regulation);
        Allure.step("Left click on the FSC link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerFSCLink, "fsc link", TestData.fscUrl);
    }

    @Test(description = "TC 8.5.1 - Verify the GB21026472 (FSC) link works with right click")
    public void checkFscLinkWithRightClickTest(){
        Allure.step("Right click on the FSC link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerFSCLink, "fsc link", TestData.fscUrl);
    }

    @Test(description = "TC 8.6 - Verify the F009856 (DFSA) link works with left click")
    @Parameters({"regulation"})
    public void checkDfsaLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Dfsa link - " + regulation);
        Allure.step("Left click on the DFSA link.");
        fortradePage.checkLinksWithLeftClick(fortradePage.footerDFSALink, "dfsa link", TestData.dfsaUrl);
    }

    @Test(description = "TC 8.6.1 - Verify the F009856 (DFSA) link works with right click")
    public void checkDfsaLinkWithRightClickTest(){
        Allure.step("Right click on the DFSA link.");
        fortradePage.checkLinksWithRightClick(fortradePage.footerDFSALink, "dfsa link", TestData.dfsaUrl);
    }

    @Test(description = "9.1. Verify the border color in the CRM for regulation")
    @Parameters({"regulation","countryCode"})
    public void checkAccountRegulationTest(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Account regulation in CRM - Fortrade - " + regulation);
        String email = TestData.generateEmail();
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,"Testq Testa",regulation);
    }

    @Test(description = "9.2. Verify the account details is displayed correctly in the CRM")
    @Parameters({"regulation","countryCode"})
    public void checkAccountDetailsInCrmTest(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Account details in CRM - Fortrade - " + regulation);
        String email = TestData.generateEmail();
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,"Testq Testa",regulation);
    }


    @Test(description = "9.3. Verify the tags are displayed correctly in CRM")
    @Parameters({"regulation","countryCode"})
    public void checkTagsInCrmTest(String regulation,String countryCode){
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

    @Test(description = "TC 10.1. Verify the email is sent on the new account email")
    @Parameters({"regulation","tag", "countryCode"})
    public void emailIsReceived(String regulation, String tag,String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Email is received successfully - Fortrade page " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?"+tag);
        fortradePage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.canadaPhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        readyFortrade.clickUsePassBtn();
        openUrl(TestData.yopmailUrl);
        YopmailPage yopmailPage = new YopmailPage();
        yopmailPage.findEmail(email);
    }


    @Test(description = "TC 12.5. Verify that message 'We sent you the code again' is received")
    @Parameters({"regulation", "countryCode"})
    public void iDidntReceiveTheCodeTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("I didn't received the code link " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=sms-age-annual-saving-knowledge-plang:all&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        fortradePage.checkIDidntReceiveTheCodeLink();
    }

    @Test(description = "TC 12.6. Verify that wrong code cannot be submitted (negative test case)")
    @Parameters({"regulation", "countryCode"})
    public void wrongCodeCannotBeSubmittedTest(String regulation, String countryCode) {
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

    @Test(description = "TC 12.9. Verify if user clicks pencil icon the same is returned to the 1st widget")
    @Parameters({"regulation", "countryCode"})
    public void editPencilButtonTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Edit pencil button redirects to the first step " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=sms-age-annual-saving-knowledge-plang:all&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        fortradePage.checkEditPencilButton();
    }

    @Test(description = "TC 13.1. Verify the user is redirected to the 2nd step - age verification window")
    @Parameters({"regulation", "countryCode"})
    public void redirectionToTheSecondStepAgeTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (age) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=age&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        WaitUtil.waitForVisible(fortradePage.age);
        Assert.assertTrue(fortradePage.age.isDisplayed());
    }

    @Test(description = "TC 13.2. Verify the 2nd step - age verification window is successfully completed and submitted")
    @Parameters({"regulation", "countryCode"})
    public void registeredAgeParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (age) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=age&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "25-34", "age");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 13.3. Verify the 2nd step - age verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForAgeParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (age) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=age&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.checkErrorMessageForParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "25-34", "-- Select --", "age");
        fortradePage.assertSecondStepErrorMessage("age");
    }

    @Test(description = "TC 13.4. Verify the age value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void ageParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (age) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=age&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "25-34", "age");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "PC_windows,25_34_age");
    }

    @Test(description = "TC 13.5. Vefiry that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationAgeTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (age) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=age&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "25-34", "age");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 14.1. Verify the user is redirected to the 2nd step - annual verification window")
    @Parameters({"regulation", "countryCode"})
    public void redirectionToTheSecondStepAnnualTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (annual) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=annual&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        WaitUtil.waitForVisible(fortradePage.annual);
        Assert.assertTrue(fortradePage.annual.isDisplayed());
    }

    @Test(description = "TC 14.2. Verify the 2nd step - annual income verification window is successfully completed and submitted")
    @Parameters({"regulation", "countryCode"})
    public void registeredAnnualParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (annual) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=annual&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "$15,000-$50,000", "annual");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 14.3. Verify the 2nd step - annual income verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForAnnualParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (annual) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=annual&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.checkErrorMessageForParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "$15,000-$50,000", "-- Select --", "annual");
        fortradePage.assertSecondStepErrorMessage("annual");
    }

    @Test(description = "TC 14.4. Verify the annual value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void annualParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (annual) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=annual&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "$15,000-$50,000", "annual");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "PC_windows,15000_50000_annual");
    }

    @Test(description = "TC 14.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationAnnualTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (annual) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=annual&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "$15,000-$50,000", "annual");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 15.1. Verify the user is redirected to the 2nd step - saving and investments verification window")
    @Parameters({"regulation", "countryCode"})
    public void redirectionToTheSecondStepSavingTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (saving) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=saving&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        WaitUtil.waitForVisible(fortradePage.saving);
        Assert.assertTrue(fortradePage.saving.isDisplayed());
    }

    @Test(description = "TC 15.2. Verify the 2nd step -  value of saving and investments verification window is successfully completed and submitted")
    @Parameters({"regulation", "countryCode"})
    public void registeredSavingParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (saving) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=saving&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "$50,000-$100,000", "saving");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 15.3. Verify the 2nd step - value of saving and investments verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForSavingParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (saving) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=saving&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.checkErrorMessageForParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "$50,000-$100,000", "-- Select --", "saving");
        fortradePage.assertSecondStepErrorMessage("saving");
    }

    @Test(description = "TC 15.4. Verify the saving value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void savingParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (saving) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=saving&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "$50,000-$100,000", "saving");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "PC_windows,50000_100000_savings");
    }

    @Test(description = "TC 15.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationSavingTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (saving) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=saving&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "$50,000-$100,000", "saving");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }


    @Test(description = "TC 16.1. Verify the user is redirected to the 2nd step - knowledge of trading verification window")
    @Parameters({"regulation", "countryCode"})
    public void redirectionToTheSecondStepKnowledgeTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (knowledge) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=knowledge&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        WaitUtil.waitForVisible(fortradePage.knowledge);
        Assert.assertTrue(fortradePage.knowledge.isDisplayed());
    }

    @Test(description = "TC 16.2. Verify the 2nd step -  knowledge of trading verification window is successfully completed and submitted")
    @Parameters({"regulation", "countryCode"})
    public void registeredKnowledgeParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (knowledge) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=knowledge&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "All the above", "knowledge");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 16.3. Verify the 2nd step - knowledge of trading verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForKnowledgeParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (knowledge) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=knowledge&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.checkErrorMessageForParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "All the above", "-- Select --", "knowledge");
        fortradePage.assertSecondStepErrorMessage("knowledge");
    }

    @Test(description = "TC 16.4. Verify the knowledge of trading value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void knowledgeParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (knowledge) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=knowledge&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "All the above", "knowledge");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "PC_windows,knowledge_of_trading_all_the_above");
    }

    @Test(description = "TC 16.5. Vefiry that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationKnowledgeTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (knowledge) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=knowledge&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "All the above", "knowledge");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }


    @Test(description = "TC 17.1. Verify the user is redirected to the 2nd step - language verification window")
    @Parameters({"regulation", "countryCode"})
    public void redirectionToTheSecondStepLanguageTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (language) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=plang:all&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        WaitUtil.waitForVisible(fortradePage.language);
        Assert.assertTrue(fortradePage.language.isDisplayed());
    }

    @Test(description = "TC 17.2. Verify the 2nd step -  desired communication language verification window is successfully completed and submitted")
    @Parameters({"regulation", "countryCode"})
    public void registeredLanguageParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (language) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=language&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "English", "language");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 17.3. Verify the 2nd step - desired communication language verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForLanguageParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (language) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=plang:all&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.checkErrorMessageForParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "English", "-- Select --", "language");
        fortradePage.assertSecondStepErrorMessage("language");
    }

    @Test(description = "TC 17.4. Verify the desired communication language value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void languageParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (language) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=plang:all&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "English", "language");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "PC_windows,lang_EN");
    }

    @Test(description = "TC 17.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationLanguageTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (language) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=plang:all&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradePage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber(), "English", "language");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 18.1. Verify the account is registered successfully with NON-valid tag in the URL")
    @Parameters({"regulation","countryCode"})
    public void nonValidParameterInTheUrlTest(String regulation,String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Demo account is successfully registered with wrong parameters in the URL - " + regulation);
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=testq-testa");
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),countryCode,TestData.canadaPhoneNumber());
        readyFortrade.assertURL("https://ready.fortrade.com/");
        readyFortrade.clickUsePassBtn();
        readyFortrade.clickMenuBtn();
        readyFortrade.checkRegulation(regulation);
    }

    @Test(description = "TC 18.2. Verify the 2nd step cannot be submitted if all parameter values are not completed")
    @Parameters({"regulation","countryCode"})
    public void noDataOnTheSecondStepTest(String regulation,String countryCode) {
        ScreenshotUtil.setCustomName("Error messages (all parameters) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=age-annual-saving-knowledge-plang:all");
        fortradePage.fillTheFormOnTheSecondStepWithWrongData(TestData.firstName, TestData.lastName, email, countryCode, TestData.canadaPhoneNumber(), "25-34", "$15,000-$50,000", "$50,000-$100,000",
                "All the above", "English");
        fortradePage.assertSecondStepErrorMessageAllParameters();
    }

    @Test(description = "TC 19.1. Verify the cutom tag field in the CRM contains Dummy value ")
    @Parameters({"regulation","tag","countryCode"})
    public void dummyLeadRegistration(String regulation, String tag,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age-equals(1,3)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]&"+tag);
        fortradePage.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.canadaPhoneNumber(), "25-34", "$50,000-$100,000", "$50,000-$100,000",
                "All the above","English");
        fortradePage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 19.2. Verify the cutom tag field in the CRM contains Dummy value ")
    @Parameters({"regulation","tag","countryCode"})
    public void dummy_Lead_Registration(String regulation, String tag,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy - " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age-equals(1_3)-or-[saving-equals(1_2_3)-and-knowledge-notequals(5)]&"+tag);
        fortradePage.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.canadaPhoneNumber(), "25-34", "$15,000-$50,000", "$50,000-$100,000",
                "All the above","English");
        fortradePage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 19.3. Verify the custom tag field in the CRM contains Invalid value")
    @Parameters({"regulation","tag","countryCode"})
    public void invalidLeadRegistration(String regulation, String tag,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom tag - Invalid " + regulation + "regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age(1)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]&"+tag);
        fortradePage.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.canadaPhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "None","English");
        fortradePage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 19.4. Verify the custom tag field in the CRM contains Invalid value")
    @Parameters({"regulation","tag","countryCode"})
    public void invalid_Lead_Registration(String regulation,String tag, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom tag - Invalid - " + regulation + "regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age(1)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]&"+tag);
        fortradePage.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.canadaPhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "None","English");
        fortradePage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 19.5. Verify the custom tag field in the CRM is empty")
    @Parameters({"regulation","tag","countryCode"})
    public void emptyLeadRegistration(String regulation, String tag, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age-equals(1,3)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]&"+tag);
        fortradePage.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.canadaPhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "All the above","English");
        fortradePage.assertURL("https://ready.fortrade.com/");
        crmPage.checkCrmData(email, TestData.fullName, regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.6. Verify the custom tag field in the CRM is empty")
    @Parameters({"regulation","tag","countryCode"})
    public void empty_Lead_Registration(String regulation, String tag,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty - " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age-equals(1,3)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]&"+tag);
        fortradePage.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.canadaPhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "All the above","English");
        fortradePage.assertURL("https://ready.fortrade.com/");
        crmPage.checkCrmData(email, TestData.fullName, regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.7. Verify the custom tag field in the CRM is Dummy if ftsquery device parameter is same as device " +
            "and OS used for demo account registration")
    @Parameters({"regulation","tag","countryCode"})
    public void deviceIsSameAsParameter(String regulation, String tag,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy - device " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?ftsquery=device-equals(1)&"+tag);
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?ftsquery=device-equals(1)&"+tag);
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        fortradePage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("PC_windows");
        crmPage.checkCustomTag("Dummy");
    }
    @Test(description = "TC 19.8. Verify the custom tag field in the CRM is invalid if syntax is not valid")
    @Parameters({"regulation","tag","countryCode"})
    public void nonValidParameterSyntax(String regulation, String tag,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Invalid - device " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?ftsquery=device(1)&"+tag);
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?ftsquery=device(1)&"+tag);
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        fortradePage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("PC_windows");
        crmPage.checkCustomTag("Invalid");
    }
    @Test(description = "TC 19.9. Verify the custom tag field in the CRM is empty if ftsquery device parameter is not " +
            "same as device and OS used for demo account registration")
    @Parameters({"regulation","tag","countryCode"})
    public void deviceIsNotSameAsParameter(String regulation, String tag,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty - device " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?ftsquery=device-equals(4)&"+tag);
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?ftsquery=device-equals(4)&"+tag);
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        fortradePage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("PC_windows");
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.10. Verify the custom tag field in the CRM is invalid if ftsquery device parameter " +
            "contains device and OS non valid index value")
    @Parameters({"regulation","tag","countryCode"})
    public void deviceParameterContainsNonValidValue(String regulation, String tag,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - non valid device value " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?ftsquery=device-equals(0)&"+tag);
        openUrl("https://dlp.fortrade.com/lps/premium-forex-landing-ephone-ca/en?ftsquery=device-equals(0)&"+tag);
        fortradePage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        fortradePage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("PC_windows");
        crmPage.checkCustomTag("Invalid");
    }
}

