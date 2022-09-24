package com.omnicoder.anichan.utils;

import android.util.Log;

import java.util.Calendar;

import javax.inject.Inject;

public class Years {

    @Inject
    public Years(){
        setYearAndSeason();
    }

    public int PREVIOUS_YEAR;
    public int CURRENT_YEAR;
    public int NEXT_YEAR;
    public String CURRENT_SEASON;
    public String NEXT_SEASON;


    public void setYearAndSeason(){
        CURRENT_YEAR= Calendar.getInstance().get(Calendar.YEAR);
        PREVIOUS_YEAR=CURRENT_YEAR-1;
        NEXT_YEAR=CURRENT_YEAR+1;

        int month=Calendar.getInstance().get(Calendar.MONTH)+1;
        Log.d("","Month"+month);
        if(month==1 || month==2 || month ==3){
            CURRENT_SEASON="Winter";
            NEXT_SEASON="Spring";
        }
        else if (month==4 || month==5 || month==6){
            CURRENT_SEASON="Spring";
            NEXT_SEASON="Summer";
        }
        else if (month==7 || month==8 || month==9){
            CURRENT_SEASON="Summer";
            NEXT_SEASON="Fall";
        }
        else {
            CURRENT_SEASON="Fall";
            NEXT_SEASON="Winter";
        }
        Log.d("Years","Current"+CURRENT_SEASON+"Next"+NEXT_SEASON);


    }

    public int[] getYears(){
        return new int[]{PREVIOUS_YEAR,CURRENT_YEAR,NEXT_YEAR};
    }

    public String[] getSeason(){
        return new String[]{CURRENT_SEASON,NEXT_SEASON};
    }

    public String getNEXT_SEASON(){
        return NEXT_SEASON;
    }



}
