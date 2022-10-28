package com.omnicoder.anichan.database;

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

    @Query("SELECT * from ANIME WHERE status=:status ORDER BY t_id DESC")
    Flowable<List<UserAnime>> getAnimeListByID(String status);

    @Query("SELECT * from ANIME WHERE status=:status ORDER BY mean DESC")
    Flowable<List<UserAnime>> getAnimeListByMean(String status);

    @Query("SELECT * from ANIME WHERE status=:status ORDER BY title DESC")
    Flowable<List<UserAnime>> getAnimeListByTitle(String status);

    @Query("SELECT * from ANIME WHERE title like :query || '%' ")
    Flowable<List<UserAnime>> searchAnime(String query);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertOrUpdateAnime(UserAnime userAnime);

    @Query("SELECT * from  ANIME ORDER BY :sortBy")
    Flowable<List<UserAnime>> getAllAnime(String sortBy);

    @Query("SELECT * from anime WHERE is_rewatching==1 ORDER BY t_id DESC")
    Flowable<List<UserAnime>> getReWatchingById();

    @Query("SELECT * from anime WHERE is_rewatching==1 ORDER BY title ASC")
    Flowable<List<UserAnime>> getReWatchingByTitle();

    @Query("SELECT * from anime WHERE is_rewatching==1 ORDER BY mean DESC")
    Flowable<List<UserAnime>> getReWatchingByMean();

    @Query("UPDATE ANIME SET num_episodes_watched=:noOfEpisodesWatched WHERE id=:id")
    Completable addEpisode(int id,int noOfEpisodesWatched);

    @Query("UPDATE ANIME SET status=:status WHERE id=:id")
    Completable animeCompleted(int id,String status);

    @Query("DELETE FROM ANIME WHERE id=:id")
    Completable deleteAnimeFromList(int id);

    @Query("DELETE FROM ANIME")
    Completable deleteAllAnime();



}
