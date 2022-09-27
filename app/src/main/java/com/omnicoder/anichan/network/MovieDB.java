package com.omnicoder.anichan.network;

import com.omnicoder.anichan.models.AccessToken;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MovieDB {
    @FormUrlEncoded
    @POST("oauth2/token")
    Observable<AccessToken> getAccessToken(@Field(value = "client_id", encoded = true) String clientId, @Field(value = "code", encoded = true) String code, @Field(value = "code_verifier", encoded = true) String codeVerifier, @Field(value = "grant_type", encoded = true) String grantType);
}