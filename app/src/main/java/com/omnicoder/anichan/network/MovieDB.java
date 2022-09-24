package com.omnicoder.anichan.Network;

import com.omnicoder.anichan.Models.AccessToken;
import com.omnicoder.anichan.Models.SearchResponse;
import com.omnicoder.anichan.Models.ViewAnime;


import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDB {
    @FormUrlEncoded
    @POST("oauth2/token")
    Observable<AccessToken> getAccessToken(@Field(value = "client_id", encoded = true) String clientId, @Field(value = "code", encoded = true) String code, @Field(value = "code_verifier", encoded = true) String codeVerifier, @Field(value = "grant_type", encoded = true) String grantType);
}
