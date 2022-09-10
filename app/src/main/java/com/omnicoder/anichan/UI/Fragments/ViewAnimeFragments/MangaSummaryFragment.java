package com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.omnicoder.anichan.Models.MangaResponse.Author;
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
    private static final String CURRENTLY_PUBLISHING="finished_airing";
    private static final String NOT_YET_PUBLISHED="currently_airing";
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
        binding.statusView.setText(getPublishingStatus(manga.getStatus()));
        binding.authorsView.setText(getAuthors(manga.getAuthors()));
        binding.japaneseView.setText(manga.getAlternativeTitles().getJa());
        binding.synonymsView.setText(getAlternateTitles(manga.getAlternativeTitles().getSynonyms()));
        binding.startView.setText(manga.getStart_date());
        binding.endView.setText(manga.getEndDate());
        String volumes= manga.getNum_volumes() == 0 ? "Unknown" : String.valueOf(manga.getNum_volumes());
        String chapters= manga.getNum_chapters() == 0 ? "Unknown" : String.valueOf(manga.getNum_chapters());
        binding.volumesView.setText(volumes);
        binding.chaptersView.setText(chapters);
        setRecyclerViews(manga.getRelated_manga(),manga.getRecommendations(),getContext());
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

    private String getAuthors(List<Author> authors) {
        StringBuilder stringBuilder=new StringBuilder();
        for(Author author: authors){
            String firstName=author.getNode().getFirst_name();
            String lastName=author.getNode().getLast_name();
            stringBuilder.append(firstName).append(" ").append(lastName).append(", ");
        }
        String name=stringBuilder.toString();
        return name.substring(0,name.length()-2);
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

    private String getPublishingStatus(String status){
        if(status.equals(CURRENTLY_PUBLISHING))
            return "Currently Publishing";
        else if(status.equals(NOT_YET_PUBLISHED))
            return "Not Yet Published";
        else
            return "Finished";
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