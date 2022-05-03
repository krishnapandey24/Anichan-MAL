package com.omnicoder.anichan.Models;


import android.util.Log;

public class Season {
    String air_date,name,overview,poster_path;
    Integer episode_count,id,season_number;
    String airYear;

    public Season(String air_date, String name, String overview, String poster_path, Integer episode_count, Integer id, Integer season_number) {
        this.air_date = air_date;
        this.name = name;
        this.overview = overview;
        this.poster_path = poster_path;
        this.episode_count = episode_count;
        this.id = id;
        this.season_number = season_number;
    }

    public String getAir_date() {
        if(air_date==null){
            airYear="";
            return "";
        }
        airYear=air_date.substring(0,4);
        return air_date;
    }

    public String getAirYear(){
        return airYear;
    }



    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getEpisode_count() {
        if(episode_count==null){
            return "";
        }
        return episode_count.toString();
    }

    public int getEpisode_count2() {
        if(episode_count==null){
            return 0;
        }
        return episode_count;
    }

    public int getEpisodeCount() {
        return episode_count;
    }

    public void setEpisode_count(Integer episode_count) {
        this.episode_count = episode_count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeason_number() {
        return season_number;
    }

    public void setSeason_number(Integer season_number) {
        this.season_number = season_number;
    }
}
