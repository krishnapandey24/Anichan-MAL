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
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.ExploreView;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Activities.ViewAnimeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {
    List<ExploreView> dataHolder;
    Context context;

    public AdapterClass(Context context, List<ExploreView> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.trending_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass.MyViewHolder holder, int position) {
        String title= dataHolder.get(position).getTitle();
        String imageURL= "https://image.tmdb.org/t/p/w500/"+dataHolder.get(position).getImageURL();
        Picasso.get().load(imageURL).into(holder.imageView);
        holder.titleView.setText(title);
        holder.imageView.setClipToOutline(true);
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
}


