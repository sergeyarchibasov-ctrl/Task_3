package ru.yandex.praktikum.tests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.pages.ForgotPasswordPage;
import ru.yandex.praktikum.pages.LoginPage;
import ru.yandex.praktikum.pages.MainPage;
import ru.yandex.praktikum.pages.RegisterPage;
import ru.yandex.praktikum.utils.BrowserFactory;
import ru.yandex.praktikum.utils.TestData;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    private WebDriver driver;
    private TestData testData;

    @BeforeEach
    public void setUp() {
        testData = new TestData();
        testData.prepareUserData();
        testData.createUser();

        driver = BrowserFactory.createDriver();
    }

    @Test
    public void shouldLoginFromMainPage() {
        openMainPage();
        clickLoginFromMainPage();
        loginAsTestUser();
        checkSuccessfulLogin();
    }

    @Test
    public void shouldLoginFromAccountButton() {
        openMainPage();
        clickAccountFromMainPage();
        loginAsTestUser();
        checkSuccessfulLogin();
    }

    @Test
    public void shouldLoginFromRegistrationPage() {
        openRegistrationPage();
        clickLoginFromRegistration();
        loginAsTestUser();
        checkSuccessfulLogin();
    }

    @Test
    public void shouldLoginFromForgotPasswordPage() {
        openForgotPasswordPage();
        clickLoginFromForgotPassword();
        loginAsTestUser();
        checkSuccessfulLogin();
    }

    @Step("Открыть главную страницу")
    private void openMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
    }

    @Step("Нажать «Войти в аккаунт»")
    private void clickLoginFromMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLogin();
    }

    @Step("Нажать «Личный Кабинет»")
    private void clickAccountFromMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickAccount();
    }

    @Step("Открыть страницу регистрации")
    private void openRegistrationPage() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
    }

    @Step("Перейти со страницы регистрации на страницу входа")
    private void clickLoginFromRegistration() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickLogin();
    }

    @Step("Открыть страницу восстановления пароля")
    private void openForgotPasswordPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.clickForgotPassword();
    }

    @Step("Перейти со страницы восстановления пароля на страницу входа")
    private void clickLoginFromForgotPassword() {
        ForgotPasswordPage forgotPasswordPage =
                new ForgotPasswordPage(driver);

        forgotPasswordPage.clickLogin();
    }

    @Step("Авторизоваться под тестовым пользователем")
    private void loginAsTestUser() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                testData.getEmail(),
                testData.getPassword()
        );
    }

    @Step("Проверить успешную авторизацию")
    private void checkSuccessfulLogin() {
        MainPage mainPage = new MainPage(driver);

        assertTrue(
                mainPage.isMainPageDisplayed(),
                "После авторизации главная страница не открылась"
        );
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

        if (testData != null) {
            testData.deleteUser();
        }
    }
}