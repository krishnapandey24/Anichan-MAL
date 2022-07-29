package com.omnicoder.anichan.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface AnimeDao {

    @Insert
    Completable insertAllAnime(List<Anime> animeList);

    @Query("SELECT * from ANIME WHERE status=:status")
    Flowable<List<Anime>> getAnimeList(String status);




    @Query("SELECT * from ANIME")
    Flowable<List<Anime>> getAllAnime();

    @Query("SELECT * from ANIME WHERE is_rewatching==1")
    Flowable<List<Anime>> getReWatching();

    @Query("DELETE FROM ANIME")
    Completable deleteAllAnime();

}
