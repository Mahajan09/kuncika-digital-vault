package com.twowaits.password.kuncika.ui.password;

import android.util.Log;

import com.twowaits.password.kuncika.data.DataManager;
import com.twowaits.password.kuncika.data.model.db.Passwords;
import com.twowaits.password.kuncika.ui.base.BaseViewModel;
import com.twowaits.password.kuncika.util.rx.SchedulerProvider;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class PasswordViewModel extends BaseViewModel<PasswordNavigator> {

    public MutableLiveData<List<Passwords>> passwordsList;

    public PasswordViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        passwordsList = new MutableLiveData<>();
    }

    public void insertPassword(Passwords passwords) {
        getCompositeDisposable().add(getDataManager()
                                .insertPasswords(passwords)
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(aBoolean -> {

                                }, throwable -> {

                                }));
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

    public void deletePassword(Passwords passwords) {

        getCompositeDisposable().add(getDataManager()
                                .deletePassword(passwords)
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(aBoolean -> {

                                }, throwable ->  {

                                }));

    }

    public void updatePassword(Passwords passwords, int position) {

        getCompositeDisposable().add(getDataManager()
                                .changePasswordValue(passwords.getValue(), passwords.getType(), position)
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(aBoolean -> {

                                }, throwable -> {

                                }));

    }

    public MutableLiveData<List<Passwords>> getPasswordsList() {
        return passwordsList;
    }

}
