package com.twowaits.password.kuncika.ui.main;

import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.twowaits.password.kuncika.App;
import com.twowaits.password.kuncika.R;
import com.twowaits.password.kuncika.data.model.api.Response;
import com.twowaits.password.kuncika.data.model.db.Apps;
import com.twowaits.password.kuncika.data.model.db.Passwords;
import com.twowaits.password.kuncika.databinding.ItemListBinding;
import com.twowaits.password.kuncika.ui.base.BaseViewHolder;
import com.twowaits.password.kuncika.ui.create.CreatePasswordDialog;

import java.io.File;
import java.util.List;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

public class AppsAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    public static final int VIEW_TYPE_NORMAL = 1;

    private List<Apps> appsResponseList;

    private AppsAdapterListener mListener;

    public AppsAdapter(List<Apps> appsResponseList) {
        this.appsResponseList = appsResponseList;
    }

    @Override
    public int getItemCount() {
            return appsResponseList.size();
    }

    @Override
    public int getItemViewType(int position) {
            return VIEW_TYPE_NORMAL;

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ItemListBinding blogViewBinding = ItemListBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new AppsViewHolder(blogViewBinding);
    }

    public void addItems(List<Apps> appsResponseList1) {
        appsResponseList.addAll(appsResponseList1);
        notifyDataSetChanged();
    }

    public void updateChangedApp(int position, Passwords passwords) {
        appsResponseList.get(position).setType(passwords.getType());
        appsResponseList.get(position).setPassword(passwords.getValue());
        notifyDataSetChanged();
    }

    public void clearItems() {
        appsResponseList.clear();
    }

    public void setListener(AppsAdapterListener listener) {
        this.mListener = listener;
    }

    public interface AppsAdapterListener {

        void onRetryClick(Apps apps);

        void changeLock(String name, String status);

        void setupCustomDialog(Apps apps, int position);

    }

    public class AppsViewHolder extends BaseViewHolder implements AppsItemViewModel.AppsItemViewModelListener {

        private ItemListBinding mBinding;

        private AppsItemViewModel appsItemViewModel;

        public AppsViewHolder(ItemListBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final Apps appsResponse = appsResponseList.get(position);
            appsItemViewModel = new AppsItemViewModel(appsResponse, this);
            mBinding.setViewModel(appsItemViewModel);
            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();
        }

        @Override
        public void showPopUpMenu() {

                PopupMenu popup = new PopupMenu(itemView.getContext(), itemView);
                MenuInflater inflater = popup.getMenuInflater();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.delete:
                                mListener.onRetryClick(appsResponseList.get(getAdapterPosition()));
                                appsResponseList.remove(getAdapterPosition());
                                notifyDataSetChanged();
                                return true;
                            case R.id.change_password:
                                mListener.setupCustomDialog(appsResponseList.get(getAdapterPosition()), getAdapterPosition());
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                inflater.inflate(R.menu.activity_main_drawer, popup.getMenu());
                popup.show();
        }

        @Override
        public void enableLock(Boolean chk) {
            if (chk) {
                Toast.makeText(itemView.getContext(), "Enabled", Toast.LENGTH_SHORT).show();
                mListener.changeLock(appsResponseList.get(getAdapterPosition()).getName(), "active");
            } else {
                mListener.changeLock(appsResponseList.get(getAdapterPosition()).getName(), "inactive");
                Toast.makeText(itemView.getContext(), "Disabled", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void openApp(Apps apps) {
            Intent launchIntent = itemView.getContext().getPackageManager().getLaunchIntentForPackage(apps.getIcon());
            itemView.getContext().startActivity(launchIntent);
        }
    }

}
