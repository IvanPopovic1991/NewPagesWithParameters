package core.pages;

import core.actions.ElementActions;
import core.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

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

    public String msgAlrRegEmailAdd = "Email or phone already exists. Please use a different email address or phone number.";

    public String msgAlrRegPhone = "Email or phone already exists. Please use a different email address or phone number.";

    @Step("Insert first name: {firstNameData}")
    public void insertFirstName(String firstNameData) {
        ElementActions.type(firstName,firstNameData, "first name field");
    }

    @Step("Insert last name : {lastNameData}")
    public void insertLastName(String lastNameData) {
        ElementActions.type(lastName, lastNameData, "last name field");
    }

    @Step("Insert email : {emailAddress}")
    public void insertEmailAddress(String emailAddress) {
        ElementActions.type(email, emailAddress, "email field");
    }

    @Step("Insert phone number : {phoneNumberData}")
    public void insertPhoneNumber(String phoneNumberData) {
        ElementActions.type(phoneNumber, phoneNumberData, "phone number field");
    }

    public void clickGetStartedBtn() {
        ElementActions.click(submitBtn, "submit button");
    }

    public void registerDemoAccount(String firstNameData,String lastNameData,String emailAddress, String phoneNumberData) {
        insertFirstName(firstNameData);
        insertLastName(lastNameData);
        insertEmailAddress(emailAddress);
        insertPhoneNumber(phoneNumberData);
        clickGetStartedBtn();
    }

    public void assertAlrRegEmailErrorMsg() {
        Assert.assertEquals(
                ElementActions.getText(
                        msgAlrRegEmail,
                        "Already registered email address error message"),
                        msgAlrRegEmailAdd);
    }

    public void assertErrMsgForAlreadyRegisteredAccount() {
        Assert.assertEquals(ElementActions.getText(alrRegPhoneMsg,"Already registered phone number error message"),msgAlrRegPhone);
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
}
