package com.twowaits.password.kuncika.ui.main;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

@Module
public class MainViewModule {

    @Provides
    AppsAdapter provideAppsAdapter() {
        return new AppsAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(MainActivity activity) {
        return new LinearLayoutManager(activity);
    }

}
