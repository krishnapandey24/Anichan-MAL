package com.omnicoder.anichan.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MalDateFormatter {
    public static String getMALDateFormat(String inputDate) {
        try{
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            Date date = inputFormat.parse(inputDate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
            if(date!=null){
                return outputFormat.format(date);
            }else{
                throw new NullPointerException("null date");
            }
        }catch (Exception e){
            return inputDate;
        }
    }
}
