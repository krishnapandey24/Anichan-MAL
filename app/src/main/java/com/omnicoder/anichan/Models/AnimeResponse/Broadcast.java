package com.omnicoder.anichan.models.animeResponse;

public class Broadcast {
    String day_of_the_week;
    String start_time;

    public String getDay_of_the_week() {
        return day_of_the_week;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getBroadCast(){
        return day_of_the_week + " at " +start_time + "(JST)";
    }


}
