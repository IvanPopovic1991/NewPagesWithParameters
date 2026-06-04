package core.pages;

import core.actions.ElementActions;
import core.base.BasePage;
import core.driver.DriverManager;
import core.waitsManagement.WaitUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import testdata.TestData;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static core.waitsManagement.WaitUtil.getWait;
import static core.waitsManagement.WaitUtil.waitForVisible;

public class BrokerView extends BasePage {

    @FindBy(xpath = "//div[@class='logo-class']")
    public WebElement logo;

    @FindBy(xpath = "//input[@name='FirstName']")
    public WebElement firstName;

    @FindBy(xpath = "//input[@name='LastName']")
    public WebElement lastName;

    @FindBy(xpath = "//input[@id='EmailAddress']")
    public WebElement email;

    @FindBy(xpath = "//input[@name='Prephone']")
    public WebElement countryCode;

    @FindBy(xpath = "//span[@class='cps-label']")
    public WebElement countryCodeDropdown;

    @FindBy(xpath = "//input[@id='Telephone']")
    public WebElement phoneNumber;

    @FindBy(xpath = "//button[@id='main-submit-btn']")
    public WebElement submitBtn1st;

    @FindBy(xpath = "//button[@id='next-stage-btn']")
    public WebElement submitBtn;

    @FindBy(xpath = "//button[@id='CybotCookiebotDialogBodyButtonDecline']")
    public WebElement denyBtn;

    @FindBy(xpath = "//input[@id='terms']")
    public WebElement checkBox;

    @FindBy(xpath = "//span[@class='errorMessage' and text()='Please check this box to proceed.']")
    public WebElement errorMessageCheckBox;

    @FindBy(xpath = "//div[@data-cmd='menu']")
    public WebElement menuBtn;

    @FindBy(xpath = "//div[@id='platformRegulation']")
    public WebElement regulationMsg;

    @FindBy(xpath = "//span[text()='Email or phone already exists. Please use a different email address or phone number.']")
    public WebElement alrdRegEmailMsg;

    @FindBy(xpath="//span[@class='errorMessage' and text()='Email or phone already exists. Please use a different email address or phone number.']")
    public WebElement alrRegPhoneMsg;

    @FindBy(xpath = "//label[@class='input-wrapper error-wrapper' and @for='FirstName']")
    public WebElement borderColorForFirstName;

    @FindBy(xpath = "//label[@class='input-wrapper error-wrapper' and @for='LastName']")
    public WebElement borderColorForLastName;

    @FindBy(xpath = "//label[@class='input-wrapper error-wrapper' and @for='EmailAddress']")
    public WebElement borderColorForEmail;

    @FindBy(xpath = "//span[text()='Must be a valid international phone number']")
    public WebElement countryCodeErrorMessage;

    @FindBy(xpath = "//label[@class='input-wrapper error-wrapper' and @for='Telephone']")
    public WebElement borderColorForPhone;

    @FindBy(xpath = "//a[contains(text(),'Already have an account?')]")
    public WebElement alreadyHaveAnAccount;

    @FindBy(xpath = "//div[@class='LcWidgetTopWrapper ClField-Age lcFieldWrapper']//select")
    public WebElement age;

    @FindBy(xpath = "//div[@class='LcWidgetTopWrapper ClField-EstimatedAnnualIncome lcFieldWrapper']//select")
    public WebElement annual;

    @FindBy(xpath = "//div[@class='LcWidgetTopWrapper ClField-ValueOfSavingAndInvestments lcFieldWrapper']//select")
    public WebElement saving;

    @FindBy(xpath = "//div[@class='LcWidgetTopWrapper ClField-KnowledgeOfTrading lcFieldWrapper']//select")
    public WebElement knowledge;

    @FindBy(xpath = "//div[@class='LcWidgetTopWrapper ClField-PreferredLanguage lcFieldWrapper']//select")
    public WebElement languageField;

    @FindBy(xpath = "//button[@id='main-submit-btn']")
    public WebElement continueBtn;

