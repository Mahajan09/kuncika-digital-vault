package com.twowaits.password.kuncika.ui.password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.twowaits.password.kuncika.BR;
import com.twowaits.password.kuncika.R;
import com.twowaits.password.kuncika.ViewModelProviderFactory;
import com.twowaits.password.kuncika.data.model.db.Passwords;
import com.twowaits.password.kuncika.databinding.ActivityPasswordBinding;
import com.twowaits.password.kuncika.ui.base.BaseActivity;
import com.twowaits.password.kuncika.ui.create.CreatePasswordDialog;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PasswordActivity extends BaseActivity<ActivityPasswordBinding, PasswordViewModel> implements PasswordNavigator, PasswordsAdapter.Clicker {

    @Inject
    ViewModelProviderFactory factory;
    PasswordViewModel passwordViewModel;
    ActivityPasswordBinding activityPasswordBinding;
    FloatingActionButton fab;
    @Inject
    PasswordsAdapter passwordsAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_password;
    }

    @Override
    public PasswordViewModel getViewModel() {
        passwordViewModel = ViewModelProviders.of(this, factory).get(PasswordViewModel.class);
        return passwordViewModel;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PasswordActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPasswordBinding = getViewDataBinding();
        passwordViewModel.setNavigator(this);
        passwordsAdapter.setListener(this);
        setUp();
        subscribeToLiveData();
    }

    public void setUp() {

        activityPasswordBinding.appsRecyclerView.setAdapter(passwordsAdapter);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        activityPasswordBinding.appsRecyclerView.setLayoutManager(linearLayoutManager);

        fab = activityPasswordBinding.plusButton;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupToCreateDialog();
            }
        });
        passwordsAdapter.setShowMenu(true);
        passwordViewModel.loadFromDb();

    }

    public void showPopupToCreateDialog() {

        CreatePasswordDialog createPasswordDialog = new CreatePasswordDialog(this, new CreatePasswordDialog.Clicker() {
            @Override
            public void onClick(Passwords passwords) {
                passwordViewModel.insertPassword(passwords);
            }
        }, true, "");

        createPasswordDialog.show();

    }

    public void subscribeToLiveData() {
        passwordViewModel.getPasswordsList().observe(this, passwordsList -> { passwordsAdapter.addItems(passwordsList); });
    }

    @Override
    public void selectedPassword(Passwords passwords) {
    }

    @Override
    public void deletePassword(Passwords passwords) {
        passwordViewModel.deletePassword(passwords);
    }

    @Override
    public void changePassword(int position, int id, String name) {
        CreatePasswordDialog createPasswordDialog = new CreatePasswordDialog(this, new CreatePasswordDialog.Clicker() {
            @Override
            public void onClick(Passwords passwords) {
                passwordViewModel.updatePassword(passwords, id);
                passwordsAdapter.updatePassword(position, passwords);
            }
        }, true, name);

        createPasswordDialog.show();
    }

}
