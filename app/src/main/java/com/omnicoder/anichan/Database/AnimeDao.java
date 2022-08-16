package com.omnicoder.anichan.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.DELETE;

@Dao
public interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllAnime(List<UserAnime> userAnimeList);

    @Query("SELECT * from ANIME WHERE status=:status")
    Flowable<List<UserAnime>> getAnimeList(String status);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertOrUpdateAnime(UserAnime userAnime);

    @Query("SELECT * from ANIME")
    Flowable<List<UserAnime>> getAllAnime();

    @Query("SELECT * from anime WHERE is_rewatching==1")
    Flowable<List<UserAnime>> getReWatching();

    @Query("UPDATE ANIME SET num_episodes_watched=:noOfEpisodesWatched WHERE id=:id")
    Completable addEpisode(int id,int noOfEpisodesWatched);

    @Query("UPDATE ANIME SET status=:status WHERE id=:id")
    Completable animeCompleted(int id,String status);

    @Query("DELETE FROM ANIME WHERE id=:id")
    Completable deleteAnimeFromList(int id);

    @Query("DELETE FROM ANIME")
    Completable deleteAllAnime();

}
