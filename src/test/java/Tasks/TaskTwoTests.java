package Tasks;


import lombok.extern.log4j.Log4j2;
import manager.PageFactoryManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.DemoShopCheckout;
import pages.DemoWebShopPage;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Log4j2
public class TaskTwoTests {
    private final Properties PROPERTIES = PageFactoryManager.getPROPERTIES();
    private PageFactoryManager pageFactoryManager;
    private DemoWebShopPage demoWebShopPage;

    @BeforeEach
    public void testsSetUp() {
        log.info("Next Test initiated");
        pageFactoryManager = new PageFactoryManager();
        demoWebShopPage = pageFactoryManager.getDemoWebShopPage();
    }

    @AfterEach
    public void tearDown() {
        pageFactoryManager.browserClose();
    }


    @Test
    @DisplayName("1. Verify that allows register a User")
    public void checkRegistration() {
        demoWebShopPage.openPage(PROPERTIES.getProperty("taskTwoHP") + "register");
        demoWebShopPage.completeRegistration();
        Assertions.assertEquals(
                PROPERTIES.getProperty("demoRegistrationRedirect"), demoWebShopPage.getUrl(), "Redirect after registration was not executed"
        );
        Assertions.assertTrue(
                demoWebShopPage.isRegistrationCompleteTitleShown(), "No congratulation message after registration is displayed"
        );
        log.info("registration test executed");
    }

    @Test
    @DisplayName("2. Verify that allows login a User")
    public void checkLogin() {
        demoWebShopPage.openPage(PROPERTIES.getProperty("demoLoginPage"));
        demoWebShopPage.loginToDemoWebShop();
        Assertions.assertTrue(
                demoWebShopPage.isProfileLinkShown(), "Login was unsuccessful"
        );
        log.info("logging in test executed");
    }

    @Test
    @DisplayName("3. Verify that ‘Computers’ group has 3 sub-groups with correct names")
    public void checkComputersGroupSelection() {
        demoWebShopPage.openPage(PROPERTIES.getProperty("demoComputersPage"));
        List<String> subCategories = Arrays.asList("Desktops", "Notebooks", "Accessories");
        Assertions.assertTrue(demoWebShopPage.checkSubcategoryList(subCategories), "One or more subcategory missing from Computer category");
        log.info("subcategory test executed");
    }

    @Test
    @DisplayName("4. Verify that allows sorting items (different options)")
    public void checkSortingProducts() {
        demoWebShopPage.openPage(PROPERTIES.getProperty("demoDesktopCatalogue"));

        //check sorting with setting via option selection
        demoWebShopPage.selectOrderBySort(4);
        Assertions.assertTrue(demoWebShopPage.checkSortingOrder("asc"),
                "sorting is not correct or not applied");

        //check sorting with setting via URL
        demoWebShopPage.openPage("https://demowebshop.tricentis.com/accessories?orderby=10");
        Assertions.assertTrue(demoWebShopPage.checkSortingOrder("desc"),
                "sorting is not correct or not applied");
        log.info("sorting test executed");
    }

    @Test
    @DisplayName("5. Verify that allows changing number of items on page")
    public void checkNumberOnPageChange() {
        demoWebShopPage.openPage(PROPERTIES.getProperty("demoAccessories"));
        demoWebShopPage.selectPageSize(0);
        Assertions.assertTrue(4 >= demoWebShopPage.getProductList().size(), "More than 4 items shown for \"4\" page size");
        demoWebShopPage.selectPageSize(1);
        Assertions.assertTrue(8 >= demoWebShopPage.getProductList().size(), "More than 8 items shown for \"8\" page size");
        log.info("page size test executed");
    }

    @Test
    @DisplayName("6. Verify that allows adding an item to the Wishlist")
    public void checkWishlistAdd() {
        demoWebShopPage.openPage(PROPERTIES.getProperty("demoItemToWishlist"));
        demoWebShopPage.addToWishlist();
        String product = demoWebShopPage.getProductTitle();
        demoWebShopPage.openWishlists();
        Assertions.assertTrue(demoWebShopPage.checkIfItemAdded(product), "Item was not added to wishlist");
        log.info("adding to wishlist test executed");
    }

    @Test
    @DisplayName("7. Verify that allows adding an item to the card")
    public void checkAddToCart() {
        demoWebShopPage.openPage(PROPERTIES.getProperty("demoTestLaptop"));
        demoWebShopPage.addToCart();
        String product = demoWebShopPage.getProductTitle();
        demoWebShopPage.openBasket();
        Assertions.assertTrue(demoWebShopPage.checkIfItemAdded(product), "Item was not added to basket");
        log.info("adding to basket test executed");
    }

    @Test
    @DisplayName("8. Verify that allows removing an item from the card")
    public void checkRemoveItemFromCart() {
        checkAddToCart();
        demoWebShopPage.emptyBasket();
        Assertions.assertTrue(demoWebShopPage.checkCartSummaryContains("Your Shopping Cart is empty!"), "Item was not removed from the cart");
        log.info("removing from basket test executed");
    }

    @Test
    @DisplayName("9. Verify that allows checkout an item ")
    public void checkCheckoutFlow() {
        DemoShopCheckout demoShopCheckout = pageFactoryManager.getDemoShopCheckout();
        checkLogin();
        checkAddToCart();
        demoShopCheckout.finishCheckoutFlow();
        Assertions.assertTrue(demoShopCheckout.isCompletedOrderTitleShown());
        log.info("checkout flow test executed");
    }


}
