package com.twowaits.password.kuncika;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

import com.twowaits.password.kuncika.di.component.DaggerAppComponent;
import com.twowaits.password.kuncika.ui.service.AppService;
import com.twowaits.password.kuncika.util.AppLogger;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppLogger.init();

        DaggerAppComponent.builder().application(this).build().inject(this);
    }
}