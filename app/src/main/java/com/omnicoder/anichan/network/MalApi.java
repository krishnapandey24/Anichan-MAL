package com.omnicoder.anichan.network;


import com.omnicoder.anichan.models.AccessToken;
import com.omnicoder.anichan.models.animeListResponse.UserAnimeListResponse;
import com.omnicoder.anichan.models.animeResponse.Anime;
import com.omnicoder.anichan.models.animeResponse.Characters.CharacterResponse;
import com.omnicoder.anichan.models.mangaListResponse.UpdateMangaResponse;
import com.omnicoder.anichan.models.mangaListResponse.UserMangaListResponse;
import com.omnicoder.anichan.models.mangaResponse.Manga;
import com.omnicoder.anichan.models.responses.RankingResponse;
import com.omnicoder.anichan.models.UpdateAnimeResponse;
import com.omnicoder.anichan.models.UserInfo;


import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface MalApi {

    // For Anime

    @FormUrlEncoded
    @PATCH("anime/{anime_id}/my_list_status")
    Observable<UpdateAnimeResponse> updateAnime(
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


    @FormUrlEncoded
    @PATCH("anime/{anime_id}/my_list_status")
    Completable animeCompleted(
            @Path("anime_id") Integer anime_id,
            @Field("status") String status
    );

    @DELETE("anime/{anime_id}/my_list_status")
    Completable deleteAnimeFromList(
            @Path("anime_id") Integer anime_id
    );



    @FormUrlEncoded
    @POST("v1/oauth2/token")
    Observable<AccessToken> getAccessToken(
            @Field(value = "client_id",encoded = true) String clientId,
            @Field(value = "code",encoded = true) String code,
            @Field(value = "code_verifier",encoded = true) String codeVerifier,
            @Field(value = "grant_type",encoded = true) String grantType
    );

    @GET("anime/ranking")
    Observable<RankingResponse> getAnimeRanking(
            @Query("ranking_type") String rankingType,
            @Query("limit") int limit,
            @Query("fields") String fields,
            @Query("nsfw") Boolean nsfw
    );


    @GET("anime/suggestions")
    Observable<RankingResponse> getSuggestions(
            @Query("limit") int limit,
            @Query("fields") String fields,
            @Query("nsfw") Boolean nsfw
    );


    @GET("anime/ranking")
    Single<RankingResponse> getAnimeRanking(
            @Query("ranking_type") String rankingType,
            @Query("limit") int limit,
            @Query("fields") String fields,
            @Query("nsfw") Boolean nsfw,
            @Query("offset") int offset
    );

    @GET("anime/season/{year}/{season}")
    Single<RankingResponse> getSeason(
            @Path("year") String year,
            @Path("season") String season,
            @Query("sort") String sort,
            @Query("limit") int limit,
            @Query("fields") String fields,
            @Query("nsfw") Boolean nsfw,
            @Query("offset") int offset
    );

    @GET("{animeOrManga}")
    Single<RankingResponse> searchAnime(
            @Path("animeOrManga") String animeOrManga,
            @Query("q") String query,
            @Query("limit") int limit,
            @Query("nsfw") Boolean nsfw,
            @Query("offset") int offset,
            @Query("fields") String fields

    );

    @GET("anime/{anime_id}?")
    Observable<Anime> getAnimeDetails(
            @Path("anime_id") int id,
            @Query("fields") String fields
    );


    @GET("users/@me/animelist?fields=list_status,title,id,media_type,main_picture,num_episodes,start_season,broadcast,mean")
    Observable<UserAnimeListResponse> getUserAnimeList(
            @Query("limit") Integer limit,
            @Query("nsfw") Boolean nsfw
    );

    @GET
    Observable<UserAnimeListResponse> getNextPage(
            @Url String url
    );





    @GET("manga/ranking")
    Observable<RankingResponse> getMangaRanking(
            @Query("ranking_type") String rankingType,
            @Query("limit") Integer limit,
            @Query("fields") String fields,
            @Query("nsfw") Boolean nsfw

    );

    @GET("manga/ranking")
    Single<RankingResponse> getMangaRanking(
            @Query("ranking_type") String rankingType,
            @Query("limit") Integer limit,
            @Query("fields") String fields,
            @Query("nsfw") Boolean nsfw,
            @Query("offset") Integer offset
    );

    @GET("manga/{manga_id}")
    Observable<Manga> getMangaDetails(
            @Path("manga_id") int id,
            @Query("fields") String fields

    );

    @GET("users/@me/mangalist?fields=list_status,title,id,media_type,main_picture,num_volumes,num_chapters,mean")
    Observable<UserMangaListResponse> getUserMangaList(
            @Query("limit") Integer limit,
            @Query("nsfw") Boolean nsfw
    );

    @GET
    Observable<UserMangaListResponse> getUserMangaList(
            @Url String url
    );

    @FormUrlEncoded
    @PATCH("manga/{manga_id}/my_list_status")
    Completable mangaCompleted(
            @Path("manga_id") Integer manga_id,
            @Field("status") String status
    );

    @DELETE("manga/{manga_id}/my_list_status")
    Completable deleteMangaFromList(
            @Path("manga_id") Integer manga_id
    );


    @FormUrlEncoded
    @PATCH("manga/{manga_id}/my_list_status")
    Observable<UpdateMangaResponse> updateManga(
            @Path("manga_id") Integer manga_id,
            @Field("status") String status,
            @Field("is_rereading") Boolean is_rewatching,
            @Field("score") Integer score,
            @Field("num_volumes_read") Integer noOfVolumesRead,
            @Field("num_chapters_read") Integer noOfChaptersRead,
            @Field("priority") Integer priority,
            @Field("num_times_reread") Integer numTimeReRead,
            @Field("reread_value") Integer reReadValue,
            @Field("tags") String tags,
            @Field("comments") String comments
    );


    @GET("users/@me?fields=id,name,gender,location,joined_at,anime_statistics")
    Observable<UserInfo> getUserInfo();



}
