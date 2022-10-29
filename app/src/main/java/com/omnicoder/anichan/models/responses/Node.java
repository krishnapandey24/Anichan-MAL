package com.omnicoder.anichan.models.responses;


import java.util.List;

public class Node {
    int id;
    float mean;
    Integer popularity;
    String title,media_type;
    MainPicture main_picture;
    List<Genre> genres;
    List<MainPicture> pictures;


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
        return title;
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

