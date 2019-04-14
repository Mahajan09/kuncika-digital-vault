package com.twowaits.password.kuncika.data;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.gson.Gson;
import com.twowaits.password.kuncika.data.local.db.DbHelper;
import com.twowaits.password.kuncika.data.local.prefs.PreferencesHelper;
import com.twowaits.password.kuncika.data.model.api.Response;
import com.twowaits.password.kuncika.data.model.api.Request;
import com.twowaits.password.kuncika.data.model.db.Apps;
import com.twowaits.password.kuncika.data.model.db.Passwords;
import com.twowaits.password.kuncika.data.remote.ApiHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.databinding.ObservableList;
import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class AppDataManager implements DataManager {


    private final ApiHelper mApiHelper;

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, Gson gson, ApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mGson = gson;
        mApiHelper = apiHelper;
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        //mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }


    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public Single<Response> getAppsData(Request.AppsRequest appsRequest) {
        return mApiHelper.getAppsData(appsRequest);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                null);
    }


    @Override
    public void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath) {

        setAccessToken(accessToken);
    }

    @Override
    public Observable<List<Apps>> loadAllApps() {
        return mDbHelper.loadAllApps();
    }

    @Override
    public Observable<Boolean> saveApps(List<Apps> appsList) {
        return mDbHelper.saveApps(appsList);
    }

    @Override
    public Observable<Boolean> deleteAll() {
        return mDbHelper.deleteAll();
    }

    @Override
    public Observable<List<Apps>> getAllInstalledApps() {

        List<Apps> appsList = new ArrayList<>();

        final PackageManager pm = mContext.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo != null && packageInfo.name != null && !packageInfo.name.equals("")) {

                    Apps apps = new Apps(pm.getApplicationLabel(packageInfo).toString(), packageInfo.packageName);
                    appsList.add(apps);

            }
        }

        Collections.sort(appsList, new Comparator<Apps>() {
            @Override
            public int compare(Apps s1, Apps s2) {
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });

        return Observable.fromCallable(new Callable<List<Apps>>() {

            @Override
            public List<Apps> call() {
                return appsList;
            }
        });
    }

    @Override
    public Observable<Boolean> insert(Apps apps) {
        Log.d("insert", "datamanager");;
        return mDbHelper.insert(apps);
    }

    @Override
    public Observable<Apps> checkExists(String name) {
        return mDbHelper.checkExists(name);
    }

    @Override
    public Observable<Apps> loadLast() {
        return mDbHelper.loadLast();
    }

    @Override
    public void addIdToLoaded(String id) {
        mPreferencesHelper.addIdToLoaded(id);
    }

    @Override
    public String getIdLoaded() {
        return mPreferencesHelper.getIdLoaded();
    }

    @Override
    public Observable<Boolean> delete(Apps apps) {
        return mDbHelper.delete(apps);
    }

    @Override
    public Observable<Boolean> update(String name, String status) {
        return mDbHelper.update(name, status);
    }

    @Override
    public Observable<Boolean> insertPasswords(Passwords passwords) {
        return mDbHelper.insertPasswords(passwords);
    }

    @Override
    public Observable<List<Passwords>> loadAll() {
        return mDbHelper.loadAll();
    }

    @Override
    public Observable<Boolean> updatePassword(String password, String type, int id) {
        return mDbHelper.updatePassword(password, type, id);
    }

    @Override
    public Observable<Boolean> deletePassword(Passwords passwords) {
        return mDbHelper.deletePassword(passwords);
    }

    @Override
    public Observable<Boolean> changePasswordValue(String value, String type, int id) {
        return mDbHelper.changePasswordValue(value, type, id);
    }

    @Override
    public Observable<String[]> loadPackageNames() {
        return mDbHelper.loadPackageNames();
    }

    @Override
    public Observable<Apps> loadByPackageName(String packageName) {
        return mDbHelper.loadByPackageName(packageName);
    }

    @Override
    public Observable<Boolean> savePackageNames(String names) {
        return mPreferencesHelper.savePackageNames(names);
    }

    @Override
    public String getPackageNames() {
        return mPreferencesHelper.getPackageNames();
    }
}
