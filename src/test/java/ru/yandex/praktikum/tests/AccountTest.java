package ru.yandex.praktikum.tests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.pages.AccountPage;
import ru.yandex.praktikum.pages.MainPage;
import ru.yandex.praktikum.pages.LoginPage;
import ru.yandex.praktikum.utils.BrowserFactory;
import ru.yandex.praktikum.utils.TestData;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest {

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
    public void shouldOpenPersonalAccount() {
        openMainPage();
        login();
        openAccount();

        assertTrue(
                isAccountPageDisplayed(),
                "Личный кабинет не открылся"
        );
    }

    @Test
    public void shouldGoToConstructorFromAccount() {
        openMainPage();
        login();
        openAccount();
        clickConstructor();

        assertTrue(
                isMainPageDisplayed(),
                "После нажатия «Конструктор» конструктор не открылся"
        );
    }

    @Test
    public void shouldGoToConstructorByLogoFromAccount() {
        openMainPage();
        login();
        openAccount();
        clickLogo();

        assertTrue(
                isMainPageDisplayed(),
                "После нажатия на логотип главная страница не открылась"
        );
    }

    @Test
    public void shouldLogout() {
        openMainPage();
        login();
        openAccount();
        logout();

        assertTrue(
                isLoginPageDisplayed(),
                "После выхода страница входа не открылась"
        );
    }

    @Step("Открыть главную страницу")
    private void openMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
    }

    @Step("Авторизоваться под тестовым пользователем")
    private void login() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                testData.getEmail(),
                testData.getPassword()
        );
    }

    @Step("Открыть личный кабинет")
    private void openAccount() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickAccount();
    }

    @Step("Проверить личный кабинет")
    private boolean isAccountPageDisplayed() {
        AccountPage accountPage = new AccountPage(driver);
        return accountPage.isProfileDisplayed();
    }

    @Step("Перейти в конструктор")
    private void clickConstructor() {
        AccountPage accountPage = new AccountPage(driver);
        accountPage.clickConstructor();
    }

    @Step("Перейти на главную страницу через логотип")
    private void clickLogo() {
        AccountPage accountPage = new AccountPage(driver);
        accountPage.clickLogo();
    }

    @Step("Проверить главную страницу")
    private boolean isMainPageDisplayed() {
        MainPage mainPage = new MainPage(driver);
        return mainPage.isMainPageDisplayed();
    }

    @Step("Выйти из аккаунта")
    private void logout() {
        AccountPage accountPage = new AccountPage(driver);
        accountPage.clickLogout();
    }

    @Step("Проверить страницу входа")
    private boolean isLoginPageDisplayed() {
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.isLoginPageDisplayed();
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