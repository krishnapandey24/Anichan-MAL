package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Database.AnimeList;
import com.omnicoder.anichan.UI.Activities.ViewAnimeActivity;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.UpdateAnimeBottomSheet;
import com.omnicoder.anichan.databinding.ListItemLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeListAdapter extends RecyclerView.Adapter<AnimeListAdapter.MyViewHolder>{
    List<AnimeList> dataHolder;
    Context context;
    ListItemLayoutBinding binding;
    static final String notRatedYet="--";
    MyViewHolder.UpdateAnimeList updateAnimeList;
    UpdateAnimeBottomSheet.UpdateAnime updateAnime;


    public AnimeListAdapter(Context context, List<AnimeList> dataHolder, MyViewHolder.UpdateAnimeList updateAnimeList,UpdateAnimeBottomSheet.UpdateAnime updateAnime){
        this.dataHolder= dataHolder;
        this.context= context;
        this.updateAnimeList=updateAnimeList;
        this.updateAnime=updateAnime;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= ListItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeListAdapter.MyViewHolder holder, int position) {
        AnimeList currentAnime= dataHolder.get(position);
        int id=currentAnime.getAnimeID();
        int score=currentAnime.getGivenScore();
        int totalEpisodes=currentAnime.getTotalEpisode();
        final int[] watchedEpisodes = {currentAnime.getWatchedEpisodes()};
        String title=currentAnime.getTitle();
        Picasso.get().load(currentAnime.getPosterPath()).into(binding.imageView);
        holder.binding.titleView.setText(title);
        holder.binding.formatView.setText(currentAnime.getFormat().toUpperCase());
        holder.binding.totalEpisodeView.setText(String.valueOf(totalEpisodes));
        holder.binding.episodeCountView.setText(String.valueOf(currentAnime.getWatchedEpisodes()));
        holder.binding.givenScoreView.setText( score!=-1 ? String.valueOf(score) :notRatedYet);
        holder.binding.progressBar.setMax(totalEpisodes);
        holder.binding.progressBar.setProgress(watchedEpisodes[0]);
//        holder.binding.statusView.setText(currentAnime.getAiringStatus());
        holder.binding.addButton.setOnClickListener(v -> {
            if(watchedEpisodes[0]<totalEpisodes-1) {
                updateAnimeList.addEpisode(currentAnime.getAnimeID());
                watchedEpisodes[0] = watchedEpisodes[0] + 1;
                holder.binding.progressBar.setProgress(watchedEpisodes[0]);
                holder.binding.episodeCountView.setText(String.valueOf(watchedEpisodes[0]));
            }else {
                watchedEpisodes[0] = watchedEpisodes[0] + 1;
                holder.binding.progressBar.setProgress(watchedEpisodes[0]);
                holder.binding.episodeCountView.setText(String.valueOf(watchedEpisodes[0]));
                updateAnimeList.animeComplete(id,title);
            }

        });
        holder.binding.editButton.setOnClickListener(v -> {
            UpdateAnimeBottomSheet updateAnimeBottomSheet=new UpdateAnimeBottomSheet();
            updateAnimeBottomSheet.setAnime(currentAnime,updateAnime);
            updateAnimeList.showEditor(updateAnimeBottomSheet);
        });
        holder.binding.getRoot().setOnClickListener(v -> {
            Intent intent= new Intent(context, ViewAnimeActivity.class);
            intent.putExtra("media_type",currentAnime.getMediaType());
            intent.putExtra("id",currentAnime.getAnimeID());
            intent.putExtra("single",true);
            intent.putExtra("seasonNo",0);
            intent.putExtra("format",currentAnime.getFormat());
            context.startActivity(intent);
            
        });

    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ListItemLayoutBinding binding;
        public MyViewHolder(ListItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


        }

        public interface UpdateAnimeList{
            void addEpisode(int id);
            void showEditor(UpdateAnimeBottomSheet updateAnimeBottomSheet);
            void animeComplete(int id, String name);
        }


    }




}


