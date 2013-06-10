package by.bsu.fpmi.second.dudkoAA;

import java.util.ResourceBundle;

/** Class for getting messages from properties. */
public class Messager {

    /**
     * Get message from property file.
     *
     * @param key the key in property file
     * @return the message from property file
     */
    public static String getMessage(final String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages");
        return bundle.getString(key);
    }
}