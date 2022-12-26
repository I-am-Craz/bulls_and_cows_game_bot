package telegram.services;

import java.util.*;

public class LocalisationService{
    private static final Object lock = new Object();
    private static final String BASE_NAME = "strings";
    private static final ResourceBundle english;
    private static final ResourceBundle russian;

    static{
        synchronized (lock){
            english = ResourceBundle.getBundle(BASE_NAME, new Locale("en"));
            russian = ResourceBundle.getBundle(BASE_NAME, new Locale("ru"));
        }
    }

    public static String getString(String key, String code){
        String phrase = "";
        switch (code){
            case "ru" : phrase = russian.getString(key);
            break;
            case "en" : phrase = english.getString(key);
            break;
        }
        return phrase;
    }
}