    @FindBy(xpath = "//input[@name='Token0']")
    public WebElement tokenField0;

    @FindBy(xpath = "//input[@name='Token1']")
    public WebElement tokenField1;

    @FindBy(xpath = "//input[@name='Token2']")
    public WebElement tokenField2;

    @FindBy(xpath = "//input[@name='Token3']")
    public WebElement tokenField3;

    @FindBy(xpath = "//span[@class='smsErrorMessage']")
    public WebElement incorrectTokenMsg;

    @FindBy(xpath="//input[@class='TokenBack-Button']")
    public WebElement didNotGetToken;

    @FindBy(xpath = "//label[@name='SentAgainLabel']")
    public WebElement codeIsSent;

    @FindBy(xpath="//input[@id='Details-Edit-Btn']")
    public WebElement editTokenBtn;

    @FindBy(xpath = "//span[text()='Fortrade']")
    public WebElement fortradeLogo;

    @FindBy(xpath = "//div[@class='nav-button']")
    public WebElement registerHereBtn;

    @FindBy(xpath = "//div[@class='webSpriteIcon cross']")
    public WebElement closeBtnPyc;

    @FindBy(xpath = "//div[@class='startTradingButton']")
    public WebElement usePasswordBtn;

    @FindBy(xpath = "//button[contains(@class,'pushcrew-btn-close')]")
    public WebElement doNotAllowNtf;

    @FindBy(xpath = "//div[@class='exitButton']")
    public WebElement btnNotSerbianRes;

    @FindBy(xpath="//a[text()='click here']")
    public WebElement clickHere;

    @FindBy(xpath = "//p/a[text()='Privacy Policy']")
    public WebElement headerPrivacyPolicyLink;

    @FindBy(xpath = "//p/a[text()='Terms and Conditions']")
    public WebElement headerTermsAndConditionsLink;

    public By clickHereLink = By.xpath("//a[text()='click here']");

    @FindBy(xpath = "//a[contains(text(), 'Already have an account?')]")
    public WebElement alreadyHaveAnAccountLink;

    @FindBy(xpath = "//div/a[text()='Contact Us']")
    public WebElement contactUsLink;

    @FindBy(xpath = "//a[contains(@href,'mailto:support@fortrade.com?subject=Client information request')]")
    public WebElement supportLink;

    @FindBy(xpath = "//a[text()=' GB21026472']")
    public WebElement footerFSCLink;

    public String[] errorMessages = {"Please enter all your given first name(s).",
            "Please enter your last name.",
            "Must be a valid email address.",
            "Must be a valid international phone number"};

    public String[] sameNamesErrorMessages = {"First Name and Last Name cannot be equal.",
            "First Name and Last Name cannot be equal."};

    public String msgAlrRegEmailAdd = "Email or phone already exists. Please use a different email address or phone number.";

    public String msgAlrRegPhone = "Email or phone already exists. Please use a different email address or phone number.";

    private String expErrMsgEmail = "Email or phone already exists. Please use a different email address or phone number.";

    public String privacyPolicyUrl = "https://www.fortrade.com/wp-content/uploads/legal/FSC/Fortrade_MA_Privacy_Policy.pdf";

    public String termsAndConditionsUrl = "https://www.fortrade.com/wp-content/uploads/legal/FSC/Fortrade_Mauritius_Client_Agreement.pdf";

    public String howToUnsubscribeURL = "https://www.fortrade.com/wp-content/uploads/legal/How_to_guides/How_to_unsubscribe.pdf";

    public String alrHaveAccountUrl = "https://authfe.kapitalrs.com/oauth/account/login";

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

    @Step("Insert country code : {countryCode}")
    public void enterCountryCode(String countryCodeData) {
        ElementActions.type(countryCode, countryCodeData, "country code");
    }

