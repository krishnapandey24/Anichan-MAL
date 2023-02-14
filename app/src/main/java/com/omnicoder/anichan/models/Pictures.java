package com.omnicoder.anichan.models;

import com.omnicoder.anichan.models.responses.MainPicture;

import java.util.List;

public class Pictures {
    List<MainPicture> pictures;

    public Pictures(List<MainPicture> pictures) {
        this.pictures = pictures;
    }

    public List<MainPicture> getPictures() {
        return pictures;
    }
}
