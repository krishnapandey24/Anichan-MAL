package com.omnicoder.anichan.models.animeResponse;

public class Broadcast {
    String day_of_the_week;
    String start_time;

    public String getBroadCast(){
        return day_of_the_week + " at " +start_time + "(JST)";
    }

    // TODO: 22-01-2023 Convert to other timezones


}
