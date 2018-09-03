package com.example.nour.backgrounds.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName ="recents",primaryKeys = {"ImageLink","CategoryId"})
public class Recents {
    @ColumnInfo(name = "ImageLink")
    @NonNull
    private String imageLink;

    @ColumnInfo(name = "CategoryId")
    @NonNull
    private String categoryId;

    @ColumnInfo(name = "saveTime")
    @NonNull
    private String saveTime;
    @ColumnInfo(name = "key")
    private String key;

    public Recents(@NonNull String imageLink, @NonNull String categoryId, @NonNull String saveTime, String key) {
        this.imageLink = imageLink;
        this.categoryId = categoryId;
        this.saveTime = saveTime;
        this.key = key;
    }

    @NonNull
    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(@NonNull String imageLink) {
        this.imageLink = imageLink;
    }

    @NonNull
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@NonNull String categoryId) {
        this.categoryId = categoryId;
    }

    @NonNull
    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(@NonNull String saveTime) {
        this.saveTime = saveTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
