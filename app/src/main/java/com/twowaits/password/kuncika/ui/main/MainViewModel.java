package com.twowaits.password.kuncika.ui.main;


import android.util.Log;

import com.twowaits.password.kuncika.data.DataManager;
import com.twowaits.password.kuncika.data.model.api.Request;
import com.twowaits.password.kuncika.data.model.api.Response;
import com.twowaits.password.kuncika.data.model.db.Apps;
import com.twowaits.password.kuncika.data.model.db.Passwords;
import com.twowaits.password.kuncika.ui.base.BaseViewModel;
import com.twowaits.password.kuncika.util.AppLogger;
import com.twowaits.password.kuncika.util.rx.SchedulerProvider;
import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    public MutableLiveData<List<Apps>> appsList;
    public MutableLiveData<List<Passwords>> passwordsList;
    public MutableLiveData<String[]> stringsArray;
    public MutableLiveData<String> stringsList;
    public ObservableField<String> appsCount;
    String arrayString = "";

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        setIsLoading(false);
        appsList = new MutableLiveData<>();
        passwordsList = new MutableLiveData<>();
        stringsArray = new MutableLiveData<>();
        stringsList = new MutableLiveData<>();
        appsCount = new ObservableField<>("0 apps protected!");
        //getCompositeDisposable().add(getDataManager().deleteAll().subscribeOn(getSchedulerProvider().io()).subscribeOn(getSchedulerProvider().ui()).subscribe(aBoolean -> {}, throwable -> {}));
        //loadQuestionCards();
        //loadFromDB();
    }

    public MutableLiveData<List<Apps>> getAppsList() {
        return appsList;
    }

    public void loadQuestionCards() {

        Request.AppsRequest appsRequest = new Request.AppsRequest("1001", "Achintya");

        getCompositeDisposable().add(getDataManager()
                .getAppsData(appsRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.getData() != null) {
                        Log.d("asd", response.getData().toString() + " -- in return");
                        saveToDb(convertObjects(response.getData()));
                    }
                    setIsLoading(false);
                }, throwable -> {
                    AppLogger.d(throwable.getStackTrace() + "");
                }));
    }

    public List<Apps> convertObjects(List<Response.AppsResponse> appsResponseList) {
        List<Apps> appsList1 = new ArrayList<>();
        int i = 0;
        for (Response.AppsResponse appsResponse:appsResponseList) {
            appsList1.add(new Apps(Integer.parseInt(appsResponse.getId()), appsResponse.getAppTitle(), appsResponse.getAppDetails(), appsResponse.getAppIcon(), appsResponse.getAppPassword(), appsResponse.getAppStatus(), appsResponse.getAppType(), appsResponse.getCreatedAt(), appsResponse.getUpdatedAt()));
            Log.d("asd", appsList1.get(i).toString());
            i++;
        }
        return appsList1;
    }

    public void saveToDb(List<Apps> insertList) {
        getCompositeDisposable().add(getDataManager()
                .saveApps(insertList)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(aBoolean -> {
                    Log.d("asd", insertList.size() + "after db call");
                    loadFromDB();
                }, throwable -> {
                    throwable.printStackTrace();
                }));
    }

    public void loadFromDB() {
        getCompositeDisposable().add(getDataManager()
                                .loadAllApps()
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(appsList1 -> {
                                    appsList.setValue(appsList1);
                                    appsCount.set(appsList1.size() + " apps protected!");
                                    Log.d("asd", appsList1.size() + "after loading");
                                }, throwable -> {
                                    throwable.printStackTrace();
                                }));
    }

    public void deleteApps(Apps apps) {

        getCompositeDisposable().add(getDataManager()
                                .delete(apps)
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(aBoolean -> {
                                    appsList.getValue().remove(apps);
                                    appsCount.set(appsList.getValue().size() + " apps protected!");
                                    loadPackageOfApps();
                                }, throwable -> {

                                }));

    }

    public void changeLock(String name, String status) {

        getCompositeDisposable().add(getDataManager()
                                .update(name, status)
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(aBoolean -> {

                                }, throwable -> {

                                }));

    }

    public void updatePassword(Apps apps, Passwords passwords) {

        getCompositeDisposable().add(getDataManager()
                                .updatePassword(passwords.getValue(), passwords.getType(), apps.getId())
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(aBoolean -> {
                                    Log.d("asd", "1");
                                }, throwable -> {
                                    throwable.printStackTrace();
                                    Log.d("asd", "2");
                                }));

    }


    public void loadPasswordsFromDb() {

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
        int l = stringsArray.length;
        arrayString = "";
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
                                    stringsList.setValue(arrayString);
                                }, throwable -> {

                                }));

            Log.d("hfhfg", arrayString);

    }

    public MutableLiveData<String> getStringsList() {
        return stringsList;
    }

    public MutableLiveData<String[]> getStringsArray() {
        return stringsArray;
    }

    public ObservableField<String> getAppsCount() {
        return appsCount;
    }

    public MutableLiveData<List<Passwords>> getPasswordsList() {
        return passwordsList;
    }
}
