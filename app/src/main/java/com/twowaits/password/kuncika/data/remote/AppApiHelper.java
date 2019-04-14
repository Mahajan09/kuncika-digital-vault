package com.twowaits.password.kuncika.data.remote;

import android.app.Application;

import com.twowaits.password.kuncika.data.model.api.Response;
import com.twowaits.password.kuncika.data.model.api.Request;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppApiHelper implements ApiHelper {


    ApiEndPoint apiEndPoint;

    @Inject
    public AppApiHelper(ApiEndPoint apiEndPoint1) {
        apiEndPoint = apiEndPoint1;
    }

    @Override
    public Single<Response> getAppsData(Request.AppsRequest appsRequest) {
        return apiEndPoint.getAllApps(appsRequest);
    }
}
