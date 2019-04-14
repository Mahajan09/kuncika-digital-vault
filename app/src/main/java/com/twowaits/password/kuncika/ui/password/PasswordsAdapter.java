package com.twowaits.password.kuncika.ui.password;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.twowaits.password.kuncika.R;
import com.twowaits.password.kuncika.data.model.db.Passwords;
import com.twowaits.password.kuncika.databinding.ItemListBinding;
import com.twowaits.password.kuncika.databinding.ItemPasswordBinding;
import com.twowaits.password.kuncika.ui.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PasswordsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    List<Passwords> passwordsList;
    boolean showMenu = false;
    private static final int VIEW_TYPE_NORMAL = 1;
    Clicker clicker;

    public PasswordsAdapter(List<Passwords> passwordsList1) {
        this.passwordsList = passwordsList1;
    }

    public void setShowMenu(boolean showMenu1) {
        this.showMenu = showMenu1;
        notifyDataSetChanged();
    }

    public void updatePassword(int position, Passwords passwords) {

        passwordsList.get(position).setValue(passwords.getValue());
        passwordsList.get(position).setType(passwords.getType());
        notifyDataSetChanged();

    }

    public void setListener(Clicker clicker1) { this.clicker = clicker1; }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPasswordBinding itemPasswordBinding = ItemPasswordBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new PasswordsViewHolder(itemPasswordBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return passwordsList.size();
    }

    public void  clearItems() {
        passwordsList.clear();
    }

    public void addItems(List<Passwords> passwordsList1) {
        passwordsList = passwordsList1;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;

    }

    public class PasswordsViewHolder extends BaseViewHolder implements PasswordsItemViewModel.PasswordsItemClickListener {

        ItemPasswordBinding itemPasswordBinding;
        PasswordsItemViewModel passwordsItemViewModel;

        public PasswordsViewHolder(ItemPasswordBinding itemPasswordBinding1) {
            super(itemPasswordBinding1.getRoot());
            this.itemPasswordBinding = itemPasswordBinding1;
        }

        @Override
        public void onBind(int position) {
            Passwords passwords = passwordsList.get(position);
            passwordsItemViewModel = new PasswordsItemViewModel(passwords, this, showMenu);
            itemPasswordBinding.setViewModel(passwordsItemViewModel);
            itemPasswordBinding.executePendingBindings();
        }

        @Override
        public void showMenu() {
            PopupMenu popup = new PopupMenu(itemView.getContext(), itemView);
            MenuInflater inflater = popup.getMenuInflater();
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.delete:
                            clicker.deletePassword(passwordsList.get(getAdapterPosition()));
                            passwordsList.remove(getAdapterPosition());
                            notifyDataSetChanged();
                            return true;
                        case R.id.change_password:
                            clicker.changePassword(getAdapterPosition(), passwordsList.get(getAdapterPosition()).getId(), passwordsList.get(getAdapterPosition()).getName());
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
        public void layout() {
            clicker.selectedPassword(passwordsList.get(getAdapterPosition()));
        }
    }

    public interface Clicker {

        public void selectedPassword(Passwords passwords);

        public void deletePassword(Passwords passwords);

        public void changePassword(int position, int id, String name);

    }

}
