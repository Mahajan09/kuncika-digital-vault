package com.twowaits.password.kuncika.data.local.prefs;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.twowaits.password.kuncika.data.DataManager;
import com.twowaits.password.kuncika.di.PreferenceInfo;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";

    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";

    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";

    private static final String PREF_KEY_PACKAGE_NAMES = "PREF_KEY_PACKAGE_NAMES";

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";

    private static final String PREF_KEY_ID_LOADED = "PREF_KEY_ID_LOADED";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPrefs.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public String getAccessToken() {
        return null;
    }

    @Override
    public void setAccessToken(String accessToken) {

    }

    @Override
    public void addIdToLoaded(String id) {
        mPrefs.edit().putString(PREF_KEY_ID_LOADED, id).apply();
    }


    @Override
    public Observable<Boolean> savePackageNames(String names) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Log.d("in_service_edit", names);
                mPrefs.edit().putString(PREF_KEY_PACKAGE_NAMES, names).apply();
                return true;
            }
        });

    }

    @Override
    public String getPackageNames() {
        Log.d("in_service_load", mPrefs.getString(PREF_KEY_PACKAGE_NAMES, ""));
        return mPrefs.getString(PREF_KEY_PACKAGE_NAMES, "");
    }

    @Override
    public String getIdLoaded() {
        return mPrefs.getString(PREF_KEY_ID_LOADED, "");
    }
}
