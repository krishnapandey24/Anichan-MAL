package com.omnicoder.anichan.Repositories;

import com.omnicoder.anichan.Models.AccessToken;
import com.omnicoder.anichan.Network.MovieDB;
import com.omnicoder.anichan.Utils.Constants;

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
