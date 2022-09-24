package com.omnicoder.anichan.models;

public class ExplorePlainView{
    int animeID;
    public String title,imageURL,format,mediaType,genres,movieDBRating;

    public ExplorePlainView(int animeID,String title, String imageURL, String format, String mediaType, String genres, String movieDBRating){
        this.animeID = animeID;
        this.title = title;
        this.imageURL = imageURL;
        this.format=format;
        this.mediaType=mediaType;
        this.genres=genres;
        this.mediaType=mediaType;
        this.movieDBRating=movieDBRating;
    }

    public int getAnimeID() {
        return animeID;
    }

    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getFormat(){
        if(format!=null){
            return format.toLowerCase();
        }
        return "tv";
    }

    public String getGenres(){
        return genres;
    }

    public String getMovieDBRating(){
        return movieDBRating;
    }



}
