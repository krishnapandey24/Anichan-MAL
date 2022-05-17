package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.Responses.Genre;
import com.omnicoder.anichan.Models.Responses.Node;
import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Activities.ViewAnimeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrendingViewPagerAdapter extends RecyclerView.Adapter<TrendingViewPagerAdapter.MyViewHolder> {
    List<Data> dataHolder;
    Context context;


    public TrendingViewPagerAdapter(Context context, List<Data> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.trending_viewpager_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingViewPagerAdapter.MyViewHolder holder, int position) {
        Node Anime= dataHolder.get(position).getNode();
        String imageURL= Anime.getMainPicture().getMedium();
        Picasso.get().load(imageURL).into(holder.imageView);
        Picasso.get().load(imageURL).into(holder.backgroundPosterView);
        holder.titleView.setText(Anime.getTitle());
        holder.imageView.setClipToOutline(true);
        holder.ratingView.setText(String.valueOf(Anime.getMean()));
        holder.formatView.setText(Anime.getMedia_type());
        holder.genresView.setText(getGenres(Anime.getGenres()));
        holder.cardView.setOnClickListener(v -> {
            Intent intent= new Intent(context,ViewAnimeActivity.class);
            intent.putExtra("id",Anime.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,backgroundPosterView;
        TextView titleView,genresView,ratingView,formatView;
        ConstraintLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.posterView2);
            titleView= itemView.findViewById(R.id.animeTitle);
            formatView= itemView.findViewById(R.id.formatView);
            cardView=itemView.findViewById(R.id.trendingAnime);
            genresView= itemView.findViewById(R.id.genresView);
            ratingView=itemView.findViewById(R.id.ratingView3);
            backgroundPosterView=itemView.findViewById(R.id.backgroundPoster);

        }

    }

    private StringBuilder getGenres(List<Genre> genres) {
        int size=genres.size()-1;
        StringBuilder studiosString = new StringBuilder();
        int i = 0;
        while (i < size - 1) {
            studiosString.append(genres.get(i).getName());
            studiosString.append(",");
            i++;
        }
        studiosString.append(genres.get(i).getName());
        return studiosString;
    }
}


