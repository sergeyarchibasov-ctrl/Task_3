package ru.yandex.praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage extends BasePage {

    private final WebDriverWait wait;

    private final By nameField =
            By.xpath("//label[normalize-space()='Имя']/following-sibling::input");

    private final By emailField =
            By.xpath("//label[normalize-space()='Email']/following-sibling::input");

    private final By passwordField =
            By.xpath("//label[normalize-space()='Пароль']/following-sibling::input");

    private final By registerButton =
            By.xpath("//button[normalize-space()='Зарегистрироваться']");

    private final By loginLink =
            By.xpath("//a[normalize-space()='Войти']");

    private final By passwordError =
            By.xpath("//*[contains(normalize-space(),'Некорректный пароль')]");

    private final By registerForm =
            By.xpath("//h2[normalize-space()='Регистрация']");

    public RegisterPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Step("Открыть страницу регистрации")
    public void open() {
        driver.get(BASE_URL + "/register");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(registerForm)
        );
    }

    @Step("Ввести имя")
    public void enterName(String name) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(nameField)
        ).sendKeys(name);
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

    @Step("Нажать кнопку «Зарегистрироваться»")
    public void clickRegister() {
        wait.until(
                ExpectedConditions.elementToBeClickable(registerButton)
        ).click();
    }

    @Step("Зарегистрировать пользователя")
    public void register(
            String name,
            String email,
            String password
    ) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        clickRegister();
    }

    @Step("Перейти на страницу входа")
    public void clickLogin() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginLink)
        ).click();
    }

    @Step("Проверить сообщение о некорректном пароле")
    public boolean isPasswordErrorDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordError)
        ).isDisplayed();
    }
}