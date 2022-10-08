package com.omnicoder.anichan.adapters.recyclerViews;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.models.animeResponse.AnimeTheme;
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
        holder.nameView.setText(name);
        holder.itemView.setOnClickListener(v -> {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query="+getCleanName(name))));
        });
        holder.itemView.setOnLongClickListener(v -> {
            ClipboardManager clipboardManager= (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
            ClipData clip= ClipData.newPlainText("ThemeName",name);
            clipboardManager.setPrimaryClip(clip);
            Toast.makeText(context,"Text copied successfully",Toast.LENGTH_SHORT).show();
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        public MyViewHolder(View itemView) {
            super(itemView);
            nameView=itemView.findViewById(R.id.themeName);
        }
    }

    String getCleanName(String name){
        name=name.replace(" ","+");
        int size=name.length();
        if (name.startsWith("#")) {
            name = name.substring(4, size);
        }
        int index=name.indexOf("(ep");
        if(index==-1){
            return name;
        }else{
            return (name.substring(0,index-1));
        }
    }
}
