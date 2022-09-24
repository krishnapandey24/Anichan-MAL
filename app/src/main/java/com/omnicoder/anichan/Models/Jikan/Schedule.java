package com.omnicoder.anichan.Models.Schedule;

import java.util.List;

public class Schedule {
    List<ScheduleAnimeEntity> monday;
    List<ScheduleAnimeEntity> tuesday;
    List<ScheduleAnimeEntity> wednesday;
    List<ScheduleAnimeEntity> thursday;
    List<ScheduleAnimeEntity> friday;
    List<ScheduleAnimeEntity> saturday;
    List<ScheduleAnimeEntity> sunday;
    List<ScheduleAnimeEntity> other;
    List<ScheduleAnimeEntity> unknown;

    public List<ScheduleAnimeEntity> getMonday() {
        return monday;
    }

    public List<ScheduleAnimeEntity> getTuesday() {
        return tuesday;
    }

    public List<ScheduleAnimeEntity> getWednesday() {
        return wednesday;
    }

    public List<ScheduleAnimeEntity> getThursday() {
        return thursday;
    }

    public List<ScheduleAnimeEntity> getFriday() {
        return friday;
    }

    public List<ScheduleAnimeEntity> getSaturday() {
        return saturday;
    }

    public List<ScheduleAnimeEntity> getSunday() {
        return sunday;
    }

    public List<ScheduleAnimeEntity> getOther() {
        return other;
    }

    public List<ScheduleAnimeEntity> getUnknown() {
        return unknown;
    }
}
