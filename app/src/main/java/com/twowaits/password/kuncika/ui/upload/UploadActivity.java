package com.twowaits.password.kuncika.ui.upload;

import android.os.Bundle;

import com.twowaits.password.kuncika.BR;
import com.twowaits.password.kuncika.R;
import com.twowaits.password.kuncika.ViewModelProviderFactory;
import com.twowaits.password.kuncika.databinding.ActivityUploadBinding;
import com.twowaits.password.kuncika.ui.base.BaseActivity;
import com.twowaits.password.kuncika.ui.main.MainViewModel;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

public class UploadActivity extends BaseActivity<ActivityUploadBinding, UploadViewModel> implements UploadNavigator {

    @Inject
    ViewModelProviderFactory factory;
    ActivityUploadBinding activityUploadBinding;
    UploadViewModel uploadViewModel;
    Toolbar toolbar;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_upload;
    }

    @Override
    public UploadViewModel getViewModel() {
        uploadViewModel = ViewModelProviders.of(this, factory).get(UploadViewModel.class);
        return uploadViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUploadBinding = getViewDataBinding();
        uploadViewModel.setNavigator(this);
        setUp();
    }

    public void setUp() {
        toolbar = activityUploadBinding.toolbar;
        setSupportActionBar(toolbar);
    }




}
