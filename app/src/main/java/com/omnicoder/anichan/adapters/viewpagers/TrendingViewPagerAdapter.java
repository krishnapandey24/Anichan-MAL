package com.omnicoder.anichan.adapters.viewpagers;

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

import com.omnicoder.anichan.models.responses.Genre;
import com.omnicoder.anichan.models.responses.Node;
import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.ui.activities.ViewAnimeActivity;
import com.omnicoder.anichan.ui.activities.ViewMangaActivity;
import com.omnicoder.anichan.utils.blurTransformation.BlurTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;


public class TrendingViewPagerAdapter extends RecyclerView.Adapter<TrendingViewPagerAdapter.MyViewHolder> {
    List<Data> dataHolder;
    Context context;
    boolean isAnime;

    public TrendingViewPagerAdapter(Context context, List<Data> dataHolder,boolean isAnime){
        this.dataHolder= dataHolder;
        this.context= context;
        this.isAnime=isAnime;
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
        try{
            String imageURL= Anime.getMainPicture().getMedium();
            Picasso.get().load(imageURL).into(holder.imageView);
            Picasso.get().load(imageURL).transform(new BlurTransformation(context,5,1)).into(holder.backgroundPosterView);
        }catch (Exception e){
            holder.imageView.setImageResource(R.drawable.ic_no_image_placeholder);
        }
        holder.titleView.setText(Anime.getTitle());
        holder.imageView.setClipToOutline(true);
        holder.ratingView.setText(String.valueOf(Anime.getMean()));
        holder.formatView.setText(Anime.getMedia_type());
        holder.genresView.setText(getGenres(Anime.getGenres()));
        holder.cardView.setOnClickListener(v -> {
            Intent intent;
            if(isAnime){
                intent = new Intent(context, ViewAnimeActivity.class);
                intent.putExtra("media_type",Anime.getMedia_type());

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
        StringBuilder studiosString = new StringBuilder();
        String prefix="";
        for(Genre genre: genres) {
            studiosString.append(prefix);
            prefix=", ";
            studiosString.append(genre.getName());
        }
        return studiosString;
    }
}


