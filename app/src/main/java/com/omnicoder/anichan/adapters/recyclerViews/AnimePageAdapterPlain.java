package com.omnicoder.anichan.adapters.recyclerViews;

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

import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.models.responses.Genre;
import com.omnicoder.anichan.models.responses.Node;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.ui.activities.ViewAnimeActivity;
import com.omnicoder.anichan.ui.activities.ViewMangaActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimePageAdapterPlain extends PagingDataAdapter<Data, AnimePageAdapterPlain.MyViewHolder> {
    public static final int LOADING_ITEM = 0;
    public static final int ANIME_ITEM = 1;
    Context context;
    boolean isAnime;

    public AnimePageAdapterPlain(DiffUtil.ItemCallback<Data> diffCallBack, Context context,boolean isAnime){
        super(diffCallBack);
        this.context=context;
        this.isAnime=isAnime;
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
        Node currentAnime= getItem(position).getNode();
        if(currentAnime != null){
            String imageURL= currentAnime.getMainPicture().getMedium();
            Picasso.get().load(imageURL).into(holder.imageView);
            holder.titleView.setText(currentAnime.getTitle());
            List<Genre> genres= currentAnime.getGenres();
            StringBuilder stringBuilder= new StringBuilder();
            int size=genres.size();
            for(int i=0;i<size;i++){
                stringBuilder.append(genres.get(i).getName());
                stringBuilder.append(",");
            }
            holder.genresView.setText(stringBuilder);
            holder.ratingView.setText(String.valueOf(currentAnime.getMean()));
            holder.imageView.setClipToOutline(true);
            holder.constraintLayout.setOnClickListener(v -> {
                Intent intent;
                if(isAnime){
                    intent = new Intent(context, ViewAnimeActivity.class);
                    intent.putExtra("media_type",currentAnime.getMedia_type());
                }else{
                    intent = new Intent(context, ViewMangaActivity.class);
                }
                intent.putExtra("id",currentAnime.getId());
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


