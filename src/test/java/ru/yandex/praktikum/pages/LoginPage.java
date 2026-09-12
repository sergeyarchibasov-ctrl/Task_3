package ru.yandex.praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    private final WebDriverWait wait;

    private final By emailField =
            By.xpath("//label[normalize-space()='Email']/following-sibling::input");

    private final By passwordField =
            By.xpath("//label[normalize-space()='Пароль']/following-sibling::input");

    private final By loginButton =
            By.xpath("//button[normalize-space()='Войти']");

    private final By registerLink =
            By.xpath("//a[normalize-space()='Зарегистрироваться']");

    private final By forgotPasswordLink =
            By.xpath("//a[normalize-space()='Восстановить пароль']");

    private final By loginForm =
            By.xpath("//h2[normalize-space()='Вход']");

    public LoginPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Step("Открыть страницу входа")
    public void open() {
        driver.get(BASE_URL + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginForm));
    }

    @Step("Ввести email")
    public void enterEmail(String email) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailField)
        ).sendKeys(email);
    }

    @Step("Ввести пароль")
    public void enterPassword(String password) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordField)
        ).sendKeys(password);
    }

    @Step("Нажать кнопку «Войти»")
    public void clickLogin() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    @Step("Войти под пользователем")
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    @Step("Перейти к регистрации")
    public void clickRegister() {
        wait.until(
                ExpectedConditions.elementToBeClickable(registerLink)
        ).click();
    }

    @Step("Перейти к восстановлению пароля")
    public void clickForgotPassword() {
        wait.until(
                ExpectedConditions.elementToBeClickable(forgotPasswordLink)
        ).click();
    }

    @Step("Проверить отображение страницы входа")
    public boolean isLoginPageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginForm)
        ).isDisplayed();
    }
}