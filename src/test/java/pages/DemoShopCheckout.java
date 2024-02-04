package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DemoShopCheckout {
    public DemoShopCheckout(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[class=inputs] [id=BillingNewAddress_City]")
    private WebElement city;

    @FindBy(css = "[class=inputs] [id=BillingNewAddress_Address1]")
    private WebElement addressOne;

    @FindBy(css = "[class=inputs] [id=BillingNewAddress_ZipPostalCode]")
    private WebElement zipPost;

    @FindBy(css = "[class=inputs] [id=BillingNewAddress_PhoneNumber]")
    private WebElement phoneNumber;

    @FindBy(css = "[id=termsofservice]")
    private WebElement termsOfServiceCheckbox;

    @FindBy(css = "[class=\"checkout-buttons\"] [name=\"checkout\"]")
    private WebElement checkoutButton;

    @FindBy(css = "[id=billing-address-select]")
    private WebElement addressSelect;

    @FindBy(css = "[id=billing-address-select] option")
    private List<WebElement> listOfAddresses;

    @FindBy(css = "[id=BillingNewAddress_CountryId]")
    private WebElement countriesListDropdown;

    @FindBy(css = "[id=BillingNewAddress_CountryId] option")
    private List<WebElement> listOfCountries;

    @FindBy(css = "[value=Continue]")
    private List<WebElement> continueButtons;

    @FindBy(css = "[value=Confirm]")
    private WebElement submitCheckoutButton;

    @FindBy(css = ".order-completed .title")
    private WebElement completedOrderTitle;

    public WebElement getCompletedOrderTitle() {
        return completedOrderTitle;
    }

    public List<WebElement> getContinueButtons() {
        return continueButtons;
    }

    public WebElement getSubmitCheckoutButton() {
        return submitCheckoutButton;
    }

    public WebElement getAddressSelect() {
        return addressSelect;
    }

    public List<WebElement> getListOfAddresses() {
        return listOfAddresses;
    }

    public WebElement getCountriesListDropdown() {
        return countriesListDropdown;
    }

    public List<WebElement> getListOfCountries() {
        return listOfCountries;
    }

    public WebElement getCheckoutButton() {
        return checkoutButton;
    }

    public WebElement getTermsOfServiceCheckbox() {
        return termsOfServiceCheckbox;
    }

    public WebElement getCity() {
        return city;
    }

    public WebElement getAddressOne() {
        return addressOne;
    }

    public WebElement getZipPost() {
        return zipPost;
    }

    public WebElement getPhoneNumber() {
        return phoneNumber;
    }
}
