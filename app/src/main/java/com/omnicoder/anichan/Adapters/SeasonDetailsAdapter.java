package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.Season;
import com.omnicoder.anichan.UI.Activities.ViewAnimeActivity;
import com.omnicoder.anichan.databinding.SeasonEpisodeItemLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SeasonDetailsAdapter extends RecyclerView.Adapter<SeasonDetailsAdapter.MyViewHolder> {
    List<Season> dataHolder;
    Context context;
    SeasonEpisodeItemLayoutBinding binding;
    int id;

    public SeasonDetailsAdapter(Context context, List<Season> dataHolder,int id){
        this.dataHolder= dataHolder;
        this.context= context;
        this.id=id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        binding=SeasonEpisodeItemLayoutBinding.inflate(inflater,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonDetailsAdapter.MyViewHolder holder, int position) {
        Season season=dataHolder.get(position);
        String imageURL= "https://image.tmdb.org/t/p/w500/"+season.getPoster_path();
        holder.binding.titleView.setText(season.getName());
        holder.binding.episodeCountView.setText(season.getEpisode_count());
        holder.binding.airDateView.setText(season.getAir_date());
        Picasso.get().load(imageURL).into(holder.binding.imageView);
        holder.binding.cardView2.setOnClickListener(v -> {
            Intent intent= new Intent(context,ViewAnimeActivity.class);
            intent.putExtra("media_type","tv");
            intent.putExtra("single",true);
            intent.putExtra("seasonNo",season.getSeason_number());
            intent.putExtra("id",id);
            intent.putExtra("main",false);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        SeasonEpisodeItemLayoutBinding binding;

        public MyViewHolder(SeasonEpisodeItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }
}


