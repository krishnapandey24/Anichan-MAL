package com.omnicoder.anichan.repositories;

import static com.omnicoder.anichan.utils.Constants.NSFW_TAG;

import android.content.SharedPreferences;

import com.omnicoder.anichan.models.jikan.Schedule;
import com.omnicoder.anichan.network.JikanAPI;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class ScheduleRepository {
    JikanAPI jikanAPI;
    private final String URL;
    @Inject
    ScheduleRepository(JikanAPI jikanAPI, SharedPreferences sharedPreferences){
        this.jikanAPI=jikanAPI;
        this.URL="https://api.jikan.moe/v4/schedule?sfw="+sharedPreferences.getBoolean(NSFW_TAG,false);
    }

    // TODO: 21-Sep-22 Migrate to V4
    public Observable<Schedule> getAnimeSchedule(){
        return jikanAPI.getAnimeSchedule(URL);
    }





}
