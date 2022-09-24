package com.omnicoder.anichan.Utils;

import com.omnicoder.anichan.Models.Responses.Data;

import java.util.Comparator;

public class SearchComparator implements Comparator<Data> {
    @Override
    public int compare(Data o1, Data o2) {
        return Integer.compare(o1.getNode().getTitle().length(),o2.getNode().getTitle().length());
    }
}
