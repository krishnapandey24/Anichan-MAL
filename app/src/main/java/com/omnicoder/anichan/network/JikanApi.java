package com.omnicoder.anichan.network;

import com.omnicoder.anichan.models.animeResponse.Characters.CharacterResponse;
import com.omnicoder.anichan.models.animeResponse.Staff.StaffResponse;
import com.omnicoder.anichan.models.animeResponse.videos.VideoResponse;
import com.omnicoder.anichan.models.jikan.CharacterDetailsResponse;
import com.omnicoder.anichan.models.jikan.ImageResponse;
import com.omnicoder.anichan.models.jikan.JikanUserResponse;
import com.omnicoder.anichan.models.jikan.PersonDetailsResponse;
import com.omnicoder.anichan.models.jikan.UserFriendResponse;
import com.omnicoder.anichan.models.jikan.ScheduleResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface JikanApi {

    @GET("anime/{id}/videos")
    Observable<VideoResponse> getVideos(@Path("id") int id);

    @GET("anime/{id}/characters")
    Observable<CharacterResponse> getCharacters(@Path("id") int id);

    @GET("anime/{id}/staff")
    Observable<StaffResponse> getStaff(@Path("id") int id);

    @GET("manga/{id}/characters")
    Observable<CharacterResponse> getMangaCharacters(@Path("id") int id);

    @GET("users/{userName}/full")
    Observable<JikanUserResponse> getUserInfo(@Path("userName") String username);

    @GET("users/{userName}/friends")
    Observable<UserFriendResponse> getUserFriends(@Path("userName") String username);

    @GET("/v4/schedules")
    Observable<ScheduleResponse> getSchedule(
            @Query("filter") String day,
            @Query("sfw") Boolean nsfw,
            @Query("kids") Boolean kids
    );

    @GET("/v4/schedules?page=1")
    Observable<ScheduleResponse> getScheduleWithPage(
            @Query("filter") String day,
            @Query("sfw") Boolean nsfw,
            @Query("kids") Boolean kids
    );


    @GET("characters/{id}/full")
    Observable<CharacterDetailsResponse> getCharactersDetails(@Path("id") int id);

    @GET("characters/{id}/pictures")
    Observable<ImageResponse> getCharacterImages(@Path("id") int id);


    @GET("people/{id}/full")
    Observable<PersonDetailsResponse> getPersonDetails(@Path("id") int id);

    @GET("people/{id}/pictures")
    Observable<ImageResponse> getPersonImages(@Path("id") int id);







}
