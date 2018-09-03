package com.example.nour.backgrounds.Database.DataSource;

import com.example.nour.backgrounds.Database.Recents;

import java.util.List;

import io.reactivex.Flowable;

public interface IRecentsDataSource {
    Flowable<List<Recents>>getAllRecents();
    void interRecents(Recents...recents);
    void UpdateRecents(Recents...recents);
    void deleteRecents(Recents...recents);
    void deleteAllRecents();
}
