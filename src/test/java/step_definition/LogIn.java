package step_definition;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.Alert;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.pages.LoginPage;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utils.JavaGmailSearchInbox;

public class LogIn {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogIn.class);

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

    @When("^I click on 'forgotten password link'$")
    public void I_click_on_forgotten_password_link() {

        try {
            loginPage.resetPassword();
            ((JavascriptExecutor) driver)
                    .executeScript("window.confirm = function(msg) { return true; }");
        } catch (UnhandledAlertException f) {
            try {
                Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                LOGGER.info("Alert data: " + alertText);
                LOGGER.info("Click on OK");
                alert.accept();
            } catch (NoAlertPresentException e) {
                e.printStackTrace();
            }
        }

    }

    @When("^I can reset password$")
    public void I_can_reset_password() {
        LOGGER.info("Should be able to click on link and reset pass");
    }

    @Then("^I '(should|should not)' be able to login$")
    public void can_I_logIn(String can_login) {

        switch (can_login) {
            case "should":
                assertEquals(false, loginPage.isAlertDisplayed());
                break;
            case "should not":
                assertEquals(true, loginPage.isAlertDisplayed());
                break;
            default:
                throw new InvalidArgumentException("Unknown word:" + can_login);
        }
    }

    private boolean checkEmail() {
        JavaGmailSearchInbox mail = new JavaGmailSearchInbox();
        boolean emailFound = false;

        try {
            emailFound = mail.searchMusoPasswordRecoveryEmail("homeautomationstatusinfo@gmail.com",
                    "aHyN044RUWeHlodVDE7lOQ==",
                    30);
        } catch (Exception e) {
            LOGGER.error("Exception occured", e);
            e.printStackTrace();
        }

        if (emailFound) {
            LOGGER.info("Found recovery link: " + mail.getManualLink());
        } else {
            LOGGER.info("Recovery link not found.");
        }

        return emailFound;
    }

    @Then("^an email(?: (.*))? sent$")
    public void an_email_is_sent(String isEmailSent) {

        switch (isEmailSent) {
            case "is":
                assertEquals(true, checkEmail());
                break;
            case "is not":
                assertEquals(false, checkEmail());
                break;
            default:
                throw new InvalidArgumentException("Unknown word:" + isEmailSent);

        }

    }

}
