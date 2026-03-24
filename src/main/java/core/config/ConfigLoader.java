package core.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static Properties properties;

    static {
        loadProperties();
    }

    private static void loadProperties() {

        String env = System.getProperty("env", "local");

        try {

            properties = new Properties();

            String fileName = "config/" + env + ".properties";

            InputStream input =
                    ConfigLoader.class
                            .getClassLoader()
                            .getResourceAsStream(fileName);

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config file");
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
