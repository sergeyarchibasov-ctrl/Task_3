package ru.yandex.praktikum.pages;

import org.openqa.selenium.WebDriver;

public class BasePage {

    protected final WebDriver driver;

    protected static final String BASE_URL =
            "https://qa-stellarburgers.education-services.ru";

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }
}