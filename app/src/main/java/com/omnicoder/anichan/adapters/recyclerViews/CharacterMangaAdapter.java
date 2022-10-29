package com.omnicoder.anichan.adapters.recyclerViews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.databinding.CastItemLayoutBinding;
import com.omnicoder.anichan.models.jikan.CharacterManga;
import com.omnicoder.anichan.models.jikan.JikanSubEntity;
import com.omnicoder.anichan.models.jikan.PersonManga;
import com.omnicoder.anichan.ui.activities.ViewMangaActivity;
import com.omnicoder.anichan.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterMangaAdapter extends RecyclerView.Adapter<CharacterMangaAdapter.MyViewHolder> {
    List<CharacterManga> characterMangas;
    List<PersonManga> personMangas;
    boolean isCharacterManga;
    Context context;
    CastItemLayoutBinding binding;
    int size;

    public CharacterMangaAdapter(Context context, List<CharacterManga> characterMangas, List<PersonManga> personMangas, boolean isCharacterManga, int size) {
        this.characterMangas = characterMangas;
        this.personMangas = personMangas;
        this.isCharacterManga = isCharacterManga;
        this.context = context;
        this.size = size;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CastItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterMangaAdapter.MyViewHolder holder, int position) {
        JikanSubEntity entity;
        if (isCharacterManga) {
            CharacterManga manga = characterMangas.get(position);
            entity = manga.getManga();
            holder.binding.characterName.setText(manga.getRole());
        } else {
            PersonManga manga = personMangas.get(position);
            entity = manga.getManga();
            holder.binding.characterName.setText(manga.getPosition());
        }
        try{
            Picasso.get().load(entity.getImages().getJpg().getImage_url()).into(holder.binding.imageView);
        }catch (Exception e){
            holder.binding.imageView.setImageResource(R.drawable.ic_no_image_placeholder);
        }
        String title = entity.getTitle();
        holder.binding.titleView.setText(title);
        holder.binding.imageView.setClipToOutline(true);
        holder.binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewMangaActivity.class);
            intent.putExtra(Constants.ID, entity.getMalId());
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return isCharacterManga ? characterMangas.size() : personMangas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CastItemLayoutBinding binding;

        public MyViewHolder(@NonNull CastItemLayoutBinding layoutBinding) {
            super(layoutBinding.getRoot());
            this.binding = layoutBinding;
        }


    }
}


