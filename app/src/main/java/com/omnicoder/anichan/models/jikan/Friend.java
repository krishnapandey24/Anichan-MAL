package com.omnicoder.anichan.models.jikan;

import com.squareup.moshi.Json;

public class Friend {
    String friendsWith;
    @Json(name = "friends_since")
    String  friendsSince;
    @Json(name = "image_url")
    String  imageUrl;
    @Json(name = "last_online")
    String  lastOnline;
    @Json(name = "url")
    String  url;
    @Json(name = "username")
    String  username;

    public String getFriendsWith() {
        return friendsWith;
    }

    public String getFriendsSince() {
        return friendsSince;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public void setFriendsWith(String friendsWith) {
        this.friendsWith = friendsWith;
    }
}
