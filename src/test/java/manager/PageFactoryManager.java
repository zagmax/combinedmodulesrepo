package manager;

import org.openqa.selenium.WebDriver;
import pages.BasePageEpam;
import pages.EpamPages;


public class PageFactoryManager {

    private WebDriver driver;

    public PageFactoryManager( WebDriver driver) {
        this.driver = driver;
    }

    public EpamPages getHomePage() {
        return new EpamPages(driver);
    }
    public BasePageEpam getBasePage() {
        return new BasePageEpam(driver);

    }
/*
    public CatalogPage getCatalogPage() {
        return new CatalogPage(driver);
    }

    public ProductPage getProductPage() {
        return new ProductPage(driver);
    }
*/
}