package com.omnicoder.anichan.models.jikan;

import com.squareup.moshi.Json;

public class JikanUser {
    @Json(name = "about")
    String about;
    @Json(name = "anime_stats")
    AnimeStatus animeStats;
    @Json(name = "birthday")
    String birthday;
    @Json(name = "favorites")
    Favorites favorites;
    @Json(name = "gender")
    String gender;
    @Json(name = "image_url")
    String imageUrl;
    @Json(name = "joined")
    String joined;
    @Json(name = "last_online")
    String lastOnline;
    @Json(name = "location")
    String location;
    @Json(name = "manga_stats")
    MangaStats mangaStats;
    @Json(name = "url")
    String url;
    @Json(name = "user_id")
    Integer userId;
    @Json(name = "username")
    String username;

    public String getAbout() {
        return about;
    }

    public AnimeStatus getAnimeStats() {
        return animeStats;
    }

    public String getBirthday() {
        return birthday;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public String getGender() {
        return gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getJoined() {
        return joined;
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public String getLocation() {
        return location;
    }

    public MangaStats getMangaStats() {
        return mangaStats;
    }

    public String getUrl() {
        return url;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
