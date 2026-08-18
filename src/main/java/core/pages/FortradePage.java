package core.pages;

import core.actions.ElementActions;
import core.base.BasePage;
import core.driver.DriverManager;
import core.enums.FirstStepField;
import core.enums.SecondStepField;
import core.localization.MessageKeys;
import core.localization.MessageProvider;
import core.localization.UrlProvider;
import core.waitsManagement.WaitUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import testdata.TestData;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FortradePage extends BasePage {

    @FindBy(xpath = "//input[@name='FirstName']")
    public WebElement firstName;

    @FindBy(xpath = "//input[@name='LastName']")
    public WebElement lastName;

    @FindBy(xpath = "//input[@id='EmailAddress']")
    public WebElement email;

    @FindBy(xpath = "//span[@class='country-code']")
    public WebElement fixedCountryCodeValue;

    @FindBy(xpath = "//div[@class='country-phone-select']")
    public WebElement dropdownCountryCode;

    @FindBy(xpath = "//label[@for='Prephone']/input[@type='text']")
    public WebElement textCountryCode;

    @FindBy(xpath = "//label[@for='Prephone']/div[@class='phone-prefix-wrapper']")
    public WebElement fixedCountryCode;

    @FindBy(xpath = "//input[@id='Telephone']")
    public WebElement phoneNumber;

    @FindBy(xpath = "//input[@id='TelephoneMask']")
    public WebElement phoneNumberEphone;

    @FindBy(xpath = "//button[@id='main-submit-btn']")
    public WebElement submitBtn;

    @FindBy(xpath = "//button[@id='next-stage-btn']")
    public WebElement continueBtn;

    @FindBy(xpath = "//div[@class='formPair error-wrapper phone-pair']/following-sibling::span[@class='errorMessage'][1]")
    public WebElement alreadyRegisteredAccount;

    @FindBy(xpath = "//label[@for='FirstName']/following-sibling::span[@class='errorMessage'][1]")
    public WebElement firstNameErrorMsg;

    @FindBy(xpath = "//label[@for='LastName']/following-sibling::span[@class='errorMessage'][1]")
    public WebElement lastNameErrorMsg;

    @FindBy(xpath = "//label[@for='EmailAddress']/following-sibling::span[@class='errorMessage'][1]")
    public WebElement emailErrorMsg;

    @FindBy(xpath = "//div[@class='formPair error-wrapper phone-pair']/following-sibling::span[@class='errorMessage'][1]")
    public WebElement phoneErrorMsg;

    @FindBy(xpath = "//label[@for='FirstName']/following-sibling::span[@class='errorMessage'][1]")
    public WebElement sameFirstNameErrorMsg;

    @FindBy(xpath = "//label[@for='LastName']/following-sibling::span[@class='errorMessage'][1]")
    public WebElement sameLastNameErrorMsg;

    @FindBy(xpath = "//div[@class='LcWidgetTopWrapper ClField-Age lcFieldWrapper']//select[@name='AgeSelect']")
    public WebElement age;

    @FindBy(xpath = "//div[@class='LcWidgetTopWrapper ClField-EstimatedAnnualIncome lcFieldWrapper']//select[@name='AnnualSelect']")
    public WebElement annual;

    @FindBy(xpath = "//div[@class='LcWidgetTopWrapper ClField-ValueOfSavingAndInvestments lcFieldWrapper']//select[@name='SavingSelect']")
    public WebElement saving;

    @FindBy(xpath = "//div[@class='LcWidgetTopWrapper ClField-KnowledgeOfTrading lcFieldWrapper']//select[@name='KnowledgeSelect']")
    public WebElement knowledge;

    @FindBy(xpath = "//div[@class='LcWidgetTopWrapper ClField-PreferredLanguage lcFieldWrapper']//select[@name='PreferredLanguage']")
    public WebElement language;

    @FindBy(xpath = "//select[@name='AgeSelect']/following-sibling::span[@class='selectErrorMessage'][1]")
    public WebElement ageErrorMsg;

    @FindBy(xpath = "//select[@name='AnnualSelect']/following-sibling::span[@class='selectErrorMessage'][1]")
    public WebElement annualErrorMsg;

    @FindBy(xpath = "//select[@name='SavingSelect']/following-sibling::span[@class='selectErrorMessage'][1]")
    public WebElement savingErrorMsg;

    @FindBy(xpath = "//select[@name='KnowledgeSelect']/following-sibling::span[@class='selectErrorMessage'][1]")
    public WebElement knowledgeErrorMsg;

    @FindBy(xpath = "//select[@name='PreferredLanguage']/following-sibling::span[@class='selectErrorMessage'][1]")
    public WebElement languageErrorMsg;

    @FindBy(xpath = "//div[@class='flex-shrink-0']")
    public WebElement iirocLogo;

    @FindBy(xpath = "//form/p")
    public WebElement textUnderForm;

    @FindBy(xpath = "//label[@class='input-wrapper error-wrapper' and @for='FirstName']")
    public WebElement borderColorForFirstName;

    @FindBy(xpath = "//label[@class='input-wrapper error-wrapper' and @for='LastName']")
    public WebElement borderColorForLastName;

    @FindBy(xpath = "//label[@class='input-wrapper error-wrapper' and @for='EmailAddress']")
    public WebElement borderColorForEmail;

    @FindBy(xpath = "//label[@class='input-wrapper error-wrapper' and @for='Telephone']")
    public WebElement borderColorForPhone;

    @FindBy(xpath = "//label[@class='input-wrapper error-wrapper' and @for='TelephoneMask']")
    public WebElement borderColorForPhoneFixed;

    @FindBy(xpath = "//div[@class='formGrid']//a[contains(@href,'Privacy_Policy.pdf')]")
    public WebElement headerPrivacyPolicyLink;

    @FindBy(xpath = "//div[@class='formGrid']//a[contains(@href,'Client_Agreement.pdf')]")
    public WebElement headerTermsAndConditionsOtherLink;

    @FindBy(xpath = "//div[@class='formGrid']//a[contains(@href,'client-agreement')]")
    public WebElement headerTermsAndConditionsFSCLink;

    @FindBy(xpath = "//div[@class='formGrid']//a[contains(@href,'Terms_and_Conditions.pdf')]")
    public WebElement headerTermsAndConditionsFCALink;

    @FindBy(xpath = "//div[@class='formGrid']//a[contains(@href,'How_to_unsubscribe.pdf')]")
    public WebElement clickHereLink;

    @FindBy(xpath = "//div[@class='formGrid']//a[contains(@href,'https://ready.fortrade.com/?lang=')]")
    public WebElement alreadyHaveAnAccountLink;

    @FindBy(xpath = "//div[@class='formGrid']//a[contains(@href,'mailto:support@fortrade.com?subject=')]")
    public WebElement contactUsLink;

    @FindBy(xpath = "//div[contains(@class, 'container')]//a[contains(@href,'mailto:support@fortrade.com')]")
    public WebElement supportLink;

    @FindBy(xpath = "//div[contains(@class, 'container')]//a[contains(@href,'Risk_Disclosure.pdf')]")
    public WebElement footerRiskWarningOtherLink;

    @FindBy(xpath = "//a[contains(@href,'Fort_Securities_AU_Product_Disclosure_Statement-ASIC.pdf') and contains(@class,'frdLink')]")
    public WebElement footerRiskWarningASICLink;

    @FindBy(xpath = "//div[contains(@class, 'container')]//a[contains(@href, 'Privacy_Policy.pdf')]")
    public WebElement footerPrivacyPolicyOtherLink;

    @FindBy(xpath = "//div[contains(@class, 'container')]//a[contains(@href, 'Fort_Securities_AU_Privacy_Policy-ASIC.pdf')]")
    public WebElement footerPrivacyPolicyASICLink;

    @FindBy(xpath = "//a[contains(@href,'Fort_Securities_AU_Financial_Services_Guide-ASIC.pdf')]")
    public WebElement fsgLink;

    @FindBy(xpath = "//a[contains(@href,'Fort_Securities_AU_Product_Disclosure_Statement-ASIC.pdf') and not(contains(@class,'frdLink'))]")
    public WebElement pdsLink;

    @FindBy(xpath = "//a[contains(@href,'Fort_Securities_AU-TMD_Policy.pdf')]")
    public WebElement tmdLink;

    @FindBy(xpath = "//a[contains(@href,'https://register.fca.org.uk/s/firm')]")
    public WebElement footerFCALink;

    @FindBy(xpath = "//a[contains(@href,'https://www.ciro.ca/investors/choosing-investment-advisor/dealers-we-regulate/fortrade-canada-limited')]")
    public WebElement footerIIROCLink;

    @FindBy(xpath = "//a[contains(@href,'https://connectonline.asic.gov.au/RegistrySearch/faces/landing/panelSearch.jspx')]")
    public WebElement footerASICLink;

    @FindBy(xpath = "//a[contains(@href,'https://www.cysec.gov.cy/en-GB/entities/investment-firms/cypriot/86639/')]")
    public WebElement footerCYSECLink;

    @FindBy(xpath = "//a[contains(@href,'https://opr.fscmauritius.org/ords/opr/r/fsc-opr/fsc-online-public-register-opr')]")
    public WebElement footerFSCLink;

    @FindBy(xpath = "//a[contains(@href,'https://www.dfsa.ae/public-register/firms/fortrade-difc-limited')]")
    public WebElement footerDFSALink;

    @FindBy(xpath = "//input[@id='Resend-Token-Btn']")
    public WebElement iDidntReceiveTheCodeLink;

    @FindBy(xpath = "//label[@name='SentAgainLabel']")
    public WebElement weSentYouTheCodeAgain;

    @FindBy(xpath = "//div/input[@name='Token0']")
    public WebElement firstSmsTokenField;

    @FindBy(xpath = "//div/input[@name='Token1']")
    public WebElement secondSmsTokenField;

    @FindBy(xpath = "//div/input[@name='Token2']")
    public WebElement thirdSmsTokenField;

    @FindBy(xpath = "//div/input[@name='Token3']")
    public WebElement fourthSmsTokenField;

    @FindBy(xpath = "//div/span[@class='smsErrorMessage']")
    public WebElement smsFieldsErrorMessage;

    @FindBy(xpath = "//div/input[@id='Details-Edit-Btn']")
    public WebElement editPencilButton;

    @FindBy(xpath = "//div[@id='stickyHeader']/div/div[2]/div/strong")
    public WebElement dynamicFCAPercentages;

    @FindBy(xpath = "//div[@class='fcaClass']/b[contains(text(), '% of retail investor accounts lose money when trading CFDs with this provider.')]")
    public WebElement staticFCAPercentages;

    @FindBy(xpath = "//div[@id='stickyHeader']/div/div[2]/div/strong")
    public WebElement dynamicCysecPercentages;

    @FindBy(xpath = "//div[@class='cysecClass']/b[contains(text(), '% of retail investor accounts lose money when trading CFDs with this provider.')]")
    public WebElement staticCysecPercentages;

    @FindBy(xpath = "//div[@id='stickyHeader']/div/div[2]")
    public WebElement riskWarningHeaderDfsa;

    @Step("Insert first name: {firstNameData}")
    public void insertFirstName(String firstNameData) {
        ElementActions.type(firstName, firstNameData, "first name field");
    }

    @Step("Insert last name : {lastNameData}")
    public void insertLastName(String lastNameData) {
        ElementActions.type(lastName, lastNameData, "last name field");
    }

    @Step("Insert email : {emailAddress}")
    public void insertEmailAddress(String emailAddress) {
        ElementActions.type(email, emailAddress, "email field");
    }

    /**
     * country code type field detection
     */
    public enum FieldType {
        TEXT,
        HIDDEN,
        UNKNOWN,
        DROPDOWN
    }

    private FieldType detectCountryCodeType() {
        if (ElementActions.isElementDisplayed(fixedCountryCode)) {
            return FieldType.HIDDEN;
        }
        if (ElementActions.isElementDisplayed(textCountryCode)) {
            return FieldType.TEXT;
        }
        if (ElementActions.isElementDisplayed(dropdownCountryCode)) {
            return FieldType.DROPDOWN;
        }
        return FieldType.UNKNOWN;
    }

    /**
     * Country code method detection
     */
    public void handleCountryCode(String countryCodeData) {
        FieldType fieldType = detectCountryCodeType();
        switch (fieldType) {
            case HIDDEN:
                validateHiddenCountryCode(countryCodeData);
                break;
            case TEXT:
                enterCountryCode(countryCodeData);
                break;
            case DROPDOWN:
                selectCountry(countryCodeData);
                break;
            default:
                throw new RuntimeException("Country code type is not supported");
        }
    }

    private void validateHiddenCountryCode(String expectedValue) {
        String actualValue = ElementActions.getText((fixedCountryCodeValue), "fixed country code").replace("+", "");

        Assert.assertEquals(actualValue, expectedValue);
        System.out.println("Expected country code number");
    }

    @Step("Insert country code : {countryCode}")
    public void enterCountryCode(String countryCodeData) {
        ElementActions.type(textCountryCode, countryCodeData, "country code");
    }

    public void selectCountry(String country) {
        ElementActions.click(dropdownCountryCode, "Country Dropdown");
        By countryLocator = By.xpath("//div/ul/li[@data-dial='" + country + "']");
        WaitUtil.waitForVisible(countryLocator);
        WebElement countryElement = driver.findElement(countryLocator);
        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", countryElement);
        ElementActions.click(countryElement, "Country: " + country);
    }

    private WebElement getPhoneNumberField() {

        return UrlProvider.isEphone()
                ? phoneNumberEphone
                : phoneNumber;
    }

    private String  getPhoneErrorMsg(){

        return UrlProvider.isEphone()
                ? MessageProvider.get(MessageKeys.PHONE_ERROR_FIXED)
                : MessageProvider.get(MessageKeys.PHONE_ERROR);
    }

    private WebElement getPhoneBorderColorField() {

        return UrlProvider.isEphone()
                ? borderColorForPhoneFixed
                : borderColorForPhone;
    }

    public WebElement getTermsAndConditionsLink(String regulation) {
        switch (regulation){
            case "fsc":
                return headerTermsAndConditionsFSCLink;
            case "fca":
                return headerTermsAndConditionsFCALink;
        }
        return headerTermsAndConditionsOtherLink;
    }

    public WebElement getFooterPrivacyPolicyLink(String regulation) {

        return regulation.equalsIgnoreCase("asic")
                ? footerPrivacyPolicyASICLink
                : footerPrivacyPolicyOtherLink;
    }

    public WebElement getFooterRiskWarningLink(String regulation) {

        return regulation.equalsIgnoreCase("asic")
                ? footerRiskWarningASICLink
                : footerRiskWarningOtherLink;
    }

    @Step("Insert phone number : {phoneNumberData}")
    public void insertPhoneNumber(String phoneNumberData) {
        ElementActions.type(getPhoneNumberField(), phoneNumberData, "phone number field");
    }

    public void clickGetStartedBtn() {
        ElementActions.click(submitBtn, "start trading button");
    }

    public void clickContinueBtn(){
        ElementActions.click(continueBtn,"next stage button");
    }

    @Step("Select age : {ageData}")
    public void selectAge(String text) {
        ElementActions.selectByValue(age, text, "age");
    }

    @Step("Select annual : {annualData}")
    public void selectAnnualIncome(String annualData) {
        ElementActions.selectByValue(annual, annualData, "annual income");
    }

    @Step("Select saving : {savingData}")
    public void selectSaving(String savingData) {
        ElementActions.selectByValue(saving, savingData, "saving");
    }

    @Step("Select knowledge : {knowledgeData}")
    public void selectKnowledge(String knowledgeData) {
        ElementActions.selectByValue(knowledge, knowledgeData, "knowledge of investments");
    }

    @Step("Select language : {languageData}")
    public void selectLanguage(String languageData) {
        ElementActions.selectByValue(language, languageData, "Preferred language");
    }

    public void registerDemoAccount(String firstNameData, String lastNameData, String emailAddress, String countryCodeData, String phoneNumberData) {
        insertFirstName(firstNameData);
        insertLastName(lastNameData);
        insertEmailAddress(emailAddress);
        handleCountryCode(countryCodeData);
        insertPhoneNumber(phoneNumberData);
        clickGetStartedBtn();
    }

    public void goToSecondStep(String firstNameData, String lastNameData, String emailAddress, String countryCodeData, String phoneNumberData) {
        insertFirstName(firstNameData);
        insertLastName(lastNameData);
        insertEmailAddress(emailAddress);
        handleCountryCode(countryCodeData);
        insertPhoneNumber(phoneNumberData);
        clickContinueBtn();
    }

    public void selectSecondStepField (SecondStepField field, String parameterData){
        switch (field){
            case AGE:
                selectAge(parameterData);
                break;
            case ANNUAL:
                selectAnnualIncome(parameterData);
                break;
            case SAVING:
                selectSaving(parameterData);
                break;
            case KNOWLEDGE:
                selectKnowledge(parameterData);
                break;
            case LANGUAGE:
                selectLanguage(parameterData);
                break;
            default:
                throw new IllegalArgumentException("Unknown second step field: " + field);
        }
    }

    public void registerDemoAccountWithParameters(String firstNameData, String lastNameData, String emailAddress,
                                                  String countryCodeData, String phoneNumberData, String ageData,
                                                  String annualData, String savingData, String knowledgeData,
                                                  String languageData) {
        goToSecondStep(firstNameData, lastNameData, emailAddress, countryCodeData, phoneNumberData);
        selectSecondStepField(SecondStepField.AGE, ageData);
        selectSecondStepField(SecondStepField.ANNUAL, annualData);
        selectSecondStepField(SecondStepField.SAVING, savingData);
        selectSecondStepField(SecondStepField.KNOWLEDGE, knowledgeData);
        selectSecondStepField(SecondStepField.LANGUAGE, languageData);
        clickGetStartedBtn();
    }

    public void registerDemoAccountWithParameter(String firstNameData, String lastNameData, String emailAddress,
                                                    String countryCodeData, String phoneNumberData, String parameterData, SecondStepField field) {
        goToSecondStep(firstNameData, lastNameData, emailAddress, countryCodeData, phoneNumberData);
        selectSecondStepField(field, parameterData);
        clickGetStartedBtn();
    }

    public void wrongDataSecondStep(String firstNameData, String lastNameData, String emailAddress,
                                    String countryCodeData, String phoneNumberData, String parameterData, String wrongParameterData, SecondStepField field) {
        goToSecondStep(firstNameData, lastNameData, emailAddress, countryCodeData, phoneNumberData);
        clickGetStartedBtn();
        selectSecondStepField(field, parameterData);
        selectSecondStepField(field, wrongParameterData);
        clickGetStartedBtn();
    }

    public void fillTheFormOnTheSecondStepWithWrongSmsCode(String firstNameData, String lastNameData, String emailAddress,
                                                  String countryCodeData, String phoneNumberData, String ageData,
                                                  String annualData, String savingData, String knowledgeData,
                                                  String languageData, String token0, String token1, String token2, String token3) {
        goToSecondStep(firstNameData, lastNameData, emailAddress, countryCodeData, phoneNumberData);
        selectAge(ageData);
        selectAnnualIncome(annualData);
        selectSaving(savingData);
        selectKnowledge(knowledgeData);
        selectLanguage(languageData);
        enterTheSmsToken(token0, token1, token2, token3);
        clickGetStartedBtn();
    }

    public void enterTheSmsToken(String token0, String token1, String token2, String token3) {
        ElementActions.type(firstSmsTokenField, token0, "first sms token field");
        ElementActions.type(secondSmsTokenField, token1, "second sms token field");
        ElementActions.type(thirdSmsTokenField, token2, "third sms token field");
        ElementActions.type(fourthSmsTokenField, token3, "fourth sms token field");
    }

    public void assertErrMsgForAlreadyRegisteredAccount() {
        Assert.assertEquals(ElementActions.getText(alreadyRegisteredAccount, "Already registered email or phone error message"), MessageProvider.get(MessageKeys.ALREADY_REGISTERED_ERROR));
    }

    public void assertFirstStepErrorMessage(FirstStepField field){
        switch (field){
            case ALL:
                assertFirstStepErrorMessage(FirstStepField.FIRST_NAME);
                assertFirstStepErrorMessage(FirstStepField.LAST_NAME);
                assertFirstStepErrorMessage(FirstStepField.EMAIL);
                assertFirstStepErrorMessage(FirstStepField.PHONE);
                break;
            case SAME_FULL_NAME:
                Assert.assertEquals(ElementActions.getText(sameFirstNameErrorMsg ,"same first name error message"), MessageProvider.get(MessageKeys.SAME_FULL_NAME_ERROR));
                assertBorderColor(borderColorForFirstName, "border-color", TestData.redBorderColor);
                Assert.assertEquals(ElementActions.getText(sameLastNameErrorMsg ,"same last name error message"), MessageProvider.get(MessageKeys.SAME_FULL_NAME_ERROR));
                assertBorderColor(borderColorForLastName, "border-color", TestData.redBorderColor);
                break;
            case FIRST_NAME:
                Assert.assertEquals(ElementActions.getText(firstNameErrorMsg ,"first name error message"), MessageProvider.get(MessageKeys.FIRST_NAME_ERROR));
                assertBorderColor(borderColorForFirstName, "border-color", TestData.redBorderColor);
                break;
            case LAST_NAME:
                Assert.assertEquals(ElementActions.getText(lastNameErrorMsg ,"last name error message"), MessageProvider.get(MessageKeys.LAST_NAME_ERROR));
                assertBorderColor(borderColorForLastName, "border-color", TestData.redBorderColor);
                break;
            case EMAIL:
                Assert.assertEquals(ElementActions.getText(emailErrorMsg ,"email error message"), MessageProvider.get(MessageKeys.EMAIL_ERROR));
                assertBorderColor(borderColorForEmail, "border-color", TestData.redBorderColor);
                break;
            case PHONE:
                Assert.assertEquals(ElementActions.getText(phoneErrorMsg ,"phone error message"), getPhoneErrorMsg());
                assertBorderColor(getPhoneBorderColorField(), "border-color", TestData.redBorderColor);
                break;
            default:
                throw new IllegalArgumentException("Unknown first step field: " + field);
        }
    }

    public void assertSecondStepErrorMessage(SecondStepField field){
        switch (field){
            case ALL:
                assertSecondStepErrorMessage(SecondStepField.AGE);
                assertSecondStepErrorMessage(SecondStepField.ANNUAL);
                assertSecondStepErrorMessage(SecondStepField.SAVING);
                assertSecondStepErrorMessage(SecondStepField.KNOWLEDGE);
                assertSecondStepErrorMessage(SecondStepField.LANGUAGE);
                break;
            case AGE:
                Assert.assertEquals(ElementActions.getText(ageErrorMsg ,"age error message"), MessageProvider.get(MessageKeys.SECOND_STEP_DROPDOWN_ERROR));
                assertBorderColor(age, "border-color", TestData.redBorderColor);
                break;
            case ANNUAL:
                Assert.assertEquals(ElementActions.getText(annualErrorMsg ,"annual income error message"), MessageProvider.get(MessageKeys.SECOND_STEP_DROPDOWN_ERROR));
                assertBorderColor(annual, "border-color", TestData.redBorderColor);
                break;
            case SAVING:
                Assert.assertEquals(ElementActions.getText(savingErrorMsg ,"saving error message"), MessageProvider.get(MessageKeys.SECOND_STEP_DROPDOWN_ERROR));
                assertBorderColor(saving, "border-color", TestData.redBorderColor);
                break;
            case KNOWLEDGE:
                Assert.assertEquals(ElementActions.getText(knowledgeErrorMsg ,"knowledge of trading error message"), MessageProvider.get(MessageKeys.SECOND_STEP_DROPDOWN_ERROR));
                assertBorderColor(knowledge, "border-color", TestData.redBorderColor);
                break;
            case LANGUAGE:
                Assert.assertEquals(ElementActions.getText(languageErrorMsg ,"preferred language error message"), MessageProvider.get(MessageKeys.SECOND_STEP_DROPDOWN_ERROR));
                assertBorderColor(language, "border-color", TestData.redBorderColor);
                break;
            default:
                throw new IllegalArgumentException("Unknown second step field: " + field);
        }
    }

    public void fillTheFormOnTheSecondStepWithWrongData(String firstNameData, String lastNameData, String emailAddress,
                                                           String countryCodeData, String phoneNumberData, String ageData,
                                                           String annualData, String savingData, String knowledgeData,
                                                           String languageData) {
        goToSecondStep(firstNameData, lastNameData, emailAddress, countryCodeData, phoneNumberData);
        clickGetStartedBtn();
        selectAge(ageData);
        selectAge("-99");
        selectAnnualIncome(annualData);
        selectAnnualIncome("-99");
        selectSaving(savingData);
        selectSaving("-99");
        selectKnowledge(knowledgeData);
        selectKnowledge("-99");
        selectLanguage(languageData);
        selectLanguage("-99");
        clickGetStartedBtn();
    }

    public void checkLogoClickability(){
        ElementActions.click(iirocLogo, "fortrade iiroc logo");
    }

    public void assertURL(String url) {
        WaitUtil.waitForUrlContains(url);
        Assert.assertTrue(driver.getCurrentUrl().contains(url));
    }

    public void assertText(WebElement element, String text){
        WaitUtil.waitForVisible(element);
        Assert.assertEquals(ElementActions.getText(element, "text under form for iiroc"), text);
    }

    public void assertBorderColor(WebElement element, String propertyName, String expectedValue) {
        WaitUtil.waitForCssValue(element, propertyName, expectedValue);
        String borderColor =  ElementActions.getCssValue(element, propertyName);
        Assert.assertEquals(borderColor, expectedValue);
    }

    public void checkLinksWithLeftClick(WebElement element, String elementName, String expectedUrl){
        WebDriver driver = DriverManager.getDriver();
        Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = caps.getBrowserName().toLowerCase();

        ElementActions.click(element, elementName);
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() > 1){
            if (browserName.equalsIgnoreCase("chrome")){
                driver.switchTo().window(tabs.get(1));
            } else {
                driver.switchTo().window(tabs.get(1));
            }
        }
        WaitUtil.waitForPageLoad();
        assertURL(expectedUrl);
        WaitUtil.threadSleep(2000);
    }

    public void checkLinksWithRightClick(WebElement element, String elementName, String expectedUrl){
        WebDriver driver = DriverManager.getDriver();
        Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = caps.getBrowserName().toLowerCase();

        ElementActions.rightClick(element, elementName);
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() > 1){
            if (browserName.equalsIgnoreCase("chrome")){
                driver.switchTo().window(tabs.get(1));
            } else {
                driver.switchTo().window(tabs.get(1));
            }
        }
        WaitUtil.waitForPageLoad();
        assertURL(expectedUrl);
    }

    public void checkMailLinks (WebElement element, String elementAttribute, String expectedUrl){
        String attribute = ElementActions.getAttributeValue(element, elementAttribute);
        String decodedAttribute = URLDecoder.decode(attribute, StandardCharsets.UTF_8);
        Assert.assertTrue(decodedAttribute.startsWith(expectedUrl));
    }

    public void checkIDidntReceiveTheCodeLink(){
        ElementActions.click(iDidntReceiveTheCodeLink, "I didn't receive the code");
        Assert.assertEquals(ElementActions.getText(weSentYouTheCodeAgain, "weSentYouTheCodeAgainMessage"), MessageProvider.get(MessageKeys.RESENT_CODE_MESSAGE));
    }

    public void assertErrorMessageForWrongSmsCode(){

        WaitUtil.waitForExactText(
                smsFieldsErrorMessage,
                MessageProvider.get(MessageKeys.INCORRECT_CODE_ERROR));

        Assert.assertEquals(ElementActions.getText(smsFieldsErrorMessage, "smsFieldErrorMessage"), MessageProvider.get(MessageKeys.INCORRECT_CODE_ERROR));
        assertBorderColor(firstSmsTokenField, "border-color", TestData.redBorderColor);
        assertBorderColor(secondSmsTokenField, "border-color", TestData.redBorderColor);
        assertBorderColor(thirdSmsTokenField, "border-color", TestData.redBorderColor);
        assertBorderColor(fourthSmsTokenField, "border-color", TestData.redBorderColor);
    }

    public void checkEditPencilButton(){
        ElementActions.click(editPencilButton, "edit pencil button");
        WaitUtil.waitForVisible(firstName);
        Assert.assertTrue(firstName.isDisplayed());
    }

    public String headerPrivacyPolicyUrl (String regulation) {
        String text = "";
        switch (regulation) {
            case "fsc": {
                text = "FSC/Fortrade_MA_Privacy_Policy.pdf";
            }
            break;
            case "fca": {
                text = "Fortrade_Privacy_Policy.pdf";
            }
            break;
            case "iiroc": {
                text = "IIROC/Privacy_Policy.pdf";
            }
            break;
            case "cysec": {
                text = "CYSEC/Privacy_Policy.pdf";
            }
            break;
            case "dfsa": {
                text = "DFSA/Privacy_Policy.pdf";
            }
        }
        String privacyPolicy = "https://www.fortrade.com/wp-content/uploads/legal/" + text;
        return privacyPolicy;
    }

    public String headerTermsAndConditionsUrl (String regulation) {
        String text = "";
        switch (regulation) {
            case "fsc": {
                text = "FSC/Fortrade_Mauritius_Client_Agreement.pdf";
            }
            break;
            case "fca": {
                text = "Fortrade_Terms_and_Conditions.pdf";
            }
            break;
            case "iiroc": {
                text = "IIROC/Client_Agreement.pdf";
            }
            break;
            case "cysec": {
                text = "CYSEC/Client_Agreement.pdf";
            }
            break;
            case "dfsa": {
                text = "DFSA/Client_Agreement.pdf";
            }
        }
        String termsAndConditions = "https://www.fortrade.com/wp-content/uploads/legal/" + text;
        return termsAndConditions;
    }

    public String footerRiskWarningUrl (String regulation) {
        String text = "";
        switch (regulation) {
            case "fsc": {
                text = "FSC/Fortrade_MA_Risk_Disclosure.pdf";
            }
            break;
            case "fca": {
                text = "Fortrade_Risk_Disclosure.pdf";
            }
            break;
            case "iiroc": {
                text = "IIROC/Risk_Disclosure.pdf";
            }
            break;
            case "cysec": {
                text = "CySEC/Risk_Disclosure.pdf";
            }
            break;
            case "asic": {
                text = "ASIC/Fort_Securities_AU_Product_Disclosure_Statement-ASIC.pdf";
            }
            break;
            case "dfsa": {
                text = "DFSA/Risk_Disclosure.pdf";
            }
        }
        String riskWarningURL = "https://www.fortrade.com/wp-content/uploads/legal/" + text;
        return riskWarningURL;
    }

    public String footerPrivacyPolicyUrl (String regulation) {
        String text = "";
        switch (regulation) {
            case "fsc": {
                text = "FSC/Fortrade_MA_Privacy_Policy.pdf";
            }
            break;
            case "fca": {
                text = "Fortrade_Privacy_Policy.pdf";
            }
            break;
            case "asic": {
                text = "ASIC/Fort_Securities_AU_Privacy_Policy-ASIC.pdf";
            }
            break;
            case "cysec": {
                text = "CYSEC/Privacy_Policy.pdf";
            }
            break;
            case "iiroc": {
                text = "IIROC/Privacy_Policy.pdf";
            }
            break;
            case "dfsa": {
                text = "DFSA/Privacy_Policy.pdf";
            }
        }
        String privacyPolicyFooterURL = "https://www.fortrade.com/wp-content/uploads/legal/" + text;
        return privacyPolicyFooterURL;
    }

    public String selectPhoneNumber(String regulation){
        String phoneNumber = TestData.generatePhoneNumber();
        if (regulation.equalsIgnoreCase("iiroc")){
            phoneNumber = TestData.canadaPhoneNumber();
        } else if (regulation.equalsIgnoreCase("fca")){
            phoneNumber = TestData.generatePhoneNumberFca();
        }
        return phoneNumber;
    }

    // It returns on which language platform will be displayed (FR) - if the regulation is DFSA it returns AR language.
    public String selectExpectedLanguage(String regulation){
        String expectedLanguage = "FR";
        if (regulation.equalsIgnoreCase("DFSA")){
            expectedLanguage = "AR";
        }
        return expectedLanguage;
    }
}
