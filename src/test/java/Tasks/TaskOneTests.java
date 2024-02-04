package Tasks;

import io.github.bonigarcia.wdm.WebDriverManager;
import manager.PageFactoryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import pages.CorePage;
import pages.EpamPages;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class TaskOneTests {

    private final Logger log = LogManager.getRootLogger();
    private JavascriptExecutor js;
    private final Duration DEFAULT_WAITING_TIME = Duration.ofSeconds(30);
    private EpamPages epamPages;
    private CorePage corePage;
    private PageFactoryManager pageFactoryManager;

    private final Properties PROPERTIES = PageFactoryManager.getPROPERTIES();

    @BeforeAll
    public static void profileSetUp() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void testsSetUp() {
        log.info("Next Test initiated");
        pageFactoryManager = new PageFactoryManager();
        js = (JavascriptExecutor) pageFactoryManager.getDriver();
        corePage = pageFactoryManager.getCorePage();
        epamPages = pageFactoryManager.getEpamHomePage();
        pageFactoryManager.getDriver().manage().window().maximize();
        pageFactoryManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
        epamPages.openHomePage();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
    }

    @AfterEach
    public void tearDown() {
        pageFactoryManager.getDriver().close();
    }

    @Test
    @DisplayName("1. check title is correct")
    public void checkTitleEpam() {
        Assertions.assertEquals("EPAM | Software Engineering & Product Development Services", pageFactoryManager.getDriver().getTitle(), "Page's title is wrong");
        log.info("Check Title EPAM executed");
    }

    @Test
    @DisplayName("2. Check the ability to switch Light / Dark mode")
    public void checkDarkLightMode() {
        Assertions.assertEquals("Dark Mode", epamPages.getSwitcherLabel().getAttribute("innerText"), "Page is not in Dark mode");
        epamPages.getSwitcherElement().click();
        Assertions.assertEquals("Light Mode", epamPages.getSwitcherLabel().getAttribute("innerText"), "Page is not in Light mode");
        log.info("Check dark\\light switch executed");
    }

    @Test
    @DisplayName("3. change language to UA")
    public void changeLang() {
        epamPages = pageFactoryManager.getEpamHomePage();
        epamPages.getLangButton().click();
        for (WebElement elem : epamPages.getLangList()) {
            if (Objects.equals(elem.getAttribute("lang"), "uk")) {
                elem.click();
                break;
            }
        }
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(epamPages.getLangButton().getAttribute("innerText").contains("UA"), "Changing language of page has not been switched to UA");
        log.info("language test executed");
    }

    @Test
    @DisplayName("4. Check policies List")
    public void checkPoliciesList() {
        epamPages = pageFactoryManager.getEpamHomePage();
        int count = 0;
        String[] policy = new String[]
                {"INVESTORS", "COOKIE POLICY", "OPEN SOURCE", "APPLICANT PRIVACY NOTICE", "PRIVACY POLICY", "WEB ACCESSIBILITY"};
        boolean present = true;
        for (int i = 0; i < policy.length; i++) {
            present = false;
            for (int j = 0; j < epamPages.getPolicyList().size(); j++) {
                if (epamPages.getPolicyList().get(j).getText().equals(policy[i])) {
                    present = true;
                    break;
                }
            }
            if (!present) {
                count = i;
                break;
            }
        }
        Assertions.assertTrue(present, "The " + policy[count] + " is missing on footer or has incorrect text");
        log.info("policy list test executed");
    }

    @Test
    @DisplayName("5. Check that allow to switch location list by region")
    public void checkLocationSwitchByRegion() {
        epamPages = pageFactoryManager.getEpamHomePage();
        epamPages.getToContactsPage();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        js.executeScript("arguments[0].scrollIntoView();", epamPages.getListOfRegions().get(1));
        js.executeScript("window.scrollBy(0,-200)", "");
        Assertions.assertTrue(epamPages.getCanadaAmerica().isDisplayed(), "Canada is not displayed in America region list");
        epamPages.getListOfRegions().get(1).click();
        Assertions.assertTrue(epamPages.getArmeniaEMEA().isDisplayed(), "Armenia is not displayed in EMEA region list");
        epamPages.getListOfRegions().get(2).click();
        Assertions.assertTrue(epamPages.getAustraliaAPAC().isDisplayed(), "Australia is not displayed in APAC region list");
        epamPages.getListOfRegions().get(0).click();
        Assertions.assertTrue(epamPages.getCanadaAmerica().isDisplayed(), "Canada is not displayed in America region list after switch back to America region");
        log.info("region countries test executed");
    }

    @Test
    @DisplayName("6. Check the search function")
    public void checkSearch() {
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        epamPages.searchIcon().click();
        epamPages.getSearchField().sendKeys(PROPERTIES.getProperty("searchQuerry"));
        epamPages.getSearchConfirmBtn().click();
        Assertions.assertEquals("https://www.epam.com/search?q=AI", pageFactoryManager.getDriver().getCurrentUrl(), "Search page has not been opened");
        List<WebElement> articleList = epamPages.getListOfSearchResults();
        Assertions.assertTrue(articleList.size() > 0);
        for (WebElement el : articleList) {
            Assertions.assertTrue(el.getText().contains(PROPERTIES.getProperty("searchQuerry")), "Article does not containt " + PROPERTIES.getProperty("searchQuerry"));
        }
        log.info("search test executed");
    }

    @Test
    @DisplayName("7. Check form's fields validation")
    public void checkFormValidation() {
        corePage.openPage(PROPERTIES.getProperty("epamContacts"));
        js.executeScript("window.scrollBy(0,2200)", "");
        epamPages.getFormSubmitButton().click();
        Assertions.assertEquals(4, epamPages.getListOfValidationWarnings().size(), "One of text fields accepts empty value");
        Assertions.assertTrue(epamPages.getListValidation().isEnabled(), "Validation for selection filed \"How did you hear about EPAM\" accepts nothing being selected");
        Assertions.assertTrue(epamPages.getCheckboxValidation().isEnabled(), "required checkbox accepted as being not checked");
        epamPages.getFormInputFields().get(0).sendKeys("Name");
        epamPages.getFormInputFields().get(1).sendKeys("Name");
        epamPages.getFormInputFields().get(2).sendKeys("Name@name.com");
        epamPages.getFormInputFields().get(3).sendKeys("+1111111");
        js.executeScript("arguments[0].scrollIntoView();", epamPages.getRequiredCheckbox());
        js.executeScript("window.scrollBy(0, -300)", "");
        epamPages.getRequiredCheckbox().click();
        epamPages.getListOfDropdownFields().get(2).click();
        epamPages.getDropdownsOptions().get(0).click();
        js.executeScript("window.scrollBy(0, 300)", "");
        epamPages.getFormSubmitButton().click();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(epamPages.getRecapcha().isDisplayed(), "Form submitting failed");
        log.info("form validation test executed");
    }

    @Test
    @DisplayName("8. Check tha the Company logo on the header lead to the main page")
    public void checkLogoRedirect() {
        corePage.openPage(PROPERTIES.getProperty("epamAbout"));
        epamPages.getEpamLogo().click();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertEquals(PROPERTIES.getProperty("epamHome"), pageFactoryManager.getDriver().getCurrentUrl(), "Redirection to home page failed");
        log.info("Logo redirect test executed");
    }

    @Test
    @DisplayName("9. Check that allows to download report ")
    public void checkReportDownload() {
        corePage.openPage(PROPERTIES.getProperty("epamAbout"));
        js.executeScript("arguments[0].scrollIntoView();", epamPages.getDownloadButton());
        js.executeScript("arguments[0].click();", epamPages.getDownloadButton());
        File file = new File(PROPERTIES.getProperty("downloadPath") + "EPAM_Corporate_Overview_Q3_october.pdf");
        Assertions.assertTrue(file.exists(), "File does not exist, or has different file name");
        Assertions.assertTrue(file.delete(), "File does not exist, or was deleted already");
        log.info("File downloading from site test executed");
    }


}
