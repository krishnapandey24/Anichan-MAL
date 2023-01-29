package com.omnicoder.anichan.repositories;


import static com.omnicoder.anichan.utils.Constants.NSFW_TAG;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.database.AnimeDao;
import com.omnicoder.anichan.database.UserAnime;
import com.omnicoder.anichan.database.UserListDB;
import com.omnicoder.anichan.models.UpdateAnimeResponse;
import com.omnicoder.anichan.models.animeListResponse.UserAnimeListResponse;
import com.omnicoder.anichan.models.animeListResponse.UserListAnime;
import com.omnicoder.anichan.models.animeResponse.Anime;
import com.omnicoder.anichan.models.animeResponse.AnimeListStatus;
import com.omnicoder.anichan.models.animeResponse.StartSeason;
import com.omnicoder.anichan.network.MalApi;
import com.omnicoder.anichan.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class AnimeListRepository {
    AnimeDao animeDao;
    UserListDB userListDB;
    MalApi malApi;
    boolean nsfw;


    @Inject
    public AnimeListRepository(AnimeDao animeDao, UserListDB userListDB, MalApi malApi, SharedPreferences sharedPreferences) {
        this.animeDao = animeDao;
        this.userListDB = userListDB;
        this.malApi = malApi;
        this.nsfw = sharedPreferences.getBoolean(NSFW_TAG, false);
    }


    public Flowable<List<UserAnime>> searchAnime(String title) {
        return animeDao.searchAnime(title);
    }


    @SuppressLint("NonConstantResourceId")
    public Flowable<List<UserAnime>> getAnimeListByStatus(String status, int sortBy) {
        switch (sortBy) {
            case R.id.title:
                return animeDao.getAnimeListByTitle(status);
            case R.id.score:
                return animeDao.getAnimeListByMean(status);
            default:
                return animeDao.getAnimeListByID(status);
        }
    }


    @SuppressLint("NonConstantResourceId")
    public Flowable<List<UserAnime>> getReWatching(int sortBy) {
        switch (sortBy) {
            case R.id.title:
                return animeDao.getReWatchingByTitle();
            case R.id.score:
                return animeDao.getReWatchingByMean();
            default:
                return animeDao.getReWatchingById();
        }
    }

    public Observable<UserAnimeListResponse> fetchUserAnimeList() {
        return malApi.getUserAnimeList(Constants.LIMIT, nsfw,Constants.USER_ANIME_LIST_FIELDS);
    }

    public Completable deleteAllAnime() {
        return animeDao.deleteAllAnime();
    }


    public boolean insertAnimeInDB(UserAnimeListResponse response) {
        try {
            List<UserListAnime> userListAnimeList = response.getData();
            int size = userListAnimeList.size();
            List<UserAnime> userAnimeList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                UserListAnime userListAnime = userListAnimeList.get(i);
                Anime node = userListAnime.getNode();
                AnimeListStatus listStatus = userListAnime.getList_status();
                String mainPicture = node.getMainPicture() == null ? "" : node.getMainPicture().getMedium();
                StartSeason startSeason = node.getStart_season();
                String season;

                if (startSeason == null) {
                    season = "";
                } else {
                    season = startSeason.getSeason() + " " + startSeason.getYear();
                }
                userAnimeList.add(new UserAnime(node.getId(), node.getTitle(), mainPicture, node.getMedia_type(), season, listStatus.getStatus(), listStatus.getStart_date(), listStatus.getFinish_date(), listStatus.getScore(), listStatus.getNum_episodes_watched(), node.getNum_episodes(), listStatus.isIs_rewatching(), node.getMean()));
            }
            CompletableObserver observer=animeDao.insertAllAnime(userAnimeList).subscribeWith(new CompletableObserver() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void onError(@NonNull Throwable e) {

                }
            });
            observer.onComplete();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    public Observable<UpdateAnimeResponse> updateAnime(Integer id, String status, boolean isRewatching, Integer score, Integer numOfWatchedEpisodes) {
        return malApi.updateAnime(
                id,
                status,
                isRewatching,
                score,
                numOfWatchedEpisodes,
                null,
                null,
                null,
                null,
                null
        );
    }


    public Completable addOrUpdateAnimeInList(UserAnime userAnime) {
        return animeDao.insertOrUpdateAnime(userAnime);
    }


    public Observable<UpdateAnimeResponse> addEpisode(int id, int numberOfEpisodesWatched) {
        return malApi.updateAnime(
                id,
                null,
                null,
                null,
                numberOfEpisodesWatched,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Completable animeCompleted(int id) {
        return malApi.animeCompleted(id, "completed");
    }

    public Completable deleteAnime(int id) {
        return malApi.deleteAnimeFromList(id);
    }

    public Completable addEpisodeInDB(int id, int numberOfEpisodesWatched) {
        return animeDao.addEpisode(id, numberOfEpisodesWatched);
    }

    public Completable animeCompletedInDB(int id) {
        return animeDao.animeCompleted(id, "completed");
    }

    public Completable deleteAnimeFromDB(int id) {
        return animeDao.deleteAnimeFromList(id);
    }


    public Observable<UserAnimeListResponse> fetchNextPage(String nextPage) {
        return malApi.getNextPage(nextPage);
    }












}
