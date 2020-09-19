package com.cgg.streetvendor.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.cgg.streetvendor.application.SVSApplication;

import java.util.Locale;

public class LocaleHelper {

    public static void onCreate(Context context) {

        String lang;
        if(getLanguage(context).isEmpty()){
            lang = getPersistedData(context, Locale.getDefault().getLanguage());
        }else {
            lang = getLanguage(context);
        }

        setLocale(context, lang);
    }

    public static void onCreate(Context context, String defaultLanguage) {
        String lang = getPersistedData(context, defaultLanguage);
        setLocale(context, lang);
    }

    public static String getLanguage(Context context) {
        return getPersistedData(context, Locale.getDefault().getLanguage());
    }

    public static void setLocale(Context context, String language) {
        persist(context, language);
        updateResources(context, language);
    }

    private static String getPersistedData(Context context, String defaultLanguage) {
        SharedPreferences sharedPreferences = SVSApplication.get(context).getPreferences();
        return sharedPreferences.getString(AppConstants.LOCALE_LANG, defaultLanguage);
    }

    private static void persist(Context context, String language) {

        SharedPreferences sharedPreferences = SVSApplication.get(context).getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(AppConstants.LOCALE_LANG, language);
        editor.commit();
    }

    private static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());


    }
}
