package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.UserData;

import java.io.File;
import java.io.IOException;
import java.util.List;
import utils.ConfigReader;

public class JsonReader {

    public static List<UserData> readUsers(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<UserData> users = objectMapper.readValue(
                    new File(filePath),
                    new TypeReference<List<UserData>>() {}
            );
            applySecretPlaceholders(users);
            return users;
        } catch (IOException e) {
            throw new RuntimeException("Cannot read JSON file: " + filePath, e);
        }
    }

    private static void applySecretPlaceholders(List<UserData> users) {
        ConfigReader config = ConfigReader.getInstance();
        for (UserData user : users) {
            user.setUsername(resolvePlaceholder(user.getUsername(), config));
            user.setPassword(resolvePlaceholder(user.getPassword(), config));
        }
    }

    private static String resolvePlaceholder(String value, ConfigReader config) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "${APP_USERNAME}":
                return config.getAppUsername();
            case "${APP_PASSWORD}":
                return config.getAppPassword();
            default:
                return value;
        }
    }
}