package com.twowaits.password.kuncika.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.twowaits.password.kuncika.R;
import com.twowaits.password.kuncika.data.model.db.Apps;
import com.twowaits.password.kuncika.data.model.db.Passwords;
import com.twowaits.password.kuncika.ui.create.CreateAppAdapter;
import com.twowaits.password.kuncika.ui.main.AppsAdapter;
import com.twowaits.password.kuncika.ui.password.PasswordsAdapter;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class BindingUtils {
    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).into(imageView);
    }

    @BindingAdapter("iconUrl")
    public static void setIconUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Drawable drawable;
        try {
            drawable = context.getPackageManager().getApplicationIcon(url);
        }
        catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            drawable = ContextCompat.getDrawable(context, R.drawable.key_full);
        }
        imageView.setImageDrawable(drawable);
    }

    @BindingAdapter({"adapter"})
    public static void addBlogItems(RecyclerView recyclerView, List<Apps> appsList) {
        AppsAdapter adapter = (AppsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(appsList);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addPasswordItems(RecyclerView recyclerView, List<Passwords> passwordsList) {
        PasswordsAdapter adapter = (PasswordsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(passwordsList);
        }
    }

    @BindingAdapter({"adapterNew"})
    public static void addAllApps(RecyclerView recyclerView, List<Apps> appsList) {
        CreateAppAdapter adapter = (CreateAppAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            Context context = recyclerView.getContext();
            final PackageManager pm = context.getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            adapter.clearItems();
            Log.d("TAGAG", "asd - util");
            for (ApplicationInfo packageInfo : packages) {
                Apps apps = new Apps(packageInfo.name, packageInfo.packageName);
                appsList.add(apps);
                Log.d("TAGAGA", packageInfo.name);
            }
            adapter.addItems(appsList);
        }
    }

}
