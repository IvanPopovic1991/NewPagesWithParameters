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

    public static String generatePhoneNumberFca() {
        return "10" + fakeNumber();
    }

    public static String generatePhoneNumberAsic() {
        return "491" + fakeNumber();
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

    public static String fullName = "Testq Testa";

    public static String sameNameAndSurname = "Test";

    public static String appUrl = "https://ready.fortrade.com/";

    public static String appUrlKapitalRS = "https://pro.kapitalrs.com/";

     public static String textForIiroc = "By proceeding, I agree to the Privacy Policy and the Terms and Conditions . By providing my contact details to Fortrade Canada, I agree to receive telephone calls from Customer Service Representatives regarding Fortrade Canada, its products, services, promotions, and offers. I can opt out of phone contact anytime by informing a Customer Service Representative or unsubscribing via notification settings.";

     public static String textForAsic = "By providing your details to Fortrade Australia you are consenting to be contacted by telephone about offers and invites to trade Contracts for Difference (CFDs).";

     public static String percentagesFCA = "74% of retail investor accounts lose money when trading CFDs with this provider.";

     public static String percentagesCysec = "69.54% of retail investor accounts lose money when trading CFDs with this provider.";

     public static String riskWarningHeaderDfsaText = "Contracts for difference (CFDs) are complex financial instruments and come with a high risk of losing money rapidly due to leverage. You should ensure you understand how CFDs work and that you can afford to take the high risk of losing your money." /*"العقود مقابل الفروقات أدوات مالية معقّدة وتنطوي على مخاطر عالية قد تؤدي إلى خسارة الأموال بسرعة بسبب الرافعة المالية. ينبغي عليك التأكّد مما إذا كنت تفهم كيفية عمل العقود مقابل الفروقات، وأنك قادر على تحمّل المخاطر العالية المترتّبة على خسارة أموالك."*/;

    public static String blueBorderColor = "";

    public static String redBorderColor = "rgb(255, 0, 0)";

    public static String sameFirstNameErrorMessage = "First Name and Last Name cannot be equal.";

    public static String sameLastNameErrorMessage = "First Name and Last Name cannot be equal.";

    public static String sameFirstNameErrorMessageEs = "El Nombre y el Apellido no pueden ser iguales.";

    public static String sameLastNameErrorMessageEs = "El Nombre y el Apellido no pueden ser iguales.";

    public static String firstNameErrorMessage = "Please enter all your given first name(s).";

    public static String firstNameErrorMessageEs = "Por favor, introduce todos tus nombres de pila.";

    public static String lastNameErrorMessage = "Please enter your last name.";

    public static String lastNameErrorMessageEs = "Por favor, introduce tu apellido.";

    public static String emailErrorMessage = "Must be a valid email address.";

    public static String emailErrorMessageEs = "Formato de correo electrónico no válido.";

    public static String wrongPhoneErrorMessage = "Phone number must be exactly 10 digits and cannot start with 0";

    public static String wrongPhoneErrorMsgOther = "Must be a valid international phone number";

    public static String wrongPhoneErrorMsgAu = "Phone number must be between 6 and 11 digits and cannot start with 0";

    public static String wrongPhoneErrorMsgOtherEs = "Formato de teléfono no válido.";

    public static String emptyPhoneErrorMessage = "Phone number is required";

    public static String secondStepErrorMessage = /*"Please select an option from the dropdown list."*/ "يرجى اختيار خيار من القائمة المنسدلة.";

    public static String secondStepErrorMessageEs = "Por favor, selecciona una opción del menú desplegable.";

    public static String sameFirstNameErrorMessageKapitalRS = "Vaše ime mora da se razlikuje od vašeg prezimena.";

    public static String sameLastNameErrorMessageKapitalRS = "Vaše ime mora da se razlikuje od vašeg prezimena.";

    public static String firstNameErrorMessageKapitalRS = "Ime nije uneto u ispravnom formatu";

    public static String lastNameErrorMessageKapitalRS = "Prezime nije uneto u ispravnom formatu";

    public static String emailErrorMessageKapitalRS = "Nevažeći format imejla.";

    public static String wrongPhoneErrorMsgKapitalRS = "Nevažeći format telefona.";

    public static String secondStepErrorMessageKapitalRS = "Molimo Vas izaberite odgovarajuću opciju iz padajuće liste.";


    public static String headerPrivacyPolicyUrlKapitalRS = "https://www.kapitalrs.com/fortrade-ltd/politika-o-zastiti-privatnosti/";
    public static String headerTermsAndConditionsUrlKapitalRS = "https://www.kapitalrs.com/fortrade-ltd/pravila-i-uslovi/";
    public static String clickHereUrlKapitalRS = "https://www.fortrade.com/wp-content/uploads/legal/How_to_guides/How_to_unsubscribe.pdf";
    public static String alreadyHaveAnAccountUrlKapitalRS = "https://authfe.kapitalrs.com/oauth/account/login";
    public static String footerPrivacyPolicyUrlKapitalRS = "https://www.kapitalrs.com/fortrade-ltd/politika-o-zastiti-privatnosti/";
    public static String footerRiskWarningKapitalRS = "https://www.fortrade.com/wp-content/uploads/legal/FSC/Fortrade_MA_Risk_Disclosure.pdf";





    public static String kontaktirajteNasLink = "mailto:podrska@kapitalrs.com?subject=Client information request";

    //public static String privacyPolicyUrl = "https://www.fortrade.com/wp-content/uploads/legal/IIROC/Privacy_Policy.pdf";

    public static String fsgUrl = "https://www.fortrade.com/wp-content/uploads/legal/ASIC/Fort_Securities_AU_Financial_Services_Guide-ASIC.pdf";

    public static String pdsUrl = "https://www.fortrade.com/wp-content/uploads/legal/ASIC/Fort_Securities_AU_Product_Disclosure_Statement-ASIC.pdf";

    public static String tmdUrl = "https://www.fortrade.com/wp-content/uploads/legal/ASIC/Fort_Securities_AU-TMD_Policy.pdf";

    public static String privacyPolicyUrlIiroc = "https://www.fortrade.com/wp-content/uploads/legal/IIROC/Privacy_Policy.pdf";

    public static String termsAndConditionsUrlIiroc = "https://www.fortrade.com/wp-content/uploads/legal/IIROC/Client_Agreement.pdf";

    public static String privacyPolicyUrl = "https://www.fortrade.com/wp-content/uploads/legal/FSC/Fortrade_MA_Privacy_Policy.pdf";

    public static String termsAndConditionsUrl = "https://www.fortrade.com/wp-content/uploads/legal/FSC/Fortrade_Mauritius_Client_Agreement.pdf";

    public static String alreadyHaveAnAccountUrl = "https://authfe.fortrade.com/oauth/account/login";

    public static String riskWarningUrl = "https://www.fortrade.com/wp-content/uploads/legal/IIROC/Risk_Disclosure.pdf";

    public static String riskWarningKapitalRS = "https://www.fortrade.com/wp-content/uploads/legal/FSC/Fortrade_MA_Risk_Disclosure.pdf";

    public static String fcaUrl = "https://register.fca.org.uk/s/firm";

    public static String iirocUrl = "https://www.ciro.ca/investors/choosing-investment-advisor/dealers-we-regulate/fortrade-canada-limited";

    public static String asicUrl = "https://connectonline.asic.gov.au/RegistrySearch/faces/landing/panelSearch.jspx?";

    public static String cysecUrl = "https://www.cysec.gov.cy/en-GB/entities/investment-firms/cypriot/86639/";

    public static String fscUrl = "https://opr.fscmauritius.org/ords/opr/r/fsc-opr/fsc-online-public-register-opr";

    public static String dfsaUrl = "https://www.dfsa.ae/public-register/firms/fortrade-difc-limited";

    public static String contactUsUrl = "mailto:support@fortrade.com?subject";
    public static String contactUsUrlKapitalRS = "mailto:podrska@kapitalrs.com?subject";

    public static String supportUrl = "mailto:support@fortrade.com";

    public static String infoUrl = "mailto:info@fortrade.com";

    public static String supportUrlEs = "mailto:support@fortrade.com";

    public static String yopmailUrl ="https://yopmail.com/";

    public static String clickHereUrl = "https://www.fortrade.com/wp-content/uploads/legal/How_to_guides/How_to_unsubscribe.pdf";

    public static String fortraderprivacyPolicy = "https://www.fortrade.com/wp-content/uploads/legal/FSC/Fortrade_MA_Privacy_Policy.pdf";

    public static String fortraderPrivacyPolicyUrl = "https://www.fortrade.com/wp-content/uploads/legal/FSC/Fortrade_MA_Privacy_Policy.pdf";

    public static String fortraderTermsAndCondUrl = "https://www.fortrade.com/wp-content/uploads/legal/FSC/Fortrade_Mauritius_Client_Agreement.pdf";

    public static String fortraderClickHereURL = "https://www.fortrade.com/wp-content/uploads/legal/How_to_guides/How_to_unsubscribe.pdf";
}