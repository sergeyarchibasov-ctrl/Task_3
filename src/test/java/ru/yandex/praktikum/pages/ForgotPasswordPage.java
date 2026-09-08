package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgotPasswordPage extends BasePage {

    private final WebDriverWait wait;

    private final By loginLink = By.xpath(
            "//a[normalize-space()='Войти']"
    );

    private final By forgotPasswordForm = By.xpath(
            "//*[contains(normalize-space(),'Восстановление пароля')]"
    );

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void open() {
        driver.get(BASE_URL + "/forgot-password");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(forgotPasswordForm)
        );
    }

    public void clickLogin() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginLink)
        ).click();
    }
}