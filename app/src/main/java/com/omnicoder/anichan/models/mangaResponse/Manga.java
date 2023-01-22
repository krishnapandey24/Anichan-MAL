package com.omnicoder.anichan.models.mangaResponse;

import com.omnicoder.anichan.models.animeResponse.AlternateTitles;
import com.omnicoder.anichan.models.animeResponse.RelatedAnime;
import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.models.responses.Genre;
import com.omnicoder.anichan.models.responses.MainPicture;

import java.util.List;

public class Manga {
    int id;
    String title;
    MainPicture main_picture;
    AlternateTitles alternative_titles;
    String start_date;
    String end_date;
    String synopsis;
    Float mean;
    int rank;
    int popularity;
    int num_list_users;
    int num_scoring_users;
    String nsfw;
    String created_at;
    String updated_at;
    String media_type;
    String status;
    List<Genre> genres;
    int num_volumes,num_chapters;
    List<Author> authors;
    List<MainPicture> pictures;
    String background;
    List<RelatedAnime> related_anime;
    List<RelatedAnime> related_manga;
    List<Data> recommendations;
    MangaListStatus my_list_status;

    public int getId() {
        return id;
    }

    public String getTitle() {
        if(alternative_titles==null || alternative_titles.getEn()==null || alternative_titles.getEn().equals("")){
            return title;
        }
        return alternative_titles.getEn();
    }

    public MainPicture getMainPicture() {
        return main_picture;
    }

    public AlternateTitles getAlternativeTitles() {
        return alternative_titles;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEndDate() {
        return end_date;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public float getMean() {
        return mean;
    }

    public int getRank() {
        return rank;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getNum_list_users() {
        return num_list_users;
    }

    public int getNum_scoring_users() {
        return num_scoring_users;
    }

    public String getNsfw() {
        return nsfw;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getMedia_type() {
        return media_type;
    }

    public String getStatus() {
        return status;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public int getNum_volumes() {
        return num_volumes;
    }

    public int getNum_chapters() {
        return num_chapters;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<MainPicture> getPictures() {
        return pictures;
    }

    public String getBackground() {
        return background;
    }

    public List<RelatedAnime> getRelated_anime() {
        return related_anime;
    }

    public List<RelatedAnime> getRelated_manga() {
        return related_manga;
    }

    public List<Data> getRecommendations() {
        return recommendations;
    }

    public MangaListStatus getMy_list_status() {
        return my_list_status;
    }

}

