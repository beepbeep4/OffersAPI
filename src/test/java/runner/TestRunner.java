package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "java/featureFiles",
    glue = {"steps"}
)
public class TestRunner
{
    // Instantiated by JUnit
}