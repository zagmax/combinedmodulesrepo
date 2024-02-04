package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DemoWebShopPage {


    public DemoWebShopPage(WebDriver driver) {
        //this.driver = driver;
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
    private WebElement updateCardButton;

    @FindBy(css = "[class=order-summary-content]")
    private WebElement cartSummary;

    public WebElement getCartSummary() {
        return cartSummary;
    }

    public WebElement getItemQuantityField() {
        return itemQuantityField;
    }

    public WebElement getUpdateCardButton() {
        return updateCardButton;
    }

    public WebElement getWishlistLink() {
        return wishlistLink;
    }

    public WebElement getHeaderCartLink() {
        return headerCartLink;
    }

    public WebElement getAddToCartButton() {
        return addToCartButton;
    }

    public List<WebElement> getBasketItems() {
        return basketItems;
    }

    public List<WebElement> getWishlistCartItems() {
        return wishlistCartItems;
    }

    public List<WebElement> getSortOptionsSorting() {
        return sortOptionsSorting;
    }

    public WebElement getProductTitle() {
        return productTitle;
    }

    public WebElement getAddToWishlistButton() {
        return addToWishlistButton;
    }

    public List<WebElement> getProductList() {
        return productList;
    }

    public WebElement getPagesizeDropdown() {
        return pagesizeDropdown;
    }

    public List<WebElement> getSortOptionsProductAmount() {
        return sortOptionsProductAmount;
    }

    public List<WebElement> getPriceList() {
        return priceList;
    }

    public List<WebElement> getSubCategoryList() {
        return subCategoryList;
    }

    public WebElement getOrderByDropdown() {
        return orderByDropdown;
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    public void clickGenderRadioButton() {
        genderRadioButton.click();
    }

    public WebElement getFirstNameField() {
        return firstNameField;
    }

    public WebElement getRegistrationCompleteTitle() {
        return registrationCompleteTitle;
    }

    public WebElement getProfileLink() {
        return profileLink;
    }

    public WebElement getLastNameField() {
        return lastNameField;
    }

    public WebElement getEmailField() {
        return emailField;
    }

    public WebElement getPasswordField() {
        return passwordField;
    }

    public WebElement getPasswordFieldConfirm() {
        return passwordFieldConfirm;
    }

    public WebElement getRegistrationSubmit() {
        return registrationSubmit;
    }

}
