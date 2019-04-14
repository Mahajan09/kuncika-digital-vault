package com.twowaits.password.kuncika;

import com.twowaits.password.kuncika.data.DataManager;
import com.twowaits.password.kuncika.ui.create.CreateViewModel;
import com.twowaits.password.kuncika.ui.create.CustomCreateViewModel;
import com.twowaits.password.kuncika.ui.lock.LockViewModel;
import com.twowaits.password.kuncika.ui.main.MainViewModel;
import com.twowaits.password.kuncika.ui.password.PasswordViewModel;
import com.twowaits.password.kuncika.ui.upload.UploadViewModel;
import com.twowaits.password.kuncika.util.rx.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public ViewModelProviderFactory(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        if(modelClass.isAssignableFrom(LockViewModel.class))
            return (T) new LockViewModel(dataManager, schedulerProvider);
        else if(modelClass.isAssignableFrom(UploadViewModel.class))
            return (T) new UploadViewModel(dataManager, schedulerProvider);
        else if(modelClass.isAssignableFrom(PasswordViewModel.class))
            return (T) new PasswordViewModel(dataManager, schedulerProvider);
        else if(modelClass.isAssignableFrom(CreateViewModel.class))
            return (T) new CreateViewModel(dataManager, schedulerProvider);
        else if(modelClass.isAssignableFrom(CustomCreateViewModel.class))
            return (T) new CustomCreateViewModel(dataManager, schedulerProvider);
        else if(modelClass.isAssignableFrom(MainViewModel.class)) return (T) new MainViewModel(dataManager, schedulerProvider);
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}