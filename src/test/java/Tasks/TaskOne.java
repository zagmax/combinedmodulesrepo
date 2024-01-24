package Tasks;

import io.github.bonigarcia.wdm.WebDriverManager;
import jdk.jfr.Name;
import manager.PageFactoryManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.BasePageEpam;
import pages.HomePageEpam;

import java.io.File;
import java.time.Duration;
import java.util.Objects;

public class TaskOne {

    private String browserType = "ch";
    private WebDriver driver;
    private JavascriptExecutor js;
    private final Duration DEFAULT_WAITING_TIME = Duration.ofSeconds(30);
    private HomePageEpam homePage;
    private BasePageEpam basePage;
    private PageFactoryManager pageFactoryManager;
    private String defaultSavePathForBrowser = "C:\\Users\\zagmax\\Downloads\\";

    @BeforeClass
    public static void profileSetUp() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    @Before
    public void testsSetUp() {
        switch (browserType) {
            case "ch" -> driver = new ChromeDriver();
            case "ff" -> driver = new FirefoxDriver();
        }
        js = (JavascriptExecutor) driver;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
        driver.get("https://www.epam.com/");
        pageFactoryManager = new PageFactoryManager(driver);
        basePage = pageFactoryManager.getBasePage();
        homePage = pageFactoryManager.getHomePage();
    }

    // UI Cases

    @Test
    @Name("1. check title is correct")
    public void checkTitleEpam() {
        Assert.assertEquals("EPAM | Software Engineering & Product Development Services", driver.getTitle());
    }

    @Test
    @Name("2. Check the ability to switch Light / Dark mode")
    public void checkDarkLightMode() {
        homePage = pageFactoryManager.getHomePage();
        Assert.assertEquals("Dark Mode", homePage.getSwitcherLabel().getAttribute("innerText"));
        homePage.getSwitcherElement().click();
        Assert.assertEquals("Light Mode", homePage.getSwitcherLabel().getAttribute("innerText"));
    }

    @Test
    @Name("3. change language to UA")
    public void changeLang() {
        homePage = pageFactoryManager.getHomePage();
        homePage.getLangButton().click();
        for (WebElement elem : homePage.getLangList()) {
            if (Objects.equals(elem.getAttribute("lang"), "uk")) {
                elem.click();
                break;
            }
        }
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assert.assertTrue(homePage.getLangButton().getAttribute("innerText").contains("UA"));
    }

    @Test
    @Name("4. Check policies List")
    public void checkPoliciesList() {
        homePage = pageFactoryManager.getHomePage();
        int count = 0;
        String[] policy = new String[]
                {"INVESTORS", "COOKIE POLICY", "OPEN SOURCE", "APPLICANT PRIVACY NOTICE", "PRIVACY POLICY"};
        for (WebElement el : homePage.getPolicyList()) {
            for (String str : policy) {
                if (str.equals(el.getText())) {
                    count++;
                    break;
                }
            }
        }
        Assert.assertEquals(5, count);
    }

    // WEB accessibility
    @Test
    @Name("5. Check that allow to switch location list by region")
    public void checkLocationSwitchByRegion() {
        homePage = pageFactoryManager.getHomePage();
        driver.get("https://www.epam.com/about/who-we-are/contact");
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
//        js.executeScript("arguments[0].scrollIntoView();",
//                homePage.getListOfRegions().get(1));
        //driver.findElement(new By.ByCssSelector(".active .horizontal-scrollbar-wrapper")));
        // driver.findElement(new By.ByCssSelector("section__wrapper section--padding-no bg-gradient")));
//        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
//                .withTimeout(Duration.ofSeconds(5L))
//                .pollingEvery(Duration.ofSeconds(1L));
//
        //homePage.getCookieAccept().click();
       // basePage.waitForAjaxToComplete(DEFAULT_WAITING_TIME);
        js.executeScript("window.scrollBy(0,1300)", "");
        Assert.assertTrue(homePage.getCanadaAmerica().isDisplayed());
        homePage.getListOfRegions().get(1).click();
        Assert.assertTrue(homePage.getArmeniaEMEA().isDisplayed());
        homePage.getListOfRegions().get(2).click();
        Assert.assertTrue(homePage.getAustraliaAPAC().isDisplayed());
    }

    @Test
    @Name("6. Check the search function")
    public void checkSearch() {
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        homePage.searchIcon().click();
        homePage.getSearchField().sendKeys("AI");
        homePage.getSearchConfirmBtn().click();
        Assert.assertEquals("https://www.epam.com/search?q=AI", driver.getCurrentUrl());

    }

    @Test
    @Name("7. Chack form's fields validation")
    public void checkFormValidation() {
        driver.get("https://www.epam.com/about/who-we-are/contact");

    }

    @Test
    @Name("8. Check tha the Company logo on the header lead to the main page")
    public void checkLogoRedirect() {
        driver.get("https://www.epam.com/about");
        basePage.getEpamLogo().click();
        basePage.waitForPageLoadComplete(DEFAULT_WAITING_TIME);
        Assert.assertEquals("https://www.epam.com/", driver.getCurrentUrl());

    }

    @Test
    @Name("9. Check that allows to download report ")
    public void checkReportDownload() {
        driver.get("https://www.epam.com/about");
        driver.findElement(By.xpath("(//*[contains(text(),\"DOWNLOAD\")])[1]")).click();
        File file = new File(defaultSavePathForBrowser + "EPAM_Corporate_Overview_Q3_october.pdf");
        Assert.assertTrue(file.exists());
        Assert.assertTrue(file.delete());
    }


    @After
    public void tearDown() {
        driver.close();
    }

}
