package manager;

import org.openqa.selenium.WebDriver;
import pages.BasePageEpam;
import pages.DemoShopCheckout;
import pages.DemoWebShopPage;
import pages.EpamPages;


public class PageFactoryManager {

    private WebDriver driver;

    public PageFactoryManager(WebDriver driver) {
        this.driver = driver;
    }

    public EpamPages getHomePage() {
        return new EpamPages(driver);
    }

    public BasePageEpam getBasePage() {
        return new BasePageEpam(driver);

    }

    public DemoWebShopPage getDemoWebShopPage() {
        return new DemoWebShopPage();
    }

    public DemoShopCheckout getDemoShopCheckout() {
        return new DemoShopCheckout(driver);
    }
}