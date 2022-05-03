package com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Adapters.VideoAdapter;
import com.omnicoder.anichan.Models.Season;
import com.omnicoder.anichan.Models.ViewAnime;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.databinding.FragmentSummaryBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SummaryFragment extends Fragment {
    ViewAnime viewAnime;
    FragmentSummaryBinding binding;
    boolean viewMore=true;
    String viewMore2="View More";
    String viewLess="View Less";
    Context context;

    public SummaryFragment(ViewAnime viewAnime){
        this.viewAnime= viewAnime;

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
        Single.fromCallable(this::getDataInBackground)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                        binding.statusView.setText(data.get(0));
                        binding.alternateTitlesView.setText(data.get(1));
                        binding.episodeDurationView.setText(data.get(2));
                        binding.totalSeasonsView.setText(data.get(3));
                        binding.releaseDateView.setText(data.get(4));
                        binding.totalWatchTimeView.setText(data.get(5));
                        if (data.get(2).equals("none")) {
                            binding.alternateTitlesView.setTypeface(Typeface.DEFAULT);
                            binding.viewMore2.setVisibility(View.INVISIBLE);
                        }
                        loadVideos();
                        loadLinks();
                    });

        
        binding.viewMore2.setOnClickListener(v -> {
            if(viewMore){
                binding.alternateTitlesView.setMaxLines(15);
                binding.viewMore2.setText(viewLess);
            }else {
                binding.alternateTitlesView.setMaxLines(3);
                binding.viewMore2.setText(viewMore2);
            }
            viewMore=!viewMore;
        });
    }
    

    private List<String> getDataInBackground(){
        List<String> data = new ArrayList<>();
        data.add(viewAnime.getStatus());
        data.add(viewAnime.getTitles());
        data.add(viewAnime.getEpisode_run_time());
        data.add(viewAnime.getNumber_of_seasons());
        if(viewAnime.getSingle()){
            Season season= viewAnime.getSeason();
            data.add(season.getAir_date());
            data.add(viewAnime.getSeasonRuntime(season));
        }else{
            data.add(viewAnime.getFirst_air_date());
            data.add(viewAnime.getRuntime());
        }
        return data;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    private void loadVideos(){
        List<Map<String,Object>> videoList=new ArrayList<>();
        List<Map<String,Object>> videos= (List<Map<String, Object>>) viewAnime.getVideos().get("results");
        int size=(videos != null ? videos.size() : 0);
        if(size>0){
            for(int i=0;i<size;i++){
                Map<String,Object> video=videos.get(i);
                String videoType=(String)video.get("type");
                Log.d("VideoView","Video Name "+video.get("name")+" Type: "+video.get("type")+(videoType.equals("Trailer") || videoType.equals("Teaser")));
                if(videoType.equals("Trailer") || videoType.equals("Teaser")){
                    videoList.add(video);
                }
            }
            Log.d("VideoView","Video Size"+videoList.size());
            VideoAdapter adapter = new VideoAdapter(context, videoList);
            RecyclerView recyclerView=binding.videoViewStub.inflate().findViewById(R.id.videoViewRv);
            recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            recyclerView.setAdapter(adapter);
        }
    }

    private void loadLinks(){
        View view =binding.linksViewStub.inflate();
        Map<String,Object> links =viewAnime.getLinks();
        view.findViewById(R.id.instagram).setOnClickListener(v -> {
            String id=(String)links.get("instagram_id");
            if(id!=null){
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"+id)));
            }else {
                Toast.makeText(context,"Instagram Page Not Found",Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.facebook).setOnClickListener(v -> {
            String id=(String)links.get("facebook_id");
            if(id!=null){
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+id)));
            }else {
                Toast.makeText(context,"Facebook Page Not Found",Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.twitter).setOnClickListener(v -> {
            String id=(String)links.get("twitter_id");
            if(id!=null){
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com/"+id)));
            }else {
                Toast.makeText(context,"Twitter Page Not Found",Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.officialSite).setOnClickListener(v -> {
            String url=viewAnime.getHomepage();
            if(url!=null){
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }else {
                Toast.makeText(context,"Official Site Not Found",Toast.LENGTH_SHORT).show();
            }
        });
    }

}