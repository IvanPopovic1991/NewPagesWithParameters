package tests.TestsKapitalRS;

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

public class ProDarkSr extends BaseTest {

    private KapitalRSPage kapitalRSPage;
    private ReadyFortrade readyFortrade;
    private CrmPage crmPage;
    private String baseUrl;

    @Parameters({"tag"})
    @BeforeMethod
    public void initPages(String tag){
        kapitalRSPage = new KapitalRSPage();
        readyFortrade = new ReadyFortrade();
        crmPage = new CrmPage();
        baseUrl = ConfigReader.getBaseUrl();
        baseUrl += tag;
        openUrl(baseUrl);
    }

    @Test(description = "TC 2.1. Verify the demo account is registered successfully with valid data")
    @Parameters({"regulation","countryCode"})
    public void demoAccountRegistrationTest(String regulation,String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Demo account is successfully registered - " + regulation + " regulation - KapitalRS");
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        readyFortrade.clickUsePassBtn();
        readyFortrade.clickMenuBtn();
        readyFortrade.checkRegulation(regulation);
    }

    @Test(description = "TC 3.1. Verify that the account cannot be registered with already registered email address")
    @Parameters({"regulation","countryCode"})
    public void alreadyRegisteredEmailAddressTest(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Already registered email address - " + regulation + " regulation - KapitalRS");
        Allure.step("Redirected to the page url");
        String email = TestData.generateEmail();
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        openUrl(baseUrl);
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        kapitalRSPage.assertErrMsgForAlreadyRegisteredAccount();
    }

    @Test(description="TC 3.2 Verify the demo account is not registered successfully with invalid data")
    @Parameters({"regulation", "countryCode"})
    public void nonValidDataTest(String regulation,String countryCode){
        ScreenshotUtil.setCustomName("Non valid data - " + regulation + " regulation - KapitalRS");
        Allure.step("Redirected to the page url");
        kapitalRSPage.registerDemoAccount("123","574","abcd134324",countryCode,"abc8798");
        kapitalRSPage.assertFirstStepErrorMessage(FirstStepField.ALL);
    }

