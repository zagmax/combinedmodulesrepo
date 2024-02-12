package Tasks;

import lombok.extern.log4j.Log4j2;
import manager.PageFactoryManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.EpamPages;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Log4j2
public class TaskOneTests {

    private EpamPages epamPages;
    private PageFactoryManager pageFactoryManager;

    private final Properties PROPERTIES = PageFactoryManager.getPROPERTIES();

    @BeforeEach
    public void testsSetUp() {
        log.info("Next Test initiated");
        pageFactoryManager = new PageFactoryManager();
        epamPages = pageFactoryManager.getEpamHomePage();
        epamPages.openHomePage();
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
                epamPages.getPageTitle(), "Page's title is wrong");
        log.info("Check Title EPAM test executed");
    }

    @Test
    @DisplayName("2. Check the ability to switch Light / Dark mode")
    public void checkDarkLightMode() {
        Assertions.assertEquals("Dark Mode", epamPages.getSwitcherLabel(), "Page is not in Dark mode");
        epamPages.clickSwitcherElement();
        Assertions.assertEquals("Light Mode", epamPages.getSwitcherLabel(), "Page is not in Light mode");
        log.info("Check dark\\light switch test executed");
    }

    @Test
    @DisplayName("3. change language to UA")
    public void changeLang() {
        epamPages.selectPageLanguage("uk");
        Assertions.assertTrue(epamPages.langButtonContainsText("UA"), "Changing language of page has not been switched to UA");
        log.info("language test executed");
    }

    @Test
    @DisplayName("4. Check policies List")
    public void checkPoliciesList() {
        List<String> policy = Arrays.asList("INVESTORS", "OPEN SOURCE", "PRIVACY POLICY", "COOKIE POLICY", "APPLICANT PRIVACY NOTICE", "WEB ACCESSIBILITY");
        Assertions.assertEquals(policy, (epamPages.getPolicyList()), "Some policies are missing on footer or have incorrect text");
        log.info("policy list test executed");
    }

    @Test
    @DisplayName("5. Check that allow to switch location list by region")
    public void checkLocationSwitchByRegion() {
        epamPages.openPage(PROPERTIES.getProperty("epamContacts"));
        epamPages.focusOnRegionSelector();
        Assertions.assertTrue(epamPages.canadaAmericaShown(), "Canada is not displayed in America region list");
        epamPages.clickWR(epamPages.getListOfRegions().get(1));
        Assertions.assertTrue(epamPages.isArmeniaEMEAShown(), "Armenia is not displayed in EMEA region list");
        epamPages.clickWR(epamPages.getListOfRegions().get(2));
        Assertions.assertTrue(epamPages.isAustraliaAPACShown(), "Australia is not displayed in APAC region list");
        epamPages.clickWR(epamPages.getListOfRegions().get(0));
        Assertions.assertTrue(epamPages.canadaAmericaShown(), "Canada is not displayed in America region list after switch back to America region");
        log.info("region countries test executed");
    }

    @Test
    @DisplayName("6. Check the search function")
    public void checkSearch() {
        epamPages.startASearch(PROPERTIES.getProperty("searchQuerry"));
        Assertions.assertEquals("https://www.epam.com/search?q=AI", epamPages.getUrl(), "Search page has not been opened");
        Assertions.assertTrue(epamPages.getListOfSearchResults().size() > 0);
        Assertions.assertTrue(epamPages.isAllResultsHaveSearchedText(), "Some articles does not contain " + PROPERTIES.getProperty("searchQuerry"));
        log.info("search test executed");
    }

    @Test
    @DisplayName("7. Check form's fields validation")
    public void checkFormValidation() {
        epamPages.clickSumbitFormWithEmptyFields();
        Assertions.assertEquals(4, epamPages.getListOfValidationWarnings().size(), "One of text fields accepts empty value");
        Assertions.assertTrue(epamPages.checkListValidationShown(), "Validation for selection filed \"How did you hear about EPAM\" accepts nothing being selected");
        Assertions.assertTrue(epamPages.checkboxValidationShown(), "required checkbox accepted as being not checked");
        epamPages.fillContactForm();
        Assertions.assertTrue(epamPages.recapchaShown(), "Form submitting failed");
        log.info("form validation test executed");
    }

    @Test
    @DisplayName("8. Check tha the Company logo on the header lead to the main page")
    public void checkLogoRedirect() {
        epamPages.openPage(PROPERTIES.getProperty("epamAbout"));
        epamPages.clickEpamLogo();
        Assertions.assertEquals(PROPERTIES.getProperty("epamHome"), epamPages.getUrl(), "Redirection to home page failed");
        log.info("Logo redirect test executed");
    }

    @Test
    @DisplayName("9. Check that allows to download report ")
    public void checkReportDownload() {
        epamPages.openPage(PROPERTIES.getProperty("epamAbout"));
        epamPages.downloadEpamFile();
        File file = new File(PROPERTIES.getProperty("downloadPath") + "EPAM_Corporate_Overview_Q3_october.pdf");
        epamPages.waitFile(file);
        Assertions.assertTrue(file.exists(), "File does not exist, or has different file name");
        Assertions.assertTrue(file.delete(), "File does not exist, or was deleted already");
        log.info("File downloading from site test executed");
    }


}
