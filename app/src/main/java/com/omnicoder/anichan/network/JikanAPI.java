package com.omnicoder.anichan.Network;

import com.omnicoder.anichan.Models.AnimeResponse.Characters.CharacterResponse;
import com.omnicoder.anichan.Models.AnimeResponse.Staff.StaffResponse;
import com.omnicoder.anichan.Models.AnimeResponse.videos.VideoResponse;
import com.omnicoder.anichan.Models.Schedule.Schedule;

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




}
