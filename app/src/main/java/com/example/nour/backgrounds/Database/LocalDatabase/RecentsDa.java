package com.example.nour.backgrounds.Database.LocalDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.nour.backgrounds.Database.Recents;

import java.util.List;

import io.reactivex.Flowable;
@Dao
public interface RecentsDa {
    @Query("SELECT * FROM recents ORDER BY saveTime DESC LIMIT 10")
    Flowable<List<Recents>> getAllRecents();
    @Insert
    void interRecents(Recents...recents);
    @Update
    void UpdateRecents(Recents...recents);
    @Delete
    void  deleteRecents(Recents...recents);
    @Query("DELETE FROM recents")
    void deleteAllRecents();
}
