package com.omnicoder.anichan.utils;

public class Constants {

    public static final String DATABASE_NAME="AnimeDB";
    public static final String API_KEY="9f2173dc79c7e04fdce1f945b4e8ff80";
    public static final String IMAGE_LINK= "https://image.tmdb.org/t/p/w500/";
    public static final String IMAGE_URL= "https://image.tmdb.org/t/p/w500/";
    public static final String ANICHAN_PAGE_LINK = "anichan://anichan.page.link/";
    public static final String CLIENT_ID="48b4816727db6871c4f7bd68ce8a8233";
    public static final String LOGIN_URL="https://myanimelist.net/v1/oauth2/authorize?response_type=code&client_id=%s&code_challenge=%s&state=%s";
    public static final int LIMIT=20;
    public static final int USER_LIST_LIMIT=50;
    public static final int OFFSET=0;
    public static final int SEARCH_LIMIT=10;
    public static final String RANKING_FIELDS="media_type,mean,genres";
    public static final String AIRING="airing";
    public static final String UPCOMING="upcoming";
    public static final String POPULAR="bypopularity";
    public static final String SEASON_SORT="anime_num_list_users";
    public static final String MOVIE="movie";
    public static final String MANGA_DETAILS_FIELDS="id,title,main_picture,synopsis,alternative_titles,start_date,end_date,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status{start_date,finish_date},num_volumes,num_chapters,authors{first_name,last_name},pictures,background,related_anime,related_manga,recommendations";
    public static final String ANIME_DETAILS_FIELDS="id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_episodes,start_season,broadcast,source,average_episode_duration,rating,pictures,background,related_anime,related_manga,recommendations,studios,statistics,opening_themes,ending_themes,my_list_status{start_date,finish_date}";
    public static final String USERNAME="username";

    public static final String WATCHING="watching";
    public static final String READING="reading";
    public static final String PLAN_TO_WATCH="plan_to_watch";
    public static final String PLAN_TO_READ="plan_to_read";
    public static final String COMPLETED= "completed";
    public static final String ON_HOLD= "on_hold";
    public static final String DROPPED="dropped";

    public static final String JIKAN_API_FRIENDS_URL="https://api.jikan.moe/v4/users/%s/friends/%s";




    public static final String ANIME="anime";
    public static final String MANGA="manga";
    public static final String CHARACTERS="characters";
    public static final String PEOPLE="people";


    public static final String FAVORITES_TYPE="favorites_type";





}
