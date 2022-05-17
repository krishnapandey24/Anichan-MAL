package com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Adapters.ThemesAdapter;
import com.omnicoder.anichan.Adapters.Top100Adapter;
import com.omnicoder.anichan.Adapters.VideoAdapter;
import com.omnicoder.anichan.Models.AnimeResponse.Anime;
import com.omnicoder.anichan.Models.AnimeResponse.AnimeTheme;
import com.omnicoder.anichan.Models.AnimeResponse.Studio;
import com.omnicoder.anichan.Models.AnimeResponse.videos.Promo;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.ViewModels.ViewAnimeViewModel;
import com.omnicoder.anichan.databinding.FragmentSummaryBinding;

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
        observeData();
        viewModel.fetchVideos(anime.getId());
        binding.statusView.setText(getAiringStatus(anime.getStatus()));
        binding.broadcastView.setText(anime.getBroadcast().getBroadCast());
        binding.japaneseView.setText(anime.getAlternateTitles().getJa());
        binding.synonymsView.setText(getAlternateTitles(anime.getAlternateTitles().getSynonyms()));
        binding.releaseDateView.setText(anime.getStart_date());
        binding.endDateView.setText(anime.getEnd_date());
        String episodeDuration=(anime.getAverage_episode_duration()/60)+" min";
        binding.episodeDurationView.setText(episodeDuration);
        binding.sourceView.setText(anime.getSource());
        binding.studiosView.setText(getStudios(anime.getStudios()));
        setupOpeningAndEndingThemes(anime.getOpening_themes(),anime.getEnding_themes());

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

    private void setupOpeningAndEndingThemes(List<AnimeTheme> opening_themes, List<AnimeTheme> ending_themes) {
        View.OnClickListener onClickListener= v -> {
            View view=binding.themesViewStub.inflate();
            RecyclerView openingRecyclerView=view.findViewById(R.id.openingListView);
            RecyclerView endingRecyclerView=view.findViewById(R.id.endingListView);
            ThemesAdapter openingAdapter = new ThemesAdapter(opening_themes,getContext());
            ThemesAdapter endingAdapter = new ThemesAdapter(ending_themes,getContext());
            openingRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            endingRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            openingRecyclerView.setAdapter(openingAdapter);
            endingRecyclerView.setAdapter(endingAdapter);
        };
        binding.openingendingtheme.setOnClickListener(onClickListener);
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

    private String calculateTotalWatchTime(int num_episodes, int average_episode_duration) {
        int watchTime=num_episodes*average_episode_duration;
        if(watchTime>1440){
            return watchTime/24/60 + "d " + watchTime/60%24 + "h " + watchTime%60 + "m";
        }
        return watchTime/60%24 + "h " + watchTime%60+"m ";

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    private void loadVideos(List<Promo> videos){
        if(videos!=null){
            VideoAdapter adapter = new VideoAdapter(context, videos);
            RecyclerView recyclerView=binding.videoViewStub.inflate().findViewById(R.id.videoViewRv);
            recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            recyclerView.setAdapter(adapter);
        }
    }
//    private void loadLinks(){
//        View view =binding.linksViewStub.inflate();
//        Map<String,Object> links =viewAnime.getLinks();
//        view.findViewById(R.id.instagram).setOnClickListener(v -> {
//            String id=(String)links.get("instagram_id");
//            if(id!=null){
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"+id)));
//            }else {
//                Toast.makeText(context,"Instagram Page Not Found",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        view.findViewById(R.id.facebook).setOnClickListener(v -> {
//            String id=(String)links.get("facebook_id");
//            if(id!=null){
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+id)));
//            }else {
//                Toast.makeText(context,"Facebook Page Not Found",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        view.findViewById(R.id.twitter).setOnClickListener(v -> {
//            String id=(String)links.get("twitter_id");
//            if(id!=null){
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com/"+id)));
//            }else {
//                Toast.makeText(context,"Twitter Page Not Found",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        view.findViewById(R.id.officialSite).setOnClickListener(v -> {
//            String url=viewAnime.getHomepage();
//            if(url!=null){
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//            }else {
//                Toast.makeText(context,"Official Site Not Found",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

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