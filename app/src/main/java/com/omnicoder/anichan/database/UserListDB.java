package com.omnicoder.anichan.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserAnime.class, UserManga.class},version = 6,exportSchema = false)
public abstract class UserListDB extends RoomDatabase {
    public abstract AnimeDao animeDao();
    public abstract MangaDao mangaDao();
}
