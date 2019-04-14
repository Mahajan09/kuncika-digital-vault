package com.twowaits.password.kuncika.data.local.db;

import android.util.Log;

import com.twowaits.password.kuncika.App;
import com.twowaits.password.kuncika.data.model.db.Apps;
import com.twowaits.password.kuncika.data.model.db.Passwords;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.databinding.ObservableList;
import io.reactivex.Observable;

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<Apps>> loadAllApps() {
        return Observable.fromCallable(new Callable<List<Apps>>() {
            @Override
            public List<Apps> call() {
                return mAppDatabase.appsDao().loadAll();
            }
        });
    }

    @Override
    public Observable<Boolean> saveApps(List<Apps> appsList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.appsDao().insertAll(appsList);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteAll() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.appsDao().deleteAll();
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> insert(Apps apps) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Log.d("insert", "dbhelper");
                mAppDatabase.appsDao().insert(apps);
                return true;
            }
        });
    }

    @Override
    public Observable<Apps> checkExists(String name) {
        return Observable.fromCallable(new Callable<Apps>() {
            @Override
            public Apps call() throws Exception {
                if(mAppDatabase.appsDao().findByName(name) == null)
                    return new Apps(0, "", "", "", "", "", "", "", "");
                else
                return mAppDatabase.appsDao().findByName(name);
            }
        });
    }

    @Override
    public Observable<Apps> loadLast() {
        return Observable.fromCallable(new Callable<Apps>() {
            @Override
            public Apps call() throws Exception {
                if(mAppDatabase.appsDao().loadLast() == null)
                    return new Apps(0, "", "", "", "", "", "", "", "");
                    else
                return mAppDatabase.appsDao().loadLast();
            }
        });
    }

    @Override
    public Observable<Boolean> delete(Apps apps) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.appsDao().delete(apps);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> update(String name, String status) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.appsDao().changeStatus(name, status);
                return null;
            }
        });
    }

    @Override
    public Observable<Boolean> insertPasswords(Passwords passwords) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Log.d("check_d", passwords.toString());
                mAppDatabase.passwordsDao().insert(passwords);
                return null;
            }
        });
    }


    @Override
    public Observable<List<Passwords>> loadAll() {
        return Observable.fromCallable(new Callable<List<Passwords>>() {
            @Override
            public List<Passwords> call() throws Exception {
                return mAppDatabase.passwordsDao().loadAll();
            }
        });
    }

    @Override
    public Observable<Boolean> updatePassword(String password, String type, int id) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.appsDao().updatePassword(password, type, id);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deletePassword(Passwords passwords) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.passwordsDao().delete(passwords);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> changePasswordValue(String value, String type, int id) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.passwordsDao().updatePassword(value, type, id);
                return true;
            }
        });
    }

    @Override
    public Observable<String[]> loadPackageNames() {
        return Observable.fromCallable(new Callable<String[]>() {
            @Override
            public String[] call() throws Exception {

                List<Apps> appsList = mAppDatabase.appsDao().loadAllActiveApps();
                String[] apps = new String[appsList.size()];
                int i = 0;
                for (Apps a: appsList) {
                    Log.d("check_app", a.getName() + " : " + a.getStatus());
                    apps[i] = a.getIcon();
                    i++;
                }
                return apps;
            }
        });
    }

    @Override
    public Observable<Apps> loadByPackageName(String packageName) {
        return Observable.fromCallable(new Callable<Apps>() {
            @Override
            public Apps call() throws Exception {
                return mAppDatabase.appsDao().loadByPackageName(packageName);
            }
        });
    }
}