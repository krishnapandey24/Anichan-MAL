package com.omnicoder.anichan.repositories;

import com.omnicoder.anichan.models.jikan.JikanUser;
import com.omnicoder.anichan.network.JikanAPI;
import com.omnicoder.anichan.network.MalApi;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class ProfileRepository {
    MalApi malApi;
    JikanAPI jikanAPI;
    int nextFriendPage=1;


    @Inject
    public ProfileRepository(MalApi malApi, JikanAPI jikanAPI){
        this.malApi= malApi;
        this.jikanAPI=jikanAPI;
    }

    public Observable<JikanUser> getUserInfoFromJikan(String username){
        return jikanAPI.getUserInfo(username);
    }













}
