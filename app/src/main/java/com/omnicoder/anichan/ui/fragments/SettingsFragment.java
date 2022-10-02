package com.omnicoder.anichan.ui.fragments;

import static com.omnicoder.anichan.utils.Constants.DARK_MODE_TAG;
import static com.omnicoder.anichan.utils.Constants.NSFW_TAG;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.omnicoder.anichan.R;


public class SettingsFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        setPreferencesFromResource(R.xml.app_preference,rootKey);
        SwitchPreferenceCompat nsfw=findPreference(NSFW_TAG);
        SwitchPreferenceCompat darkMode=findPreference(DARK_MODE_TAG);
        assert darkMode != null;
        assert nsfw !=null;
        nsfw.setOnPreferenceChangeListener((preference, newValue) -> {
            sharedPreferences.edit().putBoolean(NSFW_TAG,(boolean) newValue).apply();
            return true;
        });

        darkMode.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean value=(boolean) newValue;
            if(value){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            sharedPreferences.edit().putBoolean(DARK_MODE_TAG,value).apply();
            return true;
        });
    }
}