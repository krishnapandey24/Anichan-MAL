package com.omnicoder.anichan.models.jikan;

import com.squareup.moshi.Json;

import java.util.List;

public class CharacterDetailsData {
    @Json(name = "mal_id")
    Integer id;
    JikanImage images;
    String url,name;
    @Json(name="name_kanji")
    String kanjiName;
    List<String> nicknames;
    Integer favorites;
    String about;
    List<CharacterAnime> anime;
    List<CharacterManga> manga;
    List<CharacterVoiceActor> voices;

    public List<CharacterVoiceActor> getVoices() {
        return voices;
    }

    public Integer getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getKanjiName() {
        return kanjiName;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public Integer getFavorites() {
        return favorites;
    }

    public String getAbout() {
        return about;
    }

    public List<CharacterAnime> getAnime() {
        return anime;
    }

    public List<CharacterManga> getManga() {
        return manga;
    }

    public JikanImage getImages() {
        return images;
    }
}
