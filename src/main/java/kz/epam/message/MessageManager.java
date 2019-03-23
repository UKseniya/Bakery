package kz.epam.message;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {

    private static final String MESSAGE = "messages";

    private static ResourceBundle resourceBundle;

    public static MessageManager getInstance(Locale locale) {

            MessageManager instance = new MessageManager();
            resourceBundle = ResourceBundle.getBundle(MESSAGE, locale);

        return instance;
    }

    public String getProperty(String key) {
        return (String) resourceBundle.getObject(key);
    }

}
