package core.config;

public class ConfigReader {

    public static String getBrowser() {
        return System.getProperty(
                "browser",
                ConfigLoader.get("browser")
        );
    }

    // default URL
    public static String getBaseUrl() {
        return System.getProperty(
                "baseUrl",
                ConfigLoader.get("base.url")
        );
    }

    // dynamic URL
    public static String getBaseUrl(String key) {
        return System.getProperty(
                key,
                ConfigLoader.get(key)
        );
    }
}
