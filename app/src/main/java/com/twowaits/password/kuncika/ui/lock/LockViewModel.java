package com.twowaits.password.kuncika.ui.lock;

import com.twowaits.password.kuncika.data.DataManager;
import com.twowaits.password.kuncika.data.model.db.Apps;
import com.twowaits.password.kuncika.ui.base.BaseViewModel;
import com.twowaits.password.kuncika.util.rx.SchedulerProvider;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;

public class LockViewModel extends BaseViewModel<LockNavigator> {

    public MutableLiveData<Apps> appsData;
    public ObservableField<String> appIcon, appTitle;

    public LockViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        appIcon = new ObservableField<>();
        appTitle = new ObservableField<>();
        appsData = new MutableLiveData<>();
    }

    public void loadAppData(String packageName) {
        appIcon.set(packageName);
        getCompositeDisposable().add(getDataManager()
                                .loadByPackageName(packageName)
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(apps -> {
                                    appsData.setValue(apps);
                                    appTitle.set(apps.getName());
                                }));
    }

    public void checkPassword(String packageName, String password) {


    }

    public MutableLiveData<Apps> getApps() {
        return appsData;
    }
}
