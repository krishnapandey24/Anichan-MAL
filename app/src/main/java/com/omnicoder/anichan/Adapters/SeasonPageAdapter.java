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

import com.omnicoder.anichan.Models.ExploreView;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Activities.ViewAnimeActivity;
import com.omnicoder.anichan.Utils.Constants;
import com.omnicoder.anichan.databinding.Layout2Binding;
import com.squareup.picasso.Picasso;

public class SeasonPageAdapter extends PagingDataAdapter<ExploreView, SeasonPageAdapter.MyViewHolder> {
    public static final int LOADING_ITEM = 0;
    public static final int ANIME_ITEM = 1;
    Context context;

    public SeasonPageAdapter(DiffUtil.ItemCallback<ExploreView> diffCallBack,Context context){
        super(diffCallBack);
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.layout2,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonPageAdapter.MyViewHolder holder, int position) {
        ExploreView currentAnime= getItem(position);
        if(currentAnime != null){
            String title= currentAnime.getTitle();
            String imageURL= Constants.IMAGE_LINK+currentAnime.getImageURL();
            Picasso.get().load(imageURL).into(holder.imageView);
            holder.titleView.setText(title);
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


