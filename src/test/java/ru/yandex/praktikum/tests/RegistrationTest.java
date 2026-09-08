package ru.yandex.praktikum.tests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.pages.RegisterPage;
import ru.yandex.praktikum.pages.LoginPage;
import ru.yandex.praktikum.utils.BrowserFactory;
import ru.yandex.praktikum.utils.TestData;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest {

    private WebDriver driver;
    private TestData testData;

    @BeforeEach
    public void setUp() {

        testData = new TestData();
        testData.prepareUserData();

        driver = BrowserFactory.createDriver();
    }

    @Test
    public void shouldRegisterSuccessfully() {

        openRegistrationPage();

        registerUser();

        checkLoginPage();
    }

    @Test
    public void shouldShowErrorForShortPassword() {

        openRegistrationPage();

        enterInvalidPassword();

        checkPasswordError();
    }

    @Step("Открыть страницу регистрации")
    private void openRegistrationPage() {

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
    }

    @Step("Зарегистрировать пользователя")
    private void registerUser() {

        RegisterPage registerPage = new RegisterPage(driver);

        registerPage.register(
                testData.getName(),
                testData.getEmail(),
                testData.getPassword()
        );
    }

    @Step("Ввести пароль длиной менее 6 символов")
    private void enterInvalidPassword() {

        RegisterPage registerPage = new RegisterPage(driver);

        registerPage.enterName(testData.getName());
        registerPage.enterEmail(testData.getEmail());
        registerPage.enterPassword("12345");
        registerPage.clickRegister();
    }

    @Step("Проверить сообщение о некорректном пароле")
    private void checkPasswordError() {

        RegisterPage registerPage = new RegisterPage(driver);

        assertTrue(
                registerPage.isPasswordErrorDisplayed(),
                "Сообщение о некорректном пароле не отображается"
        );
    }

    @Step("Проверить переход на страницу входа")
    private void checkLoginPage() {

        LoginPage loginPage = new LoginPage(driver);

        assertTrue(
                loginPage.isLoginPageDisplayed(),
                "После регистрации страница входа не открылась"
        );
    }

    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }

        if (testData != null) {
            testData.deleteUserAfterUiRegistration();
        }
    }
}