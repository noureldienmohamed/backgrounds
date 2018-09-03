package com.example.nour.backgrounds.Database.LocalDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.nour.backgrounds.Database.Recents;
import com.example.nour.backgrounds.Listbackground;

import static com.example.nour.backgrounds.Database.LocalDatabase.LocalDatabase.DATABASE_VERSION;

@Database(entities = Recents.class,version = DATABASE_VERSION,exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NUME="Wallpaper";
    public abstract RecentsDa recentsDa();
    private static LocalDatabase instance;
    public static LocalDatabase getInstance(Context context)
    {
        if (instance == null)
        {
            instance= Room.databaseBuilder(context,LocalDatabase.class,DATABASE_NUME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
