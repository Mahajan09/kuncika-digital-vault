package com.twowaits.password.kuncika.data.local.db.dao;

import com.twowaits.password.kuncika.data.model.db.Apps;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface AppsDao {

    @Delete
    void delete(Apps apps);

    @Query("SELECT * FROM apps WHERE name LIKE :name LIMIT 1")
    Apps findByName(String name);

    @Query("SELECT * FROM apps ORDER BY id DESC LIMIT 1")
    Apps loadLast();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Apps apps);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Apps> apps);

    @Query("SELECT * FROM apps")
    List<Apps> loadAll();

    @Query("SELECT * FROM apps WHERE id IN (:userIds)")
    List<Apps> loadAllByIds(List<Integer> userIds);

    @Query("DELETE FROM apps")
    void deleteAll();

    @Query("UPDATE apps SET status = :status WHERE name = :name")
    void changeStatus(String name, String status);

    @Query("UPDATE apps SET password = :password, type = :type WHERE id = :id")
    void updatePassword(String password, String type, int id);

    @Query("SELECT * FROM apps WHERE icon = :packageName")
    Apps loadByPackageName(String packageName);

    @Query("SELECT * FROM apps WHERE status = 'active'")
    List<Apps> loadAllActiveApps();

}
