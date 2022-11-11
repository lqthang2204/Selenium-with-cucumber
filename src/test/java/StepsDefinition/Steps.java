package StepsDefinition;

import Util.ExecuteYaml;
import Util.TestBase;
import bean.Page;
import bean.UserDTO;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Steps {
//    public static  WebDriver driver;
    TestBase testBase;
    ExecuteYaml execute;
//    Map<String, Page> map;
    Page page;
    Map<String, String> mapSaveText;
    public static String titlePage;
    public static Map<String, String> mapFileYaml;
    Scenario scenario;
    List<UserDTO> listUserDTO;
    UserDTO userDTO;

    public Steps() {
    }

    @Before
    public void setUp(Scenario scenario) {
        testBase = new TestBase();
        execute = new ExecuteYaml();
//        map = new HashMap<>();
        page = new Page();
        userDTO = new UserDTO();
        mapSaveText = new HashMap<>();
        mapFileYaml = new HashMap<>();
        execute.findFile(new File(System.getProperty("user.dir") + "/src/test/resources/Pages"), this.mapFileYaml);
        this.scenario = scenario;
        listUserDTO = new LinkedList<>();
//        testBase.readYamlFile("CommonPage", this.mapFileYaml);


    }
    @Before
    public void setUp2(Scenario scenario){
        System.out.println("Scenarion == "+ scenario);
        System.out.println(""+ scenario.getSourceTagNames());
        Collection<String> tags = scenario.getSourceTagNames();
        for(int i=0;i<tags.size();i++){
            System.out.println(tags.toArray()[i]);
        }



    }


    @Given("I navigate to {word}")
    public void openBrowser(String url) {
           TestBase.OpenBrowser(testBase, url);
    }

    @Given("I change the page spec to {word}")
    public void updateYaml(String yaml) {
       this.page = testBase.readYamlFile(yaml, this.mapFileYaml);

        this.titlePage = yaml;
    }


    @Given("I {word} element {}")
    public void mouseAction(String action, String element) {
        testBase.mouseAction(this.page, action, element,this.mapSaveText);
    }
    @And("I switch to browser window with index {string}")
    public void i_switch_to_browser_window_with_index(String index) {
       testBase.switchTab(Integer.parseInt(index));
    }



    @When("I type {string} into element {word}")
    public void actionType(String content, String element) {
        testBase.ActionType(this.page, element, content, this.mapSaveText, this.userDTO);

    }
    @And("I run {word} with {word} data file")
    public void i_run_postman_collection_with_data_json(String collectionFile, String dataFile) {
        testBase.runCollection(collectionFile, dataFile, (Map)null, this.scenario, this.userDTO, this.mapSaveText);

    }
    @And("I run {word} with {word} data file with override values")
    public void i_run_with_data_file_with_override_values(String collectionFile, String dataFile, DataTable dataTable) {
        testBase.runCollection(collectionFile, dataFile,  dataTable.asMap(String.class, String.class), this.scenario, userDTO, this.mapSaveText);
    }

    @And("I wait for element {} to be {}")
    public void waitTo(String element, String status) {
        testBase.showUI(this.page,element, status, this.mapSaveText);
    }
    @Given("I become a random user")
    public void i_become_a_random_user() {
         this.userDTO = testBase.CreateUser();
    }
    @And("I verify the text for element {word} is {string}")
    public void verifyText(String element, String text) {
        testBase.verifyText(this.page, element, text, true, this.mapSaveText, userDTO);
    }

    @And("I verify the exact text for element {word} is {string}")
    public void verifyExactText(String element, String text) {
        testBase.verifyText(this.page,element, text, false, this.mapSaveText, userDTO);
    }

    @Given("I save text for element {word} with key {string}")
    public void saveText(String element, String text) {
        testBase.saveTextElement(this.page,element, text, this.mapSaveText);
    }

    @Given("I click keyboard {word} button on element {word}")
    public void i_click_keyboard_button_on_element(String Key, String element) {
        testBase.keyBoard(this.page, element, Key);
    }

    @Given("I {word} text from element {word}")
    public void clearText(String action, String element) {
        testBase.mouseAction(this.page, action,element,  this.mapSaveText);
    }

    @Given("I scroll to element {word}")
    public void isScrollToElement(String element) {
        testBase.scrollAction(element, this.page);
    }

    @Given("I perform {word} action")
    public void i_perform_to_action(String action) {
        testBase.executeAction(this.page, action, null, this.mapSaveText, this.userDTO);

    }

    @Given("I perform {word} action with override values")
    public void actionOverride(String action, DataTable dataTable) {
        testBase.executeAction(this.page, action, dataTable, this.mapSaveText, this.userDTO);
    }

    @Given("I close browser with title is {string}")
    public void iCLoseBrowser(String title) {
        testBase.CloseBrowser( title);

    }
    @Given("I run postman collection with link {word}")
    public void i_run_postman_collection_with_link(String link) throws IOException, InterruptedException {
        testBase.ExecutePostmanCollectionWithLink(link);
    }

    @After
    public void tearDown() {
        testBase.closeBrowser();
    }

}
