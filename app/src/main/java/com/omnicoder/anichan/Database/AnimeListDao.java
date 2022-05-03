package com.omnicoder.anichan.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface AnimeListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addAnime(AnimeList animeList);

    @Query("UPDATE ANIME_LIST SET watchedEpisodes=watchedEpisodes+1 WHERE animeID==:animeID")
    Completable addEpisode(int animeID);

    @Query("UPDATE ANIME_LIST SET status=:status WHERE animeID==:animeID")
    Completable updateStatus(String status,int animeID);

    @Query("UPDATE ANIME_LIST SET CompletedDate=:date WHERE animeID=:animeID")
    Completable setCompletedDate(String date,int animeID);

    @Query("DELETE FROM ANIME_LIST WHERE animeID=:animeID")
    Completable deleteAnime(int animeID);

    @Query("SELECT * from ANIME_LIST WHERE title like :query || '%' ")
    Flowable<List<AnimeList>> searchAnime(String query);

    @Query("SELECT * from ANIME_LIST WHERE status=:status")
    Flowable<List<AnimeList>> getAnimeList(String status);

    @Query("SELECT * from ANIME_LIST WHERE status='Watching'")
    Flowable<List<AnimeList>> getWatching();

    @Query("SELECT * from ANIME_LIST WHERE status=:status")
    Flowable<List<AnimeList>> fetchAnimeList(String status);

    @Query("SELECT * from ANIME_LIST WHERE status=:status ORDER BY "+":sortBy"+" DESC")
    Flowable<List<AnimeList>> fetchAnimeList(String status,String sortBy);

    @Query("SELECT * from ANIME_LIST WHERE status='Plan To Watch'")
    Flowable<List<AnimeList>> getPlanToWatch();

    @Query("SELECT * from ANIME_LIST WHERE status='Completed'")
    Flowable<List<AnimeList>> getCompleted();

    @Query("SELECT * from ANIME_LIST WHERE status='On Hold'")
    Flowable<List<AnimeList>> getOnHold();

    @Query("SELECT * from ANIME_LIST WHERE status='Dropped'")
    Flowable<List<AnimeList>> getDropped();

    @Query("SELECT * from ANIME_LIST WHERE status='Rewatching'")
    Flowable<List<AnimeList>> getReWatching();

    @Query("SELECT * from ANIME_LIST")
    Flowable<List<AnimeList>> getAll();



//    animeID,title,posterPath,format,mediaType,givenScore,totalEpisode,watchedEpisodes,seasonNo,status






}
