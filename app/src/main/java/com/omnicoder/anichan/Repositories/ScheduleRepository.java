package com.omnicoder.anichan.Repositories;

import com.omnicoder.anichan.Models.Schedule.Schedule;
import com.omnicoder.anichan.Network.JikanAPI;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class ScheduleRepository {
    JikanAPI jikanAPI;
    private static final String URL="https://api.jikan.moe/v3/schedule";
    @Inject
    ScheduleRepository(JikanAPI jikanAPI){
        this.jikanAPI=jikanAPI;
    }

    // TODO: 21-Sep-22 Migrate to V4 
    public Observable<Schedule> getAnimeSchedule(){
        return jikanAPI.getAnimeSchedule(URL);
    }





}
