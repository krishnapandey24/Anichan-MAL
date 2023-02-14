package com.omnicoder.anichan.models.animeResponse.Characters;

public class Character {
    int mal_id;
    String url;
    CharacterImages images;
    String name;

    public int getMal_id() {
        return mal_id;
    }

    public String getUrl() {
        return url;
    }

    public CharacterImages getImages() {
        return images;
    }

    public String getName() {
        return name;
    }
}
