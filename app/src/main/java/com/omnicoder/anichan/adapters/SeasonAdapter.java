package com.omnicoder.anichan.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.models.responses.Node;
import com.omnicoder.anichan.ui.activities.ViewAnimeActivity;
import com.omnicoder.anichan.ui.activities.ViewMangaActivity;
import com.omnicoder.anichan.databinding.LayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.MyViewHolder> {
    List<Data> dataHolder;
    Context context;
    LayoutBinding binding;
    boolean isAnime;

    public SeasonAdapter(Context context, List<Data> dataHolder, boolean isAnime){
        this.dataHolder= dataHolder;
        this.context= context;
        this.isAnime=isAnime;
    }

    public List<Data> getDataHolder() {
        return dataHolder;
    }

    public void setDataHolder(List<Data> dataHolder) {
        this.dataHolder = dataHolder;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= LayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonAdapter.MyViewHolder holder, int position) {
        Node Anime= dataHolder.get(position).getNode();
        String title= Anime.getTitle();
        String imageURL= Anime.getMainPicture().getMedium();
        String mediaType= Anime.getMedia_type();
        Picasso.get().load(imageURL).into(holder.binding.imageView);
        holder.binding.titleView.setText(title);
        holder.binding.imageView.setClipToOutline(true);
        holder.binding.getRoot().setOnClickListener(v -> {
            Intent intent;
            if(isAnime){
                intent = new Intent(context, ViewAnimeActivity.class);
                intent.putExtra("media_type",mediaType);
            }else{
                intent = new Intent(context, ViewMangaActivity.class);
            }
            intent.putExtra("id",Anime.getId());
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


