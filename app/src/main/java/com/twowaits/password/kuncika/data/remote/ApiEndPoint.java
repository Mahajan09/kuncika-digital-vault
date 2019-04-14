package com.twowaits.password.kuncika.data.remote;

import com.google.gson.JsonObject;
import com.twowaits.password.kuncika.data.model.api.Request;
import com.twowaits.password.kuncika.data.model.api.Response;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiEndPoint {

    @POST("load.php")
    Single<Response> getAllApps(@Body Request.AppsRequest appsRequest);

}
