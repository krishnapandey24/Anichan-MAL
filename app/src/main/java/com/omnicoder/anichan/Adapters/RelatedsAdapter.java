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

import com.omnicoder.anichan.Models.AnimeResponse.RelatedAnime;
import com.omnicoder.anichan.Models.Responses.MainPicture;
import com.omnicoder.anichan.Models.Responses.Node;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Activities.ViewAnimeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RelatedsAdapter extends RecyclerView.Adapter<RelatedsAdapter.MyViewHolder> {
    List<RelatedAnime> dataHolder;
    Context context;

    public RelatedsAdapter(Context context, List<RelatedAnime> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.related_anime_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedsAdapter.MyViewHolder holder, int position) {
        RelatedAnime anime=dataHolder.get(position);
        Node node=anime.getNode();
        holder.titleView.setText(node.getTitle());
        holder.relationTypeView.setText(anime.getRelation_type_formatted());
        MainPicture mainPicture= anime.getNode().getMainPicture();
        if(mainPicture!=null){
            Picasso.get().load(mainPicture.getMedium()).into(holder.imageView);
        }
        holder.cardView.setOnClickListener(v -> {
            Intent intent= new Intent(context,ViewAnimeActivity.class);
            intent.putExtra("id",node.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView,relationTypeView;
        ConstraintLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleView= itemView.findViewById(R.id.titleView);
            relationTypeView= itemView.findViewById(R.id.relationType);
            cardView=itemView.findViewById(R.id.cardView2);
        }

    }
}


