package com.twowaits.password.kuncika.data.local.db;

import com.twowaits.password.kuncika.data.model.db.Apps;
import com.twowaits.password.kuncika.data.model.db.Passwords;

import java.util.List;

import io.reactivex.Observable;

public interface DbHelper {

    public Observable<List<Apps>> loadAllApps();

    public Observable<Boolean> saveApps(List<Apps> appsList);

    public Observable<Boolean> deleteAll();

    public Observable<Boolean> insert(Apps apps);

    public Observable<Apps> checkExists(String name);

    public Observable<Apps> loadLast();

    public Observable<Boolean> delete(Apps apps);

    public Observable<Boolean> update(String name, String status);

    public Observable<Boolean> insertPasswords(Passwords passwords);

    public Observable<List<Passwords>> loadAll();

    public Observable<Boolean> updatePassword(String password, String type, int id);

    public Observable<Boolean> deletePassword(Passwords passwords);

    public Observable<Boolean> changePasswordValue(String value, String type, int id);

    public Observable<String[]> loadPackageNames();

    public Observable<Apps> loadByPackageName(String packageName);

}
