package manager;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import pages.DemoShopCheckout;
import pages.DemoWebShopPage;
import pages.EpamPages;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Log4j2
public class PageFactoryManager {
    private static final Properties PROPERTIES;

    static {
        try {
            PROPERTIES = PropertyManager.getPropertiesInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private final WebDriver driver;

    public PageFactoryManager() {
        if ("firefox".equals((System.getProperty("browser") == null ? "" : System.getProperty("browser")))) {
            log.info("creating Firefox driver");
            WebDriverManager.firefoxdriver().setup();
            FirefoxProfile fxProfile = new FirefoxProfile();
            fxProfile.setPreference("browser.download.dir", PROPERTIES.getProperty("downloadPath"));
            FirefoxOptions option = new FirefoxOptions();
            option.setProfile(fxProfile);
            driver = new FirefoxDriver(option);
        } else {
            log.info("creating Chrome driver");
            WebDriverManager.chromedriver().setup();
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("download.default_directory", PROPERTIES.getProperty("downloadPath"));
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", prefs);
            driver = new ChromeDriver(options);
        }
        log.info("setting browser");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
    }

    public EpamPages getEpamHomePage() {
        log.info("creating epam page instance");
        return new EpamPages(driver);
    }

    public static Properties getPROPERTIES() {
        return PROPERTIES;
    }

    public void browserClose() {
        log.info("closing browser");
        driver.close();
    }

    public DemoWebShopPage getDemoWebShopPage() {
        log.info("creating demo shop page instance");
        return new DemoWebShopPage(driver);
    }

    public DemoShopCheckout getDemoShopCheckout() {
        log.info("creating demo shop checkout page instance");
        return new DemoShopCheckout(driver);
    }
}