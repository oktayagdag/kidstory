package dev.akdag.kidsstory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


public class MainActivity extends AppCompatActivity {
     AdView mAdView;
     RecyclerView recyclerView;
     ArrayList<StoryList> stories;
     StoryListAdapter storyListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Admob.loadAd(MainActivity.this);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        viewSettings();
        fillTheArray();


    }

    private void fillTheArray() {
        stories.add(new StoryList(R.drawable.story1,R.color.pembe, "Aslan ile Fare","story1"));
        stories.add(new StoryList(R.drawable.story2,R.color.mavi, "Kibirli Gül","story2"));
        stories.add(new StoryList(R.drawable.story3,R.color.turuncu, "Bencil Dev","story3"));
        stories.add(new StoryList(R.drawable.story4,R.color.lila, "Kızıl Tavuk","story4"));
        stories.add(new StoryList(R.drawable.story5,R.color.kmavi, "Kırmızı Başlıklı Kız","story5"));
        stories.add(new StoryList(R.drawable.story6,R.color.yesil, "Sihirli İnek","story6"));
        stories.add(new StoryList(R.drawable.story7,R.color.aturuncu, "Minik Ayı Yavrusu","story7"));
        stories.add(new StoryList(R.drawable.story8,R.color.mavi1, "Ali Baba ve Kırk Haramiler","story8"));
        stories.add(new StoryList(R.drawable.story9,R.color.sari1, "Kurabiye Adam","story9"));
        stories.add(new StoryList(R.drawable.story10,R.color.lacivert, "Çirkin Ördek Yavrusu","story10"));
        stories.add(new StoryList(R.drawable.story11,R.color.kiremit, "Hasta Fok Balığı","story11"));
        stories.add(new StoryList(R.drawable.story12,R.color.mavi2, "Değirmencinin Oğlu","story12"));
        stories.add(new StoryList(R.drawable.story13,R.color.pembe2, "Tuzlu Deniz","story13"));
        stories.add(new StoryList(R.drawable.story14,R.color.yesil2, "Goldilocks ve Üç Ayı","story14"));
        stories.add(new StoryList(R.drawable.story15,R.color.kiremit1, "Gül Prenses ve Altın Kuş","story15"));
        stories.add(new StoryList(R.drawable.story16,R.color.mavi3, "Fil ve Karınca","story16"));
        stories.add(new StoryList(R.drawable.story17,R.color.pembe, "Karınca ile Ağustos Böceği","story17"));

    }
    private void viewSettings() {
        //recyclerview setup
        recyclerView = findViewById(R.id.story_recyclerview);
        stories = new ArrayList<>();
        storyListAdapter = new StoryListAdapter(stories, MainActivity.this);
        recyclerView.setAdapter(storyListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(storyListAdapter);
        scaleInAnimationAdapter.setDuration(1000);
        scaleInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        scaleInAnimationAdapter.setFirstOnly(false);
        recyclerView.setAdapter(scaleInAnimationAdapter);
    }


}