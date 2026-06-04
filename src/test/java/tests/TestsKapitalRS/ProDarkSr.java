package tests.TestsKapitalRS;

import core.actions.ElementActions;
import core.base.BaseTest;
import core.config.ConfigReader;
import core.pages.*;
import core.utils.ScreenshotUtil;
import core.waitsManagement.WaitUtil;
import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import testdata.TestData;

import java.awt.*;
import java.io.IOException;

public class ProDarkSr extends BaseTest {

    private KapitalRSPage kapitalRSPage;
    private ReadyFortrade readyFortrade;
    private CrmPage crmPage;
    private YopmailPage yopmailPage;

    @BeforeMethod
    public void initPages() {
        kapitalRSPage = new KapitalRSPage();
        readyFortrade = new ReadyFortrade();
        crmPage = new CrmPage();
        yopmailPage = new YopmailPage();
        openUrl(ConfigReader.getBaseUrl());
    }

    @Test(description = "TC 1.2.1 - Verify the logo is not clickable with left click")
    public void logoClickabilityTest() {
        ScreenshotUtil.setCustomName("Logo is not clickable - KapitalRS");
        Allure.step("Tried to click on KapitalRS logo");
        kapitalRSPage.checkLogoClickability();
        kapitalRSPage.assertURL("https://dlp.kapitalrs.com/lps/pro-dark/sr");
    }

    @Test(description = "TC 2.1. Verify the demo account is registered successfully with valid data")
    @Parameters({"countryCode"})
    public void demoAccountRegistrationTest(String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Demo account is successfully registered - KapitalRS");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, TestData.generateEmail(), countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL("https://pro.kapitalrs.com/");
        readyFortrade.clickUsePassBtn();
        readyFortrade.clickMenuBtn();
        readyFortrade.checkRegulation("FSC");
    }

    @Test(description = "TC 3.1. Verify that the account cannot be registered with already registered email address")
    @Parameters({"countryCode"})
    public void alreadyRegisteredEmailAddressTest(String countryCode) {
        ScreenshotUtil.setCustomName("Already registered email address -KapitalRS");
        Allure.step("Redirected to https://dlp.kapitalrs.com/lps/pro-dark/sr");
        String email = TestData.generateEmail();
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        openUrl(ConfigReader.getBaseUrl());
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        kapitalRSPage.assertAlrRegEmailErrorMsg();
    }

    @Test(description = "TC 3.2 Verify the demo account is not registered successfully with invalid data")
    @Parameters({"countryCode"})
    public void nonValidDataTest(String countryCode) {
        ScreenshotUtil.setCustomName("Non valid data - KapitalRS");
        Allure.step("Redirected to https://dlp.kapitalrs.com/lps/pro-dark/sr");
        kapitalRSPage.registerDemoAccount("123", "574", "abcd134324", countryCode, "fsfsdfd");
        kapitalRSPage.assertErrorMessages();
    }

