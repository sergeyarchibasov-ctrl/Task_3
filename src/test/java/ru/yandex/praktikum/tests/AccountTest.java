package ru.yandex.praktikum.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.pages.AccountPage;
import ru.yandex.praktikum.pages.LoginPage;
import ru.yandex.praktikum.pages.MainPage;
import ru.yandex.praktikum.utils.BrowserFactory;
import ru.yandex.praktikum.utils.TestData;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest {

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
    @DisplayName("Переход в личный кабинет авторизованного пользователя")
    void shouldOpenAccountPage() {
        loginUser();

        MainPage mainPage = new MainPage(driver);
        mainPage.clickAccount();

        AccountPage accountPage = new AccountPage(driver);

        assertTrue(
                accountPage.isAccountPageDisplayed(),
                "Личный кабинет не открылся"
        );
    }

    @Test
    @DisplayName("Переход из личного кабинета в Конструктор")
    void shouldOpenConstructorFromAccountPage() {
        loginUser();

        MainPage mainPage = new MainPage(driver);
        mainPage.clickAccount();

        AccountPage accountPage = new AccountPage(driver);
        accountPage.clickConstructor();

        assertTrue(
                mainPage.isMainPageDisplayed(),
                "Главная страница с Конструктором не открылась"
        );
    }

    @Test
    @DisplayName("Переход из личного кабинета на главную страницу по логотипу Stellar Burgers")
    void shouldOpenMainPageByLogo() {
        loginUser();

        MainPage mainPage = new MainPage(driver);
        mainPage.clickAccount();

        AccountPage accountPage = new AccountPage(driver);
        accountPage.clickLogo();

        assertTrue(
                mainPage.isMainPageDisplayed(),
                "Главная страница не открылась после нажатия на логотип"
        );
    }

    @Test
    @DisplayName("Выход пользователя из личного кабинета")
    void shouldLogoutFromAccount() {
        loginUser();

        MainPage mainPage = new MainPage(driver);
        mainPage.clickAccount();

        AccountPage accountPage = new AccountPage(driver);
        accountPage.clickLogout();

        LoginPage loginPage = new LoginPage(driver);

        assertTrue(
                loginPage.isLoginPageDisplayed(),
                "После выхода страница входа не открылась"
        );
    }

    private void loginUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(
                testData.getEmail(),
                testData.getPassword()
        );
    }
}