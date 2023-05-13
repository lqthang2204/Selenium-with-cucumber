package StepsDefinition;

import ManageDriver.Hook;
import Utilitize.ExecuteYaml;
import Utilitize.TestBase;
import bean.Page;
import bean.UserDTO;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class Steps {
    public TestBase testBase;
    ExecuteYaml execute;
    Page page;
    Map<String, String> mapSaveText;
    public static String titlePage;
    public static Map<String, String> mapFileYaml;
    Scenario scenario;
    List<UserDTO> listUserDTO;
    UserDTO userDTO;
    public WebDriver driver;
    public Hook hook;

    public Steps() {
    }

    @Before
    public void setUp(Scenario scenario) {
        hook = new Hook();
        testBase = new TestBase();
        execute = new ExecuteYaml();
        page = new Page();
        userDTO = new UserDTO();
        mapSaveText = new HashMap<>();
        mapFileYaml = new HashMap<>();
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        execute.findFile(new File(System.getProperty("user.dir") + "/src/test/resources/Pages"), this.mapFileYaml);
        this.scenario = scenario;
//        testBase.readYamlFile("CommonPage", this.mapFileYaml);

    }

    @Before
    public void setUp2(Scenario scenario) {

        System.out.println("Scenarion == " + scenario);
        System.out.println("" + scenario.getSourceTagNames());
        Collection<String> tags = scenario.getSourceTagNames();
        for (int i = 0; i < tags.size(); i++) {
            System.out.println(tags.toArray()[i]);
        }
    }


    @Given("I navigate to {word}")
    public void openBrowser(String url) {
        testBase.getDriver();
        driver = testBase.OpenBrowser(url);

    }

    @Given("I change the page spec to {word}")
    public void updateYaml(String yaml) {
        this.page = testBase.readYamlFile(yaml, this.mapFileYaml);
        this.titlePage = yaml;
    }


    @Given("I {word} element {}")
    public void mouseAction(String action, String element) {
        testBase.mouseAction(this.page, action, element, this.mapSaveText);
    }

    @And("I switch to browser window with index {string}")
    public void i_switch_to_browser_window_with_index(String index) {
        testBase.switchTab(index);
    }


    @When("I type {string} into element {word}")
    public void actionType(String content, String element) {
        testBase.ActionType(this.page, element, content, this.mapSaveText, this.listUserDTO);

    }

    @And("I run {word} with {word} data file")
    public void i_run_postman_collection_with_data_json(String collectionFile, String dataFile) {
        testBase.runCollection(collectionFile, dataFile, (Map) null, this.scenario, this.listUserDTO, this.mapSaveText);

    }

    @And("I run {word} with {word} data file with override values")
    public void i_run_with_data_file_with_override_values(String collectionFile, String dataFile, DataTable dataTable) {
        testBase.runCollection(collectionFile, dataFile, dataTable.asMap(String.class, String.class), this.scenario, listUserDTO, this.mapSaveText);
    }

    @And("I wait for element {} to be {}")
    public void waitTo(String element, String status) {
        testBase.showUI(this.page, element, status, this.mapSaveText);
    }

    @Given("I become a random user")
    public void i_become_a_random_user() throws ParseException {
       this.listUserDTO = testBase.CreateUser(listUserDTO);
    }

    @And("I verify the text for element {word} is {string}")
    public void verifyText(String element, String text) {
        testBase.verifyText(this.page, element, text, true, this.mapSaveText, listUserDTO);
    }

    @And("I verify the exact text for element {word} is {string}")
    public void verifyExactText(String element, String text) {
        testBase.verifyText(this.page, element, text, false, this.mapSaveText, listUserDTO);
    }

    @Given("I save text for element {word} with key {string}")
    public void saveText(String element, String text) {
        testBase.saveTextElement(this.page, element, text, this.mapSaveText);
    }

    //reference key enum: https://github.com/SeleniumHQ/selenium/blob/selenium-4.2.0/java/src/org/openqa/selenium/Keys.java#L28
    @Given("I click keyboard {word} button on element {word}")
    public void i_click_keyboard_button_on_element(String Key, String element) {
        testBase.keyBoard(this.page, element, Key);
    }

    @Given("I {word} text from element {word}")
    public void clearText(String action, String element) {
        testBase.mouseAction(this.page, action, element, this.mapSaveText);

    }

    @Given("I scroll to element {word}")
    public void isScrollToElement(String element) {
        testBase.scrollAction(element, this.page);
    }

    @Given("I perform {word} action")
    public void i_perform_to_action(String action) {
        testBase.executeAction(this.page, action, null, this.mapSaveText, this.listUserDTO);

    }

    @Given("I perform {word} action with override values")
    public void actionOverride(String action, DataTable dataTable) {
        testBase.executeAction(this.page, action, dataTable, this.mapSaveText, this.listUserDTO);
    }

    @Given("I close browser with title is {string}")
    public void iCLoseBrowser(String title) {
        testBase.CloseBrowser(title);

    }

    @Given("I run postman collection with link {word}")
    public void i_run_postman_collection_with_link(String link) throws IOException, InterruptedException {
        testBase.ExecutePostmanCollectionWithLink(link);
    }

    @Given("I generate {} file with header {}")
    public void i_generate_test_file_csv_file_with_header(String filename, String header) throws IOException {
        testBase.createFile(filename, header);
    }

    @Given("I write {word} into file {}")
    public void i_write_csv(String data, String fileName) throws IOException, InvalidFormatException {
        testBase.writeFile(data.replace("\"", ""), fileName, this.mapSaveText);
    }

    @Given("I type {string} from {word} into element {word}")
    public void i_type_from_file_test_file_csv_into_element_field_search(String data, String file, String element) throws IOException {

        testBase.getDataFromFile(data, file, element, this.page);
    }

    @Given("I drag and drop element {word} to element {word}")
    public void i_drag_and_drop_element_amount_to_element_amount_target(String element, String target) {
        testBase.actionDragAndDrop(element, target, this.page);
    }

    @Given("I drag and drop element {word} to element {word} by javascript")
    public void i_drag_and_drop_element_amount_to_element_amount_target_by_javascript(String element, String target) {
        testBase.actionDragAndDropByJS(element, target, this.page);
    }

    @Given("I wait {word} seconds")
    public void i_wait_seconds(String seconds) {
        testBase.iwaitSeconds(seconds);

    }

    @Given("I verify attribute element {} has css property {} with value {string}")
    public void i_verify_attribute_element_campaign_source_value_two_has_css_property_with_value(String element, String property, String value) {
        testBase.checkCssAttribute(this.page, element, property, value, this.mapSaveText);
    }
    @Given("I switch to iFrame for element {}")
    public void i_switch_to_i_frame_with_index(String element) {
       testBase.SwitchFrame(this.page, element);
    }

    @Given("I am {word} created by the file")
    public void i_am_user1_created_by_the_file(String nameFile) {
        this.listUserDTO = testBase.getUserFormFile(this.listUserDTO, nameFile);
    }
    @Given("I switch to default iFrame")
    public void i_switch_to_default_i_frame() {
       testBase.SwitchDefaulFrame();
    }

    @Given("I navigate to the page to verify email {string}")
    public void i_navigate_to_the_page_to_verify_email(String email) throws ParseException, IOException, InterruptedException {
        testBase.NavigateAnotherPage(email, this.mapSaveText, this.listUserDTO);
    }
    @After
    public void tearDown(Scenario scenario) {
        try {
            this.scenario = scenario;
            if (scenario.isFailed()) {
                final byte[] screenshot = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            testBase.closeBrowser();
        }
    }

}
