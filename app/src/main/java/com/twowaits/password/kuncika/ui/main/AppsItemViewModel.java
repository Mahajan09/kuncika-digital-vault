package com.twowaits.password.kuncika.ui.main;

import com.twowaits.password.kuncika.data.model.db.Apps;

import androidx.databinding.ObservableField;

public class AppsItemViewModel {

    public final ObservableField<String> title, icon, type;

    public final AppsItemViewModelListener mListener;

    public ObservableField<Boolean> status;

    private final Apps appsResponse;

    public AppsItemViewModel(Apps appsResponse1, AppsItemViewModelListener listener) {
        this.appsResponse = appsResponse1;
        this.mListener = listener;
        title = new ObservableField<>(appsResponse.getName());
        icon = new ObservableField<>(appsResponse.getIcon());
        type = new ObservableField<>(appsResponse.getType());
        if (appsResponse.getStatus().equals("active"))
            status = new ObservableField<>(true);
            else
        status = new ObservableField<>(false);
    }

    public void openApp() {
        mListener.openApp(appsResponse);
    }

    public void enableLock() {
        if (status.get()) {
            status.set(false);
            mListener.enableLock(false);
        } else {
            status.set(true);
            mListener.enableLock(true);
        }
    }

    public void showPopUp() {
        mListener.showPopUpMenu();
    }

    public interface AppsItemViewModelListener {

        void enableLock(Boolean blogUrl);

        void showPopUpMenu();

        void openApp(Apps apps);
    }

}
