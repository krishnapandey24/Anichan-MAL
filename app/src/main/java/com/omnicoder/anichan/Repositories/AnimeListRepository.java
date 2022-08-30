package com.omnicoder.anichan.Repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.omnicoder.anichan.Database.UserAnime;
import com.omnicoder.anichan.Database.AnimeDB;
import com.omnicoder.anichan.Database.AnimeDao;
import com.omnicoder.anichan.Models.AnimeListResponse.UserAnimeListResponse;
import com.omnicoder.anichan.Models.AnimeListResponse.UserListAnime;
import com.omnicoder.anichan.Models.AnimeResponse.Anime;
import com.omnicoder.anichan.Models.AnimeResponse.AnimeListStatus;
import com.omnicoder.anichan.Models.AnimeResponse.StartSeason;
import com.omnicoder.anichan.Models.UpdateAnimeResponse;
import com.omnicoder.anichan.Network.MalApi;
import com.omnicoder.anichan.Network.RxAPI;
import com.omnicoder.anichan.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
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
    MalApi malApi;


    @Inject
    public AnimeListRepository(AnimeDao animeDao, Context context, RxAPI rxAPI, AnimeDB animeDB, MalApi malApi){
        this.animeDao=animeDao;
        SharedPreferences sharedPreferences=context.getSharedPreferences("AccessToken", Context.MODE_PRIVATE);
        this.accessToken=" Bearer "+sharedPreferences.getString("accessToken",null);
        this.rxAPI=rxAPI;
        this.animeDB=animeDB;
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

}
