package com.omnicoder.anichan.adapters.recyclerViews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.databinding.CastItemLayoutBinding;
import com.omnicoder.anichan.models.jikan.CharacterAnime;
import com.omnicoder.anichan.models.jikan.JikanSubEntity;
import com.omnicoder.anichan.models.jikan.PersonAnime;
import com.omnicoder.anichan.models.jikan.PersonManga;
import com.omnicoder.anichan.ui.activities.ViewAnimeActivity;
import com.omnicoder.anichan.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterAnimeAdapter extends RecyclerView.Adapter<CharacterAnimeAdapter.MyViewHolder> {
    List<CharacterAnime> characterAnime;
    List<PersonAnime> personAnime;
    boolean isCharacterAnime;
    Context context;
    CastItemLayoutBinding binding;
    int size;


    public CharacterAnimeAdapter(List<CharacterAnime> characterAnime, List<PersonAnime> personAnime, boolean isCharacterAnime, Context context,int size) {
        this.characterAnime = characterAnime;
        this.personAnime = personAnime;
        this.isCharacterAnime = isCharacterAnime;
        this.context = context;
        this.size=size;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CastItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAnimeAdapter.MyViewHolder holder, int position) {
        JikanSubEntity entity;
        if (isCharacterAnime) {
            CharacterAnime anime = characterAnime.get(position);
            entity = anime.getAnime();
            holder.binding.characterName.setText(anime.getRole());
        } else {
            PersonAnime anime = personAnime.get(position);
            entity = anime.getAnime();
            holder.binding.characterName.setText(anime.getPosition());
        }
        try {
            Picasso.get().load(entity.getImages().getJpg().getImage_url()).into(holder.binding.imageView);
        } catch (Exception e) {
            holder.binding.imageView.setImageResource(R.drawable.ic_no_image_placeholder);
        }
        String title = entity.getTitle();
        holder.binding.titleView.setText(title);
        holder.binding.imageView.setClipToOutline(true);
        holder.binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewAnimeActivity.class);
            intent.putExtra("media_type", entity.getType());
            intent.putExtra(Constants.ID, entity.getMalId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return size;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CastItemLayoutBinding binding;

        public MyViewHolder(@NonNull CastItemLayoutBinding layoutBinding) {
            super(layoutBinding.getRoot());
            this.binding = layoutBinding;
        }


    }
}


