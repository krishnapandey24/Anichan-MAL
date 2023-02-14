package com.omnicoder.anichan.adapters.stateAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.omnicoder.anichan.models.jikan.CharacterAnime;
import com.omnicoder.anichan.models.jikan.CharacterManga;
import com.omnicoder.anichan.models.jikan.CharacterVoiceActor;
import com.omnicoder.anichan.models.jikan.JikanSubEntity;
import com.omnicoder.anichan.ui.fragments.JikanSubEntityFragment;
import com.omnicoder.anichan.ui.fragments.viewCharacter.CharacterAnimeFragment;
import com.omnicoder.anichan.ui.fragments.viewCharacter.CharacterMangaFragment;
import com.omnicoder.anichan.ui.fragments.viewCharacter.VoiceActorsFragment;
import com.omnicoder.anichan.utils.Constants;

import java.util.List;

public class CharacterFragmentsStateAdapter extends FragmentStateAdapter {
    VoiceActorsFragment voiceActorFragment;
    CharacterAnimeFragment animeFragment;
    CharacterMangaFragment mangaFragment;
    List<CharacterVoiceActor> voicesActors;
    List<CharacterAnime> anime;
    List<CharacterManga> manga;

    public CharacterFragmentsStateAdapter(@NonNull FragmentActivity activity, List<CharacterVoiceActor> voicesActors, List<CharacterAnime> anime, List<CharacterManga> manga) {
        super(activity);
        this.voicesActors = voicesActors;
        this.anime = anime;
        this.manga = manga;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            if(voiceActorFragment==null){
                voiceActorFragment=new VoiceActorsFragment(voicesActors);
            }
            return voiceActorFragment;
        }else if(position==1){
            if(animeFragment==null){
                animeFragment=new CharacterAnimeFragment(anime);
            }
            return animeFragment;
        }else{
            if(mangaFragment==null){
                mangaFragment=new CharacterMangaFragment(manga);
            }
            return mangaFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
