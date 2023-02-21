package com.omnicoder.anichan.ui.fragments;

import static com.omnicoder.anichan.utils.Constants.ANIME_JAPANESE_TITLES;
import static com.omnicoder.anichan.utils.Constants.DARK_MODE_TAG;
import static com.omnicoder.anichan.utils.Constants.KIDS_TAG;
import static com.omnicoder.anichan.utils.Constants.MANGA_JAPANESE_TITLES;
import static com.omnicoder.anichan.utils.Constants.NSFW_TAG;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.omnicoder.anichan.R;



public class SettingsFragment extends PreferenceFragmentCompat {
    View view;

    OnBackPressedCallback callback;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        view=getView();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        setPreferencesFromResource(R.xml.app_preference,rootKey);
        SwitchPreferenceCompat nsfw=findPreference(NSFW_TAG);
        SwitchPreferenceCompat darkMode=findPreference(DARK_MODE_TAG);
        SwitchPreferenceCompat kidsInSchedule=findPreference(KIDS_TAG);
        SwitchPreferenceCompat animeJapaneseTitles=findPreference(ANIME_JAPANESE_TITLES);
        SwitchPreferenceCompat mangaJapaneseTitles=findPreference(MANGA_JAPANESE_TITLES);
        assert darkMode != null;
        assert nsfw !=null;
        assert kidsInSchedule !=null;
        assert animeJapaneseTitles !=null;
        assert mangaJapaneseTitles !=null;
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

        kidsInSchedule.setOnPreferenceChangeListener((preference, newValue) -> {
            sharedPreferences.edit().putBoolean(KIDS_TAG,(boolean) newValue).apply();
            return true;
        });

        animeJapaneseTitles.setOnPreferenceChangeListener((preference, newValue) -> {
            sharedPreferences.edit().putBoolean(ANIME_JAPANESE_TITLES,(boolean) newValue).apply();
            return true;
        });

        mangaJapaneseTitles.setOnPreferenceChangeListener((preference, newValue) -> {
            sharedPreferences.edit().putBoolean(MANGA_JAPANESE_TITLES,(boolean) newValue).apply();
            return true;
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        if(callback==null){
            callback= new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    setEnabled(true);
                    Navigation.findNavController(requireView()).navigateUp();

                }
            };
            requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(),callback);
        }
        callback.setEnabled(true);
    }


    @Override
    public void onPause() {
        super.onPause();
        callback.setEnabled(false);
    }

}