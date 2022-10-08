package com.omnicoder.anichan.adapters.recyclerViews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.models.animeResponse.videos.Promo;
import com.omnicoder.anichan.databinding.VideoItemLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    List<Promo> dataHolder;
    Context context;
    VideoItemLayoutBinding binding;

    public VideoAdapter(Context context, List<Promo> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= VideoItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.MyViewHolder holder, int position) {
        Promo video= dataHolder.get(position);
        Log.d("tagg","title: "+video.getTitle());
        try {
            Picasso.get().load(video.getTrailer().getImages().getMedium_image_url()).into(holder.binding.thumbnailView);
            binding.constraintLayout.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getTrailer().getUrl()));
                context.startActivity(browserIntent);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        binding.textView17.setText(video.getTitle());

    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        VideoItemLayoutBinding binding;
        public MyViewHolder(VideoItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }



    }
}


