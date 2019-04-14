package com.twowaits.password.kuncika.data.local.prefs;

import com.twowaits.password.kuncika.data.DataManager;

import io.reactivex.Observable;

public interface PreferencesHelper {

    String getAccessToken();

    void setAccessToken(String accessToken);

    int getCurrentUserLoggedInMode();

    void addIdToLoaded(String id);

    String getIdLoaded();

    Observable<Boolean> savePackageNames(String names);

    String getPackageNames();

}
