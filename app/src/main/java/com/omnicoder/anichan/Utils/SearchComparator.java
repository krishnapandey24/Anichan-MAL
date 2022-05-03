package com.omnicoder.anichan.Utils;

import com.omnicoder.anichan.Models.Animes;

import java.util.Comparator;

public class SearchComparator implements Comparator<Animes> {
    @Override
    public int compare(Animes o1, Animes o2) {
        return Integer.compare(o1.getTitle().length(),o2.getTitle().length());
//        return Float.compare(o2.getPopularity(),o1.getPopularity());
    }
}
