package com.omnicoder.anichan.repositories;

import static com.omnicoder.anichan.utils.Constants.KIDS_TAG;
import static com.omnicoder.anichan.utils.Constants.NSFW_TAG;

import android.content.SharedPreferences;

import com.omnicoder.anichan.models.jikan.ScheduleResponse;
import com.omnicoder.anichan.network.JikanApi;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class ScheduleRepository {
    JikanApi jikanAPI;
    boolean nsfw,kids;



    @Inject
    ScheduleRepository(JikanApi jikanAPI, SharedPreferences sharedPreferences){
        this.jikanAPI=jikanAPI;
        this.nsfw=sharedPreferences.getBoolean(NSFW_TAG,false);
        this.nsfw=sharedPreferences.getBoolean(KIDS_TAG,false);
    }

    public Observable<ScheduleResponse> getAnimeSchedule(String day){
        return jikanAPI.getSchedule(day,nsfw,kids);
    }





}
