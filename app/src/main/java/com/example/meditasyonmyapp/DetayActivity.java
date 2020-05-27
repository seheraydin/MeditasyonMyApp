package com.example.meditasyonmyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

public class DetayActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener{

    Bundle bundle;
    SQLiteHandler veritabani;
    Cursor imlec;
    String gelenID, id, baslik, aciklama, resim, ses, favori, tarih, kategori;
    ImageView detayResim;
    SeekBar seekBar;
    TextView basYazi, sonYazi;
    Button playBtn, favoriBtn;

    private MediaPlayer mediaPlayer;
    private int toplamSure;
    private final Handler handler= new Handler();

    public DetayActivity() {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);

        veritabani = new SQLiteHandler(getApplicationContext());
        detayResim=findViewById(R.id.idDetayImage);
        seekBar =findViewById(R.id.idSeekbarPlay);
        basYazi=findViewById(R.id.idTextBas);
        sonYazi=findViewById(R.id.idTextSon);
        playBtn = findViewById(R.id.idPlayButton);
        favoriBtn = findViewById(R.id.idFavoriButton);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(mediaPlayer.isPlaying()){
                    int sure =(toplamSure / 100) * seekBar.getProgress();
                    mediaPlayer.seekTo(sure);
                }
                return false;
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaPlayer.setDataSource(ses);
                    mediaPlayer.prepare();

                }catch (Exception e){
                    Log.e("hata oluştu",e.getLocalizedMessage());

                }
                toplamSure = mediaPlayer.getDuration();

                @SuppressLint("DefaultLocale") String toplamSureYazisi = String.format("%02d:%02d:%02d:",
                        TimeUnit.MILLISECONDS.toHours(toplamSure)-
                                TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(toplamSure)),
                        TimeUnit.MILLISECONDS.toMinutes(toplamSure)-
                                TimeUnit.HOURS.toMinutes(toplamSure)-(TimeUnit.MILLISECONDS.toHours(toplamSure)),
                        TimeUnit.MILLISECONDS.toHours(toplamSure)-
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(toplamSure))
                );
                sonYazi.setText(toplamSureYazisi);

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    playBtn.setBackgroundResource(R.drawable.play);
                }else{
                    mediaPlayer.start();
                    playBtn.setBackgroundResource(R.drawable.pause);
                }
                seekBarGuncelle();
            }


            private void seekBarGuncelle() {
                final int anlikUzunSure = mediaPlayer.getCurrentPosition();
                seekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/toplamSure*100)));
                if (mediaPlayer.isPlaying()){
                    Runnable hareket = new Runnable() {
                        @Override
                        public void run() {
                            seekBarGuncelle();

                            @SuppressLint("DefaultLocale") String anlikSureYazisi = String.format("%02d:%02d:%02d:",
                                    TimeUnit.MILLISECONDS.toHours(anlikUzunSure)-
                                            TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(anlikUzunSure)),
                                    TimeUnit.MILLISECONDS.toMinutes(anlikUzunSure)-
                                            TimeUnit.HOURS.toMinutes(toplamSure)-(TimeUnit.MILLISECONDS.toHours(anlikUzunSure)),
                                    TimeUnit.MILLISECONDS.toHours(anlikUzunSure)-
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(anlikUzunSure))
                            );

                            basYazi.setText(anlikSureYazisi);

                        }
                    };
                    handler.postDelayed(hareket,1000);
                }
            }

        });


        bundle = getIntent().getExtras();
        if (bundle != null) {
            gelenID = bundle.getString("anahtarDegeri");
            imlec = veritabani.getWritableDatabase().rawQuery("SELECT * FROM veriler WHERE id = '" + gelenID + "'", null);

            while (imlec.moveToNext()) {
                id = imlec.getString(imlec.getColumnIndex("id"));
                baslik = imlec.getString(imlec.getColumnIndex("baslik"));
                aciklama = imlec.getString(imlec.getColumnIndex("aciklama"));
                resim = "http://mistikyol.com/mistikmobil/thumbnails/" + imlec.getString(imlec.getColumnIndex("resim"));
                ses = "http://mistikyol.com/mistikmobil/audios/" + imlec.getString(imlec.getColumnIndex("ses"));
                favori = imlec.getString(imlec.getColumnIndex("favori"));
                tarih = imlec.getString(imlec.getColumnIndex("tarih"));
                kategori = imlec.getString(imlec.getColumnIndex("kategori"));
            }
        }
        Picasso.with(this).load(resim).into(detayResim);

        if(favori.equals("1")){
            favoriBtn.setBackgroundResource(R.drawable.minus);
        }
        else
        {
            favoriBtn.setBackgroundResource(R.drawable.plus);
        }
        favoriBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favori.equals("1")){
                    veritabani.favoriDurumu(id,"0");
                    favoriBtn.setBackgroundResource(R.drawable.plus);
                    Toast.makeText(DetayActivity.this, "Favorilerden Çıkarıldı", Toast.LENGTH_SHORT).show();
                    favori ="0";
                }
                else
                {
                    veritabani.favoriDurumu(id,"1");
                    favoriBtn.setBackgroundResource(R.drawable.minus);
                    Toast.makeText(DetayActivity.this, "Favorilerden Eklendi", Toast.LENGTH_SHORT).show();
                    favori="1";
                }

            }
        });

    }


    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        seekBar.setSecondaryProgress(i);

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playBtn.setBackgroundResource(R.drawable.play);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();

    }
}
