package com.omnicoder.anichan.models.animeResponse;

import com.omnicoder.anichan.models.animeResponse.videos.Video;
import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.models.responses.Genre;
import com.omnicoder.anichan.models.responses.MainPicture;

import java.util.List;

public class Anime {
    int id;
    String title;
    MainPicture main_picture;
    AlternateTitles alternative_titles;
    String start_date;
    String end_date;
    String synopsis;
    float mean;
    float rank;
    int popularity;
    int num_list_users;
    int num_scoring_users;
    String nsfw;
    String created_at;
    String updated_at;
    String media_type;
    String status;
    List<Genre> genres;
    int num_episodes;
    StartSeason start_season;
    Broadcast broadcast;
    String source;
    int average_episode_duration;
    String rating;
    List<MainPicture> pictures;
    String background;
    List<RelatedAnime> related_anime;
    List<RelatedAnime> related_manga;
    List<Data> recommendations;
    List<Studio> studios;
    AnimeStatistics statistics;
    List<AnimeTheme> opening_themes;
    List<AnimeTheme> ending_themes;
    List<Video> videos;
    AnimeListStatus my_list_status;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public MainPicture getMainPicture() {
        return main_picture;
    }

    public AlternateTitles getAlternateTitles() {
        return alternative_titles;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public float getMean() {
        return mean;
    }

    public float getRank() {
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

    public int getNum_episodes() {
        return num_episodes;
    }

    public StartSeason getStart_season() {
        return start_season;
    }

    public Broadcast getBroadcast() {
        return broadcast;
    }

    public String getSource() {
        return source;
    }

    public int getAverage_episode_duration() {
        return average_episode_duration;
    }

    public String getRating() {
        return rating;
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

    public List<Studio> getStudios() {
        return studios;
    }

    public AnimeStatistics getStatistics() {
        return statistics;
    }

    public List<AnimeTheme> getOpening_themes() {
        return opening_themes;
    }

    public List<AnimeTheme> getEnding_themes() {
        return ending_themes;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public AnimeListStatus getMy_list_status() {
        return my_list_status;
    }
}
