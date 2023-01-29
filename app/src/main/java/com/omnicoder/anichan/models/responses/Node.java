package com.omnicoder.anichan.models.responses;

import com.omnicoder.anichan.models.animeResponse.AlternateTitles;

import java.util.List;

public class Node {
    int id;
    float mean;
    Integer popularity;
    String title,media_type;
    MainPicture main_picture;
    List<Genre> genres;
    List<MainPicture> pictures;
    AlternateTitles alternative_titles;
    Integer num_scoring_users;



    public Integer getPopularity() {
        return popularity;
    }

    public int getId() {
        return id;
    }

    public float getMean() {
        return mean;
    }

    public String getTitle() {
        if((num_scoring_users==null) || (alternative_titles==null || alternative_titles.getEn()==null || alternative_titles.getEn().equals(""))){
            return title;
        }
        return alternative_titles.getEn();
    }

    public String getMedia_type() {
        return media_type;
    }

    public MainPicture getMainPicture() {
        return main_picture;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<MainPicture> getPictures() {
        return pictures;
    }
}

