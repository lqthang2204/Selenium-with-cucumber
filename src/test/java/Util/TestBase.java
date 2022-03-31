package Util;

import bean.Elements;
import bean.Locators;
import bean.Page;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestBase {
    WebDriver driver;

    public TestBase() {
        Configuration.ReadConfig();
    }

    public WebDriver getDriver() {
        switch (Configuration.WEB_BROWSER) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "opera":
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
        return driver;
    }

    public void OpenBrowser(WebDriver driver, String URl) {
        try {
            if (Configuration.DEFAULT_MAXIMUM) {
                driver.manage().window().maximize();
            }
            driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(Configuration.PAGE_LOAD_TIME));
            driver.get(URl);
        }catch (Exception e){
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }

    public void mouseAction(Page page, String action, WebDriver driver, String element) {
        Locators locators = getValueElement(page, driver, element);
        WebDriverWait wait = getWait(driver);
        By by = getBy(driver, locators.getType(), locators.getValue());
        try {
            switch (action) {
                case "click":
                    wait.until(ExpectedConditions.elementToBeClickable(by));
                    driver.findElement(by).click();
                    break;
                case "clear":
                    wait.until(ExpectedConditions.elementToBeClickable(by));
                    driver.findElement(by).clear();
                    break;

            }
        }catch (Exception e){
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }

    public void showUI(Page page, WebDriver driver, String element, String status) {
        try{
            Locators locators = getValueElement(page, driver, element);
            WebDriverWait wait = getWait(driver);
            By by = getBy(driver, locators.getType(), locators.getValue());
            switch (status) {
                case "DISPLAYED":
                    wait.until(ExpectedConditions.visibilityOfElementLocated(by));
                    break;
                case "NOT_DISPLAYED":
                    wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(by)));
                    break;
                case "ENABLED":
                    wait.until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            return driver.findElement(by).isEnabled();
                        }
                    });
                    break;
                case "NOT_ENABLED":
                    wait.until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            return !(driver.findElement(by).isEnabled());
                        }
                    });
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }

    public void verifyText(Page page, WebDriver driver, String element, String content, boolean status, Map<String, String> map) {
        Locators locators = getValueElement(page, driver, element);
        By by = getBy(driver, locators.getType(), locators.getValue());
        try {
            if (map.containsKey(content)) {
                content = map.get(content);
            }
            if (status) {
                String finalResult = content;
                new WebDriverWait(driver, Duration.ofMillis(Configuration.TIME_OUT)).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return driver.findElement(by).getText().contains(finalResult);
                    }
                });
            } else {
                String finalResult = content;
                 new WebDriverWait(driver, Duration.ofMillis(Configuration.TIME_OUT)).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return driver.findElement(by).getText().equals(finalResult);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Actual Text verify: " + driver.findElement(by).getText());
            System.out.println("Expect Text verify: " + content);
            Assert.assertTrue(false);
        }

    }

    public void ActionType(Page page, WebDriver driver, String element, String content, Map<String, String> map) {
        try {
            String text = content;
            if (map.containsKey(content)) {
                text = map.get(content);
            }
            Locators locators = getValueElement(page, driver, element);
            WebDriverWait wait = getWait(driver);
            By by = getBy(driver, locators.getType(), locators.getValue());
            wait.until(ExpectedConditions.elementToBeClickable(by));
            driver.findElement(by).sendKeys(text);
        } catch(Exception e){
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }

    public void saveTextElement(Page page, WebDriver driver, String element, String text, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        try {
            Locators locators = getValueElement(page, driver, element);
            WebDriverWait wait = getWait(driver);
            By by = getBy(driver, locators.getType(), locators.getValue());
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            String element_text = driver.findElement(by).getText();
            map.put("KEY." + text, element_text);
        }catch(Exception e){
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }
    public void scrollAction(WebDriver driver,String element,Page page){
        try{
            Locators locators = getValueElement(page, driver, element);
            WebDriverWait wait = getWait(driver);
            By by = getBy(driver, locators.getType(), locators.getValue());
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
        }catch (Exception e){
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    public WebDriverWait getWait(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(Configuration.TIME_OUT));
        return wait;
    }

    public Locators getValueElement(Page page, WebDriver driver, String id) {
        for (int i = 0; i < page.getElements().size(); i++) {
            if (page.getElements().get(i).getId().equals(id)) {
                return page.getElements().get(i).getLocator();
            }
        }
        System.out.println("Not Found element in file yaml");
        return null;
    }

    public By getBy(WebDriver driver, String type, String value) {
        By by = null;
        switch (type) {
            case "XPATH":
                by = By.xpath(value);
                break;
            case "ID":
                by = By.id(value);
                break;
            case "NAME":
                by = By.name(value);
                break;
            case "CLASS":
                by = By.className(value);
                break;
            default:
                System.out.println("Not Found type");
        }
        return by;
    }
}
