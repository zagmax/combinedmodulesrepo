package pages;

import jdk.jfr.Description;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Log4j2
public class CorePage {
    private final WebDriver driver;
    private final JavascriptExecutor js;


    public CorePage(WebDriver driver) {
        log.info("initializing corepage instance");
        this.driver = driver;
        js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public void openPage(String url) {
        log.info("opening link: " + url);
        driver.get(url);
    }

    public String getUrl() {
        log.info("getting current URL");
        return driver.getCurrentUrl();
    }

    public void refresh() {
        log.info("refreshing page");
        driver.navigate().refresh();
    }

    public void waitForPageLoadComplete(Duration timeToWait) {
        log.info("waiting for page to load");
        new WebDriverWait(driver, timeToWait).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    @Description("Click on element when its clickable. WR - \"when ready\"")
    public void clickWR(WebElement element) {
        log.info("clicking on " + element + " with waiter");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public String getPageTitle() {
        log.info("getting current page title");
        return driver.getTitle();
    }

    public void scrollToElement(WebElement el) {
        log.info("scrolling to the " + el + " element");
        js.executeScript("arguments[0].scrollIntoView();", el);
    }

    public void scrollForSetAmount(String str) {
        log.info("scrolling for " + str + " amount of pixels");
        js.executeScript("window.scrollBy(0," + str + ")", "");
    }

    public void clickOnElementWithJS(WebElement el) {
        log.info("clicking on element " + el + " with JS executor");
        js.executeScript("arguments[0].click();", el);
    }
}
