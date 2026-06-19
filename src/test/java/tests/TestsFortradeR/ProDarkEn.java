package tests.TestsFortradeR;

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

public class ProDarkEn extends BaseTest {

    private FortradeRPage fortradeRPage;
    private ReadyFortrade readyFortrade;
    private CrmPage crmPage;
    private YopmailPage yopmailPage;

    @BeforeMethod
    public void initPages() {
        fortradeRPage = new FortradeRPage();
        readyFortrade = new ReadyFortrade();
        crmPage = new CrmPage();
        yopmailPage = new YopmailPage();
        openUrl(ConfigReader.getBaseUrl());
    }

    @Test(description = "TC 1.2.1 - Verify the logo is not clickable with left click")
    public void logoClickabilityTest() {
        ScreenshotUtil.setCustomName("Logo is not clickable - FortradeR");
        Allure.step("Tried to click on FortradeR logo");
        fortradeRPage.checkLogoClickability();
        fortradeRPage.assertURL("https://dlp.fortrader.com/lps/pro-dark/en");
    }

    @Test(description = "TC 2.1. Verify the demo account is registered successfully with valid data")
    @Parameters({"countryCode"})
    public void demoAccountRegistrationTest(String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Demo account is successfully registered - FortradeR");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, TestData.generateEmail(), countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL("https://ready.fortrade.com/");
        readyFortrade.clickUsePassBtn();
        readyFortrade.clickMenuBtn();
        readyFortrade.checkRegulation("FSC");
    }

    @Test(description = "TC 3.1. Verify that the account cannot be registered with already registered email address")
    @Parameters({"countryCode"})
    public void alreadyRegisteredEmailAddressTest(String countryCode) {
        ScreenshotUtil.setCustomName("Already registered email address -FortradeR");
        Allure.step("Redirected to https://dlp.fortrader.com/lps/pro-dark/en");
        String email = TestData.generateEmail();
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        openUrl(ConfigReader.getBaseUrl());
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        fortradeRPage.assertAlrRegEmailErrorMsg();
    }

    @Test(description = "TC 3.2 Verify the demo account is not registered successfully with invalid data")
    @Parameters({"countryCode"})
    public void nonValidDataTest(String countryCode) {
        ScreenshotUtil.setCustomName("Non valid data - FortradeR");
        Allure.step("Redirected to https://dlp.fortrader.com/lps/pro-dark/en");
        fortradeRPage.registerDemoAccount("123", "574", "abcd134324", countryCode, "fsfsdfd");
        fortradeRPage.assertErrorMessages();
    }

