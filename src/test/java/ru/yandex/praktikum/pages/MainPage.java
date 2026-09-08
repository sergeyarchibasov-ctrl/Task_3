package ru.yandex.praktikum.pages;

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

    public void open() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(bunsTab));
    }

    public void clickLogin() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    public void clickAccount() {
        wait.until(
                ExpectedConditions.elementToBeClickable(accountLink)
        ).click();
    }

    public void clickConstructor() {
        wait.until(
                ExpectedConditions.elementToBeClickable(constructorLink)
        ).click();
    }

    public void clickLogo() {
        wait.until(
                ExpectedConditions.elementToBeClickable(logo)
        ).click();
    }

    public void clickBuns() {
        clickTab(bunsTab);
    }

    public void clickSauces() {
        clickTab(saucesTab);
    }

    public void clickFillings() {
        clickTab(fillingsTab);
    }

    private void clickTab(By locator) {
        WebElement tab = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

        tab.click();

        wait.until(driver ->
                driver.findElement(locator)
                        .getAttribute("class")
                        .contains("tab_tab_type_current")
        );
    }

    public boolean isBunsSelected() {
        return isTabSelected(bunsTab);
    }

    public boolean isSaucesSelected() {
        return isTabSelected(saucesTab);
    }

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

    public boolean isMainPageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(bunsTab)
        ).isDisplayed();
    }
}