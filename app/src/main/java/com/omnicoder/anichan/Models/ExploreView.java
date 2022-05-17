package com.omnicoder.anichan.Models;


public class ExploreView {
    int animeID;
    public String title,imageURL,format,mediaType;

    public ExploreView(int animeID, String title, String imageURL,String format,String mediaType) {
        this.animeID = animeID;
        this.title = title;
        this.imageURL = imageURL;
        this.format=format;
        this.mediaType=mediaType;
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



}
