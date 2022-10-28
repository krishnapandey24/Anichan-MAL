package com.omnicoder.anichan.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MangaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllManga(List<UserManga> userMangaList);

    @Query("SELECT * from MANGA WHERE status=:status ORDER BY t_id DESC")
    Flowable<List<UserManga>> getMangaListByID(String status);

    @Query("SELECT * from MANGA WHERE status=:status ORDER BY mean DESC")
    Flowable<List<UserManga>> getMangaListByMean(String status);

    @Query("SELECT * from MANGA WHERE status=:status ORDER BY title DESC")
    Flowable<List<UserManga>> getMangaListByTitle(String status);

    @Query("SELECT * from MANGA WHERE title like :query || '%' ")
    Flowable<List<UserManga>> searchManga(String query);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertOrUpdateManga(UserManga userManga);

    @Query("SELECT * from  MANGA ORDER BY :sortBy")
    Flowable<List<UserManga>> getAllManga(String sortBy);

    @Query("SELECT * from manga WHERE is_rereading==1 ORDER BY t_id DESC")
    Flowable<List<UserManga>> getReReadingById();

    @Query("SELECT * from manga WHERE is_rereading==1 ORDER BY title ASC")
    Flowable<List<UserManga>> getReReadingByTitle();

    @Query("SELECT * from manga WHERE is_rereading==1 ORDER BY mean DESC")
    Flowable<List<UserManga>> getReReadingByMean();


    @Query("UPDATE MANGA SET noOfChaptersRead=:noOfChapters WHERE id=:id")
    Completable addChapters(int id, int noOfChapters);

    @Query("UPDATE MANGA SET status=:status WHERE id=:id")
    Completable mangaCompleted(int id,String status);

    @Query("DELETE FROM MANGA WHERE id=:id")
    Completable deleteMangaFromList(int id);

    @Query("DELETE FROM MANGA")
    Completable deleteAllManga();

}
