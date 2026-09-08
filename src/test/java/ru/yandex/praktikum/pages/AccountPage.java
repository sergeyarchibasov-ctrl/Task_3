package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountPage extends BasePage {

    private final WebDriverWait wait;

    private final By accountPage = By.xpath(
            "//*[contains(normalize-space(),'Профиль')]"
    );

    private final By constructorLink = By.xpath(
            "//a[contains(normalize-space(),'Конструктор')]"
    );

    private final By logo = By.xpath(
            "//a[@href='/']"
    );

    private final By logoutButton = By.xpath(
            "//button[normalize-space()='Выход']"
    );

    public AccountPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public boolean isProfileDisplayed() {
        return wait.until(
                ExpectedConditions.urlContains("/account")
        );
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

    public void clickLogout() {
        wait.until(
                ExpectedConditions.elementToBeClickable(logoutButton)
        ).click();
    }

    public boolean isAccountPageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(accountPage)
        ).isDisplayed();
    }
}