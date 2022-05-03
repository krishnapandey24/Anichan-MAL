package com.omnicoder.anichan.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.omnicoder.anichan.Models.ExplorePlainView;
import com.omnicoder.anichan.Models.ExploreView;
import com.omnicoder.anichan.Models.TrendingAnime;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface AnimeDao {

    @Insert
    Completable insertAllAnime(List<Anime> animeList);

    @Query("SELECT "+Anime.ANIME_ID+","+Anime.TITLE+","+Anime.IMAGE_URL+","+Anime.FORMAT+","+Anime.MEDIA_TYPE+" FROM ANIME ORDER BY TRENDING DESC")
    Single<List<ExploreView>> getTrendingAnime();

    @Query("SELECT "+Anime.ANIME_ID+","+Anime.TITLE+","+Anime.IMAGE_URL+","+Anime.FORMAT+","+Anime.MEDIA_TYPE+","+Anime.GENRES+","+Anime.MOVIE_DB_RATING+" FROM ANIME ORDER BY TRENDING DESC")
    Single<List<ExplorePlainView>> getTrendingAnimePlain();

    @Query("SELECT "+Anime.ANIME_ID+","+Anime.TITLE+","+Anime.IMAGE_URL+","+Anime.FORMAT+","+Anime.MEDIA_TYPE+","+Anime.GENRES+","+Anime.MOVIE_DB_RATING+","+Anime.BACKGROUND_POSTER+" FROM ANIME ORDER BY TRENDING DESC LIMIT 9")
    Flowable<List<TrendingAnime>> get9TrendingAnime();

    @Query("SELECT "+Anime.ANIME_ID+","+Anime.TITLE+","+Anime.IMAGE_URL+","+Anime.FORMAT+","+Anime.MEDIA_TYPE+" FROM ANIME ORDER BY RATING DESC")
    Single<List<ExploreView>> getTop100Anime();

    @Query("SELECT "+Anime.ANIME_ID+","+Anime.TITLE+","+Anime.IMAGE_URL+","+Anime.FORMAT+","+Anime.MEDIA_TYPE+","+Anime.GENRES+","+Anime.MOVIE_DB_RATING+" FROM ANIME ORDER BY RATING DESC")
    Single<List<ExplorePlainView>> getTop100Anime2();

    @Query("SELECT "+Anime.ANIME_ID+","+Anime.TITLE+","+Anime.IMAGE_URL+","+Anime.FORMAT+","+Anime.MEDIA_TYPE+" FROM ANIME ORDER BY RATING DESC LIMIT 9")
    Flowable<List<ExploreView>> get9Top100Anime();

    @Query("SELECT "+Anime.ANIME_ID+","+Anime.TITLE+","+Anime.IMAGE_URL+","+Anime.FORMAT+","+Anime.MEDIA_TYPE+" FROM ANIME ORDER BY popularity DESC")
    Single<List<ExploreView>> getPopularAnime();

    @Query("SELECT "+Anime.ANIME_ID+","+Anime.TITLE+","+Anime.IMAGE_URL+","+Anime.FORMAT+","+Anime.MEDIA_TYPE+","+Anime.GENRES+","+Anime.MOVIE_DB_RATING+" FROM ANIME ORDER BY popularity DESC")
    Single<List<ExplorePlainView>> getPopularAnimePlain();

    @Query("SELECT "+Anime.ANIME_ID+","+Anime.TITLE+","+Anime.IMAGE_URL+","+Anime.FORMAT+","+Anime.MEDIA_TYPE+" FROM ANIME ORDER BY popularity DESC LIMIT 9")
    Flowable<List<ExploreView>> get9PopularAnime();

    @Query("SELECT "+Anime.ANIME_ID+","+Anime.TITLE+","+Anime.IMAGE_URL+","+Anime.FORMAT+","+Anime.MEDIA_TYPE+" FROM ANIME WHERE STATUS='Not Yet Aired' ORDER BY popularity DESC")
    Single<List<ExploreView>> getTopUpcomingAnime();

    @Query("SELECT "+Anime.ANIME_ID+","+Anime.TITLE+","+Anime.IMAGE_URL+","+Anime.FORMAT+","+Anime.MEDIA_TYPE+","+Anime.GENRES+","+Anime.MOVIE_DB_RATING+" FROM ANIME WHERE STATUS='Not Yet Aired' ORDER BY popularity DESC")
    Single<List<ExplorePlainView>> getTopUpcomingAnimePlain();

    @Query("SELECT "+Anime.ANIME_ID+","+Anime.TITLE+","+Anime.IMAGE_URL+","+Anime.FORMAT+","+Anime.MEDIA_TYPE+" FROM ANIME WHERE STATUS='Not Yet Aired' ORDER BY popularity DESC LIMIT 9")
    Flowable<List<ExploreView>> get9TopUpcomingAnime();

    @Query("SELECT "+Anime.ANIME_ID+","+Anime.TITLE+","+Anime.IMAGE_URL+","+Anime.FORMAT+","+Anime.MEDIA_TYPE+" FROM ANIME WHERE SEASON=:season ORDER BY popularity DESC")
    Single<List<ExploreView>> getAnimeBySeason(String season);

    @Query("SELECT "+Anime.ANIME_ID+","+Anime.TITLE+","+Anime.IMAGE_URL+","+Anime.FORMAT+","+Anime.MEDIA_TYPE+","+Anime.GENRES+","+Anime.MOVIE_DB_RATING+" FROM ANIME WHERE SEASON=:season ORDER BY popularity DESC")
    Single<List<ExplorePlainView>> getAnimeBySeasonPlain(String season);

    @RawQuery
    Single<List<ExploreView>> getPersonalizedAnime(SupportSQLiteQuery query);

    @RawQuery
    Single<List<ExplorePlainView>> getPersonalizedAnimePlain(SupportSQLiteQuery query);


    @Query("DELETE FROM ANIME")
    Completable reset();






}
