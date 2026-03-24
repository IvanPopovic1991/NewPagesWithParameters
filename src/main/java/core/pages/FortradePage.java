package core.pages;

import core.actions.ElementActions;
import core.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

}
