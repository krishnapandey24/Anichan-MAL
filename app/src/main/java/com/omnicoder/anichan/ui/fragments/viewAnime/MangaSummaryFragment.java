package com.omnicoder.anichan.ui.fragments.viewAnime;

import static com.omnicoder.anichan.utils.AdsConstants.NATIVE_AD_UNIT_ID;
import static com.omnicoder.anichan.utils.Constants.VIEW_LESS;
import static com.omnicoder.anichan.utils.Constants.VIEW_MORE;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.omnicoder.anichan.adapters.recyclerViews.AllTimePopularAdapter;
import com.omnicoder.anichan.adapters.recyclerViews.RelatedAnimeAdapter;
import com.omnicoder.anichan.models.animeResponse.RelatedAnime;
import com.omnicoder.anichan.models.mangaResponse.Author;
import com.omnicoder.anichan.models.mangaResponse.Manga;
import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.databinding.FragmentMangaSummaryBinding;

import java.util.List;

public class MangaSummaryFragment extends Fragment {
    Manga manga;
    NativeAd nativeAd;
    FragmentMangaSummaryBinding binding;
    boolean viewMore=true;
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
        setRecommendations(manga.getRecommendations(),getContext());
        setRelatedAnime(manga.getRelated_manga(),getContext());
        binding.viewMore2.setOnClickListener(v -> {
            if(viewMore){
                binding.synonymsView.setMaxLines(15);
                binding.viewMore2.setText(VIEW_LESS);
            }else {
                binding.synonymsView.setMaxLines(3);
                binding.viewMore2.setText(VIEW_MORE);
            }
            viewMore=!viewMore;
        });
        initializeGoogleAdmob();
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

    private void initializeGoogleAdmob(){
        AdLoader adLoader = new AdLoader.Builder(requireContext(), NATIVE_AD_UNIT_ID).forNativeAd(nativeAd -> {
            if (requireActivity().isDestroyed()) {
                nativeAd.destroy();
                return;
            }

            if(this.nativeAd!=null){
                this.nativeAd.destroy();
            }
            this.nativeAd=nativeAd;
            binding.nativeAdView.setNativeAd(nativeAd);
        }).build();
        AdRequest nativeAdRequest = new AdRequest.Builder().build();
        adLoader.loadAd(nativeAdRequest);
        binding.nativeAdView.destroyNativeAd();
    }



    private void setRecommendations(List<Data> recommendations, Context context){
        if(recommendations.size()>0){
            AllTimePopularAdapter allTimePopularAdapter= new AllTimePopularAdapter(context,recommendations,false);
            binding.recommendationRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            binding.recommendationRv.setAdapter(allTimePopularAdapter);
        }else{
            binding.recommendations.setVisibility(View.GONE);
            binding.recommendationRv.setVisibility(View.GONE);
        }

    }

    private void setRelatedAnime(List<RelatedAnime> related_anime, Context context) {
        if(related_anime.size()>0){
            RelatedAnimeAdapter relatedAnimeAdapter= new RelatedAnimeAdapter(context,related_anime,false);
            binding.relatedRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            binding.relatedRv.setAdapter(relatedAnimeAdapter);
        }else{
            binding.related.setVisibility(View.GONE);
            binding.relatedRv.setVisibility(View.GONE);
        }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(nativeAd!=null){
            nativeAd.destroy();
        }
    }
}