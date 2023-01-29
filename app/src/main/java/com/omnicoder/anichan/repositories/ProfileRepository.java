package com.omnicoder.anichan.repositories;

import com.omnicoder.anichan.models.jikan.JikanUserResponse;
import com.omnicoder.anichan.models.jikan.UserFriendResponse;
import com.omnicoder.anichan.network.JikanApi;
import com.omnicoder.anichan.network.MalApi;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class ProfileRepository {
    MalApi malApi;
    JikanApi jikanAPI;

    @Inject
    public ProfileRepository(MalApi malApi, JikanApi jikanAPI){
        this.malApi= malApi;
        this.jikanAPI=jikanAPI;
    }

    public Observable<JikanUserResponse> getUserInfoFromJikan(String username){
        return jikanAPI.getUserInfo(username);
    }

    public Observable<UserFriendResponse> getUserFriends(String username){
        return jikanAPI.getUserFriends(username);
    }


















}
