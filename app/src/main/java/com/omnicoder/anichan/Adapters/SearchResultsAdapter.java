package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.Animes;
import com.omnicoder.anichan.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.MyViewHolder> {
    List<Animes> dataHolder;
    Context context;

    public SearchResultsAdapter(Context context, List<Animes> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.search_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsAdapter.MyViewHolder holder, int position) {
        String[] data= dataHolder.get(position).getData();
        holder.titleView.setText(data[0]);
        String imageURL= "https://image.tmdb.org/t/p/w500/"+data[1];
        holder.imageView.setClipToOutline(true);
        holder.ratingView.setText(data[2]);
        holder.mediaTypeView.setText(data[3].toUpperCase());
        Picasso.get().load(imageURL).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView titleView,ratingView,mediaTypeView;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.imageView);
            titleView= itemView.findViewById(R.id.titleView);
            ratingView=itemView.findViewById(R.id.ratingView);
        }

        @Override
        public void onClick(View v) {


        }
    }


}


