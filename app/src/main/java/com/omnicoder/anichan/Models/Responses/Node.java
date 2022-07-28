package com.omnicoder.anichan.Models.Responses;


import java.util.List;

public class Node {
    int id;
    float mean;
    String title,media_type;
    MainPicture main_picture;
    List<Genre> genres;
    List<MainPicture> pictures;



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

