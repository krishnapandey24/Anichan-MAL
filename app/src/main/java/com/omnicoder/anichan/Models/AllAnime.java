package com.omnicoder.anichan.Models;

import com.omnicoder.anichan.Database.Anime;

import java.util.List;

public class AllAnime {
    private List<Anime> ANIME;

    public AllAnime(List<Anime> ANIME) {
        this.ANIME = ANIME;
    }

    public List<Anime> getAllAnime() {
        return ANIME;
    }

    public void setAllAnime(List<Anime> ANIME) {
        this.ANIME = ANIME;
    }
}
