package com.twowaits.password.kuncika.ui.create;

import android.util.Log;

import com.twowaits.password.kuncika.data.DataManager;
import com.twowaits.password.kuncika.data.model.db.Apps;
import com.twowaits.password.kuncika.data.model.db.Passwords;
import com.twowaits.password.kuncika.ui.base.BaseViewModel;
import com.twowaits.password.kuncika.util.CommonUtils;
import com.twowaits.password.kuncika.util.rx.SchedulerProvider;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class CreateViewModel extends BaseViewModel<CreateNavigator> {

    public MutableLiveData<List<Passwords>> passwordsList;
    public MutableLiveData<List<Apps>> appsList;
    Boolean inserted;
    String arrayString = "";

    public CreateViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        setIsLoading(false);
        appsList = new MutableLiveData<>();
        passwordsList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Apps>> getAppsList() {
        return appsList;
    }

    public void setAppsList(MutableLiveData<List<Apps>> appsList) {
        this.appsList = appsList;
    }

    public void load() {
        getCompositeDisposable().add(getDataManager().getAllInstalledApps()
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(appsList1 -> {
                                    Log.d("TAGAG", "asd");
                                    appsList.setValue(appsList1);
                                }, throwable -> {

                                }));
    }

    public Boolean insert(Apps apps) {
        inserted = false;
        apps.setCreatedAt(CommonUtils.getTimestamp());
        apps.setUpdatedAt(CommonUtils.getTimestamp());
        apps.setStatus("active");
        getCompositeDisposable().add(getDataManager()
                                .checkExists(apps.getName())
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(apps1 -> {
                                    if (apps1.getName() == "") {
                                        getCompositeDisposable().add(getDataManager().insert(apps).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(aBoolean ->  {
                                            loadPackageOfApps();
                                        }, throwable -> {throwable.printStackTrace();}));
                                    }
                                    inserted = true;
                                }, throwable -> {
                                    throwable.printStackTrace();
                                    inserted = false;
                                }));


        Log.d("insert", "view model later");
        return inserted;
    }

    public void loadFromDb() {

        getCompositeDisposable().add(getDataManager()
                .loadAll()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(passwordsList1 -> {
                    Log.d("check_d", passwordsList1.size() + " after");
                    passwordsList.setValue(passwordsList1);
                }, throwable -> {

                }));

    }

    public void loadPackageOfApps() {

        getCompositeDisposable().add(getDataManager()
                .loadPackageNames()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(strings -> {
                    saveToPrefs(strings);
                }, throwable -> {
                    throwable.printStackTrace();
                }));

    }

    public void saveToPrefs(String[] stringsArray) {
        arrayString = "";
        int l = stringsArray.length;
        for (int i = 0; i < l; i++) {
            if (i == (l - 1))
                arrayString += stringsArray[i];
            else
                arrayString += stringsArray[i] + ",";
        }

        getCompositeDisposable().add(getDataManager()
                .savePackageNames(arrayString)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(aBoolean -> {

                }, throwable -> {

                }));

        Log.d("hfhfg", arrayString);

    }

    public MutableLiveData<List<Passwords>> getPasswordsList() {
        return passwordsList;
    }
}
