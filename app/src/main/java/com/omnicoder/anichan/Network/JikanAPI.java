package com.omnicoder.anichan.Network;

import com.omnicoder.anichan.Models.AnimeResponse.videos.VideoResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JikanAPI {

    @GET("anime/{id}/videos")
    Observable<VideoResponse> getVideos(@Path("id") int id);

}
