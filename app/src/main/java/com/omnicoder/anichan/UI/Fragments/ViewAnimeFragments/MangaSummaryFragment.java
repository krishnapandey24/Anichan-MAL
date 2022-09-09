package com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omnicoder.anichan.Adapters.AllTimePopularAdapter;
import com.omnicoder.anichan.Adapters.RelatedAnimeAdapter;
import com.omnicoder.anichan.Models.AnimeResponse.RelatedAnime;
import com.omnicoder.anichan.Models.MangaResponse.Manga;
import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.databinding.FragmentMangaSummaryBinding;

import java.util.List;

public class MangaSummaryFragment extends Fragment {
    Manga manga;
    FragmentMangaSummaryBinding binding;
    boolean viewMore=true;
    final String viewMore2="View More";
    final String viewLess="View Less";
    private static final String FINISHED_AIRING="finished_airing";
    private static final String CURRENTLY_AIRING="currently_airing";
    Context context;

    public MangaSummaryFragment(Manga manga){
        this.manga= manga;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= FragmentMangaSummaryBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context=getContext();
        binding.statusView.setText(manga.getStatus());
        binding.authors.setText(manga.getAuthors().toString().replace("[","").replace("]",""));
        binding.japaneseView.setText(manga.getAlternativeTitles().getJa());
        binding.synonymsView.setText(getAlternateTitles(manga.getAlternativeTitles().getSynonyms()));
        binding.releaseDateView.setText(manga.getStart_date());
        binding.endDateView.setText(manga.getEndDate());
        setRecyclerViews(manga.getRelated_anime(),manga.getRecommendations(),getContext());
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
        AllTimePopularAdapter allTimePopularAdapter= new AllTimePopularAdapter(context,recommendations);
        RelatedAnimeAdapter relatedAnimeAdapter= new RelatedAnimeAdapter(context,related_anime);
        binding.recommendationRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        binding.relatedRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        binding.recommendationRv.setAdapter(allTimePopularAdapter);
        binding.relatedRv.setAdapter(relatedAnimeAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
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