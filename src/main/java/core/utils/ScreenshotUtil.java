package core.utils;

import core.driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {

    private static ThreadLocal<String> customName = new ThreadLocal<>();

    public static void setCustomName(String name) {
        customName.set(name);
    }

    public static String getCustomName() {
        return customName.get();
    }

    public static void clearCustomName() {
        customName.remove();
    }

    public static String captureScreenshot(String name, String status) {

        String custom = getCustomName();

        if (custom != null && !custom.isEmpty()) {
            name = custom + "_" + status;
        }

        TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);

        String folderPath = "screenshots/" + status.toLowerCase();
        new File(folderPath).mkdirs();

        File destination = new File(folderPath + "/" + name + ".png");

        try {
            FileHandler.copy(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }

        clearCustomName();

        return destination.getAbsolutePath();
    }
}