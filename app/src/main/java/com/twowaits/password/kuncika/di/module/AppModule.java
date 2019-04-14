package com.twowaits.password.kuncika.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twowaits.password.kuncika.BuildConfig;
import com.twowaits.password.kuncika.data.AppDataManager;
import com.twowaits.password.kuncika.data.DataManager;
import com.twowaits.password.kuncika.data.local.db.AppDatabase;
import com.twowaits.password.kuncika.data.local.db.AppDbHelper;
import com.twowaits.password.kuncika.data.local.db.DbHelper;
import com.twowaits.password.kuncika.data.local.prefs.AppPreferencesHelper;
import com.twowaits.password.kuncika.data.local.prefs.PreferencesHelper;
import com.twowaits.password.kuncika.data.remote.ApiClient;
import com.twowaits.password.kuncika.data.remote.ApiEndPoint;
import com.twowaits.password.kuncika.data.remote.ApiHelper;
import com.twowaits.password.kuncika.data.remote.AppApiHelper;
import com.twowaits.password.kuncika.di.ApiInfo;
import com.twowaits.password.kuncika.di.DatabaseInfo;
import com.twowaits.password.kuncika.di.PreferenceInfo;
import com.twowaits.password.kuncika.util.AppConstants;
import com.twowaits.password.kuncika.util.rx.AppSchedulerProvider;
import com.twowaits.password.kuncika.util.rx.SchedulerProvider;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration()
                .build();
    }

    /*@Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }*/

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    ApiEndPoint getApiEndPoint(Context context) {
        return ApiClient.getClient(context).create(ApiEndPoint.class);
    }


    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

}
