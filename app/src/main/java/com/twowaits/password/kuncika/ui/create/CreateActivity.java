package com.twowaits.password.kuncika.ui.create;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.twowaits.password.kuncika.BR;
import com.twowaits.password.kuncika.R;
import com.twowaits.password.kuncika.ViewModelProviderFactory;
import com.twowaits.password.kuncika.data.model.db.Apps;
import com.twowaits.password.kuncika.data.model.db.Passwords;
import com.twowaits.password.kuncika.databinding.ActivityCreateBinding;
import com.twowaits.password.kuncika.ui.base.BaseActivity;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CreateActivity extends BaseActivity<ActivityCreateBinding, CreateViewModel> implements CreateNavigator, CreateAppAdapter.Click {

    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    CreateAppAdapter createAppAdapter;
    @Inject
    ViewModelProviderFactory factory;
    CreateViewModel createViewModel;
    ActivityCreateBinding activityCreateBinding;
    CreatePasswordDialog createPasswordDialog;

    public static Intent newIntent(Context context) {
        return new Intent(context, CreateActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create;
    }

    @Override
    public CreateViewModel getViewModel() {
        createViewModel = ViewModelProviders.of(this, factory).get(CreateViewModel.class);
        return createViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreateBinding = getViewDataBinding();
        createViewModel.setNavigator(this);
        setUp();
        subscribeToLiveData();
        createAppAdapter.setListener(this);
    }

    private void setUp() {
        activityCreateBinding.appsRecyclerView.setAdapter(createAppAdapter);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        activityCreateBinding.appsRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public void subscribeToLiveData() {
        createViewModel.getAppsList().observe(this, appsList -> { createAppAdapter.addItems(appsList); Toast.makeText(this, appsList.size() + " apps", Toast.LENGTH_SHORT).show(); });
    }


    @Override
    public void onClick(Apps apps, int position) {

        createViewModel.getPasswordsList().observe(this, passwordsList -> { createPasswordDialog.updateList(passwordsList); });
        createViewModel.loadFromDb();

        createPasswordDialog = new CreatePasswordDialog(this, new CreatePasswordDialog.Clicker() {
            @Override
            public void onClick(Passwords passwords) {
                apps.setPassword(passwords.getValue());
                apps.setType(passwords.getType());
                createViewModel.insert(apps);
                createAppAdapter.removePosition(position);
            }
        }, false, "");

        createPasswordDialog.show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        createViewModel.load();
    }
}
