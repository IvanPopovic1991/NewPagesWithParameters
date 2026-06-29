package core.utils;

import core.config.ConfigReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AllureEnvironmentWriter {

    public static void writeEnvironment() {

        try {

            Properties properties = new Properties();

            properties.setProperty(
                    "Browser",
                    ConfigReader.getBrowser()
            );

            properties.setProperty(
                    "Environment",
                    System.getProperty("env", "local")
            );

            properties.setProperty(
                    "OS",
                    System.getProperty("os.name")
            );

            File resultsDir = new File("allure-results");

            if (!resultsDir.exists()) {
                resultsDir.mkdirs();
            }

            try (FileOutputStream output =
                         new FileOutputStream(
                                 new File(resultsDir, "environment.properties")
                         )) {

                properties.store(output, "Allure Environment");
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to create environment.properties",
                    e
            );
        }
    }
}
