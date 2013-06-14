package resource;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import static org.apache.commons.io.FileUtils.copyFile;
import static org.openqa.selenium.By.xpath;

@SuppressWarnings("unused")
public class FunctionReference extends Results {
    public WebDriver driver;
    public String browser = "Chrome";
    public Integer resultcount = 0;
    public String testresults;    

    @BeforeClass
    public void Startup() {
        if (browser.contains("Firefox")) {
            FirefoxProfile profile = new FirefoxProfile();
            driver = new FirefoxDriver();
        }
        if (browser.contains("HTMLUnit")) {
            driver = new HtmlUnitDriver(true);
        }
        if (browser.contains("Chrome")) {
            System.setProperty("webdriver.chrome.driver", "c:\\Selenium\\chromedriver.exe");  //path of the   chromedriver
            driver = new ChromeDriver();
        }
        if (browser.contains("InternetExplorer")) {
            System.setProperty("webdriver.ie.driver", "IEDriverServer.exe");  //path of the   IEDriverServer
            driver = new InternetExplorerDriver();
        }
    }

    public void takeScreenshot() throws IOException {
        Random rand = new Random();
        int num = rand.nextInt(150);
        Integer numNoRange = Integer.valueOf(rand.nextInt());
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filename = numNoRange.toString();
        fail("Screenshot file name is : " + filename);
        copyFile(scrFile, new File(filename + ".png")); // add the path to store the screen shot
    }

    public void waitForElementNotPresent(By by) {
        WebDriverWait wait = new WebDriverWait(driver, 120);
        Boolean res = wait.until(ExpectedConditions.stalenessOf(driver.findElement(by)));
    }

    public void waitForElementNotPresent(By by, int t) {
        WebDriverWait wait = new WebDriverWait(driver, t);
        Boolean res = wait.until(ExpectedConditions.stalenessOf(driver.findElement(by)));
    }

    public void readPDF() throws IOException {
        URL url = new URL(driver.getCurrentUrl());
        BufferedInputStream fileToParse = new BufferedInputStream(url.openStream());
        PDFParser parser = new PDFParser(fileToParse);
        parser.parse();
        String output = new PDFTextStripper().getText(parser.getPDDocument());
        pass(output);
        parser.getPDDocument().close();
        driver.close();
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAt(By by) throws InterruptedException {
        Thread.sleep(1000);
        Actions builder = new Actions(driver);
        WebElement tagElement = driver.findElement(by);
        builder.moveToElement(tagElement).click().perform();
        Thread.sleep(1000);
    }

    public void click(By by) throws InterruptedException {
        waitForElementPresent(by);
        waitForElementVisible(by);
        driver.findElement(by).click();
    }

    public void type(By by, String value) {
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(value);
    }

    public void typeAll(String[] path, String[] value) {
        int len = path.length;
        for (int i = 0; i <= (len - 1); i++) {
            if (isElementPresent(xpath(path[i]))) {
                type(xpath(path[i]), value[i]);
            }
        }
    }

    public void actionType(By by, String value) {
        new Actions(driver).moveToElement(driver.findElement(by)).click()
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), value).perform();
    }

    public void waitForElementPresent(By by) throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= 15)
                break;
            try {
                if (isElementPresent(by))
                    break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    public void waitForElementPresent(By by, int i) throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= i)
                break;
            try {
                if (isElementPresent(by))
                    break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    public void waitLongerForElementPresent(By by) throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= 180)
                break;
            try {
                if (isElementPresent(by))
                    break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    public boolean isElementVisible(final By by) throws InterruptedException {
        boolean value = false;
        waitForElementPresent(by);
        if (driver.findElement(by).isDisplayed()) {
            value = true;
        }
        return value;
    }

    public void waitForElementVisible(By by) throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= 15)
                break;
            try {
                if (isElementVisible(by))
                    break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    public void waitForElementInVisible(By by, int time) throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= time)
                break;
            try {
                if (!isElementVisible(by))
                    break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    public String getText(By by) {
        String value = driver.findElement(by).getText();
        return value;
    }

    public String getValue(By by) {
        String value = "";
        if (isElementPresent(by)) {
            value = driver.findElement(by).getAttribute("value");
        }
        return value;
    }

    public void rightClick(By by) {
        new Actions(driver).moveToElement(driver.findElement(by)).contextClick(driver.findElement(by)).perform();
    }
    
    @AfterClass
    public void teardown() throws Exception {        
        driver.quit();
    }

}
