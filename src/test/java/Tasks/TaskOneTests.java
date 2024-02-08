package Tasks;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import manager.PageFactoryManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CorePage;
import pages.EpamPages;

import java.io.File;
import java.time.Duration;
import java.util.Properties;

@Log4j2
public class TaskOneTests {

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
        corePage = pageFactoryManager.getCorePage();
        epamPages = pageFactoryManager.getEpamHomePage();
        pageFactoryManager.browerConfiguration();
        epamPages.openHomePage();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
    }

    @AfterEach
    public void tearDown() {
        pageFactoryManager.browserClose();
    }

    @Test
    @DisplayName("1. check title is correct")
    public void checkTitleEpam() {
        Assertions.assertEquals(
                "EPAM | Software Engineering & Product Development Services",
                corePage.getPageTitle(), "Page's title is wrong");
        log.info("Check Title EPAM test executed");
    }

    @Test
    @DisplayName("2. Check the ability to switch Light / Dark mode")
    public void checkDarkLightMode() {
        Assertions.assertEquals(
                "Dark Mode",
                epamPages.getSwitcherLabel(), "Page is not in Dark mode");
        epamPages.clickSwitcherElement();
        Assertions.assertEquals(
                "Light Mode",
                epamPages.getSwitcherLabel(), "Page is not in Light mode");
        log.info("Check dark\\light switch test executed");
    }

    @Test
    @DisplayName("3. change language to UA")
    public void changeLang() {
        epamPages = pageFactoryManager.getEpamHomePage();
        epamPages.clickLangButton();
        epamPages.selectPageLanguage("uk");
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(epamPages.langButtonContainsText("UA"), "Changing language of page has not been switched to UA");
        log.info("language test executed");
    }

    @Test
    @DisplayName("4. Check policies List")
    public void checkPoliciesList() {
        String[] policy = new String[]
                {"INVESTORS", "COOKIE POLICY", "OPEN SOURCE", "APPLICANT PRIVACY NOTICE", "PRIVACY POLICY", "WEB ACCESSIBILITY"};
        epamPages = pageFactoryManager.getEpamHomePage();
        int numberOfMissingPolicy = epamPages.areAllPolicyPresent(policy, epamPages.getPolicyList());
        boolean allPresent;
        String missingPolicy = "";
        if (numberOfMissingPolicy == -1) {
            allPresent = true;
        } else {
            missingPolicy = policy[numberOfMissingPolicy];
            allPresent = false;
        }
        Assertions.assertTrue(allPresent, "The " + missingPolicy + " is missing on footer or has incorrect text");
        log.info("policy list test executed");
    }

    @Test
    @DisplayName("5. Check that allow to switch location list by region")
    public void checkLocationSwitchByRegion() {
        epamPages = pageFactoryManager.getEpamHomePage();
        epamPages.getToContactsPage();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        corePage.scrollToElement(epamPages.getListOfRegions().get(1));
        corePage.scrollForSetAmount("-200");
        Assertions.assertTrue(epamPages.canadaAmericaShown(), "Canada is not displayed in America region list");
        corePage.clickWR(epamPages.getListOfRegions().get(1));
        Assertions.assertTrue(epamPages.isArmeniaEMEAShown(), "Armenia is not displayed in EMEA region list");
        corePage.clickWR(epamPages.getListOfRegions().get(2));
        Assertions.assertTrue(epamPages.isAustraliaAPACShown(), "Australia is not displayed in APAC region list");
        corePage.clickWR(epamPages.getListOfRegions().get(0));
        Assertions.assertTrue(epamPages.canadaAmericaShown(), "Canada is not displayed in America region list after switch back to America region");
        log.info("region countries test executed");
    }

    @Test
    @DisplayName("6. Check the search function")
    public void checkSearch() {
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        epamPages.clickSearchIcon();
        epamPages.getSearchField().sendKeys(PROPERTIES.getProperty("searchQuerry"));
        epamPages.clickSearchConfirmBtn();
        Assertions.assertEquals("https://www.epam.com/search?q=AI", corePage.getUrl(), "Search page has not been opened");
        Assertions.assertTrue(epamPages.getListOfSearchResults().size() > 0);
        Assertions.assertTrue(epamPages.isAllResultsHaveSearchedText(), "Some articles does not contain " + PROPERTIES.getProperty("searchQuerry"));
        log.info("search test executed");
    }

    @Test
    @DisplayName("7. Check form's fields validation")
    public void checkFormValidation() {
        corePage.openPage(PROPERTIES.getProperty("epamContacts"));
        corePage.scrollForSetAmount("2200");
        epamPages.clickFormSubmitButton();
        Assertions.assertEquals(4, epamPages.getListOfValidationWarnings().size(), "One of text fields accepts empty value");
        Assertions.assertTrue(epamPages.checkListValidationShown(), "Validation for selection filed \"How did you hear about EPAM\" accepts nothing being selected");
        Assertions.assertTrue(epamPages.checkboxValidationShown(), "required checkbox accepted as being not checked");
        epamPages.fillFormTextFields();
        corePage.scrollToElement(epamPages.getRequiredCheckbox());
        corePage.scrollForSetAmount("-300");
        epamPages.clickCheckbox();
        epamPages.selectOptionFromFormDropdown(0);
        corePage.scrollForSetAmount("300");
        epamPages.clickFormSubmitButton();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertTrue(epamPages.recapchaShown(), "Form submitting failed");
        log.info("form validation test executed");
    }

    @Test
    @DisplayName("8. Check tha the Company logo on the header lead to the main page")
    public void checkLogoRedirect() {
        corePage.openPage(PROPERTIES.getProperty("epamAbout"));
        epamPages.clickEpamLogo();
        corePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assertions.assertEquals(PROPERTIES.getProperty("epamHome"), corePage.getUrl(), "Redirection to home page failed");
        log.info("Logo redirect test executed");
    }

    @Test
    @DisplayName("9. Check that allows to download report ")
    public void checkReportDownload() {
        corePage.openPage(PROPERTIES.getProperty("epamAbout"));
        corePage.scrollToElement(epamPages.getDownloadButton());
        corePage.clickOnElementWithJS(epamPages.getDownloadButton());
        File file = new File(PROPERTIES.getProperty("downloadPath") + "EPAM_Corporate_Overview_Q3_october.pdf");
        Assertions.assertTrue(file.exists(), "File does not exist, or has different file name");
        Assertions.assertTrue(file.delete(), "File does not exist, or was deleted already");
        log.info("File downloading from site test executed");
    }


}
