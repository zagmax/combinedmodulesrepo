package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePageEpam {
    private final WebDriver driver;

    @FindBy(xpath = "//*[@class=\"header__logo-container desktop-logo\"]")
    private WebElement epamLogo;

    public BasePageEpam(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitForPageLoadComplete(Duration timeToWait) {
        new WebDriverWait(driver, timeToWait).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public void clickCSSElementWhenClickable(String path) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(new By.ByCssSelector(path)))).click();
    }

    public WebElement getEpamLogo() {
        return epamLogo;
    }
}
