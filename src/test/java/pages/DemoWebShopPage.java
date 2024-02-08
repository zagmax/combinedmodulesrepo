package pages;

import lombok.extern.log4j.Log4j2;
import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class DemoWebShopPage {


    public DemoWebShopPage(WebDriver driver) {
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
    private WebElement updateCardButton;

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

    public void clickUpdateCardButton() {
        updateCardButton.click();
    }

    public void clickWishlistLink() {
        log.info("click add to wishlist");
        wishlistLink.click();
    }

    public boolean checkIfItemAdded(String product){
        log.info("checking if item added to cart");
        for (WebElement item : getWishlistCartItems()) {
            if (item.getAttribute("innerText").equals(product)) {
                return true;
            }
        }
        return false;
    }
    public void clickHeaderCartLink() {
        log.info("clicking cart button");
        headerCartLink.click();
    }

    public void clickAddToCartButton() {
        log.info("clicking add to cart button");
        addToCartButton.click();
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

    public String getProductTitle() {
        return productTitle.getText();
    }

    public void clickAddToWishlistButton() {
        addToWishlistButton.click();
    }

    public List<WebElement> getProductList() {
        return productList;
    }

    public WebElement getPagesizeDropdown() {
        return pagesizeDropdown;
    }
    public void selectPageSize(int size){
        log.info("changing amount of products shown");
        getPagesizeDropdown().click();
        getSortOptionsProductAmount().get(size).click();
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
    public void selectOrderBySort(int num){
        log.info("select order by sort");
        getOrderByDropdown().click();
        getSortOptionsSorting().get(num).click();
    }
    public boolean checkSortingOrder(String direction){
        log.info("checking sort by "+direction+" price");
        for (int i = 0; i < getPriceList().size() - 1; i++) {
            if(direction.equals("asc")&&Integer.parseInt(getPriceList().get(i).getText()) < Integer.parseInt(getPriceList().get(i + 1).getText())){
                return false;
            }
            if(direction.equals("desc")&&Integer.parseInt(getPriceList().get(i).getText()) > Integer.parseInt(getPriceList().get(i + 1).getText())){
                return false;
            }
        }
        return true;
    }
    public WebElement getLoginButton() {
        return loginButton;
    }

    public void clickGenderRadioButton() {
        log.info("click gender radiobtn");
        genderRadioButton.click();
    }

    public WebElement getFirstNameField() {
        return firstNameField;
    }
    public void fillRegistrationFields(){
        log.info("filling fields for registration");
        String mailName = RandomString.make(5);
        getFirstNameField().sendKeys("asdasd");
        getLastNameField().sendKeys("asdasd");
        getEmailField().sendKeys(mailName + "@mail.mail");
        getPasswordField().sendKeys("asdasd");
        getPasswordFieldConfirm().sendKeys("asdasd");
    }

    public boolean isRegistrationCompleteTitleShown() {
        log.info("check registration completion");
        return registrationCompleteTitle.isDisplayed();
    }
    public void loginToDemoWebShop(){
        log.info("logging in with credentials");
        getEmailField().sendKeys("asdasd@asd.asdasd");
        getPasswordField().sendKeys("asdasd");
        getLoginButton().click();
    }

    public boolean isProfileLinkShown() {
        log.info("check display of profile link button");
        return profileLink.isDisplayed();
    }
    public int checkSubcategoryList(String[] list){
        log.info("checking subcategory list");
        int count = 0;
        for (WebElement el : getSubCategoryList()) {
            for (String str : list) {
                if (el.getText().equals(str)) {
                    count++;
                    break;
                }
            }
        }
        return count;
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

    public void clickRegistrationSubmit() {
        log.info("clicking confirm btn");
        registrationSubmit.click();
    }

}
