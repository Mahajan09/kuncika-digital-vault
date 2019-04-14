package com.twowaits.password.kuncika.ui.service;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.twowaits.password.kuncika.data.AppDataManager;
import com.twowaits.password.kuncika.data.DataManager;
import com.twowaits.password.kuncika.data.local.prefs.AppPreferencesHelper;
import com.twowaits.password.kuncika.data.local.prefs.PreferencesHelper;
import com.twowaits.password.kuncika.data.model.db.Apps;
import com.twowaits.password.kuncika.ui.lock.LockActivity;
import com.twowaits.password.kuncika.ui.main.MainActivity;
import com.twowaits.password.kuncika.util.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppService extends Service
{

    String CURRENT_PACKAGE_NAME = "", lastOpened = "";
    public static AppService instance;
    Context context = this;
    String[] apps;
    List<Apps> appsList;
    PreferencesHelper preferencesHelper;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub

        appsList = new ArrayList<>();
        preferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
        scheduleMethod();
        CURRENT_PACKAGE_NAME = getApplicationContext().getPackageName();
        Log.d("Current PN", "" + CURRENT_PACKAGE_NAME);

        instance = this;

        return START_STICKY;
    }

    private void scheduleMethod() {
        // TODO Auto-generated method stub

        ScheduledExecutorService scheduler = Executors
                .newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {

            String currentApp = "";

            @Override
            public void run() {
                // TODO Auto-generated method stub

                apps = preferencesHelper.getPackageNames().split(",");
                printArray();

                currentApp = getForegroundApp();
                if (!currentApp.equals(CURRENT_PACKAGE_NAME) && checkOpened(currentApp)) {
                    Log.d("activity on TOp", lastOpened+ " last");
                    if (lastOpened.equals("") || !currentApp.equals(lastOpened)) {
                        Log.d("activity on TOp",  currentApp + " in");
                        Intent intent = new Intent(context, LockActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("packageName", currentApp);
                        startActivity(intent);
                        lastOpened = currentApp;
                    }
                }
            }
        }, 0, 1500, TimeUnit.MILLISECONDS);
    }

    public void checkRunningApps() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
        String activityOnTop = ar.baseActivity.getPackageName();

        // Provide the packagename(s) of apps here, you want to show password activity
        if (activityOnTop.contains("whatsapp")  // you can make this check even better
                || activityOnTop.contains(CURRENT_PACKAGE_NAME)) {
            // Show Password Activity
            MainActivity.newIntent(this);
        } else {
            // DO nothing
        }
    }

    public String getForegroundApp() {
        String currentApp = "NULL";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            if (appList != null && appList.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }

        return currentApp;
    }

    public boolean searchInArr(String str) {
        for (int i = 0; i < apps.length; i++){
            if (str.equals(apps[i])) return true;
        }
        return false;
    }

    public void printArray(){
        appsList.clear();
        for (int i = 0; i < apps.length; i++) {
            Apps appsO = new Apps(apps[i], "false");
            appsList.add(i, appsO);
            Log.d("in_service", apps[i] + " -- " + apps.length);
        }
    }
    
    public boolean checkOpened(String name) {

        int i = 0;
        Log.d("activity on TOp",  name + " check opened");

        for (Apps a: appsList) {
            if (a.getName().equals(name)) {
                if (a.getIcon().equals("false")) {
                    appsList.get(i).setIcon("true");
                    return true;
                } else {
                    return false;
                }
            }
            i++;
        }
        Log.d("activity on TOp",  name + " set all");
        setAllFalse();
        
        return false;
    }

    public void setAllFalse() {
        int i = 0;

        for (Apps a: appsList) {
            appsList.get(i).setIcon("false");
            i++;
        }
        lastOpened = "";
    }


}
