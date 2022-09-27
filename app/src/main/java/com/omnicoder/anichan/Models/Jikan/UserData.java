package com.omnicoder.anichan.models.jikan;

import com.squareup.moshi.Json;

public class UserData {
    private static final String ifNull="---";
    @Json(name = "mal_id")
    Integer id;
    String username,url,birthday,location,joined;
    JikanImage images;
    JikanUserStatistic statistics;
    Favorites favorites;
    String about;

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getLocation() {
        return location==null ? ifNull : location;
    }

    public String getJoined() {
        return joined;
    }

    public JikanImage getImages() {
        return images;
    }

    public JikanUserStatistic getStatistics() {
        return statistics;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public String getAbout() {
        return about;
    }
}
