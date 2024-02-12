package pages;

import lombok.extern.log4j.Log4j2;
import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class DemoWebShopPage extends CorePage {


    public DemoWebShopPage(WebDriver driver) {
        super(driver);
        log.info("initializing demo web shop page instance");
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".registration-page [type=\"submit\"]")
    private WebElement registrationSubmit;

    @FindBy(css = "[id=\"gender-male\"]")
    private WebElement genderRadioButton;

    @FindBy(css = "[id=\"FirstName\"]")
    private WebElement firstNameField;

    @FindBy(css = "[id=\"LastName\"]")
    private WebElement lastNameField;

    @FindBy(css = "[id=\"Email\"]")
    private WebElement emailField;

    @FindBy(css = "[id=\"Password\"]")
    private WebElement passwordField;

    @FindBy(css = "[id=\"ConfirmPassword\"]")
    private WebElement passwordFieldConfirm;

    @FindBy(xpath = "//*[contains(text(),\"Your registration completed\")]")
    private WebElement registrationCompleteTitle;

    @FindBy(xpath = "//*[@class=\"header-links\"]//*[contains(text(),\"asdasd@asd.asdasd\")]")
    private WebElement profileLink;

    @FindBy(css = "[value=\"Log in\"]")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@class=\"item-box\"]//*[@class=\"title\"]/a")
    private List<WebElement> subCategoryList;

    @FindBy(css = "[id=\"products-orderby\"]")
    private WebElement orderByDropdown;

    @FindBy(css = "[id=\"products-pagesize\"]")
    private WebElement pagesizeDropdown;
    @FindBy(xpath = "//*[@id=\"products-orderby\"]//option")
    private List<WebElement> sortOptionsSorting;

    @FindBy(xpath = "//*[@id=\"products-pagesize\"]//option")
    private List<WebElement> sortOptionsProductAmount;
    @FindBy(css = "actual-price")
    private List<WebElement> priceList;

    @FindBy(css = ".product-item")
    private List<WebElement> productList;

    @FindBy(css = "[value=\"Add to wishlist\"]")
    private WebElement addToWishlistButton;

    @FindBy(css = "[itemprop=\"name\"]")
    private WebElement productTitle;

    @FindBy(css = ".header-links [class=\"ico-wishlist\"]")
    private WebElement wishlistLink;
    @FindBy(css = ".header [id=topcartlink]")
    private WebElement headerCartLink;
    @FindBy(css = ".cart .product")
    private List<WebElement> wishlistCartItems;

    /*
        Yes, basketItems is a dupe of wishlistCartItems, it was caused by
        demo website having very similar namings and structure for basket
        and wishlist pages, but as this issue usually avoided on real sites,
        this variable was created to distinguish the use of those two items
        within test cases
    */
    @FindBy(css = ".cart .product")
    private List<WebElement> basketItems;
    @FindBy(css = ".add-to-cart [type=\"button\"]")
    private WebElement addToCartButton;

    @FindBy(css = "[class=\"qty nobr\"] input")
    private WebElement itemQuantityField;

    @FindBy(css = "[name=updatecart]")
    private WebElement updateCartButton;

    @FindBy(css = "[class=order-summary-content]")
    private WebElement cartSummary;

    public boolean checkCartSummaryContains(String str) {
        return cartSummary.getText().contains(str);
    }

    public void setItemQuantityField(String amount) {
        log.info("setting item quantity");
        itemQuantityField.clear();
        itemQuantityField.sendKeys(amount);
    }

    public void clickUpdateCartButton() {
        log.info("updating cart");
        updateCartButton.click();
        waitForPageLoadComplete();
    }

    public void emptyBasket() {
        log.info("emptying basket");
        setItemQuantityField("0");
        clickUpdateCartButton();
        waitForPageLoadComplete();
    }

    public void openWishlists() {
        log.info("click add to wishlist");
        wishlistLink.click();
        waitForPageLoadComplete();
        refresh();
    }

    public boolean checkIfItemAdded(String product) {
        log.info("checking if item added to cart");
        for (WebElement item : wishlistCartItems) {
            if (item.getAttribute("innerText").equals(product)) {
                return true;
            }
        }
        return false;
    }

    public void openBasket() {
        log.info("clicking cart button");
        headerCartLink.click();
        waitForPageLoadComplete();
        refresh();
    }

    public void addToCart() {
        log.info("clicking add to cart button");
        addToCartButton.click();
    }

    public String getProductTitle() {
        return productTitle.getText();
    }

    public void addToWishlist() {
        addToWishlistButton.click();
    }

    public List<WebElement> getProductList() {
        return productList;
    }

    public void selectPageSize(int size) {
        log.info("changing amount of products shown");
        pagesizeDropdown.click();
        sortOptionsProductAmount.get(size).click();
        waitForPageLoadComplete();
    }

    public List<WebElement> getPriceList() {
        return priceList;
    }

    public void selectOrderBySort(int num) {
        log.info("select order by sort");
        orderByDropdown.click();
        sortOptionsSorting.get(num).click();
        waitForPageLoadComplete();
    }

    public boolean checkSortingOrder(String direction) {
        log.info("checking sort by " + direction + " price");
        for (int i = 0; i < getPriceList().size() - 1; i++) {
            if (direction.equals("asc") && Integer.parseInt(getPriceList().get(i).getText()) < Integer.parseInt(getPriceList().get(i + 1).getText())) {
                return false;
            }
            if (direction.equals("desc") && Integer.parseInt(getPriceList().get(i).getText()) > Integer.parseInt(getPriceList().get(i + 1).getText())) {
                return false;
            }
        }
        return true;
    }

    public void completeRegistration() {
        log.info("click gender radiobtn");
        genderRadioButton.click();

        log.info("filling fields for registration");
        String mailName = RandomString.make(5);
        firstNameField.sendKeys("asdasd");
        lastNameField.sendKeys("asdasd");
        emailField.sendKeys(mailName + "@mail.mail");
        passwordField.sendKeys("asdasd");
        passwordFieldConfirm.sendKeys("asdasd");

        log.info("clicking confirm btn");
        registrationSubmit.click();
        waitForPageLoadComplete();
    }

    public boolean isRegistrationCompleteTitleShown() {
        log.info("check registration completion");
        return registrationCompleteTitle.isDisplayed();
    }

    public void loginToDemoWebShop() {
        log.info("logging in with credentials");
        emailField.sendKeys("asdasd@asd.asdasd");
        passwordField.sendKeys("asdasd");
        loginButton.click();
        waitForPageLoadComplete();
    }

    public boolean isProfileLinkShown() {
        log.info("check display of profile link button");
        return profileLink.isDisplayed();
    }

    public boolean checkSubcategoryList(List<String> list) {
        log.info("checking subcategory list");
        return subCategoryList.stream().map(WebElement::getText).collect(Collectors.toList()).containsAll(list);
    }
}
