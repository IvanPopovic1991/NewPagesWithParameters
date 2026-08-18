package core.localization;

public class UrlProvider {

    private static String currentUrl;

    private UrlProvider() {
    }

    // Stores the current URL.
    public static void initialize(String url) {
        currentUrl = url;
    }

    // Returns the current URL.
    public static String getCurrentUrl() {
        return currentUrl;
    }

    // Returns the language from the current URL.
    public static String getLanguage() {
        return getLanguage(currentUrl);
    }

    // Extracts the language code from the given URL.
    public static String getLanguage(String url) {

        url = url.split("\\?")[0];
        url = url.replaceAll("/$", "");

        String[] parts = url.split("/");

        return parts[parts.length - 1].toLowerCase();
    }

    // Checks whether the current URL belongs to an E-phone page.
    public static boolean isEphone() {
        return currentUrl.contains("-ephone-");
    }
}