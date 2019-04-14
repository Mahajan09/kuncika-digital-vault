package com.twowaits.password.kuncika.data.local.db.dao;

import com.twowaits.password.kuncika.data.model.db.Passwords;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PasswordsDao {

    @Delete
    void delete(Passwords passwords);

    @Insert
    void insert(Passwords passwords);

    @Query("SELECT * FROM passwords")
    List<Passwords> loadAll();

    @Query("UPDATE passwords SET value = :value, type = :type WHERE id = :id")
    void updatePassword(String value, String type, int id);

}
