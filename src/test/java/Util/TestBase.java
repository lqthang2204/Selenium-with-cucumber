package Util;

import StepsDefinition.Steps;
import bean.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.datatable.DataTable;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.codeborne.selenide.Selenide.$;

public class TestBase {
    WebDriver driver;

    public TestBase() {
        Configuration.ReadConfig();

    }


    public WebDriver getDriver() {
        switch (Configuration.WEB_BROWSER) {
            case "CHROME":
//                WebDriverManager.chromedriver().clearDriverCache();
                WebDriverManager.chromedriver().setup();
                driver = new  ChromeDriver();
                break;
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "OPERA":
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver();
                break;
            case "EDGE":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                System.out.println("Browser "+ Configuration.PAGE_LOAD_TIME + " is not suuport");
                Assert.assertTrue(false);
                break;
        }
        return driver;
    }

    public static WebDriver OpenBrowser(TestBase testBase, WebDriver driver, String URl) {
        try {
            if (driver == null) {
                driver = testBase.getDriver();
                System.out.println("Duration.ofMillis(Configuration.PAGE_LOAD_TIME)=="+ Duration.ofMillis(Configuration.PAGE_LOAD_TIME));
                driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(Configuration.PAGE_LOAD_TIME));
                if (Configuration.DEFAULT_MAXIMUM) {
                    driver.manage().window().maximize();
                }
            }
            if(URl.equals("refresh-page")){
               driver.navigate().refresh();
            }
            if(!URl.equals("refresh-page")){
                driver.get(URl.replace("\"",""));
            }


        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
        return  driver;
    }

    public void mouseAction(Page page, String action, WebDriver driver, String element, Map<String, String> map) {
        Locators locators = getValueElement(page, element);
        String valueElement = getValueElementToWithText(locators, element,map);
        WebDriverWait wait = getWait(driver);
        By by = getBy(driver, locators.getType(), valueElement);
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
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }


