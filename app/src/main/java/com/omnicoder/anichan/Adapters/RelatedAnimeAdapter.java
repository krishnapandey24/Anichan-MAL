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

import com.omnicoder.anichan.Models.AnimeResponse.RelatedAnime;
import com.omnicoder.anichan.Models.Responses.Node;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Activities.ViewAnimeActivity;
import com.omnicoder.anichan.UI.Activities.ViewMangaActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RelatedAnimeAdapter extends RecyclerView.Adapter<RelatedAnimeAdapter.MyViewHolder> {
    List<RelatedAnime> dataHolder;
    Context context;

    public RelatedAnimeAdapter(Context context, List<RelatedAnime> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.layout3,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedAnimeAdapter.MyViewHolder holder, int position) {
        RelatedAnime anime= dataHolder.get(position);
        Node node=anime.getNode();
        try {
            String imageURL = node.getMainPicture().getMedium();
            Picasso.get().load(imageURL).into(holder.imageView);
        }catch (Exception e){
            //
        }
        holder.titleView.setText(node.getTitle());
        holder.relationType.setText(anime.getRelation_type_formatted());
        holder.imageView.setClipToOutline(true);
        holder.cardView.setOnClickListener(v -> {
            Intent intent;
            if(node.getMedia_type().equals("manga")){
                intent= new Intent(context, ViewMangaActivity.class);
            }else{
                intent= new Intent(context, ViewAnimeActivity.class);
            }
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
        TextView titleView,relationType;
        ConstraintLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleView= itemView.findViewById(R.id.titleView);
            cardView=itemView.findViewById(R.id.cardView2);
            relationType=itemView.findViewById(R.id.relationTypeView);
        }

    }
}


