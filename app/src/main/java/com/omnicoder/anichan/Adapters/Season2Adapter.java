package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.ExploreView;
import com.omnicoder.anichan.UI.Activities.ViewAnimeActivity;
import com.omnicoder.anichan.databinding.LayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Season2Adapter extends RecyclerView.Adapter<Season2Adapter.MyViewHolder> {
    List<ExploreView> dataHolder;
    Context context;
    LayoutBinding binding;

    public Season2Adapter(Context context, List<ExploreView> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
    }

    public List<ExploreView> getDataHolder() {
        return dataHolder;
    }

    public void setDataHolder(List<ExploreView> dataHolder) {
        this.dataHolder = dataHolder;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= LayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Season2Adapter.MyViewHolder holder, int position) {
        ExploreView Anime= dataHolder.get(position);
        String title= Anime.getTitle();
        String imageURL= "https://image.tmdb.org/t/p/w500/"+Anime.getImageURL();
        Picasso.get().load(imageURL).into(holder.binding.imageView);
        holder.binding.titleView.setText(title);
        holder.binding.imageView.setClipToOutline(true);
        holder.binding.getRoot().setOnClickListener(v -> {
            Intent intent= new Intent(context, ViewAnimeActivity.class);
            intent.putExtra("media_type",dataHolder.get(position).getMediaType());
            intent.putExtra("id",dataHolder.get(position).getAnimeID());
            intent.putExtra("single",true);
            intent.putExtra("seasonNo",0);
            intent.putExtra("format",dataHolder.get(position).getFormat());
            context.startActivity(intent);
        });



    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        LayoutBinding binding;

        public MyViewHolder(@NonNull LayoutBinding layoutBinding) {
            super(layoutBinding.getRoot());
            this.binding= layoutBinding;
        }
    }
}


