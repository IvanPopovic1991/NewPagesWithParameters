package testdata;

import com.github.javafaker.Faker;

public class TestData {

    private static final Faker faker = new Faker();

    public static String fakeNumber() {
        return faker.number().digits(8);
    }

    public static String fakeWord() {
        return faker.regexify("[a-zA-Z]{5}");
    }

    public static String generateEmail() {
        return "test" + System.currentTimeMillis() + "@yopmail.com";
    }

    public static String generatePhoneNumber() {
        return "000" + fakeNumber();
    }

    private static final Faker fake = new Faker();

    public static String canadaPhoneNumber() {
        int firstDigit = fake.number().numberBetween(2, 10);
        int remainingAreaDigits = fake.number().numberBetween(0, 100);
        String areaCode = String.format("%d%02d", firstDigit, remainingAreaDigits);
        String exchange = "555";
        int lineNumber = fake.number().numberBetween(1000, 10000);
        return areaCode + exchange + lineNumber;
    }

    public static String firstName = "Testq";

    public static String lastName = "Testa";

    public static String sameNameAndSurname = "Test";
    public static String appUrl = "https://ready.fortrade.com/";

    //public static String textForIiroc = "By proceeding, I agree to the Privacy Policy and the Terms and Conditions By providing my contact details to Fortrade Canada, I agree to receive telephone calls from Customer Service Representatives regarding Fortrade Canada, its products, services, promotions, and offers. I can opt out of phone contact anytime by informing a Customer Service Representative or unsubscribing via notification settings.";

    public static String textForIiroc = "By proceeding, I agree to the Privacy Policy and the Terms and Conditions . By providing my contact details to Fortrade Canada, I agree to receive telephone calls from Customer Service Representatives regarding Fortrade Canada, its products, services, promotions, and offers. I can opt out of phone contact anytime by informing a Customer Service Representative or unsubscribing via notification settings.";

    public static String blueBorderColor = "";

    public static String redBorderColor = "rgb(255, 0, 0)";

    public static String sameFirstNameErrorMessage = "First Name and Last Name cannot be equal.";

    public static String sameLastNameErrorMessage = "First Name and Last Name cannot be equal.";

    public static String firstNameErrorMessage = "Please enter all your given first name(s).";

    public static String lastNameErrorMessage = "Please enter your last name.";

    public static String emailErrorMessage = "Must be a valid email address.";

    public static String wrongPhoneErrorMessage = "Phone number must be exactly 10 digits and cannot start with 0";

    public static String emptyPhoneErrorMessage = "Phone number is required";

    public static String secondStepErrorMessage = "Please select an option from the dropdown list.";

    public static String privacyPolicyUrl = "https://www.fortrade.com/wp-content/uploads/legal/IIROC/Privacy_Policy.pdf";

    public static String termsAndConditionsUrl = "https://www.fortrade.com/wp-content/uploads/legal/IIROC/Client_Agreement.pdf";

    public static String alreadyHaveAnAccountUrl = "https://authfe.fortrade.com/oauth/account/login";

    public static String riskWarningUrl = "https://www.fortrade.com/wp-content/uploads/legal/IIROC/Risk_Disclosure.pdf";

    public static String fcaUrl = "https://register.fca.org.uk/s/firm";

    public static String iirocUrl = "https://www.ciro.ca/investors/choosing-investment-advisor/dealers-we-regulate/fortrade-canada-limited";

    public static String asicUrl = "https://connectonline.asic.gov.au/RegistrySearch/faces/landing/panelSearch.jspx?";

    public static String cysecUrl = "https://www.cysec.gov.cy/en-GB/entities/investment-firms/cypriot/86639/";

    public static String fscUrl = "https://opr.fscmauritius.org/ords/opr/r/fsc-opr/fsc-online-public-register-opr";

    public static String dfsaUrl = "https://www.dfsa.ae/public-register/firms/fortrade-difc-limited";

    public static String contactUsUrl = "mailto:support@fortrade.com?subject=Client information request";

    public static String supportUrl = "mailto:support@fortrade.com";
}