package com.omnicoder.anichan.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.omnicoder.anichan.models.UserInfo;

@Database(entities = {UserAnime.class, UserManga.class, UserInfo.class},version = 10,exportSchema = false)
public abstract class UserListDB extends RoomDatabase {
    public abstract AnimeDao animeDao();
    public abstract MangaDao mangaDao();
    public abstract UserInfoDao userInfoDao();

}
