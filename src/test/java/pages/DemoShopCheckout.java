package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DemoShopCheckout {
    private final WebDriver driver;

    public DemoShopCheckout(WebDriver driver) {
        this.driver = driver;
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
