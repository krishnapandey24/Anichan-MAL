package com.omnicoder.anichan.models.jikan;

import com.squareup.moshi.Json;

import java.util.List;

public class Favorites {
    @Json(name = "anime")
    List<JikanSubEntity> anime;
    @Json(name = "characters")
    List<JikanSubEntity> characters;
    @Json(name = "manga")
    List<JikanSubEntity> manga;
    @Json(name = "people")
    List<JikanSubEntity> people;

    public List<JikanSubEntity> getAnime() {
        return anime;
    }

    public List<JikanSubEntity> getCharacters() {
        return characters;
    }

    public List<JikanSubEntity> getManga() {
        return manga;
    }

    public List<JikanSubEntity> getPeople() {
        return people;
    }
}
