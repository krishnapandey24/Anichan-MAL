package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.AnimeResponse.AnimeTheme;
import com.omnicoder.anichan.R;

import java.util.List;

public class ThemesAdapter extends RecyclerView.Adapter<ThemesAdapter.MyViewHolder> {
    List<AnimeTheme> dataHolder;
    Context context;

    public ThemesAdapter(List<AnimeTheme> dataHolder, Context context) {
        this.dataHolder = dataHolder;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.anime_theme_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AnimeTheme theme=dataHolder.get(position);
        String name=theme.getText();
        holder.textView.setText(name);
        holder.itemView.setOnClickListener(v -> {
            String query=name.replace(" ","+");
            if(query.startsWith("#")){
                query=query.replaceFirst("#","");
            }
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query="+query)));
        });

    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.themeName);
        }

    }
}
