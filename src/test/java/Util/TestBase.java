package Util;

import StepsDefinition.Steps;
import bean.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.stream.Stream;

//import static com.codeborne.selenide.Selenide.$;

public class TestBase {
    public static WebDriver driver;
    private JsonNode arrayJsonNode;
    FakerData fake;
    public WebDriverWait wait;
    public Actions actions;
    public Map<String, Page> map = new HashMap<>();
    ExecuteYaml yamlExecute = new ExecuteYaml();

    public TestBase() {
        Configuration.ReadConfig();

    }


    public WebDriver getDriver() {
        switch (Configuration.WEB_BROWSER) {
            case "CHROME":
                WebDriverManager.chromedriver().clearDriverCache();
                WebDriverManager.chromedriver().setup();
                this.driver = new  ChromeDriver();
                break;
            case "FIREFOX":
//                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
//            case "OPERA":
////                WebDriverManager.operadriver().setup();
//                driver = new
//                break;
            case "EDGE":
//                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                System.out.println("Not support to run browser");
                Assert.assertTrue(false);
                break;
        }
        wait = getWait(driver);
        return driver;
    }

    public static void OpenBrowser(TestBase testBase, String URl) {
        try {
            if (driver == null ) {
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
    }

    public void mouseAction(Page page, String action, String element, Map<String, String> map) {
        Locators locators = getValueElement(page, element);
        String valueElement = getValueElementToWithText(locators, element,map);
//        WebDriverWait wait = getWait(driver);
        By by = getBy(driver, locators.getType(), valueElement);
        try {
            switch (action) {
                case "click":
                    wait.until(ExpectedConditions.elementToBeClickable(by));
                    String disabled = driver.findElement(by).getAttribute("disabled");
                    if(disabled!=null){
                        wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(by), "disabled")));
                    }

//                    wait.until(ExpectedConditions.invisibilityOfElementLocated(driver.findElement(by).getAttribute("disabled")));
//                    scrollToElement(this.driver, by);
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
    public void switchTab( int index){
        try {
            ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
            System.out.println( "tab===="+ tabs.size());
            driver.switchTo().window(tabs.get(index-1));
        }catch (Exception e)
        {
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }


    public void showUI(Page page, String element, String status, Map<String, String> map) {
        try {
            Locators locators = getValueElement(page, element);
            String valueElement = getValueElementToWithText(locators, element, map);
//            WebDriverWait wait = getWait(driver);
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

    public void verifyText(Page page,String element, String content, boolean status, Map<String, String> map, UserDTO userDTO) {
        Locators locators = getValueElement(page, element);
        By by = getBy(driver, locators.getType(), locators.getValue());
        scrollAction( element, page);

        try {
            if (map.containsKey(content)) {
                content = map.get(content);
            }
            if(content.toLowerCase().contains("user.")){
                content = content.substring(5);
                content = getProfileUser(content, userDTO);
            }
            if (status) {
            String result = content;
                new WebDriverWait(driver, Duration.ofMillis(Configuration.TIME_OUT)).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return driver.findElement(by).getText().contains(result);
                    }
                });
            } else {
                String result = content;
                new WebDriverWait(driver, Duration.ofMillis(Configuration.TIME_OUT)).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return driver.findElement(by).getText().equals(result);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Actual Text verify: " + driver.findElement(by).getText());
            System.out.println("Expect Text verify: " + content);
            Assert.assertTrue(false);
            Assert.fail("Actual Text verify: " + driver.findElement(by).getText()+"\n + Expect Text verify: " + content);
        }

    }

    public void ActionType(Page page, String element, String content, Map<String, String> map, UserDTO userDTO) {
        try {
            String text = content;
            Locators locators = getValueElement(page, element);
//            WebDriverWait wait = getWait(driver);
            By by = getBy(driver, locators.getType(), locators.getValue());
            wait.until(ExpectedConditions.elementToBeClickable(by));
            if (map.containsKey(content)) {
                text = map.get(content);
            }
           else if(content.contains("USER.")){
                String suffix = content.substring(5);
                text = getProfileUser(suffix, userDTO);
            }
          else if(content.contains("UNIQUE.")){
                text= getReplaceValue(content, userDTO, map);
            }
//          else if(content.contains("keyboard."){
//                text = Keys.getKeyFromUnicode()
//
//            }
            driver.findElement(by).sendKeys(new CharSequence[]{text});
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }
    public void runCollection(String collectionJson, String dataFile, Map<String, String> datatable, Scenario scenario, UserDTO userDTO, Map<String, String> mapSavetext)  {
         collectionJson = Configuration.PATH_POSTMAN+"/collection/"+collectionJson;
         dataFile = Configuration.PATH_POSTMAN+"/data-files/"+dataFile;
         try {
            this.arrayJsonNode = ( new ObjectMapper()).readTree(new File(dataFile));
             JsonNode jsonNode = UpdateNodeJson( datatable, userDTO, mapSavetext);
             System.out.println("json node== "+ jsonNode);
                ObjectMapper mapper = new ObjectMapper();
                dataFile = Configuration.PATH_POSTMAN+"/data-files/"+ scenario.getName()+"_" +System.currentTimeMillis()+".json";
                mapper.writeValue(Paths.get(dataFile).toFile(), jsonNode);
                String[] arrComman = new String[]{"newman","run",null,null,null};
             arrComman[2] = collectionJson;
             arrComman[3] = "-d";
             arrComman[4] = dataFile;
                List<String> commandLineAggrument = new ArrayList<>(Arrays.asList(arrComman));
                if(System.getProperty("os.name").toLowerCase().contains("win")){
                    commandLineAggrument.add(0,"cmd");
                    commandLineAggrument.add(1,"/c");
                }
             ExecuteWithOutToFile(dataFile, commandLineAggrument.toArray(new String[0]));
         }catch (Exception e){
             e.printStackTrace();
             Assert.assertTrue(false);
         }

    }

    public JsonNode UpdateNodeJson(Map<String, String> dataTable, UserDTO userDTO, Map<String, String> mapSaveText){
//        if(dataTable!=null){

            Iterator<JsonNode> var1 = this.arrayJsonNode.iterator();
            if(var1.hasNext()){
                 JsonNode jsonNode = var1.next();
                Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
                while(fields.hasNext()){
                    Map.Entry<String, JsonNode> jsonNodeMap = fields.next();
                    String key = jsonNodeMap.getKey();
                    String value = jsonNodeMap.getValue().asText();
                    List<String> list = new ArrayList<>(Arrays.asList("user.", "unique.","key."));
                    Iterator<String> var = list.iterator();
                    for(int i=0;i<list.size();i++){
                        String field_key = list.get(i);
                        if(value.contains(field_key)){
                            ((ObjectNode) jsonNode).put(key, getReplaceValue(value, userDTO, mapSaveText));
                        }
                        if(Objects.nonNull(dataTable) && dataTable.containsKey(key)){
                            String data = dataTable.get(key).toString();
                            data = getReplaceValue(data, userDTO, mapSaveText);
                            ((ObjectNode)jsonNode).put(key, data);
                        }
                    }

                }

            }

            return this.arrayJsonNode;
//        }

    }
    public void keyBoard(Page page,String element, String action){
        Actions actions = new Actions(driver);
//        WebDriverWait wait = getWait(driver);
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

    public void saveTextElement(Page page,String element, String text, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        try {
            Locators locators = getValueElement(page, element);
//            WebDriverWait wait = getWait(driver);
            By by = getBy(driver, locators.getType(), locators.getValue());
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            String element_text = "input".equals(driver.findElement(by).getTagName())? driver.findElement(by).getAttribute("value"):driver.findElement(by).getText();
            map.put("KEY." + text, element_text);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }

    public void scrollAction(String element, Page page) {
        try {
            Locators locators = getValueElement(page, element);
//            WebDriverWait wait = getWait(driver);
            By by = getBy(driver, locators.getType(), locators.getValue());
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
    public void scrollToElement(WebDriver driver, By by) {
        try {
             actions = new Actions(driver);
            actions.moveToElement(driver.findElement(by));
            actions.perform();
//            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
//            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
    public void ExecuteWithOutToFile(String path, String... args)  {
        try {
            int exitCode = 0;
            Process process = Runtime.getRuntime().exec(args);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder outputBuilder;
            String line;
            for(outputBuilder = new StringBuilder(); process.isAlive(); exitCode = process.waitFor()) {
                while((line = stdInput.readLine()) != null) {
                    outputBuilder.append(line).append("\n");
                }
            }
            if (exitCode != 0) {
                System.out.println(outputBuilder.toString());
                Assert.assertTrue(false);
            } else {
                System.out.println(outputBuilder.toString());
                stdInput.close();

            }

        }catch (Exception e){
            e.printStackTrace();
            Assert.assertTrue(false);
        }
        finally {
            deleteFile(path);
        }

    }

    public void executeAction(Page page, String action, DataTable dataTable,Map<String, String> map, UserDTO userDTO) {
        ActionsTest actions = getActions(page, action);
        WebDriverWait waitAction ;
        boolean flag= false;
        boolean temp= false;
        List<ActionElements> list = actions.getList();
            String value="";
            for (int i = 0; i <list.size(); i++) {

                try {
                 if(dataTable!=null){
                     Map<String,String> mapOverride = dataTable.asMap(String.class,String.class);
                     if(mapOverride.containsKey(list.get(i).getElement())){
                         value = mapOverride.get(list.get(i).getElement());
                     }
                 }
                 if(map.containsKey(value)){
                     value = map.get(value);
                 } if(value.contains("USER.")){
                        String suffix = value.substring(5);
                        value = getProfileUser(suffix, userDTO);
                    }
                Locators locators = getValueElement(page,list.get(i).getElement());
                By by = getBy(driver, locators.getType(), locators.getValue());
//                    scrollToElement(this.driver, by);
                if(list.get(i).getCondition()!=null){
                    waitAction = getWaitAction(driver,list.get(i).getTimeout());
                    if(waitAction!=null){
                        temp = true;
                    }else{
                        waitAction = this.wait;
                    }
                    switch (list.get(i).getCondition()){
                        case "DISPLAYED":
                            if(temp){
                                flag = temp;
                            }
                            flag = waitAction.until(new ExpectedCondition<Boolean>() {
                                public Boolean apply(WebDriver driver) {
                                    return driver.findElement(by).isDisplayed();
                                }
                            });
                            if(list.get(i).getInputType()!=null){
                                runType(driver,by, list.get(i).getInputType(),value);
                            }

                            break;
                        case "NOT_DISPLAYED":
                            if(temp){
                                flag = temp;
                            }
//                            flag = waitAction.until(ExpectedConditions.invisibilityOfElementLocated(by));
                            flag = waitAction.until(new ExpectedCondition<Boolean>() {
                                public Boolean apply(WebDriver driver) {
                                    return !driver.findElement(by).isDisplayed();
                                }
                            });
                            if(list.get(i).getInputType()!=null){
                                runType(driver,by, list.get(i).getInputType(),value);
                            }
                            break;
                        case "ENABLED":
                            if(temp){
                                flag = temp;
                            }
//                            flag = waitAction.until(ExpectedConditions.invisibilityOfElementLocated(by));
                            flag = waitAction.until(new ExpectedCondition<Boolean>() {
                                public Boolean apply(WebDriver driver) {
                                    return driver.findElement(by).isEnabled();
                                }
                            });
                            if(list.get(i).getInputType()!=null){
                                runType(driver,by, list.get(i).getInputType(),value);
                            }
                            break;
                        case "NOT_ENABLED":
                            if(temp){
                                flag = temp;
                            }
//                            flag = waitAction.until(ExpectedConditions.invisibilityOfElementLocated(by));
                            flag = waitAction.until(new ExpectedCondition<Boolean>() {
                                public Boolean apply(WebDriver driver) {
                                    return !driver.findElement(by).isEnabled();
                                }
                            });
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
                scrollToElement(this.driver, by);
                driver.findElement(by).sendKeys(value);
                break;
            case "click":
                scrollToElement(this.driver, by);
                driver.findElement(by).click();
                break;
            default:
                throw new RuntimeException();


        }
    }
    public void CloseBrowser(String title){
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
        System.out.println("get locator in common page");
         page = getPage();
        return getValueElement(page, id);
    }
    public Page getPage(){
        Page page = this.map.get("CommonPage");
        return page;
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
            case "CSS":
                by = By.cssSelector(value);
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
        String value = null;
        try {
            value=locator.getValue();
        }catch (Exception e){

        }

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
    public String getReplaceValue(String value, UserDTO userDTO, Map<String, String> mapSaveText){
        String replaceValue = null;
        if(value.toLowerCase().contains("user.")){
            String suffix = value.substring(5);
            replaceValue = getProfileUser(suffix, userDTO);
        }
        else if(value.toLowerCase().contains("unique.")){
            String suffix = value.substring(7);
            if(suffix.contains("number.")){
                String number = suffix.substring(7);
                replaceValue= getRandomNumber(Integer.parseInt(number));
            }else{
                replaceValue =  getRandomCharacter(value.replace("UNIQUE.","").replace("number.",""));
            }

        }
        else if(mapSaveText.containsKey(value)){
            replaceValue =mapSaveText.get(value);
        }
        else{
            replaceValue = value;
        }
        return replaceValue;

    }

    public UserDTO CreateUser(){
        fake = new FakerData();
         return fake.CreateUser();

    }
    public void deleteFile(String path){
        if(path!=""){
            File f = new File(path);
            f.delete();
        }

    }
//    public String getText(Map<String, String> map, List<UserDTO> listUserDTO, String content){
//        Stream<String> var1 = Arrays.stream(Configuration.PREFIX);
//        if(var1.anyMatch(content.toLowerCase()::startsWith)){
//            String[] arrCharacter = content.split(" ");
//            for(int i=0;i<Configuration.PREFIX.length;i++){
//                for(int k=0;k<arrCharacter.length;k++){
//                    if(arrCharacter[k].toLowerCase().contains(Configuration.PREFIX[i])){
//
//                    }
//                }
//            }
//        }else{
//            return content;
//        }
//        String text =null;
//        if (map.containsKey(content)) {
//            text = map.get(content);
//            return text;
//        }
//        if(content.contains("USER.")){
//
//        }
//        return "";
//    }
//    public String getValue(String key, List<UserDTO> listUserDTO, Map<String, String> mapSaveText){
//        String value =null;
//        String count;
//        UserDTO userDTO;
//        switch (key){
//            case "user.":
//                String suffix = key.substring(5);
//                String suffix2= key.substring(6);
//                boolean isDigit = checkIsDigit(suffix2);
//                if(isDigit){
//                     userDTO = listUserDTO.get(Integer.parseInt(suffix2));
//                }else{
//                    userDTO = listUserDTO.get(listUserDTO.size()-1);
//                }
//                value = getProfileUser(suffix, userDTO);
//            case "key.":
////               getValueKey("ds");
//        }
//        return value;
//
//    }
    public String getProfileUser(String suffix, UserDTO userDTO){
        String value ="";
        switch (suffix){
            case "firstName":
                value= userDTO.getFirstname();
                break;
            case "lastName":
                value= userDTO.getLastname();
                break;
            case "dob":
                value= userDTO.getDob();
                break;
            case "email":
                value= userDTO.getEmail();
                break;
            case "phoneNumber":
                value= userDTO.getPhoneNumber();
                break;
            case "address":
                value= userDTO.getAddress();
                break;
            default:
                System.out.println("Not Found User");
        }
        return value;
    }
//    public String getValueKey(String key, Map<String, String> map){
//        String value =null;
//        if(map.containsKey(key)){
//            value =  map.get(key);
//        }else{
//            System.out.println("Not Found key in cached");
//            Assert.assertTrue(false);
//        }
//        return value;
//    }
//    public boolean checkIsDigit(String value){
//        try {
//            Integer.parseInt(value);
//            return true;
//        }catch (Exception e){
//            return false;
//        }
//    }
//
    public  String getRandomCharacter(String content){
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int N = alphabet.length();
        Random r = new Random();
        String value = "";
        for (int i = 0; i < 10; i++) {
            value = value+alphabet.charAt(r.nextInt(N));
        }
        return value+content;
    }
    public  String getRandomNumber(int length){
        final String number = "0123456789";
        final int N = number.length();
        Random r = new Random();
        String value = "";
        for (int i = 0; i < length; i++) {
            value = value+number.charAt(r.nextInt(N));
        }
        return value;
    }
    public static void main(String[] args) {
        final String number = "0123456789";
        final int N = number.length();
        Random r = new Random();
        String value = "";
        for (int i = 0; i < 10; i++) {
            value = value+number.charAt(r.nextInt(N));
        }
        System.out.println("value =="+ value);

    }
    public void ExecutePostmanCollectionWithLink(String link) throws IOException, InterruptedException {
        String[] arrCommand = new String[]{"newman","run",null};
        arrCommand[2] = link;
        List<String> commandLineAggrument = new ArrayList<>(Arrays.asList(arrCommand));
        if(System.getProperty("os.name").toLowerCase().contains("win")){
            commandLineAggrument.add(0,"cmd");
            commandLineAggrument.add(1,"/c");
        }
        ExecuteWithOutToFile("", commandLineAggrument.toArray(new String[0]));
    }
    public Page readYamlFile(String yaml, Map<String, String> mapFileYaml){
        Page page = yamlExecute.updateYaml(yaml,this.map, mapFileYaml);
        return page;
    }
    public void closeBrowser(){
        if(driver!=null){
            driver.quit();
            driver=null;
        }

    }

}