    @Test(description = "TC 3.3. Verify that the account cannot be registered with already registered phone number")
    @Parameters({"countryCode"})
    public void alreadyRegisteredPhoneNumberTest(String countryCode) {
        ScreenshotUtil.setCustomName("Already registered phone number - KapitalRS");
        Allure.step("Redirected to https://dlp.kapitalrs.com/lps/pro-dark/sr");
        String phoneNumber = TestData.generatePhoneNumber();
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, TestData.generateEmail(), countryCode, phoneNumber);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        openUrl(ConfigReader.getBaseUrl());
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, TestData.generateEmail(), countryCode, phoneNumber);
        kapitalRSPage.assertErrMsgForAlreadyRegisteredAccount();
    }

    @Test(description = "TC 3.4. Verify that the account cannot be registered with already registered email address and phone number")
    @Parameters({"countryCode"})
    public void alreadyRegisteredEmailAndPhoneTest(String countryCode) {
        ScreenshotUtil.setCustomName("Already registered email address and phone number - KapitalRS");
        Allure.step("Redirected to https://dlp.kapitalrs.com/lps/pro-dark/sr");
        String email = TestData.generateEmail();
        String phone = TestData.generatePhoneNumber();
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, phone);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        openUrl(ConfigReader.getBaseUrl());
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, phone);
        kapitalRSPage.assertAlrRegEmailErrorMsg();
    }

    @Test(description = "TC 3.5. Verify the demo account is not registered successfully with empty fields")
    public void emptyDataRegistrationTest() {
        ScreenshotUtil.setCustomName("Unsuccessfully account registration with empty data - KapitalRS");
        Allure.step("Redirected to https://dlp.kapitalrs.com/lps/pro-dark/sr");
        kapitalRSPage.clickGetStartedBtn();
        //kapitalRSPage.assertErrorMessages();
        kapitalRSPage.assertBorderColor(kapitalRSPage.borderColorForFirstName, "border-color", TestData.redBorderColor);
        kapitalRSPage.assertFirstStepErrorMessage(TestData.firstNameErrorMessageKapitalRS);
    }

    @Test(description = "TC 4.1 - Verify that the invalid data for the First Name field will show valid error message with red border")
    public void verifyErrorMessageForFirstNameTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for First Name - KapitalRS");
        Allure.step("Verified error message and border color for First Name field");
        ElementActions.type(kapitalRSPage.firstName, "123", "first name");
        kapitalRSPage.assertBorderColor(kapitalRSPage.borderColorForFirstName, "border-color", TestData.redBorderColor);
        kapitalRSPage.assertFirstStepErrorMessage(TestData.firstNameErrorMessageKapitalRS);
    }

    @Test(description = "TC 4.2 - Verify that the invalid data for the Last Name field will show valid error message with red border")
    public void verifyErrorMessageForLastNameTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Last Name - KapitalRS");
        Allure.step("Verified error message and border color for Last Name field");
        ElementActions.type(kapitalRSPage.lastName, "456", "last name");
        kapitalRSPage.assertBorderColor(kapitalRSPage.borderColorForLastName, "border-color", TestData.redBorderColor);
        kapitalRSPage.assertFirstStepErrorMessage(TestData.lastNameErrorMessageKapitalRS);
    }

    @Test(description = "TC 4.3 - Verify that the invalid data for the Email field will show valid error message with red border")
    public void verifyErrorMessageForEmailTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Email - KapitalRS");
        Allure.step("Verified error message and border color for Email field");
        ElementActions.type(kapitalRSPage.email, "dsv124234/=", "email");
        kapitalRSPage.assertBorderColor(kapitalRSPage.borderColorForEmail, "border-color", TestData.redBorderColor);
        kapitalRSPage.assertFirstStepErrorMessage(TestData.emailErrorMessageKapitalRS);
    }

    @Test(description = "TC 4.5 - Verify that the invalid data for Phone field will show valid error message with red border")
    public void verifyErrorMessageForPhoneTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Phone - FortadeR");
        Allure.step("Verified error message and border color for Phone field");
        ElementActions.type(kapitalRSPage.phoneNumber, "0034334424558200", "phone");
        kapitalRSPage.assertBorderColor(kapitalRSPage.borderColorForPhone, "border-color", TestData.redBorderColor);
        kapitalRSPage.assertFirstStepErrorMessage(TestData.wrongPhoneErrorMsgKapitalRS);
    }

    @Test(description = "TC 4.6 - Verify that the Last Name cannot be the same as First name.")
    public void sameFNameAndLNameTest() {
        ScreenshotUtil.setCustomName("Your first name must be different from your last name - KapitalRS");
        Allure.step("Check for error message for the same first and last name.");
        kapitalRSPage.insertFirstName("Test");
        kapitalRSPage.insertLastName("Test");
        ElementActions.click(kapitalRSPage.email, "email address");
        kapitalRSPage.assertFirstStepErrorMessage(TestData.sameFirstNameErrorMessageKapitalRS);
        kapitalRSPage.assertFirstStepErrorMessage(TestData.sameLastNameErrorMessageKapitalRS);
    }

    @Test(description = "TC 4.7 - Verify that the First Name cannot be the same as Last name.")
    public void sameLNameAndFNameTest() {
        ScreenshotUtil.setCustomName("Your last name must be different from your first name - KapitalRS");
        Allure.step("Check for error message for the same last and first name.");
        kapitalRSPage.insertLastName("Test");
        kapitalRSPage.insertFirstName("Test");
        ElementActions.click(kapitalRSPage.email, "email address");
        kapitalRSPage.assertFirstStepErrorMessage(TestData.sameLastNameErrorMessageKapitalRS);
        kapitalRSPage.assertFirstStepErrorMessage(TestData.sameFirstNameErrorMessageKapitalRS);
    }

    @Test(description = "TC 7.1 - Verify the Privacy Policy link works with left click")
    public void checkHeaderPrivacyPolicyLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Header privacy policy link - KapitalRS");
        Allure.step("Left click on the header privacy policy link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.headerPolitikaPrivatnostiLink, "header privacy policy link", TestData.fortraderPrivacyPolicyUrl);
    }

    @Test(description = "TC 7.1.1 - Verify the Privacy Policy link works with right click")
    public void checkHeaderPrivacyPolicyLinkWithRightClickTest() {
        Allure.step("Right click on the header privacy policy link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.headerPolitikaPrivatnostiLink, "header privacy policy link", TestData.fortraderPrivacyPolicyUrl);
    }

    @Test(description = "TC 7.2 - Verify the Terms and Conditions link works with left click")
    public void checkHeaderTermsAndConditionsLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Header terms and conditions link - KapitalRS");
        Allure.step("Left click on the terms and conditions link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.headerUsloviIOdredbeLink, "header terms and conditions link", TestData.fortraderTermsAndCondUrl);
    }

    @Test(description = "TC 7.2.1 - Verify the Terms and Conditions link works with right click")
    public void checkHeaderTermsAndConditionsLinkWithRightClickTest() {
        Allure.step("Right click on the terms and conditions link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.headerUsloviIOdredbeLink, "header terms and conditions link", TestData.fortraderTermsAndCondUrl);
    }

    @Test(description = "TC 7.3. Verify the click here link works with left click")
    public void checkClickHereTest() {
        ScreenshotUtil.setCustomName("CLick here link - KapitalRS");
        Allure.step("Left mouse click on click here link");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.klikniteOvdeLink, "click here", TestData.fortraderClickHereURL);
    }

    @Test(description = "TC 7.3.1 Verify the click here link works with right click")
    public void checkClickHereWithRightClick() {
        Allure.step("Right mouse click on the click here link");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.klikniteOvdeLink, "click here", TestData.fortraderClickHereURL);
    }

    @Test(description = "TC 7.4 - Verify the Already have an account? link works with left click")
    public void checkAlreadyHaveAnAccountLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Already have an account link - KapitalRS");
        Allure.step("Left click on the already have an account link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.vecImateNalogLink, "already have an account link", TestData.alreadyHaveAnAccountUrlKapitalRS);
    }

    @Test(description = "TC 7.4.1 - Verify the Already have an account? link works with right click")
    public void checkAlreadyHaveAnAccountLinkWithRightClickTest() {
        Allure.step("Right click on the already have an account link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.vecImateNalogLink, "already have an account link", TestData.alreadyHaveAnAccountUrlKapitalRS);
    }

    @Test(description = "TC 7.5 - Verify the click on Contact us link opens new mail window")
    public void checkContactUsLinkTest() {
        ScreenshotUtil.setCustomName("Contact Us link - KapitalRS");
        Allure.step("Left click on the contact us link.");
        kapitalRSPage.checkMailLinks(kapitalRSPage.kontaktirajteNasLink, "href", TestData.kontaktirajteNasLink);
    }

    @Test(description = "TC 7.7 - Verify the Risk warning link works with left click")
    public void checkFooterRiskWarningLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Footer risk warning link - KapitalRS");
        Allure.step("Left click on the risk warning link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.footerUpozorenjeORizikuLink, "footer risk warning link", TestData.riskWarningKapitalRS);
    }

    @Test(description = "TC 7.7.1 - Verify the Risk warning link works with right click")
    public void checkFooterRiskWarningLinkWithRightClickTest() {
        Allure.step("Right click on the risk warning link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.footerUpozorenjeORizikuLink, "footer risk warning link", TestData.riskWarningKapitalRS);
    }

    @Test(description = "TC 7.8 - Verify the Privacy policy link (in footer) works with left click")
    public void checkFooterPrivacyPolicyLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Footer privacy policy link - KapitalRS");
        Allure.step("Left click on the footer privacy policy link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.footerPolitikaPrivatnostiLink, "footer privacy policy link", TestData.fortraderPrivacyPolicyUrl);
    }

    @Test(description = "TC 7.8.1 - Verify the Privacy policy (in footer) link works with right click")
    public void checkFooterPrivacyPolicyLinkWithRightClickTest() {
        Allure.step("Right click on the footer privacy policy link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.footerPolitikaPrivatnostiLink, "footer privacy policy link", TestData.fortraderPrivacyPolicyUrl);
    }

    @Test(description = "TC 8.1 - Verify the FRN: 609970 (FCA) link works with left click")
    public void checkFcaLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Fca link - KapitalRS");
        Allure.step("Left click on the Fca link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.footerFCALink, "fsc link", TestData.fcaUrl);
    }

    @Test(description = "TC 8.1.1 - Verify the FRN: 609970 (FCA) link works with right click")
    public void checkFcaLinkWithRightClickTest() {
        Allure.step("Right click on the Fca link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.footerFCALink, "fsc link", TestData.fcaUrl);
    }

    @Test(description = "TC 8.3 - Verify the ABN: 33 614 683 831 | AFSL: 493520 (ASIC) link works with left click")
    public void checkAsicLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Asic link - KapitalRS");
        Allure.step("Left click on the Asic link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.footerASICLink, "fsc link", TestData.asicUrl);
    }

    @Test(description = "TC 8.3.1 - Verify the ABN: 33 614 683 831 | AFSL: 493520 (ASIC) link works with right click")
    public void checkAsicLinkWithRightClickTest() {
        Allure.step("Right click on the Asic link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.footerASICLink, "fsc link", TestData.asicUrl);
    }

    @Test(description = "TC 8.5 - Verify the GB21026472 (FSC) link works with left click")
    public void checkFscLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Fsc link - KapitalRS");
        Allure.step("Left click on the FSC link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.footerFSCLink, "fsc link", TestData.fscUrl);
    }

    @Test(description = "TC 8.5.1 - Verify the GB21026472 (FSC) link works with right click")
    public void checkFscLinkWithRightClickTest() {
        Allure.step("Right click on the FSC link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.footerFSCLink, "fsc link", TestData.fscUrl);
    }

    @Test(description = "9.1. Verify the border color in the CRM for regulation")
    @Parameters({"countryCode"})
    public void checkAccountRegulationTest(String countryCode) {
        ScreenshotUtil.setCustomName("Account regulation in CRM - KapitalRS");
        String email = TestData.generateEmail();
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, "Testq Testa", "FSC");
    }

    @Test(description = "9.2. Verify the account details is displayed correctly in the CRM")
    @Parameters({"countryCode"})
    public void checkAccountDetailsInCrmTest(String countryCode) {
        ScreenshotUtil.setCustomName("Account details in CRM - KapitalRS");
        String email = TestData.generateEmail();
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, "Testq Testa", "FSC");
    }

    @Test(description = "9.3. Verify the tags are displayed correctly in CRM")
    @Parameters({"countryCode"})
    public void checkTagsInCrmTest(String countryCode) {
        ScreenshotUtil.setCustomName("Marketing tags KapitalRS page ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=age-annual-saving-knowledge-plang:all&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(),
                "25-34", "$15,000-$50,000", "$50,000-$100,000", "Sve navedeno", "Engleski");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, "Testq Testa", "FSC");
        crmPage.checkCrmTags();
    }

    @Test(description = "9.4. Verify that the Link ID field contains 'PC_windows' value in the CRM")
    @Parameters({"countryCode"})
    public void checkLinkIDPCWindows(String countryCode){
        ScreenshotUtil.setCustomName("Link ID tag contains the 'PC_windows' value - KapitalRS page");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr");
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.canadaPhoneNumber());
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,"FSC");
        crmPage.checkLinkIdValue("PC_windows");
    }

    @Test(description = "TC 10.1. Verify the email is sent on the new account email")
    @Parameters({"countryCode"})
    public void emailIsReceived(String countryCode) {
        ScreenshotUtil.setCustomName("Email is received successfully - KapitalRS page ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.kapitalrs.com/lps/pro-dark/sr");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        readyFortrade.clickUsePassBtn();
        openUrl(TestData.yopmailUrl);
        yopmailPage.findEmailKRS(email);
    }

    @Test(description = "TC 12.5. Verify that message 'We sent you the code again' is received")
    @Parameters({"countryCode"})
    public void iDidntReceiveTheCodeTest(String countryCode) {
        ScreenshotUtil.setCustomName("I didn't received the code link - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=sms");
        kapitalRSPage.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        kapitalRSPage.checkIDidntReceiveTheCodeLinkKapitalRS();
    }

    @Test(description = "TC 12.6. Verify that wrong code cannot be submitted (negative test case)")
    @Parameters({"countryCode"})
    public void wrongCodeCannotBeSubmittedTest(String countryCode) {
        ScreenshotUtil.setCustomName("Wrong code cannot be submitted - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=sms-age-annual-saving-knowledge-plang:all");
        kapitalRSPage.fillTheFormOnTheSecondStepWithWrongSmsCode(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(),
                "25-34", "$15,000-$50,000", "$50,000-$100,000", "Sve navedeno", "Engleski",
                "1", "1", "1", "1");
        kapitalRSPage.assertErrorMessageForWrongSmsCodeKapitalRS();
    }

    @Test(description = "TC 12.9. Verify if user clicks pencil icon the same is returned to the 1st widget")
    @Parameters({"countryCode"})
    public void editPencilButtonTest(String countryCode) {
        ScreenshotUtil.setCustomName("Edit pencil button redirects to the first step - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=sms");
        kapitalRSPage.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        kapitalRSPage.checkEditPencilButton();
    }

    @Test(description = "TC 13.1. Verify the user is redirected to the 2nd step - age verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepAgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (age) - KapitalRS ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=age");
        kapitalRSPage.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(kapitalRSPage.age);
        Assert.assertTrue(kapitalRSPage.age.isDisplayed());
    }

    @Test(description = "TC 13.2. Verify the 2nd step - age verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredAgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (age) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=age");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "age");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
    }

    @Test(description = "TC 13.3. Verify the 2nd step - age verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForAgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (age) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=age");
        kapitalRSPage.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "-- Izaberite --", "age");
        kapitalRSPage.assertSecondStepErrorMessage("age");
    }

    @Test(description = "TC 13.4. Verify the age value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void ageParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (age) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=age");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "age");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkParameterLinkIdInTheCRM(email, "PC_windows,25_34_age");
    }

    @Test(description = "TC 13.5. Vefiry that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationAgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (age) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=age");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "age");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 14.1. Verify the user is redirected to the 2nd step - annual verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepAnnualTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (annual) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=annual");
        kapitalRSPage.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(kapitalRSPage.annual);
        Assert.assertTrue(kapitalRSPage.annual.isDisplayed());
    }

    @Test(description = "TC 14.2. Verify the 2nd step - annual income verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredAnnualParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (annual) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=annual");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "annual");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
    }

    @Test(description = "TC 14.3. Verify the 2nd step - annual income verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForAnnualParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (annual) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=annual");
        kapitalRSPage.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "-- Izaberite --", "annual");
        kapitalRSPage.assertSecondStepErrorMessage("annual");
    }

    @Test(description = "TC 14.4. Verify the annual value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void annualParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (annual) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=annual");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "annual");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkParameterLinkIdInTheCRM(email, "PC_windows,15000_50000_annual");
    }

    @Test(description = "TC 14.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationAnnualTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (annual) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=annual");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "annual");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 15.1. Verify the user is redirected to the 2nd step - saving and investments verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepSavingTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (saving) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=saving");
        kapitalRSPage.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(kapitalRSPage.saving);
        Assert.assertTrue(kapitalRSPage.saving.isDisplayed());
    }

    @Test(description = "TC 15.2. Verify the 2nd step -  value of saving and investments verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredSavingParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (saving) -KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=saving");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "saving");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
    }

    @Test(description = "TC 15.3. Verify the 2nd step - value of saving and investments verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForSavingParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (saving) -KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=saving");
        kapitalRSPage.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "-- Izaberite --", "saving");
        kapitalRSPage.assertSecondStepErrorMessage("saving");
    }

    @Test(description = "TC 15.4. Verify the saving value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void savingParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (saving) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=saving");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "saving");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkParameterLinkIdInTheCRM(email, "PC_windows,50000_100000_savings");
    }

    @Test(description = "TC 15.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationSavingTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (saving) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=saving");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "saving");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 16.1. Verify the user is redirected to the 2nd step - knowledge of trading verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepKnowledgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (knowledge) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=knowledge");
        kapitalRSPage.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(kapitalRSPage.knowledge);
        Assert.assertTrue(kapitalRSPage.knowledge.isDisplayed());
    }

    @Test(description = "TC 16.2. Verify the 2nd step -  knowledge of trading verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredKnowledgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (knowledge) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=knowledge&");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "Sve navedeno", "knowledge");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
    }

    @Test(description = "TC 16.3. Verify the 2nd step - knowledge of trading verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForKnowledgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (knowledge) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=knowledge");
        kapitalRSPage.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "Sve navedeno", "-- Izaberite --", "knowledge");
        kapitalRSPage.assertSecondStepErrorMessage("knowledge");
    }

    @Test(description = "TC 16.4. Verify the knowledge of trading value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void knowledgeParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (knowledge) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=knowledge");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "Sve navedeno", "knowledge");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkParameterLinkIdInTheCRM(email, "PC_windows,knowledge_of_trading_all_the_above");
    }

    @Test(description = "TC 16.5. Vefiry that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationKnowledgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (knowledge) -KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=knowledge");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "Sve navedeno", "knowledge");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 17.1. Verify the user is redirected to the 2nd step - language verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepLanguageTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (language) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=plang:all");
        kapitalRSPage.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(kapitalRSPage.language);
        Assert.assertTrue(kapitalRSPage.language.isDisplayed());
    }

    @Test(description = "TC 17.2. Verify the 2nd step -  desired communication language verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredLanguageParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (language) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=plang:all");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "Engleski", "language");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
    }

    @Test(description = "TC 17.3. Verify the 2nd step - desired communication language verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForLanguageParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (language) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=plang:all");
        kapitalRSPage.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "Engleski", "-- Izaberite --", "language");
        kapitalRSPage.assertSecondStepErrorMessage("language");
    }

    @Test(description = "TC 17.4. Verify the desired communication language value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void languageParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (language) -KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=plang:all");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "Engleski", "language");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkParameterLinkIdInTheCRM(email, "PC_windows,lang_EN");
    }

    @Test(description = "TC 17.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationLanguageTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (language) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=plang:all");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "Engleski", "language");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 18.1. Verify the account is registered successfully with NON-valid tag in the URL")
    @Parameters({"countryCode"})
    public void nonValidParameterInTheUrlTest(String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Demo account is successfully registered with wrong parameters in the URL - KapitalRS");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=testq-testa");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, TestData.generateEmail(), countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL("https://pro.kapitalrs.com/");
        readyFortrade.clickUsePassBtn();
        readyFortrade.clickMenuBtn();
        readyFortrade.checkRegulation("FSC");
    }

    @Test(description = "TC 18.2. Verify the 2nd step cannot be submitted if all parameter values are not completed")
    @Parameters({"countryCode"})
    public void noDataOnTheSecondStepTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error messages (all parameters) - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=age-annual-saving-knowledge-plang:all");
        kapitalRSPage.fillTheFormOnTheSecondStepWithWrongData(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "$15,000-$50,000", "$50,000-$100,000",
                "Sve navedeno", "Engleski");
        kapitalRSPage.assertSecondStepErrorMessageAllParameters();
    }

    @Test(description = "TC 19.1. Verify the cutom tag field in the CRM contains Dummy value ")
    @Parameters({"countryCode"})
    public void dummyLeadRegistration(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age-equals(1,3)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode,
                TestData.generatePhoneNumber(), "25-34", "$50,000-$100,000", "$50,000-$100,000",
                "Sve navedeno", "Engleski");
        kapitalRSPage.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 19.2. Verify the cutom tag field in the CRM contains Dummy value ")
    @Parameters({"countryCode"})
    public void dummy_Lead_Registration(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag with mark - Dummy - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age-equals(1_3)-or-[saving-equals(1_2_3)-and-knowledge-notequals(5)]");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode,
                TestData.generatePhoneNumber(), "25-34", "$15,000-$50,000", "$50,000-$100,000",
                "Sve navedeno", "Engleski");
        kapitalRSPage.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 19.3. Verify the custom tag field in the CRM contains Invalid value")
    @Parameters({"countryCode"})
    public void invalidLeadRegistration(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom tag - Invalid - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age(1)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "Ništa od navedenog", "Engleski");
        kapitalRSPage.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 19.4. Verify the custom tag field in the CRM contains Invalid value")
    @Parameters({"countryCode"})
    public void invalid_Lead_Registration(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom tag with mark - Invalid - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age(1)-or-[saving-equals(1_2_3)-and-knowledge-notequals(5)]");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "Ništa od navedenog", "Engleski");
        kapitalRSPage.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 19.5. Verify the custom tag field in the CRM is empty")
    @Parameters({"countryCode"})
    public void emptyLeadRegistration(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty  - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age-equals(1,3)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "Sve navedeno", "Engleski");
        kapitalRSPage.assertURL("https://pro.kapitalrs.com/");
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.6. Verify the custom tag field in the CRM is empty")
    @Parameters({"countryCode"})
    public void empty_Lead_Registration(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag with mark - Empty - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age-equals(1_3)-or-[saving-equals(1_2_3)-and-knowledge-notequals(5)]");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "Sve navedeno", "Engleski");
        kapitalRSPage.assertURL("https://pro.kapitalrs.com/");
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.7. Verify the custom tag field in the CRM is Dummy if ftsquery device parameter is same as device " +
            "and OS used for demo account registration")
    @Parameters({"countryCode"})
    public void deviceIsSameAsParameter(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy - device KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.kapitalrs.com/lps/pro-dark/sr?ftsquery=device-equals(1)");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?ftsquery=device-equals(1)");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        kapitalRSPage.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 19.8. Verify the custom tag field in the CRM is invalid if syntax is not valid")
    @Parameters({"countryCode"})
    public void nonValidParameterSyntax(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Invalid - device - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.kapitalrs.com/lps/pro-dark/sr?ftsquery=device(1)");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?ftsquery=device(1)");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        kapitalRSPage.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkLinkIdValue("PC_windows");
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 19.9. Verify the custom tag field in the CRM is empty if ftsquery device parameter is not " +
            "same as device and OS used for demo account registration")
    @Parameters({"countryCode"})
    public void deviceIsNotSameAsParameter(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty - device KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.kapitalrs.com/lps/pro-dark/sr?ftsquery=device-equals(4)");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?ftsquery=device-equals(4)");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        kapitalRSPage.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.10. Verify the custom tag field in the CRM is invalid if ftsquery device parameter " +
            "contains device and OS non valid index value")
    @Parameters({"countryCode"})
    public void deviceParameterContainsNonValidValue(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - non valid device value - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.kapitalrs.com/lps/pro-dark/sr?ftsquery=device-equals(0)");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?ftsquery=device-equals(0)");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        kapitalRSPage.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 20.3. Verify that the Custom Tag in the CRM is empty")
    @Parameters({"countryCode"})
    public void checkingTheCustomTag(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 20-3-Custom Tag is empty KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=age-annual-saving-knowledge-plang:all");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?fts=age-annual-saving-knowledge-plang:all");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "Sve navedeno", "Engleski");
        kapitalRSPage.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 22.1. Verify that the Language field in the CRM contains expected value (in this case FR)")
    @Parameters({"countryCode"})
    public void checkLanguageFieldContainsExpectedValue(String countryCode) {
        ScreenshotUtil.setCustomName("Language field in the CRM contains expected (EN) value - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.kapitalrs.com/lps/pro-dark/sr");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?userLang=EN");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        readyFortrade.clickUsePassBtn();
        readyFortrade.assertDisplayedLanguage("EN");
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkLanguageField(email, "en");
    }

    @Test(description = "TC 22.2. Verify that the Language field in the CRM contains the default value (the language of the base page URL) when we enter the wrong language in the userLang parameter")
    @Parameters({"countryCode"})
    public void checkLanguageFieldContainsDefaultValue(String countryCode) {
        ScreenshotUtil.setCustomName("Language field in the CRM contains default (SR) value - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.kapitalrs.com/lps/pro-dark/sr");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?userLang=FRA");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        readyFortrade.assertDisplayedLanguage("SR");
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkLanguageField(email, "sr");
    }

    @Test(description = "TC 23.1. Verify that the Custom Tag field in the CRM contains the DummyP value")
    @Parameters({"countryCode"})
    public void dummypParameter(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-1-Custom tag-DummyP value-KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.kapitalrs.com/lps/pro-dark/sr" +
                "?ftsquery=device-equals(1)&dummyP=1");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?ftsquery=device-equals(1)&dummyP=1");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        kapitalRSPage.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("DummyP");
    }

    @Test(description = "TC 23.2. Verify that the Custom Tag field in the CRM contains the Dummy value")
    @Parameters({"countryCode"})
    public void dummyParameter(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-2-Custom tag-Dummy value-KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.kapitalrs.com/lps/pro-dark/sr" +
                "?ftsquery=device-equals(1)&dummyP=0");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?ftsquery=device-equals(1)&dummyP=0");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        kapitalRSPage.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 23.3. Verify that the dummyP parameter is ignored when it's not correctly typed in the URL")
    @Parameters({"countryCode"})
    public void parameterDummy(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-3- Custom tag-Dummy value-KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.kapitalrs.com/lps/pro-dark/sr" +
                "?ftsquery=device-equals(1)&dummyp=1");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?ftsquery=device-equals(1)&dummyp=1");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        kapitalRSPage.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.1. Verify that the custom tag field in the CRM contains Dummy parameter ")
    @Parameters({"countryCode"})
    public void customTagContainsDummy(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-1-Custom tag field-dummy-KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.kapitalrs.com/lps/pro-dark/sr?Dummy=true");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?Dummy=true");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        kapitalRSPage.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.2. Verify that the custom tag field in the CRM contains Dummy parameter ")
    @Parameters({"countryCode"})
    public void customTagContainsDummyValue(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-2-Custom tag field-dummy-KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.kapitalrs.com/lps/pro-dark/sr?Dummy=1");
        openUrl("https://dlp.kapitalrs.com/lps/pro-dark/sr?Dummy=1");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        kapitalRSPage.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Dummy");
    }
}
