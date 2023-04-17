package com.example.birdsentinel1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class NagadjanjeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nagadjanje);
        getSupportActionBar().hide();



        MediaPlayer tacan = MediaPlayer.create(this,R.raw.tacan);
        MediaPlayer netacan = MediaPlayer.create(this,R.raw.netacan);

        Button dugme1, dugme2, dugme3, dugme4;
        Random rand = new Random();
        List<String> odabirStringova = Arrays.asList("svraka", "jastreb", "fazan", "golub", "grlica", "detlic",
                "vrana", "vrabac", "kos", "orao", "papagaj",
                 "kolibri", "tukan", "soko", "pelikan", "noj", "roda",
                "prepelica", "patka", "flamingo", "zdral",
                "zeba", "lastavica", "sova", "šojka");

        List<String> kopija = new ArrayList<>(odabirStringova);


        dugme1 = findViewById(R.id.nagadjanje1);
        dugme2 = findViewById(R.id.nagadjanje2);
        dugme3 = findViewById(R.id.nagadjanje3);
        dugme4 = findViewById(R.id.nagadjanje4);
        Button[] dugmad = {dugme1,dugme2,dugme3,dugme4};

        Random rand2 = new Random();
        String sacuvan = getIntent().getStringExtra("imePtice");
        dugmad[rand2.nextInt(dugmad.length)].setText(sacuvan);
        kopija.remove(sacuvan);


        if(dugme1.getText().toString().equalsIgnoreCase("dugme1")){
            dugme1.setText(kopija.get(rand.nextInt(kopija.size())));
            for (int i = 0; i < kopija.size(); i++) {
                if (dugme1.getText() == kopija.get(i)) {
                    kopija.remove(kopija.get(i));
                }
            }
        }
        if(dugme2.getText().toString().equalsIgnoreCase("dugme2")){
            dugme2.setText(kopija.get(rand.nextInt(kopija.size())));
            for (int i = 0; i < kopija.size(); i++) {
                if (dugme2.getText() == kopija.get(i)) {
                    kopija.remove(kopija.get(i));
                }
            }
        }
        if(dugme3.getText().toString().equalsIgnoreCase("dugme3")){
            dugme3.setText(kopija.get(rand.nextInt(kopija.size())));
            for (int i = 0; i < kopija.size(); i++) {
                if (dugme3.getText() == kopija.get(i)) {
                    kopija.remove(kopija.get(i));
                }
            }
        }
        if(dugme4.getText().toString().equalsIgnoreCase("dugme4")){
            dugme4.setText(kopija.get(rand.nextInt(kopija.size())));
            //for (int i = 0; i < kopija.size(); i++) {           Ako bude vise dugmadi, morace for za svako osim za poslednje
//               if (dugme4.getText() == kopija.get(i)) {
//                   kopija.remove(kopija.get(i));
//            }
//        }
        }

        Intent vracanje = new Intent(NagadjanjeActivity.this, NewMainActivity.class);
        dugme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isGolden(dugme1,sacuvan)){

                    vracanje.putExtra("odgovor",dugme1.getText().toString());
                    Toast.makeText(NagadjanjeActivity.this, "Vas odgovor je tacan!", Toast.LENGTH_LONG).show();
                    tacan.start();
                    startActivity(vracanje);
                    finish();

                }
                else{
                    Toast.makeText(NagadjanjeActivity.this, "Vas odgovor je pogrešan, više sreće drugi put!", Toast.LENGTH_LONG).show();
                    netacan.start();
                    startActivity(vracanje);
                    finish();
                }
            }
        });
        dugme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isGolden(dugme2,sacuvan)){

                    Toast.makeText(NagadjanjeActivity.this, "Vas odgovor je tacan!", Toast.LENGTH_LONG).show();
                    tacan.start();
                    startActivity(vracanje);
                    finish();

                }else{

                    Toast.makeText(NagadjanjeActivity.this, "Vas odgovor je pogrešan, više sreće drugi put!", Toast.LENGTH_LONG).show();
                    netacan.start();
                    startActivity(vracanje);
                    finish();

                }
            }
        });

        dugme3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isGolden(dugme3,sacuvan)){

                    Toast.makeText(NagadjanjeActivity.this, "Vas odgovor je tacan!", Toast.LENGTH_LONG).show();
                    tacan.start();
                    startActivity(vracanje);
                    finish();

                }else{

                    Toast.makeText(NagadjanjeActivity.this, "Vas odgovor je pogrešan, više sreće drugi put!", Toast.LENGTH_LONG).show();
                    netacan.start();
                    startActivity(vracanje);
                    finish();
                }
            }
        });
        dugme4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isGolden(dugme4,sacuvan)){

                    Toast.makeText(NagadjanjeActivity.this, "Vas odgovor je tacan!", Toast.LENGTH_LONG).show();
                    tacan.start();
                    startActivity(vracanje);
                    finish();

                }else{

                    Toast.makeText(NagadjanjeActivity.this, "Vas odgovor je pogrešan, više sreće drugi put!", Toast.LENGTH_LONG).show();
                    netacan.start();
                    startActivity(vracanje);
                    finish();
                }
            }
        });
    }

    public Boolean isGolden(Button dugme,String s) {
        if(dugme.getText().toString().equalsIgnoreCase(s)){
            return true;
        }
        return false;
    }
}