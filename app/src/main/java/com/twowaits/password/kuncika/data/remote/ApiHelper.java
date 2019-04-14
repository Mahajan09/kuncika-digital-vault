package com.twowaits.password.kuncika.data.remote;

import com.twowaits.password.kuncika.data.model.api.Response;
import com.twowaits.password.kuncika.data.model.api.Request;

import io.reactivex.Single;

public interface ApiHelper {

    Single<Response> getAppsData(Request.AppsRequest appsRequest);

}
