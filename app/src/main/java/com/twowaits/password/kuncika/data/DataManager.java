package com.twowaits.password.kuncika.data;

import com.twowaits.password.kuncika.data.local.db.DbHelper;
import com.twowaits.password.kuncika.data.local.prefs.PreferencesHelper;
import com.twowaits.password.kuncika.data.model.api.Response;
import com.twowaits.password.kuncika.data.model.api.Request;
import com.twowaits.password.kuncika.data.model.db.Apps;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface DataManager extends DbHelper, PreferencesHelper {


    void setUserAsLoggedOut();

    Single<Response> getAppsData(Request.AppsRequest appsRequest);

    Observable<List<Apps>> getAllInstalledApps();

    void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath);

    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }

}
