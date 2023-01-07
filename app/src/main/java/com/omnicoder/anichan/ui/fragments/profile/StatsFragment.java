package com.omnicoder.anichan.ui.fragments.profile;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnicoder.anichan.databinding.AnimeStatisticsBinding;
import com.omnicoder.anichan.databinding.FragmentStatsBinding;
import com.omnicoder.anichan.databinding.MangaStatisticsBinding;
import com.omnicoder.anichan.models.jikan.AnimeStats;
import com.omnicoder.anichan.models.jikan.JikanUserStatistic;
import com.omnicoder.anichan.models.jikan.MangaStats;

import java.util.ArrayList;
import java.util.List;

import app.futured.donut.DonutProgressView;
import app.futured.donut.DonutSection;

public class StatsFragment extends Fragment {
    JikanUserStatistic statistics;
    FragmentStatsBinding binding;
    AnimeStatisticsBinding animeBinding;
    MangaStatisticsBinding mangaBinding;
    public static final String watchingAndReadingColor="#78e08f";
    public static final String planToWatchAndReadColor="#9cafb5";
    public static final String completedColor="#6a89cc";
    public static final String onHoldColor="#fad390";
    public static final String droppedColor="#e55039";



    public StatsFragment(){}

    public StatsFragment(JikanUserStatistic statistics) {
        this.statistics=statistics;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentStatsBinding.inflate(inflater,container,false);
        animeBinding=binding.animeStatistics;
        mangaBinding=binding.mangaStatistics;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnimeStats anime=statistics.getAnime();
        MangaStats manga=statistics.getManga();
        setDonutProgressView(anime,manga);
        
        animeBinding.animeWatchingTextView.setText(String.valueOf(anime.getWatching()));
        animeBinding.animePlanToWatchTextView.setText(String.valueOf(anime.getPlanToWatch()));
        animeBinding.animeCompletedTextView.setText(String.valueOf(anime.getCompleted()));
        animeBinding.animeOnHoldTextView.setText(String.valueOf(anime.getOnHold()));
        animeBinding.animeDroppedTextView.setText(String.valueOf(anime.getDropped()));
        animeBinding.totalAnimeTextView.setText(String.valueOf(anime.getTotalEntries()));

        mangaBinding.mangaReadingTextView.setText(String.valueOf(manga.getReading()));
        mangaBinding.mangaPlanToReadTextView.setText(String.valueOf(manga.getPlanToRead()));
        mangaBinding.mangaCompletedTextView.setText(String.valueOf(manga.getCompleted()));
        mangaBinding.mangaOnHoldTextView.setText(String.valueOf(manga.getOnHold()));
        mangaBinding.mangaDroppedTextView.setText(String.valueOf(manga.getDropped()));
        mangaBinding.totalMangaTextView.setText(String.valueOf(manga.getTotalEntries()));

        animeBinding.daysView.setText(String.valueOf(anime.getDaysWatched()));
        animeBinding.episodesView.setText(String.valueOf(anime.getEpisodesWatched()));
        animeBinding.meanScoreView.setText(String.valueOf(anime.getMeanScore()));
        animeBinding.rewatchedView.setText(String.valueOf(anime.getRewatched()));

        mangaBinding.daysView.setText(String.valueOf(manga.getDaysRead()));
        mangaBinding.chaptersView.setText(String.valueOf(manga.getChaptersRead()));
        mangaBinding.meanScoreView.setText(String.valueOf(manga.getMeanScore()));
        mangaBinding.rereadView.setText(String.valueOf(manga.getReread()));

    }

    private void setDonutProgressView(AnimeStats anime, MangaStats manga) {

        DonutProgressView animeDonutProgressView=animeBinding.animeStatsPieChart;
        DonutProgressView mangaDonutProgressView=mangaBinding.mangaStatsPieChart;

        List<DonutSection> animeSections=new ArrayList<>();
        animeSections.add(new DonutSection("Watching", Color.parseColor(watchingAndReadingColor),anime.getWatchingFloat()));
        animeSections.add(new DonutSection("Plan To Watch", Color.parseColor(planToWatchAndReadColor),anime.getPlanToWatchFloat()));
        animeSections.add(new DonutSection("Completed", Color.parseColor(completedColor),anime.getCompletedFloat()));
        animeSections.add(new DonutSection("On Hold", Color.parseColor(onHoldColor),anime.getOnHoldFloat()));
        animeSections.add(new DonutSection("Dropped", Color.parseColor(watchingAndReadingColor),anime.getDroppedFloat()));

        List<DonutSection> mangaSections=new ArrayList<>();
        mangaSections.add(new DonutSection("Reading", Color.parseColor(watchingAndReadingColor),manga.getReadingFloat()));
        mangaSections.add(new DonutSection("Plan To Read", Color.parseColor(planToWatchAndReadColor),manga.getPlanToReadFloat()));
        mangaSections.add(new DonutSection("Completed", Color.parseColor(completedColor),manga.getCompletedFloat()));
        mangaSections.add(new DonutSection("On Hold", Color.parseColor(onHoldColor),manga.getOnHoldFloat()));
        mangaSections.add(new DonutSection("Dropped", Color.parseColor(droppedColor),manga.getDroppedFloat()));

        animeDonutProgressView.setCap(0.0f);
        animeDonutProgressView.submitData(animeSections);

        mangaDonutProgressView.setCap(0.0f);
        mangaDonutProgressView.submitData(mangaSections);

    }
}