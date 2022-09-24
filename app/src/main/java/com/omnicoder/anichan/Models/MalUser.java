package com.omnicoder.anichan.Models;

public class User {
    Integer id;
    String name,gender,birthday,location,joined_at,picture,message,error;
    UserAnimeStatistic anime_statistics;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getLocation() {
        return location;
    }

    public String getJoined_at() {
        return joined_at;
    }

    public String getPicture() {
        return picture;
    }

    public UserAnimeStatistic getAnime_statistics() {
        return anime_statistics;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}