    @Step("Click here link")
    public void clickHereLink(){
        ElementActions.click(clickHere,"click here link");
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

    private FortradePage.FieldType detectCountryCodeType() {
        if (driver.findElements(By.xpath("//label/div[@class='phone-prefix-wrapper']")).size() > 0) {
            return FortradePage.FieldType.HIDDEN;
        }
        if (countryCode.getAttribute("type").equalsIgnoreCase("text")) {
            return FortradePage.FieldType.TEXT;
        }
        if (driver.findElements(By.xpath("//div[@class='country-phone-select']")).size() > 0) {
            return FortradePage.FieldType.DROPDOWN;
        }
        return FortradePage.FieldType.UNKNOWN;
    }

    private void validateHiddenCountryCode(String expectedValue) {
        String actualValue = countryCode.getAttribute("value");
        Assert.assertEquals(actualValue, expectedValue);
        System.out.println("Expected country code number");
    }

    public void selectCountry(String country) {
        ElementActions.click(countryCodeDropdown, "Country Dropdown");
        By countryLocator = By.xpath("//div/ul/li[@data-name='" + country + "']");
        WebElement countryElement = getWait().until(ExpectedConditions.visibilityOfElementLocated(countryLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", countryElement);
        ElementActions.click(countryElement, "Country: " + country);
    }

    /**
     * Country code method detection
     */
    public void handleCountryCode(String countryCodeData) {
        FortradePage.FieldType fieldType = detectCountryCodeType();
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

    @Step("Insert phone number : {phoneNumberData}")
    public void insertPhoneNumber(String phoneNumberData) {
        ElementActions.type(phoneNumber, phoneNumberData, "phone number field");
    }

    public void clickGetStartedBtn() {
        ElementActions.click(continueBtn, "submit button");
    }

    public void clickSubmitBtnParams(){
        ElementActions.click(submitBtn,"Start trading button");
    }

    @Step("Select age : {ageData}")
    public void selectAge(String text) {
        ElementActions.selectByText(age, text, "age");
    }

    @Step("Select annual : {annualData}")
    public void selectAnnualIncome(String annualData) {
        ElementActions.selectByText(annual, annualData, "annual income");
    }

    @Step("Select saving : {savingData}")
    public void selectSaving(String savingData) {
        ElementActions.selectByText(saving, savingData, "saving");
    }

    @Step("Select knowledge : {knowledgeData}")
    public void selectKnowledge(String knowledgeData) {
        ElementActions.selectByText(knowledge, knowledgeData, "knowledge of investments");
    }

    @Step("Select language : {languageData}")
    public void selectLanguage(String languageData) {
        ElementActions.selectByText(languageField, languageData, "Preferred language");
    }

    @Step("Click submit button on 2nd step")
    public void clickSubmitBtn2nd() {
        ElementActions.click(continueBtn, "Continue button - 2nd step");
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
        clickSubmitBtnParams();
    }

    public void registerDemoAccountWithParameters(String firstNameData, String lastNameData, String emailAddress,
                                                  String countryCodeData, String phoneNumberData, String ageData,
                                                  String annualData, String savingData, String knowledgeData,
                                                  String languageData) {
        insertFirstName(firstNameData);
        insertLastName(lastNameData);
        insertEmailAddress(emailAddress);
        handleCountryCode(countryCodeData);
        insertPhoneNumber(phoneNumberData);
        clickSubmitBtnParams();
        selectAge(ageData);
        selectAnnualIncome(annualData);
        selectSaving(savingData);
        selectKnowledge(knowledgeData);
        selectLanguage(languageData);
        clickSubmitBtn2nd();
    }

    public void registerDemoAccountWithParameter(String firstNameData, String lastNameData, String emailAddress,
                                                 String countryCodeData, String phoneNumberData, String parameterData, String parameter) {
        insertFirstName(firstNameData);
        insertLastName(lastNameData);
        insertEmailAddress(emailAddress);
        handleCountryCode(countryCodeData);
        insertPhoneNumber(phoneNumberData);
        clickSubmitBtnParams();
        switch (parameter){
            case "age":
                selectAge(parameterData);
                break;
            case "annual":
                selectAnnualIncome(parameterData);
                break;
            case "saving":
                selectSaving(parameterData);
                break;
            case "knowledge":
                selectKnowledge(parameterData);
                break;
            case "language":
                selectLanguage(parameterData);
                break;
            default:
                System.out.println("Wrong parameter!");
        }
        clickSubmitBtn2nd();
    }

    public void checkErrorMessageForParameter(String firstNameData, String lastNameData, String emailAddress,
                                              String countryCodeData, String phoneNumberData, String parameterData, String wrongParameterData, String parameter) {
        insertFirstName(firstNameData);
        insertLastName(lastNameData);
        insertEmailAddress(emailAddress);
        handleCountryCode(countryCodeData);
        insertPhoneNumber(phoneNumberData);
        clickSubmitBtnParams();
        clickSubmitBtn2nd();
        switch (parameter){
            case "age":
                selectAge(parameterData);
                selectAge(wrongParameterData);
                break;
            case "annual":
                selectAnnualIncome(parameterData);
                selectAnnualIncome(wrongParameterData);
                break;
            case "saving":
                selectSaving(parameterData);
                selectSaving(wrongParameterData);
                break;
            case "knowledge":
                selectKnowledge(parameterData);
                selectKnowledge(wrongParameterData);
                break;
            case "language":
                selectLanguage(parameterData);
                selectLanguage(wrongParameterData);
                break;
            default:
                System.out.println("Wrong parameter!");
        }
        clickSubmitBtn2nd();
    }

    public void fillTheFormOnTheSecondStepWithWrongSmsCode(String firstNameData, String lastNameData, String emailAddress,
                                                           String countryCodeData, String phoneNumberData, String ageData,
                                                           String annualData, String savingData, String knowledgeData,
                                                           String languageData, String token0, String token1, String token2, String token3) {
        insertFirstName(firstNameData);
        insertLastName(lastNameData);
        insertEmailAddress(emailAddress);
        handleCountryCode(countryCodeData);
        insertPhoneNumber(phoneNumberData);
        clickSubmitBtnParams();
        selectAge(ageData);
        selectAnnualIncome(annualData);
        selectSaving(savingData);
        selectKnowledge(knowledgeData);
        selectLanguage(languageData);
        enterTheSmsToken(token0, token1, token2, token3);
        clickSubmitBtn2nd();
    }

    public void enterTheSmsToken(String token0, String token1, String token2, String token3) {
        ElementActions.type(tokenField0, token0, "first sms token field");
        ElementActions.type(tokenField1, token1, "second sms token field");
        ElementActions.type(tokenField2, token2, "third sms token field");
        ElementActions.type(tokenField3, token3, "fourth sms token field");
    }

    public void assertAlrRegEmailErrorMsg() {
        Assert.assertEquals(
                ElementActions.getText(
                        alrdRegEmailMsg,
                        "Already registered email address error message"),
                msgAlrRegEmailAdd);
    }

    public void assertErrMsgForAlreadyRegisteredAccount() {
        Assert.assertEquals(ElementActions.getText(alrRegPhoneMsg, "Already registered phone number error message"), msgAlrRegPhone);
    }

    public void assertErrorMessages() {
        for (int i = 1; i <= 4; i++) {
            Assert.assertEquals(ElementActions.getText(By.xpath("(//span[@class='errorMessage'])[position()=number]".replace("number", String.valueOf(i))), "error message " + errorMessages[i - 1]), errorMessages[i - 1]);
        }
    }

    public void assertSecondStepErrorMessage(String parameter){
        Assert.assertEquals(ElementActions.getText(By.xpath("(//span[@class='selectErrorMessage'])[position()=1]"), "error message"), TestData.secondStepErrorMessage);
        switch (parameter){
            case "age":
                assertBorderColor(age, "border-color", TestData.redBorderColor);
                break;
            case "annual":
                assertBorderColor(annual, "border-color", TestData.redBorderColor);
                break;
            case "saving":
                assertBorderColor(saving, "border-color", TestData.redBorderColor);
                break;
            case "knowledge":
                assertBorderColor(knowledge, "border-color", TestData.redBorderColor);
                break;
            case "plang":
                assertBorderColor(languageField, "border-color", TestData.redBorderColor);
                break;
            default:
                System.out.println("Wrong parameter!");
        }
    }

    public void assertSecondStepErrorMessageAllParameters(){
        for (int i = 1; i < 6; i++){
            Assert.assertEquals(ElementActions.getText(By.xpath("(//span[@class='selectErrorMessage'])[position()={index}]".replace("{index}", String.valueOf(i))), "second step error message"), TestData.secondStepErrorMessage);
        }
        assertBorderColor(age, "border-color", TestData.redBorderColor);
        assertBorderColor(annual, "border-color", TestData.redBorderColor);
        assertBorderColor(saving, "border-color", TestData.redBorderColor);
        assertBorderColor(knowledge, "border-color", TestData.redBorderColor);
        assertBorderColor(languageField, "border-color", TestData.redBorderColor);
    }

    public void fillTheFormOnTheSecondStepWithWrongData(String firstNameData, String lastNameData, String emailAddress,
                                                        String countryCodeData, String phoneNumberData, String ageData,
                                                        String annualData, String savingData, String knowledgeData,
                                                        String languageData) {
        insertFirstName(firstNameData);
        insertLastName(lastNameData);
        insertEmailAddress(emailAddress);
        handleCountryCode(countryCodeData);
        insertPhoneNumber(phoneNumberData);
        clickSubmitBtnParams();
        clickSubmitBtn2nd();
        selectAge(ageData);
        selectAge("-- Select --");
        selectAnnualIncome(annualData);
        selectAnnualIncome("-- Select --");
        selectSaving(savingData);
        selectSaving("-- Select --");
        selectKnowledge(knowledgeData);
        selectKnowledge("-- Select --");
        selectLanguage(languageData);
        selectLanguage("-- Select --");
        clickSubmitBtn2nd();
    }

    public void checkLogoClickability(){
        ElementActions.click(fortradeLogo, "fortrade iiroc logo");
    }

    public void assertURL(String url) {
        WaitUtil.waitForUrlContains(url);
        Assert.assertTrue(driver.getCurrentUrl().contains(url));
    }

    public void assertText(WebElement element, String text){
        waitForVisible(element);
        Assert.assertEquals(ElementActions.getText(element, "text under form for iiroc"), text);
    }

    public void assertBorderColor(WebElement element, String propertyName, String expectedValue) {
        String borderColor =  ElementActions.getCssValue(element, propertyName);
        Assert.assertEquals(borderColor, expectedValue);
    }

    public void assertFirstStepErrorMessage (String errorMessage){
        WebElement webElement = driver.findElement(By.xpath("//span[@class='errorMessage'][text()='{text}']".replace("{text}", errorMessage)));
        Assert.assertEquals(ElementActions.getText(webElement, "error message"), errorMessage);
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
        try {
            Thread.sleep(2000);
        } catch (Exception e){
            System.out.println(e);
        }
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
        Assert.assertEquals(decodedAttribute, expectedUrl);
    }

    public void checkIDidntReceiveTheCodeLink(){
        ElementActions.click(didNotGetToken, "I didn't receive the code");
        Assert.assertEquals(ElementActions.getText(codeIsSent, "weSentYouTheCodeAgainMessage"), "We sent you the code again");
    }

    public void assertErrorMessageForWrongSmsCode(){
        Assert.assertEquals(ElementActions.getText(incorrectTokenMsg, "smsFieldErrorMessage"), "Incorrect Code. Please check and try again.");
        assertBorderColor(tokenField0, "border-color", TestData.redBorderColor);
        assertBorderColor(tokenField1, "border-color", TestData.redBorderColor);
        assertBorderColor(tokenField2, "border-color", TestData.redBorderColor);
        assertBorderColor(tokenField3, "border-color", TestData.redBorderColor);
    }

    public void checkEditPencilButton(){
        ElementActions.click(editTokenBtn, "edit pencil button");
        waitForVisible(firstName);
        Assert.assertTrue(firstName.isDisplayed());
    }

}
