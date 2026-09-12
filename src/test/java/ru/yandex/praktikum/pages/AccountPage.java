package ru.yandex.praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountPage extends BasePage {

    private final WebDriverWait wait;

    private final By accountPage =
            By.xpath("//*[contains(normalize-space(),'Профиль')]");

    private final By constructorLink =
            By.xpath("//a[contains(normalize-space(),'Конструктор')]");

    private final By logo =
            By.xpath("//a[@href='/']");

    private final By logoutButton =
            By.xpath("//button[normalize-space()='Выход']");

    public AccountPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Step("Проверить переход в личный кабинет")
    public boolean isProfileDisplayed() {
        return wait.until(
                ExpectedConditions.urlContains("/account")
        );
    }

    @Step("Перейти из личного кабинета в «Конструктор»")
    public void clickConstructor() {
        wait.until(
                ExpectedConditions.elementToBeClickable(constructorLink)
        ).click();
    }

    @Step("Перейти на главную страницу по логотипу")
    public void clickLogo() {
        wait.until(
                ExpectedConditions.elementToBeClickable(logo)
        ).click();
    }

    @Step("Выйти из аккаунта")
    public void clickLogout() {
        wait.until(
                ExpectedConditions.elementToBeClickable(logoutButton)
        ).click();
    }

    @Step("Проверить отображение личного кабинета")
    public boolean isAccountPageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(accountPage)
        ).isDisplayed();
    }
}