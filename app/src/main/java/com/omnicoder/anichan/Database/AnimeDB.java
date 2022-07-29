package com.omnicoder.anichan.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Anime.class},version = 3,exportSchema = false)
public abstract class AnimeDB extends RoomDatabase {
    public abstract AnimeDao animeDao();
}
