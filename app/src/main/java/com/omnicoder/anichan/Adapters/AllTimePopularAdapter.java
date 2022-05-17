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

import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.Models.Responses.Node;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Activities.ViewAnimeActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AllTimePopularAdapter extends RecyclerView.Adapter<AllTimePopularAdapter.MyViewHolder> {
    List<Data> dataHolder;
    Context context;

    public AllTimePopularAdapter(Context context, List<Data> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllTimePopularAdapter.MyViewHolder holder, int position) {
        Node Anime= dataHolder.get(position).getNode();
        String title= Anime.getTitle();
        String imageURL= Anime.getMainPicture().getMedium();
        Picasso.get().load(imageURL).into(holder.imageView);
        holder.titleView.setText(title);
        holder.imageView.setClipToOutline(true);
        holder.cardView.setOnClickListener(v -> {
            Intent intent= new Intent(context, ViewAnimeActivity.class);
            intent.putExtra("media_type",Anime.getMedia_type());
            intent.putExtra("id",Anime.getId());
            intent.putExtra("single",true);
            intent.putExtra("seasonNo",0);
            context.startActivity(intent);
        });



    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView;
        ConstraintLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleView= itemView.findViewById(R.id.titleView);
            cardView=itemView.findViewById(R.id.cardView2);
        }

    }
}


