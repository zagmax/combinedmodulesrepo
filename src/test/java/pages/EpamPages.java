package pages;

import lombok.extern.log4j.Log4j2;
import manager.PageFactoryManager;
import manager.PropertyManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Log4j2
public class EpamPages {
    private final WebDriver driver;
    private final Properties PROPERTIES = PageFactoryManager.getPROPERTIES();

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

    public void clickEpamLogo() {
        log.info("click epam logo");
        epamLogo.click();
    }


    public boolean recapchaShown() {
        log.info("recapcha shown");
        return recapcha.isDisplayed();
    }

    public WebElement getDownloadButton() {
        return downloadButton;
    }


    public boolean checkboxValidationShown() {
        log.info("checking checkbox being shown");
        return checkboxValidation.isEnabled();
    }

    public WebElement getRequiredCheckbox() {
        return requiredCheckbox;
    }

    public void clickCheckbox() {
        log.info("clicking checkbox");
        requiredCheckbox.click();
    }

    public void selectOptionFromFormDropdown(int number) {
        log.info("selecting option from dropdown");
        getListOfDropdownFields().get(2).click();
        getDropdownsOptions().get(number).click();
    }

    public boolean checkListValidationShown() {
        log.info("checking display of list");
        return listValidation.isDisplayed();
    }

    public List<WebElement> getFormInputFields() {
        log.info("gathering input fields list");
        List<WebElement> list = new ArrayList<>();

        list.add(driver.findElement(formInputFields).findElement(By.xpath("//*[@name=\"user_first_name\"]")));
        list.add(driver.findElement(formInputFields).findElement(By.xpath("//*[@name=\"user_last_name\"]")));
        list.add(driver.findElement(formInputFields).findElement(By.xpath("//*[@name=\"user_email\"]")));
        list.add(driver.findElement(formInputFields).findElement(By.xpath("//*[@name=\"user_phone\"]")));

        return list;
    }

    public void fillFormTextFields() {
        log.info("filling required text fields in \"about\" form");
        getFormInputFields().get(0).sendKeys("Name");
        getFormInputFields().get(1).sendKeys("Name");
        getFormInputFields().get(2).sendKeys("Name@name.com");
        getFormInputFields().get(3).sendKeys("+1111111");
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

    @FindBy(css = ".hamburger-menu__dropdown .theme-switcher-label")
    private WebElement switcherLabel;

    public EpamPages(WebDriver driver) {
        log.info("initializing epamPage instance");
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getSwitcherLabel() {
        log.info("getting switcher label attribute");
        return switcherLabel.getAttribute("innerText");
    }

    public void getToContactsPage() {
        log.info("open contact page");
        driver.get("https://www.epam.com/about/who-we-are/contact");
    }

    public WebElement getSearchField() {
        return searchField;
    }

    public List<WebElement> getListOfSearchResults() {
        return driver.findElements(articles);
    }

    public void clickFormSubmitButton() {
        log.info("about page form submit button click");
        formSubmitButton.click();
    }

    public List<WebElement> getLangList() {
        log.info("get list of languages");
        return driver.findElements(languageList);
    }

    public List<WebElement> getPolicyList() {
        return driver.findElements(polList);
    }

    public List<WebElement> getListOfRegions() {
        return listOfRegions;
    }

    public void clickSearchIcon() {
        log.info("clicking search icon");
        searchIcon.click();
    }

    public void clickSwitcherElement() {
        log.info("clicking dark\"light switch");
        switcher.click();
    }

    public boolean isAustraliaAPACShown() {
        log.info("check display of australia");
        return australiaCountryPlateAPAC.isDisplayed();
    }

    public List<WebElement> getListOfValidationWarnings() {
        return listOfValidationWarnings;
    }

    public void clickSearchConfirmBtn() {
        log.info("clicking confirm form button");
        searchConfirmBtn.click();
    }

    public boolean isAllResultsHaveSearchedText() {
        log.info("checking search results for presence of search text");
        boolean result = true;
        for (WebElement el : getListOfSearchResults()) {
            if (!el.getText().contains(PROPERTIES.getProperty("searchQuerry"))) {
                result = false;
                break;
            }
        }
        return result;
    }

    public boolean canadaAmericaShown() {
        log.info("check Canada shown");
        return canadaCountryPlateAmerica.isDisplayed();
    }

    public boolean isArmeniaEMEAShown() {
        log.info("check armenia shown");
        return armeniaCountryPlateEMEA.isDisplayed();
    }

    public void clickLangButton() {
        log.info("click language select button");
        langButton.click();
    }

    public boolean langButtonContainsText(String str) {
        log.info("check language text");
        return langButton.getAttribute("innerText").contains(str);
    }

    public void selectPageLanguage(String str) {
        log.info("selecting language of page");
        for (WebElement elem : getLangList()) {
            if (Objects.equals(elem.getAttribute("lang"), str)) {
                elem.click();
                break;
            }
        }
    }

    public int areAllPolicyPresent(String[] policy, List<WebElement> actualPolicy) {
        log.info("Policy list check method is called");
        int count = -1;
        boolean present;
        for (int i = 0; i < policy.length; i++) {
            present = false;
            for (WebElement webElement : actualPolicy) {
                if (webElement.getText().equals(policy[i])) {
                    present = true;
                    break;
                }
            }
            if (!present) {
                count = i;
                break;
            }
        }
        return count;
    }

    public void openHomePage() {
        log.info("open hope page");
        try {
            driver.get(PropertyManager.getPropertiesInstance().getProperty("epamHome"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
