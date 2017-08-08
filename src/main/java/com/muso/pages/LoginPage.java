package com.muso.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    @FindBy(id = "UserName")
    private WebElement userNameTextField;

    @FindBy(name = "UserPassword")
    private WebElement passwordTextField;

    @FindBy(css = "input.button")
    private WebElement signInButton;

    @FindBy(css = "div.alert")
    private WebElement alert;

    @FindBy(css = "a.grey")
    private WebElement resetPassword;

    public LoginPage(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);
    }

    public LoginPage enterUsername(String username) {
        userNameTextField.sendKeys(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordTextField.sendKeys(password);
        return this;
    }

    public void signIn() {
        signInButton.click();
    }

    public void resetPassword() {
        resetPassword.click();
    }

    public String getAlertMessage() {
        return alert.getText();
    }

    public boolean isAlertDisplayed() {
        if (alert != null) {
            return true;
        }
        return false;
    }

}
