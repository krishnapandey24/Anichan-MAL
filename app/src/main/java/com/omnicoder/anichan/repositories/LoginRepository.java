package com.omnicoder.anichan.repositories;

import com.omnicoder.anichan.models.AccessToken;
import com.omnicoder.anichan.network.MalAuthApi;
import com.omnicoder.anichan.utils.Constants;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class LoginRepository {
    MalAuthApi malAuthApi;

    @Inject
    public LoginRepository(MalAuthApi malAuthApi) {
        this.malAuthApi = malAuthApi;
    }

    public Observable<AccessToken> getAccessToken(String code, String codeVerified){
        String clientId= Constants.CLIENT_ID;
        String grantType= "authorization_code";
        return malAuthApi.getAccessToken(clientId,code,codeVerified,grantType);
    }

    public Observable<AccessToken> refreshAccessToken(String refreshToken){
        return malAuthApi.refreshAccessToken(Constants.CLIENT_ID,refreshToken,Constants.REFRESH_GRANT_CODE);
    }
}
