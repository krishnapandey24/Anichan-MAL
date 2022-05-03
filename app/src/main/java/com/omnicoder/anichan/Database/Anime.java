package com.omnicoder.anichan.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ANIME")
public class Anime {
    public static final String ANIME_ID="animeID";
    public static final String TITLE="title";
    public static final String IMAGE_URL="imageURL";
    public static final String GENRES="genres";
    public static final String DURATION="episodes";
    public static final String STATUS="status";
    public static final String SEASON="season";
    public static final String FORMAT="format";
    public static final String MEDIA_TYPE="mediaType";
    public static final String RATING= "rating";
    public static final String MOVIE_DB_RATING= "movieDBRating";
    public static final String BACKGROUND_POSTER="backgroundPoster";

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo(name = "rating")
    private Integer averageScore;
    private String format,mediaType,genres,backgroundPoster;
    private Integer animeID,trending,popularity,episodes;
    private String title,status,season,releaseDate,movieDBRating;
    @ColumnInfo(name = "imageURL")
    private String imageURL;


    public Anime(Integer id, Integer animeID, Integer trending, Integer averageScore, String mediaType, String genres, Integer popularity, Integer episodes, String title, String status, String season, String imageURL, String format, String releaseDate, String movieDBRating, String backgroundPoster) {
        this.id = id;
        this.animeID = animeID;
        this.trending = trending;
        this.averageScore = averageScore;
        this.mediaType = mediaType;
        this.genres = genres;
        this.popularity = popularity;
        this.episodes = episodes;
        this.title = title;
        this.status = status;
        this.season = season;
        this.imageURL = imageURL;
        this.format=format;
        this.releaseDate=releaseDate;
        this.movieDBRating=movieDBRating;
        this.backgroundPoster=backgroundPoster;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnimeID() {
        return animeID;
    }

    public void setAnimeID(Integer animeID) {
        this.animeID = animeID;
    }

    public Integer getTrending() {
        return trending;
    }

    public void setTrending(Integer trending) {
        this.trending = trending;
    }

    public Integer getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Integer averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMovieDBRating() {
        return movieDBRating;
    }

    public void setMovieDBRating(String rating) {
        this.movieDBRating = rating;
    }

    public String getBackgroundPoster() {
        return backgroundPoster;
    }

    public void setBackgroundPoster(String backgroundPoster) {
        this.backgroundPoster = backgroundPoster;
    }
}

