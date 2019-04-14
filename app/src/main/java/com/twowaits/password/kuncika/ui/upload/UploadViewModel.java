package com.twowaits.password.kuncika.ui.upload;

import android.util.Log;

import com.twowaits.password.kuncika.data.DataManager;
import com.twowaits.password.kuncika.ui.base.BaseViewModel;
import com.twowaits.password.kuncika.util.rx.SchedulerProvider;

public class UploadViewModel extends BaseViewModel<UploadNavigator> {

    public UploadViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        super(dataManager, schedulerProvider);
        setIsLoading(false);
    }

    public void upload() {
        Log.d("clicked", getIsLoading() + "");
        Log.d("clicked", "1");
        setIsLoading(true);
    }

}
