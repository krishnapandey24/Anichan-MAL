package com.omnicoder.anichan.repositories;

import com.omnicoder.anichan.models.AccessToken;
import com.omnicoder.anichan.network.MovieDB;
import com.omnicoder.anichan.utils.Constants;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class LoginRepository {
    MovieDB movieDB;

    @Inject
    public LoginRepository(MovieDB movieDB) {
        this.movieDB = movieDB;
    }

    public Observable<AccessToken> getAccessToken(String code, String codeVerified){
        String clientId= Constants.CLIENT_ID;
        String grantType= "authorization_code";
        return movieDB.getAccessToken(clientId,code,codeVerified,grantType);
    }
}
