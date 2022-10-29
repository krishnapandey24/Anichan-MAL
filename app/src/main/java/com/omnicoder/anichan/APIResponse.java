package com.omnicoder.anichan;

import android.content.Intent;

import com.omnicoder.anichan.models.ExploreView;
import com.omnicoder.anichan.models.jikan.CharacterAnime;
import com.omnicoder.anichan.models.jikan.JikanSubEntity;
import com.omnicoder.anichan.models.jikan.PersonAnime;
import com.omnicoder.anichan.ui.activities.ViewAnimeActivity;
import com.omnicoder.anichan.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class APIResponse {
    private List<ExploreView> trending;

    public APIResponse(List<ExploreView> trending) {
        this.trending = trending;
    }

    public List<ExploreView> getResults() {
        return trending;
    }

    public void setResults(List<ExploreView> trending) {
        this.trending = trending;
    }


    public void setViews() {

    }
}

