package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class DemoShopCheckout extends DemoWebShopPage {
    public DemoShopCheckout(WebDriver driver) {
        super(driver);
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

    @FindBy(css = "[id=billing-address-select] :last-child")
    private WebElement newAddress;

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

    public boolean isCompletedOrderTitleShown() {
        log.info("checking display of order completion title");
        return completedOrderTitle.isDisplayed();
    }

    public void finishCheckoutFlow() {
        log.info("completing checkout with no extra additions");
        clickWR(termsOfServiceCheckbox);
        clickWR(checkoutButton);
        clickWR(addressSelect);
        clickWR(newAddress);
        clickWR(countriesListDropdown);
        clickWR(listOfCountries.get(1));

        log.info("filling address section");
        city.sendKeys("city");
        addressOne.sendKeys("address");
        zipPost.sendKeys("010101");
        phoneNumber.sendKeys("+499999999");

        log.info("going through checkout billing sections");
        for (int i = 0; i < 5; i++) {
            clickWR(continueButtons.get(i));
        }
        clickWR(submitCheckoutButton);
        waitForPageLoadComplete();
    }
}
