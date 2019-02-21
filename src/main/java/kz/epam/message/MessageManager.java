package kz.epam.message;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    private static final String MESSAGE = "messages";
    private  static MessageManager instance;
    private static ResourceBundle resourceBundle;

    public static MessageManager getInstance(Locale locale) {
        if (instance == null) {
            instance = new MessageManager();
            locale = new Locale("en");
            instance.resourceBundle = ResourceBundle.getBundle(MESSAGE, locale);
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resourceBundle.getObject(key);
    }

}
