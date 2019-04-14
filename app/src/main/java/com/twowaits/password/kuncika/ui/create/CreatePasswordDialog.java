package com.twowaits.password.kuncika.ui.create;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.rxpatternlockview.RxPatternLockView;
import com.andrognito.rxpatternlockview.events.PatternLockCompoundEvent;
import com.twowaits.password.kuncika.R;
import com.twowaits.password.kuncika.ViewModelProviderFactory;
import com.twowaits.password.kuncika.data.model.db.Passwords;
import com.twowaits.password.kuncika.databinding.CustomCreateDialogBinding;
import com.twowaits.password.kuncika.ui.password.PasswordsAdapter;
import com.twowaits.password.kuncika.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.functions.Consumer;

public class CreatePasswordDialog extends Dialog implements PasswordsAdapter.Clicker {

    PasswordsAdapter passwordsAdapter;
    Context context;
    Clicker clicker;
    CheckBox checkBox1, checkBox2;
    LinearLayout passcodeContainer, patternContainer, passwordContainer, showContainer;
    PatternLockView patternLockView;
    TextView clearPattern, submitPattern, submit;
    Boolean type;
    Passwords passwords;
    EditText name, password;
    String patternValue = "", nameText;
    List<Passwords> passwordsList;
    RecyclerView recyclerView;

    @Inject
    ViewModelProviderFactory factory;
    CustomCreateDialogBinding customCreateDialogBinding;

    @Override
    public void selectedPassword(Passwords passwords) {
        clicker.onClick(passwords);
        dismiss();
    }

    public CreatePasswordDialog(Context context, Clicker clicker, Boolean type, String name) {
        super(context);
        this.context = context;
        this.clicker = clicker;
        this.type = type;
        this.nameText = name;
        passwords = new Passwords();
        passwordsList = new ArrayList<>();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.custom_create_dialog);

        submit = findViewById(R.id.submit);
        password = findViewById(R.id.password);
        checkBox1 = findViewById(R.id.select_passcode);
        checkBox2 = findViewById(R.id.select_pattern);
        passcodeContainer = findViewById(R.id.passcode_container);
        patternContainer = findViewById(R.id.pattern_container);
        patternLockView = findViewById(R.id.pattern_lock_view);
        clearPattern = findViewById(R.id.clear_pattern);
        submitPattern = findViewById(R.id.submit_pattern);
        name = findViewById(R.id.name);

        passwordContainer = findViewById(R.id.password_container);
        showContainer = findViewById(R.id.show_container);

        if (type) {
            showContainer.setVisibility(View.GONE);
            passwordContainer.setVisibility(View.VISIBLE);
        } else {
            passwordContainer.setVisibility(View.GONE);
            showContainer.setVisibility(View.VISIBLE);

            recyclerView = findViewById(R.id.appsRecyclerView1);

            passwordsAdapter = new PasswordsAdapter(passwordsList);
            passwordsAdapter.setListener(this);
            recyclerView.setAdapter(passwordsAdapter);
            passwordsAdapter.setShowMenu(type);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        }

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) passcodeContainer.setVisibility(View.VISIBLE);
                else passcodeContainer.setVisibility(View.GONE);

                patternContainer.setVisibility(View.GONE);
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) patternContainer.setVisibility(View.VISIBLE);
                else patternContainer.setVisibility(View.GONE);

                passcodeContainer.setVisibility(View.GONE);
            }
        });

        if (!nameText.equals(""))
            name.setText(nameText);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().equals("")) {
                    if (password.getText().toString().equals("") || password.getText().toString().length() < 4) {
                        Toast.makeText(context, "Invalid password!", Toast.LENGTH_SHORT).show();
                    } else {
                        passwords.setName(name.getText().toString());
                        passwords.setType("passcode");
                        passwords.setValue(password.getText().toString());
                        passwords.setStatus("active");
                        passwords.setCreatedAt(CommonUtils.getTimestamp());

                        clicker.onClick(passwords);
                        Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                } else {
                    Toast.makeText(context, "Add a name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patternLockView.clearPattern();
                submitPattern.setVisibility(View.GONE);
                clearPattern.setVisibility(View.GONE);
            }
        });

        submitPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().equals("")) {
                    if (patternValue.equals("")) {
                        Toast.makeText(context, "Select a pattern!", Toast.LENGTH_SHORT).show();
                    } else {
                        passwords.setName(name.getText().toString());
                        passwords.setType("pattern");
                        passwords.setValue(patternValue);
                        passwords.setStatus("active");
                        passwords.setCreatedAt(CommonUtils.getTimestamp());

                        clicker.onClick(passwords);
                        Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                } else {
                    Toast.makeText(context, "Add a name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RxPatternLockView.patternChanges(patternLockView)
                         .subscribe(new Consumer<PatternLockCompoundEvent>() {
                             @Override
                             public void accept(PatternLockCompoundEvent event) throws Exception {
                                 if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_STARTED) {

                                 } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_PROGRESS) {

                                 } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_COMPLETE) {
                                     submitPattern.setVisibility(View.VISIBLE);
                                     clearPattern.setVisibility(View.VISIBLE);
                                     patternValue = PatternLockUtils.patternToString(patternLockView, event.getPattern());
                                 } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_CLEARED) {
                                     patternValue = "";
                                 }
                             }
                         });


    }

    public interface Clicker {

        public void onClick(Passwords passwords);

    }

    public void updateList(List<Passwords> passwordsList1) {

        passwordsAdapter.addItems(passwordsList1);

    }

    @Override
    public void deletePassword(Passwords passwords) {

    }

    @Override
    public void changePassword(int position, int id, String name) {

    }
}
