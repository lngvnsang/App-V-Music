package com.example.appvmusic.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appvmusic.Adapter.PlaylistAdapter;
import com.example.appvmusic.Adapter.ViewPagerPlaylistNhac;
import com.example.appvmusic.Fragment.Fragment_Dia_Nhac;
import com.example.appvmusic.Fragment.Fragment_Play_Danh_Sach_Bai_Hat;
import com.example.appvmusic.Model.BaiHat;
import com.example.appvmusic.R;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacActivity extends AppCompatActivity {

    Toolbar toolbarPlayNhac;
    TextView txtTimSong,txtTotalTimeSong;
    SeekBar seekBarSong;
    ImageButton imgPlay,imgRandom,imgNext,imgPre,imgRepeat;
    ViewPager viewPagerPlayNhac;

    public static ArrayList<BaiHat> mangBaiHat = new ArrayList<>();
    public static ViewPagerPlaylistNhac viewPagerPlaylistNhac;
    Fragment_Dia_Nhac fragment_dia_nhac;
    Fragment_Play_Danh_Sach_Bai_Hat fragment_play_danh_sach_bai_hat;

    int position = 0;
    boolean repeat = false;
    boolean random = false;
    boolean next = false;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GetDataFromIntent();
        init();
        eventClick();
    }

    private void eventClick() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewPagerPlaylistNhac.getItem(1) != null){
                    if (mangBaiHat.size() > 0){
                        fragment_dia_nhac.PlayNhac(mangBaiHat.get(0).getHinhbaihat());
                        handler.removeCallbacks(this);
                    }
                    else {
                        handler.postDelayed(this,300);
                    }
                }
            }
        }, 500);
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.iconplay);
                    if (fragment_dia_nhac.objectAnimator != null){
                        fragment_dia_nhac.objectAnimator.pause();
                    }
                }else {
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.iconpause);
                    if (fragment_dia_nhac.objectAnimator != null){
                        fragment_dia_nhac.objectAnimator.resume();
                    }
                }
            }
        });
        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat == false){
                    if (random == true){
                        random = false;
                        imgRepeat.setImageResource(R.drawable.iconsyned);
                        imgRandom.setImageResource(R.drawable.iconsuffle);
                    }
                    imgRepeat.setImageResource(R.drawable.iconsyned);
                    repeat = true;
                }else {
                    imgRepeat.setImageResource(R.drawable.iconrepeat);
                    repeat = false;
                }
            }
        });
        imgRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (random == false){
                    if (repeat == true){
                        repeat = false;
                        imgRandom.setImageResource(R.drawable.iconshuffled);
                        imgRepeat.setImageResource(R.drawable.iconrepeat);
                    }
                    imgRandom.setImageResource(R.drawable.iconshuffled);
                    random = true;
                }else {
                    imgRandom.setImageResource(R.drawable.iconsuffle);
                    random = false;
                }
            }
        });
        seekBarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangBaiHat.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < mangBaiHat.size()){
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position++;
                        if (repeat == true){
                            if (position == 0){
                                position = mangBaiHat.size();
                            }
                            position--;

                        }
                        if (random == true){
                            Random random = new Random();
                            int index = random.nextInt(mangBaiHat.size());
                            if (index == position){
                                position = index -1;
                            }
                            position = index;
                        }
                        if (position > mangBaiHat.size()-1){
                            position = 0;
                        }
                        new PlayMP3().execute(mangBaiHat.get(position).getLinkbaihat());
                        fragment_dia_nhac.PlayNhac(mangBaiHat.get(position).getHinhbaihat());
                        getSupportActionBar().setTitle(mangBaiHat.get(position).getTenbaihat());
                        UpdateTime();
                    }
                }
                imgPre.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPre.setClickable(true);
                        imgNext.setClickable(true);
                    }
                },3000);

            }
        });

        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mangBaiHat.size() > 0){
                    if (mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < mangBaiHat.size()){
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position--;
                        if (position < 0){
                            position = mangBaiHat.size()-1;
                        }
                        if (repeat == true){
                            position++;

                        }
                        if (random == true){
                            Random random = new Random();
                            int index = random.nextInt(mangBaiHat.size());
                            if (index == position){
                                position = index -1;
                            }
                            position = index;
                        }
                        new PlayMP3().execute(mangBaiHat.get(position).getLinkbaihat());
                        fragment_dia_nhac.PlayNhac(mangBaiHat.get(position).getHinhbaihat());
                        getSupportActionBar().setTitle(mangBaiHat.get(position).getTenbaihat());
                        UpdateTime();
                    }
                }
                imgPre.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPre.setClickable(true);
                        imgNext.setClickable(true);
                    }
                },3000);
            }
        });
    }

    private void GetDataFromIntent() {
        Intent intent = getIntent();
        mangBaiHat.clear();
        if (intent != null){
            if (intent.hasExtra("cakhuc")){
                BaiHat baiHat = (BaiHat) intent.getParcelableExtra("cakhuc");
                mangBaiHat.add(baiHat);
            }
            if  (intent.hasExtra("danhsachcakhuc")){
                ArrayList<BaiHat> baiHatArrayList = intent.getParcelableArrayListExtra("danhsachcakhuc");
                mangBaiHat = baiHatArrayList;
            }
        }else Toast.makeText(this, "Loi!", Toast.LENGTH_SHORT).show();
    }

    class PlayMP3 extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.setDataSource(s);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            UpdateTime();
        }
    }


    private void init() {
        toolbarPlayNhac = findViewById(R.id.toolbarPlayNhac);
        txtTimSong = findViewById(R.id.textViewTimeSong);
        txtTotalTimeSong = findViewById(R.id.textViewTotalTimeSong);
        seekBarSong = findViewById(R.id.seekbarSong);
        imgNext = findViewById(R.id.imagebuttonNext);
        imgPlay = findViewById(R.id.imagebuttonPlay);
        imgPre = findViewById(R.id.imagebuttonPreview);
        imgRandom = findViewById(R.id.imagebuttonSuffle);
        imgRepeat = findViewById(R.id.imagebuttonRepeat);
        viewPagerPlayNhac = findViewById(R.id.viewPagerPlayNhac);

        setSupportActionBar(toolbarPlayNhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Play music");
        toolbarPlayNhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();
                mangBaiHat.clear();
            }
        });
        toolbarPlayNhac.setTitleTextColor(Color.WHITE);
        fragment_dia_nhac = new Fragment_Dia_Nhac();
        fragment_play_danh_sach_bai_hat = new Fragment_Play_Danh_Sach_Bai_Hat();
        viewPagerPlaylistNhac = new ViewPagerPlaylistNhac(getSupportFragmentManager());
        viewPagerPlaylistNhac.addFragment(fragment_play_danh_sach_bai_hat);
        viewPagerPlaylistNhac.addFragment(fragment_dia_nhac);
        viewPagerPlayNhac.setAdapter(viewPagerPlaylistNhac);

        fragment_dia_nhac = (Fragment_Dia_Nhac) viewPagerPlaylistNhac.getItem(1);
        Log.d("BBB", mangBaiHat.get(0).getLinkbaihat() + "");
        if (mangBaiHat.size() > 0){
            getSupportActionBar().setTitle(mangBaiHat.get(0).getTenbaihat());

            new PlayMP3().execute(mangBaiHat.get(0).getLinkbaihat());
            imgPlay.setImageResource(R.drawable.iconpause);
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotalTimeSong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBarSong.setMax(mediaPlayer.getDuration());
    }
    private void UpdateTime(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null){
                    seekBarSong.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this,300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        },300);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next==true){
                        if (position < mangBaiHat.size()){
                            imgPlay.setImageResource(R.drawable.iconpause);
                            position++;
                            if (repeat == true){
                                if (position == 0){
                                    position = mangBaiHat.size();
                                }
                                position--;

                            }
                            if (random == true){
                                Random random = new Random();
                                int index = random.nextInt(mangBaiHat.size());
                                if (index == position){
                                    position = index -1;
                                }
                                position = index;
                            }
                            if (position > mangBaiHat.size()-1){
                                position = 0;
                            }
                            new PlayMP3().execute(mangBaiHat.get(position).getLinkbaihat());
                            fragment_dia_nhac.PlayNhac(mangBaiHat.get(position).getHinhbaihat());
                            getSupportActionBar().setTitle(mangBaiHat.get(position).getTenbaihat());
                        }
                    imgPre.setClickable(false);
                    imgNext.setClickable(false);
                    Handler handler1 = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgPre.setClickable(true);
                            imgNext.setClickable(true);
                        }
                    },3000);

                    next = false;
                    handler1.removeCallbacks(this::run);
                }else {
                    handler1.postDelayed(this::run,1000);
                }
            }
        },1000);
    }
}