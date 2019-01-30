package kz.epam.config;

import java.util.ResourceBundle;

public class ConfigManager {
    private static ConfigManager instance;
    private ResourceBundle resourceBundle;
    private static final String BUNDLE_NAME = "config";
    public static final String DATABASE_DRIVER_NAME = "DRIVER_NAME";
    public static final String DATABASE_URL = "DATABASE_URL";
    public static final String DATABASE_USER = "DATABASE_USER";
    public static final String DATABASE_PASSWORD = "DATABASE_PASSWORD";
    public static final String MAX_CONN = "MAX_CONN";

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
            instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String getProperty (String key) {
        return (String) resourceBundle.getObject(key);
    }
}
