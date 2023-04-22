import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features ="src/test/resources/featuresTemp", glue = {"StepsDefinition"},
        plugin = {"json:target/cucumber.json", "pretty"},
        monochrome = true

)


public class TestRunner  {

}
