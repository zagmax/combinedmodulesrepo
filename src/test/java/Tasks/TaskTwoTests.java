package Tasks;


import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import manager.PageFactoryManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CorePage;
import pages.DemoShopCheckout;
import pages.DemoWebShopPage;

import java.time.Duration;
import java.util.Properties;

@Log4j2
public class TaskTwoTests {
    private final Properties PROPERTIES = PageFactoryManager.getPROPERTIES();
    private final Duration DEFAULT_WAITING_TIME = Duration.ofSeconds(30);
    private CorePage corePage;
    private PageFactoryManager pageFactoryManager;
    private DemoWebShopPage demoWebShopPage;


    @BeforeAll
    public static void profileSetUp() {
        log.info("setup browser drivers");
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void testsSetUp() {
        log.info("Next Test initiated");
        pageFactoryManager = new PageFactoryManager();
        demoWebShopPage = pageFactoryManager.getDemoWebShopPage();
        corePage = pageFactoryManager.getCorePage();
        pageFactoryManager.browerConfiguration();
    }

    @AfterEach
    public void tearDown() {
        pageFactoryManager.browserClose();
    }


    @Test
    @DisplayName("1. Verify that allows register a User")
    public void checkRegistration() {
        corePage.openPage(PROPERTIES.getProperty("taskTwoHP") + "register");
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        demoWebShopPage.clickGenderRadioButton();
        demoWebShopPage.fillRegistrationFields();
        demoWebShopPage.clickRegistrationSubmit();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertEquals(
                PROPERTIES.getProperty("demoRegistrationRedirect"), corePage.getUrl(), "Redirect after registration was not executed"
        );
        Assertions.assertTrue(
                demoWebShopPage.isRegistrationCompleteTitleShown(), "No congratulation message after registration is displayed"
        );
        log.info("registration test executed");
    }

    @Test
    @DisplayName("2. Verify that allows login a User")
    public void checkLogin() {
        corePage.openPage(PROPERTIES.getProperty("demoLoginPage"));
        demoWebShopPage.loginToDemoWebShop();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(
                demoWebShopPage.isProfileLinkShown(), "Login was unsuccessful"
        );
        log.info("logging in test executed");
    }

    @Test
    @DisplayName("3. Verify that ‘Computers’ group has 3 sub-groups with correct names")
    public void checkComputersGroupSelection() {
        corePage.openPage(PROPERTIES.getProperty("demoComputersPage"));
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        String[] subCategories = {"Desktops", "Notebooks", "Accessories"};
        int count = demoWebShopPage.checkSubcategoryList(subCategories);
        Assertions.assertEquals(subCategories.length, count, "One or more subcategory missing from Computer category");
        log.info("subcategory test executed");
    }

    @Test
    @DisplayName("4. Verify that allows sorting items (different options)")
    public void checkSortingProducts() {
        corePage.openPage(PROPERTIES.getProperty("demoDesktopCatalogue"));
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);

        //check sorting with setting via option selection
        demoWebShopPage.selectOrderBySort(4);
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
            Assertions.assertTrue(demoWebShopPage.checkSortingOrder("asc"),
                            "sorting is not correct or not applied");

        //check sorting with setting via URL
        corePage.openPage("https://demowebshop.tricentis.com/accessories?orderby=10");
            Assertions.assertTrue(demoWebShopPage.checkSortingOrder("desc"),
                            "sorting is not correct or not applied");
                log.info("sorting test executed");
    }

    @Test
    @DisplayName("5. Verify that allows changing number of items on page")
    public void checkNumberOnPageChange() {
        corePage.openPage(PROPERTIES.getProperty("demoAccessories"));
        demoWebShopPage.selectPageSize(0);
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(4 >= demoWebShopPage.getProductList().size(), "More than 4 items shown for \"4\" page size");
        demoWebShopPage.selectPageSize(1);
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(8 >= demoWebShopPage.getProductList().size(), "More than 8 items shown for \"8\" page size");
        log.info("page size test executed");
    }

    @Test
    @DisplayName("6. Verify that allows adding an item to the Wishlist")
    public void checkWishlistAdd() {
        corePage.openPage(PROPERTIES.getProperty("demoItemToWishlist"));
        demoWebShopPage.clickAddToWishlistButton();
        String product = demoWebShopPage.getProductTitle();
        demoWebShopPage.clickWishlistLink();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        corePage.refresh();
        Assertions.assertTrue(demoWebShopPage.checkIfItemAdded(product), "Item was not added to wishlist");
        log.info("adding to wishlist test executed");
    }

    @Test
    @DisplayName("7. Verify that allows adding an item to the card")
    public void checkAddToCart() {
        corePage.openPage(PROPERTIES.getProperty("demoTestLaptop"));
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        demoWebShopPage.clickAddToCartButton();
        String product = demoWebShopPage.getProductTitle();
        demoWebShopPage.clickHeaderCartLink();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        corePage.refresh();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(demoWebShopPage.checkIfItemAdded(product), "Item was not added to basket");
        log.info("adding to basket test executed");
    }

    @Test
    @DisplayName("8. Verify that allows removing an item from the card")
    public void checkRemoveItemFromCart() {
        checkAddToCart();
        demoWebShopPage.setItemQuantityField("0");
        demoWebShopPage.clickUpdateCardButton();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(demoWebShopPage.checkCartSummaryContains("Your Shopping Cart is empty!"), "Item was not removed from the cart");
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
        corePage.clickWR(demoShopCheckout.getAddressSelect());
        corePage.clickWR(demoShopCheckout.getListOfAddresses().get(1));
        corePage.clickWR(demoShopCheckout.getCountriesListDropdown());
        corePage.clickWR(demoShopCheckout.getListOfCountries().get(1));
        demoShopCheckout.fillAddressSection();
        demoShopCheckout.goThroughCheckoutSections(corePage);
        corePage.clickWR(demoShopCheckout.getSubmitCheckoutButton());
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(demoShopCheckout.isCompletedOrderTitleShown());
        log.info("checkout flow test executed");
    }


}
