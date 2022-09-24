package com.omnicoder.anichan.Utils;

import androidx.room.TypeConverter;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;
import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.util.List;

public class GenresConverter{

    @TypeConverter
    public String fromArrayList(List<String> genres){
        return genres.toString();

    }





}
