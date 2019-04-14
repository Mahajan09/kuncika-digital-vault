package com.twowaits.password.kuncika.ui.create;

import com.twowaits.password.kuncika.ui.main.MainActivity;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

@Module
public class CreateViewModule {

    @Provides
    CreateAppAdapter providesCreateAppAdapter() { return new CreateAppAdapter(new ArrayList<>()); }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(CreateActivity activity) {
        return new LinearLayoutManager(activity);
    }

}
