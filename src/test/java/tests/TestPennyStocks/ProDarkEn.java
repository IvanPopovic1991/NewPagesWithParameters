package tests.TestPennyStocks;

import core.actions.ElementActions;
import core.base.BaseTest;
import core.config.ConfigReader;
import core.pages.CrmPage;
import core.pages.PennyStocks;
import core.pages.ReadyFortrade;
import core.pages.YopmailPage;
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

    private PennyStocks pennyStocks;
    private ReadyFortrade readyFortrade;
    private CrmPage crmPage;
    private YopmailPage yopmailPage;
    private String baseUrl;

    @BeforeMethod
    public void initPages() {
        pennyStocks = new PennyStocks();
        readyFortrade = new ReadyFortrade();
        crmPage = new CrmPage();
        yopmailPage = new YopmailPage();
        baseUrl = ConfigReader.getBaseUrl();
        openUrl(baseUrl);
    }


    @Test(description = "TC 1.2.1 - Verify the logo is not clickable with left click")
    public void logoClickabilityTest() {
        ScreenshotUtil.setCustomName("Logo is not clickable - Penny Stocks");
        Allure.step("Tried to click on Penny Stocks logo");
        pennyStocks.checkLogoClickability();
        pennyStocks.assertURL("https://dlp.pennystocks-uk.com/lps/pro-dark/en");
    }

    @Test(description = "TC 2.1. Verify the demo account is registered successfully with valid data")
    @Parameters({"countryCode","regulation"})
    public void demoAccountRegistrationTest(String countryCode, String regulation) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Demo account is successfully registered - PennyStocks");
        pennyStocks.registerDemoAccount(TestData.firstName, TestData.lastName, TestData.generateEmail(), countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        readyFortrade.clickNotSerbResBtn();
        readyFortrade.clickUsePassBtn();
        readyFortrade.clickMenuBtn();
        readyFortrade.checkRegulation(regulation);
    }

    @Test(description = "TC 3.1. Verify that the account cannot be registered with already registered email address")
    @Parameters({"countryCode"})
    public void alreadyRegisteredEmailAddressTest(String countryCode) {
        ScreenshotUtil.setCustomName("Already registered email address -PennyStocks");
        Allure.step("Redirected to https://dlp.pennystocks-uk.com/lps/pro-dark/en");
        String email = TestData.generateEmail();
        pennyStocks.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        openUrl(ConfigReader.getBaseUrl());
        pennyStocks.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        pennyStocks.assertAlrRegEmailErrorMsg();
    }

    @Test(description = "TC 3.2 Verify the demo account is not registered successfully with invalid data")
    @Parameters({"countryCode"})
    public void nonValidDataTest(String countryCode) {
        ScreenshotUtil.setCustomName("Non valid data - PennyStocks");
        Allure.step("Redirected to https://dlp.pennystocks-uk.com/lps/pro-dark/en");
        pennyStocks.registerDemoAccount("123", "574", "abcd134324", countryCode, "fsfsdfd");
        pennyStocks.assertErrorMessages();
    }

    @Test(description = "TC 3.3. Verify that the account cannot be registered with already registered phone number")
    @Parameters({"countryCode"})
    public void alreadyRegisteredPhoneNumberTest(String countryCode) {
        ScreenshotUtil.setCustomName("Already registered phone number - PennyStocks");
        Allure.step("Redirected to https://dlp.pennystocks-uk.com/lps/pro-dark/en");
        String phoneNumber = TestData.generatePhoneNumber();
        pennyStocks.registerDemoAccount(TestData.firstName, TestData.lastName, TestData.generateEmail(), countryCode, phoneNumber);
        readyFortrade.assertURL(TestData.appUrl);
        openUrl(ConfigReader.getBaseUrl());
        pennyStocks.registerDemoAccount(TestData.firstName, TestData.lastName, TestData.generateEmail(), countryCode, phoneNumber);
        pennyStocks.assertErrMsgForAlreadyRegisteredAccount();
    }

    @Test(description = "TC 3.4. Verify that the account cannot be registered with already registered email address and phone number")
    @Parameters({"countryCode"})
    public void alreadyRegisteredEmailAndPhoneTest(String countryCode) {
        ScreenshotUtil.setCustomName("Already registered email address and phone number - PennyStocks");
        Allure.step("Redirected to https://dlp.pennystocks-uk.com/lps/pro-dark/en");
        String email = TestData.generateEmail();
        String phone = TestData.generatePhoneNumber();
        pennyStocks.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, phone);
        readyFortrade.assertURL(TestData.appUrl);
        openUrl(ConfigReader.getBaseUrl());
        pennyStocks.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, phone);
        pennyStocks.assertAlrRegEmailErrorMsg();
    }

    @Test(description = "TC 3.5. Verify the demo account is not registered successfully with empty fields")
    public void emptyDataRegistrationTest() {
        ScreenshotUtil.setCustomName("Unsuccessfully account registration with empty data - PennyStocks");
        Allure.step("Redirected to https://dlp.pennystocks-uk.com/lps/pro-dark/en");
        pennyStocks.clickGetStartedBtn();
        //missing assertation
    }

    @Test(description = "TC 4.1 - Verify that the invalid data for the First Name field will show valid error message with red border")
    public void verifyErrorMessageForFirstNameTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for First Name - PennyStocks");
        Allure.step("Verified error message and border color for First Name field");
        ElementActions.type(pennyStocks.firstName, "123", "first name");
        pennyStocks.assertBorderColor(pennyStocks.borderColorForFirstName, "border-color", TestData.redBorderColor);
        pennyStocks.assertFirstStepErrorMessage(TestData.firstNameErrorMessage);
    }

    @Test(description = "TC 4.2 - Verify that the invalid data for the Last Name field will show valid error message with red border")
    public void verifyErrorMessageForLastNameTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Last Name - PennyStocks");
        Allure.step("Verified error message and border color for Last Name field");
        ElementActions.type(pennyStocks.lastName, "456", "last name");
        pennyStocks.assertBorderColor(pennyStocks.borderColorForLastName, "border-color", TestData.redBorderColor);
        pennyStocks.assertFirstStepErrorMessage(TestData.lastNameErrorMessage);
    }

    @Test(description = "TC 4.3 - Verify that the invalid data for the Email field will show valid error message with red border")
    public void verifyErrorMessageForEmailTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Email - PennyStocks");
        Allure.step("Verified error message and border color for Email field");
        ElementActions.type(pennyStocks.email, "dsv124234/=", "email");
        pennyStocks.assertBorderColor(pennyStocks.borderColorForEmail, "border-color", TestData.redBorderColor);
        pennyStocks.assertFirstStepErrorMessage(TestData.emailErrorMessage);
    }

    @Test(description = "TC 4.5 - Verify that the invalid data for Phone field will show valid error message with red border")
    public void verifyErrorMessageForPhoneTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Phone - FortadeR");
        Allure.step("Verified error message and border color for Phone field");
        ElementActions.type(pennyStocks.phoneNumber, "0034334424558200", "phone");
        pennyStocks.assertBorderColor(pennyStocks.borderColorForPhone, "border-color", TestData.redBorderColor);
        pennyStocks.assertFirstStepErrorMessage(TestData.wrongPhoneErrorMsgOther);
    }

    @Test(description = "TC 4.6 - Verify that the Last Name cannot be the same as First name.")
    public void sameFNameAndLNameTest() {
        ScreenshotUtil.setCustomName("Your first name must be different from your last name - PennyStocks");
        Allure.step("Check for error message for the same first and last name.");
        pennyStocks.insertFirstName("Test");
        pennyStocks.insertLastName("Test");
        ElementActions.click(pennyStocks.email, "email address");
        pennyStocks.assertFirstStepErrorMessage(TestData.sameFirstNameErrorMessage);
        pennyStocks.assertFirstStepErrorMessage(TestData.sameLastNameErrorMessage);
    }

    @Test(description = "TC 4.7 - Verify that the First Name cannot be the same as Last name.")
    public void sameLNameAndFNameTest() {
        ScreenshotUtil.setCustomName("Your last name must be different from your first name - PennyStocks");
        Allure.step("Check for error message for the same last and first name.");
        pennyStocks.insertLastName("Test");
        pennyStocks.insertFirstName("Test");
        ElementActions.click(pennyStocks.email, "email address");
        pennyStocks.assertFirstStepErrorMessage(TestData.sameLastNameErrorMessage);
        pennyStocks.assertFirstStepErrorMessage(TestData.sameFirstNameErrorMessage);
    }

    @Test(description = "TC 7.1 - Verify the Privacy Policy link works with left click")
    public void checkHeaderPrivacyPolicyLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Header privacy policy link - PennyStocks");
        Allure.step("Left click on the header privacy policy link.");
        pennyStocks.checkLinksWithLeftClick(pennyStocks.headerPrivacyPolicyLink, "header privacy policy link", TestData.privacyPolicyUrl);
    }

    @Test(description = "TC 7.1.1 - Verify the Privacy Policy link works with right click")
    public void checkHeaderPrivacyPolicyLinkWithRightClickTest() {
        Allure.step("Right click on the header privacy policy link.");
        pennyStocks.checkLinksWithRightClick(pennyStocks.headerPrivacyPolicyLink, "header privacy policy link", TestData.privacyPolicyUrl);
    }

    @Test(description = "TC 7.2 - Verify the Terms and Conditions link works with left click")
    public void checkHeaderTermsAndConditionsLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Header terms and conditions link - PennyStocks");
        Allure.step("Left click on the terms and conditions link.");
        pennyStocks.checkLinksWithLeftClick(pennyStocks.headerTermsAndConditionsLink, "header terms and conditions link", TestData.termsAndConditionsUrl);
    }

    @Test(description = "TC 7.2.1 - Verify the Terms and Conditions link works with right click")
    public void checkHeaderTermsAndConditionsLinkWithRightClickTest() {
        Allure.step("Right click on the terms and conditions link.");
        pennyStocks.checkLinksWithRightClick(pennyStocks.headerTermsAndConditionsLink, "header terms and conditions link", TestData.termsAndConditionsUrl);
    }

    @Test(description = "TC 7.3. Verify the click here link works with left click")
    public void checkClickHereTest() {
        ScreenshotUtil.setCustomName("CLick here link - PennyStocks");
        Allure.step("Left mouse click on click here link");
        pennyStocks.checkLinksWithLeftClick(pennyStocks.clickHere, "click here", TestData.clickHereUrl);
    }

    @Test(description = "TC 7.3.1 Verify the click here link works with right click")
    public void checkClickHereWithRightClick() {
        Allure.step("Right mouse click on the click here link");
        pennyStocks.checkLinksWithRightClick(pennyStocks.clickHere, "click here", TestData.clickHereUrl);
    }

    @Test(description = "TC 7.4 - Verify the Already have an account? link works with left click")
    public void checkAlreadyHaveAnAccountLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Already have an account link - PennyStocks");
        Allure.step("Left click on the already have an account link.");
        pennyStocks.checkLinksWithLeftClick(pennyStocks.alreadyHaveAnAccountLink, "already have an account link", TestData.alreadyHaveAnAccountUrl);
    }

    @Test(description = "TC 7.4.1 - Verify the Already have an account? link works with right click")
    public void checkAlreadyHaveAnAccountLinkWithRightClickTest() {
        Allure.step("Right click on the already have an account link.");
        pennyStocks.checkLinksWithRightClick(pennyStocks.alreadyHaveAnAccountLink, "already have an account link", TestData.alreadyHaveAnAccountUrl);
    }

    @Test(description = "TC 7.5 - Verify the click on Contact us link opens new mail window")
    public void checkContactUsLinkTest() {
        ScreenshotUtil.setCustomName("Contact Us link - PennyStocks");
        Allure.step("Left click on the contact us link.");
        pennyStocks.checkMailLinks(pennyStocks.contactUsLink, "href", TestData.contactUsUrl);
    }

    @Test(description = "TC 7.6 - Verify the click on support@fortrade.com link opens email window")
    public void checkSupportLinkTest() {
        ScreenshotUtil.setCustomName("Support link - PennyStocks");
        Allure.step("Right click on the contact us link.");
        pennyStocks.checkMailLinks(pennyStocks.supportLink, "href", TestData.contactUsUrl);
    }

    @Test(description = "TC 8.5 - Verify the GB21026472 (FSC) link works with left click")
    public void checkFscLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Fsc link - PennyStocks");
        Allure.step("Left click on the FSC link.");
        pennyStocks.checkLinksWithLeftClick(pennyStocks.footerFSCLink, "fsc link", TestData.fscUrl);
    }

    @Test(description = "TC 8.5.1 - Verify the GB21026472 (FSC) link works with right click")
    public void checkFscLinkWithRightClickTest() {
        Allure.step("Right click on the FSC link.");
        pennyStocks.checkLinksWithRightClick(pennyStocks.footerFSCLink, "fsc link", TestData.fscUrl);
    }

    @Test(description = "9.1. Verify the border color in the CRM for regulation")
    @Parameters({"countryCode","regulation"})
    public void checkAccountRegulationTest(String countryCode, String regulation) {
        ScreenshotUtil.setCustomName("Account regulation in CRM - PennyStocks");
        String email = TestData.generateEmail();
        pennyStocks.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, "Testq Testa", regulation);
    }

    @Test(description = "9.2. Verify the account details is displayed correctly in the CRM")
    @Parameters({"countryCode","regulation"})
    public void checkAccountDetailsInCrmTest(String countryCode, String regulation) {
        ScreenshotUtil.setCustomName("Account details in CRM - PennyStocks");
        String email = TestData.generateEmail();
        pennyStocks.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, "Testq Testa", regulation);
    }

    @Test(description = "9.3. Verify the tags are displayed correctly in CRM")
    @Parameters({"countryCode","regulation"})
    public void checkTagsInCrmTest(String countryCode,String regulation) {
        ScreenshotUtil.setCustomName("Marketing tags PennyStocks page ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=age-annual-saving-knowledge-plang:all&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(),
                "25-34", "$15,000-$50,000", "$50,000-$100,000", "All the above", "English");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, "Testq Testa", regulation);
        crmPage.checkCrmTags();
    }

    @Test(description = "TC 10.1. Verify the email is sent on the new account email")
    @Parameters({"countryCode"})
    public void emailIsReceived(String countryCode) {
        ScreenshotUtil.setCustomName("Email is received successfully - PennyStocks page ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.pennystocks-uk.com/lps/pro-dark/en");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en");
        pennyStocks.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        readyFortrade.clickNotSerbResBtn();
        readyFortrade.clickUsePassBtn();
        openUrl(TestData.yopmailUrl);
        yopmailPage.findEmail(email);
    }

    @Test(description = "TC 12.5. Verify that message 'We sent you the code again' is received")
    @Parameters({"countryCode"})
    public void iDidntReceiveTheCodeTest(String countryCode) {
        ScreenshotUtil.setCustomName("I didn't received the code link - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=sms");
        pennyStocks.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        pennyStocks.checkIDidntReceiveTheCodeLink();
    }

    @Test(description = "TC 12.6. Verify that wrong code cannot be submitted (negative test case)")
    @Parameters({"countryCode"})
    public void wrongCodeCannotBeSubmittedTest(String countryCode) {
        ScreenshotUtil.setCustomName("Wrong code cannot be submitted - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=sms-age-annual-saving-knowledge-plang:all");
        pennyStocks.fillTheFormOnTheSecondStepWithWrongSmsCode(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(),
                "25-34", "$15,000-$50,000", "$50,000-$100,000", "All the above", "English",
                "1", "1", "1", "1");
        pennyStocks.assertErrorMessageForWrongSmsCode();
    }

    @Test(description = "TC 12.9. Verify if user clicks pencil icon the same is returned to the 1st widget")
    @Parameters({"countryCode"})
    public void editPencilButtonTest(String countryCode) {
        ScreenshotUtil.setCustomName("Edit pencil button redirects to the first step - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=sms");
        pennyStocks.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        pennyStocks.checkEditPencilButton();
    }

    @Test(description = "TC 13.1. Verify the user is redirected to the 2nd step - age verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepAgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (age) - PennyStocks ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=age");
        pennyStocks.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(pennyStocks.age);
        Assert.assertTrue(pennyStocks.age.isDisplayed());
    }

    @Test(description = "TC 13.2. Verify the 2nd step - age verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredAgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (age) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=age");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "age");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 13.3. Verify the 2nd step - age verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForAgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (age) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=age");
        pennyStocks.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "-- Select --", "age");
        pennyStocks.assertSecondStepErrorMessage("age");
    }

    @Test(description = "TC 13.4. Verify the age value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void ageParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (age) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=age");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "age");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "PC_windows,25_34_age");
    }

    @Test(description = "TC 13.5. Vefiry that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationAgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (age) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=age");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "age");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 14.1. Verify the user is redirected to the 2nd step - annual verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepAnnualTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (annual) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=annual");
        pennyStocks.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(pennyStocks.annual);
        Assert.assertTrue(pennyStocks.annual.isDisplayed());
    }

    @Test(description = "TC 14.2. Verify the 2nd step - annual income verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredAnnualParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (annual) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=annual");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "annual");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 14.3. Verify the 2nd step - annual income verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForAnnualParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (annual) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=annual");
        pennyStocks.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "-- Select --", "annual");
        pennyStocks.assertSecondStepErrorMessage("annual");
    }

    @Test(description = "TC 14.4. Verify the annual value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void annualParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (annual) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=annual");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "annual");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "PC_windows,15000_50000_annual");
    }

    @Test(description = "TC 14.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationAnnualTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (annual) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=annual");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "annual");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 15.1. Verify the user is redirected to the 2nd step - saving and investments verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepSavingTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (saving) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=saving");
        pennyStocks.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(pennyStocks.saving);
        Assert.assertTrue(pennyStocks.saving.isDisplayed());
    }

    @Test(description = "TC 15.2. Verify the 2nd step -  value of saving and investments verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredSavingParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (saving) -PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=saving");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "saving");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 15.3. Verify the 2nd step - value of saving and investments verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForSavingParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (saving) -PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=saving");
        pennyStocks.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "-- Select --", "saving");
        pennyStocks.assertSecondStepErrorMessage("saving");
    }

    @Test(description = "TC 15.4. Verify the saving value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void savingParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (saving) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=saving");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "saving");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "PC_windows,50000_100000_savings");
    }

    @Test(description = "TC 15.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationSavingTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (saving) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=saving");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "saving");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 16.1. Verify the user is redirected to the 2nd step - knowledge of trading verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepKnowledgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (knowledge) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=knowledge");
        pennyStocks.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(pennyStocks.knowledge);
        Assert.assertTrue(pennyStocks.knowledge.isDisplayed());
    }

    @Test(description = "TC 16.2. Verify the 2nd step -  knowledge of trading verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredKnowledgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (knowledge) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=knowledge&");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "All the above", "knowledge");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 16.3. Verify the 2nd step - knowledge of trading verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForKnowledgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (knowledge) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=knowledge");
        pennyStocks.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "All the above", "-- Select --", "knowledge");
        pennyStocks.assertSecondStepErrorMessage("knowledge");
    }

    @Test(description = "TC 16.4. Verify the knowledge of trading value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void knowledgeParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (knowledge) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=knowledge");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "All the above", "knowledge");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "PC_windows,knowledge_of_trading_all_the_above");
    }

    @Test(description = "TC 16.5. Vefiry that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationKnowledgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (knowledge) -PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=knowledge");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "All the above", "knowledge");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 17.1. Verify the user is redirected to the 2nd step - language verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepLanguageTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (language) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=plang:all");
        pennyStocks.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(pennyStocks.languageField);
        Assert.assertTrue(pennyStocks.languageField.isDisplayed());
    }

    @Test(description = "TC 17.2. Verify the 2nd step -  desired communication language verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredLanguageParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (language) - PennyStocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=plang:all");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "English", "language");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 17.3. Verify the 2nd step - desired communication language verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForLanguageParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (language) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "?fts=plang:all");
        pennyStocks.checkErrorMessageForParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber(), "English", "-- Select --", "language");
        pennyStocks.assertSecondStepErrorMessage("language");
    }

    @Test(description = "TC 17.4. Verify the desired communication language value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void languageParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (language) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "?fts=plang:all");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber(), "English", "language");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "PC_windows,lang_EN");
    }

    @Test(description = "TC 17.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationLanguageTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (language) ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "?fts=plang:all");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber(), "English", "language");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 18.1. Verify the account is registered successfully with NON-valid tag in the URL")
    @Parameters({"regulation", "countryCode"})
    public void nonValidParameterInTheUrlTest(String regulation,String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Demo account is successfully registered with wrong parameters in the URL - " + regulation);
        openUrl(baseUrl + "?fts=testq-testa");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),countryCode,TestData.generatePhoneNumber());
        readyFortrade.assertURL("https://ready.fortrade.com/");
        readyFortrade.clickNotSerbResBtn();
        readyFortrade.clickUsePassBtn();
        readyFortrade.clickMenuBtn();
        readyFortrade.checkRegulation(regulation);
    }

    @Test(description = "TC 18.2. Verify the 2nd step cannot be submitted if all parameter values are not completed")
    @Parameters({"regulation", "countryCode"})
    public void noDataOnTheSecondStepTest(String regulation,String countryCode) {
        ScreenshotUtil.setCustomName("Error messages (all parameters) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "?fts=age-annual-saving-knowledge-plang:all");
        pennyStocks.fillTheFormOnTheSecondStepWithWrongData(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "$15,000-$50,000", "$50,000-$100,000",
                "All the above", "English");
        pennyStocks.assertSecondStepErrorMessageAllParameters();
    }

    @Test(description = "TC 19.1. Verify the cutom tag field in the CRM contains Dummy value ")
    @Parameters({"regulation","countryCode"})
    public void dummyLeadRegistration(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "?fts=annual-saving-knowledge-age-plang:all&ftsquery=age-equals(1,3)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.generatePhoneNumber(), "25-34", "$50,000-$100,000", "$50,000-$100,000",
                "All the above","English");
        pennyStocks.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 19.2. Verify the cutom tag field in the CRM contains Dummy value ")
    @Parameters({"regulation","countryCode"})
    public void dummy_Lead_Registration(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy - " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "?fts=annual-saving-knowledge-age-plang:all&ftsquery=age-equals(1_3)-or-[saving-equals(1_2_3)-and-knowledge-notequals(5)]");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.generatePhoneNumber(), "25-34", "$15,000-$50,000", "$50,000-$100,000",
                "All the above","English");
        pennyStocks.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 19.3. Verify the custom tag field in the CRM contains Invalid value")
    @Parameters({"regulation","countryCode"})
    public void invalidLeadRegistration(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom tag - Invalid " + regulation + "regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "?fts=annual-saving-knowledge-age-plang:all&ftsquery=age(1)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "None","English");
        pennyStocks.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 19.4. Verify the custom tag field in the CRM contains Invalid value")
    @Parameters({"regulation","countryCode"})
    public void invalid_Lead_Registration(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom tag - Invalid - " + regulation + "regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "?fts=annual-saving-knowledge-age-plang:all&ftsquery=age(1)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "None","English");
        pennyStocks.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 19.5. Verify the custom tag field in the CRM is empty")
    @Parameters({"regulation","countryCode"})
    public void emptyLeadRegistration(String regulation,  String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "?fts=annual-saving-knowledge-age-plang:all&ftsquery=age-equals(1,3)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "All the above","English");
        pennyStocks.assertURL("https://ready.fortrade.com/");
        crmPage.checkCrmData(email, TestData.fullName, regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.6. Verify the custom tag field in the CRM is empty")
    @Parameters({"regulation","countryCode"})
    public void empty_Lead_Registration(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty - " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "?fts=annual-saving-knowledge-age-plang:all&ftsquery=age-equals(1,3)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "All the above","English");
        pennyStocks.assertURL("https://ready.fortrade.com/");
        crmPage.checkCrmData(email, TestData.fullName, regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.7. Verify the custom tag field in the CRM is Dummy if ftsquery device parameter is same as device " +
            "and OS used for demo account registration")
    @Parameters({"regulation","countryCode"})
    public void deviceIsSameAsParameter(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy - device " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.pennystocks-uk.com/lps/pro-dark/en?ftsquery=device-equals(1)");
        openUrl(baseUrl + "?ftsquery=device-equals(1)");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        pennyStocks.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }
    @Test(description = "TC 19.8. Verify the custom tag field in the CRM is invalid if syntax is not valid")
    @Parameters({"regulation","countryCode"})
    public void nonValidParameterSyntax(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Invalid - device " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.pennystocks-uk.com/lps/pro-dark/en?ftsquery=device(1)");
        openUrl(baseUrl + "?ftsquery=device(1)");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        pennyStocks.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }
    @Test(description = "TC 19.9. Verify the custom tag field in the CRM is empty if ftsquery device parameter is not " +
            "same as device and OS used for demo account registration")
    @Parameters({"regulation","countryCode"})
    public void deviceIsNotSameAsParameter(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty - device " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.pennystocks-uk.com/lps/pro-dark/en?ftsquery=device-equals(4)");
        openUrl(baseUrl + "?ftsquery=device-equals(4)");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        pennyStocks.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.10. Verify the custom tag field in the CRM is invalid if ftsquery device parameter " +
            "contains device and OS non valid index value")
    @Parameters({"regulation","countryCode"})
    public void deviceParameterContainsNonValidValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - non valid device value " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.pennystocks-uk.com/lps/pro-dark/en?ftsquery=device-equals(0)");
        openUrl(baseUrl + "?ftsquery=device-equals(0)");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        pennyStocks.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 20.3. Verify that the Custom Tag in the CRM is empty")
    @Parameters({"regulation","countryCode"})
    public void checkingTheCustomTag(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 20-3-Custom Tag is empty " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.pennystocks-uk.com/lps/pro-dark/en?fts=age-annual-saving-knowledge-plang:all");
        openUrl(baseUrl + "?fts=age-annual-saving-knowledge-plang:all");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "All the above","English");
        pennyStocks.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 22.1. Verify that the Language field in the CRM contains expected value (in this case FR)")
    @Parameters({"regulation", "countryCode"})
    public void checkLanguageFieldContainsExpectedValue(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Language field in the CRM contains expected (FR) value " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.pennystocks-uk.com/lps/pro-dark/en");
        openUrl(baseUrl + "?userLang=FR");
        pennyStocks.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        readyFortrade.clickNotSerbResBtn();
        readyFortrade.clickUsePassBtn();
        readyFortrade.assertDisplayedLanguage("FR");
        crmPage.checkCrmData(email, TestData.fullName, regulation);
        crmPage.checkLanguageField(email, "fr");
    }

    @Test(description = "TC 22.2. Verify that the Language field in the CRM contains the default value (the language of the base page URL) when we enter the wrong language in the userLang parameter")
    @Parameters({"regulation","countryCode"})
    public void checkLanguageFieldContainsDefaultValue(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Language field in the CRM contains default (EN) value - " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.pennystocks-uk.com/lps/pro-dark/en");
        openUrl(baseUrl + "?userLang=FRA");
        pennyStocks.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        readyFortrade.clickNotSerbResBtn();
        readyFortrade.clickUsePassBtn();
        readyFortrade.assertDisplayedLanguage("EN");
        crmPage.checkCrmData(email, TestData.fullName, regulation);
        crmPage.checkLanguageField(email, "en");
    }

    @Test(description = "TC 23.1. Verify that the Custom Tag field in the CRM contains the DummyP value")
    @Parameters({"regulation","countryCode"})
    public void dummypParameter(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-1-Custom tag-DummyP value ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.pennystocks-uk.com/lps/pro-dark/en" +
                "?ftsquery=device-equals(1)&dummyP=1");
        openUrl(baseUrl + "?ftsquery=device-equals(1)&dummyP=1");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        pennyStocks.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("DummyP");
    }

    @Test(description = "TC 23.2. Verify that the Custom Tag field in the CRM contains the Dummy value")
    @Parameters({"regulation","countryCode"})
    public void dummyParameter(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-2-Custom tag - Dummy value ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.pennystocks-uk.com/lps/pro-dark/en" +
                "?ftsquery=device-equals(1)&dummyP=0");
        openUrl(baseUrl + "?ftsquery=device-equals(1)&dummyP=0");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        pennyStocks.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 23.3. Verify that the dummyP parameter is ignored when it's not correctly typed in the URL")
    @Parameters({"regulation","countryCode"})
    public void parameterDummy(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-3-Custom tag - Dummy value ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.pennystocks-uk.com/lps/pro-dark/en" +
                "?ftsquery=device-equals(1)&dummyp=1");
        openUrl(baseUrl + "?ftsquery=device-equals(1)&dummyp=1");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        pennyStocks.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.1. Verify that the custom tag field in the CRM contains Dummy parameter ")
    @Parameters({"regulation","countryCode"})
    public void customTagContainsDummy(String regulation,  String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-1-Custom tag field - dummy ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.pennystocks-uk.com/lps/pro-dark/en?Dummy=true");
        openUrl(baseUrl + "?Dummy=true");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        pennyStocks.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.2. Verify that the custom tag field in the CRM contains Dummy parameter ")
    @Parameters({"regulation","countryCode"})
    public void customTagContainsDummyValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-2-Custom tag field - dummy ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.pennystocks-uk.com/lps/pro-dark/en?Dummy=1");
        openUrl(baseUrl + "?Dummy=1");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        pennyStocks.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

}
