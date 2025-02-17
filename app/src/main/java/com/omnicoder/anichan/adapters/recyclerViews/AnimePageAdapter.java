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
import com.omnicoder.anichan.models.responses.MainPicture;
import com.omnicoder.anichan.models.responses.Node;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.ui.activities.ViewAnimeActivity;
import com.omnicoder.anichan.ui.activities.ViewMangaActivity;
import com.squareup.picasso.Picasso;

public class AnimePageAdapter extends PagingDataAdapter<Data, AnimePageAdapter.MyViewHolder> {
    public static final int LOADING_ITEM = 0;
    public static final int ANIME_ITEM = 1;
    Context context;
    boolean isAnime;

    public AnimePageAdapter(DiffUtil.ItemCallback<Data> diffCallBack,Context context,boolean isAnime){
        super(diffCallBack);
        this.context=context;
        this.isAnime=isAnime;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.layout2,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimePageAdapter.MyViewHolder holder, int position) {
        Node currentAnime= getItem(position).getNode();
        if(currentAnime != null){
            String title= currentAnime.getTitle();
            try{
                Picasso.get().load(currentAnime.getMainPicture().getMedium()).into(holder.imageView);
            } catch (Exception e) {
                holder.imageView.setImageResource(R.drawable.ic_no_image_placeholder);
            }
            holder.titleView.setText(title);
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
        ImageView imageView;
        TextView titleView;
        ConstraintLayout constraintLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleView= itemView.findViewById(R.id.titleView);
            constraintLayout= itemView.findViewById(R.id.cardView2);
        }
    }
}


