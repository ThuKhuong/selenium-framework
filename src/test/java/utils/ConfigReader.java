package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static ConfigReader instance;
    private final Properties properties;

    private ConfigReader() {
        properties = new Properties();

        String env = System.getProperty("env", "dev");
        String fileName = "config/config-" + env + ".properties";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new RuntimeException("Không tìm thấy file config: " + fileName);
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Không thể load file config: " + fileName, e);
        }
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }

    public String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicit.wait"));
    }

    public String getEnvironment() {
        return properties.getProperty("environment");
    }

    public int getRetryCount() {
        return Integer.parseInt(properties.getProperty("retry.count", "0"));
    }

    public String getAppUsername() {
        String username = System.getenv("APP_USERNAME");
        if (username == null || username.isBlank()) {
            username = properties.getProperty("app.username");
        }
        return username;
    }

    public String getAppPassword() {
        String password = System.getenv("APP_PASSWORD");
        if (password == null || password.isBlank()) {
            password = properties.getProperty("app.password");
        }
        return password;
    }
}