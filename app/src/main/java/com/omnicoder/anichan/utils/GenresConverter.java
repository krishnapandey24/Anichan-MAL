package com.omnicoder.anichan.utils;

import androidx.room.TypeConverter;

import java.util.List;

public class GenresConverter{

    @TypeConverter
    public String fromArrayList(List<String> genres){
        return genres.toString();

    }





}
