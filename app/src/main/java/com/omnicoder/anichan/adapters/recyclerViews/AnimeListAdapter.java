package com.omnicoder.anichan.adapters.recyclerViews;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.database.UserAnime;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.ui.activities.ViewAnimeActivity;
import com.omnicoder.anichan.ui.fragments.bottomSheets.UpdateAnimeBottomSheet;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class AnimeListAdapter extends RecyclerView.Adapter<AnimeListAdapter.MyViewHolder>{
    List<UserAnime> animeList;
    Context context;
    static final String notRatedYet="--";
    MyViewHolder.UpdateAnimeList updateAnimeList;
    UpdateAnimeBottomSheet.UpdateAnime updateAnime;
    int viewPagerPosition;
    int size;

    public AnimeListAdapter(Context context, List<UserAnime> animeList, MyViewHolder.UpdateAnimeList updateAnimeList, UpdateAnimeBottomSheet.UpdateAnime updateAnime, int viewPagerPosition){
        this.animeList = animeList;
        this.context= context;
        this.updateAnimeList=updateAnimeList;
        this.updateAnime=updateAnime;
        this.viewPagerPosition=viewPagerPosition;
        this.size= animeList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.anime_list_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeListAdapter.MyViewHolder holder, int position) {
        UserAnime currentUserAnime = animeList.get(position);
        int id= currentUserAnime.getId();
        int score= currentUserAnime.getScore();
        int totalEpisodes= currentUserAnime.getNum_episodes();
        final int[] watchedEpisodes = {currentUserAnime.getNum_episodes_watched()};
        String title= currentUserAnime.getTitle();
        Picasso.get().load(currentUserAnime.getMain_picture()).into(holder.imageView);
        holder.titleView.setText(title);
        holder.formatView.setText(currentUserAnime.getMedia_type().toUpperCase());
        holder.episodeCountView.setText(String.valueOf(currentUserAnime.getNum_episodes_watched()));
        holder.givenScoreView.setText( score!=0 ? String.valueOf(score) : notRatedYet);
        if(totalEpisodes==0){
            holder.totalEpisodeView.setText("?");
            holder.progressBar.setMax(100);
        }else{
            holder.totalEpisodeView.setText(String.valueOf(totalEpisodes));
            holder.progressBar.setMax(totalEpisodes);
        }
        holder.progressBar.setProgress(watchedEpisodes[0]);
        holder.seasonView.setText(currentUserAnime.getStart_season().toUpperCase(Locale.ROOT));
        holder.addButton.setOnClickListener(v -> {
            if(watchedEpisodes[0]<totalEpisodes-1 || totalEpisodes==0) {
                holder.progressBar.setProgress(++watchedEpisodes[0]);
                updateAnimeList.addEpisode(id,watchedEpisodes[0]);
                holder.episodeCountView.setText(String.valueOf(watchedEpisodes[0]));
            }else {
                holder.progressBar.setProgress(++watchedEpisodes[0]);
                holder.episodeCountView.setText(String.valueOf(watchedEpisodes[0]));
                updateAnimeList.animeComplete(id,title);
            }

        });
        holder.editButton.setOnClickListener(v -> {
            UpdateAnimeBottomSheet updateAnimeBottomSheet=new UpdateAnimeBottomSheet();
            updateAnimeBottomSheet.setAnime(currentUserAnime,updateAnime,viewPagerPosition,watchedEpisodes[0]);
            updateAnimeList.showEditor(updateAnimeBottomSheet);
        });
        holder.constraintLayout.setOnClickListener(v -> {
            Intent intent= new Intent(context, ViewAnimeActivity.class);
            intent.putExtra("media_type", currentUserAnime.getMedia_type());
            intent.putExtra("id",id);
            context.startActivity(intent);
        });

        if(position==size-2){
            updateAnimeList.fetchMore();
        }

    }

    public void updateData(List<UserAnime> newAnimeList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new AnimeDiffCallback(this.animeList, newAnimeList));
        this.animeList.clear();
        this.animeList.addAll(newAnimeList);
        Log.d("tagg","size: "+newAnimeList.size());
        diffResult.dispatchUpdatesTo(this);
    }

    static class AnimeDiffCallback extends DiffUtil.Callback {
        private final List<UserAnime> oldList;
        private final List<UserAnime> newList;

        public AnimeDiffCallback(List<UserAnime> oldList, List<UserAnime> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }




    @Override
    public int getItemCount() {
        return size;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleView,formatView,totalEpisodeView ,episodeCountView,givenScoreView,seasonView;
        ProgressBar progressBar;
        ImageButton addButton,editButton;
        ImageView imageView;
        ConstraintLayout constraintLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            titleView=itemView.findViewById(R.id.titleView);
            formatView=itemView.findViewById(R.id.formatView);
            totalEpisodeView=itemView.findViewById(R.id.totalEpisodeView);
            episodeCountView=itemView.findViewById(R.id.episodeCountView);
            givenScoreView=itemView.findViewById(R.id.givenScoreView);
            progressBar=itemView.findViewById(R.id.progressBar);
            seasonView=itemView.findViewById(R.id.seasonView);
            addButton=itemView.findViewById(R.id.addButton);
            editButton=itemView.findViewById(R.id.editButton);
            imageView=itemView.findViewById(R.id.imageView);
            constraintLayout=itemView.findViewById(R.id.layout);
 
        }

        public interface UpdateAnimeList{
            void addEpisode(int id,int noOfEpisodesWatched);
            void showEditor(UpdateAnimeBottomSheet updateAnimeBottomSheet);
            void animeComplete(int id,String title);
            void fetchMore();
        }
    }
}


