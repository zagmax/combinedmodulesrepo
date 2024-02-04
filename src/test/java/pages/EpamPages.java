package pages;

import manager.PropertyManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EpamPages {
    private final WebDriver driver;

    @FindBy(css = ".desktop-logo + .theme-switcher-ui > .theme-switcher")
    private WebElement switcher;

    @FindBy(css = "[role=\"tab\"]")
    private List<WebElement> listOfRegions;
    @FindBy(xpath = "//*[@class=\"column-control\"]//*[@class=\"validation-tooltip\"]//*[contains(text(),\"This is a required field\")]")
    private List<WebElement> listOfValidationWarnings;

    @FindBy(xpath = "//*[@class=\"dropdown-list__input form-component__input\"]//*[contains(text(),\"This is a required field\")]")
    private WebElement listValidation;

    @FindBy(xpath = "//*[contains(@class,\"button-submit\")]//*[contains(text(),\"Submit\")]")
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
    @FindBy(xpath = "(//*[contains(text(),\"DOWNLOAD\")])[1]")
    private WebElement downloadButton;

    @FindBy(xpath = "//*[@class=\"header__logo-container desktop-logo\"]")
    private WebElement epamLogo;

    @FindBy(xpath = "//*[@class=\"checkbox\"]//*[contains(text(),\"proceed\")]")
    private WebElement checkboxValidation;

    @FindBy(css = "[class=checkbox] .checkbox__holder")
    private WebElement requiredCheckbox;

    @FindBy(xpath = "//iframe[contains(@title,\"recaptcha\")]")
    private WebElement recapcha;

    public WebElement getEpamLogo() {
        return epamLogo;
    }


    public WebElement getRecapcha() {
        return recapcha;
    }

    public WebElement getDownloadButton() {
        return downloadButton;
    }


    public WebElement getCheckboxValidation() {
        return checkboxValidation;
    }

    public WebElement getRequiredCheckbox() {
        return requiredCheckbox;
    }


    public WebElement getListValidation() {
        return listValidation;
    }

    public List<WebElement> getFormInputFields() {
        List<WebElement> list = new ArrayList<>();

        list.add(driver.findElement(formInputFields).findElement(By.xpath("//*[@name=\"user_first_name\"]")));
        list.add(driver.findElement(formInputFields).findElement(By.xpath("//*[@name=\"user_last_name\"]")));
        list.add(driver.findElement(formInputFields).findElement(By.xpath("//*[@name=\"user_email\"]")));
        list.add(driver.findElement(formInputFields).findElement(By.xpath("//*[@name=\"user_phone\"]")));

        return list;
    }

    public List<WebElement> getListOfDropdownFields() {
        return driver.findElements(listOfDropdownFields);
    }

    public List<WebElement> getDropdownsOptions() {
        return driver.findElements(dropdownsOptions);
    }

    private final By listOfDropdownFields = By.xpath("//*[@class=\"dropdown-list\"]//*[contains(@role,\"combobox\")]");

    private final By dropdownsOptions = By.xpath("//*[@class=\"select2-results\"]//*[@role=\"option\"]");
    private final By formInputFields = By.xpath("//*[@class=\"layout-box\"]//*[@class=\"colctrl__holder\"]");
    private final By articles = By.xpath("//article[@class=\"search-results__item\"]");
    private final By polList = new By.ByCssSelector(".policies .fat-links");
    private final By languageList = new By.ByCssSelector(".location-selector__panel .location-selector__link");

    public WebElement getSwitcherLabel() {
        return switcherLabel;
    }

    @FindBy(css = ".hamburger-menu__dropdown .theme-switcher-label")
    private WebElement switcherLabel;

    public EpamPages(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void getToContactsPage() {
        driver.get("https://www.epam.com/about/who-we-are/contact");
    }

    public WebElement getSearchField() {
        return searchField;
    }

    public List<WebElement> getListOfSearchResults() {
        return driver.findElements(articles);
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

    public void openHomePage() {
        try {
            driver.get(PropertyManager.getPropertiesInstance().getProperty("epamHome"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
