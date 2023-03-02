import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.junit.runner.RunWith;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

@RunWith(Cucumber.class)
@CucumberOptions(features ="src/test/resources/features", glue = {"StepsDefinition"},
        plugin = {"json:target/cucumber.json", "pretty"},
        monochrome = true
)


public class TestRunner {


}
