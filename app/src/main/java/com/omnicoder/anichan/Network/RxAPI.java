package com.omnicoder.anichan.Network;


import com.omnicoder.anichan.APIResponse;
import com.omnicoder.anichan.Models.AllAnime;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface RxAPI {E
    @GET("trendinganime")
    Observable<APIResponse> getTrending();

    @GET("anime")
    Observable<AllAnime> getAllAnime();







}
