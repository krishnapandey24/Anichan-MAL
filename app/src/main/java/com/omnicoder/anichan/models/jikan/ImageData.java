package com.omnicoder.anichan.models.jikan;

import com.omnicoder.anichan.models.animeResponse.Characters.Jpg;

public class ImageData {
    Jpg jpg;

    public ImageData(Jpg jpg) {
        this.jpg = jpg;
    }

    public Jpg getJpg() {
        return jpg;
    }
}
