package step_definition;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;

import com.muso.pages.LoginPage;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LogIn {

    private LoginPage loginPage;
    private ByAngular.Factory factory;
    private NgWebDriver ngwd;
    private WebDriver driver = Hooks.driver;

    @Given("^I navigate to Muso LogIn Page$")
    public void I_navigate_to_Muso_LogIn_Page() throws Throwable {
        loginPage = new LoginPage(driver);
        loginPage.navigateTo();
    }

    @When("^I enter userName '(.+)'$")
    public void I_enter_userName(String name) {
        loginPage.enterUsername(name);
    }

    @When("^I enter password '(.+)'$")
    public void I_enter_password(String password) {
        loginPage.enterPassword(password);
    }

    @When("^I click on signIn$")
    public void I_click_on_SignIn() {
        loginPage.signIn();
    }

    @Then("^I '(should|shouldn't)' be able to login$")
    public void can_I_logIn(String can_login) {

        switch (can_login) {
            case "should":
                assertEquals(true, loginPage.isAlertDisplayed());
                break;
            case "shouldn't":
                assertEquals(false, loginPage.isAlertDisplayed());
                break;
            default:
                throw new InvalidArgumentException("Unknown word:" + can_login);
        }
    }

}
