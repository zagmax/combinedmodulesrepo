package Tasks;

import io.github.bonigarcia.wdm.WebDriverManager;
import jdk.jfr.Name;
import manager.PageFactoryManager;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.BasePageEpam;
import pages.DemoShopCheckout;
import pages.DemoWebShopPage;

import java.time.Duration;
import java.util.List;

public class TaskTwoTests {
    private static final String TESTING_SITE = "https://demowebshop.tricentis.com/";
    private String browserType = "ch";
    private WebDriver driver;
    private final Duration DEFAULT_WAITING_TIME = Duration.ofSeconds(30);
    private DemoShopCheckout demoShopCheckout;
    private BasePageEpam basePage;
    private PageFactoryManager pageFactoryManager;
    private DemoWebShopPage demoWebShopPage;


    @BeforeAll
    public static void profileSetUp() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void testsSetUp() {
        switch (browserType) {
            case "ch" -> driver = new ChromeDriver();
            case "ff" -> driver = new FirefoxDriver();
        }
        pageFactoryManager = new PageFactoryManager(driver);
        demoWebShopPage = pageFactoryManager.getDemoWebShopPage();
        basePage = pageFactoryManager.getBasePage();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
    }


    @Test
    @Name("1. Verify that allows register a User")
    public void checkRegistration() {
        driver.get(TESTING_SITE + "register");
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        String mailName = RandomString.make(5);
        driver.findElement(new By.ByCssSelector("[id=\"gender-male\"]")).click();
        driver.findElement(new By.ByCssSelector("[id=\"FirstName\"]")).sendKeys("asdasd");
        driver.findElement(new By.ByCssSelector("[id=\"LastName\"]")).sendKeys("asdasd");
        driver.findElement(new By.ByCssSelector("[id=\"Email\"]")).sendKeys(mailName + "@mail.mail");
        driver.findElement(new By.ByCssSelector("[id=\"Password\"]")).sendKeys("asdasd");
        driver.findElement(new By.ByCssSelector("[id=\"ConfirmPassword\"]")).sendKeys("asdasd");
        demoWebShopPage.getRegistrationSubmit().click();
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertEquals("https://demowebshop.tricentis.com/registerresult/1", driver.getCurrentUrl());
        Assertions.assertTrue(driver.findElement(By.xpath("//*[contains(text(),\"Your registration completed\")]")).isDisplayed());
    }

    @Test
    @Name("2. Verify that allows login a User")
    public void checkLogin() {
        driver.get("https://demowebshop.tricentis.com/login");
        driver.findElement(new By.ByCssSelector("[id=\"Email\"]")).sendKeys("asdasd@asd.asdasd");
        driver.findElement(new By.ByCssSelector("[id=\"Password\"]")).sendKeys("asdasd");
        driver.findElement(new By.ByCssSelector("[value=\"Log in\"]")).click();
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(driver.findElement(By.xpath("//*[@class=\"header-links\"]//*[contains(text(),\"asdasd@asd.asdasd\")]")).isDisplayed());
    }

    @Test
    @Name("3. Verify that ‘Computers’ group has 3 sub-groups with correct names")
    public void checkComputersGroupSelection() {
        driver.get("https://demowebshop.tricentis.com/computers");
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        String[] subCategories = {"Desktops", "Notebooks", "Accessories"};
        By path = By.xpath("//*[@class=\"item-box\"]//*[@class=\"title\"]/a");
        int count = 0;
        for (WebElement el : driver.findElements(path)) {
            for (String str : subCategories) {
                if (el.getText().equals(str)) {
                    count++;
                    break;
                }
            }
        }
        Assertions.assertEquals(3, count);
    }

    @Test
    @Name("4. Verify that allows sorting items (different options)")
    public void checkSortingProducts() {
        driver.get("https://demowebshop.tricentis.com/desktops");
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        //check sorting with setting via option selection
        driver.findElement(new By.ByCssSelector("[id=\"products-orderby\"]")).click();
        driver.findElement(By.xpath("//*[contains(text(),\"Price: High to Low\")]")).click();
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        List<WebElement> listPrices = driver.findElements(By.xpath("actual-price"));
        for (int i = 0; i < listPrices.size() - 1; i++) {
            Assertions.assertTrue(Integer.parseInt(listPrices.get(i).getText()) < Integer.parseInt(listPrices.get(i + 1).getText()));
        }
        //check sorting with setting via URL
        driver.get("https://demowebshop.tricentis.com/accessories?orderby=10");
        listPrices = driver.findElements(By.xpath("actual-price"));
        for (int i = 0; i < listPrices.size() - 1; i++) {
            Assertions.assertTrue(Integer.parseInt(listPrices.get(i).getText()) > Integer.parseInt(listPrices.get(i + 1).getText()));
        }
    }

