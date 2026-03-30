package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    public static WebDriver createDriver(String browser, boolean headless, String gridUrl) {
        String normalized = browser == null ? "chrome" : browser.trim().toLowerCase();
        boolean useGrid = gridUrl != null && !gridUrl.isBlank();

        switch (normalized) {
            case "firefox":
                return useGrid
                        ? createFirefoxGridDriver(headless, gridUrl)
                        : createFirefoxDriver(headless);
            case "chrome":
            default:
                return useGrid
                        ? createChromeGridDriver(headless, gridUrl)
                        : createChromeDriver(headless);
        }
    }

    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }
        return new ChromeDriver(options);
    }

    private static WebDriver createChromeGridDriver(boolean headless, String gridUrl) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }
        return createRemoteDriver(gridUrl, options);
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        return new FirefoxDriver(options);
    }

    private static WebDriver createFirefoxGridDriver(boolean headless, String gridUrl) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        return createRemoteDriver(gridUrl, options);
    }

    private static WebDriver createRemoteDriver(String gridUrl, org.openqa.selenium.Capabilities options) {
        try {
            return new RemoteWebDriver(new URL(gridUrl), options);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid grid.url: " + gridUrl, e);
        }
    }
}
