package com.twowaits.password.kuncika.di.component;

import android.app.Application;

import com.twowaits.password.kuncika.App;
import com.twowaits.password.kuncika.di.builder.ActivityBuilder;
import com.twowaits.password.kuncika.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(App app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}