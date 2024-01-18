package dev.akdag.kidsstory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ListenStory extends AppCompatActivity {
    AdView mAdView;
    TextView tvTime, tvDuration,sarkiadi;
    SeekBar seekBarTime;
    Button btnPlay,btnNext,btnBack,btnLoop;
    ImageView imageView;
    MediaPlayer musicPlayer;
    String subject,sarkiadiTv,storyId;
    int siradakisarkino;
    int loop = 0;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_story);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        tanimlar();
        Bundle bundle = getIntent().getExtras();


        if(bundle != null){
            // referansa ulaşıp ilgili sohbetleri getirebilmemiz için gerekli yapı
            subject = bundle.getString("subject");
            sarkiadiTv=bundle.getString("sarkiadi");
            sarkiadi.setText(sarkiadiTv);
            imageView.setImageResource(getResources().getIdentifier(subject, "drawable", getPackageName()));

        }


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextMusic();
            }
        });

        musicSettings();

        linearLayout.setOnClickListener(v -> {
            onBackPressed();
        });


    }
    public void tanimlar() {
        tvTime = findViewById(R.id.tvTime);
        tvDuration = findViewById(R.id.tvDuration);
        seekBarTime = findViewById(R.id.seekBarTime);
        btnPlay = findViewById(R.id.btnPlay);
        sarkiadi=findViewById(R.id.sarkiAdi);
        imageView = findViewById(R.id.resim);
        btnNext=findViewById(R.id.btnNext);
        btnBack=findViewById(R.id.btnBack);
        btnLoop=findViewById(R.id.musicLoop);
        linearLayout=findViewById(R.id.backMenu);
    }

    public String millisecondsToString(int time) {
        String elapsedTime = "";
        int minutes = time / 1000 / 60;
        int seconds = time / 1000 % 60;
        elapsedTime = minutes + ":";
        if (seconds < 10) {
            elapsedTime += "0";
        }
        elapsedTime += seconds;

        return elapsedTime;
    }


    public void backMusic(View view) {
        subject = subject.replace("story","");
        if (Integer.parseInt(subject)==1){
            siradakisarkino=17;
            loadMusic();
        }
        else{
            siradakisarkino= Integer.parseInt(subject)-1;
            loadMusic();
        }
    }

    public void nextMusic() {
        subject = subject.replace("story","");
        if (Integer.parseInt(subject)==17){
            siradakisarkino=1;
            loadMusic();
        }
        else{
            siradakisarkino= Integer.parseInt(subject)+1;
            loadMusic();
        }
    }
    public void loadMusic(){
        storyId= "story"+ siradakisarkino;
        subject=storyId;
        musicPlayer.stop();
        seekBarTime.setProgress(0);
        tvTime.setText("0:00");
        btnPlay.setBackgroundResource(R.drawable.ic_pause);
        int resId = getResources().getIdentifier(subject, "array",this.getPackageName());
        String[] res = getResources().getStringArray(resId);
        sarkiadi.setText(res[0]);
        imageView.setImageResource(getResources().getIdentifier(storyId, "drawable", getPackageName()));
        musicSettings();
    }


    public void musicLoop(View view) {
        if (loop==0){
            loop=1;
            musicPlayer.setLooping(true);
            btnLoop.setBackgroundResource(R.drawable.arrow_loop);
            Toast.makeText(this, "Hikaye Döngüye Alındı!", Toast.LENGTH_SHORT).show();
        }
        else if (loop==1){
            loop=0;
            musicPlayer.setLooping(false);
            btnLoop.setBackgroundResource(R.drawable.no_loop);
            Toast.makeText(this, "Hikaye Döngüden Çıkarıldı", Toast.LENGTH_SHORT).show();
        }
    }


    public void playMusic(View view) {
        btnPlay.setEnabled(false);
        if (musicPlayer.isPlaying()) {
            // çalıyorsa
            musicPlayer.pause();
            btnPlay.setBackgroundResource(R.drawable.ic_play);
            btnPlay.setEnabled(true);
        } else {
            musicPlayer.start();
            btnPlay.setBackgroundResource(R.drawable.ic_pause);
            btnPlay.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        musicPlayer.stop();
        super.onBackPressed();
    }

    public void musicSettings(){
        Resources res = getResources();
        int sound = res.getIdentifier(subject, "raw", getPackageName());
        musicPlayer = MediaPlayer.create(this, sound);
        musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        musicPlayer.seekTo(0);
        musicPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                // Do something when media player end playing
                nextMusic();
            }
        });


        String duration = millisecondsToString(musicPlayer.getDuration());
        tvDuration.setText(duration);
        seekBarTime.setMax(musicPlayer.getDuration());
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isForumUser) {
                if (isForumUser) {
                    musicPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (musicPlayer != null) {
                    if (musicPlayer.isPlaying()) {
                        try {
                            final double current = musicPlayer.getCurrentPosition();
                            final String elapsedTime = millisecondsToString((int) current);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvTime.setText(elapsedTime);
                                    seekBarTime.setProgress((int) current);
                                }
                            });
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }).start();


        musicPlayer.start();
    }

}