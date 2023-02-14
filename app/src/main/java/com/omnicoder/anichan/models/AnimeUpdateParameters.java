package com.omnicoder.anichan.models;

import java.util.List;

public class AnimeUpdateParameters{
    String status;
    Boolean is_rewatching;
    int score;
    int priority;
    int num_times_rewatched;
    int rewatch_value;
    List<String> tags;
    String comments;
    String start_date;
    String finish_date;
    int num_watched_episodes;
}
