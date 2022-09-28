package com.omnicoder.anichan.adapters;

import static com.omnicoder.anichan.utils.Constants.ANIME;
import static com.omnicoder.anichan.utils.Constants.CHARACTERS;
import static com.omnicoder.anichan.utils.Constants.MANGA;
import static com.omnicoder.anichan.utils.Constants.PEOPLE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.databinding.LayoutBinding;
import com.omnicoder.anichan.models.jikan.JikanSubEntity;
import com.omnicoder.anichan.ui.activities.ViewAnimeActivity;
import com.omnicoder.anichan.ui.activities.ViewCharacterActivity;
import com.omnicoder.anichan.ui.activities.ViewMangaActivity;
import com.omnicoder.anichan.ui.activities.ViewPersonActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class JikanEntityAdapter extends RecyclerView.Adapter<JikanEntityAdapter.MyViewHolder> {
    List<JikanSubEntity> dataHolder;
    Context context;
    LayoutBinding binding;
    String entityType;
    int count;

    public JikanEntityAdapter(Context context, List<JikanSubEntity> dataHolder, String entityType, int count){
        this.dataHolder= dataHolder;
        this.context= context;
        this.entityType=entityType;
        this.count=count;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= LayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull JikanEntityAdapter.MyViewHolder holder, int position) {
        JikanSubEntity entity=dataHolder.get(position);
        String title= entity.getTitle();
        String imageURL= entity.getImages().getJpg().getImage_url();
        Picasso.get().load(imageURL).into(holder.binding.imageView);
        holder.binding.titleView.setText(title);
        holder.binding.imageView.setClipToOutline(true);
        holder.binding.getRoot().setOnClickListener(v -> {
            Intent intent = null;
            switch (entityType){
                case ANIME:
                    intent = new Intent(context, ViewAnimeActivity.class);
                    intent.putExtra("media_type",entity.getType());
                    break;
                case MANGA:
                    intent = new Intent(context, ViewMangaActivity.class);
                    break;
                case CHARACTERS:
                    intent= new Intent(context, ViewCharacterActivity.class);
                    break;
                case PEOPLE:
                    break;
                default:
                    intent= new Intent(context, ViewPersonActivity.class);
                    break;

            }
            intent.putExtra("id",entity.getMalId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        LayoutBinding binding;

        public MyViewHolder(@NonNull LayoutBinding layoutBinding) {
            super(layoutBinding.getRoot());
            this.binding= layoutBinding;
        }




    }
}


