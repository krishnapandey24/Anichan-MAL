package com.omnicoder.anichan.Network;


import com.omnicoder.anichan.Models.UpdateAnimeResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface MalApi {

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

    @FormUrlEncoded
    @PATCH("anime/{anime_id}/my_list_status")
    Completable addEpisode(@Header("Authorization") String accessToken,
                           @Path("anime_id") Integer anime_id,
                           @Field("num_watched_episodes") Integer noOfWatchedEpisodes
    );

    @FormUrlEncoded
    @PATCH("anime/{anime_id}/my_list_status")
    Completable animeCompleted(@Header("Authorization") String accessToken,
                                                @Path("anime_id") Integer anime_id,
                                                @Field("status") String status
    );

    @FormUrlEncoded
    @DELETE("anime/{anime_id}/my_list_status")
    Completable deleteAnimeFromList(@Header("Authorization") String accessToken,
                               @Path("anime_id") Integer anime_id
    );












}
