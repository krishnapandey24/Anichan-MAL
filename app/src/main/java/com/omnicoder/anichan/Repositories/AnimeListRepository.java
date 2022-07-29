package com.omnicoder.anichan.Repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.omnicoder.anichan.Database.Anime;
import com.omnicoder.anichan.Database.AnimeDB;
import com.omnicoder.anichan.Database.AnimeDao;
import com.omnicoder.anichan.Models.AnimeListResponse.UserAnimeListResponse;
import com.omnicoder.anichan.Models.AnimeListResponse.UserListAnime;
import com.omnicoder.anichan.Models.AnimeResponse.AnimeListStatus;
import com.omnicoder.anichan.Models.AnimeResponse.StartSeason;
import com.omnicoder.anichan.Network.RxAPI;
import com.omnicoder.anichan.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class AnimeListRepository {
    AnimeDao animeDao;
    AnimeDB animeDB;
    RxAPI rxAPI;
    String accessToken;
    CompositeDisposable compositeDisposable=new CompositeDisposable();


    @Inject
    public AnimeListRepository(AnimeDao animeDao, Context context, RxAPI rxAPI, AnimeDB animeDB){
        this.animeDao=animeDao;
        SharedPreferences sharedPreferences=context.getSharedPreferences("AccessToken", Context.MODE_PRIVATE);
        this.accessToken=" Bearer "+sharedPreferences.getString("accessToken",null);
        this.rxAPI=rxAPI;
        this.animeDB=animeDB;

    }

    public Observable<AnimeListStatus> updateAnimeList(int anime_id, String status, boolean is_rewatching, int num_of_episodes_watched, int score, String start_date, String finish_date){
        List<String> strings=new ArrayList<>();
        return rxAPI.updateAnimeListStatus(accessToken, anime_id, status, is_rewatching, score, num_of_episodes_watched, 0, 0, 0, strings, "", start_date, finish_date);
    }

    public Flowable<List<Anime>> getAnimeListByStatus(String status){
        return animeDao.getAnimeList(status);
    }

    public Flowable<List<Anime>> getAllAnime(){
        return animeDao.getAllAnime();
    }


    public Observable<UserAnimeListResponse> fetchUserAnimeList(){
        return rxAPI.getUserAnimeList(accessToken,Constants.LIMIT);
    }

    public Flowable<List<Anime>> getReWatching(){
        return animeDao.getReWatching();
    }

    public boolean insertAnimeInDB(UserAnimeListResponse response){
        try{
            animeDao.deleteAllAnime();
            int limit=Constants.USER_LIST_LIMIT;
            List<UserListAnime> userListAnimeList=response.getData();
            int size=userListAnimeList.size();
            List<Anime> animeList=new ArrayList<>();
            Log.d("tagg","Starting");
            for(int i=0;i<size;i++){
                UserListAnime userListAnime=userListAnimeList.get(i);
                Log.d("tagg","name: "+userListAnime.getNode().getTitle());
                com.omnicoder.anichan.Models.AnimeResponse.Anime node= userListAnime.getNode();
                AnimeListStatus listStatus= userListAnime.getList_status();
                String mainPicture=node.getMainPicture()==null ? "" : node.getMainPicture().getMedium();
                StartSeason startSeason= node.getStart_season();
                String season;
                if(startSeason==null){
                    season="";
                }else{
                    season=startSeason.getSeason() + " " + startSeason.getYear();
                }
                animeList.add(new Anime(node.getId(),node.getTitle(),mainPicture,node.getMedia_type(),season,listStatus.getStatus(),listStatus.getScore(),listStatus.getNum_episodes_watched(), node.getNum_episodes(), listStatus.isIs_rewatching()));
            }
            Log.d("tagg","End");
            Log.d("tagg","size of animelist now : "+animeList.size());
            animeDao.insertAllAnime(animeList).subscribeWith(new CompletableObserver() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    Log.d("tagg","on start");

                }

                @Override
                public void onComplete() {
                    Log.d("tagg","Completed");

                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Log.d("tagg","Something wrong: "+e.getMessage());

                }
            });
            Log.d("tagg","inserted!!!!!!");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("tagg","Error:  "+e.getMessage());
            return false;
        }

    }
























}
