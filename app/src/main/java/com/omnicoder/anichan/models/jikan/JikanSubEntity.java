package com.omnicoder.anichan.models.jikan;

import com.squareup.moshi.Json;

public class JikanSubEntity {
    JikanImage images;
    @Json(name = "mal_id")
    Integer malId;

    String title;
    String name;
    String url;
    String type;
    @Json(name = "title_english")
    String englishTitle;

    Pagination pagination;


    public JikanImage getImages() {
        return images;
    }

    public Integer getMalId() {
        return malId;
    }

    public String getTitle() {
        if(pagination==null || (englishTitle==null || englishTitle.equals(""))){
            return title==null ? name : title;
        }
        return englishTitle;
    }

    public String getUrl() {
        return url;
    }


    public String getType() {
        return type;
    }
}
