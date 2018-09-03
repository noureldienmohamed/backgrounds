package com.example.nour.backgrounds.Database.DataSource;

import com.example.nour.backgrounds.Database.Recents;

import java.util.List;

import io.reactivex.Flowable;

public class RecentRepository implements IRecentsDataSource {
    private IRecentsDataSource mLocalDataSource;
    private static RecentRepository intance;

    public RecentRepository(IRecentsDataSource localDataSource) {
        mLocalDataSource = localDataSource;
    }

    public static RecentRepository getIntance(IRecentsDataSource localDataSource) {
    if (intance==null)
        intance=new RecentRepository(localDataSource);
    return intance;

    }
    @Override
    public Flowable<List<Recents>> getAllRecents() {
        return mLocalDataSource.getAllRecents();
    }

    @Override
    public void interRecents(Recents... recents) {
        mLocalDataSource.interRecents(recents);
    }

    @Override
    public void UpdateRecents(Recents... recents) {
        mLocalDataSource.UpdateRecents(recents);
    }

    @Override
    public void deleteRecents(Recents... recents) {
        mLocalDataSource.deleteRecents(recents);
    }

    @Override
    public void deleteAllRecents() {
       mLocalDataSource.deleteAllRecents();


    }


    }