    @Test(description = "TC 3.3. Verify that the account cannot be registered with already registered phone number")
    @Parameters({"regulation", "countryCode"})
    public void alreadyRegisteredPhoneNumberTest(String regulation,String countryCode){
        ScreenshotUtil.setCustomName("Already registered phone number - " + regulation + " regulation - KapitalRS");
        Allure.step("Redirected to the page url");
        String phoneNumber = kapitalRSPage.selectPhoneNumber(regulation);
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),countryCode,phoneNumber);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        openUrl(baseUrl);
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),countryCode,phoneNumber);
        kapitalRSPage.assertErrMsgForAlreadyRegisteredAccount();
    }

    @Test(description = "TC 3.4. Verify that the account cannot be registered with already registered email address and phone number")
    @Parameters({"regulation", "countryCode"})
    public void alreadyRegisteredEmailAndPhoneTest(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Already registered email address and phone number - " + regulation + " regulation - KapitalRS");
        Allure.step("Redirected to the page url");
        String email = TestData.generateEmail();
        String phone = kapitalRSPage.selectPhoneNumber(regulation);
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,phone);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        openUrl(baseUrl);
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,phone);
        kapitalRSPage.assertErrMsgForAlreadyRegisteredAccount();
    }

    @Test(description = "TC 3.5. Verify the demo account is not registered successfully with empty fields")
    @Parameters({"regulation"})
    public void emptyDataRegistrationTest(String regulation){
        ScreenshotUtil.setCustomName("Unsuccessfully account registration with empty data - " + regulation + " regulation - KapitalRS");
        Allure.step("Redirected to the page url");
        kapitalRSPage.clickGetStartedBtn();
        kapitalRSPage.assertFirstStepErrorMessage(FirstStepField.FIRST_NAME);
    }

    @Test(description = "TC 4.1 - Verify that the invalid data for the First Name field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForFirstNameTest(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for First Name - " + regulation + " regulation - KapitalRS");
        Allure.step("Verified error message and border color for First Name field");
        ElementActions.type(kapitalRSPage.firstName,"123", "first name");
        kapitalRSPage.assertFirstStepErrorMessage(FirstStepField.FIRST_NAME);
    }

    @Test(description = "TC 4.2 - Verify that the invalid data for the Last Name field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForLastNameTest(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Last Name - " + regulation + " regulation - KapitalRS");
        Allure.step("Verified error message and border color for Last Name field");
        ElementActions.type(kapitalRSPage.lastName,"456", "last name");
        ElementActions.click(kapitalRSPage.email, "email");
        kapitalRSPage.assertFirstStepErrorMessage(FirstStepField.LAST_NAME);
    }

    @Test(description = "TC 4.3 - Verify that the invalid data for the Email field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForEmailTest(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Email - " + regulation + " regulation - KapitalRS");
        Allure.step("Verified error message and border color for Email field");
        ElementActions.type(kapitalRSPage.email,"dsv124234/=", "email");
        kapitalRSPage.assertFirstStepErrorMessage(FirstStepField.EMAIL);
    }

    @Test(description = "TC 4.5 - Verify that the invalid data for Phone field will show valid error message with red border")
    @Parameters({"regulation"})
    public void verifyErrorMessageForPhoneTest(String regulation){
        ScreenshotUtil.setCustomName("Error message and border color are displayed for Phone - " + regulation + " regulation - KapitalRS");
        Allure.step("Verified error message and border color for Phone field");
        kapitalRSPage.insertPhoneNumber("0034334424558200");
        kapitalRSPage.assertFirstStepErrorMessage(FirstStepField.PHONE);
    }

    @Test(description = "TC 4.6 - Verify that the Last Name cannot be the same as First name.")
    @Parameters({"regulation"})
    public void sameFNameAndLNameTest(String regulation) {
        ScreenshotUtil.setCustomName("Your first name must be different from your last name - " + regulation + " regulation - KapitalRS");
        Allure.step("Check for error message for the same first and last name.");
        kapitalRSPage.insertFirstName("Test");
        kapitalRSPage.insertLastName("Test");
        ElementActions.click(kapitalRSPage.email, "email address");
        kapitalRSPage.assertFirstStepErrorMessage(FirstStepField.SAME_FULL_NAME);
    }

    @Test(description = "TC 4.7 - Verify that the First Name cannot be the same as Last name.")
    @Parameters({"regulation"})
    public void sameLNameAndFNameTest(String regulation) {
        ScreenshotUtil.setCustomName("Your last name must be different from your first name - " + regulation + " regulation - KapitalRS");
        Allure.step("Check for error message for the same last and first name.");
        kapitalRSPage.insertLastName("Test");
        kapitalRSPage.insertFirstName("Test");
        ElementActions.click(kapitalRSPage.email, "email address");
        kapitalRSPage.assertFirstStepErrorMessage(FirstStepField.SAME_FULL_NAME);
    }

    @Test(description = "TC 7.1 - Verify the Privacy Policy link works with left click")
    @Parameters({"regulation"})
    public void checkHeaderPrivacyPolicyLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Header privacy policy link - " + regulation + " regulation - KapitalRS");
        Allure.step("Left click on the header privacy policy link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.headerPrivacyPolicyLink, "header privacy policy link", TestData.headerPrivacyPolicyUrlKapitalRS);
    }

    @Test(description = "TC 7.1.1 - Verify the Privacy Policy link works with right click")
    @Parameters({"regulation"})
    public void checkHeaderPrivacyPolicyLinkWithRightClickTest(String regulation){
        Allure.step("Right click on the header privacy policy link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.headerPrivacyPolicyLink, "header privacy policy link", TestData.headerPrivacyPolicyUrlKapitalRS);
    }

    @Test(description = "TC 7.2 - Verify the Terms and Conditions link works with left click")
    @Parameters({"regulation"})
    public void checkHeaderTermsAndConditionsLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Header terms and conditions link - " + regulation + " regulation - KapitalRS");
        Allure.step("Left click on the terms and conditions link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.headerTermsAndConditionsLink, "header terms and conditions link", TestData.headerTermsAndConditionsUrlKapitalRS);
    }

    @Test(description = "TC 7.2.1 - Verify the Terms and Conditions link works with right click")
    @Parameters({"regulation"})
    public void checkHeaderTermsAndConditionsLinkWithRightClickTest(String regulation){
        Allure.step("Right click on the terms and conditions link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.headerTermsAndConditionsLink, "header terms and conditions link", TestData.headerTermsAndConditionsUrlKapitalRS);
    }

    @Test(description = "TC 7.3 - Verify the click here link works with left click")
    @Parameters({"regulation"})
    public void checkClickHereLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Click here link - " + regulation + " regulation - KapitalRS");
        Allure.step("Left click on the click here link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.clickHereLink, "click here link", TestData.clickHereUrlKapitalRS);
    }

    @Test(description = "TC 7.3.1 - Verify the click here link works with right click")
    public void checkClickHereLinkWithRightClickTest(){
        Allure.step("Right click on the click here link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.clickHereLink, "header terms and conditions link", TestData.clickHereUrlKapitalRS);
    }

    @Test(priority = -2,
            description = "TC 7.4 - Verify the Already have an account? link works with left click")
    @Parameters({"regulation"})
    public void checkAlreadyHaveAnAccountLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Already have an account link - " + regulation + " regulation - KapitalRS");
        Allure.step("Left click on the already have an account link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.alreadyHaveAnAccountLink, "already have an account link", TestData.alreadyHaveAnAccountUrlKapitalRS);
    }

    @Test(priority = -1,
            description = "TC 7.4.1 - Verify the Already have an account? link works with right click")
    public void checkAlreadyHaveAnAccountLinkWithRightClickTest(){
        Allure.step("Right click on the already have an account link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.alreadyHaveAnAccountLink, "already have an account link", TestData.alreadyHaveAnAccountUrlKapitalRS);
    }

    @Test(description = "TC 7.5 - Verify the click on Contact us link opens new mail window")
    @Parameters({"regulation"})
    public void checkContactUsLinkTest(String regulation){
        ScreenshotUtil.setCustomName("Contact Us link - " + regulation + " regulation - KapitalRS");
        Allure.step("Left click on the contact us link.");
        kapitalRSPage.checkMailLinks(kapitalRSPage.contactUsLink, "href", TestData.contactUsUrlKapitalRS);
    }

    @Test(description = "TC 7.7 - Verify the Risk warning link works with left click")
    @Parameters({"regulation"})
    public void checkRiskWarningLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Risk warning link - " + regulation + " regulation - KapitalRS");
        Allure.step("Left click on the risk warning link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.footerRiskWarningLink, "risk warning link", TestData.footerRiskWarningKapitalRS);
    }

    @Test(description = "TC 7.7.1 - Verify the Risk warning link works with right click")
    @Parameters({"regulation"})
    public void checkRiskWarningLinkWithRightClickTest(String regulation){
        Allure.step("Right click on the risk warning link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.footerRiskWarningLink, "risk warning link", TestData.footerRiskWarningKapitalRS);
    }

    @Test(description = "TC 7.8 - Verify the Privacy policy link (in footer) works with left click")
    @Parameters({"regulation"})
    public void checkFooterPrivacyPolicyLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Footer privacy policy link - " + regulation + " regulation - KapitalRS");
        Allure.step("Left click on the footer privacy policy link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.footerPrivacyPolicyLink, "footer privacy policy link", TestData.footerPrivacyPolicyUrlKapitalRS);
    }

    @Test(description = "TC 7.8.1 - Verify the Privacy policy (in footer) link works with right click")
    @Parameters({"regulation"})
    public void checkFooterPrivacyPolicyLinkWithRightClickTest(String regulation){
        Allure.step("Right click on the footer privacy policy link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.footerPrivacyPolicyLink, "footer privacy policy link", TestData.footerPrivacyPolicyUrlKapitalRS);
    }

    @Test(description = "TC 8.1 - Verify the FRN: 609970 (FCA) link works with left click")
    @Parameters({"regulation"})
    @RunForRegulations("fca")
    public void checkFcaLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Fca link - " + regulation + " regulation - KapitalRS");
        Allure.step("Left click on the FCA link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.footerFCALink, "fca link", TestData.fcaUrl);
    }

    @Test(description = "TC 8.1.1 - Verify the FRN: 609970 (FCA) link works with right click")
    @RunForRegulations("fca")
    public void checkFcaLinkWithRightClickTest(){
        Allure.step("Right click on the FCA link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.footerFCALink, "fca link", TestData.fcaUrl);
    }

    @Test(description = "TC 8.3 - Verify the ABN: 33 614 683 831 | AFSL: 493520 (ASIC) link works with left click")
    @Parameters({"regulation"})
    @RunForRegulations("Asic")
    public void checkAsicLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Asic link - " + regulation + " regulation - KapitalRS");
        Allure.step("Left click on the ASIC link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.footerASICLink, "asic link", TestData.asicUrl);
    }

    @Test(description = "TC 8.3.1 - Verify the ABN: 33 614 683 831 | AFSL: 493520 (ASIC) link works with right click")
    @RunForRegulations("Asic")
    public void checkAsicLinkWithRightClickTest(){
        Allure.step("Right click on the ASIC link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.footerASICLink, "asic link", TestData.asicUrl);
    }

    @Test(description = "TC 8.5 - Verify the GB21026472 (FSC) link works with left click")
    @Parameters({"regulation"})
    @RunForRegulations("fsc")
    public void checkFscLinkWithLeftClickTest(String regulation){
        ScreenshotUtil.setCustomName("Fsc link - " + regulation + " regulation - KapitalRS");
        Allure.step("Left click on the FSC link.");
        kapitalRSPage.checkLinksWithLeftClick(kapitalRSPage.footerFSCLink, "fsc link", TestData.fscUrl);
    }

    @Test(description = "TC 8.5.1 - Verify the GB21026472 (FSC) link works with right click")
    @RunForRegulations("fsc")
    public void checkFscLinkWithRightClickTest(){
        Allure.step("Right click on the FSC link.");
        kapitalRSPage.checkLinksWithRightClick(kapitalRSPage.footerFSCLink, "fsc link", TestData.fscUrl);
    }

    @Test(description = "9.1. Verify the border color in the CRM for regulation")
    @Parameters({"regulation", "countryCode"})
    public void checkAccountRegulationTest(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Account regulation in CRM - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
    }

    @Test(description = "9.2. Verify the account details is displayed correctly in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void checkAccountDetailsInCrmTest(String regulation, String countryCode){
        ScreenshotUtil.setCustomName("Account details in CRM - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
    }

    @Test(description = "9.3. Verify the tags are displayed correctly in CRM")
    @Parameters({"regulation", "countryCode"})
    public void checkTagsInCrmTest(String regulation,String countryCode){
        ScreenshotUtil.setCustomName("Marketing tags - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=age-annual-saving-knowledge-plang:all&tg=ivanA1434&tag1=ivanB@1434&tag2=ivanL1434&tag3=ivanM1434&gid=ivanC@1434&G_GEO=ivanD1434&G_GEOint=ivanE1434&G_Device=ivanF1434&G_DeviceModel=ivanG1434&G_AdPos=ivanH1434&g_Track=ivanI1434&Track=ivanj1434&gclid=ivanK1434");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation),
                "25_34_age", "15000_50000_annual", "50000_100000_savings", "knowledge_of_trading_all_the_above","lang_EN");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkCrmTags();
    }
    @Test(description = "9.4. Verify that the Link ID field contains 'PC_windows' or 'PC_Other' value in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void checkLinkIDPCWindows(String regulation,String countryCode){
        ScreenshotUtil.setCustomName("Link ID tag contains the 'PC_windows' value - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl);
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkDeviceValue();
    }

    @Test(description = "TC 10.1. Verify the email is sent on the new account email")
    @Parameters({"regulation", "countryCode"})
    public void emailIsReceived(String regulation,String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Email is received successfully - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the the page url");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        readyFortrade.clickUsePassBtn();
        openUrl(TestData.yopmailUrl);
        YopmailPage yopmailPage = new YopmailPage();
        yopmailPage.findEmailKapitalRS(email);
    }

    @Test(description = "TC 12.5. Verify that message 'We sent you the code again' is received")
    @Parameters({"regulation", "countryCode"})
    public void iDidntReceiveTheCodeTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("I didn't received the code link - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=sms-age-annual-saving-knowledge-plang:all");
        kapitalRSPage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        kapitalRSPage.checkIDidntReceiveTheCodeLink();
    }

    @Test(description = "TC 12.6. Verify that wrong code cannot be submitted (negative test case)")
    @Parameters({"regulation", "countryCode"})
    public void wrongCodeCannotBeSubmittedTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Wrong code cannot be submitted - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=sms-age-annual-saving-knowledge-plang:all");
        kapitalRSPage.fillTheFormOnTheSecondStepWithWrongSmsCode(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation),
                "25_34_age", "15000_50000_annual", "50000_100000_savings", "knowledge_of_trading_all_the_above","lang_EN",
                "1", "1", "1", "1");
        kapitalRSPage.assertErrorMessageForWrongSmsCode();
    }

    @Test(description = "TC 12.9. Verify if user clicks pencil icon the same is returned to the 1st widget")
    @Parameters({"regulation", "countryCode"})
    public void editPencilButtonTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Edit pencil button redirects to the first step - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=sms-age-annual-saving-knowledge-plang:all");
        kapitalRSPage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        kapitalRSPage.checkEditPencilButton();
    }

    @Test(description = "TC 13.1. Verify the user is redirected to the 2nd step - age verification window")
    @Parameters({"regulation", "countryCode"})
    public void redirectionToTheSecondStepAgeTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (age) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=age");
        kapitalRSPage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        WaitUtil.waitForVisible(kapitalRSPage.age);
        Assert.assertTrue(kapitalRSPage.age.isDisplayed());
    }

    @Test(description = "TC 13.2. Verify the 2nd step - age verification window is successfully completed and submitted")
    @Parameters({"regulation", "countryCode"})
    public void registeredAgeParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (age) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=age");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "25_34_age", SecondStepField.AGE);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
    }

    @Test(description = "TC 13.3. Verify the 2nd step - age verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForAgeParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (age) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=age");
        kapitalRSPage.wrongDataSecondStep(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "25_34_age", "-99", SecondStepField.AGE);
        kapitalRSPage.assertSecondStepErrorMessage(SecondStepField.AGE);
    }

    @Test(description = "TC 13.4. Verify the age value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void ageParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (age) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=age");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "25_34_age", SecondStepField.AGE);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkLinkIdValue("25_34_age");
    }

    @Test(description = "TC 13.5. Vefiry that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationAgeTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (age) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=age");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "25_34_age", SecondStepField.AGE);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkSMSVerification("--");
    }

    @Test(description = "TC 14.1. Verify the user is redirected to the 2nd step - annual verification window")
    @Parameters({"regulation", "countryCode"})
    public void redirectionToTheSecondStepAnnualTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (annual) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual");
        kapitalRSPage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        WaitUtil.waitForVisible(kapitalRSPage.annual);
        Assert.assertTrue(kapitalRSPage.annual.isDisplayed());
    }

    @Test(description = "TC 14.2. Verify the 2nd step - annual income verification window is successfully completed and submitted")
    @Parameters({"regulation", "countryCode"})
    public void registeredAnnualParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (annual) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "15000_50000_annual", SecondStepField.ANNUAL);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
    }

    @Test(description = "TC 14.3. Verify the 2nd step - annual income verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForAnnualParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (annual) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual");
        kapitalRSPage.wrongDataSecondStep(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "15000_50000_annual", "-99", SecondStepField.ANNUAL);
        kapitalRSPage.assertSecondStepErrorMessage(SecondStepField.ANNUAL);
    }

    @Test(description = "TC 14.4. Verify the annual value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void annualParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (annual) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "15000_50000_annual", SecondStepField.ANNUAL);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkLinkIdValue("15000_50000_annual");
    }

    @Test(description = "TC 14.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationAnnualTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (annual) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "15000_50000_annual", SecondStepField.ANNUAL);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkSMSVerification("--");
    }

    @Test(description = "TC 15.1. Verify the user is redirected to the 2nd step - saving and investments verification window")
    @Parameters({"regulation", "countryCode"})
    public void redirectionToTheSecondStepSavingTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (saving) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=saving");
        kapitalRSPage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        WaitUtil.waitForVisible(kapitalRSPage.saving);
        Assert.assertTrue(kapitalRSPage.saving.isDisplayed());
    }

    @Test(description = "TC 15.2. Verify the 2nd step -  value of saving and investments verification window is successfully completed and submitted")
    @Parameters({"regulation", "countryCode"})
    public void registeredSavingParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (saving) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=saving");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "50000_100000_savings", SecondStepField.SAVING);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
    }

    @Test(description = "TC 15.3. Verify the 2nd step - value of saving and investments verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForSavingParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (saving) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=saving");
        kapitalRSPage.wrongDataSecondStep(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "50000_100000_savings", "-99", SecondStepField.SAVING);
        kapitalRSPage.assertSecondStepErrorMessage(SecondStepField.SAVING);
    }

    @Test(description = "TC 15.4. Verify the saving value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void savingParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (saving) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=saving");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "50000_100000_savings", SecondStepField.SAVING);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkLinkIdValue("50000_100000_savings");
    }

    @Test(description = "TC 15.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationSavingTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (saving) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=saving");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "50000_100000_savings", SecondStepField.SAVING);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkSMSVerification("--");
    }


    @Test(description = "TC 16.1. Verify the user is redirected to the 2nd step - knowledge of trading verification window")
    @Parameters({"regulation", "countryCode"})
    public void redirectionToTheSecondStepKnowledgeTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (knowledge) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=knowledge");
        kapitalRSPage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        WaitUtil.waitForVisible(kapitalRSPage.knowledge);
        Assert.assertTrue(kapitalRSPage.knowledge.isDisplayed());
    }

    @Test(description = "TC 16.2. Verify the 2nd step -  knowledge of trading verification window is successfully completed and submitted")
    @Parameters({"regulation", "countryCode"})
    public void registeredKnowledgeParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (knowledge) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=knowledge");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "knowledge_of_trading_all_the_above", SecondStepField.KNOWLEDGE);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
    }

    @Test(description = "TC 16.3. Verify the 2nd step - knowledge of trading verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForKnowledgeParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (knowledge) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=knowledge");
        kapitalRSPage.wrongDataSecondStep(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "knowledge_of_trading_all_the_above", "-99", SecondStepField.KNOWLEDGE);
        kapitalRSPage.assertSecondStepErrorMessage(SecondStepField.KNOWLEDGE);
    }

    @Test(description = "TC 16.4. Verify the knowledge of trading value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void knowledgeParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (knowledge) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=knowledge");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "knowledge_of_trading_all_the_above", SecondStepField.KNOWLEDGE);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkLinkIdValue("knowledge_of_trading_all_the_above");
    }

    @Test(description = "TC 16.5. Vefiry that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationKnowledgeTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (knowledge) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=knowledge");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "knowledge_of_trading_all_the_above", SecondStepField.KNOWLEDGE);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkSMSVerification("--");
    }

    @Test(description = "TC 17.1. Verify the user is redirected to the 2nd step - language verification window")
    @Parameters({"regulation", "countryCode"})
    public void redirectionToTheSecondStepLanguageTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is redirected to the second step (language) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=plang:all");
        kapitalRSPage.goToSecondStep(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        WaitUtil.waitForVisible(kapitalRSPage.language);
        Assert.assertTrue(kapitalRSPage.language.isDisplayed());
    }

    @Test(description = "TC 17.2. Verify the 2nd step -  desired communication language verification window is successfully completed and submitted")
    @Parameters({"regulation", "countryCode"})
    public void registeredLanguageParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("User is registered (language) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=plang:all");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "lang_EN", SecondStepField.LANGUAGE);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
    }

    @Test(description = "TC 17.3. Verify the 2nd step - desired communication language verification window cannot be submitted if it's not completed")
    @Parameters({"regulation", "countryCode"})
    public void errorMessageForLanguageParameterTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Error message (language) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=plang:all");
        kapitalRSPage.wrongDataSecondStep(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "lang_EN", "-99", SecondStepField.LANGUAGE);
        kapitalRSPage.assertSecondStepErrorMessage(SecondStepField.LANGUAGE);
    }

    @Test(description = "TC 17.4. Verify the desired communication language value is displayed correctly in the Link ID field in the CRM")
    @Parameters({"regulation", "countryCode"})
    public void languageParameterCRMTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("Parameter in the CRM (language) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=plang:all");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation), "lang_EN", SecondStepField.LANGUAGE);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkLinkIdValue("lang_EN");

    }

    @Test(description = "TC 17.5. Verify that SMS verification field has emtpty (-) value")
    @Parameters({"regulation", "countryCode"})
    public void smsVerificationLanguageTest(String regulation, String countryCode) {
        ScreenshotUtil.setCustomName("SMS field in the CRM (language) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=plang:all");
        kapitalRSPage.registerDemoAccountWithParameter(TestData.firstName,TestData.lastName,email,countryCode, kapitalRSPage.selectPhoneNumber(regulation), "lang_EN", SecondStepField.LANGUAGE);
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.firstName + " " + TestData.lastName,regulation);
        crmPage.checkSMSVerification("--");
    }

    @Test(priority = 1000,
            description = "TC 18.1. Verify the account is registered successfully with NON-valid tag in the URL")
    @Parameters({"regulation", "countryCode"})
    public void nonValidParameterInTheUrlTest(String regulation,String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Demo account is successfully registered with wrong parameters in the URL - " + regulation + " regulation - KapitalRS");
        openUrl(baseUrl + "&fts=testq-testa");
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,TestData.generateEmail(),countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        readyFortrade.clickUsePassBtn();
        readyFortrade.clickMenuBtn();
        readyFortrade.checkRegulation(regulation);
    }

    @Test(description = "TC 18.2. Verify the 2nd step cannot be submitted if all parameter values are not completed")
    @Parameters({"regulation", "countryCode"})
    public void noDataOnTheSecondStepTest(String regulation,String countryCode) {
        ScreenshotUtil.setCustomName("Error messages (all parameters) - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=age-annual-saving-knowledge-plang:all");
        kapitalRSPage.fillTheFormOnTheSecondStepWithWrongData(TestData.firstName, TestData.lastName, email, countryCode, kapitalRSPage.selectPhoneNumber(regulation), "25_34_age", "15000_50000_annual", "50000_100000_savings",
                "knowledge_of_trading_all_the_above", "lang_EN");
        kapitalRSPage.assertSecondStepErrorMessage(SecondStepField.ALL);
    }

    @Test(description = "TC 19.1. Verify the cutom tag field in the CRM contains Dummy value ")
    @Parameters({"regulation","countryCode"})
    public void dummyLeadRegistration(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual-saving-knowledge-age-plang:all&ftsquery=age-equals(1,3)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                kapitalRSPage.selectPhoneNumber(regulation), "25_34_age", "50000_100000_annual", "50000_100000_savings",
                "knowledge_of_trading_all_the_above","lang_EN");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 19.2. Verify the cutom tag field in the CRM contains Dummy value ")
    @Parameters({"regulation","countryCode"})
    public void dummy_Lead_Registration(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual-saving-knowledge-age-plang:all&ftsquery=age-equals(1_3)-or-[saving-equals(1_2_3)-and-knowledge-notequals(5)]");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                kapitalRSPage.selectPhoneNumber(regulation), "25_34_age", "15000_50000_annual", "50000_100000_savings",
                "knowledge_of_trading_all_the_above","lang_EN");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 19.3. Verify the custom tag field in the CRM contains Invalid value")
    @Parameters({"regulation","countryCode"})
    public void invalidLeadRegistration(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom tag - Invalid - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual-saving-knowledge-age-plang:all&ftsquery=age(1)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                kapitalRSPage.selectPhoneNumber(regulation), "45_54_age", "15000_50000_annual", "100000_250000_savings",
                "knowledge_of_trading_none","lang_EN");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 19.4. Verify the custom tag field in the CRM contains Invalid value")
    @Parameters({"regulation","countryCode"})
    public void invalid_Lead_Registration(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom tag - Invalid - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("Redirected to the page url");
        openUrl(baseUrl + "&fts=annual-saving-knowledge-age-plang:all&ftsquery=age(1)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                kapitalRSPage.selectPhoneNumber(regulation), "45_54_age", "15000_50000_annual", "100000_250000_savings",
                "knowledge_of_trading_none","lang_EN");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 19.5. Verify the custom tag field in the CRM is empty")
    @Parameters({"regulation","countryCode"})
    public void emptyLeadRegistration(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&fts=annual-saving-knowledge-age-plang:all&ftsquery=age-equals(1,3)-or-[saving-equals(1,2,3)-and-knowledge-notequals(5)]");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                kapitalRSPage.selectPhoneNumber(regulation), "45_54_age", "15000_50000_annual", "100000_250000_savings",
                "knowledge_of_trading_all_the_above","lang_EN");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.6. Verify the custom tag field in the CRM is empty")
    @Parameters({"regulation","countryCode"})
    public void empty_Lead_Registration(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&fts=annual-saving-knowledge-age-plang:all&ftsquery=age-equals(1_3)-or-[saving-equals(1_2_3)-and-knowledge-notequals(5)]");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                kapitalRSPage.selectPhoneNumber(regulation), "45_54_age", "15000_50000_annual", "100000_250000_savings",
                "knowledge_of_trading_all_the_above","lang_EN");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email, TestData.fullName, regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.7. Verify the custom tag field in the CRM is Dummy if ftsquery device parameter is same as device " +
            "and OS used for demo account registration")
    @Parameters({"regulation","countryCode"})
    public void deviceIsSameAsParameter(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Dummy - device - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            openUrl(baseUrl + "&ftsquery=device-equals(1)");
        }else{
            openUrl(baseUrl + "&ftsquery=device-equals(2)");
        }
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }
    @Test(description = "TC 19.8. Verify the custom tag field in the CRM is invalid if syntax is not valid")
    @Parameters({"regulation","countryCode"})
    public void nonValidParameterSyntax(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Invalid - device - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            openUrl(baseUrl + "&ftsquery=device(1)");
        }else{
            openUrl(baseUrl + "&ftsquery=device(2)");
        }
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }
    @Test(description = "TC 19.9. Verify the custom tag field in the CRM is empty if ftsquery device parameter is not " +
            "same as device and OS used for demo account registration")
    @Parameters({"regulation","countryCode"})
    public void deviceIsNotSameAsParameter(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - Empty - device - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&ftsquery=device-equals(4)");
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 19.10. Verify the custom tag field in the CRM is invalid if ftsquery device parameter " +
            "contains device and OS non valid index value")
    @Parameters({"regulation","countryCode"})
    public void deviceParameterContainsNonValidValue(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("Custom Tag - non valid device value - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&ftsquery=device-equals(0)");
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Invalid");
    }

    @Test(description = "TC 20.3. Verify that the Custom Tag in the CRM is empty")
    @Parameters({"regulation","countryCode"})
    public void checkingTheCustomTag(String regulation,String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 20-3-Custom Tag is empty - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&fts=age-annual-saving-knowledge-plang:all");
        kapitalRSPage.registerDemoAccountWithParameters(TestData.firstName,TestData.lastName,email,countryCode,
                kapitalRSPage.selectPhoneNumber(regulation), "45_54_age", "15000_50000_annual", "100000_250000_savings",
                "knowledge_of_trading_all_the_above","lang_EN");
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 22.1. Verify that the Language field in the CRM contains the expected value (the language that you enter in the URL)")
    @Parameters({"regulation", "countryCode"})
    public void checkLanguageFieldContainsExpectedValue(String regulation,String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Language field in the CRM contains expected (FR) value - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        String expectedLanguage = kapitalRSPage.selectExpectedLanguage();
        openUrl(baseUrl + "&userLang=" + expectedLanguage);
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        readyFortrade.assertDisplayedLanguage(expectedLanguage);
        crmPage.checkCrmData(email, TestData.fullName, regulation);
        crmPage.checkLanguageField(email, expectedLanguage.toLowerCase());
    }

    @Test(description = "TC 22.2. Verify that the Language field in the CRM contains the default value (the language of the base page URL) when we enter the wrong language in the userLang parameter")
    @Parameters({"regulation", "countryCode"})
    public void checkLanguageFieldContainsDefaultValue(String regulation,String countryCode) throws IOException, AWTException {
        ScreenshotUtil.setCustomName("Language field in the CRM contains default (EN) value - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        String defaultLanguage = UrlProvider.getLanguage().toUpperCase();
        openUrl(baseUrl + "&userLang=FRA");
        kapitalRSPage.registerDemoAccount(TestData.firstName, TestData.lastName, email, countryCode, kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        readyFortrade.assertDisplayedLanguage(defaultLanguage);
        crmPage.checkCrmData(email, TestData.fullName, regulation);
        crmPage.checkLanguageField(email, defaultLanguage.toLowerCase());
    }

    @Test(description = "TC 23.1. Verify that the Custom Tag field in the CRM contains the DummyP value")
    @Parameters({"regulation","countryCode"})
    public void dummypParameter(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-1-Custom tag-DummyP value - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            openUrl(baseUrl + "&ftsquery=device-equals(1)&dummyP=1");
        }else{
            openUrl(baseUrl + "&ftsquery=device-equals(2)&dummyP=1");
        }
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("DummyP");
    }

    @Test(description = "TC 23.2. Verify that the Custom Tag field in the CRM contains the Dummy value")
    @Parameters({"regulation","countryCode"})
    public void dummyParameter(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-2-Custom tag - Dummy value - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            openUrl(baseUrl + "&ftsquery=device-equals(1)&dummyP=0");
        }else{
            openUrl(baseUrl + "&ftsquery=device-equals(2)&dummyp=0");
        }
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 23.3. Verify that the dummyP parameter is ignored when it's not correctly typed in the URL")
    @Parameters({"regulation","countryCode"})
    public void parameterDummy(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 23-3-Custom tag - Dummy value - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            openUrl(baseUrl + "&ftsquery=device-equals(1)&dummyp=1");
        } else{
            openUrl(baseUrl + "&ftsquery=device-equals(2)&dummyp=1");
        }
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.1. Verify that the custom tag field in the CRM contains Dummy parameter ")
    @Parameters({"regulation","countryCode"})
    public void customTagContainsDummy(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-1-Custom tag field - dummy - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&Dummy=true");
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.2. Verify that the custom tag field in the CRM contains Dummy parameter ")
    @Parameters({"regulation","countryCode"})
    public void customTagContainsDummyValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-2-Custom tag field - dummy - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&Dummy=1");
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("Dummy");
    }

    @Test(description = "TC 24.3. Verify that the custom tag field in the CRM contains '' (empty) value")
    @Parameters({"regulation","countryCode"})
    public void customTagContainsEmpty(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-3-Custom tag field - empty - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&Dummy=false");
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 24.4. Verify that the custom tag field in the CRM contains '' (empty) value")
    @Parameters({"regulation","countryCode"})
    public void customTagContainsEmptyValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 24-4-Custom tag field - empty - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&Dummy=0");
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkCustomTag("");
    }

    @Test(description = "TC 25.1. Verify that the Link Id field in the CRM contains '{number}_IPL' value")
    @Parameters({"regulation","countryCode"})
    public void checkNumberIplValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 25-1-Link Id tag field - {number}_IPL - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&tag1=1452789330");
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue(TestData.resultOfAnIPLValue);
    }

    @Test(description = "TC 25.2. Verify that the Link Id field in the CRM contains 'missingTag1_IPL' value")
    @Parameters({"regulation","countryCode"})
    public void checkMissingTag1IplValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 25-2-Link Id tag field - missingTag1_IPL - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&tag1=123abc");
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("missingTag1");
    }

    @Test(description = "TC 25.3. Verify that the Link Id field in the CRM contains 'missingCID_IPL' value")
    @Parameters({"regulation","countryCode"})
    public void checkMissingCidIplValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 25-3-Link Id tag field - missingCID_IPL - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&tag1=123456789");
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("missingCID");
    }

    @Test(description = "TC 25.4. Verify that the Link Id field in the CRM contains 'divByZero_IPL' value")
    @Parameters({"regulation","countryCode"})
    public void checkDivByZeroIplValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 25-4-Link Id tag field - divByZero_IPL - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl + "&tag1=930863512");
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("divByZero");
    }

    @Test(description = "TC 26.2. Verify the Link ID field contains None-usd value")
    @Parameters({"regulation","countryCode"})
    public void checkNoneUsdValue(String regulation, String countryCode) throws InterruptedException {
        ScreenshotUtil.setCustomName("TC 26-2-Link Id tag field - None-usd - " + regulation + " regulation - KapitalRS");
        String email = TestData.generateEmail();
        Allure.step("redirected to the url");
        openUrl(baseUrl);
        kapitalRSPage.registerDemoAccount(TestData.firstName,TestData.lastName,email,countryCode,kapitalRSPage.selectPhoneNumber(regulation));
        readyFortrade.assertURL(TestData.appUrlKapitalRS);
        crmPage.checkCrmData(email,TestData.fullName,regulation);
        crmPage.checkLinkIdValue("None_usd");
    }
}