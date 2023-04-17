package com.example.birdsentinel1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;

public class NewMainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    String cinjenica;

    String[] cinjenice = {"Golubovi mogu vidjeti ultraljubičasto zračenje", "Ptice se ne znoje, jer nemaju znojne žlijezde",
            "Najveće jaje u odnosu na pticu koja ga je izlegla je jaje ptice Kiwi. Jaje je veliko kao 1/3 ptice.",
            "Ptice su životinje koje najbrže dišu zbog održavanja metabolizma na visokom nivou tokom letenja",
            "Najviše žumanaca nađeno u jednom jajetu je 9", "Ptica sa najdužim kljunom je Australski pelikan. Kljun mu je dug 47 cm",
            "Ćurani mogu doživjeti srčani udar", "Rode su jedine ptice koje se ne oglašavaju", "Istraživači navode da guske, patke i labudovi, koji zimu provode u močvarama sjeverne Evrope, mijenjaju svoje migracione navike usljed porasta temperature",
            "Danas postoji oko 10000 poznatih vrsta ptica. Najviše ih živi u tropskim šumama Južne Amerike, preko 3000. Antartik ima tek oko 65 različitih vrsta ptica.",
            "1784. godine Benjamin Franklin nije bio zadovoljan izborom orla za simbol Amerike. Želio je da to bude ćurka.",
            "Rimski imperator Gaius Julius Caesar je za bitke dresirao ptice lešinare da ubijaju golubove pismonoše njegovih neprijatelja."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Notifikacija", "Moja notifikacija", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener
                , mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;


        Button dugmeNagadjanje = findViewById(R.id.idnagadjanje);
        dugmeNagadjanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewMainActivity.this, "Srecno!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(NewMainActivity.this, NagadjanjePticaActivity.class);
                startActivity(i);
            }
        });
        Button dugmePrikazSacuvanih = findViewById(R.id.zabavnecinjeniceid);
        dugmePrikazSacuvanih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random rand = new Random();

                cinjenica = cinjenice[rand.nextInt(cinjenice.length)];

                NotificationCompat.Builder builder = new NotificationCompat.Builder(NewMainActivity.this, "Notifikacija");
                builder.setContentTitle("Da li ste znali...?");
                builder.setContentText(cinjenica);
                builder.setSmallIcon(R.drawable.bezpozadine__1_);
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(cinjenica))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setAutoCancel(true);

                Toast.makeText(NewMainActivity.this, "Proverite vaša obaveštenja!", Toast.LENGTH_SHORT).show();
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NewMainActivity.this);
                managerCompat.notify(1, builder.build());

            }
        });


        Button mapa = findViewById(R.id.prikazpolokacijiid);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewMainActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

    }


    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 12) {

                Random rand = new Random();

                cinjenica = cinjenice[rand.nextInt(cinjenice.length)];

                NotificationCompat.Builder builder = new NotificationCompat.Builder(NewMainActivity.this, "Notifikacija");
                builder.setContentTitle("Da li ste znali...?");
                builder.setContentText(cinjenica);
                builder.setSmallIcon(R.drawable.bezpozadine__1_);
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(cinjenica))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NewMainActivity.this);
                managerCompat.notify(1, builder.build());
                Toast.makeText(getApplicationContext(), "Izmućkan sam, proveri obaveštenja!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

}

