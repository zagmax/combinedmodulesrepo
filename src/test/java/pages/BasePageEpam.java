package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePageEpam {
    private WebDriver driver;

    @FindBy(xpath = "//*[@class=\"header__logo-container desktop-logo\"]")
    private WebElement epamLogo;

    public BasePageEpam(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitForPageLoadComplete(Duration timeToWait) {
        new WebDriverWait(driver, timeToWait).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public void waitForAjaxToComplete(Duration timeToWait) {
        new WebDriverWait(driver, timeToWait).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return window.jQuery != undefined && jQuery.active == 0;"));
    }

    public WebElement getEpamLogo() {
        return epamLogo;
    }
}
