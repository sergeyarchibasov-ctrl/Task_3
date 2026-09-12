package ru.yandex.praktikum.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    void setUp() {
        testData = new TestData();
        testData.prepareUserData();
        testData.createUser();

        driver = BrowserFactory.createDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }

        if (testData != null) {
            testData.deleteUser();
        }
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной странице")
    void shouldLoginFromMainPageLoginButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                testData.getEmail(),
                testData.getPassword()
        );

        assertTrue(
                mainPage.isMainPageDisplayed(),
                "После входа главная страница не открылась"
        );
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    void shouldLoginFromAccountButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickAccount();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                testData.getEmail(),
                testData.getPassword()
        );

        assertTrue(
                mainPage.isMainPageDisplayed(),
                "После входа через «Личный кабинет» главная страница не открылась"
        );
    }

    @Test
    @DisplayName("Вход через форму регистрации")
    void shouldLoginFromRegistrationPage() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.clickLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                testData.getEmail(),
                testData.getPassword()
        );

        MainPage mainPage = new MainPage(driver);

        assertTrue(
                mainPage.isMainPageDisplayed(),
                "После входа через форму регистрации главная страница не открылась"
        );
    }

    @Test
    @DisplayName("Вход через форму восстановления пароля")
    void shouldLoginFromForgotPasswordPage() {
        ForgotPasswordPage forgotPasswordPage =
                new ForgotPasswordPage(driver);

        forgotPasswordPage.open();
        forgotPasswordPage.clickLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                testData.getEmail(),
                testData.getPassword()
        );

        MainPage mainPage = new MainPage(driver);

        assertTrue(
                mainPage.isMainPageDisplayed(),
                "После входа через форму восстановления пароля главная страница не открылась"
        );
    }
}