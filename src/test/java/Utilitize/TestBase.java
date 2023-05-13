package Utilitize;

import ManageDriver.Hook;
import Nada.MessageEmail;
import Retrofit.ControllerAPI;
import Retrofit.ServiceAPI;
import StepsDefinition.Steps;
import bean.*;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.impl.JavaScript;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import org.apache.commons.exec.ExecuteException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import retrofit2.Response;

import java.io.*;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.text.ParseException;
import java.time.Duration;
import java.util.*;

//import static com.codeborne.selenide.Selenide.$;

public class TestBase {
    public WebDriver driver;
    private JsonNode arrayJsonNode;
    FakerData fake;
    public WebDriverWait wait;
    public Hook hook;
    public Actions actions;
    public Map<String, Page> map = new HashMap<>();
    ExecuteYaml yamlExecute = new ExecuteYaml();
    ProcessFile fileProcess = new ProcessFile();


    public TestBase() {
        Configuration.ReadConfig();
       hook = new Hook();
    }


    public void getDriver() {
       hook.getInstance(Configuration.WEB_BROWSER);
        driver = hook.getWebdriver();
        wait = getWait();
    }

    public WebDriver OpenBrowser(String URl) {
        try {
            if (URl.equals("refresh-page")) {
                hook.getWebdriver().navigate().refresh();
            }
            if (!URl.equals("refresh-page")) {
                hook.getWebdriver().get(URl.replace("\"", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
        return hook.getWebdriver();
    }

    public void mouseAction(Page page, String action, String element, Map<String, String> map) {
        Locators locators = getValueElement(page, element);
        String valueElement = getValueElementToWithText(locators, element, map);
//        WebDriverWait wait = getWait(driver);
        By by = getBy(driver, locators.getType(), valueElement);
        WebElement ele = driver.findElement(by);
        beforeAction(ele);
        try {
            switch (action) {
                case "click":
                    wait.until(ExpectedConditions.elementToBeClickable(by));
                    String disabled = driver.findElement(by).getAttribute("disabled");
                    if (disabled != null) {
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

    public void switchTab(String index) {
        try {
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            if(Util.isNumber(index)){
                driver.switchTo().window(tabs.get(Integer.parseInt(index) - 1));
            }else{
                System.out.println(" please set index to switch browser");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }


    public void showUI(Page page, String element, String status, Map<String, String> map) {
        System.out.println("thread show=== "+ Thread.currentThread().getId());
        try {
            Locators locators = getValueElement(page, element);
            String valueElement = getValueElementToWithText(locators, element, map);
//            WebDriverWait wait = getWait(driver);
            By by = getBy(driver, locators.getType(), valueElement);
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
                case "EXIST":
                    wait.until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            return driver.findElements(by).size() > 0;
                        }
                    });
                    break;
                case "NOT_EXIST":
                    wait.until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            return !(driver.findElements(by).size() > 0);
                        }
                    });
                    break;
                case "SELECTED":
                    wait.until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            return driver.findElement(by).isSelected();
                        }
                    });
                    break;
                case "NOT_SELECTED":
                    wait.until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            return !(driver.findElement(by).isSelected());
                        }
                    });
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }

    public void verifyText(Page page, String element, String content, boolean status, Map<String, String> map,List<UserDTO> listUserDto) {
        Locators locators = getValueElement(page, element);
        By by = getBy(driver, locators.getType(), locators.getValue());
        scrollAction(element, page);

        try {
            if (map.containsKey(content)) {
                content = map.get(content);
            }
            if (content.toLowerCase().contains("user.")) {
                content = content.substring(5);
                content = getProfileUser(content, listUserDto);
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
            Assert.fail("Actual Text verify: " + driver.findElement(by).getText() + "\n + Expect Text verify: " + content);
        }

    }

    public void ActionType(Page page, String element, String content, Map<String, String> map, List<UserDTO> listUserDto) {
        try {
            String text = content;
            Locators locators = getValueElement(page, element);
//            WebDriverWait wait = getWait(driver);
            By by = getBy(driver, locators.getType(), locators.getValue());
            wait.until(ExpectedConditions.elementToBeClickable(by));
            if (map.containsKey(content)) {
                text = map.get(content);
            } else if (content.contains("USER.")) {
                String suffix = content.substring(5);
                text = getProfileUser(suffix, listUserDto);
            } else if (content.contains("UNIQUE.")) {
                text = getReplaceValue(content, listUserDto, map);
            }
//          else if(content.contains("keyboard.")){
//                text = text.replace("keyboard.","");
//               driver.findElement(by).sendKeys(Keys.getKeyFromUnicode('a'));
//
//            }
            WebElement ele = driver.findElement(by);
            beforeAction(ele);
            ele.click();
            ele.clear();
            ele.sendKeys(text);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }

    public void runCollection(String collectionJson, String dataFile, Map<String, String> datatable, Scenario scenario, List<UserDTO> listUserDto, Map<String, String> mapSavetext) {
        collectionJson = Configuration.PATH_POSTMAN + "/collection/" + collectionJson;
        dataFile = Configuration.PATH_POSTMAN + "/data-files/" + dataFile;
        try {
            this.arrayJsonNode = (new ObjectMapper()).readTree(new File(dataFile));
            JsonNode jsonNode = UpdateNodeJson(datatable, listUserDto, mapSavetext);
            System.out.println("json node== " + jsonNode);
            ObjectMapper mapper = new ObjectMapper();
            dataFile = Configuration.PATH_POSTMAN + "/data-files/" + scenario.getName() + "_" + System.currentTimeMillis() + ".json";
            mapper.writeValue(Paths.get(dataFile).toFile(), jsonNode);
            String[] arrComman = new String[]{"newman", "run", null, null, null};
            arrComman[2] = collectionJson;
            arrComman[3] = "-d";
            arrComman[4] = dataFile;
            List<String> commandLineAggrument = new ArrayList<>(Arrays.asList(arrComman));
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                commandLineAggrument.add(0, "cmd");
                commandLineAggrument.add(1, "/c");
            }
            ExecuteWithOutToFile(dataFile, commandLineAggrument.toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }

    public JsonNode UpdateNodeJson(Map<String, String> dataTable, List<UserDTO> listUserDto, Map<String, String> mapSaveText) throws ParseException {
//        if(dataTable!=null){

        Iterator<JsonNode> var1 = this.arrayJsonNode.iterator();
        if (var1.hasNext()) {
            JsonNode jsonNode = var1.next();
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> jsonNodeMap = fields.next();
                String key = jsonNodeMap.getKey();
                String value = jsonNodeMap.getValue().asText();
                List<String> list = new ArrayList<>(Arrays.asList("user.", "unique.", "key."));
                Iterator<String> var = list.iterator();
                for (int i = 0; i < list.size(); i++) {
                    String field_key = list.get(i);
                    if (value.contains(field_key)) {
                        ((ObjectNode) jsonNode).put(key, getReplaceValue(value, listUserDto, mapSaveText));
                    }
                    if (Objects.nonNull(dataTable) && dataTable.containsKey(key)) {
                        String data = dataTable.get(key).toString();
                        data = getReplaceValue(data, listUserDto, mapSaveText);
                        ((ObjectNode) jsonNode).put(key, data);
                    }
                }

            }

        }

        return this.arrayJsonNode;
//        }

    }

    public void keyBoard(Page page, String element, String action) {
        Actions actions = new Actions(driver);
//        WebDriverWait wait = getWait(driver);
        Locators locators = getValueElement(page, element);
        By by = getBy(driver, locators.getType(), locators.getValue());
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement ele = driver.findElement(by);
        beforeAction(ele);
        switch (action) {
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
                try {
                    actions.sendKeys(Keys.valueOf(action)).perform();
                } catch (NotFoundException e) {
                    System.out.println("Not support case  " + action);
                    e.printStackTrace();
                    Assert.assertTrue(false);
                }

        }


    }

    public void saveTextElement(Page page, String element, String text, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        try {
            Locators locators = getValueElement(page, element);
//            WebDriverWait wait = getWait(driver);
            By by = getBy(driver, locators.getType(), locators.getValue());
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            String element_text = "input".equals(driver.findElement(by).getTagName()) ? driver.findElement(by).getAttribute("value") : driver.findElement(by).getText();
            map.put("KEY." + text, element_text);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }

    public void scrollAction(String element, Page page) {
        WebElement ele = null;
        try {
            Locators locators = getValueElement(page, element);
//            WebDriverWait wait = getWait(driver);
            By by = getBy(driver, locators.getType(), locators.getValue());
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            ele = driver.findElement(by);
            Actions actions = new Actions(driver);
            actions.moveToElement(ele);
            actions.perform();
        } catch (Exception e) {
            try {
                e.printStackTrace();
                System.out.println("try to scroll by javascript");
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
            } catch (RuntimeException r) {
                r.printStackTrace();
                Assert.assertTrue(false);
            }

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

    public void ExecuteWithOutToFile(String path, String... args) {
        try {
            int exitCode = 0;
            Process process = Runtime.getRuntime().exec(args);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder outputBuilder;
            String line;
            for (outputBuilder = new StringBuilder(); process.isAlive(); exitCode = process.waitFor()) {
                while ((line = stdInput.readLine()) != null) {
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

        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        } finally {
            deleteFile(path);
        }

    }

    public void executeAction(Page page, String action, DataTable dataTable, Map<String, String> map, List<UserDTO> listUserDto) {
        ActionsTest actions = getActions(page, action);
        WebDriverWait waitAction;
        boolean flag = false;
        boolean temp = false;
        List<ActionElements> list = actions.getList();
        String value = "";
        for (int i = 0; i < list.size(); i++) {

            try {
                if (dataTable != null) {
                    Map<String, String> mapOverride = dataTable.asMap(String.class, String.class);
                    if (mapOverride.containsKey(list.get(i).getElement())) {
                        value = mapOverride.get(list.get(i).getElement());
                    }
                }
                if (map.containsKey(value)) {
                    value = map.get(value);
                }
                if (value.contains("USER.")) {
                    String suffix = value.substring(5);
                    value = getProfileUser(suffix, listUserDto);
                }
                Locators locators = getValueElement(page, list.get(i).getElement());
                By by = getBy(driver, locators.getType(), locators.getValue());
//                    scrollToElement(this.driver, by);
                if (list.get(i).getCondition() != null) {
                    waitAction = getWaitAction(list.get(i).getTimeout());
                    if (waitAction != null) {
                        temp = true;
                    } else {
                        waitAction = this.wait;
                    }
                    switch (list.get(i).getCondition()) {
                        case "DISPLAYED":
                            if (temp) {
                                flag = temp;
                            }
                            flag = waitAction.until(new ExpectedCondition<Boolean>() {
                                public Boolean apply(WebDriver driver) {
                                    return driver.findElement(by).isDisplayed();
                                }
                            });
                            if (list.get(i).getInputType() != null) {
                                runType(driver, by, list.get(i).getInputType(), value);
                            }

                            break;
                        case "NOT_DISPLAYED":
                            if (temp) {
                                flag = temp;
                            }
//                            flag = waitAction.until(ExpectedConditions.invisibilityOfElementLocated(by));
                            flag = waitAction.until(new ExpectedCondition<Boolean>() {
                                public Boolean apply(WebDriver driver) {
                                    return !driver.findElement(by).isDisplayed();
                                }
                            });
                            if (list.get(i).getInputType() != null) {
                                runType(driver, by, list.get(i).getInputType(), value);
                            }
                            break;
                        case "ENABLED":
                            if (temp) {
                                flag = temp;
                            }
//                            flag = waitAction.until(ExpectedConditions.invisibilityOfElementLocated(by));
                            flag = waitAction.until(new ExpectedCondition<Boolean>() {
                                public Boolean apply(WebDriver driver) {
                                    return driver.findElement(by).isEnabled();
                                }
                            });
                            if (list.get(i).getInputType() != null) {
                                runType(driver, by, list.get(i).getInputType(), value);
                            }
                            break;
                        case "NOT_ENABLED":
                            if (temp) {
                                flag = temp;
                            }
//                            flag = waitAction.until(ExpectedConditions.invisibilityOfElementLocated(by));
                            flag = waitAction.until(new ExpectedCondition<Boolean>() {
                                public Boolean apply(WebDriver driver) {
                                    return !driver.findElement(by).isEnabled();
                                }
                            });
                            if (list.get(i).getInputType() != null) {
                                runType(driver, by, list.get(i).getInputType(), value);
                            }
                            break;
                    }

                } else {
//                    wait = getWait(driver);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(by));
                    runType(driver, by, list.get(i).getInputType(), value);
                }

            } catch (Exception e) {
                e.printStackTrace();
                Assert.assertTrue(flag);
                System.out.println("Error at action have element " + list.get(i).getElement() + " and input " + list.get(i).getInputType());
            }
        }

//        By by = getBy(driver, locators.getType(), locators.getValue());

    }

    public void runType(WebDriver driver, By by, String status, String value) {
        switch (status) {
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

    public void CloseBrowser(String title) {
        boolean flag = false;
        try {
            Set<String> windows = driver.getWindowHandles();
            String mainWindow = driver.getWindowHandle();
            for (String handle : windows) {
                driver.switchTo().window(handle);
                String pageTitle = driver.getTitle();
                if (pageTitle.equalsIgnoreCase(title)) {
                    driver.close();
                    flag = true;
                    System.out.println("Closed the  '" + pageTitle + "' Tab now ...");
                }
            }
            driver.switchTo().window(mainWindow);
            Assert.assertTrue(flag);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(flag);
        }


    }


    public WebDriverWait getWait() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(Configuration.TIME_OUT));
        return wait;
    }

    public WebDriverWait getWaitAction(long duration) {
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
        System.out.println("Not Found element " + id + " in file " + Steps.titlePage + ".yaml");
        System.out.println("get locator in common page");
        page = getPage();
        return getValueElement(page, id);
    }

    public Page getPage() {
        Page page = this.map.get("CommonPage");
        return page;
    }

    public ActionsTest getActions(Page page, String action_id) {
        ActionsTest actions = null;
        try {
            Map<String, ActionsTest> map = page.getMapActions();
            actions = map.get(action_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return actions;
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

    public String getValueElementToWithText(Locators locator, String element, Map<String, String> map) {
        String elementText = null;
        String value = null;
        try {
            value = locator.getValue();
        } catch (Exception e) {

        }

        if (element.contains("with text")) {
            String split[] = element.split("with text");
            elementText = split[1].trim().replace("\"", "");
        }
        if (value.contains("{text}")) {
            if (map.containsKey(elementText)) {
                elementText = map.get(elementText);
            }
            return value.replace("{text}", elementText);
        }
        return value;

    }

    public String getElement(String element) {
        if (element.contains("with text")) {
            String split[] = element.split("with text");
            element = split[0].trim();
            return element;
        } else {
            return element;
        }
    }

    public String getReplaceValue(String value, List<UserDTO> listUserDto, Map<String, String> mapSaveText) throws ParseException {
        String replaceValue = null;
        if (value.toLowerCase().contains("user.")) {
            String suffix = value.substring(5);
            replaceValue = getProfileUser(suffix, listUserDto);
        } else if (value.toLowerCase().contains("unique.")) {
            String suffix = value.substring(7);
            if (suffix.contains("number.")) {
                String number = suffix.substring(7);
                replaceValue = getRandomNumber(Integer.parseInt(number));
            } else {
                replaceValue = getRandomCharacter(value.replace("UNIQUE.", "").replace("number.", ""));
            }

        } else if (mapSaveText.containsKey(value)) {
            replaceValue = mapSaveText.get(value);
        } else {
            replaceValue = value;
        }
        return replaceValue;

    }

    public List<UserDTO> CreateUser(List<UserDTO> list) throws ParseException {
        fake = new FakerData();
         list = fake.CreateUser(list);
         return list;

    }

    public void deleteFile(String path) {
        if (path != "") {
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
    public String getProfileUser(String suffix, List<UserDTO> listUserDto) throws ParseException {
        String value = "";
        UserDTO userDTO = null;
        if(suffix.contains(".")){
            String[] arr = suffix.split("\\.");
            if(isNumber(arr[0])){
                userDTO =  listUserDto.get(Integer.parseInt(arr[0])-1);
                suffix = arr[1];
            }else{
                throw new NotFoundException("not support keyword "+ suffix);
            }
        }else{
            userDTO = listUserDto.get(0);
        }
        switch (suffix) {
            case "firstName":
                value = userDTO.getFirstName();
                break;
            case "lastName":
                value = userDTO.getLastName();
                break;
            case "middleName":
                value = userDTO.getMiddleName();
                break;
            case "dob":
                value =   Util.convertMilisecondsToDob(userDTO.getDob());
                break;
            case "email":
                value = userDTO.getEmail();
                break;
            case "phoneNumber":
                value = userDTO.getUserAddresses().getPhoneNumber();
                break;
            case "city" :
                value =   userDTO.getUserAddresses().getCity();
                break;
            case "state" :
                value =   userDTO.getUserAddresses().getState();
                break;
            case "street" :
                value =   userDTO.getUserAddresses().getStreetOne();
                break;
            case "zip" :
                value =   userDTO.getUserAddresses().getZip();
                break;
            case "password" :
                value =   userDTO.getPassword();
                break;
            default:
                System.out.println("not support key value");
                throw new RuntimeException();
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
    public String getRandomCharacter(String content) {
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int N = alphabet.length();
        Random r = new Random();
        String value = "";
        for (int i = 0; i < 10; i++) {
            value = value + alphabet.charAt(r.nextInt(N));
        }
        return value + content;
    }

    public String getRandomNumber(int length) {
        final String number = "0123456789";
        final int N = number.length();
        Random r = new Random();
        String value = "";
        for (int i = 0; i < length; i++) {
            value = value + number.charAt(r.nextInt(N));
        }
        return value;
    }


    public void ExecutePostmanCollectionWithLink(String link) throws IOException, InterruptedException {
        String[] arrCommand = new String[]{"newman", "run", null};
        arrCommand[2] = link;
        List<String> commandLineAggrument = new ArrayList<>(Arrays.asList(arrCommand));
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            commandLineAggrument.add(0, "cmd");
            commandLineAggrument.add(1, "/c");
        }
        ExecuteWithOutToFile("", commandLineAggrument.toArray(new String[0]));
    }

    public Page readYamlFile(String yaml, Map<String, String> mapFileYaml) {
        Page page = yamlExecute.updateYaml(yaml, this.map, mapFileYaml);
        return page;
    }

    public void createFile(String file_name, String header) throws IOException {
        try {
            String[] var = file_name.split("\\.");
            String fileType = var[1];
            String fileName = var[0];
            String[] headerName = header.replace("\"", "").split(",");
            switch (fileType) {
                case "csv":
                    fileProcess.createFileCSV(fileName, headerName);
                    break;
                case "xlsx":
                    fileProcess.createFileExcel(fileName, headerName, fileType);
                    break;
                case "xls":
                    fileProcess.createFileExcel(fileName, headerName, fileType);
                    break;
                default:
                    throw new RuntimeException("Not support with file type " + fileType);
            }
            Assert.assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }


    }

    public void WriteExcel(String data, String fileName, Map<String, String> map) throws IOException, InvalidFormatException {
        fileProcess.WriteDataExcel(data, fileName, map);
    }

    public void getDataFromFile(String data, String fileName, String element, Page page) throws IOException {
        try {
            String[] arr = fileName.split("\\.");
            String fileType = arr[1];
            fileName = arr[0];
            switch (fileType) {
                case "csv":
                    data = fileProcess.getDataFromCSV(data, fileName);
                    Type(data, element, page);
                    break;
                case "xlsx":
                    data = fileProcess.getDataFormExcel(data, fileName, fileType);
                    Type(data, element, page);
                    break;
                case "xls":
                    data = fileProcess.getDataFormExcel(data, fileName, fileType);
                    Type(data, element, page);
                    break;
                default:
                    throw new RuntimeException("Not found support file " + fileType);
            }
        } catch (RuntimeException | InvalidFormatException r) {
            r.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    public void actionDragAndDrop(String element, String target, Page page) {
        try {
            Locators locatorsFrom = getValueElement(page, element);
            By byFrom = getBy(driver, locatorsFrom.getType(), locatorsFrom.getValue());
            Locators locatorsTo = getValueElement(page, target);
            By byTo = getBy(driver, locatorsTo.getType(), locatorsTo.getValue());
            wait.until(ExpectedConditions.visibilityOfElementLocated(byFrom));
            wait.until(ExpectedConditions.visibilityOfElementLocated(byTo));
            new Actions(driver).dragAndDrop(driver.findElement(byFrom), driver.findElement(byTo)).perform();
            Assert.assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Not execute drag and drop", " execute");
        }
    }

    public void actionDragAndDropByJS(String element, String target, Page page) {
        try {
            Locators locatorsFrom = getValueElement(page, element);
            By byFrom = getBy(driver, locatorsFrom.getType(), locatorsFrom.getValue());
            Locators locatorsTo = getValueElement(page, target);
            By byTo = getBy(driver, locatorsTo.getType(), locatorsTo.getValue());
            wait.until(ExpectedConditions.visibilityOfElementLocated(byFrom));
            wait.until(ExpectedConditions.visibilityOfElementLocated(byTo));
            WebElement eleFrom = driver.findElement(byFrom);
            WebElement eleTo = driver.findElement(byTo);
            JavaScript js = new JavaScript("drag_and_drop_script.js");
            js.execute(driver, eleFrom, eleTo);
            Assert.assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertEquals("Not execute drag and drop", " execute");
        }


    }

    public void iwaitSeconds(String seconds) {
        String[] arrSecond = seconds.split("-");
        if (arrSecond.length > 2) {
            throw new RuntimeException("please input less two number");
        }
        if (!isNumber(arrSecond[0]) && !isNumber(arrSecond[1])) {
            throw new RuntimeException("seconds must be a number");
        }
        if (arrSecond.length == 2) {
            if (Integer.parseInt(arrSecond[0]) > Integer.parseInt(arrSecond[1])) {
                throw new RuntimeException("Wait seconds range: wait seconds from should be less than wait seconds to");
            } else {
                int number = getRandomNumber(Integer.parseInt(arrSecond[0]), Integer.parseInt(arrSecond[1]));
                Selenide.sleep((long) number * 1000L);
            }
        } else {
            Selenide.sleep((long) Integer.parseInt(arrSecond[0]) * 1000L);
        }


    }

    public boolean checkCssAttribute(Page page, String element, String property, String value, Map<String, String> map) {
        By temp = null;
        boolean flag = false;
        try {
            Locators Locator = getValueElement(page, element);
            By by = getBy(driver, Locator.getType(), Locator.getValue());
            if (map.containsKey(value)) {
                value = map.get(value);
            }
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return driver.findElement(by).isDisplayed();
                }
            });
            scrollToElement(driver, by);
            flag = driver.findElement(by).getAttribute(property).equals(value);
            Assert.assertTrue(flag);
            temp = by;
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertEquals(driver.findElement(temp).getAttribute(property), value);
        }
        return flag;

    }

    public void SwitchFrame(Page page,String element) {
        try {
            Locators Locator = getValueElement(page, element);
            By by = getBy(driver, Locator.getType(), Locator.getValue());
            driver.switchTo().frame(driver.findElement(by));
        } catch (Exception e) {
            Assert.assertFalse("Not Switch to jFrame with element " + element, false);
            e.printStackTrace();
        }
    }
    public void SwitchDefaulFrame(){
        driver.switchTo().defaultContent();
    }
    public void Type(String data, String element, Page page) {
        Locators locators = getValueElement(page, element);
        WebDriverWait wait = getWait();
        By by = getBy(driver, locators.getType(), locators.getValue());
//        wait.until(ExpectedConditions.elementToBeClickable(by));
        driver.findElement(by).click();
        driver.findElement(by).sendKeys(data);
    }

    public void writeFile(String data, String fileName, Map<String, String> map) throws IOException, InvalidFormatException {
        String[] arr = fileName.split("\\.");
        String fileType = arr[1];
        switch (fileType) {
            case "csv":
                fileProcess.WriteDataCSV(data, fileName, map);
                break;
            case "xls":
                fileProcess.WriteDataExcel(data, fileName, map);
                break;
            case "xlsx":
                fileProcess.WriteDataExcel(data, fileName, map);
                break;
            default:
               Assert.assertTrue("Not support file type " + fileType,false);
            break;
        }


    }
    public List<UserDTO> getUserFormFile(List<UserDTO> list, String nameFile){
        list = yamlExecute.getUserFormFile(nameFile, list);
        return  list;
    }
    public void NavigateAnotherPage(String email, Map<String, String> map, List<UserDTO> list) throws ParseException, IOException, InterruptedException {
        if(map.containsKey(email)){
            email = map.get(email);
        }else if (email.toLowerCase().contains("user.")) {
            email = email.substring(5);
            email   = getProfileUser(email, list);
        }
        ServiceAPI serviceAPI = ControllerAPI.getServiceFromURL("https://getnada.com");
        Response<MessageEmail> response = ControllerAPI.getMessageID(serviceAPI, email.toLowerCase().trim());
        if(driver!=null){
            driver.switchTo().newWindow(WindowType.TAB);
            driver.get("https://getnada.com/message/"+response.body().msgs[0].uid);
        }else{
            throw new NoSuchWindowException("Not Found driver to open link verify");
        }

    }

    public boolean isNumber(String data) {
        try {
            Integer.parseInt(data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getRandomNumber(int min, int max) {
        return (new SecureRandom()).nextInt(max - min + 1) + min;
    }

    public void closeBrowser() {
        hook.quit();
    }

    public void beforeAction(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element);
            actions.perform();
        } catch (Exception e) {
            try {
                System.out.println("try to scroll by javascript");
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true);", element);

            } catch (RuntimeException r) {
                throw new RuntimeException(r.getMessage());
            }

        }
    }

    public static void main(String[] args) {
        String exmp = "random[a-z0-9._-]{0,15}@";
    }

}
