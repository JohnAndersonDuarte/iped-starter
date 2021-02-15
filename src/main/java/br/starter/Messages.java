package br.starter;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class Messages {

    private static final String BUNDLE_NAME = "iped-starter-messages"; //$NON-NLS-1$


    private static ResourceBundle RESOURCE_BUNDLE;

    private Messages() {
    }

    public static String getString(String key) {
        if (RESOURCE_BUNDLE == null) {
            RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());
            String finalLocale = RESOURCE_BUNDLE.getLocale().toLanguageTag();
            if (finalLocale.equals("und")) //$NON-NLS-1$
                finalLocale = "en"; //$NON-NLS-1$
        }
        try {
            return RESOURCE_BUNDLE.getString(key);

        } catch (MissingResourceException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
