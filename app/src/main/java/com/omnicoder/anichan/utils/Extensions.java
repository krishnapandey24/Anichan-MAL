package com.omnicoder.anichan.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.omnicoder.anichan.R;

public class Extensions {
    private static Extensions extensions;
    public static Extensions getInstance(){
        if(extensions==null){
            extensions= new Extensions();
        }
        return extensions;
    }

    public void openCustomTab(String url, Context context){
        CustomTabColorSchemeParams colors= new CustomTabColorSchemeParams.Builder().setToolbarColor(ContextCompat.getColor(context, R.color.blue)).build();
        CustomTabsIntent customTabsIntent= new CustomTabsIntent.Builder().setDefaultColorSchemeParams(colors).build();
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }




}
