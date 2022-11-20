import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features ="src/test/resources/features", glue = {"StepsDefinition"},
        plugin = {"json:target/cucumber.json", "pretty"},
        monochrome = true

        //run with comman line mvaven
//        mvn clean verify  -Dcucumber.filter.tags=@mc-test2
//        mvn clean verify  -Dcucumber.filter.tags="@mc-test2 or @mc-login"
//        mvn clean verify  -Dcucumber.filter.tags="@mc-test2 and @mc-login"
//mvn clean verify
)
public class TestRunner {

}
