package com.omnicoder.anichan;


import android.content.Context;
import android.content.SharedPreferences;

import com.omnicoder.anichan.Database.AnimeDao;
import com.omnicoder.anichan.Database.UserAnime;

import com.omnicoder.anichan.Models.UpdateAnimeResponse;
import com.omnicoder.anichan.Network.MalApi;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class Repository {
    MalApi rxAPI;
    AnimeDao animeDao;
    public String accessToken;

    @Inject
    public Repository(MalApi rxAPI, AnimeDao animeDao, Context context){
        this.animeDao=animeDao;
        SharedPreferences sharedPreferences=context.getSharedPreferences("AccessToken", Context.MODE_PRIVATE);
        this.accessToken=" Bearer "+sharedPreferences.getString("accessToken",null);
        this.rxAPI=rxAPI;
    }


    public Observable<UpdateAnimeResponse> updateAnime(Integer id, String status, boolean isRewatching, Integer score, Integer numOfWatchedEpisodes){
        return rxAPI.updateAnime(accessToken,
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


    public Completable addOrUpdateAnimeInList(UserAnime userAnime){
        return animeDao.insertOrUpdateAnime(userAnime);
    }


    public Completable addEpisode(int id,int numberOfEpisodesWatched){
        return rxAPI.addEpisode(accessToken,id,numberOfEpisodesWatched);
    }

    public Completable animeCompleted(int id){
        return rxAPI.animeCompleted(accessToken,id,"completed");
    }

    public Completable deleteAnime(int id){
        return rxAPI.deleteAnimeFromList(accessToken,id);
    }

    public Completable addEpisodeInDB(int id,int numberOfEpisodesWatched){
        return animeDao.addEpisode(id,numberOfEpisodesWatched);
    }

    public Completable animeCompletedInDB(int id){
        return animeDao.animeCompleted(id,"completed");
    }

    public Completable deleteAnimeFromDB(int id){
        return animeDao.deleteAnimeFromList(id);
    }


}
