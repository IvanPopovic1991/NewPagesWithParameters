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

}