package com.omnicoder.anichan.models.animeResponse;


import java.util.List;


public class AlternateTitles {
    List<String> synonyms;
    String en;
    String ja;

    public List<String> getSynonyms() {
        return synonyms;
    }

    public String getEn() {
        return en;
    }

    public String getJa() {
        return ja;
    }
}
