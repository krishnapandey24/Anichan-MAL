package com.omnicoder.anichan.ui.fragments.viewCharacter;

import static com.omnicoder.anichan.utils.AdsConstants.NATIVE_AD_UNIT_ID;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.recyclerViews.CharacterAnimeAdapter;

import com.omnicoder.anichan.databinding.FragmentVoiceActorsBinding;
import com.omnicoder.anichan.models.jikan.CharacterAnime;

import java.util.List;


public class CharacterAnimeFragment extends Fragment {
    List<CharacterAnime> anime;
    NativeAd nativeAd;
    FragmentVoiceActorsBinding binding;

    public CharacterAnimeFragment(List<CharacterAnime> anime) {
        this.anime = anime;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentVoiceActorsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CharacterAnimeAdapter characterAnimeAdapter=new CharacterAnimeAdapter(anime,null,true,getContext(), anime.size());
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(characterAnimeAdapter);
        initializeGoogleAdmob();
    }

    private void initializeGoogleAdmob(){
        MobileAds.initialize(requireContext(), initializationStatus -> {
            AdLoader adLoader = new AdLoader.Builder(requireContext(), NATIVE_AD_UNIT_ID).forNativeAd(nativeAd -> {
                if (isAdded() && requireActivity().isDestroyed()) {
                    nativeAd.destroy();
                    return;
                }
                if(this.nativeAd!=null){
                    this.nativeAd.destroy();
                }

                this.nativeAd=nativeAd;
                binding.adView.setNativeAd(nativeAd);
            }).build();
            AdRequest nativeAdRequest = new AdRequest.Builder().build();
            adLoader.loadAd(nativeAdRequest);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (nativeAd != null) {
            nativeAd.destroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.getRoot().requestLayout();
    }
}