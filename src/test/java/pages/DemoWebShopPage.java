package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DemoWebShopPage {
    private WebDriver driver;

    public DemoWebShopPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

//    @FindBy(css = "inputs")
//    private List<WebElement> listOfInputFieldsRegistration;

    @FindBy(css = ".registration-page [type=\"submit\"]")
    private WebElement registrationSubmit;

    public WebElement getRegistrationSubmit() {
        return registrationSubmit;
    }

}
