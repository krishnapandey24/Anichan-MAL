package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.TrendingAnime;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Activities.ViewAnimeActivity;
import com.omnicoder.anichan.Utils.Constants;
import com.omnicoder.anichan.ViewModels.ExploreViewModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class TrendingViewPagerAdapter extends RecyclerView.Adapter<TrendingViewPagerAdapter.MyViewHolder> {
    List<TrendingAnime> dataHolder;
    Context context;


    public TrendingViewPagerAdapter(Context context, List<TrendingAnime> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.trending_viewpager_item,parent,false);
        return new MyViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingViewPagerAdapter.MyViewHolder holder, int position) {
        TrendingAnime Anime= dataHolder.get(position);
        String imageURL= Constants.IMAGE_URL +Anime.getImageURL();
        Picasso.get().load(imageURL).into(holder.imageView);
        Picasso.get().load(Constants.IMAGE_URL+Anime.getBackgroundPoster()).into(holder.backgroundPosterView);
        holder.titleView.setText(Anime.getTitle());
        holder.imageView.setClipToOutline(true);
        holder.ratingView.setText(Anime.getMovieDBRating());
        holder.formatView.setText(Anime.getFormat());
        holder.genresView.setText(Anime.getGenres());
        holder.cardView.setOnClickListener(v -> {
            Intent intent= new Intent(context,ViewAnimeActivity.class);
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,backgroundPosterView;
        TextView titleView,genresView,ratingView,formatView;
        ConstraintLayout cardView;

        public MyViewHolder(View itemView, Context context) {
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
}


