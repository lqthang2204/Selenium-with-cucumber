package Util;

import bean.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.File;
import java.util.*;

public class ExecuteYaml {
    public String ConvertFileYaml(File f) {
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            Object obj = objectMapper.readValue(f, Object.class);
            ObjectMapper json = new ObjectMapper();
            System.out.println(obj.toString());
            return json.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "can not read file yaml";
    }
    public static void main(String[] args) {
        ExecuteYaml exe = new ExecuteYaml();
        Map<String, Page> map = new HashMap<>();
        exe.updateYaml("test", map);
    }

    public Page updateYaml(String pageYaml, Map<String, Page> mapPage) {
        Page page = null;
        try {
            Locators locators;
            if (mapPage.get(pageYaml) != null) {
                page = mapPage.get(pageYaml);
            } else {
                String file_name = System.getProperty("user.dir")+"/src/test/resources/Pages/"+pageYaml+".yaml";
                String json = ConvertFileYaml(new File(file_name));
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
                        locators = new Locators();
                        locators.setDevice(objLocator.get("device").toString());
                        locators.setType(objLocator.get("type").toString());
                        locators.setValue(objLocator.get("value").toString());
                        elements.setLocator(locators);
                    }
                    elementsList.add(elements);
                }
                if (object.has("actions")) {
                    List<Actions> listAction = new LinkedList<>();
                    List<ActionElements> listActionElements = new LinkedList<>();
                    JSONArray arrActions = new JSONArray(object.get("actions").toString());
                    for (int i = 0; i < arrActions.length(); i++) {
                        JSONObject objAction = new JSONObject(arrActions.get(i).toString());
                        Actions action = new Actions();
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
                            listActionElements.add(actionElements);
                            action.setList(listActionElements);
                        }
                        listAction.add(action);
                    }
                    page.setActions(listAction);
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
}
