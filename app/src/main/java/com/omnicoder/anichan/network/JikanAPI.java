package com.omnicoder.anichan.network;

import com.omnicoder.anichan.models.animeResponse.Characters.CharacterResponse;
import com.omnicoder.anichan.models.animeResponse.Staff.StaffResponse;
import com.omnicoder.anichan.models.animeResponse.videos.VideoResponse;
import com.omnicoder.anichan.models.jikan.JikanUser;
import com.omnicoder.anichan.models.jikan.Schedule;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface JikanAPI {

    @GET("anime/{id}/videos")
    Observable<VideoResponse> getVideos(@Path("id") int id);

    @GET("anime/{id}/characters")
    Observable<CharacterResponse> getCharacters(@Path("id") int id);

    @GET("anime/{id}/staff")
    Observable<StaffResponse> getStaff(@Path("id") int id);

    @GET("manga/{id}/characters")
    Observable<CharacterResponse> getMangaCharacters(@Path("id") int id);

    @GET
    Observable<Schedule> getAnimeSchedule(@Url String url);

    @GET("user/{userName}/")
    Observable<JikanUser> getUserInfo(@Path("userName") String username);

}
