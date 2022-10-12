package com.omnicoder.anichan.repositories;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.omnicoder.anichan.models.AccessToken;
import com.omnicoder.anichan.models.animeResponse.Anime;
import com.omnicoder.anichan.models.animeResponse.Characters.CharacterResponse;
import com.omnicoder.anichan.models.animeResponse.Staff.StaffResponse;
import com.omnicoder.anichan.models.animeResponse.videos.VideoResponse;
import com.omnicoder.anichan.models.mangaResponse.Manga;
import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.models.responses.RankingResponse;
import com.omnicoder.anichan.network.JikanAPI;
import com.omnicoder.anichan.network.MalApi;
import com.omnicoder.anichan.paging.SearchPagingSource;
import com.omnicoder.anichan.utils.Constants;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;


public class ExploreRepository {
    private static final String AIRING="airing";
    private static final String MANGA="manga";
    private static final String MANHWA="manhwa";
    private static final String MANHUA="manhua";
    private static final String UPCOMING="upcoming";
    private static final String FIELDS="media_type,mean,genres";
    MalApi malApi;
    String accessToken;
    JikanAPI jikanAPI;
    boolean nsfw;
    // TODO: 09-Oct-22 Add Nsfw


    @Inject
    public ExploreRepository(MalApi malApi, Context context, JikanAPI jikanAPI){
        this.malApi= malApi;
        this.jikanAPI=jikanAPI;
        SharedPreferences sharedPreferences=context.getSharedPreferences("AccessToken",Context.MODE_PRIVATE);
        this.nsfw= sharedPreferences.getBoolean("nsfw",false);
    }



    public Observable<RankingResponse> get9TrendingAnime(){
        return malApi.getAnimeRanking(AIRING,9,FIELDS);
    }

    public Observable<RankingResponse> getSuggestions(){
        return malApi.getSuggestions(9,FIELDS);
    }


    public Observable<RankingResponse> get9TopUpcomingAnime(){
        return malApi.getAnimeRanking(UPCOMING,9,FIELDS);
    }




    public Observable<AccessToken> getAccessToken(String code, String codeVerified){
        String clientId= Constants.CLIENT_ID;
        String grantType= "authorization_code";
        return malApi.getAccessToken(clientId,code,codeVerified,grantType);
    }



    public Flowable<PagingData<Data>> searchAnime(String query, int isAnime){
        SearchPagingSource searchPagingSource= new SearchPagingSource(malApi,query, nsfw,isAnime);
        return PagingRx.getFlowable(new Pager(new PagingConfig(Constants.LIMIT),() -> searchPagingSource));
    }


    public Observable<Anime> getAnime(int id){
        return malApi.getAnimeDetails(id,Constants.ANIME_DETAILS_FIELDS);
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


    public Observable<Manga> getManga(int id){
        return malApi.getMangaDetails(id,Constants.MANGA_DETAILS_FIELDS);
    }

    public Observable<RankingResponse> get9TopManga(){
        return malApi.getMangaRanking(MANGA,9,FIELDS);
    }

    public Observable<RankingResponse> get9TopManhwa(){
        return malApi.getMangaRanking(MANHWA,9,FIELDS);
    }

    public Observable<RankingResponse> get9TopManhua(){
        return malApi.getMangaRanking(MANHUA,9,FIELDS);
    }

    public Observable<RankingResponse> get9OneShots(){
        return malApi.getMangaRanking(MANGA,9,FIELDS);
    }

    public Observable<CharacterResponse> getMangaCharacters(int id){
        return jikanAPI.getMangaCharacters(id);
    }

    public Observable<StaffResponse> getAuthors(int id){
        return jikanAPI.getStaff(id);
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
