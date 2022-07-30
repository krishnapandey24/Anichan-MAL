package com.omnicoder.anichan.Models;

import com.omnicoder.anichan.Database.UserAnime;

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
