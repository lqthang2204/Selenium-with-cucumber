import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features ="@target/failedrerun.txt", glue = {"StepsDefinition"},
        plugin = {},
        monochrome = true

)
public class RerunFailed {
}
