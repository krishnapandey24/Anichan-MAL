package com.omnicoder.anichan.models.animeResponse.Characters;

import com.omnicoder.anichan.models.jikan.JikanImage;
import com.omnicoder.anichan.models.jikan.PersonAnime;
import com.omnicoder.anichan.models.jikan.PersonManga;
import com.omnicoder.anichan.models.jikan.VoiceActingRole;
import com.squareup.moshi.Json;

import java.util.List;

public class Person {
    @Json(name = "mal_id")
    Integer malId;
    JikanImage images;
    Integer favorites;
    String url,name,birthday,about;
    @Json(name = "given_name")
    String givenName;
    @Json(name = "family_name")
    String familyName;
    List<PersonAnime> anime;
    List<PersonManga> manga;
    List<VoiceActingRole> voices;

    public Integer getMalId() {
        return malId;
    }

    public Integer getFavorites() {
        return favorites;
    }

    public JikanImage getImages() {
        return images;
    }


    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAbout() {
        return about;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public List<PersonAnime> getAnime() {
        return anime;
    }

    public List<PersonManga> getManga() {
        return manga;
    }

    public List<VoiceActingRole> getVoices() {
        return voices;
    }
}

