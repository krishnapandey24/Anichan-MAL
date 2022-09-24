package com.omnicoder.anichan.models.jikan;

import com.squareup.moshi.Json;

public class JikanSubEntity {
    @Json(name = "image_url")
    String imageUrl;
    @Json(name = "mal_id")
    Integer malId;
    @Json(name = "name")
    String name;
    @Json(name = "url")
    String url;

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getMalId() {
        return malId;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
