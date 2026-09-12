package ru.yandex.praktikum.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BrowserFactory {

    private static final String YANDEX_BINARY_PROPERTY = "yandex.binary";
    private static final String YANDEX_DRIVER_PROPERTY = "yandex.driver";

    public static WebDriver createDriver() {
        String browser = System.getProperty("browser", "chrome");

        if ("yandex".equalsIgnoreCase(browser)) {
            return createYandexDriver();
        }

        return new ChromeDriver();
    }

    private static WebDriver createYandexDriver() {
        String yandexBinary =
                getRequiredSystemProperty(YANDEX_BINARY_PROPERTY);

        String yandexDriver =
                getRequiredSystemProperty(YANDEX_DRIVER_PROPERTY);

        ChromeOptions options = new ChromeOptions();
        options.setBinary(yandexBinary);

        try {
            Path profileDirectory =
                    Files.createTempDirectory("yandex-selenium-profile-");

            options.addArguments(
                    "--user-data-dir=" + profileDirectory.toAbsolutePath()
            );
        } catch (IOException e) {
            throw new RuntimeException(
                    "Не удалось создать временный профиль Яндекс.Браузера",
                    e
            );
        }

        options.addArguments("--no-first-run");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--disable-background-mode");

        ChromeDriverService service =
                new ChromeDriverService.Builder()
                        .usingDriverExecutable(new File(yandexDriver))
                        .build();

        WebDriver driver = new ChromeDriver(service, options);

        prepareCleanYandexTab(driver);

        return driver;
    }

    private static String getRequiredSystemProperty(String propertyName) {
        String value = System.getProperty(propertyName);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Не задана системная переменная -D" + propertyName
            );
        }

        return value;
    }

    private static void prepareCleanYandexTab(WebDriver driver) {
        driver.switchTo().newWindow(WindowType.TAB);
    }
}