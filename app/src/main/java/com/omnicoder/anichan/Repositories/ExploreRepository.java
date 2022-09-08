package com.omnicoder.anichan.Repositories;


import android.content.Context;
import android.content.SharedPreferences;
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
import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.Models.Responses.RankingResponse;
import com.omnicoder.anichan.Network.JikanAPI;
import com.omnicoder.anichan.Network.MalApi;
import com.omnicoder.anichan.Paging.SearchPagingSource;
import com.omnicoder.anichan.Utils.Constants;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;


public class ExploreRepository {
    private static final String AIRING="airing";
    private static final String MANGA="manga";
    private static final String MANHWA="manhwa";
    private static final String MANHUA="manhua";
    private static final String ONE_SHOTS="oneshots";
    private static final String UPCOMING="upcoming";
    private static final String FIELDS="media_type,mean,genres";
    MalApi malApi;
    String accessToken;
    JikanAPI jikanAPI;
    boolean nsfw;

    @Inject
    public ExploreRepository(MalApi malApi, Context context, JikanAPI jikanAPI){
        this.malApi= malApi;
        this.jikanAPI=jikanAPI;
        SharedPreferences sharedPreferences=context.getSharedPreferences("AccessToken",Context.MODE_PRIVATE);
        this.accessToken=" Bearer "+sharedPreferences.getString("accessToken",null);
        Log.d("tagg","ac:\n"+accessToken);
        this.nsfw= sharedPreferences.getBoolean("nsfw",false);
    }



    public Observable<RankingResponse> get9TrendingAnime(){
        return malApi.getAnimeRanking(accessToken,AIRING,9,FIELDS);
    }

    public Observable<RankingResponse> getSuggestions(){
        return malApi.getSuggestions(accessToken,9,FIELDS);
    }


    public Observable<RankingResponse> get9TopUpcomingAnime(){
        return malApi.getAnimeRanking(accessToken,UPCOMING,9,FIELDS);
    }




    public Observable<AccessToken> getAccessToken(String code, String codeVerified){
        String clientId= Constants.CLIENT_ID;
        String grantType= "authorization_code";
        return malApi.getAccessToken(clientId,code,codeVerified,grantType);
    }



    public Flowable<PagingData<Data>> searchAnime(String query, int isAnime){
        SearchPagingSource searchPagingSource= new SearchPagingSource(malApi,query,accessToken, nsfw,isAnime);
        return PagingRx.getFlowable(new Pager(new PagingConfig(Constants.LIMIT),() -> searchPagingSource));
    }


    public Observable<Anime> getAnime(int id){
        return malApi.getAnimeDetails(accessToken,id);
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


    // For Manga
    public Observable<RankingResponse> get9TopManga(){
        return malApi.getMangaRanking(accessToken,MANGA,9,FIELDS);
    }

    public Observable<RankingResponse> get9TopManhwa(){
        return malApi.getMangaRanking(accessToken,MANHWA,9,FIELDS);
    }

    public Observable<RankingResponse> get9TopManhua(){
        return malApi.getMangaRanking(accessToken,MANHUA,9,FIELDS);
    }

    public Observable<RankingResponse> get9OneShots(){
        return malApi.getMangaRanking(accessToken,MANGA,9,FIELDS);
    }




    
    
    
    /*
    all	All
manga	Top Manga
novels	Top Novels
oneshots	Top One-shots
doujin	Top Doujinshi
manhwa	Top Manhwa
manhua	Top Manhua
bypopularity	Most Popular
favorite	Most Favorited
     */


}
