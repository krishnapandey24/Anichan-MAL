package com.omnicoder.anichan.Repositories;


import android.content.Context;
import android.util.Log;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.omnicoder.anichan.Models.AccessToken;
import com.omnicoder.anichan.Models.AnimeResponse.Anime;
import com.omnicoder.anichan.Models.AnimeResponse.Characters.CharacterResponse;
import com.omnicoder.anichan.Models.AnimeResponse.Staff.StaffResponse;
import com.omnicoder.anichan.Models.AnimeResponse.videos.VideoResponse;
import com.omnicoder.anichan.Models.Animes;
import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.Models.Responses.RankingResponse;
import com.omnicoder.anichan.Network.JikanAPI;
import com.omnicoder.anichan.Paging.RankingPagingSource;
import com.omnicoder.anichan.Network.RxAPI;
import com.omnicoder.anichan.Paging.SearchPagingSource;
import com.omnicoder.anichan.Paging.SeasonPagingSource;
import com.omnicoder.anichan.Utils.Constants;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;


public class ExploreRepository {
    RxAPI rxAPI;
    String accessToken;
    public static final String AIRING="airing";
    public static final String UPCOMING="upcoming";
    public static final String POPULAR="bypopularity";
    public static final String FIELDS="media_type,mean,genres";
    JikanAPI jikanAPI;

    @Inject
    public ExploreRepository(RxAPI rxAPI, Context context, JikanAPI jikanAPI){
        this.rxAPI= rxAPI;
        this.accessToken=" Bearer "+context.getSharedPreferences("AccessToken",Context.MODE_PRIVATE).getString("accessToken",null);
        this.jikanAPI=jikanAPI;
    }

    public Observable<RankingResponse> get9TrendingAnime(){
        return rxAPI.getRanking(accessToken,AIRING,9,FIELDS);
    }

    public Observable<RankingResponse> get9PopularAnime(){
        return rxAPI.getRanking(accessToken,POPULAR,9,FIELDS);
    }

    public Observable<RankingResponse> get9Top100Anime(){
        return rxAPI.getRanking(accessToken,null,9,FIELDS);
    }

    public Observable<RankingResponse> get9TopUpcomingAnime(){
        return rxAPI.getRanking(accessToken,UPCOMING,9,FIELDS);
    }




    public Observable<AccessToken> getAccessToken(String code, String codeVerified){
        String clientId= Constants.CLIENT_ID;
        String grantType= "authorization_code";
        return rxAPI.getAccessToken(clientId,code,codeVerified,grantType);
    }

    public Flowable<PagingData<Data>> getRanking(String rankingType){
        RankingPagingSource rankingPagingSource= new RankingPagingSource(rxAPI,rankingType,accessToken);
        return PagingRx.getFlowable(new Pager(new PagingConfig(Constants.LIMIT),() -> rankingPagingSource));
    }

    public Flowable<PagingData<Data>> getSeason(String year,String season){
        SeasonPagingSource seasonPagingSource= new SeasonPagingSource(rxAPI,accessToken,year,season);
        return PagingRx.getFlowable(new Pager(new PagingConfig(Constants.LIMIT),() -> seasonPagingSource));
    }

    public Flowable<PagingData<Data>> searchAnime(String query){
        SearchPagingSource searchPagingSource= new SearchPagingSource(rxAPI,query,accessToken);
        return PagingRx.getFlowable(new Pager(new PagingConfig(Constants.LIMIT),() -> searchPagingSource));
    }


    public Observable<Anime> getAnime(int id){
        return rxAPI.getAnime(accessToken,id);
    }

    public Observable<VideoResponse> getVideos(int id){
        return jikanAPI.getVideos(id);
    }

    public Observable<CharacterResponse> getCharacters(int id){
        return jikanAPI.getCharacters(id);
    }

    public Observable<StaffResponse> getStaff(int id){
        return jikanAPI.getStaff(id);
    }






}
