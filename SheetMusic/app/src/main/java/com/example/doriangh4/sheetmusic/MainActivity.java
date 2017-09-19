package com.example.doriangh4.sheetmusic;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    /*Buttons that corespond with each sound*/
    Button a,b,c,d,e,f,g;

    /*Sound pool object*/
    private SoundPool soundPool;

    /*Ints to represent each sound*/
    private int sa,sb,sc,sd,se,sf,sg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a = (Button) findViewById(R.id.a);
        b = (Button) findViewById(R.id.b);
        c = (Button) findViewById(R.id.c);
        d = (Button) findViewById(R.id.d);
        e = (Button) findViewById(R.id.e);
        f = (Button) findViewById(R.id.f);
        g = (Button) findViewById(R.id.g);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        }
        else{
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        sa = soundPool.load(this, R.raw.a, 1);
        sb = soundPool.load(this, R.raw.b, 1);
        sc = soundPool.load(this, R.raw.c, 1);
        sd = soundPool.load(this, R.raw.d, 1);
        se = soundPool.load(this, R.raw.e, 1);
        sf = soundPool.load(this, R.raw.f, 1);
        sg = soundPool.load(this, R.raw.g, 1);

        a.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sa, 1,1,0,0,1);
            }
        });

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sb, 1,1,0,0,1);
            }
        });

        c.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sc, 1,1,0,0,1);
            }
        });

        d.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sd, 1,1,0,0,1);
            }
        });

        e.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(se, 1,1,0,0,1);
            }
        });

        f.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sf, 1,1,0,0,1);
            }
        });

        g.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sg, 1,1,0,0,1);
            }
        });

     }
}
