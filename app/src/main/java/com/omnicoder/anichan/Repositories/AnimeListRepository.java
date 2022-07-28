package com.omnicoder.anichan.Repositories;

import android.content.Context;
import android.content.SharedPreferences;

import com.omnicoder.anichan.Database.AnimeList;
import com.omnicoder.anichan.Database.AnimeListDao;
import com.omnicoder.anichan.Models.AnimeListResponse.UserAnimeListResponse;
import com.omnicoder.anichan.Models.AnimeListResponse.UserListAnime;
import com.omnicoder.anichan.Models.AnimeResponse.AnimeListStatus;
import com.omnicoder.anichan.Network.RxAPI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public class AnimeListRepository {
    AnimeListDao animeListDao;
    RxAPI rxAPI;
    String accessToken;

    @Inject
    public AnimeListRepository(AnimeListDao animeListDao, Context context, RxAPI rxAPI){
        this.animeListDao=animeListDao;
        SharedPreferences sharedPreferences=context.getSharedPreferences("AccessToken", Context.MODE_PRIVATE);
        this.accessToken=" Bearer "+sharedPreferences.getString("accessToken",null);
        this.rxAPI=rxAPI;

    }

    public Completable addAnimeToList(AnimeList animeListItem){
        return animeListDao.addAnime(animeListItem);
    }

    public Flowable<List<AnimeList>> getWatching(){
        return animeListDao.getWatching();
    }

    public Flowable<List<AnimeList>> getPlanToWatch(){
        return animeListDao.getPlanToWatch();
    }

    public Flowable<List<AnimeList>> getCompleted(){
        return animeListDao.getCompleted();
    }

    public Flowable<List<AnimeList>> getOnHold(){
        return animeListDao.getOnHold();
    }

    public Flowable<List<AnimeList>> getDropped(){
        return animeListDao.getDropped();
    }

    public Flowable<List<AnimeList>> getReWatching(){
        return animeListDao.getReWatching();
    }

    public Flowable<List<AnimeList>> getAll(){
        return animeListDao.getAll();
    }

    public Flowable<List<AnimeList>> getAnimeList(String status){
        return animeListDao.fetchAnimeList(status,"watchedEpisodes");
    }

    public Completable addEpisode(int id){
        return animeListDao.addEpisode(id);
    }

    public Completable animeCompleted(int id){
        return animeListDao.updateStatus("Completed",id);
    }

    public Completable deleteAnime(int id){
        return animeListDao.deleteAnime(id);
    }

    public Flowable<List<AnimeList>> searchAnime(CharSequence query){
        return animeListDao.searchAnime(query.toString());
    }

    public Observable<AnimeListStatus> updateAnimeList(int anime_id, String status, boolean is_rewatching, int num_of_episodes_watched, int score, String start_date, String finish_date){
        List<String> strings=new ArrayList<>();
        return rxAPI.updateAnimeListStatus(accessToken, anime_id, status, is_rewatching, score, num_of_episodes_watched, 0, 0, 0, strings, "", start_date, finish_date);
    }











}
