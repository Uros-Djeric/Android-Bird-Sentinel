package com.example.birdsentinel1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import java.util.Random;

public class NagadjanjePticaActivity extends AppCompatActivity {
    int sacuvanBroj;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nagadjanje_ptica);
        int[] images = {R.drawable.detlic,R.drawable.fazan,R.drawable.flamingo
                ,R.drawable.golub,R.drawable.grlica,R.drawable.jastreb,R.drawable.kolibri,
                R.drawable.kos,R.drawable.kreja,R.drawable.lastavica,R.drawable.noj,
                R.drawable.orao,R.drawable.papagaj,R.drawable.patka,R.drawable.pelikan,
                R.drawable.prepelica,R.drawable.roda,R.drawable.soko,R.drawable.sova,
                R.drawable.svraka,R.drawable.tukan,R.drawable.vrabac,R.drawable.vrana,R.drawable.zdral,
                R.drawable.zeba};
        Random rand = new Random();
        int duzina = images.length;
        int broj = rand.nextInt(duzina);
        System.out.println(images.length);
        System.out.println("Broj "+broj);
        for(int i =0; i<images.length;i++){

            if(broj==i){
                sacuvanBroj=broj;
            }
        }

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(images[broj]);
        String ime = imageView.getResources().getResourceName(images[sacuvanBroj]);
        String[] imena =ime.split("/");
        ime=imena[1];
        imageView.setTag(ime);

        //System.out.println(ime);

        Intent ii = new Intent(NagadjanjePticaActivity.this, NagadjanjeActivity.class);
        ii.putExtra("imePtice",ime);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(ii);
                finish();
            }
        }, 5000);

    }
}