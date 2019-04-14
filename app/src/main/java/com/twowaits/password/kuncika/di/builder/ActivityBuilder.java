package com.twowaits.password.kuncika.di.builder;

import com.twowaits.password.kuncika.ui.create.CreateActivity;
import com.twowaits.password.kuncika.ui.create.CreateViewModule;
import com.twowaits.password.kuncika.ui.lock.LockActivity;
import com.twowaits.password.kuncika.ui.main.MainActivity;
import com.twowaits.password.kuncika.ui.main.MainViewModule;
import com.twowaits.password.kuncika.ui.password.PasswordActivity;
import com.twowaits.password.kuncika.ui.password.PasswordsViewModule;
import com.twowaits.password.kuncika.ui.upload.UploadActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {
            MainViewModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract LockActivity bindLockActivity();

    @ContributesAndroidInjector
    abstract UploadActivity bindUploadActivity();

    @ContributesAndroidInjector(modules = {CreateViewModule.class})
    abstract CreateActivity bindCreateActivity();

    @ContributesAndroidInjector(modules = {PasswordsViewModule.class})
    abstract PasswordActivity bindPasswordActivity();

}
