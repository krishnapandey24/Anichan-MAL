package com.omnicoder.anichan.models.jikan;

import com.squareup.moshi.Json;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;


public class Favorites implements Parcelable {
    @Json(name = "anime")
    List<JikanSubEntity> anime;
    @Json(name = "characters")
    List<JikanSubEntity> characters;
    @Json(name = "manga")
    List<JikanSubEntity> manga;
    @Json(name = "people")
    List<JikanSubEntity> people;

    protected Favorites(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Favorites> CREATOR = new Creator<Favorites>() {
        @Override
        public Favorites createFromParcel(Parcel in) {
            return new Favorites(in);
        }

        @Override
        public Favorites[] newArray(int size) {
            return new Favorites[size];
        }
    };

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
