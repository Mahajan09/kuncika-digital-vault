package com.twowaits.password.kuncika.ui.create;

import com.twowaits.password.kuncika.data.model.db.Apps;

import androidx.databinding.ObservableField;

public class CreateAppItemViewModel {

    public ObservableField<String> title;
    public ObservableField<String> icon;
    Apps apps;
    CreateAppItemViewModelListener createAppItemViewModelListener;

    public CreateAppItemViewModel(Apps apps1, CreateAppItemViewModelListener createAppItemViewModelListener1) {
        this.apps = apps1;
        this.createAppItemViewModelListener = createAppItemViewModelListener1;
        title = new ObservableField<>(apps.getName());
        icon = new ObservableField<>(apps.getIcon());
    }

    public void onClick() {
        createAppItemViewModelListener.onClick();
    }


    public interface CreateAppItemViewModelListener {

        public void onClick();

    }

}
