package Tasks;

import io.github.bonigarcia.wdm.WebDriverManager;
import manager.PageFactoryManager;
import net.bytebuddy.utility.RandomString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import pages.CorePage;
import pages.DemoShopCheckout;
import pages.DemoWebShopPage;

import java.time.Duration;
import java.util.Properties;

public class TaskTwoTests {
    private final Logger log = LogManager.getRootLogger();
    private final Properties PROPERTIES = PageFactoryManager.getPROPERTIES();

    private final Duration DEFAULT_WAITING_TIME = Duration.ofSeconds(30);
    private CorePage corePage;
    private PageFactoryManager pageFactoryManager;
    private DemoWebShopPage demoWebShopPage;


    @BeforeAll
    public static void profileSetUp() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void testsSetUp() {
        log.info("Next Test initiated");
        pageFactoryManager = new PageFactoryManager();
        demoWebShopPage = pageFactoryManager.getDemoWebShopPage();
        corePage = pageFactoryManager.getCorePage();
        pageFactoryManager.getDriver().manage().window().maximize();
        pageFactoryManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
    }

    @AfterEach
    public void tearDown() {
        pageFactoryManager.getDriver().close();
    }


    @Test
    @DisplayName("1. Verify that allows register a User")
    public void checkRegistration() {
        corePage.openPage(PROPERTIES.getProperty("taskTwoHP") + "register");
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        String mailName = RandomString.make(5);
        demoWebShopPage.clickGenderRadioButton();
        demoWebShopPage.getFirstNameField().sendKeys("asdasd");
        demoWebShopPage.getLastNameField().sendKeys("asdasd");
        demoWebShopPage.getEmailField().sendKeys(mailName + "@mail.mail");
        demoWebShopPage.getPasswordField().sendKeys("asdasd");
        demoWebShopPage.getPasswordFieldConfirm().sendKeys("asdasd");
        demoWebShopPage.getRegistrationSubmit().click();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertEquals(
                PROPERTIES.getProperty("demoRegistrationRedirect"), pageFactoryManager.getDriver().getCurrentUrl(), "Redirect after registration was not executed"
        );
        Assertions.assertTrue(
                demoWebShopPage.getRegistrationCompleteTitle().isDisplayed(), "No congratulation message after registration is displayed"
        );
        log.info("registration test executed");
    }

    @Test
    @DisplayName("2. Verify that allows login a User")
    public void checkLogin() {
        corePage.openPage(PROPERTIES.getProperty("demoLoginPage"));
        demoWebShopPage.getEmailField().sendKeys("asdasd@asd.asdasd");
        demoWebShopPage.getPasswordField().sendKeys("asdasd");
        demoWebShopPage.getLoginButton().click();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(
                demoWebShopPage.getProfileLink().isDisplayed(), "Login was unsuccessful"
        );
        log.info("logging in test executed");
    }

    @Test
    @DisplayName("3. Verify that ‘Computers’ group has 3 sub-groups with correct names")
    public void checkComputersGroupSelection() {
        corePage.openPage(PROPERTIES.getProperty("demoComputersPage"));
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        String[] subCategories = {"Desktops", "Notebooks", "Accessories"};
        int count = 0;
        for (WebElement el : demoWebShopPage.getSubCategoryList()) {
            for (String str : subCategories) {
                if (el.getText().equals(str)) {
                    count++;
                    break;
                }
            }
        }
        Assertions.assertEquals(3, count, "One or more subcategory missing from Computer category");
        log.info("subcategory test executed");
    }

    @Test
    @DisplayName("4. Verify that allows sorting items (different options)")
    public void checkSortingProducts() {
        corePage.openPage(PROPERTIES.getProperty("demoDesktopCatalogue"));
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);

        //check sorting with setting via option selection
        demoWebShopPage.getOrderByDropdown().click();
        demoWebShopPage.getSortOptionsSorting().get(4).click();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        for (int i = 0; i < demoWebShopPage.getPriceList().size() - 1; i++) {
            Assertions.assertTrue(Integer.parseInt(demoWebShopPage.getPriceList().get(i).getText()) < Integer.parseInt(demoWebShopPage.getPriceList().get(i + 1).getText()), "sorting is not correct or not applied");
        }

