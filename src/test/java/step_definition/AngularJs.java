package step_definition;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AngularJs {

    protected WebDriver driver = Hooks.driver;
    private ByAngular.Factory factory;
    private NgWebDriver ngwd;

    @Given("^I navigate to AngularJs page$")
    public void I_navigate_to_AngularJs_page() throws Throwable {
        driver.get("https://angularjs.org/");
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

        ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("[ng-controller]");
        ngwd.waitForAngularRequestsToFinish();

        factory = ngwd.makeByAngularFactory();

    }

    @When("^I enter name '(.+)'$")
    public void I_enter_name(String name) {
        WebElement nameElement = driver.findElement(factory.model("yourName"));
        nameElement.sendKeys(name);
    }

    @Then("^The massage should be '(.+)'$")
    public void the_massage_should_be(String expectedMessage) throws Throwable {

        WebElement resultElement = driver.findElement(factory.binding("yourName"));

        System.out.println("resultElement: " + resultElement.getText());
        assertEquals(expectedMessage, resultElement.getText());
    }

}
