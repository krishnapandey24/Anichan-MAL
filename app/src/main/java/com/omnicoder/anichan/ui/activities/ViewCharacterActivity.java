package com.omnicoder.anichan.ui.activities;

import static com.omnicoder.anichan.utils.Constants.CHARACTER_IMAGES;
import static com.omnicoder.anichan.utils.Constants.ID;
import static com.omnicoder.anichan.utils.Constants.IMAGE_TYPE;
import static com.omnicoder.anichan.utils.Constants.VIEW_LESS;
import static com.omnicoder.anichan.utils.Constants.VIEW_MORE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.stateAdapters.CharacterFragmentsStateAdapter;
import com.omnicoder.anichan.databinding.ActivityViewCharacterBinding;
import com.omnicoder.anichan.di.BaseApplication;
import com.omnicoder.anichan.models.jikan.CharacterAnime;
import com.omnicoder.anichan.models.jikan.CharacterDetailsData;
import com.omnicoder.anichan.models.jikan.CharacterManga;
import com.omnicoder.anichan.models.jikan.CharacterVoiceActor;
import com.omnicoder.anichan.models.jikan.ImageData;
import com.omnicoder.anichan.utils.Constants;
import com.omnicoder.anichan.utils.LoadingDialog;
import com.omnicoder.anichan.viewModels.CharacterViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class ViewCharacterActivity extends AppCompatActivity {
    ActivityViewCharacterBinding binding;
    CharacterViewModel viewModel;
    boolean viewMore=true;
    FragmentStateAdapter fragmentStateAdapter;
    LoadingDialog loadingDialog;
    ImageData characterImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewCharacterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingDialog=new LoadingDialog(this);
        loadingDialog.startLoadingForActivity();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        viewModel=new ViewModelProvider(this).get(CharacterViewModel.class);
        viewModel.fetchCharacterDetails(getIntent().getIntExtra(ID,0));
        viewModel.getCharacterDetails().observe(this, this::initViews);
        viewModel.getCharacterImages().observe(this, this::setImages);
        setOnClickListeners();
    }


    private void setOnClickListeners() {
        View.OnClickListener onClickListener= v -> {
            if(viewMore){
                binding.about.setMaxLines(50);
                binding.viewMore.setText(VIEW_LESS);
            }else {
                binding.about.setMaxLines(5);
                binding.viewMore.setText(VIEW_MORE);
            }
            viewMore=!viewMore;
        };
        binding.viewMore.setOnClickListener(onClickListener);
        binding.about.setOnClickListener(onClickListener);
        binding.backButton.setOnClickListener(v -> finish());
    }


    private void initViews(CharacterDetailsData character) {
        loadingDialog.stopLoading();
        try{
            Picasso.get().load(character.getImages().getJpg().getImage_url()).into(binding.characterImageView);
            characterImage=new ImageData(character.getImages().getJpg());
        }catch (Exception e){
            e.printStackTrace();
        }
        binding.nameView.setText(character.getName());
        binding.kanjiNameView.setText(character.getKanjiName());
        binding.favoriteCount.setText(String.valueOf(character.getFavorites()));
        binding.about.setText(character.getAbout());
        setTabLayout(character.getVoices(),character.getAnime(),character.getManga());
    }

    private void setImages(List<ImageData> jpgs){
        try{
            if(jpgs!=null && jpgs.size()>0){
                Picasso.get().load(jpgs.get(0).getJpg().getImage_url()).into(binding.backgroundPoster);
                binding.characterImageView.setOnClickListener(v -> {
                    if(characterImage!=null){
                        jpgs.add(0,characterImage);
                    }
                    BaseApplication application=(BaseApplication) getApplicationContext();
                    application.setCharacterImages(jpgs);
                    Intent intent=new Intent(ViewCharacterActivity.this,PosterViewActivity.class);
                    intent.putExtra(IMAGE_TYPE,CHARACTER_IMAGES);
                    startActivity(intent);
                });

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setTabLayout(List<CharacterVoiceActor> voicesActors, List<CharacterAnime> anime, List<CharacterManga> manga){
        String[] tabs=getResources().getStringArray(R.array.ViewCharacterTabs);
        ViewPager2 viewPager;
        viewPager=binding.fragmentContainerView;
        if(fragmentStateAdapter==null){
            fragmentStateAdapter=new CharacterFragmentsStateAdapter(ViewCharacterActivity.this,voicesActors,anime,manga);
        }
        viewPager.setAdapter(fragmentStateAdapter);
        new TabLayoutMediator(binding.tabLayout,viewPager, (tab, position) -> tab.setText(tabs[position])).attach();
    }

    @Override
    protected void onPause() {
        super.onPause();
        loadingDialog.stopLoading();
    }
}