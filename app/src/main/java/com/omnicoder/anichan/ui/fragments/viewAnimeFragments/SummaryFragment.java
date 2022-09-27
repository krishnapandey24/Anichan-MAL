package com.omnicoder.anichan.ui.fragments.viewAnimeFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.adapters.AllTimePopularAdapter;
import com.omnicoder.anichan.adapters.RelatedAnimeAdapter;
import com.omnicoder.anichan.adapters.VideoAdapter;
import com.omnicoder.anichan.di.BaseApplication;
import com.omnicoder.anichan.models.animeResponse.Anime;
import com.omnicoder.anichan.models.animeResponse.AnimeTheme;
import com.omnicoder.anichan.models.animeResponse.RelatedAnime;
import com.omnicoder.anichan.models.animeResponse.Studio;
import com.omnicoder.anichan.models.animeResponse.videos.Promo;
import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.ui.activities.ViewThemesActivity;
import com.omnicoder.anichan.viewModels.ViewAnimeViewModel;
import com.omnicoder.anichan.databinding.FragmentSummaryBinding;

import java.util.ArrayList;
import java.util.List;

public class SummaryFragment extends Fragment {
    Anime anime;
    FragmentSummaryBinding binding;
    boolean viewMore=true;
    final String viewMore2="View More";
    final String viewLess="View Less";
    private static final String FINISHED_AIRING="finished_airing";
    private static final String CURRENTLY_AIRING="currently_airing";
    Context context;
    ViewAnimeViewModel viewModel;

    public SummaryFragment(Anime anime, ViewAnimeViewModel viewModel){
        this.anime= anime;
        this.viewModel=viewModel;

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= FragmentSummaryBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context=getContext();
        viewModel.fetchVideos(anime.getId());
        observeData();
        binding.statusView.setText(getAiringStatus(anime.getStatus()));
        if(anime.getBroadcast()!=null){
            binding.broadcastView.setText(anime.getBroadcast().getBroadCast());
        }
        binding.japaneseView.setText(anime.getAlternateTitles().getJa());
        binding.synonymsView.setText(getAlternateTitles(anime.getAlternateTitles().getSynonyms()));
        binding.releaseDateView.setText(anime.getStart_date());
        binding.endDateView.setText(anime.getEnd_date());
        String episodeDuration=(anime.getAverage_episode_duration()/60)+" min";
        binding.episodeDurationView.setText(episodeDuration);
        binding.sourceView.setText(anime.getSource());
        try{
            binding.studiosView.setText(getStudios(anime.getStudios()));
        }catch (Exception e){
            e.printStackTrace();
        }
        setupOpeningAndEndingThemes(anime.getOpening_themes(),anime.getEnding_themes());
        setRecyclerViews(anime.getRelated_anime(),anime.getRecommendations(),getContext());
        binding.viewMore2.setOnClickListener(v -> {
            if(viewMore){
                binding.synonymsView.setMaxLines(15);
                binding.viewMore2.setText(viewLess);
            }else {
                binding.synonymsView.setMaxLines(3);
                binding.viewMore2.setText(viewMore2);
            }
            viewMore=!viewMore;
        });
    }

    private void setRecyclerViews(List<RelatedAnime> related_anime, List<Data> recommendations, Context context) {
        AllTimePopularAdapter allTimePopularAdapter= new AllTimePopularAdapter(context,recommendations,true);
        RelatedAnimeAdapter relatedAnimeAdapter= new RelatedAnimeAdapter(context,related_anime,true);
        binding.recommendationRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        binding.relatedRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        binding.recommendationRv.setAdapter(allTimePopularAdapter);
        binding.relatedRv.setAdapter(relatedAnimeAdapter);
    }

    private void setupOpeningAndEndingThemes(List<AnimeTheme> opening_themes, List<AnimeTheme> ending_themes) {
        View.OnClickListener onClickListener= v -> {
            if((opening_themes==null || opening_themes.isEmpty()) && (ending_themes==null || ending_themes.isEmpty())){
                Toast.makeText(context,"No opening or ending themes available",Toast.LENGTH_SHORT).show();
            }else{
                BaseApplication application= (BaseApplication) requireContext().getApplicationContext();
                ArrayList<List<AnimeTheme>> arrayList= new ArrayList<>();
                arrayList.add(opening_themes);
                arrayList.add(ending_themes);
                application.setAnimeThemes(arrayList);
                startActivity(new Intent(getContext(), ViewThemesActivity.class));
            }
        };
        binding.openingEndingTheme.setOnClickListener(onClickListener);
        binding.imageButton.setOnClickListener(onClickListener);
        binding.view7.setOnClickListener(onClickListener);
    }

    private void observeData() {
        viewModel.getVideos().observe(getViewLifecycleOwner(), this::loadVideos);
    }

    private StringBuilder getStudios(List<Studio> studios) {
        int size=studios.size()-1;
        StringBuilder studiosString = new StringBuilder();
        int i = 0;
        while (i < size - 1) {
            studiosString.append(studios.get(i).getName());
            studiosString.append(",");
            i++;
        }
        studiosString.append(studios.get(i).getName());
        return studiosString;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    private void loadVideos(List<Promo> videos){
        Log.d("tagg","Videos is null "+(videos==null));
        if(videos!=null){
            VideoAdapter adapter = new VideoAdapter(context, videos);
            RecyclerView recyclerView = binding.videoViewStub.inflate().findViewById(R.id.videoViewRv);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(adapter);
        }
    }
    private String getAiringStatus(String status){
        if(status.equals(FINISHED_AIRING))
            return "Finished Airing";
        else if(status.equals(CURRENTLY_AIRING))
            return "Currently Airing";
        else
            return "Not Yet Aired";
    }

    public StringBuilder getAlternateTitles(List<String> titles) {
        StringBuilder title = new StringBuilder();
        int size = titles.size();
        for (int i = 0; i < size; i++) {
            title.append(titles.get(i));
            title.append("\n");

        }
        if(size<4){
            binding.viewMore2.setVisibility(View.INVISIBLE);
        }
        return title;
    }


}