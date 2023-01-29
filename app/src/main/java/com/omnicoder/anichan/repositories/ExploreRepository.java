package com.omnicoder.anichan.repositories;


import static com.omnicoder.anichan.utils.Constants.ANIME_JAPANESE_TITLES;
import static com.omnicoder.anichan.utils.Constants.MANGA_JAPANESE_TITLES;
import static com.omnicoder.anichan.utils.Constants.NSFW_TAG;

import android.content.SharedPreferences;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.omnicoder.anichan.models.animeResponse.Anime;
import com.omnicoder.anichan.models.animeResponse.Characters.CharacterResponse;
import com.omnicoder.anichan.models.animeResponse.Staff.StaffResponse;
import com.omnicoder.anichan.models.animeResponse.videos.VideoResponse;
import com.omnicoder.anichan.models.mangaResponse.Manga;
import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.models.responses.RankingResponse;
import com.omnicoder.anichan.network.JikanApi;
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
    private static final String FIELDS="media_type,mean,genres,alternative_titles";
    MalApi malApi;
    JikanApi jikanAPI;
    boolean nsfw,animeJapaneseTitles,mangaJapaneseTitles;


    @Inject
    public ExploreRepository(MalApi malApi, JikanApi jikanAPI, SharedPreferences sharedPreferences){
        this.malApi= malApi;
        this.jikanAPI=jikanAPI;
        this.nsfw= sharedPreferences.getBoolean(NSFW_TAG,false);
        this.animeJapaneseTitles=sharedPreferences.getBoolean(ANIME_JAPANESE_TITLES,false);
        this.mangaJapaneseTitles=sharedPreferences.getBoolean(MANGA_JAPANESE_TITLES,false);
    }



    public Observable<RankingResponse> get9TrendingAnime(){
        String fields= animeJapaneseTitles ? FIELDS : FIELDS+Constants.NUM_SCORE;
        return malApi.getAnimeRanking(AIRING,9,fields,nsfw);
    }

    public Observable<RankingResponse> getSuggestions(){
        String fields= animeJapaneseTitles ? FIELDS : FIELDS+Constants.NUM_SCORE;
        return malApi.getSuggestions(9,fields,nsfw);
    }


    public Observable<RankingResponse> get9TopUpcomingAnime(){
        String fields= animeJapaneseTitles ? FIELDS : FIELDS+Constants.NUM_SCORE;
        return malApi.getAnimeRanking(UPCOMING,9,fields,nsfw);
    }



    public Flowable<PagingData<Data>> searchAnime(String query, int isAnime){
        String fields= animeJapaneseTitles ? FIELDS : FIELDS+Constants.NUM_SCORE;
        SearchPagingSource searchPagingSource= new SearchPagingSource(malApi,query, nsfw,isAnime,fields);
        return PagingRx.getFlowable(new Pager(new PagingConfig(Constants.LIMIT),() -> searchPagingSource));
    }


    public Observable<Anime> getAnime(int id){
        String fields= animeJapaneseTitles ? Constants.ANIME_DETAILS_FIELDS : Constants.ANIME_DETAILS_FIELDS+Constants.NUM_SCORE;
        return malApi.getAnimeDetails(id,fields);
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
        String fields= mangaJapaneseTitles ? Constants.MANGA_DETAILS_FIELDS : Constants.MANGA_DETAILS_FIELDS+Constants.NUM_SCORE;
        return malApi.getMangaDetails(id,fields);
    }

    public Observable<RankingResponse> get9TopManga(){
        String fields= mangaJapaneseTitles ? FIELDS : FIELDS+Constants.NUM_SCORE;
        return malApi.getMangaRanking(MANGA,9,fields,nsfw);
    }

    public Observable<RankingResponse> get9TopManhwa(){
        String fields= mangaJapaneseTitles ? FIELDS : FIELDS+Constants.NUM_SCORE;
        return malApi.getMangaRanking(MANHWA,9,fields,nsfw);
    }

    public Observable<RankingResponse> get9TopManhua(){
        String fields= mangaJapaneseTitles ? FIELDS : FIELDS+Constants.NUM_SCORE;
        return malApi.getMangaRanking(MANHUA,9,fields,nsfw);
    }

    public Observable<CharacterResponse> getMangaCharacters(int id){
        return jikanAPI.getMangaCharacters(id);
    }




}
