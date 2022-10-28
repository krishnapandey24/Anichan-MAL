package com.omnicoder.anichan.adapters.recyclerViews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.databinding.CastItemLayoutBinding;
import com.omnicoder.anichan.models.jikan.CharacterManga;
import com.omnicoder.anichan.models.jikan.JikanSubEntity;
import com.omnicoder.anichan.ui.activities.ViewAnimeActivity;
import com.omnicoder.anichan.ui.activities.ViewMangaActivity;
import com.omnicoder.anichan.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterMangaAdapter extends RecyclerView.Adapter<CharacterMangaAdapter.MyViewHolder> {
    List<CharacterManga> dataHolder;
    Context context;
    CastItemLayoutBinding binding;

    public CharacterMangaAdapter(Context context, List<CharacterManga> dataHolder) {
        this.dataHolder = dataHolder;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CastItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterMangaAdapter.MyViewHolder holder, int position) {
        CharacterManga manga=dataHolder.get(position);
        JikanSubEntity entity = manga.getManga();
        String title = entity.getTitle();
        String imageURL = entity.getImages().getJpg().getImage_url();
        Picasso.get().load(imageURL).into(holder.binding.imageView);
        holder.binding.titleView.setText(title);
        holder.binding.imageView.setClipToOutline(true);
        holder.binding.characterName.setText(manga.getRole());
        holder.binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewMangaActivity.class);
            intent.putExtra(Constants.ID, entity.getMalId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CastItemLayoutBinding binding;

        public MyViewHolder(@NonNull CastItemLayoutBinding layoutBinding) {
            super(layoutBinding.getRoot());
            this.binding = layoutBinding;
        }


    }
}


