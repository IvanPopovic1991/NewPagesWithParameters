package core.base;

import core.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {

    protected WebDriver driver;

    public BasePage() {

        this.driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

}
