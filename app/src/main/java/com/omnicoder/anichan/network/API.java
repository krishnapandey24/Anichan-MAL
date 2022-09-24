package com.omnicoder.anichan.network;

import com.omnicoder.anichan.models.SearchResponse;
import com.omnicoder.anichan.models.ViewAnime;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @GET("search/multi")
    Single<SearchResponse> searchAnime(@Query("query") String searchQuery,@Query("page") int page, @Query("api_key") String api_key);

    @GET("{format}/{id}")
    Observable<ViewAnime> getAnimeDetails(@Path("format") String format,@Path("id") int id,@Query("api_key") String api_key,@Query("append_to_response") String extra);
}
