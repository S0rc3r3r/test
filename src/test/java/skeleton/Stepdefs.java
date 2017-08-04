package skeleton;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class Stepdefs {
    protected WebDriver driver;
    private ByAngular.Factory factory;
    private NgWebDriver ngwd;

    @Given("I navigate to (.+)$")
    public void I_navigate_to(String url) throws Throwable {
        System.out.println("URL: " + url);
        driver.get(url);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

        ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("[ng-controller]");
        ngwd.waitForAngularRequestsToFinish();

        factory = ngwd.makeByAngularFactory();

    }

    @When("I enter name (.+)$")
    public void I_enter_name(String name) {
        System.out.println(name);

        WebElement nameElement = driver.findElement(factory.model("yourName"));
        nameElement.sendKeys("Tanase");

    }

    @Then("^the result should be (.+)$")
    public void the_result_should_be(String expectedResult) {
        WebElement resultElement = driver.findElement(factory.binding("yourName"));

        System.out.println("resultElement: " + resultElement.getText());
        Assert.assertEquals(expectedResult, resultElement.getText());
    }

    @Before
    public void startUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe"); // geckodriver
        driver = new ChromeDriver();
    }

    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {
            try {
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");

                FileUtils.copyFile(scrFile, new File("c:\\tmp\\" + scenario.getName() + ".png"));
            } catch (WebDriverException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        driver.quit();
    }

}
