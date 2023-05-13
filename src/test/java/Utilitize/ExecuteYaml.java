package Utilitize;

import bean.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import com.github.javafaker.Faker;
import org.junit.Assert;

import java.io.File;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;

public class ExecuteYaml {

    public String ConvertFileYaml(File f) {
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            Object obj = objectMapper.readValue(f, Object.class);
            ObjectMapper json = new ObjectMapper();
            return json.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "can not read file yaml";
    }

    public Page updateYaml(String pageYaml, Map<String, Page> mapPage, Map<String, String> mapFileyaml) {
        Page page = null;
        try {
            Locators locators;
            if (mapPage.containsKey(pageYaml)) {
                page = mapPage.get(pageYaml);
            }
            if(!mapPage.containsKey("CommonPage")){
                getCommonPage("CommonPage", mapPage, mapFileyaml, page);
            }
             if(!mapPage.containsKey(pageYaml)){
                String file_path = mapFileyaml.get(pageYaml+".yaml");
//                String json = ConvertFileYaml(new File(file_path));
//                String file_name = System.getProperty("user.dir")+"/src/test/resources/Pages/"+pageYaml+".yaml";
                String json = ConvertFileYaml(new File(file_path));
                page = new Page();
                List<Elements> elementsList = new LinkedList<>();
                JSONObject object = new JSONObject(json);
                JSONArray arr = new JSONArray(object.get("elements").toString());
                for (int i = 0; i < arr.length(); i++) {
                    Elements elements = new Elements();
                    JSONObject obj = new JSONObject(arr.get(i).toString());
                    elements.setId(obj.get("id").toString());
                    elements.setDescription(obj.get("description").toString());
                    JSONArray arrLocator = new JSONArray(obj.get("locators").toString());
                    for (int j = 0; j < arrLocator.length(); j++) {
                        JSONObject objLocator = new JSONObject(arrLocator.get(j).toString());
                        if(objLocator.get("device").toString().equals(Configuration.WEB_BROWSER) || objLocator.get("device").toString().equals("WEB") ){
                            locators = new Locators();
                            locators.setDevice(objLocator.get("device").toString());
                            locators.setType(objLocator.get("type").toString());
                            locators.setValue(objLocator.get("value").toString());
                            elements.setLocator(locators);
                        }

                    }
                    elementsList.add(elements);
                }
                if (object.has("actions")) {
                    Map<String, ActionsTest> mapAction = new LinkedHashMap<>();
//                    List<ActionElements> listActionElements = new LinkedList<>();
                    JSONArray arrActions = new JSONArray(object.get("actions").toString());
                    for (int i = 0; i < arrActions.length(); i++) {
                        List<ActionElements> listActionElements = new LinkedList<>();
                        JSONObject objAction = new JSONObject(arrActions.get(i).toString());
                        ActionsTest action = new ActionsTest();
                        action.setAction_id(objAction.get("id").toString());
                        action.setDescription(objAction.get("description").toString());
                        JSONArray arrActionElements = new JSONArray(objAction.get("actionElements").toString());
                        for (int j = 0; j < arrActionElements.length(); j++) {
                            JSONObject objActionElements = new JSONObject(arrActionElements.get(j).toString());
                            ActionElements actionElements = new ActionElements();
                            actionElements.setElement(objActionElements.get("element").toString());
                            if (objActionElements.has("infoType")) {
                                actionElements.setInfoType(objActionElements.get("infoType").toString());
                            }
                            if (objActionElements.has("inputType")) {
                                actionElements.setInputType(objActionElements.get("inputType").toString());
                            }
                            if(objActionElements.has("condition")){
                                actionElements.setCondition(objActionElements.get("condition").toString());
                            }
                            if(objActionElements.has("timeout")){
                                actionElements.setTimeout(Long.parseLong(objActionElements.get("timeout").toString()));
                            }
                            listActionElements.add(actionElements);
                            action.setList(listActionElements);
                        }
                        mapAction.put(objAction.get("id").toString(),action);
                    }
                    page.setMapActions(mapAction);
                }
                page.setElements(elementsList);
                mapPage.put(pageYaml,page);
            }
        }catch(Exception e){
            e.printStackTrace();
            Assert.assertTrue(false);
        }
        return page;
    }
    public Map<String, String> findFile(File dir, Map<String, String> list) {
        File[] listFiles = dir.listFiles();
        for (File file : listFiles) {
            if (file.isFile()) {
                list.put(file.getName(), file.getAbsolutePath());

            } else if (file.isDirectory()) {
                findFile(new File(file.getAbsolutePath()), list);
            }
        }
        return list;
    }
//    public void getPath(String configPath){
//        String os = System.getProperty("os.name");
//        if (os.indexOf("Windows") != -1) {
//            configPath = configPath.replace("\", "");
//            if (configPath.indexOf("file:\\\\") != -1) {
//                configPath = configPath.replace("file:\\\\", "");
//            }
//        } else if (configPath.indexOf("file:") != -1) {
//            configPath = configPath.replace("file:", "");
//        }
//    }

//    public static void main(String[] args) {
//        ExecuteYaml exe = new ExecuteYaml();
//        exe.getPath(System.getProperty("user.dir") + "/src/test/resources/Pages");
//    }
    public void getCommonPage(String pageYaml, Map<String, Page> mapPage, Map<String, String> mapFileyaml, Page page) {
        try {
            Locators locators;
            String file_path = mapFileyaml.get(pageYaml+".yaml");
            String json = ConvertFileYaml(new File(file_path));
            page = new Page();
            List<Elements> elementsList = new LinkedList<>();
            JSONObject object = new JSONObject(json);
            JSONArray arr = new JSONArray(object.get("elements").toString());
            for (int i = 0; i < arr.length(); i++) {
                Elements elements = new Elements();
                JSONObject obj = new JSONObject(arr.get(i).toString());
                elements.setId(obj.get("id").toString());
                elements.setDescription(obj.get("description").toString());
                JSONArray arrLocator = new JSONArray(obj.get("locators").toString());
                for (int j = 0; j < arrLocator.length(); j++) {
                    JSONObject objLocator = new JSONObject(arrLocator.get(j).toString());
                    if(objLocator.get("device").toString().equals(Configuration.WEB_BROWSER) || objLocator.get("device").toString().equals("WEB") ){
                        locators = new Locators();
                        locators.setDevice(objLocator.get("device").toString());
                        locators.setType(objLocator.get("type").toString());
                        locators.setValue(objLocator.get("value").toString());
                        elements.setLocator(locators);
                    }

                }
                elementsList.add(elements);
            }
            if (object.has("actions")) {
                Map<String, ActionsTest> mapAction = new LinkedHashMap<>();
//                    List<ActionElements> listActionElements = new LinkedList<>();
                JSONArray arrActions = new JSONArray(object.get("actions").toString());
                for (int i = 0; i < arrActions.length(); i++) {
                    List<ActionElements> listActionElements = new LinkedList<>();
                    JSONObject objAction = new JSONObject(arrActions.get(i).toString());
                    ActionsTest action = new ActionsTest();
                    action.setAction_id(objAction.get("id").toString());
                    action.setDescription(objAction.get("description").toString());
                    JSONArray arrActionElements = new JSONArray(objAction.get("actionElements").toString());
                    for (int j = 0; j < arrActionElements.length(); j++) {
                        JSONObject objActionElements = new JSONObject(arrActionElements.get(j).toString());
                        ActionElements actionElements = new ActionElements();
                        actionElements.setElement(objActionElements.get("element").toString());
                        if (objActionElements.has("infoType")) {
                            actionElements.setInfoType(objActionElements.get("infoType").toString());
                        }
                        if (objActionElements.has("inputType")) {
                            actionElements.setInputType(objActionElements.get("inputType").toString());
                        }
                        if(objActionElements.has("condition")){
                            actionElements.setCondition(objActionElements.get("condition").toString());
                        }
                        if(objActionElements.has("timeout")){
                            actionElements.setTimeout(Long.parseLong(objActionElements.get("timeout").toString()));
                        }
                        listActionElements.add(actionElements);
                        action.setList(listActionElements);
                    }
                    mapAction.put(objAction.get("id").toString(),action);
                }
                page.setMapActions(mapAction);
            }
            page.setElements(elementsList);
            mapPage.put(pageYaml,page);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("reading common page failed");
        }

    }
    public List<UserDTO> getUserFormFile(String nameFile, List<UserDTO> list){
        if(list == null){
            list = new LinkedList<>();
        }
        UserDTO user = new UserDTO();
        File f  = new File(System.getProperty("user.dir") + "/src/test/resources/user/"+nameFile+".yaml");
        String json = ConvertFileYaml(f);
        System.out.println("json obect = "+ json);
        JSONObject object = new JSONObject(json);
        user.setFirstName(getStringFormKey("firstName",object));
        user.setMiddleName(getStringFormKey("middleName",object));
        user.setLastName(getStringFormKey("lastName",object));
        user.setFullName(Util.removeBlank(user.getFirstName()+" "+user.getMiddleName()+" "+user.getLastName()));
        user.setDob(Long.parseLong(getStringFormKey("dob",object)));
        user.setPrefix(getStringFormKey("prefix",object));
        user.setSuffix(getStringFormKey("suffix",object));
        user.setEmail(Util.getRandomEmail(getStringFormKey("email",object)));
        user.setEthnicities(getStringFormKey("Ethnicities",object));
        user.setGender(getStringFormKey("Genders",object));
        user.setPassword(Util.DecryptTextWithoutKey(getStringFormKey("password",object)));
        JSONObject objectAddress = new JSONObject(getStringFormKey("userAddresses",object));
        UserAddress address = new UserAddress();
        address.setCity(getStringFormKey("city",objectAddress));
        address.setState(getStringFormKey("state",objectAddress));
        address.setStreetOne(getStringFormKey("streetOne",objectAddress));
        address.setZip(getStringFormKey("zip",objectAddress));
        address.setPhoneNumber(getStringFormKey("phoneNumber",objectAddress));
        user.setUserAddresses(address);
        System.out.println("email random is "+ user.getEmail());
        list.add(user);
        return list;
    }
    public String getStringFormKey(String key,JSONObject object){
        if(object.has(key)){
            String result =  object.get(key).toString() == "null"  ? "" : object.get(key).toString();
            return result;
        }else{
            return "";
        }
    }




}
