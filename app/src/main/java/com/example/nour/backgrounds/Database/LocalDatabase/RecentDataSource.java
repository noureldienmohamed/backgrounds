package com.example.nour.backgrounds.Database.LocalDatabase;

import com.example.nour.backgrounds.Database.DataSource.IRecentsDataSource;
import com.example.nour.backgrounds.Database.Recents;

import java.util.List;

import io.reactivex.Flowable;

public class RecentDataSource implements IRecentsDataSource {

    private RecentsDa recentsDa;
    private static RecentDataSource instance;

    public RecentDataSource(RecentsDa recentsDa) {
        this.recentsDa = recentsDa;
    }
public static RecentDataSource getInstance(RecentsDa recentsDa)
{
    if (instance==null)
        instance = new RecentDataSource(recentsDa);
    return instance;

}
    @Override
    public Flowable<List<Recents>> getAllRecents() {
        return recentsDa.getAllRecents();
    }

    @Override
    public void interRecents(Recents... recents) {
       recentsDa.interRecents(recents);
    }

    @Override
    public void UpdateRecents(Recents... recents) {
       recentsDa.UpdateRecents(recents);
    }

    @Override
    public void deleteRecents(Recents... recents) {
        recentsDa.deleteRecents(recents);
    }

    @Override
    public void deleteAllRecents() {
    recentsDa.deleteAllRecents();
    }

}
