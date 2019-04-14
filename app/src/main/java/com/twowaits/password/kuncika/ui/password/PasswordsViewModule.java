package com.twowaits.password.kuncika.ui.password;

import com.twowaits.password.kuncika.ui.main.AppsAdapter;
import com.twowaits.password.kuncika.ui.main.MainActivity;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

@Module
public class PasswordsViewModule {

    @Provides
    PasswordsAdapter provideAppsAdapter() {
        return new PasswordsAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(PasswordActivity activity) {
        return new LinearLayoutManager(activity);
    }

}
