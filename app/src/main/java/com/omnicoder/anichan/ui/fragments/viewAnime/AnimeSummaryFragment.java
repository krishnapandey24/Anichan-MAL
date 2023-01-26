package com.omnicoder.anichan.ui.fragments.viewAnime;

import static com.omnicoder.anichan.utils.AdsConstants.NATIVE_AD_UNIT_ID;
import static com.omnicoder.anichan.utils.Constants.VIEW_LESS;
import static com.omnicoder.anichan.utils.Constants.VIEW_MORE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.recyclerViews.AllTimePopularAdapter;
import com.omnicoder.anichan.adapters.recyclerViews.RelatedAnimeAdapter;
import com.omnicoder.anichan.adapters.recyclerViews.VideoAdapter;
import com.omnicoder.anichan.databinding.FragmentSummaryBinding;
import com.omnicoder.anichan.di.BaseApplication;
import com.omnicoder.anichan.models.animeResponse.Anime;
import com.omnicoder.anichan.models.animeResponse.AnimeTheme;
import com.omnicoder.anichan.models.animeResponse.Broadcast;
import com.omnicoder.anichan.models.animeResponse.RelatedAnime;
import com.omnicoder.anichan.models.animeResponse.Studio;
import com.omnicoder.anichan.models.animeResponse.videos.Promo;
import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.ui.activities.ViewThemesActivity;
import com.omnicoder.anichan.viewModels.ViewAnimeViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AnimeSummaryFragment extends Fragment {
    Anime anime;
    NativeAd nativeAd;
    FragmentSummaryBinding binding;
    boolean viewMore=true;
    private static final String FINISHED_AIRING="finished_airing";
    private static final String CURRENTLY_AIRING="currently_airing";
    Context context;
    ViewAnimeViewModel viewModel;


    public AnimeSummaryFragment(Anime anime, ViewAnimeViewModel viewModel){
        this.anime= anime;
        this.viewModel=viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.fetchVideos(anime.getId());
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
        initializeGoogleAdmob();
        observeData();
        binding.statusView.setText(getAiringStatus(anime.getStatus()));
        setBroadcast(anime.getBroadcast());
        binding.japaneseView.setText(anime.getAlternateTitles().getJa());
        binding.synonymsView.setText(getAlternateTitles(anime.getAlternateTitles().getSynonyms()));
        binding.releaseDateView.setText(formatDate(anime.getStart_date()));
        binding.endDateView.setText(formatDate(anime.getEnd_date()));
        String episodeDuration=(anime.getAverage_episode_duration()/60)+" min";
        binding.episodeDurationView.setText(episodeDuration);
        binding.sourceView.setText(anime.getSource());
        try{
            binding.studiosView.setText(getStudios(anime.getStudios()));
        }catch (Exception e){
            e.printStackTrace();
        }
        setupOpeningAndEndingThemes(anime.getOpening_themes(),anime.getEnding_themes());
        setRecommendations(anime.getRecommendations(),getContext());
        setRelatedAnime(anime.getRelated_anime(),getContext());
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
    }

    private String formatDate(String d){
        try{
            Locale defaultLocale=Locale.getDefault();
            Date date = new SimpleDateFormat("yyyy-MM-dd",defaultLocale).parse(d);
            assert date != null;
            return new SimpleDateFormat("d MMMM yyyy",defaultLocale).format(date);
        }catch (Exception e){
            return d;
        }
    }

    private void setBroadcast(Broadcast broadcast) {
        if(broadcast==null) return;
        String broadcastString= broadcast.getBroadCast();
        String broadcastStringCapitalized=broadcastString.substring(0, 1).toUpperCase() + broadcastString.substring(1);
        binding.broadcastView.setText(broadcastStringCapitalized);
    }


    private void initializeGoogleAdmob(){
        AdLoader adLoader = new AdLoader.Builder(requireContext(), NATIVE_AD_UNIT_ID).forNativeAd(nativeAd -> {
            if (isAdded() && requireActivity().isDestroyed()) {
                nativeAd.destroy();
                return;
            }

            if(this.nativeAd!=null){
                this.nativeAd.destroy();
            }
            this.nativeAd=nativeAd;
            if(binding!=null){
                binding.nativeAdView.setNativeAd(nativeAd);
            }
        }).build();
        AdRequest nativeAdRequest = new AdRequest.Builder().build();
        adLoader.loadAd(nativeAdRequest);
    }

    private void setRecommendations(List<Data> recommendations, Context context){
        if(recommendations.size()>0){
            AllTimePopularAdapter allTimePopularAdapter= new AllTimePopularAdapter(context,recommendations,true);
            binding.recommendationRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            binding.recommendationRv.setAdapter(allTimePopularAdapter);
        }else{
            binding.recommendations.setVisibility(View.GONE);
            binding.recommendationRv.setVisibility(View.GONE);
        }

    }

    private void setRelatedAnime(List<RelatedAnime> related_anime, Context context) {
        if(related_anime.size()>0){
            RelatedAnimeAdapter relatedAnimeAdapter= new RelatedAnimeAdapter(context,related_anime,true);
            binding.relatedRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            binding.relatedRv.setAdapter(relatedAnimeAdapter);
        }else{
            binding.related.setVisibility(View.GONE);
            binding.relatedRv.setVisibility(View.GONE);
        }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(this.nativeAd!=null){
            nativeAd.destroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.getRoot().requestLayout();
    }
}