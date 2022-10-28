package com.omnicoder.anichan.adapters.stateAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.omnicoder.anichan.models.jikan.PersonAnime;
import com.omnicoder.anichan.models.jikan.PersonManga;
import com.omnicoder.anichan.models.jikan.VoiceActingRole;
import com.omnicoder.anichan.ui.fragments.viewCharacter.CharacterAnimeFragment;
import com.omnicoder.anichan.ui.fragments.viewCharacter.CharacterMangaFragment;
import com.omnicoder.anichan.ui.fragments.viewPerson.VoiceActingRoleFragment;

import java.util.List;

public class PersonFragmentsStateAdapter extends FragmentStateAdapter {
    VoiceActingRoleFragment voiceActingRoleFragment;
    CharacterAnimeFragment animeFragment;
    CharacterMangaFragment mangaFragment;
    List<VoiceActingRole> voicesActors;
    List<PersonAnime> anime;
    List<PersonManga> manga;

    public PersonFragmentsStateAdapter(@NonNull FragmentActivity activity, List<VoiceActingRole> voicesActors, List<PersonAnime> anime, List<PersonManga> manga) {
        super(activity);
        this.voicesActors = voicesActors;
        this.anime = anime;
        this.manga = manga;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            if(voiceActingRoleFragment==null){
                voiceActingRoleFragment=new VoiceActingRoleFragment(voicesActors);
            }
            return voiceActingRoleFragment;
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
