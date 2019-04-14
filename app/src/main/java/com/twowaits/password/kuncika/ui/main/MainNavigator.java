package com.twowaits.password.kuncika.ui.main;

import com.twowaits.password.kuncika.data.model.api.Response;
import com.twowaits.password.kuncika.data.model.db.Apps;

import java.util.List;

public interface MainNavigator {

    void handleError(Throwable throwable);

    void openLoginActivity();

    void updateApps(List<Apps> appsResponseList);

    void openCreateActivity();
}
