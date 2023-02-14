package com.omnicoder.anichan.adapters.recyclerViews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.database.UserManga;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.ui.activities.ViewMangaActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class SearchMangaListAdapter extends RecyclerView.Adapter<SearchMangaListAdapter.MyViewHolder>{
    List<UserManga> dataHolder;
    Context context;
    static final String notRatedYet="--";

    public SearchMangaListAdapter(Context context, List<UserManga> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.search_manga_list_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMangaListAdapter.MyViewHolder holder, int position) {
        UserManga manga = dataHolder.get(position);
        int id= manga.getId();
        int score= manga.getScore();
        int totalVolumes= manga.getNoOfVolumes();
        int totalChapters= manga.getNoOfChapters();
        final int[] chaptersRead = {manga.getNoOfChaptersRead()};
        String title= manga.getTitle();
        Picasso.get().load(manga.getMain_picture()).into(holder.imageView);
        holder.titleView.setText(title);
        String volumesRead=manga.getNoOfVolumesRead()+"/"+(totalVolumes==0 ? "?" : String.valueOf(totalVolumes));
        holder.totalVolumesView.setText(volumesRead);
        holder.givenScoreView.setText( score!=0 ? String.valueOf(score) : notRatedYet);
        holder.chaptersReadView.setText(String.valueOf(chaptersRead[0]));
        if(totalChapters==0){
            holder.totalChaptersView.setText("?");
            holder.progressBar.setMax(100);
        }else{
            holder.totalChaptersView.setText(String.valueOf(totalChapters));
            holder.progressBar.setMax(totalChapters);
        }
        holder.progressBar.setProgress(chaptersRead[0]);
        holder.progressBar.setMax(totalChapters);
        holder.progressBar.setProgress(chaptersRead[0]);
        holder.statusView.setText(manga.getStatus().replaceAll("_"," ").toUpperCase(Locale.ROOT));
        holder.constraintLayout.setOnClickListener(v -> {
            Intent intent= new Intent(context, ViewMangaActivity.class);
            intent.putExtra("id",id);
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleView, totalVolumesView, totalChaptersView, chaptersReadView, givenScoreView,statusView;
        ProgressBar progressBar;
        ImageButton addButton, editButton;
        ImageView imageView;
        ConstraintLayout constraintLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.titleView);
            totalVolumesView = itemView.findViewById(R.id.totalVolumesView);
            totalChaptersView = itemView.findViewById(R.id.totalChapterView);
            chaptersReadView = itemView.findViewById(R.id.chaptersReadView);
            givenScoreView = itemView.findViewById(R.id.givenScoreView);
            progressBar = itemView.findViewById(R.id.progressBar);
            addButton = itemView.findViewById(R.id.addButton);
            editButton = itemView.findViewById(R.id.editButton);
            imageView = itemView.findViewById(R.id.imageView);
            constraintLayout = itemView.findViewById(R.id.layout);
            statusView=itemView.findViewById(R.id.statusView);

        }
    }



}