    @Test
    @Name("5. Verify that allows changing number of items on page")
    public void checkNumberOnPageChange() {
        driver.get("https://demowebshop.tricentis.com/accessories?pagesize=4");
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(4 >= driver.findElements(new By.ByCssSelector(".product-item")).size());
        driver.findElement(new By.ByCssSelector("[id=\"products-pagesize\"]")).click();
        driver.findElement(new By.ByCssSelector("[id=\"products-pagesize\"] :nth-child(2)")).click();
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(driver.findElements(new By.ByCssSelector(".product-item")).size() <= 8);
    }

    @Test
    @Name("6. Verify that allows adding an item to the Wishlist")
    public void checkWishlistAdd() {
        checkLogin();
        driver.get("https://demowebshop.tricentis.com/copy-of-copy-of-copy-of-copy-of-tcp-self-paced-training");
        driver.findElement(new By.ByCssSelector("[value=\"Add to wishlist\"]")).click();
        String product = driver.findElement(new By.ByCssSelector("[itemprop=\"name\"]")).getText();
        driver.findElement(new By.ByCssSelector(".header-links [class=\"ico-wishlist\"]")).click();
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertEquals(product, driver.findElement(new By.ByCssSelector(".cart .product")).getAttribute("innerText"));
    }

    @Test
    @Name("7. Verify that allows adding an item to the card")
    public void checkAddToCart() {
        driver.get("https://demowebshop.tricentis.com/141-inch-laptop");
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        driver.findElement(new By.ByCssSelector(".add-to-cart [type=\"button\"]")).click();
        String product = driver.findElement(new By.ByCssSelector("[itemprop=\"name\"]")).getText();
        driver.findElement(new By.ByCssSelector(".header [id=topcartlink]")).click();
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        driver.navigate().refresh();
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertEquals(product, driver.findElement(new By.ByCssSelector(".cart .product")).getAttribute("innerText"));
    }

    @Test
    @Name("8. Verify that allows removing an item from the card")
    public void checkRemoveItemFromCart() {
        checkAddToCart();
        driver.findElement(new By.ByCssSelector("[class=\"qty nobr\"] input")).clear();
        driver.findElement(new By.ByCssSelector("[class=\"qty nobr\"] input")).sendKeys("0");
        driver.findElement(new By.ByCssSelector("[name=updatecart]")).click();
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(driver.findElement(new By.ByCssSelector("[class=order-summary-content]")).isDisplayed());
    }

    @Test
    @Name("9. Verify that allows checkout an item ")
    public void checkCheckoutFlow() {
        checkLogin();
        checkAddToCart();
        basePage.clickCSSElementWhenClickable("[id=termsofservice]");
        basePage.clickCSSElementWhenClickable("[class=\"checkout-buttons\"] [name=\"checkout\"]");
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        demoShopCheckout = pageFactoryManager.getDemoShopCheckout();
        basePage.clickCSSElementWhenClickable("[id=billing-address-select]");
        basePage.clickCSSElementWhenClickable("[id=billing-address-select] :nth-child(2)");
        basePage.clickCSSElementWhenClickable("[id=BillingNewAddress_CountryId]");
        basePage.clickCSSElementWhenClickable("[id=BillingNewAddress_CountryId] [value=\"2\"]");
        demoShopCheckout.getCity().sendKeys("city");
        demoShopCheckout.getAddressOne().sendKeys("address");
        demoShopCheckout.getZipPost().sendKeys("010101");
        demoShopCheckout.getPhoneNumber().sendKeys("+499999999");
        basePage.clickCSSElementWhenClickable("[id=billing-buttons-container] [value=Continue]");
        basePage.clickCSSElementWhenClickable("[id=shipping-buttons-container] [value=Continue]");
        basePage.clickCSSElementWhenClickable("[id=shipping-method-buttons-container] [value=Continue]");
        basePage.clickCSSElementWhenClickable("[id=payment-method-buttons-container] [value=Continue]");
        basePage.clickCSSElementWhenClickable("[id=payment-info-buttons-container] [value=Continue]");
        basePage.clickCSSElementWhenClickable("[value=Confirm]");
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(driver.findElement(new By.ByCssSelector(".order-completed .title")).isDisplayed());
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }
}
