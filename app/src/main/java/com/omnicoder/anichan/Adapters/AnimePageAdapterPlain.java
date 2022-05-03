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
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.ExplorePlainView;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Activities.ViewAnimeActivity;
import com.omnicoder.anichan.Utils.Constants;
import com.squareup.picasso.Picasso;

public class AnimePageAdapterPlain extends PagingDataAdapter<ExplorePlainView, AnimePageAdapterPlain.MyViewHolder> {
    public static final int LOADING_ITEM = 0;
    public static final int ANIME_ITEM = 1;
    Context context;

    public AnimePageAdapterPlain(DiffUtil.ItemCallback<ExplorePlainView> diffCallBack, Context context){
        super(diffCallBack);
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.plain_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimePageAdapterPlain.MyViewHolder holder, int position) {
        ExplorePlainView currentAnime= getItem(position);
        if(currentAnime != null){
            String imageURL= Constants.IMAGE_LINK+currentAnime.getImageURL();
            Picasso.get().load(imageURL).into(holder.imageView);
            holder.titleView.setText(currentAnime.getTitle());
            holder.genresView.setText(currentAnime.getGenres());
            holder.ratingView.setText(currentAnime.getMovieDBRating());
            holder.imageView.setClipToOutline(true);
            holder.constraintLayout.setOnClickListener(v -> {
                Intent intent= new Intent(context, ViewAnimeActivity.class);
                intent.putExtra("media_type",currentAnime.getMediaType());
                intent.putExtra("id",currentAnime.getAnimeID());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
       return position== getItemCount() ? ANIME_ITEM : LOADING_ITEM;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintLayout;
        ImageView imageView;
        TextView titleView,genresView,ratingView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout= itemView.findViewById(R.id.cardView2);
            imageView = itemView.findViewById(R.id.imageView);
            titleView= itemView.findViewById(R.id.titleView);
            genresView= itemView.findViewById(R.id.genresView);
            ratingView= itemView.findViewById(R.id.ratingView);
        }
    }
}


