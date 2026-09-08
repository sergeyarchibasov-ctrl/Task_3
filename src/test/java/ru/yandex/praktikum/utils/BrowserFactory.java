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
import java.util.Set;

public class BrowserFactory {

    private static final String YANDEX_BINARY =
            "C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe";

    private static final String YANDEX_DRIVER =
            "C:\\WebDriver\\yandex\\chromedriver.exe";

    public static WebDriver createDriver() {
        String browser = System.getProperty("browser", "chrome");

        if ("yandex".equalsIgnoreCase(browser)) {
            return createYandexDriver();
        }

        // Google Chrome не изменяем
        return new ChromeDriver();
    }

    private static WebDriver createYandexDriver() {
        ChromeOptions options = new ChromeOptions();

        options.setBinary(YANDEX_BINARY);

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
                        .usingDriverExecutable(new File(YANDEX_DRIVER))
                        .build();

        WebDriver driver = new ChromeDriver(service, options);

        prepareCleanYandexTab(driver);

        return driver;
    }

    private static void prepareCleanYandexTab(WebDriver driver) {
        String newTabHandle =
                driver.switchTo()
                        .newWindow(WindowType.TAB)
                        .getWindowHandle();

        Set<String> windowHandles = driver.getWindowHandles();

        for (String handle : windowHandles) {
            if (!handle.equals(newTabHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }

        driver.switchTo().window(newTabHandle);
    }
}