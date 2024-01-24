package Tasks;

import io.github.bonigarcia.wdm.WebDriverManager;
import jdk.jfr.Name;
import manager.PageFactoryManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.BasePageEpam;
import pages.HomePageEpam;

import java.time.Duration;

public class TaskTwo {
    private static final String TESTING_SITE = "https://demowebshop.tricentis.com/";
    private String browserType = "ff";
    private WebDriver driver;
    private JavascriptExecutor js;
    private final Duration DEFAULT_WAITING_TIME = Duration.ofSeconds(30);
    private HomePageEpam homePage;
    private BasePageEpam basePage;
    private PageFactoryManager pageFactoryManager;

    @BeforeClass
    public void profileSetUp() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    @Before
    public void testsSetUp() {
        switch (browserType) {
            case "ch" -> driver = new ChromeDriver();
            case "ff" -> driver = new FirefoxDriver();
        }
        js = (JavascriptExecutor) driver;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
        driver.get("https://www.epam.com/");
        pageFactoryManager = new PageFactoryManager(driver);
        basePage = pageFactoryManager.getBasePage();
        homePage = pageFactoryManager.getHomePage();
    }


    @Test
    @Name("1. Verify that allows register a User")
    public void checkRegistration() {

    }
    @Test
    @Name("2. Verify that allows login a User")
    public void checkLogin() {

    }
    @Test
    @Name("3. Verify that ‘Computers’ group has 3 sub-groups with correct names")
    public void checkComputersGroupSelection() {

    }
    @Test
    @Name("4. Verify that allows sorting items (different options)")
    public void checkSortingProducts() {

    }
    @Test
    @Name("5. Verify that allows changing number of items on page")
    public void checkNumberOnPageChange() {

    }
    @Test
    @Name("6. Verify that allows adding an item to the Wishlist")
    public void checkWishlistAdd() {

    }
    @Test
    @Name("7. Verify that allows adding an item to the card")
    public void checkAddToCart() {

    }
    @Test
    @Name("8. Verify that allows removing an item from the card")
    public void checkRemoveItemFromCart() {

    }
    @Test
    @Name("9. Verify that allows checkout an item ")
    public void checkCheckoutFlow() {

    }
}
