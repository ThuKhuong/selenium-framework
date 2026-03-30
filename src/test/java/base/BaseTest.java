package base;

import org.apache.commons.io.FileUtils;
import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {

    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return driver.get();
    }

    @Parameters({"env"})
    @BeforeMethod
    public void setUp(@Optional("dev") String env) {
        String runtimeEnv = System.getProperty("env", env);
        String browser = System.getProperty("browser", "chrome");
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        String gridUrl = System.getProperty("grid.url", "");
        System.setProperty("env", runtimeEnv);

        ConfigReader.reset();
        ConfigReader config = ConfigReader.getInstance();

        System.out.println("Đang dùng môi trường: " + config.getEnvironment());
        System.out.println("explicit wait = " + config.getExplicitWait());

        driver.set(DriverFactory.createDriver(browser, headless, gridUrl));
        getDriver().manage().window().maximize();
        getDriver().get(config.getBaseUrl());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (getDriver() != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                attachScreenshot(getDriver());
            }
            takeScreenshot(result.getMethod().getMethodName(), getDriver());
            getDriver().quit();
            driver.remove();
        }
    }

    private void takeScreenshot(String testName, WebDriver driver) {
        try {
            Path path = Paths.get("target/screenshots");
            Files.createDirectories(path);

            String time = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = path.resolve(testName + "_" + time + ".png").toFile();

            FileUtils.copyFile(src, dest);
        } catch (IOException e) {
            System.out.println("Screenshot failed");
        }
    }

    @Attachment(value = "Anh chup khi that bai", type = "image/png")
    private byte[] attachScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}