package telegram.services;

import java.util.*;

public class LocalisationService {
    private volatile String currentLanguage = "en";
    private static final String STRINGS_FILE = "strings";

    private static LocalisationService instance;

    private LocalisationService(){}

    public static LocalisationService getInstance(){
        if(instance == null) {
            instance = new LocalisationService();
        }
        return instance;
    }

    public String getString(String key){
        Locale locale;
        synchronized (currentLanguage){
             locale = new Locale(currentLanguage);
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle(STRINGS_FILE, locale);
        return resourceBundle.getString(key);
    }

    public void setCurrentLanguage(String language){
        currentLanguage = language;
    }

}
