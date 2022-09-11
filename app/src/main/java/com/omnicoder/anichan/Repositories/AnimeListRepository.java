package com.omnicoder.anichan.Repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.omnicoder.anichan.Database.UserListDB;
import com.omnicoder.anichan.Database.AnimeDao;
import com.omnicoder.anichan.Database.UserAnime;
import com.omnicoder.anichan.Models.AnimeListResponse.UserAnimeListResponse;
import com.omnicoder.anichan.Models.AnimeListResponse.UserListAnime;
import com.omnicoder.anichan.Models.AnimeResponse.Anime;
import com.omnicoder.anichan.Models.AnimeResponse.AnimeListStatus;
import com.omnicoder.anichan.Models.AnimeResponse.StartSeason;
import com.omnicoder.anichan.Models.UpdateAnimeResponse;
import com.omnicoder.anichan.Network.MalApi;
import com.omnicoder.anichan.Utils.Constants;

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
    String accessToken;


    @Inject
    public AnimeListRepository(AnimeDao animeDao, Context context, UserListDB userListDB, MalApi malApi){
        this.animeDao=animeDao;
        SharedPreferences sharedPreferences=context.getSharedPreferences("AccessToken", Context.MODE_PRIVATE);
        this.accessToken=" Bearer "+sharedPreferences.getString("accessToken",null);
        this.userListDB = userListDB;
        this.malApi=malApi;

    }



    public Flowable<List<UserAnime>> searchAnime(String title){
        return animeDao.searchAnime(title);
    }


    public Flowable<List<UserAnime>> getAnimeListByStatus(String status, String sortBy){
        return animeDao.getAnimeList(status,sortBy);
    }

    public Completable addAnimeToList(UserAnime userAnime){
        return animeDao.insertOrUpdateAnime(userAnime);
    }

    public Flowable<List<UserAnime>> getAllAnime(String sortBy){
        return animeDao.getAllAnime(sortBy);
    }


    public Flowable<List<UserAnime>> getReWatching(String sortBy){
        return animeDao.getReWatching(sortBy);
    }

    public Observable<UserAnimeListResponse> fetchUserAnimeList(){
        return malApi.getUserAnimeList(accessToken,Constants.LIMIT);
    }

    public Completable deleteAllAnime(){
        return animeDao.deleteAllAnime();
    }



    public boolean insertAnimeInDB(UserAnimeListResponse response){
        try{
            Log.d("tagg","functions called");
            List<UserListAnime> userListAnimeList=response.getData();
            int size=userListAnimeList.size();
            List<UserAnime> userAnimeList =new ArrayList<>();
            for(int i=0;i<size;i++){
                UserListAnime userListAnime=userListAnimeList.get(i);
                Anime node= userListAnime.getNode();
                AnimeListStatus listStatus= userListAnime.getList_status();
                String mainPicture=node.getMainPicture()==null ? "" : node.getMainPicture().getMedium();
                StartSeason startSeason= node.getStart_season();
                String season;

                if(startSeason==null){
                    season="";
                }else{
                    season=startSeason.getSeason() + " " + startSeason.getYear();
                }
                userAnimeList.add(new UserAnime(node.getId(),node.getTitle(),mainPicture,node.getMedia_type(),season,listStatus.getStatus(), listStatus.getStart_date(), listStatus.getFinish_date(), listStatus.getScore(),listStatus.getNum_episodes_watched(), node.getNum_episodes(), listStatus.isIs_rewatching()));
            }
            animeDao.insertAllAnime(userAnimeList).subscribeWith(new CompletableObserver() {
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
            Log.d("tagg","Error: insertAnime: "+e.getMessage());
            return false;
        }

    }


    public Observable<UpdateAnimeResponse> updateAnime(Integer id, String status, boolean isRewatching, Integer score, Integer numOfWatchedEpisodes){
        return malApi.updateAnime(accessToken,
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
        return malApi.addEpisode(accessToken,id,numberOfEpisodesWatched);
    }

    public Completable animeCompleted(int id){
        return malApi.animeCompleted(accessToken,id,"completed");
    }

    public Completable deleteAnime(int id){
        return malApi.deleteAnimeFromList(accessToken,id);
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
