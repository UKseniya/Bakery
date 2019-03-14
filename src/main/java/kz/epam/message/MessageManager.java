package kz.epam.message;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {

    private static final String MESSAGE = "messages";

    private static ResourceBundle resourceBundle;
    private static MessageManager instance;

    public static MessageManager getInstance(Locale locale) {

            instance = new MessageManager();
            instance.resourceBundle = ResourceBundle.getBundle(MESSAGE, locale);

        return instance;
    }

    public String getProperty(String key) {
        return (String) resourceBundle.getObject(key);
    }

}
