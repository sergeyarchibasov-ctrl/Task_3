package ru.yandex.praktikum.tests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.pages.MainPage;
import ru.yandex.praktikum.utils.BrowserFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstructorTest {

    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() {
        driver = BrowserFactory.createDriver();
        mainPage = new MainPage(driver);

        openConstructor();
    }

    @Test
    public void shouldSelectBunsTab() {
        selectSauces();
        selectBuns();

        assertTrue(
                mainPage.isBunsSelected(),
                "Вкладка «Булки» не выбрана"
        );
    }

    @Test
    public void shouldSelectSaucesTab() {
        selectSauces();

        assertTrue(
                mainPage.isSaucesSelected(),
                "Вкладка «Соусы» не выбрана"
        );
    }

    @Test
    public void shouldSelectFillingsTab() {
        selectFillings();

        assertTrue(
                mainPage.isFillingsSelected(),
                "Вкладка «Начинки» не выбрана"
        );
    }

    @Step("Открыть конструктор")
    private void openConstructor() {
        mainPage.open();
    }

    @Step("Выбрать вкладку «Булки»")
    private void selectBuns() {
        mainPage.clickBuns();
    }

    @Step("Выбрать вкладку «Соусы»")
    private void selectSauces() {
        mainPage.clickSauces();
    }

    @Step("Выбрать вкладку «Начинки»")
    private void selectFillings() {
        mainPage.clickFillings();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}