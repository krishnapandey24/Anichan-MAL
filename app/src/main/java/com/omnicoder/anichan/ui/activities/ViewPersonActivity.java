package com.omnicoder.anichan.ui.activities;

import static com.omnicoder.anichan.utils.Constants.ANIME;
import static com.omnicoder.anichan.utils.Constants.CHARACTER_IMAGES;
import static com.omnicoder.anichan.utils.Constants.DATE_PATTERN;
import static com.omnicoder.anichan.utils.Constants.ID;
import static com.omnicoder.anichan.utils.Constants.IMAGE_TYPE;
import static com.omnicoder.anichan.utils.Constants.MANGA;
import static com.omnicoder.anichan.utils.Constants.PEOPLE;
import static com.omnicoder.anichan.utils.Constants.VIEW_LESS;
import static com.omnicoder.anichan.utils.Constants.VIEW_MORE;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.stateAdapters.PersonFragmentsStateAdapter;
import com.omnicoder.anichan.databinding.ActivityViewPersonBinding;
import com.omnicoder.anichan.di.BaseApplication;
import com.omnicoder.anichan.models.animeResponse.Characters.Person;
import com.omnicoder.anichan.models.jikan.ImageData;
import com.omnicoder.anichan.models.jikan.PersonAnime;
import com.omnicoder.anichan.models.jikan.PersonManga;
import com.omnicoder.anichan.models.jikan.VoiceActingRole;
import com.omnicoder.anichan.ui.fragments.viewPerson.PersonAnimeFragment;
import com.omnicoder.anichan.ui.fragments.viewPerson.PersonMangaFragment;
import com.omnicoder.anichan.ui.fragments.viewPerson.VoiceActingRoleFragment;
import com.omnicoder.anichan.utils.Constants;
import com.omnicoder.anichan.utils.LoadingDialog;
import com.omnicoder.anichan.viewModels.PersonViewModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class ViewPersonActivity extends AppCompatActivity {
    private static final String VOICES = "Voice";
    ActivityViewPersonBinding binding;
    boolean viewMore=true;
    FragmentStateAdapter fragmentStateAdapter;
    LoadingDialog loadingDialog;
    ImageData characterImage;
    ArrayList<Fragment> fragments;
    PopupMenu popupMenu;
    int malId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 05-01-2023 Fix lagging and add pagination
        binding=ActivityViewPersonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        loadingDialog=new LoadingDialog(this);
        loadingDialog.startLoadingForActivity();
        PersonViewModel viewModel=new ViewModelProvider(this).get(PersonViewModel.class);
        viewModel.fetchPersonDetails(getIntent().getIntExtra(ID,0));
        viewModel.getPersonDetails().observe(this,this::initViews);
        viewModel.getPersonImages().observe(this,this::setImages);
    }


    private void initViews(Person person) {
        malId=person.getMalId();
        loadingDialog.stopLoading();
        try{
            Picasso.get().load(person.getImages().getJpg().getImage_url()).into(binding.characterImageView);
            characterImage=new ImageData(person.getImages().getJpg());
        }catch (Exception e){
            e.printStackTrace();
            binding.characterImageView.setImageResource(R.drawable.ic_no_image_placeholder);
        }
        binding.nameView.setText(person.getName());
        String kanjiName=person.getFamilyName() + " " + person.getGivenName();
        binding.kanjiNameView.setText(kanjiName);
        binding.favoriteCount.setText(String.valueOf(person.getFavorites()));
        binding.about.setText(person.getAbout());
        binding.birthdayDate.setText(formatDate(person.getBirthday()));
        setTabLayout(person.getVoices(),person.getAnime(),person.getManga());
        setOnClickListeners();
    }

    @SuppressLint("NonConstantResourceId")
    private void launchMenu(){
        String link = String.format(Constants.MY_ANIME_LIST_LINK,PEOPLE,malId);
        if(popupMenu==null) {
            popupMenu = new PopupMenu(this, binding.menuButton, Gravity.END);
            popupMenu.getMenuInflater().inflate(R.menu.view_anime_manga_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.share:
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, link);
                        startActivity(Intent.createChooser(shareIntent, "Share link using"));
                        break;
                    case R.id.openInMal:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                        startActivity(browserIntent);
                        break;
                    case R.id.copyLink:
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("text", link);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(this, "Link copied!",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            });
        }
        popupMenu.show();
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
                    Intent intent=new Intent(ViewPersonActivity.this,PosterViewActivity.class);
                    intent.putExtra(IMAGE_TYPE,CHARACTER_IMAGES);
                    startActivity(intent);
                });

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setTabLayout(List<VoiceActingRole> voicesActors, List<PersonAnime> anime, List<PersonManga> manga){
        List<String> tabs=new ArrayList<>();
        ViewPager2 viewPager;
        viewPager=binding.fragmentContainerView;
        if(fragments==null){
            fragments=new ArrayList<>();
            if(voicesActors!=null && !voicesActors.isEmpty()){
                fragments.add(new VoiceActingRoleFragment(voicesActors));
                tabs.add(VOICES);
            }
            if(anime!=null && !anime.isEmpty()){
                fragments.add(new PersonAnimeFragment(anime));
                tabs.add(ANIME);
            }
            if(manga!=null && !manga.isEmpty()){
                fragments.add(new PersonMangaFragment(manga));
                tabs.add(MANGA);
            }
        }

        if(fragmentStateAdapter==null){
            fragmentStateAdapter=new PersonFragmentsStateAdapter(ViewPersonActivity.this,fragments);
        }
        viewPager.setAdapter(fragmentStateAdapter);
        new TabLayoutMediator(binding.tabLayout,viewPager, (tab, position) -> tab.setText(tabs.get(position))).attach();
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
        binding.menuButton.setOnClickListener(v -> launchMenu());
    }



    private String formatDate(String birthDate) {
        if (birthDate == null) {
            return "??";
        }
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = formatter.parse(birthDate);
            SimpleDateFormat newFormat = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
            assert date != null;
            return newFormat.format(date);
        } catch (Exception e) {
            return birthDate.substring(0, 10);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(loadingDialog!=null){
            loadingDialog.stopLoading();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}