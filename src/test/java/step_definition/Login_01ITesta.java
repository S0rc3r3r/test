package step_definition;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(strict = true, features = { "/Users/bogdantanasoiu/git/musoAutomation/src/test/resources/feature/MusoDashboard.feature" }, plugin = {
        "json:/Users/bogdantanasoiu/git/musoAutomation/target/1.json" }, monochrome = true, tags = { "@Reports" }, glue = { "step_definition" })
public class Login_01ITesta {
}