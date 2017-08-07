package step_definition;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/html",
        "json:target/cucumber.json" }, monochrome = true, features = "classpath:feature", tags = {})
public class RunTest2 {

    //tags = "@angular"
}
