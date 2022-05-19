package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.AnimeResponse.AnimeTheme;
import com.omnicoder.anichan.R;

import java.util.ArrayList;
import java.util.List;


public class ThemesViewPagerAdapter extends RecyclerView.Adapter<ThemesViewPagerAdapter.PageHolder>  {
    Context context;
    ArrayList<List<AnimeTheme>> themes;

    public ThemesViewPagerAdapter(Context context,ArrayList<List<AnimeTheme>> themes){
        this.context=context;
        this.themes=themes;
    }

    @NonNull
    @Override
    public PageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.just_recycler_view,parent,false);
        return new PageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageHolder holder, int position) {
        List<AnimeTheme> animeThemes=themes.get(position);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        holder.recyclerView.setAdapter(new ThemesAdapter(animeThemes,context));
    }



    @Override
    public int getItemCount() {
        return 2;
    }

    public static class PageHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;

        public PageHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.recyclerView);

        }
    }
}
