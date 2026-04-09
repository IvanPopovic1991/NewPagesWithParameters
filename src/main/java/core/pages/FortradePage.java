package core.pages;

import core.actions.ElementActions;
import core.base.BasePage;
import core.driver.DriverManager;
import core.waitsManagement.WaitUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
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

    @FindBy(xpath = "//input[@name='Prephone']")
    public WebElement countryCode;

    @FindBy(xpath = "//span[@class='cps-label']")
    public WebElement countryCodeDropdown;

    @FindBy(xpath = "//input[@id='TelephoneMask']")
    public WebElement phoneNumber;

    @FindBy(xpath = "//button[@id='main-submit-btn']")
    public WebElement submitBtn;

    @FindBy(xpath = "//span[@class='errorMessage' and contains(text(),'Email or phone already exists')]")
    public WebElement msgAlrRegEmail;

    @FindBy(xpath = "//span[@class='errorMessage' and text()='Email or phone already exists. Please use a different email address or phone number.']")
    public WebElement alrRegPhoneMsg;

    @FindBy(xpath = "//button[@id='next-stage-btn']")
    public WebElement submitParamsBtn;

    public String msgAlrRegEmailAdd = "Email or phone already exists. Please use a different email address or phone number.";

    public String msgAlrRegPhone = "Email or phone already exists. Please use a different email address or phone number.";

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

    @FindBy(xpath = "//div[@class='flex-shrink-0']")
    public WebElement iirocLogo;

    @FindBy(xpath = "//form/p")
    public WebElement textUnderFormIiroc;

    @FindBy(xpath = "//label[@class='input-wrapper error-wrapper' and @for='FirstName']")
    public WebElement borderColorForFirstName;

    @FindBy(xpath = "//label[@class='input-wrapper error-wrapper' and @for='LastName']")
    public WebElement borderColorForLastName;

    @FindBy(xpath = "//label[@class='input-wrapper error-wrapper' and @for='EmailAddress']")
    public WebElement borderColorForEmail;

    @FindBy(xpath = "//label[@class='input-wrapper error-wrapper' and @for='TelephoneMask']")
    public WebElement borderColorForPhone;

    @FindBy(xpath = "//p/a[text()='Privacy Policy']")
    public WebElement headerPrivacyPolicyLink;

    @FindBy(xpath = "//p/a[text()='Terms and Conditions']")
    public WebElement headerTermsAndConditionsLink;
    @FindBy(xpath = "//p/a[text()='Already have an account?']")
    public WebElement alreadyHaveAnAccountLink;

    @FindBy(xpath = "//div/a[text()='Contact Us']")
    public WebElement contactUsLink;

    @FindBy(xpath = "//b/a[text()='support@fortrade.com']")
    public WebElement supportLink;

    @FindBy(xpath = "//div/a[contains(text(), 'Risk warning')]")
    public WebElement footerRiskWarningLink;

    @FindBy(xpath = "//div/a[contains(text(), 'Privacy policy')]")
    public WebElement footerPrivacyPolicyLink;

    @FindBy(xpath = "//div/a[text()='FRN: 609970']")
    public WebElement footerFCALink;

    @FindBy(xpath = "//div/a[text()='CRN: BC1148613']")
    public WebElement footerIIROCLink;

    @FindBy(xpath = "//div/a[text()='ABN: 33 614 683 831 | AFSL: 493520']")
    public WebElement footerASICLink;

    @FindBy(xpath = "//div/a[text()='CIF license number 385/20']")
    public WebElement footerCYSECLink;

    @FindBy(xpath = "//div/a[text()=' GB21026472']")
    public WebElement footerFSCLink;

    @FindBy(xpath = "//div/a[text()=' No. F009856']")
    public WebElement footerDFSALink;

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
        if (driver.findElements(By.xpath("//label/div[@class='phone-prefix-wrapper']")).size() > 0) {
            return FieldType.HIDDEN;
        }
        if (countryCode.getAttribute("type").equalsIgnoreCase("text")) {
            return FieldType.TEXT;
        }
        if (driver.findElements(By.xpath("//div[@class='country-phone-select']")).size() > 0) {
            return FieldType.DROPDOWN;
        }
        return FieldType.UNKNOWN;
    }

    private void validateHiddenCountryCode(String expectedValue) {
        String actualValue = countryCode.getAttribute("value");
        Assert.assertEquals(actualValue, expectedValue);
        System.out.println("Expected country code number");
    }

    public void selectCountry(String country) {
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

    @Step("Insert phone number : {phoneNumberData}")
    public void insertPhoneNumber(String phoneNumberData) {
        ElementActions.type(phoneNumber, phoneNumberData, "phone number field");
    }

    public void clickGetStartedBtn() {
        ElementActions.click(submitBtn, "submit button");
    }

    public void clickSubmitBtnParams(){
        ElementActions.click(submitParamsBtn,"Start trading button");
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

    public void assertAlrRegEmailErrorMsg() {
        Assert.assertEquals(
                ElementActions.getText(
                        msgAlrRegEmail,
                        "Already registered email address error message"),
                msgAlrRegEmailAdd);
    }

    public void assertErrMsgForAlreadyRegisteredAccount() {
        Assert.assertEquals(ElementActions.getText(alrRegPhoneMsg, "Already registered phone number error message"), msgAlrRegPhone);
    }

    String[] errorMessages = {"Please enter all your given first name(s).",
            "Please enter your last name.",
            "Must be a valid email address.",
            //"Phone number is required"
            "Phone number must be exactly 10 digits and cannot start with 0"};

    public void assertErrorMessages() {
        for (int i = 1; i <= 4; i++) {
            Assert.assertEquals(ElementActions.getText(By.xpath("(//span[@class='errorMessage'])[position()=number]".replace("number", String.valueOf(i))), "error message " + errorMessages[i - 1]), errorMessages[i - 1]);
        }
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
                driver.switchTo().window(tabs.get(2));
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
                driver.switchTo().window(tabs.get(2));
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
}
