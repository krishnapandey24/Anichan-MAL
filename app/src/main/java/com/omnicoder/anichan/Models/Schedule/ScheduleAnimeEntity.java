package com.omnicoder.anichan.Models.Schedule;

import java.util.List;

public class ScheduleAnimeEntity {
    public Integer mal_id;
    public String url;
    public String title;
    public String image_url;
    public String synopsis;
    public Object type;
    public String airing_start;
    public Integer episodes;
    public Integer members;
    public List<JikanEntity> genres;
    public List<JikanEntity> themes;
    public List<JikanEntity> demographics;
    public String source;
    public List<JikanEntity> producers;
    public Double score;
    public List<Object> licensors;
    public Boolean r18;
    public Boolean kids;

    public Integer getMal_id() {
        return mal_id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Object getType() {
        return type;
    }

    public String getAiring_start() {
        return airing_start;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public Integer getMembers() {
        return members;
    }

    public List<JikanEntity> getGenres() {
        return genres;
    }



    public List<JikanEntity> getThemes() {
        return themes;
    }

    public List<JikanEntity> getDemographics() {
        return demographics;
    }

    public String getSource() {
        return source;
    }

    public List<JikanEntity> getProducers() {
        return producers;
    }

    public Double getScore() {
        return score;
    }

    public List<Object> getLicensors() {
        return licensors;
    }

    public Boolean isR18() {
        return r18;
    }

    public Boolean isKids() {
        return kids;
    }
}
