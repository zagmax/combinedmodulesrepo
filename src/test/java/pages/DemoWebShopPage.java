package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DemoWebShopPage {


    @FindBy(css = ".registration-page [type=\"submit\"]")
    private WebElement registrationSubmit;

    public WebElement getRegistrationSubmit() {
        return registrationSubmit;
    }

}
