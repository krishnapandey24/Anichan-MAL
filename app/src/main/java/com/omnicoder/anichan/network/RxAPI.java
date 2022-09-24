package com.omnicoder.anichan.Network;


import com.omnicoder.anichan.Models.AccessToken;
import com.omnicoder.anichan.Models.AnimeListResponse.UserAnimeListResponse;
import com.omnicoder.anichan.Models.AnimeResponse.Anime;
import com.omnicoder.anichan.Models.AnimeResponse.AnimeListStatus;
import com.omnicoder.anichan.Models.Responses.RankingResponse;
import com.omnicoder.anichan.Models.UpdateAnimeResponse;
import com.omnicoder.anichan.UI.Activities.TestActivity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RxAPI {

    @FormUrlEncoded
    @POST("v1/oauth2/token")
    Observable<AccessToken> getAccessToken(@Field(value = "client_id",encoded = true) String clientId, @Field(value = "code",encoded = true) String code, @Field(value = "code_verifier",encoded = true) String codeVerifier, @Field(value = "grant_type",encoded = true) String grantType);

    @GET("anime/ranking")
    Observable<RankingResponse> getRanking(@Header("Authorization") String accessToken, @Query("ranking_type") String rankingType, @Query("limit") int limit, @Query("fields") String fields);

    @GET("anime/suggestions")
    Observable<RankingResponse> getSuggestions(@Header("Authorization") String accessToken, @Query("limit") int limit, @Query("fields") String fields);


    @GET("anime/ranking")
    Single<RankingResponse> getRanking(@Header("Authorization") String accessToken, @Query("ranking_type") String rankingType, @Query("limit") int limit, @Query("fields") String fields,@Query("nsfw") Boolean nsfw, @Query("offset") int offset);

    @GET("anime/season/{year}/{season}")
    Single<RankingResponse> getSeason(@Header("Authorization") String accessToken,@Path("year") String year, @Path("season") String season, @Query("sort") String sort, @Query("limit") int limit, @Query("fields") String fields,@Query("nsfw") Boolean nsfw, @Query("offset") int offset);

    @GET("{animeOrManga}")
    Single<RankingResponse> searchAnime(@Header("Authorization") String accessToken, @Path("animeOrManga") String animeOrManga,@Query("q") String query, @Query("limit") int limit,@Query("nsfw") Boolean nsfw, @Query("offset") int offset);

    @GET("anime/{anime_id}?fields=id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_episodes,start_season,broadcast,source,average_episode_duration,rating,pictures,background,related_anime,related_manga,recommendations,studios,statistics,opening_themes,ending_themes,my_list_status")
    Observable<Anime> getAnime(@Header("Authorization") String accessToken,@Path("anime_id") int id);

    @FormUrlEncoded
    @PATCH("anime/{anime_id}/my_list_status")
    Observable<UpdateAnimeResponse> updateAnime(@Header("Authorization") String accessToken,
                                                @Path("anime_id") Integer anime_id,
                                                @Field("status") String status,
                                                @Field("is_rewatching") Boolean is_rewatching,
                                                @Field("score") Integer score,
                                                @Field("num_watched_episodes") Integer noOfWatchedEpisodes,
                                                @Field("priority") Integer priority,
                                                @Field("num_times_rewatched") Integer numOfTimesRewatched,
                                                @Field("rewatch_value") Integer rewatchValue,
                                                @Field("tags") String tags,
                                                @Field("comments") String comments
    );


    @GET("users/@me/animelist?fields=list_status,title,id,media_type,main_picture,num_episodes,start_season,broadcast")
    Observable<UserAnimeListResponse> getUserAnimeList(@Header("Authorization") String accessToken,@Query("limit") int limit);

















}
