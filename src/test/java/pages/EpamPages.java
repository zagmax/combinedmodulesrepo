package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class EpamPages {
    private WebDriver driver;

    @FindBy(css = ".desktop-logo + .theme-switcher-ui > .theme-switcher")
    private WebElement switcher;

    @FindBy(css = "[role=\"tab\"]")
    private List<WebElement> listOfRegions;
    @FindBy(xpath = "//*[@class=\"column-control\"]//*[@class=\"validation-tooltip\"]//*[contains(text(),\"This is a required field\")]")
    private List<WebElement> listOfValidationWarnings;

    @FindBy(xpath="//*[contains(@class,\"button-submit\")]//*[contains(text(),\"Submit\")]")
    private WebElement formSubmitButton;

    @FindBy(css = ".location-selector__button")
    private WebElement langButton;

    @FindBy(xpath = "//*[@class=\"header-search-ui header-search-ui-23 header__control\"]")
    private WebElement searchIcon;
    @FindBy(xpath = "//*[@class=\"bth-text-layer\"]")
    private WebElement searchConfirmBtn;
    @FindBy(css = "[class=\"owl-item active\"] [data-country-title=\"Australia\"]")
    private WebElement australiaCountryPlateAPAC;
    @FindBy(css = "[id=\"new_form_search\"]")
    private WebElement searchField;
    @FindBy(css = "[class=\"owl-item active\"] [data-country-title=\"Canada\"]")
    private WebElement canadaCountryPlateAmerica;

    @FindBy(css = "[id=\"onetrust-accept-btn-handler\"]")
    private WebElement cookieAccept;
    @FindBy(css = "[class=\"owl-item active\"] [data-country-title=\"Armenia\"]")
    private WebElement armeniaCountryPlateEMEA;
    private By polList = new By.ByCssSelector(".policies .fat-links");
    private By languageList = new By.ByCssSelector(".location-selector__panel .location-selector__link");

    public WebElement getSwitcherLabel() {
        return switcherLabel;
    }

    @FindBy(css = ".hamburger-menu__dropdown .theme-switcher-label")
    private WebElement switcherLabel;

    public EpamPages(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getSearchField() {
        return searchField;
    }

    public WebElement getFormSubmitButton() {
        return formSubmitButton;
    }

    public List<WebElement> getLangList() {
        return driver.findElements(languageList);
    }

    public List<WebElement> getPolicyList() {
        return driver.findElements(polList);
    }

    public List<WebElement> getListOfRegions() {
        return listOfRegions;
    }

    public WebElement searchIcon() {
        return searchIcon;
    }

    public WebElement getSwitcherElement() {
        return switcher;
    }

    public WebElement getCookieAccept() {
        return cookieAccept;
    }

    public WebElement getAustraliaAPAC() {
        return australiaCountryPlateAPAC;
    }

    public List<WebElement> getListOfValidationWarnings() {
        return listOfValidationWarnings;
    }

    public WebElement getSearchConfirmBtn() {
        return searchConfirmBtn;
    }

    public WebElement getCanadaAmerica() {
        return canadaCountryPlateAmerica;
    }

    public WebElement getArmeniaEMEA() {
        return armeniaCountryPlateEMEA;
    }

    public WebElement getLangButton() {
        return langButton;
    }

    public void openHomePage(String url) {
        driver.get(url);
    }
}