    public void showUI(Page page, WebDriver driver, String element, String status, Map<String, String> map) {
        try {
            Locators locators = getValueElement(page, element);
            String valueElement = getValueElementToWithText(locators, element, map);
            WebDriverWait wait = getWait(driver);
            By by = getBy(driver, locators.getType(),valueElement);
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
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }

    public void verifyText(Page page, WebDriver driver, String element, String content, boolean status, Map<String, String> map) {
        Locators locators = getValueElement(page, element);
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
            Locators locators = getValueElement(page, element);
            WebDriverWait wait = getWait(driver);
            By by = getBy(driver, locators.getType(), locators.getValue());
            wait.until(ExpectedConditions.elementToBeClickable(by));
            driver.findElement(by).sendKeys(text);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }
    public void keyBoard(Page page, WebDriver driver,String element, String action){
        Actions actions = new Actions(driver);
        WebDriverWait wait = getWait(driver);
        Locators locators = getValueElement(page, element);
        By by = getBy(driver, locators.getType(), locators.getValue());
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement ele = null;
        switch (action){
            case "ENTER":
                actions.sendKeys(Keys.ENTER).perform();
                break;
            case "DOUBLE-CLICK":
                ele = driver.findElement(by);
                actions.doubleClick(ele).perform();
                break;
            case "RIGHT-CLICK":
                ele = driver.findElement(by);
                actions.contextClick(ele).perform();
                break;
            case "HOVER":
                 ele = driver.findElement(by);
                actions.moveToElement(ele).perform();
                break;
            case "HOVER-AND-CLICK":
                 ele = driver.findElement(by);
                actions.moveToElement(ele).perform();
                actions.click();
                actions.perform();
                break;
            default:
                System.out.println("Not suuport case  "+action );
                Assert.assertTrue(false);
        }




    }

    public void saveTextElement(Page page, WebDriver driver, String element, String text, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        try {
            Locators locators = getValueElement(page, element);
            WebDriverWait wait = getWait(driver);
            By by = getBy(driver, locators.getType(), locators.getValue());
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            String element_text = driver.findElement(by).getAttribute("value");;
            map.put("KEY." + text, element_text);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }

    public void scrollAction(WebDriver driver, String element, Page page) {
        try {
            Locators locators = getValueElement(page, element);
            WebDriverWait wait = getWait(driver);
            By by = getBy(driver, locators.getType(), locators.getValue());
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    public void executeAction(WebDriver driver, Page page, String action, DataTable dataTable,Map<String, String> map) {
        ActionsTest actions = getActions(page, action);
        WebDriverWait wait ;
        boolean flag= false;
        List<ActionElements> list = actions.getList();
            String value="";
            for (int i = 0; i < list.size(); i++) {
                try {
                 if(dataTable!=null){
                     Map<String,String> mapOverride = dataTable.asMap(String.class,String.class);
                     if(mapOverride.containsKey(list.get(i).getElement())){
                         value = mapOverride.get(list.get(i).getElement());
                     }
                 }
                 if(map.containsKey(value)){
                     value = map.get(value);
                 }
                Locators locators = getValueElement(page,list.get(i).getElement());
                By by = getBy(driver, locators.getType(), locators.getValue());
                if(list.get(i).getCondition()!=null){
                    wait = getWaitAction(driver,list.get(i).getTimeout());
                    switch (list.get(i).getCondition()){
                        case "DISPLAYED":
                            flag = true;
                            wait.until(ExpectedConditions.presenceOfElementLocated(by));
                            if(list.get(i).getInputType()!=null){
                                runType(driver,by, list.get(i).getInputType(),value);
                            }

                            break;
                        case "NOT_DISPLAYED":
                            flag = true;
                           wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(by)));
                            if(list.get(i).getInputType()!=null){
                                runType(driver,by, list.get(i).getInputType(),value);
                            }
                            break;
                    }

                }else{
                    wait = getWait(driver);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(by));
                    runType(driver,by, list.get(i).getInputType(),value);
                }

            }
                catch (Exception e){
                    e.printStackTrace();
                    Assert.assertTrue(flag);
                    System.out.println("Error at action have element "+ list.get(i).getElement() +" and input "+ list.get(i).getInputType());
                }
        }

//        By by = getBy(driver, locators.getType(), locators.getValue());

    }
    public void runType(WebDriver driver, By by, String status,String value){
        switch (status){
            case "text":
                driver.findElement(by).sendKeys(value);
                break;
            case "click":
                driver.findElement(by).click();
                break;
            default:
                throw new RuntimeException();


        }
    }
    public void CloseBrowser(WebDriver driver,String title){
        boolean flag = false;
        try {
            Set<String> windows =  driver.getWindowHandles();
            String mainWindow =  driver.getWindowHandle();
            for (String handle: windows)
            {
                driver.switchTo().window(handle);
                String pageTitle = driver.getTitle();
                if(pageTitle.equalsIgnoreCase(title))
                {
                    driver.close();
                    flag = true;
                    System.out.println("Closed the  '"+pageTitle+"' Tab now ...");
                }
            }
            driver.switchTo().window(mainWindow);
            Assert.assertTrue(flag);
        }catch (Exception e){
            e.printStackTrace();
            Assert.assertTrue(flag);
        }


    }


    public WebDriverWait getWait(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(Configuration.TIME_OUT));
        return wait;
    }
    public WebDriverWait getWaitAction(WebDriver driver,long duration) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(duration));
        return wait;
    }

    public Locators getValueElement(Page page, String id) {
        String element = getElement(id);
        for (int i = 0; i < page.getElements().size(); i++) {
                if (page.getElements().get(i).getId().equals(element)) {
                    return page.getElements().get(i).getLocator();
                }
            }

        System.out.println("Not Found element "+ id+  " in file "+ Steps.titlePage+".yaml");
        return null;
    }

    public ActionsTest getActions(Page page, String action_id) {
        ActionsTest actions= null;
        try {
            Map<String, ActionsTest> map= page.getMapActions();
             actions =  map.get(action_id);
        }catch (Exception e){
            e.printStackTrace();
        }
         return  actions;
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
    public String getValueElementToWithText(Locators locator, String element, Map<String, String> map){
        String elementText=null;
        String value=locator.getValue();
        if(element.contains("with text")){
            String split[] =  element.split("with text");
            elementText = split[1].trim().replace("\"","");
        }
        if(value.contains("{text}")){
            if(map.containsKey(elementText)){
                elementText = map.get(elementText);
            }
            return value.replace("{text}", elementText);
        }
       return value;

    }
    public String getElement(String element){
        if(element.contains("with text")){
            String split[] =  element.split("with text");
            element = split[0].trim();
            return element;
        }else{
            return  element;
        }
    }
}
