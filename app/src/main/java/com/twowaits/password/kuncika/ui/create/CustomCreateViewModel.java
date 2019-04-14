package com.twowaits.password.kuncika.ui.create;

import com.twowaits.password.kuncika.data.DataManager;
import com.twowaits.password.kuncika.ui.base.BaseViewModel;
import com.twowaits.password.kuncika.util.rx.SchedulerProvider;

public class CustomCreateViewModel extends BaseViewModel<CreateNavigator> {

    public CustomCreateViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

}
