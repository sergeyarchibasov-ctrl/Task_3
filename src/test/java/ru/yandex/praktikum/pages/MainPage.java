package ru.yandex.praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage extends BasePage {

    private final WebDriverWait wait;

    private final By loginButton = By.xpath(
            "//button[normalize-space()='Войти в аккаунт']"
    );

    private final By accountLink = By.xpath(
            "//a[contains(@href,'/account')]"
    );

    private final By constructorLink = By.xpath(
            "//a[contains(normalize-space(.),'Конструктор')]"
    );

    private final By logo = By.xpath(
            "//a[@href='/']"
    );

    private final By bunsTab = By.xpath(
            "//span[normalize-space()='Булки']/ancestor::div[contains(@class,'tab_tab')]"
    );

    private final By saucesTab = By.xpath(
            "//span[normalize-space()='Соусы']/ancestor::div[contains(@class,'tab_tab')]"
    );

    private final By fillingsTab = By.xpath(
            "//span[normalize-space()='Начинки']/ancestor::div[contains(@class,'tab_tab')]"
    );

    public MainPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Step("Открыть главную страницу")
    public void open() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(bunsTab));
    }

    @Step("Нажать кнопку «Войти в аккаунт»")
    public void clickLogin() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    @Step("Нажать «Личный кабинет»")
    public void clickAccount() {
        wait.until(
                ExpectedConditions.elementToBeClickable(accountLink)
        ).click();
    }

    @Step("Нажать «Конструктор»")
    public void clickConstructor() {
        wait.until(
                ExpectedConditions.elementToBeClickable(constructorLink)
        ).click();
    }

    @Step("Нажать на логотип Stellar Burgers")
    public void clickLogo() {
        wait.until(
                ExpectedConditions.elementToBeClickable(logo)
        ).click();
    }

    @Step("Перейти в раздел «Булки»")
    public void clickBuns() {
        clickTab(bunsTab);
    }

    @Step("Перейти в раздел «Соусы»")
    public void clickSauces() {
        clickTab(saucesTab);
    }

    @Step("Перейти в раздел «Начинки»")
    public void clickFillings() {
        clickTab(fillingsTab);
    }

    private void clickTab(By locator) {
        WebElement tab =
                wait.until(ExpectedConditions.elementToBeClickable(locator));

        tab.click();

        wait.until(driver ->
                driver.findElement(locator)
                        .getAttribute("class")
                        .contains("tab_tab_type_current")
        );
    }

    @Step("Проверить, что выбран раздел «Булки»")
    public boolean isBunsSelected() {
        return isTabSelected(bunsTab);
    }

    @Step("Проверить, что выбран раздел «Соусы»")
    public boolean isSaucesSelected() {
        return isTabSelected(saucesTab);
    }

    @Step("Проверить, что выбран раздел «Начинки»")
    public boolean isFillingsSelected() {
        return isTabSelected(fillingsTab);
    }

    private boolean isTabSelected(By locator) {
        return wait.until(driver ->
                driver.findElement(locator)
                        .getAttribute("class")
                        .contains("tab_tab_type_current")
        );
    }

    @Step("Проверить отображение главной страницы")
    public boolean isMainPageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(bunsTab)
        ).isDisplayed();
    }
}