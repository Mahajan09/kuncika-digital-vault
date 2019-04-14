package com.twowaits.password.kuncika.data.local.db;


import com.twowaits.password.kuncika.data.local.db.dao.AppsDao;
import com.twowaits.password.kuncika.data.local.db.dao.PasswordsDao;
import com.twowaits.password.kuncika.data.model.db.Apps;
import com.twowaits.password.kuncika.data.model.db.Passwords;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Apps.class, Passwords.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppsDao appsDao();
    public abstract PasswordsDao passwordsDao();
}
