package com.aindrea.com.aindrea;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    public static final String Chinese = "i18n_zh_CN";
    public static final String English = "i18n_en_US";
    public static final String Default = "i18n";
    public static ResourceBundle bundle = null;

    public static boolean setLanguage(String language) {
        if (language.equals(Chinese)) {
            bundle = ResourceBundle.getBundle("i18n", new Locale("zh", "CN"));
            return true;
        } else if (language.equals(English)) {
            bundle = ResourceBundle.getBundle("i18n", new Locale("en", "US"));
            return true;
        } else if (language.equals(Default)) {
            bundle = ResourceBundle.getBundle("i18n", new Locale("zh", "CN"));
            return true;
        } else {
            return false;
        }
    }

    public String getString(String key) {
        if (bundle == null) {
            throw new IllegalStateException("Language not set");
        }
        return bundle.getString(key);
    }
}
