package com.twowaits.password.kuncika.ui.create;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.twowaits.password.kuncika.App;
import com.twowaits.password.kuncika.data.DataManager;
import com.twowaits.password.kuncika.data.model.db.Apps;
import com.twowaits.password.kuncika.databinding.ItemCreateAppBinding;
import com.twowaits.password.kuncika.ui.base.BaseViewHolder;
import com.twowaits.password.kuncika.ui.main.AppsAdapter;
import com.twowaits.password.kuncika.util.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CreateAppAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    List<Apps> appsList;

    Click click;


    public CreateAppAdapter(List<Apps> appsList1) {
        this.appsList = appsList1;
    }

    public void setListener(Click listener) {
        this.click = listener;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCreateAppBinding itemCreateAppBinding = ItemCreateAppBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CreateAppViewHolder(itemCreateAppBinding);
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }

    public void addItems(List<Apps> appsResponseList1) {
        appsList.addAll(appsResponseList1);
        notifyDataSetChanged();
    }

    public void removePosition(int position) {
        appsList.remove(position);
        notifyDataSetChanged();
    }

    public void clearItems() {
        appsList.clear();
    }

    public class CreateAppViewHolder extends BaseViewHolder implements CreateAppItemViewModel.CreateAppItemViewModelListener {

        ItemCreateAppBinding itemCreateAppBinding;
        CreateAppItemViewModel createAppItemViewModel;

        public CreateAppViewHolder(ItemCreateAppBinding itemCreateAppBinding) {
            super(itemCreateAppBinding.getRoot());
            this.itemCreateAppBinding = itemCreateAppBinding;
        }

        @Override
        public void onBind(int position) {
            final Apps apps = appsList.get(position);
            createAppItemViewModel = new CreateAppItemViewModel(apps, this);
            itemCreateAppBinding.setViewModel(createAppItemViewModel);
            itemCreateAppBinding.executePendingBindings();
        }

        @Override
        public void onClick() {
            click.onClick(appsList.get(getAdapterPosition()), getAdapterPosition());
        }
    }

    public interface Click {
        public void onClick(Apps apps, int position);
    }

}
