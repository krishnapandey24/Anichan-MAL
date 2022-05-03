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
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.Animes;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Activities.ViewAnimeActivity;
import com.squareup.picasso.Picasso;

public class SearchPageAdapter extends PagingDataAdapter<Animes, SearchPageAdapter.MyViewHolder> {
    public static final int LOADING_ITEM = 0;
    public static final int ANIME_ITEM = 1;
    private boolean firstTime;
    Context context;

    public SearchPageAdapter(DiffUtil.ItemCallback<Animes> diffCallBack, Context context){
        super(diffCallBack);
        firstTime=true;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.search_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchPageAdapter.MyViewHolder holder, int position) {
        Animes currentAnime= getItem(position);
        if(currentAnime != null){
            holder.titleView.setText(currentAnime.getTitle());
            String imageURL= "https://image.tmdb.org/t/p/w500/"+currentAnime.getPoster_path();
            holder.imageView.setClipToOutline(true);
            Picasso.get().load(imageURL).into(holder.imageView);
            holder.cardView.setOnClickListener(v -> {
                Intent intent= new Intent(context, ViewAnimeActivity.class);
                intent.putExtra("media_type",currentAnime.getMedia_type());
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
        ImageView imageView;
        TextView titleView;
        CardView cardView;


        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleView= itemView.findViewById(R.id.titleView);
            cardView=itemView.findViewById(R.id.cardView2);
        }

    }
    public boolean hasZeroElements(){
        boolean hasZeroElements=getItemCount()<1;
        if(firstTime){
            hasZeroElements=false;
            firstTime=false;
        }
        return hasZeroElements;
    }
}


