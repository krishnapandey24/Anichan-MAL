package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.databinding.VideoItemLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    List<Map<String,Object>> dataHolder;
    Context context;
    VideoItemLayoutBinding binding;
    static final String thumbnail="https://img.youtube.com/vi/%s/mqdefault.jpg";
    static final String videoURL="https://youtu.be/%s";

    public VideoAdapter(Context context, List<Map<String,Object>> dataHolder){
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
        Map<String,Object> video = dataHolder.get(position);
        String key=(String) video.get("key");
        Picasso.get().load(String.format(thumbnail,key)).into(holder.binding.thumbnailView);
        binding.textView17.setText((String)video.get("name"));
        binding.constraintLayout.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(videoURL,key)));
            context.startActivity(browserIntent);
        });

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


