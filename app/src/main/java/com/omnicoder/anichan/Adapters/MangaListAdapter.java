package com.omnicoder.anichan.Adapters;

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

import com.omnicoder.anichan.Database.UserManga;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Activities.ViewMangaActivity;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.UpdateMangaBottomSheet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MangaListAdapter extends RecyclerView.Adapter<MangaListAdapter.MyViewHolder>{
    List<UserManga> dataHolder;
    Context context;
    static final String notRatedYet="--";
    MyViewHolder.UpdateMangaList updateMangaList;
    UpdateMangaBottomSheet.UpdateManga updateManga;
    int viewPagerPosition;


    public MangaListAdapter(Context context, List<UserManga> dataHolder, MyViewHolder.UpdateMangaList updateMangaList, UpdateMangaBottomSheet.UpdateManga updateManga,int viewPagerPosition){
        this.dataHolder= dataHolder;
        this.context= context;
        this.updateMangaList=updateMangaList;
        this.updateManga=updateManga;
        this.viewPagerPosition=viewPagerPosition;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.manga_list_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaListAdapter.MyViewHolder holder, int position) {
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
        holder.addButton.setOnClickListener(v -> {
            if(chaptersRead[0]<totalChapters-1 || totalChapters==0) {
                holder.progressBar.setProgress(++chaptersRead[0]);
                updateMangaList.addChapter(id,chaptersRead[0]);
                holder.chaptersReadView.setText(String.valueOf(chaptersRead[0]));
            }else {
                holder.progressBar.setProgress(++chaptersRead[0]);
                holder.chaptersReadView.setText(String.valueOf(chaptersRead[0]));
                updateMangaList.mangaComplete(id,title);
            }
        });
        holder.editButton.setOnClickListener(v -> {
            UpdateMangaBottomSheet updateMangaBottomSheet=new UpdateMangaBottomSheet();
            updateMangaBottomSheet.setManga(manga,updateManga,viewPagerPosition);
            updateMangaList.showEditor(updateMangaBottomSheet);
        });
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


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleView,totalVolumesView,totalChaptersView,chaptersReadView,givenScoreView;
        ProgressBar progressBar;
        ImageButton addButton,editButton;
        ImageView imageView;
        ConstraintLayout constraintLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleView=itemView.findViewById(R.id.titleView);
            totalVolumesView=itemView.findViewById(R.id.totalVolumesView);
            totalChaptersView=itemView.findViewById(R.id.totalChapterView);
            chaptersReadView=itemView.findViewById(R.id.chaptersReadView);
            givenScoreView=itemView.findViewById(R.id.givenScoreView);
            progressBar=itemView.findViewById(R.id.progressBar);
            addButton=itemView.findViewById(R.id.addButton);
            editButton=itemView.findViewById(R.id.editButton);
            imageView=itemView.findViewById(R.id.imageView);
            constraintLayout=itemView.findViewById(R.id.layout);
 
        }

        public interface UpdateMangaList{
            void addChapter(int id,int noOfChaptersRead);
            void showEditor(UpdateMangaBottomSheet updateMangaBottomSheet);
            void mangaComplete(int id,String title);
        }

    }



}


