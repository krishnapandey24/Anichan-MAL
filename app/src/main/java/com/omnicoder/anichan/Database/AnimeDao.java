package com.omnicoder.anichan.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllAnime(List<UserAnime> userAnimeList);

    @Query("SELECT * from ANIME WHERE status=:status")
    Flowable<List<UserAnime>> getAnimeList(String status);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAnime(UserAnime userAnime);



    @Query("SELECT * from ANIME")
    Flowable<List<UserAnime>> getAllAnime();

    @Query("SELECT * from anime WHERE is_rewatching==1")
    Flowable<List<UserAnime>> getReWatching();

    @Query("DELETE FROM ANIME")
    Completable deleteAllAnime();

}