    @Test(description = "TC 3.3. Verify that the account cannot be registered with already registered phone number")
    @Parameters({"countryCode"})
    public void alreadyRegisteredPhoneNumberTest(String countryCode) {
        ScreenshotUtil.setCustomName("Already registered phone number - FortradeR");
        Allure.step("Redirected to https://dlp.fortrader.com/lps/pro-dark/en");
        String phoneNumber = TestData.generatePhoneNumber();
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, TestData.generateEmail(), countryCode, phoneNumber);
        readyFortrade.assertURL(TestData.appUrl);
        openUrl(ConfigReader.getBaseUrl());
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, TestData.generateEmail(), countryCode, phoneNumber);
        fortradeRPage.assertErrMsgForAlreadyRegisteredAccount();
    }

    @Test(description = "TC 3.4. Verify that the account cannot be registered with already registered email address and phone number")
    @Parameters({"countryCode"})
    public void alreadyRegisteredEmailAndPhoneTest(String countryCode) {
        ScreenshotUtil.setCustomName("Already registered email address and phone number - FortradeR");
        Allure.step("Redirected to https://dlp.fortrader.com/lps/pro-dark/en");
        String email = TestData.generateEmail();
        String phone = TestData.generatePhoneNumber();
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, phone);
        readyFortrade.assertURL(TestData.appUrl);
        openUrl(ConfigReader.getBaseUrl());
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, phone);
        fortradeRPage.assertAlrRegEmailErrorMsg();
    }

    @Test(description = "TC 3.5. Verify the demo account is not registered successfully with empty fields")
    public void emptyDataRegistrationTest() {
        ScreenshotUtil.setCustomName("Unsuccessfully account registration with empty data - FortradeR");
        Allure.step("Redirected to https://dlp.fortrader.com/lps/pro-dark/en");
        fortradeRPage.clickGetStartedBtn();
        //missing assertation
    }

    @Test(description = "TC 4.1 - Verify that the invalid data for the First Name field will show valid error message with red border")
    public void verifyErrorMessageForFirstNameTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for First Name - FortradeR");
        Allure.step("Verified error message and border color for First Name field");
        ElementActions.type(fortradeRPage.firstName, "123", "first name");
        fortradeRPage.assertBorderColor(fortradeRPage.borderColorForFirstName, "border-color", TestData.redBorderColor);
        fortradeRPage.assertFirstStepErrorMessage(TestData.firstNameErrorMessage);
    }

    @Test(description = "TC 4.2 - Verify that the invalid data for the Last Name field will show valid error message with red border")
    public void verifyErrorMessageForLastNameTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Last Name - FortradeR");
        Allure.step("Verified error message and border color for Last Name field");
        ElementActions.type(fortradeRPage.lastName, "456", "last name");
        fortradeRPage.assertBorderColor(fortradeRPage.borderColorForLastName, "border-color", TestData.redBorderColor);
        fortradeRPage.assertFirstStepErrorMessage(TestData.lastNameErrorMessage);
    }

    @Test(description = "TC 4.3 - Verify that the invalid data for the Email field will show valid error message with red border")
    public void verifyErrorMessageForEmailTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Email - FortradeR");
        Allure.step("Verified error message and border color for Email field");
        ElementActions.type(fortradeRPage.email, "dsv124234/=", "email");
        fortradeRPage.assertBorderColor(fortradeRPage.borderColorForEmail, "border-color", TestData.redBorderColor);
        fortradeRPage.assertFirstStepErrorMessage(TestData.emailErrorMessage);
    }

    @Test(description = "TC 4.5 - Verify that the invalid data for Phone field will show valid error message with red border")
    public void verifyErrorMessageForPhoneTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Phone - FortadeR");
        Allure.step("Verified error message and border color for Phone field");
        ElementActions.type(fortradeRPage.phoneNumber, "0034334424558200", "phone");
        fortradeRPage.assertBorderColor(fortradeRPage.borderColorForPhone, "border-color", TestData.redBorderColor);
        fortradeRPage.assertFirstStepErrorMessage(TestData.wrongPhoneErrorMsgOther);
    }

    @Test(description = "TC 4.6 - Verify that the Last Name cannot be the same as First name.")
    public void sameFNameAndLNameTest() {
        ScreenshotUtil.setCustomName("Your first name must be different from your last name - FortradeR");
        Allure.step("Check for error message for the same first and last name.");
        fortradeRPage.insertFirstName("Test");
        fortradeRPage.insertLastName("Test");
        ElementActions.click(fortradeRPage.email, "email address");
        fortradeRPage.assertFirstStepErrorMessage(TestData.sameFirstNameErrorMessage);
        fortradeRPage.assertFirstStepErrorMessage(TestData.sameLastNameErrorMessage);
    }

    @Test(description = "TC 4.7 - Verify that the First Name cannot be the same as Last name.")
    public void sameLNameAndFNameTest() {
        ScreenshotUtil.setCustomName("Your last name must be different from your first name - FortradeR");
        Allure.step("Check for error message for the same last and first name.");
        fortradeRPage.insertLastName("Test");
        fortradeRPage.insertFirstName("Test");
        ElementActions.click(fortradeRPage.email, "email address");
        fortradeRPage.assertFirstStepErrorMessage(TestData.sameLastNameErrorMessage);
        fortradeRPage.assertFirstStepErrorMessage(TestData.sameFirstNameErrorMessage);
    }

    @Test(description = "TC 7.1 - Verify the Privacy Policy link works with left click")
    public void checkHeaderPrivacyPolicyLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Header privacy policy link - FortradeR");
        Allure.step("Left click on the header privacy policy link.");
        fortradeRPage.checkLinksWithLeftClick(fortradeRPage.headerPrivacyPolicyLink, "header privacy policy link", TestData.fortraderPrivacyPolicyUrl);
    }

    @Test(description = "TC 7.1.1 - Verify the Privacy Policy link works with right click")
    public void checkHeaderPrivacyPolicyLinkWithRightClickTest() {
        Allure.step("Right click on the header privacy policy link.");
        fortradeRPage.checkLinksWithRightClick(fortradeRPage.headerPrivacyPolicyLink, "header privacy policy link", TestData.fortraderPrivacyPolicyUrl);
    }

    @Test(description = "TC 7.2 - Verify the Terms and Conditions link works with left click")
    public void checkHeaderTermsAndConditionsLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Header terms and conditions link - FortradeR");
        Allure.step("Left click on the terms and conditions link.");
        fortradeRPage.checkLinksWithLeftClick(fortradeRPage.headerTermsAndConditionsLink, "header terms and conditions link", TestData.fortraderTermsAndCondUrl);
    }

    @Test(description = "TC 7.2.1 - Verify the Terms and Conditions link works with right click")
    public void checkHeaderTermsAndConditionsLinkWithRightClickTest() {
        Allure.step("Right click on the terms and conditions link.");
        fortradeRPage.checkLinksWithRightClick(fortradeRPage.headerTermsAndConditionsLink, "header terms and conditions link", TestData.fortraderTermsAndCondUrl);
    }

    @Test(description = "TC 7.3. Verify the click here link works with left click")
    public void checkClickHereTest() {
        ScreenshotUtil.setCustomName("CLick here link - FortradeR");
        Allure.step("Left mouse click on click here link");
        fortradeRPage.checkLinksWithLeftClick(fortradeRPage.clickHere, "click here", TestData.fortraderClickHereURL);
    }

    @Test(description = "TC 7.3.1 Verify the click here link works with right click")
    public void checkClickHereWithRightClick() {
        Allure.step("Right mouse click on the click here link");
        fortradeRPage.checkLinksWithRightClick(fortradeRPage.clickHere, "click here", TestData.fortraderClickHereURL);
    }

    @Test(description = "TC 7.4 - Verify the Already have an account? link works with left click")
    public void checkAlreadyHaveAnAccountLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Already have an account link - FortradeR");
        Allure.step("Left click on the already have an account link.");
        fortradeRPage.checkLinksWithLeftClick(fortradeRPage.alreadyHaveAnAccountLink, "already have an account link", TestData.alreadyHaveAnAccountUrl);
    }

    @Test(description = "TC 7.4.1 - Verify the Already have an account? link works with right click")
    public void checkAlreadyHaveAnAccountLinkWithRightClickTest() {
        Allure.step("Right click on the already have an account link.");
        fortradeRPage.checkLinksWithRightClick(fortradeRPage.alreadyHaveAnAccountLink, "already have an account link", TestData.alreadyHaveAnAccountUrl);
    }

    @Test(description = "TC 7.5 - Verify the click on Contact us link opens new mail window")
    public void checkContactUsLinkTest() {
        ScreenshotUtil.setCustomName("Contact Us link - FortradeR");
        Allure.step("Left click on the contact us link.");
        fortradeRPage.checkMailLinks(fortradeRPage.contactUsLink, "href", TestData.contactUsUrl);
    }

    @Test(description = "TC 7.6 - Verify the click on support@fortrade.com link opens email window")
    public void checkSupportLinkTest() {
        ScreenshotUtil.setCustomName("Support link - FortradeR");
        Allure.step("Right click on the contact us link.");
        fortradeRPage.checkMailLinks(fortradeRPage.supportLink, "href", TestData.supportUrl);
    }

    @Test(description = "TC 8.5 - Verify the GB21026472 (FSC) link works with left click")
    public void checkFscLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Fsc link - FortradeR");
        Allure.step("Left click on the FSC link.");
        fortradeRPage.checkLinksWithLeftClick(fortradeRPage.footerFSCLink, "fsc link", TestData.fscUrl);
    }

    @Test(description = "TC 8.5.1 - Verify the GB21026472 (FSC) link works with right click")
    public void checkFscLinkWithRightClickTest() {
        Allure.step("Right click on the FSC link.");
        fortradeRPage.checkLinksWithRightClick(fortradeRPage.footerFSCLink, "fsc link", TestData.fscUrl);
    }

    @Test(description = "9.1. Verify the border color in the CRM for regulation")
    @Parameters({"countryCode"})
    public void checkAccountRegulationTest(String countryCode) {
        ScreenshotUtil.setCustomName("Account regulation in CRM - FortradeR");
        String email = TestData.generateEmail();
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, "Testq Testa", "FSC");
    }

    @Test(description = "9.2. Verify the account details is displayed correctly in the CRM")
    @Parameters({"countryCode"})
    public void checkAccountDetailsInCrmTest(String countryCode) {
        ScreenshotUtil.setCustomName("Account details in CRM - FortradeR");
        String email = TestData.generateEmail();
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, "Testq Testa", "FSC");
    }

    @Test(description = "9.3. Verify the tags are displayed correctly in CRM")
    @Parameters({"countryCode"})
    public void checkTagsInCrmTest(String countryCode) {
        ScreenshotUtil.setCustomName("Marketing tags FortradeR page ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=age-annual-saving-knowledge-plang:all&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradeRPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(),
                "25-34", "$15,000-$50,000", "$50,000-$100,000", "All the above", "English");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, "Testq Testa", "FSC");
        crmPage.checkCrmTags();
    }

    @Test(description = "9.4. Verify that the Link ID field contains 'PC_windows' value in the CRM")
    @Parameters({"countryCode"})
    public void checkLinkIDPCWindows(String countryCode){
        ScreenshotUtil.setCustomName("Link ID tag contains the 'PC_windows' value - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=age-annual-saving-knowledge-plang:all&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        fortradeRPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(),
                "25-34", "$15,000-$50,000", "$50,000-$100,000", "All the above", "English");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName, "FSC");
        crmPage.checkLinkIdValue("PC_windows");
    }

    @Test(description = "TC 10.1. Verify the email is sent on the new account email")
    @Parameters({"countryCode"})
    public void emailIsReceived(String countryCode) {
        ScreenshotUtil.setCustomName("Email is received successfully - FortradeR page ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        readyFortrade.clickUsePassBtn();
        openUrl(TestData.yopmailUrl);
        yopmailPage.findEmail(email);
    }

    @Test(description = "TC 12.5. Verify that message 'We sent you the code again' is received")
    @Parameters({"countryCode"})
    public void iDidntReceiveTheCodeTest(String countryCode) {
        ScreenshotUtil.setCustomName("I didn't received the code link - Fortrader");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=sms");
        fortradeRPage.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        fortradeRPage.checkIDidntReceiveTheCodeLink();
    }

    @Test(description = "TC 12.6. Verify that wrong code cannot be submitted (negative test case)")
    @Parameters({"countryCode"})
    public void wrongCodeCannotBeSubmittedTest(String countryCode) {
        ScreenshotUtil.setCustomName("Wrong code cannot be submitted - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=sms-age-annual-saving-knowledge-plang:all");
        fortradeRPage.fillTheFormOnTheSecondStepWithWrongSmsCode(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(),
                "25-34", "$15,000-$50,000", "$50,000-$100,000", "All the above", "English",
                "1", "1", "1", "1");
        fortradeRPage.assertErrorMessageForWrongSmsCode();
    }

    @Test(description = "TC 12.9. Verify if user clicks pencil icon the same is returned to the 1st widget")
    @Parameters({"countryCode"})
    public void editPencilButtonTest(String countryCode) {
        ScreenshotUtil.setCustomName("Edit pencil button redirects to the first step - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=sms");
        fortradeRPage.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        fortradeRPage.checkEditPencilButton();
    }

    @Test(description = "TC 13.1. Verify the user is redirected to the 2nd step - age verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepAgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (age) - FortradeR ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=age");
        fortradeRPage.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(fortradeRPage.age);
        Assert.assertTrue(fortradeRPage.age.isDisplayed());
    }

    @Test(description = "TC 13.2. Verify the 2nd step - age verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredAgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (age) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=age");
        fortradeRPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "age");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 13.3. Verify the 2nd step - age verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForAgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (age) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=age");
        fortradeRPage.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "-- Select --", "age");
        fortradeRPage.assertSecondStepErrorMessage("age");
    }

    @Test(description = "TC 13.4. Verify the age value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void ageParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (age) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=age");
        fortradeRPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "age");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "25_34_age");
    }

    @Test(description = "TC 13.5. Vefiry that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationAgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (age) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=age");
        fortradeRPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "age");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 14.1. Verify the user is redirected to the 2nd step - annual verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepAnnualTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (annual) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=annual");
        fortradeRPage.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(fortradeRPage.annual);
        Assert.assertTrue(fortradeRPage.annual.isDisplayed());
    }

    @Test(description = "TC 14.2. Verify the 2nd step - annual income verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredAnnualParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (annual) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=annual");
        fortradeRPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "annual");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 14.3. Verify the 2nd step - annual income verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForAnnualParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (annual) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=annual");
        fortradeRPage.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "-- Select --", "annual");
        fortradeRPage.assertSecondStepErrorMessage("annual");
    }

    @Test(description = "TC 14.4. Verify the annual value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void annualParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (annual) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=annual");
        fortradeRPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "annual");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "15000_50000_annual");
    }

    @Test(description = "TC 14.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationAnnualTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (annual) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=annual");
        fortradeRPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "annual");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 15.1. Verify the user is redirected to the 2nd step - saving and investments verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepSavingTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (saving) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=saving");
        fortradeRPage.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(fortradeRPage.saving);
        Assert.assertTrue(fortradeRPage.saving.isDisplayed());
    }

    @Test(description = "TC 15.2. Verify the 2nd step -  value of saving and investments verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredSavingParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (saving) -FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=saving");
        fortradeRPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "saving");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 15.3. Verify the 2nd step - value of saving and investments verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForSavingParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (saving) -FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=saving");
        fortradeRPage.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "-- Select --", "saving");
        fortradeRPage.assertSecondStepErrorMessage("saving");
    }

    @Test(description = "TC 15.4. Verify the saving value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void savingParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (saving) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=saving");
        fortradeRPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "saving");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "50000_100000_savings");
    }

    @Test(description = "TC 15.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationSavingTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (saving) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=saving");
        fortradeRPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "saving");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 16.1. Verify the user is redirected to the 2nd step - knowledge of trading verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepKnowledgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (knowledge) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=knowledge");
        fortradeRPage.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(fortradeRPage.knowledge);
        Assert.assertTrue(fortradeRPage.knowledge.isDisplayed());
    }

    @Test(description = "TC 16.2. Verify the 2nd step -  knowledge of trading verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredKnowledgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (knowledge) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=knowledge&");
        fortradeRPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "All the above", "knowledge");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 16.3. Verify the 2nd step - knowledge of trading verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForKnowledgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (knowledge) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=knowledge");
        fortradeRPage.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "All the above", "-- Select --", "knowledge");
        fortradeRPage.assertSecondStepErrorMessage("knowledge");
    }

    @Test(description = "TC 16.4. Verify the knowledge of trading value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void knowledgeParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (knowledge) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=knowledge");
        fortradeRPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "All the above", "knowledge");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "knowledge_of_trading_all_the_above");
    }

    @Test(description = "TC 16.5. Vefiry that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationKnowledgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (knowledge) -FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=knowledge");
        fortradeRPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "All the above", "knowledge");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 17.1. Verify the user is redirected to the 2nd step - language verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepLanguageTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (language) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=plang:all");
        fortradeRPage.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(fortradeRPage.language);
        Assert.assertTrue(fortradeRPage.language.isDisplayed());
    }

    @Test(description = "TC 17.2. Verify the 2nd step -  desired communication language verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredLanguageParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (language) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=plang:all");
        fortradeRPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "English", "language");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 17.3. Verify the 2nd step - desired communication language verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForLanguageParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (language) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=plang:all");
        fortradeRPage.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "English", "-- Select --", "language");
        fortradeRPage.assertSecondStepErrorMessage("language");
    }

    @Test(description = "TC 17.4. Verify the desired communication language value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void languageParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (language) -FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=plang:all");
        fortradeRPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "English", "language");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "lang_EN");
    }

    @Test(description = "TC 17.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationLanguageTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (language) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=plang:all");
        fortradeRPage.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "English", "language");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 18.1. Verify the account is registered successfully with NON-valid tag in the URL")
    @Parameters({"countryCode"})
    public void nonValidParameterInTheUrlTest(String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Demo account is successfully registered with wrong parameters in the URL - FortradeR");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=testq-testa");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, TestData.generateEmail(), countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL("https://ready.fortrade.com/");
        readyFortrade.clickUsePassBtn();
        readyFortrade.clickMenuBtn();
        readyFortrade.checkRegulation("FSC");
    }

    @Test(description = "TC 18.2. Verify the 2nd step cannot be submitted if all parameter values are not completed")
    @Parameters({"countryCode"})
    public void noDataOnTheSecondStepTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error messages (all parameters) - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=age-annual-saving-knowledge-plang:all");
        fortradeRPage.fillTheFormOnTheSecondStepWithWrongData(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "$15,000-$50,000", "$50,000-$100,000",
                "All the above", "English");
        fortradeRPage.assertSecondStepErrorMessageAllParameters();
    }

    @Test(description = "TC 19.1. Verify the cutom tag field in the CRM contains Dummy value ")
    @Parameters({"countryCode"})
    public void dummyLeadRegistration(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age-equals(1,3)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        fortradeRPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode,
                TestData.generatePhoneNumber(), "25-34", "$50,000-$100,000", "$50,000-$100,000",
                "All the above", "English");
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 19.2. Verify the cutom tag field in the CRM contains Dummy value ")
    @Parameters({"countryCode"})
    public void dummy_Lead_Registration(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag with mark - Dummy - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age-equals(1_3)-or-[saving-equals(1_2_3)-and-knowledge-notequals(5)]");
        fortradeRPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode,
                TestData.generatePhoneNumber(), "25-34", "$15,000-$50,000", "$50,000-$100,000",
                "All the above", "English");
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 19.3. Verify the custom tag field in the CRM contains Invalid value")
    @Parameters({"countryCode"})
    public void invalidLeadRegistration(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom tag - Invalid - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age(1)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        fortradeRPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "None", "English");
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 19.4. Verify the custom tag field in the CRM contains Invalid value")
    @Parameters({"countryCode"})
    public void invalid_Lead_Registration(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom tag with mark - Invalid - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age(1)-or-[saving-equals(1_2_3)-and-knowledge-notequals(5)]");
        fortradeRPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "None", "English");
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 19.5. Verify the custom tag field in the CRM is empty")
    @Parameters({"countryCode"})
    public void emptyLeadRegistration(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty  - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age-equals(1,3)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        fortradeRPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "All the above", "English");
        fortradeRPage.assertURL("https://ready.fortrade.com/");
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.6. Verify the custom tag field in the CRM is empty")
    @Parameters({"countryCode"})
    public void empty_Lead_Registration(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag with mark - Empty - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=annual-saving-knowledge-age-plang:all&" +
                "ftsquery=age-equals(1_3)-or-[saving-equals(1_2_3)-and-knowledge-notequals(5)]");
        fortradeRPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "All the above", "English");
        fortradeRPage.assertURL("https://ready.fortrade.com/");
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.7. Verify the custom tag field in the CRM is Dummy if ftsquery device parameter is same as device " +
            "and OS used for demo account registration")
    @Parameters({"countryCode"})
    public void deviceIsSameAsParameter(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy - device FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en?ftsquery=device-equals(1)");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?ftsquery=device-equals(1)");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 19.8. Verify the custom tag field in the CRM is invalid if syntax is not valid")
    @Parameters({"countryCode"})
    public void nonValidParameterSyntax(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Invalid - device - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en?ftsquery=device(1)");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?ftsquery=device(1)");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 19.9. Verify the custom tag field in the CRM is empty if ftsquery device parameter is not " +
            "same as device and OS used for demo account registration")
    @Parameters({"countryCode"})
    public void deviceIsNotSameAsParameter(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty - device FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en?ftsquery=device-equals(4)");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?ftsquery=device-equals(4)");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.10. Verify the custom tag field in the CRM is invalid if ftsquery device parameter " +
            "contains device and OS non valid index value")
    @Parameters({"countryCode"})
    public void deviceParameterContainsNonValidValue(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - non valid device value - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en?ftsquery=device-equals(0)");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?ftsquery=device-equals(0)");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 20.3. Verify that the Custom Tag in the CRM is empty")
    @Parameters({"countryCode"})
    public void checkingTheCustomTag(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 20-3-Custom Tag is empty FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en?fts=age-annual-saving-knowledge-plang:all");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?fts=age-annual-saving-knowledge-plang:all");
        fortradeRPage.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "All the above", "English");
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 22.1. Verify that the Language field in the CRM contains expected value (in this case FR)")
    @Parameters({"countryCode"})
    public void checkLanguageFieldContainsExpectedValue(String countryCode) {
        ScreenshotUtil.setCustomName("Language field in the CRM contains expected (FR) value - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?userLang=FR");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        readyFortrade.clickNotSerbResBtn();
        readyFortrade.clickUsePassBtn();
        readyFortrade.assertDisplayedLanguage("FR");
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkLanguageField(email, "fr");
    }

    @Test(description = "TC 22.2. Verify that the Language field in the CRM contains the default value (the language of the base page URL) when we enter the wrong language in the userLang parameter")
    @Parameters({"countryCode"})
    public void checkLanguageFieldContainsDefaultValue(String countryCode) {
        ScreenshotUtil.setCustomName("Language field in the CRM contains default (EN) value - FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?userLang=FRA");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        readyFortrade.assertDisplayedLanguage("EN");
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkLanguageField(email, "en");
    }

    @Test(description = "TC 23.1. Verify that the Custom Tag field in the CRM contains the DummyP value")
    @Parameters({"countryCode"})
    public void dummypParameter(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-1-Custom tag-DummyP value-FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en" +
                "?ftsquery=device-equals(1)&dummyP=1");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?ftsquery=device-equals(1)&dummyP=1");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("DummyP");
    }

    @Test(description = "TC 23.2. Verify that the Custom Tag field in the CRM contains the Dummy value")
    @Parameters({"countryCode"})
    public void dummyParameter(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-2-Custom tag-Dummy value-FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en" +
                "?ftsquery=device-equals(1)&dummyP=0");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?ftsquery=device-equals(1)&dummyP=0");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 23.3. Verify that the dummyP parameter is ignored when it's not correctly typed in the URL")
    @Parameters({"countryCode"})
    public void parameterDummy(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-3- Custom tag-Dummy value-FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en" +
                "?ftsquery=device-equals(1)&dummyp=1");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?ftsquery=device-equals(1)&dummyp=1");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.1. Verify that the custom tag field in the CRM contains Dummy parameter ")
    @Parameters({"countryCode"})
    public void customTagContainsDummy(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-1-Custom tag field-dummy-FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en?Dummy=true");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?Dummy=true");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.2. Verify that the custom tag field in the CRM contains Dummy parameter ")
    @Parameters({"countryCode"})
    public void customTagContainsDummyValue(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-2-Custom tag field-dummy-FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en?Dummy=1");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?Dummy=1");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.3. Verify that the custom tag field in the CRM contains '' (empty) value")
    @Parameters({"countryCode"})
    public void customTagContainsEmpty(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-3-Custom tag field-empty-FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en?Dummy=false");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?Dummy=false");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 24.4. Verify that the custom tag field in the CRM contains '' (empty) value")
    @Parameters({"countryCode"})
    public void customTagContainsEmptyValue(String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-4-Custom tag field-empty-FortradeR");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en?Dummy=0");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?Dummy=0");
        fortradeRPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 25.1. Verify that the Link Id field in the CRM contains '{number}' value")
    @Parameters({"countryCode"})
    public void checkNumberIplValue(String countryCode){
        ScreenshotUtil.setCustomName("TC 25-1-Link Id tag field - {number}");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en?tag1=1452789330");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?tag1=1452789330");
        fortradeRPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkLinkIdValue("0.0953@1500");
    }

    @Test(description = "TC 25.2. Verify that the Link Id field in the CRM contains 'missingTag1' value")
    @Parameters({"countryCode"})
    public void checkMissingTag1IplValue(String countryCode){
        ScreenshotUtil.setCustomName("TC 25-2-Link Id tag field - missingTag1");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en?tag1=123abc");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?tag1=123abc");
        fortradeRPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkLinkIdValue("missingTag1");
    }

    @Test(description = "TC 25.3. Verify that the Link Id field in the CRM contains 'missingCID' value")
    @Parameters({"countryCode"})
    public void checkMissingCidIplValue(String countryCode) {
        ScreenshotUtil.setCustomName("TC 25-3-Link Id tag field - missingCID");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en?tag1=123456789");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?tag1=123456789");
        fortradeRPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkLinkIdValue("missingCID");
    }

    @Test(description = "TC 25.4. Verify that the Link Id field in the CRM contains 'divByZero' value")
    @Parameters({"countryCode"})
    public void checkDivByZeroIplValue(String countryCode) {
        ScreenshotUtil.setCustomName("TC 25-4-Link Id tag field - div by zero");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.fortrader.com/lps/pro-dark/en?tag1=930863512");
        openUrl("https://dlp.fortrader.com/lps/pro-dark/en?tag1=930863512");
        fortradeRPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        fortradeRPage.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, "FSC");
        crmPage.checkLinkIdValue("divByZero");
    }
}
