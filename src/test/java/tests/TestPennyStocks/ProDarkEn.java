package tests.TestPennyStocks;

import core.actions.ElementActions;
import core.annotations.RunForRegulations;
import core.base.BaseTest;
import core.config.ConfigReader;
import core.enums.FirstStepField;
import core.enums.SecondStepField;
import core.localization.UrlProvider;
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

    private PennyStocks pennyStocks;
    private ReadyFortrade readyFortrade;
    private CrmPage crmPage;
    private String baseUrl;

    @Parameters({"tag"})
    @BeforeMethod
    public void initPages(String tag){
        pennyStocks = new PennyStocks();
        readyFortrade = new ReadyFortrade();
        crmPage = new CrmPage();
        baseUrl = ConfigReader.getBaseUrl();
        baseUrl += tag;
        openUrl(baseUrl);
    }

    @Test(description = "TC 2.1. Verify the demo account is registered successfully with valid data")
    @Parameters({"regulation","countryCode"})
    public void demoAccountRegistrationTest(String regulation,String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Demo account is successfully registered - " + regulation + " regulation - Penny Stocks");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        readyFortrade.clickUsePassBtn();
        readyFortrade.clickMenuBtn();
        readyFortrade.checkRegulation(regulation);
    }

    @Test(description = "TC 3.1. Verify that the account cannot be registered with already registered email address")
    @Parameters({"regulation","countryCode"})
    public void alreadyRegisteredEmailAddressTest(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Already registered email address - " + regulation + " regulation - Penny Stocks");
        Allure.step("Redirected to the page url");
        String email = TestData.generateEmail();
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        openUrl(baseUrl);
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        pennyStocks.assertErrMsgForAlreadyRegisteredAccount();
    }

    @Test(description="TC 3.2 Verify the demo account is not registered successfully with invalid data")
    @Parameters({"regulation", "countryCode"})
    public void nonValidDataTest(String regulation,String countryCode){
        ScreenshotUtil.setCustomName("Non valid data - " + regulation + " regulation - Penny Stocks");
        Allure.step("Redirected to the page url");
        pennyStocks.registerDemoAccount("123","574","abcd134324",countryCode,"abc8798");
        pennyStocks.assertFirstStepErrorMessage(FirstStepField.ALL);
    }

    @Test(description = "TC 3.3. Verify that the account cannot be registered with already registered phone number")
    @Parameters({"regulation", "countryCode"})
    public void alreadyRegisteredPhoneNumberTest(String regulation,String countryCode){
        ScreenshotUtil.setCustomName("Already registered phone number - " + regulation + " regulation - Penny Stocks");
        Allure.step("Redirected to the page url");
        String phoneNumber = pennyStocks.selectPhoneNumber(regulation);
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),countryCode,phoneNumber);
        readyFortrade.assertURL(TestData.appUrl);
        openUrl(baseUrl);
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),countryCode,phoneNumber);
        pennyStocks.assertErrMsgForAlreadyRegisteredAccount();
    }

    @Test(description = "TC 3.4. Verify that the account cannot be registered with already registered email address and phone number")
    @Parameters({"regulation", "countryCode"})
    public void alreadyRegisteredEmailAndPhoneTest(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Already registered email address and phone number - " + regulation + " regulation - Penny Stocks");
        Allure.step("Redirected to the page url");
        String email = TestData.generateEmail();
        String phone = pennyStocks.selectPhoneNumber(regulation);
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,phone);
        readyFortrade.assertURL(TestData.appUrl);
        openUrl(baseUrl);
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,phone);
        pennyStocks.assertErrMsgForAlreadyRegisteredAccount();
    }

    @Test(description = "TC 3.5. Verify the demo account is not registered successfully with empty fields")
    @Parameters({"regulation"})
    public void emptyDataRegistrationTest(String regulation){
        ScreenshotUtil.setCustomName("Unsuccessfully account registration with empty data - " + regulation + " regulation - Penny Stocks");
        Allure.step("Redirected to the page url");
        pennyStocks.clickGetStartedBtn();
        pennyStocks.assertFirstStepErrorMessage(FirstStepField.FIRST_NAME);
    }

    @Test(description = "TC 4.1 - Verify that the invalid data for the First Name field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForFirstNameTest(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for First Name - " + regulation + " regulation - Penny Stocks");
        Allure.step("Verified error message and border color for First Name field");
        ElementActions.type(pennyStocks.firstName,"123", "first name");
        pennyStocks.assertFirstStepErrorMessage(FirstStepField.FIRST_NAME);
    }

    @Test(description = "TC 4.2 - Verify that the invalid data for the Last Name field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForLastNameTest(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Last Name - " + regulation + " regulation - Penny Stocks");
        Allure.step("Verified error message and border color for Last Name field");
        ElementActions.type(pennyStocks.lastName,"456", "last name");
        ElementActions.click(pennyStocks.email, "email");
        pennyStocks.assertFirstStepErrorMessage(FirstStepField.LAST_NAME);
    }

    @Test(description = "TC 4.3 - Verify that the invalid data for the Email field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForEmailTest(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Email - " + regulation + " regulation - Penny Stocks");
        Allure.step("Verified error message and border color for Email field");
        ElementActions.type(pennyStocks.email,"dsv124234/=", "email");
        pennyStocks.assertFirstStepErrorMessage(FirstStepField.EMAIL);
    }

    @Test(description = "TC 4.5 - Verify that the invalid data for Phone field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForPhoneTest(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Phone - " + regulation + " regulation - Penny Stocks");
        Allure.step("Verified error message and border color for Phone field");
        pennyStocks.insertPhoneNumber("0034334424558200");
        pennyStocks.assertFirstStepErrorMessage(FirstStepField.PHONE);
    }

    @Test(description = "TC 4.6 - Verify that the Last Name cannot be the same as First name.")
    @Parameters({"regulation"})
    public void sameFNameAndLNameTest(String regulation) {
        ScreenshotUtil.setCustomName("Your first name must be different from your last name - " + regulation + " regulation - Penny Stocks");
        Allure.step("Check for error message for the same first and last name.");
        pennyStocks.insertFirstName("Test");
        pennyStocks.insertLastName("Test");
        ElementActions.click(pennyStocks.email, "email address");
        pennyStocks.assertFirstStepErrorMessage(FirstStepField.SAME_FULL_NAME);
    }

    @Test(description = "TC 4.7 - Verify that the First Name cannot be the same as Last name.")
    @Parameters({"regulation"})
    public void sameLNameAndFNameTest(String regulation) {
        ScreenshotUtil.setCustomName("Your last name must be different from your first name - " + regulation + " regulation - Penny Stocks");
        Allure.step("Check for error message for the same last and first name.");
        pennyStocks.insertLastName("Test");
        pennyStocks.insertFirstName("Test");
        ElementActions.click(pennyStocks.email, "email address");
        pennyStocks.assertFirstStepErrorMessage(FirstStepField.SAME_FULL_NAME);
    }

    @Test(description = "TC 7.1 - Verify the Privacy Policy link works with left click")
    @Parameters({"regulation"})
    public void checkHeaderPrivacyPolicyLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Header privacy policy link - " + regulation + " regulation - Penny Stocks");
        Allure.step("Left click on the header privacy policy link.");
        pennyStocks.checkLinksWithLeftClick(pennyStocks.headerPrivacyPolicyLink, "header privacy policy link", pennyStocks.headerPrivacyPolicyUrl(regulation));
    }

    @Test(description = "TC 7.1.1 - Verify the Privacy Policy link works with right click")
    @Parameters({"regulation"})
    public void checkHeaderPrivacyPolicyLinkWithRightClickTest(String regulation){
        Allure.step("Right click on the header privacy policy link.");
        pennyStocks.checkLinksWithRightClick(pennyStocks.headerPrivacyPolicyLink, "header privacy policy link", pennyStocks.headerPrivacyPolicyUrl(regulation));
    }

    @Test(description = "TC 7.2 - Verify the Terms and Conditions link works with left click")
    @Parameters({"regulation"})
    public void checkHeaderTermsAndConditionsLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Header terms and conditions link - " + regulation + " regulation - Penny Stocks");
        Allure.step("Left click on the terms and conditions link.");
        pennyStocks.checkLinksWithLeftClick(pennyStocks.getTermsAndConditionsLink(regulation), "header terms and conditions link", pennyStocks.headerTermsAndConditionsUrl(regulation));
    }

    @Test(description = "TC 7.2.1 - Verify the Terms and Conditions link works with right click")
    @Parameters({"regulation"})
    public void checkHeaderTermsAndConditionsLinkWithRightClickTest(String regulation){
        Allure.step("Right click on the terms and conditions link.");
        pennyStocks.checkLinksWithRightClick(pennyStocks.getTermsAndConditionsLink(regulation), "header terms and conditions link", pennyStocks.headerTermsAndConditionsUrl(regulation));
    }

    @Test(description = "TC 7.3 - Verify the click here link works with left click")
    @Parameters({"regulation"})
    public void checkClickHereLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Click here link - " + regulation + " regulation - Penny Stocks");
        Allure.step("Left click on the click here link.");
        pennyStocks.checkLinksWithLeftClick(pennyStocks.clickHereLink, "click here link", TestData.clickHereUrl);
    }

    @Test(description = "TC 7.3.1 - Verify the click here link works with right click")
    public void checkClickHereLinkWithRightClickTest(){
        Allure.step("Right click on the click here link.");
        pennyStocks.checkLinksWithRightClick(pennyStocks.clickHereLink, "header terms and conditions link", TestData.clickHereUrl);
    }

    @Test(priority = -2,
            description = "TC 7.4 - Verify the Already have an account? link works with left click")
    @Parameters({"regulation"})
    public void checkAlreadyHaveAnAccountLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Already have an account link - " + regulation + " regulation - Penny Stocks");
        Allure.step("Left click on the already have an account link.");
        pennyStocks.checkLinksWithLeftClick(pennyStocks.alreadyHaveAnAccountLink, "already have an account link", TestData.alreadyHaveAnAccountUrl);
    }

    @Test(priority = -1,
            description = "TC 7.4.1 - Verify the Already have an account? link works with right click")
    public void checkAlreadyHaveAnAccountLinkWithRightClickTest(){
        Allure.step("Right click on the already have an account link.");
        pennyStocks.checkLinksWithRightClick(pennyStocks.alreadyHaveAnAccountLink, "already have an account link", TestData.alreadyHaveAnAccountUrl);
    }

    @Test(description = "TC 7.5 - Verify the click on Contact us link opens new mail window")
    @Parameters({"regulation"})
    public void checkContactUsLinkTest(String regulation){
        ScreenshotUtil.setCustomName("Contact Us link - " + regulation + " regulation - Penny Stocks");
        Allure.step("Left click on the contact us link.");
        pennyStocks.checkMailLinks(pennyStocks.contactUsLink, "href", TestData.contactUsUrl);
    }

    @Test(description = "9.1. Verify the border color in the CRM for regulation")
    @Parameters({"regulation", "countryCode"})
    public void checkAccountRegulationTest(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Account regulation in CRM - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
    }

    @Test(description = "9.2. Verify the account details is displayed correctly in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void checkAccountDetailsInCrmTest(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Account details in CRM - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
    }

    @Test(description = "9.3. Verify the tags are displayed correctly in CRM")
    @Parameters({"regulation", "countryCode"})
    public void checkTagsInCrmTest(String regulation,String countryCode){
        ScreenshotUtil.setCustomName("Marketing tags - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=age-annual-saving-knowledge-plang:all&tg=ivanA1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation),
                "25_34_age", "15000_50000_annual", "50000_100000_savings", "knowledge_of_trading_all_the_above","lang_EN");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkCrmTags();
    }
    @Test(description = "9.4. Verify that the Link ID field contains 'PC_windows' or 'PC_Other' value in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void checkLinkIDPCWindows(String regulation,String countryCode){
        ScreenshotUtil.setCustomName("Link ID tag contains the 'PC_windows' value - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl);
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkDeviceValue();
    }

    @Test(description = "TC 10.1. Verify the email is sent on the new account email")
    @Parameters({"regulation", "countryCode"})
    public void emailIsReceived(String regulation,String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Email is received successfully - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the the page url");
        pennyStocks.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        readyFortrade.clickUsePassBtn();
        openUrl(TestData.yopmailUrl);
        YopmailPage yopmailPage = new YopmailPage();
        yopmailPage.findEmail(email);
    }

    @Test(description = "TC 12.5. Verify that message 'We sent you the code again' is received")
    @Parameters({"regulation", "countryCode"})
    public void iDidntReceiveTheCodeTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("I didn't received the code link - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=sms-age-annual-saving-knowledge-plang:all");
        pennyStocks.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        pennyStocks.checkIDidntReceiveTheCodeLink();
    }

    @Test(description = "TC 12.6. Verify that wrong code cannot be submitted (negative test case)")
    @Parameters({"regulation", "countryCode"})
    public void wrongCodeCannotBeSubmittedTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Wrong code cannot be submitted - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=sms-age-annual-saving-knowledge-plang:all");
        pennyStocks.fillTheFormOnTheSecondStepWithWrongSmsCode(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation),
                "25_34_age", "15000_50000_annual", "50000_100000_savings", "knowledge_of_trading_all_the_above","lang_EN",
                "1", "1", "1", "1");
        pennyStocks.assertErrorMessageForWrongSmsCode();
    }

    @Test(description = "TC 12.9. Verify if user clicks pencil icon the same is returned to the 1st widget")
    @Parameters({"regulation", "countryCode"})
    public void editPencilButtonTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Edit pencil button redirects to the first step - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=sms-age-annual-saving-knowledge-plang:all");
        pennyStocks.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        pennyStocks.checkEditPencilButton();
    }

    @Test(description = "TC 13.1. Verify the user is redirected to the 2nd step - age verification window")
    @Parameters({"regulation", "countryCode"})
    public void redirectionToTheSecondStepAgeTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (age) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=age");
        pennyStocks.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        WaitUtil.waitForVisible(pennyStocks.age);
        Assert.assertTrue(pennyStocks.age.isDisplayed());
    }

    @Test(description = "TC 13.2. Verify the 2nd step - age verification window is successfully completed and submitted")
    @Parameters({"regulation", "countryCode"})
    public void registeredAgeParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (age) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=age");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "25_34_age", SecondStepField.AGE);
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 13.3. Verify the 2nd step - age verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForAgeParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (age) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=age");
        pennyStocks.wrongDataSecondStep(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "25_34_age", "-99", SecondStepField.AGE);
        pennyStocks.assertSecondStepErrorMessage(SecondStepField.AGE);
    }

    @Test(description = "TC 13.4. Verify the age value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void ageParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (age) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=age");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "25_34_age", SecondStepField.AGE);
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkLinkIdValue("25_34_age");
    }

    @Test(description = "TC 13.5. Vefiry that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationAgeTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (age) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=age");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "25_34_age", SecondStepField.AGE);
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkSMSVerification("--");
    }

    @Test(description = "TC 14.1. Verify the user is redirected to the 2nd step - annual verification window")
    @Parameters({"regulation", "countryCode"})
    public void redirectionToTheSecondStepAnnualTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (annual) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual");
        pennyStocks.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        WaitUtil.waitForVisible(pennyStocks.annual);
        Assert.assertTrue(pennyStocks.annual.isDisplayed());
    }

    @Test(description = "TC 14.2. Verify the 2nd step - annual income verification window is successfully completed and submitted")
    @Parameters({"regulation", "countryCode"})
    public void registeredAnnualParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (annual) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "15000_50000_annual", SecondStepField.ANNUAL);
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 14.3. Verify the 2nd step - annual income verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForAnnualParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (annual) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual");
        pennyStocks.wrongDataSecondStep(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "15000_50000_annual", "-99", SecondStepField.ANNUAL);
        pennyStocks.assertSecondStepErrorMessage(SecondStepField.ANNUAL);
    }

    @Test(description = "TC 14.4. Verify the annual value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void annualParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (annual) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "15000_50000_annual", SecondStepField.ANNUAL);
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkLinkIdValue("15000_50000_annual");
    }

    @Test(description = "TC 14.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationAnnualTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (annual) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "15000_50000_annual", SecondStepField.ANNUAL);
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkSMSVerification("--");
    }

    @Test(description = "TC 15.1. Verify the user is redirected to the 2nd step - saving and investments verification window")
    @Parameters({"regulation", "countryCode"})
    public void redirectionToTheSecondStepSavingTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (saving) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=saving");
        pennyStocks.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        WaitUtil.waitForVisible(pennyStocks.saving);
        Assert.assertTrue(pennyStocks.saving.isDisplayed());
    }

    @Test(description = "TC 15.2. Verify the 2nd step -  value of saving and investments verification window is successfully completed and submitted")
    @Parameters({"regulation", "countryCode"})
    public void registeredSavingParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (saving) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=saving");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "50000_100000_savings", SecondStepField.SAVING);
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 15.3. Verify the 2nd step - value of saving and investments verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForSavingParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (saving) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=saving");
        pennyStocks.wrongDataSecondStep(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "50000_100000_savings", "-99", SecondStepField.SAVING);
        pennyStocks.assertSecondStepErrorMessage(SecondStepField.SAVING);
    }

    @Test(description = "TC 15.4. Verify the saving value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void savingParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (saving) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=saving");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "50000_100000_savings", SecondStepField.SAVING);
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkLinkIdValue("50000_100000_savings");
    }

    @Test(description = "TC 15.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationSavingTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (saving) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=saving");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "50000_100000_savings", SecondStepField.SAVING);
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkSMSVerification("--");
    }


    @Test(description = "TC 16.1. Verify the user is redirected to the 2nd step - knowledge of trading verification window")
    @Parameters({"regulation", "countryCode"})
    public void redirectionToTheSecondStepKnowledgeTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (knowledge) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=knowledge");
        pennyStocks.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        WaitUtil.waitForVisible(pennyStocks.knowledge);
        Assert.assertTrue(pennyStocks.knowledge.isDisplayed());
    }

    @Test(description = "TC 16.2. Verify the 2nd step -  knowledge of trading verification window is successfully completed and submitted")
    @Parameters({"regulation", "countryCode"})
    public void registeredKnowledgeParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (knowledge) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=knowledge");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "knowledge_of_trading_all_the_above", SecondStepField.KNOWLEDGE);
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 16.3. Verify the 2nd step - knowledge of trading verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForKnowledgeParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (knowledge) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=knowledge");
        pennyStocks.wrongDataSecondStep(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "knowledge_of_trading_all_the_above", "-99", SecondStepField.KNOWLEDGE);
        pennyStocks.assertSecondStepErrorMessage(SecondStepField.KNOWLEDGE);
    }

    @Test(description = "TC 16.4. Verify the knowledge of trading value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void knowledgeParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (knowledge) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=knowledge");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "knowledge_of_trading_all_the_above", SecondStepField.KNOWLEDGE);
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkLinkIdValue("knowledge_of_trading_all_the_above");
    }

    @Test(description = "TC 16.5. Vefiry that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationKnowledgeTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (knowledge) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=knowledge");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "knowledge_of_trading_all_the_above", SecondStepField.KNOWLEDGE);
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkSMSVerification("--");
    }

    @Test(description = "TC 17.1. Verify the user is redirected to the 2nd step - language verification window")
    @Parameters({"regulation", "countryCode"})
    public void redirectionToTheSecondStepLanguageTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (language) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=plang:all");
        pennyStocks.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        WaitUtil.waitForVisible(pennyStocks.language);
        Assert.assertTrue(pennyStocks.language.isDisplayed());
    }

    @Test(description = "TC 17.2. Verify the 2nd step -  desired communication language verification window is successfully completed and submitted")
    @Parameters({"regulation", "countryCode"})
    public void registeredLanguageParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (language) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=plang:all");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "lang_EN", SecondStepField.LANGUAGE);
        readyFortrade.assertURL(TestData.appUrl);
    }

    @Test(description = "TC 17.3. Verify the 2nd step - desired communication language verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForLanguageParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (language) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=plang:all");
        pennyStocks.wrongDataSecondStep(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "lang_EN", "-99", SecondStepField.LANGUAGE);
        pennyStocks.assertSecondStepErrorMessage(SecondStepField.LANGUAGE);
    }

    @Test(description = "TC 17.4. Verify the desired communication language value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void languageParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (language) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=plang:all");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation), "lang_EN", SecondStepField.LANGUAGE);
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkLinkIdValue("lang_EN");

    }

    @Test(description = "TC 17.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationLanguageTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (language) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=plang:all");
        pennyStocks.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode, pennyStocks.selectPhoneNumber(regulation), "lang_EN", SecondStepField.LANGUAGE);
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkSMSVerification("--");
    }

    @Test(priority = 1000,
            description = "TC 18.1. Verify the account is registered successfully with NON-valid tag in the URL")
    @Parameters({"regulation", "countryCode"})
    public void nonValidParameterInTheUrlTest(String regulation,String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Demo account is successfully registered with wrong parameters in the URL - " + regulation + " regulation - Penny Stocks");
        openUrl(baseUrl + "&fts=testq-testa");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        readyFortrade.clickUsePassBtn();
        readyFortrade.clickMenuBtn();
        readyFortrade.checkRegulation(regulation);
    }

    @Test(description = "TC 18.2. Verify the 2nd step cannot be submitted if all parameter values are not completed")
    @Parameters({"regulation", "countryCode"})
    public void noDataOnTheSecondStepTest(String regulation,String countryCode) {
        ScreenshotUtil.setCustomName("Error messages (all parameters) - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=age-annual-saving-knowledge-plang:all");
        pennyStocks.fillTheFormOnTheSecondStepWithWrongData(TestData.firstName, TestData.lastName, email, countryCode, pennyStocks.selectPhoneNumber(regulation), "25_34_age", "15000_50000_annual", "50000_100000_savings",
                "knowledge_of_trading_all_the_above", "lang_EN");
        pennyStocks.assertSecondStepErrorMessage(SecondStepField.ALL);
    }

    @Test(description = "TC 19.1. Verify the cutom tag field in the CRM contains Dummy value ")
    @Parameters({"regulation","countryCode"})
    public void dummyLeadRegistration(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual-saving-knowledge-age-plang:all&ftsquery=age-equals(1,3)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                pennyStocks.selectPhoneNumber(regulation), "25_34_age", "50000_100000_annual", "50000_100000_savings",
                "knowledge_of_trading_all_the_above","lang_EN");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 19.2. Verify the cutom tag field in the CRM contains Dummy value ")
    @Parameters({"regulation","countryCode"})
    public void dummy_Lead_Registration(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual-saving-knowledge-age-plang:all&ftsquery=age-equals(1_3)-or-[saving-equals(1_2_3)-and-knowledge-notequals(5)]");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                pennyStocks.selectPhoneNumber(regulation), "25_34_age", "15000_50000_annual", "50000_100000_savings",
                "knowledge_of_trading_all_the_above","lang_EN");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 19.3. Verify the custom tag field in the CRM contains Invalid value")
    @Parameters({"regulation","countryCode"})
    public void invalidLeadRegistration(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom tag - Invalid - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual-saving-knowledge-age-plang:all&ftsquery=age(1)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                pennyStocks.selectPhoneNumber(regulation), "45_54_age", "15000_50000_annual", "100000_250000_savings",
                "knowledge_of_trading_none","lang_EN");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 19.4. Verify the custom tag field in the CRM contains Invalid value")
    @Parameters({"regulation","countryCode"})
    public void invalid_Lead_Registration(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom tag - Invalid - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual-saving-knowledge-age-plang:all&ftsquery=age(1)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                pennyStocks.selectPhoneNumber(regulation), "45_54_age", "15000_50000_annual", "100000_250000_savings",
                "knowledge_of_trading_none","lang_EN");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 19.5. Verify the custom tag field in the CRM is empty")
    @Parameters({"regulation","countryCode"})
    public void emptyLeadRegistration(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&fts=annual-saving-knowledge-age-plang:all&ftsquery=age-equals(1,3)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                pennyStocks.selectPhoneNumber(regulation), "45_54_age", "15000_50000_annual", "100000_250000_savings",
                "knowledge_of_trading_all_the_above","lang_EN");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.6. Verify the custom tag field in the CRM is empty")
    @Parameters({"regulation","countryCode"})
    public void empty_Lead_Registration(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&fts=annual-saving-knowledge-age-plang:all&ftsquery=age-equals(1_3)-or-[saving-equals(1_2_3)-and-knowledge-notequals(5)]");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                pennyStocks.selectPhoneNumber(regulation), "45_54_age", "15000_50000_annual", "100000_250000_savings",
                "knowledge_of_trading_all_the_above","lang_EN");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email, TestData.fullName, regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.7. Verify the custom tag field in the CRM is Dummy if ftsquery device parameter is same as device " +
            "and OS used for demo account registration")
    @Parameters({"regulation","countryCode"})
    public void deviceIsSameAsParameter(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy - device - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            openUrl(baseUrl + "&ftsquery=device-equals(1)");
        }else{
            openUrl(baseUrl + "&ftsquery=device-equals(2)");
        }
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }
    @Test(description = "TC 19.8. Verify the custom tag field in the CRM is invalid if syntax is not valid")
    @Parameters({"regulation","countryCode"})
    public void nonValidParameterSyntax(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Invalid - device - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            openUrl(baseUrl + "&ftsquery=device(1)");
        }else{
            openUrl(baseUrl + "&ftsquery=device(2)");
        }
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }
    @Test(description = "TC 19.9. Verify the custom tag field in the CRM is empty if ftsquery device parameter is not " +
            "same as device and OS used for demo account registration")
    @Parameters({"regulation","countryCode"})
    public void deviceIsNotSameAsParameter(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty - device - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&ftsquery=device-equals(4)");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.10. Verify the custom tag field in the CRM is invalid if ftsquery device parameter " +
            "contains device and OS non valid index value")
    @Parameters({"regulation","countryCode"})
    public void deviceParameterContainsNonValidValue(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - non valid device value - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&ftsquery=device-equals(0)");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 20.3. Verify that the Custom Tag in the CRM is empty")
    @Parameters({"regulation","countryCode"})
    public void checkingTheCustomTag(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 20-3-Custom Tag is empty - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&fts=age-annual-saving-knowledge-plang:all");
        pennyStocks.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                pennyStocks.selectPhoneNumber(regulation), "45_54_age", "15000_50000_annual", "100000_250000_savings",
                "knowledge_of_trading_all_the_above","lang_EN");
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 22.1. Verify that the Language field in the CRM contains the expected value (the language that you enter in the URL)")
    @Parameters({"regulation", "countryCode"})
    public void checkLanguageFieldContainsExpectedValue(String regulation,String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Language field in the CRM contains expected (FR) value - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        String expectedLanguage = pennyStocks.selectExpectedLanguage(regulation);
        openUrl(baseUrl + "&userLang=" + expectedLanguage);
        pennyStocks.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        readyFortrade.assertDisplayedLanguage(expectedLanguage);
        crmPage.checkCrmData(email, TestData.fullName, regulation);
        crmPage.checkLanguageField(email, expectedLanguage.toLowerCase());
    }

    @Test(description = "TC 22.2. Verify that the Language field in the CRM contains the default value (the language of the base page URL) when we enter the wrong language in the userLang parameter")
    @Parameters({"regulation", "countryCode"})
    public void checkLanguageFieldContainsDefaultValue(String regulation,String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Language field in the CRM contains default (EN) value - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        String defaultLanguage = UrlProvider.getLanguage().toUpperCase();
        openUrl(baseUrl + "&userLang=FRA");
        pennyStocks.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        readyFortrade.assertDisplayedLanguage(defaultLanguage);
        crmPage.checkCrmData(email, TestData.fullName, regulation);
        crmPage.checkLanguageField(email, defaultLanguage.toLowerCase());
    }

    @Test(description = "TC 23.1. Verify that the Custom Tag field in the CRM contains the DummyP value")
    @Parameters({"regulation","countryCode"})
    public void dummypParameter(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-1-Custom tag-DummyP value - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            openUrl(baseUrl + "&ftsquery=device-equals(1)&dummyP=1");
        }else{
            openUrl(baseUrl + "&ftsquery=device-equals(2)&dummyP=1");
        }
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("DummyP");
    }

    @Test(description = "TC 23.2. Verify that the Custom Tag field in the CRM contains the Dummy value")
    @Parameters({"regulation","countryCode"})
    public void dummyParameter(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-2-Custom tag - Dummy value - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            openUrl(baseUrl + "&ftsquery=device-equals(1)&dummyP=0");
        }else{
            openUrl(baseUrl + "&ftsquery=device-equals(2)&dummyp=0");
        }
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 23.3. Verify that the dummyP parameter is ignored when it's not correctly typed in the URL")
    @Parameters({"regulation","countryCode"})
    public void parameterDummy(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-3-Custom tag - Dummy value - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            openUrl(baseUrl + "&ftsquery=device-equals(1)&dummyp=1");
        } else{
            openUrl(baseUrl + "&ftsquery=device-equals(2)&dummyp=1");
        }
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.1. Verify that the custom tag field in the CRM contains Dummy parameter ")
    @Parameters({"regulation","countryCode"})
    public void customTagContainsDummy(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-1-Custom tag field - dummy - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&Dummy=true");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.2. Verify that the custom tag field in the CRM contains Dummy parameter ")
    @Parameters({"regulation","countryCode"})
    public void customTagContainsDummyValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-2-Custom tag field - dummy - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&Dummy=1");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.3. Verify that the custom tag field in the CRM contains '' (empty) value")
    @Parameters({"regulation","countryCode"})
    public void customTagContainsEmpty(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-3-Custom tag field - empty - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&Dummy=false");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 24.4. Verify that the custom tag field in the CRM contains '' (empty) value")
    @Parameters({"regulation","countryCode"})
    public void customTagContainsEmptyValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-4-Custom tag field - empty - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&Dummy=0");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 25.1. Verify that the Link Id field in the CRM contains '{number}_IPL' value")
    @Parameters({"regulation","countryCode"})
    public void checkNumberIplValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 25-1-Link Id tag field - {number}_IPL - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&tag1=1452789330");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue(TestData.resultOfAnIPLValue);
    }

    @Test(description = "TC 25.2. Verify that the Link Id field in the CRM contains 'missingTag1_IPL' value")
    @Parameters({"regulation","countryCode"})
    public void checkMissingTag1IplValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 25-2-Link Id tag field - missingTag1_IPL - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&tag1=123abc");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("missingTag1");
    }

    @Test(description = "TC 25.3. Verify that the Link Id field in the CRM contains 'missingCID_IPL' value")
    @Parameters({"regulation","countryCode"})
    public void checkMissingCidIplValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 25-3-Link Id tag field - missingCID_IPL - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&tag1=123456789");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("missingCID");
    }

    @Test(description = "TC 25.4. Verify that the Link Id field in the CRM contains 'divByZero_IPL' value")
    @Parameters({"regulation","countryCode"})
    public void checkDivByZeroIplValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 25-4-Link Id tag field - divByZero_IPL - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&tag1=930863512");
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("divByZero");
    }

    @Test(description = "TC 26.2. Verify the Link ID field contains None-usd value")
    @Parameters({"regulation","countryCode"})
    public void checkNoneUsdValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 26-2-Link Id tag field - None-usd - " + regulation + " regulation - Penny Stocks");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl);
        pennyStocks.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,pennyStocks.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrl);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("None_usd");
    }
}