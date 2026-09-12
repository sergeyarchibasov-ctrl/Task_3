package ru.yandex.praktikum.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.pages.MainPage;
import ru.yandex.praktikum.utils.BrowserFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstructorTest {

    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    void setUp() {
        driver = BrowserFactory.createDriver();

        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Переход в раздел «Булки»")
    void shouldSwitchToBunsTab() {
        mainPage.clickSauces();
        mainPage.clickBuns();

        assertTrue(
                mainPage.isBunsSelected(),
                "Раздел «Булки» не выбран"
        );
    }

    @Test
    @DisplayName("Переход в раздел «Соусы»")
    void shouldSwitchToSaucesTab() {
        mainPage.clickSauces();

        assertTrue(
                mainPage.isSaucesSelected(),
                "Раздел «Соусы» не выбран"
        );
    }

    @Test
    @DisplayName("Переход в раздел «Начинки»")
    void shouldSwitchToFillingsTab() {
        mainPage.clickFillings();

        assertTrue(
                mainPage.isFillingsSelected(),
                "Раздел «Начинки» не выбран"
        );
    }
}