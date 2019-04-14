package com.twowaits.password.kuncika.ui.password;

import com.twowaits.password.kuncika.data.model.db.Passwords;

import androidx.databinding.ObservableField;

public class PasswordsItemViewModel {

    PasswordsItemClickListener passwordsItemClickListener;
    Passwords passwords;
    public boolean showMenu;
    public ObservableField<String> values, type, status, name;

    public PasswordsItemViewModel(Passwords passwords1, PasswordsItemClickListener passwordsItemClickListener1, boolean showMenu1) {
        this.passwordsItemClickListener = passwordsItemClickListener1;
        this.passwords = passwords1;
        name = new ObservableField<>(passwords.getName());
        values = new ObservableField<>(passwords.getValue());
        type = new ObservableField<>(passwords.getType());
        status = new ObservableField<>(passwords.getStatus());
        this.showMenu = showMenu1;
    }

    public void layout() {
        passwordsItemClickListener.layout();
    }

    public void onClick() {
        passwordsItemClickListener.showMenu();
    }

    public interface PasswordsItemClickListener {
        public void showMenu();
        public void layout();
    }


}
