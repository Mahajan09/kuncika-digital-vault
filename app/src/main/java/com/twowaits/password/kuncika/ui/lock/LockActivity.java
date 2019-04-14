package com.twowaits.password.kuncika.ui.lock;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.rxpatternlockview.RxPatternLockView;
import com.andrognito.rxpatternlockview.events.PatternLockCompoundEvent;
import com.twowaits.password.kuncika.BR;
import com.twowaits.password.kuncika.R;
import com.twowaits.password.kuncika.ViewModelProviderFactory;
import com.twowaits.password.kuncika.data.model.db.Apps;
import com.twowaits.password.kuncika.databinding.ActivityLockBinding;
import com.twowaits.password.kuncika.ui.base.BaseActivity;
import com.twowaits.password.kuncika.ui.main.MainViewModel;
import com.twowaits.password.kuncika.ui.password.PasswordActivity;
import com.twowaits.password.kuncika.ui.upload.UploadActivity;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.functions.Consumer;

public class LockActivity extends BaseActivity<ActivityLockBinding, LockViewModel> implements LockNavigator {

    @Inject
    ViewModelProviderFactory factory;
    LockViewModel lockViewModel;
    ActivityLockBinding activityLockBinding;
    PatternLockView patternLockView;
    LinearLayout patternContainer, passcodeContainer, passwordContainer;
    EditText password;
    TextView submit;
    String patternValue = "";
    Apps apps1;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_lock;
    }

    @Override
    public LockViewModel getViewModel() {
        lockViewModel = ViewModelProviders.of(this, factory).get(LockViewModel.class);
        return lockViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lockViewModel.setNavigator(this);
        activityLockBinding = getViewDataBinding();
        setUp();
        subscribeToLiveData();
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
            case R.id.menu:
                //start popup
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUp() {
        patternLockView = activityLockBinding.patternLockView;
        passcodeContainer = activityLockBinding.passcodeContainer;
        patternContainer = activityLockBinding.patternContainer;
        passwordContainer = activityLockBinding.passwordContainer;
        password = activityLockBinding.password;
        submit = activityLockBinding.submit;

        lockViewModel.loadAppData(getIntent().getStringExtra("packageName"));

        RxPatternLockView.patternChanges(patternLockView)
                .subscribe(new Consumer<PatternLockCompoundEvent>() {
                    @Override
                    public void accept(PatternLockCompoundEvent event) throws Exception {
                        if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_COMPLETE) {
                            patternValue = PatternLockUtils.patternToString(patternLockView, event.getPattern());
                            if (apps1.getPassword().equals(patternValue)) {
                                showToast("Correct!");
                                finish();
                            } else {
                                showToast("Invalid, Try Again");
                                patternLockView.clearPattern();
                            }
                        } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_CLEARED) {
                            patternValue = "";
                        }
                    }
                });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().length() == 4) {
                    if (apps1.getPassword().equals(password.getText().toString())) {
                        showToast("Correct!");
                        finish();
                    } else {
                        showToast("Invalid, Try Again");
                    }
                } else {
                    showToast("Enter 4 digits!");
                }
            }
        });


    }

    public void subscribeToLiveData() {
        lockViewModel.getApps().observe(this, apps -> {
            apps1 = apps;
            passwordContainer.setVisibility(View.VISIBLE);
            if (apps.getType().equals("pattern")) {
                passcodeContainer.setVisibility(View.GONE);
                patternContainer.setVisibility(View.VISIBLE);
            } else {
                patternContainer.setVisibility(View.GONE);
                passcodeContainer.setVisibility(View.VISIBLE);
            }
        });
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
        startHomescreen.addCategory(Intent.CATEGORY_HOME);
        startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startHomescreen);
    }
}
