package com.twowaits.password.kuncika.ui.main;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.twowaits.password.kuncika.BR;
import com.twowaits.password.kuncika.R;
import com.twowaits.password.kuncika.ViewModelProviderFactory;
import com.twowaits.password.kuncika.data.local.prefs.PreferencesHelper;
import com.twowaits.password.kuncika.data.model.db.Apps;
import com.twowaits.password.kuncika.data.model.db.Passwords;
import com.twowaits.password.kuncika.databinding.ActivityMainBinding;
import com.twowaits.password.kuncika.databinding.NavHeaderMainBinding;
import com.twowaits.password.kuncika.ui.base.BaseActivity;
import com.twowaits.password.kuncika.ui.create.CreateActivity;
import com.twowaits.password.kuncika.ui.create.CreatePasswordDialog;
import com.twowaits.password.kuncika.ui.password.PasswordActivity;
import com.twowaits.password.kuncika.ui.service.AppService;
import com.twowaits.password.kuncika.ui.upload.UploadActivity;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.DispatchingAndroidInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, AppsAdapter.AppsAdapterListener {

    @Inject
    AppsAdapter appsAdapter;
    @Inject
    ViewModelProviderFactory factory;
    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    PreferencesHelper preferencesHelper;
    private ActivityMainBinding mActivityMainBinding;
    private DrawerLayout mDrawer;
    private MainViewModel mMainViewModel;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private FloatingActionButton fab;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        return mMainViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Drawable drawable = item.getIcon();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
        switch (item.getItemId()) {
            case R.id.action_cut:
                startActivity(new Intent(this, PasswordActivity.class));
                return true;
            case R.id.action_share:
                startActivity(new Intent(this, UploadActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void openLoginActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
        appsAdapter.setListener(this);
        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        appsAdapter.clearItems();
        mMainViewModel.loadFromDB();
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    private void lockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    private void setUp() {
        mMainViewModel.loadPackageOfApps();
        mDrawer = mActivityMainBinding.drawerView;
        mToolbar = mActivityMainBinding.toolbar;
        mNavigationView = mActivityMainBinding.navigationView;
        fab = mActivityMainBinding.plusButton;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateActivity();
            }
        });

        mActivityMainBinding.appsRecyclerView.setAdapter(appsAdapter);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mActivityMainBinding.appsRecyclerView.setLayoutManager(linearLayoutManager);

        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }
        };
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setupNavMenu();
        subscribeToLiveData();

        if (Build.VERSION.SDK_INT > 19) {

            boolean granted = false;
            AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), getPackageName());

            if (mode == AppOpsManager.MODE_DEFAULT) {
                granted = (checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
            } else {
                granted = (mode == AppOpsManager.MODE_ALLOWED);
            }

            if (!granted) {
                Toast.makeText(this, "Usage access needed for app to work!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            }

        }

    }


    private void setupNavMenu() {
        NavHeaderMainBinding navHeaderMainBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.nav_header_main, mActivityMainBinding.navigationView, false);
        mActivityMainBinding.navigationView.addHeaderView(navHeaderMainBinding.getRoot());
        navHeaderMainBinding.setViewModel(mMainViewModel);
        mActivityMainBinding.navigationView.setItemIconTintList(null);

        mNavigationView.setNavigationItemSelectedListener(item -> {
            mDrawer.closeDrawer(GravityCompat.START);
            switch (item.getItemId()) {
                case R.id.about:

                    return true;
                case R.id.rateUs:
                    final String appPackageName = getPackageName();
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                    return true;
                case R.id.passwords:
                    startActivity(new Intent(this, PasswordActivity.class));
                    return true;
                case R.id.faq:

                    return true;
                case R.id.share:
                    Intent share = new Intent(android.content.Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    share.putExtra(Intent.EXTRA_SUBJECT, "Share App!");
                    share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());
                    startActivity(Intent.createChooser(share, "Share link!"));
                    return true;
                default:
                    return false;
            }
        });

    }

    private void subscribeToLiveData() {
        Intent intent = new Intent(this, AppService.class);
        startService(intent);
        mMainViewModel.getAppsList().observe(this, questionCardDatas -> appsAdapter.addItems(questionCardDatas));
    }

    private void unlockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }


    @Override
    public void setupCustomDialog(Apps apps, int position) {

        mMainViewModel.loadPasswordsFromDb();
        CreatePasswordDialog createPasswordDialog = new CreatePasswordDialog(this, new CreatePasswordDialog.Clicker() {
            @Override
            public void onClick(Passwords passwords) {
                appsAdapter.updateChangedApp(position, passwords);
                mMainViewModel.updatePassword(apps, passwords);
            }
        }, false, "");
        mMainViewModel.getPasswordsList().observe(this, passwordsList -> { createPasswordDialog.updateList(passwordsList); });
        createPasswordDialog.show();
    }

    @Override
    public void onRetryClick(Apps apps) {
        mMainViewModel.deleteApps(apps);
    }

    @Override
    public void changeLock(String name, String status) {
        mMainViewModel.changeLock(name, status);
    }

    @Override
    public void updateApps(List<Apps> appsResponseList) {
        appsAdapter.addItems(appsResponseList);
    }

    @Override
    public void openCreateActivity() {
        startActivity(CreateActivity.newIntent(this));
    }

}
