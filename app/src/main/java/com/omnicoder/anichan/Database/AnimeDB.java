package com.omnicoder.anichan.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Anime.class,AnimeList.class},version = 2,exportSchema = false)
public abstract class AnimeDB extends RoomDatabase {
    public abstract AnimeDao animeDao();
    public abstract AnimeListDao animeListDao();

}
