package com.omnicoder.anichan.models;

import com.omnicoder.anichan.database.UserAnime;

import java.util.List;

public class AllAnime {
    private List<UserAnime> UserANIMES;

    public AllAnime(List<UserAnime> UserANIMES) {
        this.UserANIMES = UserANIMES;
    }

    public List<UserAnime> getAllAnime() {
        return UserANIMES;
    }

    public void setAllAnime(List<UserAnime> UserANIMES) {
        this.UserANIMES = UserANIMES;
    }
}
