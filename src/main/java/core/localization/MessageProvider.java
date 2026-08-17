package core.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageProvider {

    private static ResourceBundle bundle;

    private MessageProvider() {
    }

    // Determines the language from the URL and loads the corresponding message file.
    public static void initialize(String url) {

        String language = UrlProvider.getLanguage(url);

        bundle = ResourceBundle.getBundle(
                "lang.messages",
                new Locale(language));
    }

    // Returns the localized message for the given key.
    public static String get(String key) {

        if (bundle == null) {
            throw new IllegalStateException(
                    "MessageProvider is not initialized!");
        }

        return bundle.getString(key);
    }
}