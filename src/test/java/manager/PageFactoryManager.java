package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import pages.CorePage;
import pages.DemoShopCheckout;
import pages.DemoWebShopPage;
import pages.EpamPages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class PageFactoryManager {
    private static final Properties PROPERTIES;

    static {
        try {
            PROPERTIES = PropertyManager.getPropertiesInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private WebDriver driver;

    public PageFactoryManager() {
        switch (PROPERTIES.getProperty("browser")) {
            case "ch" -> {

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("download.default_directory", PROPERTIES.getProperty("downloadPath"));
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("prefs", prefs);
                driver = new ChromeDriver(options);
            }
            case "ff" -> {

                FirefoxProfile fxProfile = new FirefoxProfile();
                fxProfile.setPreference("browser.download.dir", PROPERTIES.getProperty("downloadPath"));
                FirefoxOptions option = new FirefoxOptions();
                option.setProfile(fxProfile);
                driver = new FirefoxDriver(option);
            }
        }
    }

    public EpamPages getEpamHomePage() {
        return new EpamPages(driver);
    }

    public static Properties getPROPERTIES() {
        return PROPERTIES;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public CorePage getCorePage() {
        return new CorePage(driver);

    }

    public DemoWebShopPage getDemoWebShopPage() {
        return new DemoWebShopPage(driver);
    }

    public DemoShopCheckout getDemoShopCheckout() {
        return new DemoShopCheckout(driver);
    }
}