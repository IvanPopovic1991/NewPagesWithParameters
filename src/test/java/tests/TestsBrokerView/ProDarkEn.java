package tests.TestsBrokerView;

import core.actions.ElementActions;
import core.base.BaseTest;
import core.config.ConfigReader;
import core.pages.CrmPage;
import core.pages.BrokerView;
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

    private BrokerView brokerView;
    private ReadyFortrade readyFortrade;
    private CrmPage crmPage;
    private YopmailPage yopmailPage;
    private String baseUrl;

    @BeforeMethod
    public void initPages() {
        brokerView = new BrokerView();
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
        brokerView.checkLogoClickability();
        brokerView.assertURL("https://dlp.brokereviews.com/lps/pro-dark/en");
    }

    @Test(description = "TC 2.1. Verify the demo account is registered successfully with valid data")
    @Parameters({"countryCode","regulation"})
    public void demoAccountRegistrationTest(String countryCode, String regulation) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Demo account is successfully registered - BrokerView");
        brokerView.registerDemoAccount(TestData.firstName, TestData.lastName, TestData.generateEmail(), countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        readyFortrade.clickNotSerbResBtn();
        readyFortrade.clickUsePassBtn();
        readyFortrade.clickMenuBtn();
        readyFortrade.checkRegulation(regulation);
    }

    @Test(description = "TC 3.1. Verify that the account cannot be registered with already registered email address")
    @Parameters({"countryCode"})
    public void alreadyRegisteredEmailAddressTest(String countryCode) {
        ScreenshotUtil.setCustomName("Already registered email address -BrokerView");
        Allure.step("Redirected to https://dlp.brokereviews.com/lps/pro-dark/en");
        String email = TestData.generateEmail();
        brokerView.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        openUrl(ConfigReader.getBaseUrl());
        brokerView.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        brokerView.assertAlrRegEmailErrorMsg();
    }

    @Test(description = "TC 3.2 Verify the demo account is not registered successfully with invalid data")
    @Parameters({"countryCode"})
    public void nonValidDataTest(String countryCode) {
        ScreenshotUtil.setCustomName("Non valid data - BrokerView");
        Allure.step("Redirected to https://dlp.brokereviews.com/lps/pro-dark/en");
        brokerView.registerDemoAccount("123", "574", "abcd134324", countryCode, "fsfsdfd");
        brokerView.assertErrorMessages();
    }

    @Test(description = "TC 3.3. Verify that the account cannot be registered with already registered phone number")
    @Parameters({"countryCode"})
    public void alreadyRegisteredPhoneNumberTest(String countryCode) {
        ScreenshotUtil.setCustomName("Already registered phone number - BrokerView");
        Allure.step("Redirected to https://dlp.brokereviews.com/lps/pro-dark/en");
        String phoneNumber = TestData.generatePhoneNumber();
        brokerView.registerDemoAccount(TestData.firstName, TestData.lastName, TestData.generateEmail(), countryCode, phoneNumber);
        readyFortrade.assertURL(TestData.appUrl);
        openUrl(ConfigReader.getBaseUrl());
        brokerView.registerDemoAccount(TestData.firstName, TestData.lastName, TestData.generateEmail(), countryCode, phoneNumber);
        brokerView.assertErrMsgForAlreadyRegisteredAccount();
    }

    @Test(description = "TC 3.4. Verify that the account cannot be registered with already registered email address and phone number")
    @Parameters({"countryCode"})
    public void alreadyRegisteredEmailAndPhoneTest(String countryCode) {
        ScreenshotUtil.setCustomName("Already registered email address and phone number - BrokerView");
        Allure.step("Redirected to https://dlp.brokereviews.com/lps/pro-dark/en");
        String email = TestData.generateEmail();
        String phone = TestData.generatePhoneNumber();
        brokerView.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, phone);
        readyFortrade.assertURL(TestData.appUrl);
        openUrl(ConfigReader.getBaseUrl());
        brokerView.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, phone);
        brokerView.assertAlrRegEmailErrorMsg();
    }

    @Test(description = "TC 3.5. Verify the demo account is not registered successfully with empty fields")
    public void emptyDataRegistrationTest() {
        ScreenshotUtil.setCustomName("Unsuccessfully account registration with empty data - BrokerView");
        Allure.step("Redirected to https://dlp.brokereviews.com/lps/pro-dark/en");
        brokerView.clickGetStartedBtn();
        //missing assertation
    }

    @Test(description = "TC 4.1 - Verify that the invalid data for the First Name field will show valid error message with red border")
    public void verifyErrorMessageForFirstNameTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for First Name - BrokerView");
        Allure.step("Verified error message and border color for First Name field");
        ElementActions.type(brokerView.firstName, "123", "first name");
        brokerView.assertBorderColor(brokerView.borderColorForFirstName, "border-color", TestData.redBorderColor);
        brokerView.assertFirstStepErrorMessage(TestData.firstNameErrorMessage);
    }

    @Test(description = "TC 4.2 - Verify that the invalid data for the Last Name field will show valid error message with red border")
    public void verifyErrorMessageForLastNameTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Last Name - BrokerView");
        Allure.step("Verified error message and border color for Last Name field");
        ElementActions.type(brokerView.lastName, "456", "last name");
        brokerView.assertBorderColor(brokerView.borderColorForLastName, "border-color", TestData.redBorderColor);
        brokerView.assertFirstStepErrorMessage(TestData.lastNameErrorMessage);
    }

    @Test(description = "TC 4.3 - Verify that the invalid data for the Email field will show valid error message with red border")
    public void verifyErrorMessageForEmailTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Email - BrokerView");
        Allure.step("Verified error message and border color for Email field");
        ElementActions.type(brokerView.email, "dsv124234/=", "email");
        brokerView.assertBorderColor(brokerView.borderColorForEmail, "border-color", TestData.redBorderColor);
        brokerView.assertFirstStepErrorMessage(TestData.emailErrorMessage);
    }

    @Test(description = "TC 4.5 - Verify that the invalid data for Phone field will show valid error message with red border")
    public void verifyErrorMessageForPhoneTest() {
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Phone - FortadeR");
        Allure.step("Verified error message and border color for Phone field");
        ElementActions.type(brokerView.phoneNumber, "0034334424558200", "phone");
        brokerView.assertBorderColor(brokerView.borderColorForPhone, "border-color", TestData.redBorderColor);
        brokerView.assertFirstStepErrorMessage(TestData.wrongPhoneErrorMsgOther);
    }

    @Test(description = "TC 4.6 - Verify that the Last Name cannot be the same as First name.")
    public void sameFNameAndLNameTest() {
        ScreenshotUtil.setCustomName("Your first name must be different from your last name - BrokerView");
        Allure.step("Check for error message for the same first and last name.");
        brokerView.insertFirstName("Test");
        brokerView.insertLastName("Test");
        ElementActions.click(brokerView.email, "email address");
        brokerView.assertFirstStepErrorMessage(TestData.sameFirstNameErrorMessage);
        brokerView.assertFirstStepErrorMessage(TestData.sameLastNameErrorMessage);
    }

    @Test(description = "TC 4.7 - Verify that the First Name cannot be the same as Last name.")
    public void sameLNameAndFNameTest() {
        ScreenshotUtil.setCustomName("Your last name must be different from your first name - BrokerView");
        Allure.step("Check for error message for the same last and first name.");
        brokerView.insertLastName("Test");
        brokerView.insertFirstName("Test");
        ElementActions.click(brokerView.email, "email address");
        brokerView.assertFirstStepErrorMessage(TestData.sameLastNameErrorMessage);
        brokerView.assertFirstStepErrorMessage(TestData.sameFirstNameErrorMessage);
    }

    @Test(description = "TC 7.1 - Verify the Privacy Policy link works with left click")
    public void checkHeaderPrivacyPolicyLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Header privacy policy link - BrokerView");
        Allure.step("Left click on the header privacy policy link.");
        brokerView.checkLinksWithLeftClick(brokerView.headerPrivacyPolicyLink, "header privacy policy link", TestData.privacyPolicyUrl);
    }

    @Test(description = "TC 7.1.1 - Verify the Privacy Policy link works with right click")
    public void checkHeaderPrivacyPolicyLinkWithRightClickTest() {
        Allure.step("Right click on the header privacy policy link.");
        brokerView.checkLinksWithRightClick(brokerView.headerPrivacyPolicyLink, "header privacy policy link", TestData.privacyPolicyUrl);
    }

    @Test(description = "TC 7.2 - Verify the Terms and Conditions link works with left click")
    public void checkHeaderTermsAndConditionsLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Header terms and conditions link - BrokerView");
        Allure.step("Left click on the terms and conditions link.");
        brokerView.checkLinksWithLeftClick(brokerView.headerTermsAndConditionsLink, "header terms and conditions link", TestData.termsAndConditionsUrl);
    }

    @Test(description = "TC 7.2.1 - Verify the Terms and Conditions link works with right click")
    public void checkHeaderTermsAndConditionsLinkWithRightClickTest() {
        Allure.step("Right click on the terms and conditions link.");
        brokerView.checkLinksWithRightClick(brokerView.headerTermsAndConditionsLink, "header terms and conditions link", TestData.termsAndConditionsUrl);
    }

    @Test(description = "TC 7.3. Verify the click here link works with left click")
    public void checkClickHereTest() {
        ScreenshotUtil.setCustomName("CLick here link - BrokerView");
        Allure.step("Left mouse click on click here link");
        brokerView.checkLinksWithLeftClick(brokerView.clickHere, "click here", TestData.clickHereUrl);
    }

    @Test(description = "TC 7.3.1 Verify the click here link works with right click")
    public void checkClickHereWithRightClick() {
        Allure.step("Right mouse click on the click here link");
        brokerView.checkLinksWithRightClick(brokerView.clickHere, "click here", TestData.clickHereUrl);
    }

    @Test(description = "TC 7.4 - Verify the Already have an account? link works with left click")
    public void checkAlreadyHaveAnAccountLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Already have an account link - BrokerView");
        Allure.step("Left click on the already have an account link.");
        brokerView.checkLinksWithLeftClick(brokerView.alreadyHaveAnAccountLink, "already have an account link", TestData.alreadyHaveAnAccountUrl);
    }

    @Test(description = "TC 7.4.1 - Verify the Already have an account? link works with right click")
    public void checkAlreadyHaveAnAccountLinkWithRightClickTest() {
        Allure.step("Right click on the already have an account link.");
        brokerView.checkLinksWithRightClick(brokerView.alreadyHaveAnAccountLink, "already have an account link", TestData.alreadyHaveAnAccountUrl);
    }

    @Test(description = "TC 7.5 - Verify the click on Contact us link opens new mail window")
    public void checkContactUsLinkTest() {
        ScreenshotUtil.setCustomName("Contact Us link - BrokerView");
        Allure.step("Left click on the contact us link.");
        brokerView.checkMailLinks(brokerView.contactUsLink, "href", TestData.contactUsUrl);
    }

    @Test(description = "TC 7.6 - Verify the click on support@fortrade.com link opens email window")
    public void checkSupportLinkTest() {
        ScreenshotUtil.setCustomName("Support link - BrokerView");
        Allure.step("Right click on the contact us link.");
        brokerView.checkMailLinks(brokerView.supportLink, "href", TestData.contactUsUrl);
    }

    @Test(description = "TC 8.5 - Verify the GB21026472 (FSC) link works with left click")
    public void checkFscLinkWithLeftClickTest() {
        ScreenshotUtil.setCustomName("Fsc link - BrokerView");
        Allure.step("Left click on the FSC link.");
        brokerView.checkLinksWithLeftClick(brokerView.footerFSCLink, "fsc link", TestData.fscUrl);
    }

    @Test(description = "TC 8.5.1 - Verify the GB21026472 (FSC) link works with right click")
    public void checkFscLinkWithRightClickTest() {
        Allure.step("Right click on the FSC link.");
        brokerView.checkLinksWithRightClick(brokerView.footerFSCLink, "fsc link", TestData.fscUrl);
    }

    @Test(description = "9.1. Verify the border color in the CRM for regulation")
    @Parameters({"countryCode","regulation"})
    public void checkAccountRegulationTest(String countryCode, String regulation) {
        ScreenshotUtil.setCustomName("Account regulation in CRM - BrokerView");
        String email = TestData.generateEmail();
        brokerView.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, "Testq Testa", regulation);
    }

    @Test(description = "9.2. Verify the account details is displayed correctly in the CRM")
    @Parameters({"countryCode","regulation"})
    public void checkAccountDetailsInCrmTest(String countryCode, String regulation) {
        ScreenshotUtil.setCustomName("Account details in CRM - BrokerView");
        String email = TestData.generateEmail();
        brokerView.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, "Testq Testa", regulation);
    }

    @Test(description = "9.3. Verify the tags are displayed correctly in CRM")
    @Parameters({"countryCode","regulation"})
    public void checkTagsInCrmTest(String countryCode,String regulation) {
        ScreenshotUtil.setCustomName("Marketing tags BrokerView page ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=age-annual-saving-knowledge-plang:all&tg=ivanA" +
                "1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_" +
                "Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        brokerView.registerDemoAccountWithParameters(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(),
                "25-34", "$15,000-$50,000", "$50,000-$100,000", "All the above", "English");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, "Testq Testa", regulation);
        crmPage.checkCrmTags();
    }

    @Test(description = "9.4. Verify that the Link ID field contains 'PC_windows' value in the CRM")
    @Parameters({"countryCode"})
    public void checkLinkIDPCWindows(String countryCode){
        ScreenshotUtil.setCustomName("Link ID tag contains the 'PC_windows' value - Broker Views");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en");
        brokerView.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName, "FSC");
        crmPage.checkLinkIdValue("PC_windows");
    }

    @Test(description = "TC 10.1. Verify the email is sent on the new account email")
    @Parameters({"countryCode"})
    public void emailIsReceived(String countryCode) {
        ScreenshotUtil.setCustomName("Email is received successfully - BrokerView page ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en");
        brokerView.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        readyFortrade.assertURL(TestData.appUrl);
        readyFortrade.clickNotSerbResBtn();
        readyFortrade.clickUsePassBtn();
        openUrl(TestData.yopmailUrl);
        yopmailPage.findEmail(email);
    }

    @Test(description = "TC 12.5. Verify that message 'We sent you the code again' is received")
    @Parameters({"countryCode"})
    public void iDidntReceiveTheCodeTest(String countryCode) {
        ScreenshotUtil.setCustomName("I didn't received the code link - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=sms");
        brokerView.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        brokerView.checkIDidntReceiveTheCodeLink();
    }

    @Test(description = "TC 12.6. Verify that wrong code cannot be submitted (negative test case)")
    @Parameters({"countryCode"})
    public void wrongCodeCannotBeSubmittedTest(String countryCode) {
        ScreenshotUtil.setCustomName("Wrong code cannot be submitted - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=sms-age-annual-saving-knowledge-plang:all");
        brokerView.fillTheFormOnTheSecondStepWithWrongSmsCode(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(),
                "25-34", "$15,000-$50,000", "$50,000-$100,000", "All the above", "English",
                "1", "1", "1", "1");
        brokerView.assertErrorMessageForWrongSmsCode();
    }

    @Test(description = "TC 12.9. Verify if user clicks pencil icon the same is returned to the 1st widget")
    @Parameters({"countryCode"})
    public void editPencilButtonTest(String countryCode) {
        ScreenshotUtil.setCustomName("Edit pencil button redirects to the first step - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=sms");
        brokerView.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        brokerView.checkEditPencilButton();
    }

    @Test(description = "TC 13.1. Verify the user is redirected to the 2nd step - age verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepAgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (age) - BrokerView ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=age");
        brokerView.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(brokerView.age);
        Assert.assertTrue(brokerView.age.isDisplayed());
    }

    @Test(description = "TC 13.2. Verify the 2nd step - age verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredAgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (age) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=age");
        brokerView.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "age");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 13.3. Verify the 2nd step - age verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForAgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (age) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=age");
        brokerView.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "-- Select --", "age");
        brokerView.assertSecondStepErrorMessage("age");
    }

    @Test(description = "TC 13.4. Verify the age value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void ageParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (age) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=age");
        brokerView.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "age");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "25_34_age");
    }

    @Test(description = "TC 13.5. Vefiry that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationAgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (age) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=age");
        brokerView.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "age");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 14.1. Verify the user is redirected to the 2nd step - annual verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepAnnualTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (annual) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=annual");
        brokerView.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(brokerView.annual);
        Assert.assertTrue(brokerView.annual.isDisplayed());
    }

    @Test(description = "TC 14.2. Verify the 2nd step - annual income verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredAnnualParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (annual) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=annual");
        brokerView.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "annual");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 14.3. Verify the 2nd step - annual income verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForAnnualParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (annual) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=annual");
        brokerView.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "-- Select --", "annual");
        brokerView.assertSecondStepErrorMessage("annual");
    }

    @Test(description = "TC 14.4. Verify the annual value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void annualParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (annual) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=annual");
        brokerView.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "annual");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "15000_50000_annual");
    }

    @Test(description = "TC 14.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationAnnualTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (annual) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=annual");
        brokerView.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$15,000-$50,000", "annual");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 15.1. Verify the user is redirected to the 2nd step - saving and investments verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepSavingTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (saving) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=saving");
        brokerView.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(brokerView.saving);
        Assert.assertTrue(brokerView.saving.isDisplayed());
    }

    @Test(description = "TC 15.2. Verify the 2nd step -  value of saving and investments verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredSavingParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (saving) -BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=saving");
        brokerView.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "saving");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 15.3. Verify the 2nd step - value of saving and investments verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForSavingParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (saving) -BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=saving");
        brokerView.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "-- Select --", "saving");
        brokerView.assertSecondStepErrorMessage("saving");
    }

    @Test(description = "TC 15.4. Verify the saving value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void savingParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (saving) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=saving");
        brokerView.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "saving");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "50000_100000_savings");
    }

    @Test(description = "TC 15.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationSavingTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (saving) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=saving");
        brokerView.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "$50,000-$100,000", "saving");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 16.1. Verify the user is redirected to the 2nd step - knowledge of trading verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepKnowledgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (knowledge) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=knowledge");
        brokerView.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(brokerView.knowledge);
        Assert.assertTrue(brokerView.knowledge.isDisplayed());
    }

    @Test(description = "TC 16.2. Verify the 2nd step -  knowledge of trading verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredKnowledgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (knowledge) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=knowledge&");
        brokerView.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "All the above", "knowledge");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 16.3. Verify the 2nd step - knowledge of trading verification window cannot be submitted if it's not completed")
    @Parameters({"countryCode"})
    public void errorMessageForKnowledgeParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("Error message (knowledge) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=knowledge");
        brokerView.checkErrorMessageForParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "All the above", "-- Select --", "knowledge");
        brokerView.assertSecondStepErrorMessage("knowledge");
    }

    @Test(description = "TC 16.4. Verify the knowledge of trading value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"countryCode"})
    public void knowledgeParameterCRMTest(String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (knowledge) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=knowledge");
        brokerView.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "All the above", "knowledge");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "knowledge_of_trading_all_the_above");
    }

    @Test(description = "TC 16.5. Vefiry that SMS verification field has emtpty (-) value")
    @Parameters({"countryCode"})
    public void smsVerificationKnowledgeTest(String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (knowledge) -BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=knowledge");
        brokerView.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "All the above", "knowledge");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 17.1. Verify the user is redirected to the 2nd step - language verification window")
    @Parameters({"countryCode"})
    public void redirectionToTheSecondStepLanguageTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (language) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=plang:all");
        brokerView.goToSecondStep(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
        WaitUtil.waitForVisible(brokerView.languageField);
        Assert.assertTrue(brokerView.languageField.isDisplayed());
    }

    @Test(description = "TC 17.2. Verify the 2nd step -  desired communication language verification window is successfully completed and submitted")
    @Parameters({"countryCode"})
    public void registeredLanguageParameterTest(String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (language) - BrokerView");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl("https://dlp.brokereviews.com/lps/pro-dark/en?fts=plang:all");
        brokerView.registerDemoAccountWithParameter(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "English", "language");
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 17.3. Verify the 2nd step - desired communication language verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForLanguageParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (language) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "?fts=plang:all");
        brokerView.checkErrorMessageForParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber(), "English", "-- Select --", "language");
        brokerView.assertSecondStepErrorMessage("language");
    }

    @Test(description = "TC 17.4. Verify the desired communication language value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void languageParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (language) " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "?fts=plang:all");
        brokerView.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber(), "English", "language");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkParameterLinkIdInTheCRM(email, "lang_EN");
    }

    @Test(description = "TC 17.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationLanguageTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (language) ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "?fts=plang:all");
        brokerView.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber(), "English", "language");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkSMSValueParameter(email, "--");
    }

    @Test(description = "TC 18.1. Verify the account is registered successfully with NON-valid tag in the URL")
    @Parameters({"regulation", "countryCode"})
    public void nonValidParameterInTheUrlTest(String regulation,String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Demo account is successfully registered with wrong parameters in the URL - " + regulation);
        openUrl(baseUrl + "?fts=testq-testa");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),countryCode,TestData.generatePhoneNumber());
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
        brokerView.fillTheFormOnTheSecondStepWithWrongData(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber(), "25-34", "$15,000-$50,000", "$50,000-$100,000",
                "All the above", "English");
        brokerView.assertSecondStepErrorMessageAllParameters();
    }

    @Test(description = "TC 19.1. Verify the cutom tag field in the CRM contains Dummy value ")
    @Parameters({"regulation","countryCode"})
    public void dummyLeadRegistration(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "?fts=annual-saving-knowledge-age-plang:all&ftsquery=age-equals(1,3)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        brokerView.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.generatePhoneNumber(), "25-34", "$50,000-$100,000", "$50,000-$100,000",
                "All the above","English");
        brokerView.assertURL(TestData.appUrl);
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
        brokerView.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.generatePhoneNumber(), "25-34", "$15,000-$50,000", "$50,000-$100,000",
                "All the above","English");
        brokerView.assertURL(TestData.appUrl);
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
        brokerView.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "None","English");
        brokerView.assertURL(TestData.appUrl);
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
        brokerView.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "None","English");
        brokerView.assertURL(TestData.appUrl);
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
        brokerView.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "All the above","English");
        brokerView.assertURL("https://ready.fortrade.com/");
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
        brokerView.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "All the above","English");
        brokerView.assertURL("https://ready.fortrade.com/");
        crmPage.checkCrmData(email, TestData.fullName, regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.7. Verify the custom tag field in the CRM is Dummy if ftsquery device parameter is same as device " +
            "and OS used for demo account registration")
    @Parameters({"regulation","countryCode"})
    public void deviceIsSameAsParameter(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy - device " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en?ftsquery=device-equals(1)");
        openUrl(baseUrl + "?ftsquery=device-equals(1)");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }
    @Test(description = "TC 19.8. Verify the custom tag field in the CRM is invalid if syntax is not valid")
    @Parameters({"regulation","countryCode"})
    public void nonValidParameterSyntax(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Invalid - device " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en?ftsquery=device(1)");
        openUrl(baseUrl + "?ftsquery=device(1)");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }
    @Test(description = "TC 19.9. Verify the custom tag field in the CRM is empty if ftsquery device parameter is not " +
            "same as device and OS used for demo account registration")
    @Parameters({"regulation","countryCode"})
    public void deviceIsNotSameAsParameter(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty - device " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en?ftsquery=device-equals(4)");
        openUrl(baseUrl + "?ftsquery=device-equals(4)");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.10. Verify the custom tag field in the CRM is invalid if ftsquery device parameter " +
            "contains device and OS non valid index value")
    @Parameters({"regulation","countryCode"})
    public void deviceParameterContainsNonValidValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - non valid device value " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en?ftsquery=device-equals(0)");
        openUrl(baseUrl + "?ftsquery=device-equals(0)");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 20.3. Verify that the Custom Tag in the CRM is empty")
    @Parameters({"regulation","countryCode"})
    public void checkingTheCustomTag(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 20-3-Custom Tag is empty " + regulation + " regulation");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en?fts=age-annual-saving-knowledge-plang:all");
        openUrl(baseUrl + "?fts=age-annual-saving-knowledge-plang:all");
        brokerView.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                TestData.generatePhoneNumber(), "45-54", "$15,000-$50,000", "$100,000-$250,000",
                "All the above","English");
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 22.1. Verify that the Language field in the CRM contains expected value (in this case FR)")
    @Parameters({"regulation", "countryCode"})
    public void checkLanguageFieldContainsExpectedValue(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Language field in the CRM contains expected (FR) value " + regulation);
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en");
        openUrl(baseUrl + "?userLang=FR");
        brokerView.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
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
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en");
        openUrl(baseUrl + "?userLang=FRA");
        brokerView.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, TestData.generatePhoneNumber());
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
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en" +
                "?ftsquery=device-equals(1)&dummyP=1");
        openUrl(baseUrl + "?ftsquery=device-equals(1)&dummyP=1");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("DummyP");
    }

    @Test(description = "TC 23.2. Verify that the Custom Tag field in the CRM contains the Dummy value")
    @Parameters({"regulation","countryCode"})
    public void dummyParameter(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-2-Custom tag - Dummy value ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en" +
                "?ftsquery=device-equals(1)&dummyP=0");
        openUrl(baseUrl + "?ftsquery=device-equals(1)&dummyP=0");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 23.3. Verify that the dummyP parameter is ignored when it's not correctly typed in the URL")
    @Parameters({"regulation","countryCode"})
    public void parameterDummy(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-3-Custom tag - Dummy value ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en" +
                "?ftsquery=device-equals(1)&dummyp=1");
        openUrl(baseUrl + "?ftsquery=device-equals(1)&dummyp=1");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.1. Verify that the custom tag field in the CRM contains Dummy parameter ")
    @Parameters({"regulation","countryCode"})
    public void customTagContainsDummy(String regulation,  String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-1-Custom tag field - dummy ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en?Dummy=true");
        openUrl(baseUrl + "?Dummy=true");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.2. Verify that the custom tag field in the CRM contains Dummy parameter ")
    @Parameters({"regulation","countryCode"})
    public void customTagContainsDummyValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-2-Custom tag field - dummy ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en?Dummy=1");
        openUrl(baseUrl + "?Dummy=1");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.3. Verify that the custom tag field in the CRM contains '' (empty) value")
    @Parameters({"regulation","countryCode"})
    public void customTagContainsEmpty(String regulation,  String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-3-Custom tag field - empty ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en?Dummy=false");
        openUrl(baseUrl + "?Dummy=false");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 24.4. Verify that the custom tag field in the CRM contains '' (empty) value")
    @Parameters({"regulation","countryCode"})
    public void customTagContainsEmptyValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-4-Custom tag field - empty ");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en?Dummy=0");
        openUrl(baseUrl + "?Dummy=0");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("");
    }


    @Test(description = "TC 25.1. Verify that the Link Id field in the CRM contains '{number}_IPL' value")
    @Parameters({"regulation","countryCode"})
    public void checkNumberIplValue(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("TC 25-1-Link Id tag field - {number}_IPL - Broker Views");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en?tag1=1452789330&");
        openUrl(baseUrl + "?tag1=1452789330");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("0.0953@1500");
    }

    @Test(description = "TC 25.2. Verify that the Link Id field in the CRM contains 'missingTag1_IPL' value")
    @Parameters({"regulation","countryCode"})
    public void checkMissingTag1IplValue(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("TC 25-2-Link Id tag field - missingTag1_IPL - Broker Views");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en?tag1=123abc&");
        openUrl(baseUrl + "?tag1=123abc");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("missingTag1");
    }

    @Test(description = "TC 25.3. Verify that the Link Id field in the CRM contains 'missingCID_IPL' value")
    @Parameters({"regulation","countryCode"})
    public void checkMissingCidIplValue(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("TC 25-3-Link Id tag field - missingCID_IPL - Broker Views");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en?tag1=123456789&");
        openUrl(baseUrl + "?tag1=123456789");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("missingCID");
    }

    @Test(description = "TC 25.4. Verify that the Link Id field in the CRM contains 'divByZero_IPL' value")
    @Parameters({"regulation","countryCode"})
    public void checkDivByZeroIplValue(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("TC 25-4-Link Id tag field - missingCID_IPL - Broker Views");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the https://dlp.brokereviews.com/lps/pro-dark/en?tag1=930863512&");
        openUrl(baseUrl + "?tag1=930863512");
        brokerView.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,TestData.generatePhoneNumber());
        brokerView.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("divByZero");
    }

}
