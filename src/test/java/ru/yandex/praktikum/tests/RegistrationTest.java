package ru.yandex.praktikum.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.pages.LoginPage;
import ru.yandex.praktikum.pages.RegisterPage;
import ru.yandex.praktikum.utils.BrowserFactory;
import ru.yandex.praktikum.utils.TestData;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest {

    private WebDriver driver;
    private TestData testData;

    @BeforeEach
    void setUp() {
        testData = new TestData();
        testData.prepareUserData();

        driver = BrowserFactory.createDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }

        if (testData != null) {
            testData.deleteUserAfterUiRegistration();
        }
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    void shouldRegisterUserSuccessfully() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();

        registerPage.register(
                testData.getName(),
                testData.getEmail(),
                testData.getPassword()
        );

        LoginPage loginPage = new LoginPage(driver);

        assertTrue(
                loginPage.isLoginPageDisplayed(),
                "После успешной регистрации страница входа не открылась"
        );
    }

    @Test
    @DisplayName("Ошибка регистрации при вводе пароля короче шести символов")
    void shouldShowErrorForShortPassword() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();

        registerPage.register(
                testData.getName(),
                testData.getEmail(),
                "12345"
        );

        assertTrue(
                registerPage.isPasswordErrorDisplayed(),
                "Сообщение «Некорректный пароль» не появилось"
        );
    }
}