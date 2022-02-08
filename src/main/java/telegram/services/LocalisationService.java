package telegram.services;

import java.util.*;

public class LocalisationService {
    public static String currentLanguage = "en";
    private static final String STRINGS_FILE = "strings";

    public static String getString(String key){
        Locale locale = new Locale(currentLanguage);
        ResourceBundle rb = ResourceBundle.getBundle(STRINGS_FILE, locale);
        return rb.getString(key);
    }

}