        //check sorting with setting via URL
        corePage.openPage("https://demowebshop.tricentis.com/accessories?orderby=10");
        for (int i = 0; i < demoWebShopPage.getPriceList().size() - 1; i++) {
            Assertions.assertTrue(Integer.parseInt(demoWebShopPage.getPriceList().get(i).getText()) > Integer.parseInt(demoWebShopPage.getPriceList().get(i + 1).getText()), "sorting is not correct or not applied");
        }
        log.info("sorting test executed");
    }

    @Test
    @DisplayName("5. Verify that allows changing number of items on page")
    public void checkNumberOnPageChange() {
        corePage.openPage(PROPERTIES.getProperty("demoAccessories"));
        demoWebShopPage.getPagesizeDropdown().click();
        demoWebShopPage.getSortOptionsProductAmount().get(0).click();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(4 >= demoWebShopPage.getProductList().size(), "More than 4 items shown for \"4\" page size");
        demoWebShopPage.getSortOptionsProductAmount().get(1).click();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(8 >= demoWebShopPage.getProductList().size(), "More than 8 items shown for \"8\" page size");
        log.info("page size test executed");
    }

    @Test
    @DisplayName("6. Verify that allows adding an item to the Wishlist")
    public void checkWishlistAdd() {
        corePage.openPage(PROPERTIES.getProperty("demoItemToWishlist"));
        demoWebShopPage.getAddToWishlistButton().click();
        String product = demoWebShopPage.getProductTitle().getText();
        demoWebShopPage.getWishlistLink().click();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        pageFactoryManager.getDriver().navigate().refresh();
        boolean itemAdded = false;
        for (WebElement item : demoWebShopPage.getWishlistCartItems()) {
            if (item.getAttribute("innerText").equals(product)) {
                itemAdded = true;
                break;
            }
        }
        Assertions.assertTrue(itemAdded, "Item was not added to wishlist");
        log.info("adding to wishlist test executed");
    }

    @Test
    @DisplayName("7. Verify that allows adding an item to the card")
    public void checkAddToCart() {
        corePage.openPage(PROPERTIES.getProperty("demoTestLaptop"));
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        demoWebShopPage.getAddToCartButton().click();
        String product = demoWebShopPage.getProductTitle().getText();
        demoWebShopPage.getHeaderCartLink().click();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        pageFactoryManager.getDriver().navigate().refresh();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        boolean itemAdded = false;
        for (WebElement item : demoWebShopPage.getBasketItems()) {
            if (item.getAttribute("innerText").equals(product)) {
                itemAdded = true;
                break;
            }
        }
        Assertions.assertTrue(itemAdded, "Item was not added to basket");
        log.info("adding to basket test executed");
    }

    @Test
    @DisplayName("8. Verify that allows removing an item from the card")
    public void checkRemoveItemFromCart() {
        checkAddToCart();
        demoWebShopPage.getItemQuantityField().clear();
        demoWebShopPage.getItemQuantityField().sendKeys("0");
        demoWebShopPage.getUpdateCardButton().click();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(demoWebShopPage.getCartSummary().getText().contains("Your Shopping Cart is empty!"), "Item was not removed from the cart");
        log.info("removing from basket test executed");
    }

    @Test
    @DisplayName("9. Verify that allows checkout an item ")
    public void checkCheckoutFlow() {
        checkLogin();
        checkAddToCart();
        DemoShopCheckout demoShopCheckout = pageFactoryManager.getDemoShopCheckout();
        corePage.clickWR(demoShopCheckout.getTermsOfServiceCheckbox());
        corePage.clickWR(demoShopCheckout.getCheckoutButton());
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        corePage.clickWR(demoShopCheckout.getAddressSelect());
        corePage.clickWR(demoShopCheckout.getListOfAddresses().get(1));
        corePage.clickWR(demoShopCheckout.getCountriesListDropdown());
        corePage.clickWR(demoShopCheckout.getListOfCountries().get(1));
        demoShopCheckout.getCity().sendKeys("city");
        demoShopCheckout.getAddressOne().sendKeys("address");
        demoShopCheckout.getZipPost().sendKeys("010101");
        demoShopCheckout.getPhoneNumber().sendKeys("+499999999");
        for (int i = 0; i < 5; i++) {
            corePage.clickWR(demoShopCheckout.getContinueButtons().get(i));
        }
        corePage.clickWR(demoShopCheckout.getSubmitCheckoutButton());
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(demoShopCheckout.getCompletedOrderTitle().isDisplayed());
        log.info("checkout flow test executed");

    }


}
