package vanwingerdenbarrier.sheetmusictutor.Key;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import vanwingerdenbarrier.sheetmusictutor.R;

/**
 * Created by Doriangh4 on 9/28/2017.
 * API that may be used to implement keynote sounds into buttons
 */

public class KeySound2 {

    /**Sound pool of the notes being played*/
    private SoundPool soundPool;

    /*Ints to represent each sound*/
    private int sa,sb,sc,sd,se,sf,sg,scs,sds,sfs,sgs,sas;



    /**Button corresponding to notes*/
    private Button a,b,c,d,e,f,g,cs,ds,fs,gs,as,a2,b2,c2,d2,e2,f2,g2;

    /**Context of class that will be implementing sounds into buttons*/
    private Context context;

    /**
     * Takes notes that will be given sound as arguments
     * Could potentially pass an array of buttons in the future
     * @param a note button
     * @param b note button
     * @param c note button
     * @param d note button
     * @param e note button
     * @param f note button
     * @param g note button
     * @param cs note button
     * @param ds note button
     * @param fs note button
     * @param gs note button
     * @param as note button
     * @param context of the class using the sounds
     */
    public KeySound2(Button a, Button b, Button c, Button d, Button e, Button f, Button g,Button cs, Button ds,
                     Button fs,Button gs, Button as, Button a2, Button b2, Button c2, Button d2, Button e2, Button f2, Button g2,Context context){
        this.context = context;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
        this.cs = cs;
        this.ds = ds;
        this.fs = fs;
        this.gs = gs;
        this.as = as;
        this.a2 = a2;
        this.b2 = b2;
        this.c2 = c2;
        this.d2 = d2;
        this.e2 = e2;
        this.f2 = f2;
        this.g2 = g2;

    }


    public void aButtons(){
        a.setBackgroundColor(Color.RED);
        a2.setBackgroundColor(Color.RED);
    }

    public void aButtons2(){

    }


    /**
     * Creates a sound pool then loads in the sounds from .wav files for each sound
     */
    public void createPool() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        } else {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        /*Load in sound files*/
        sa = soundPool.load(context, R.raw.a, 1);
        sb = soundPool.load(context, R.raw.b, 1);
        sc = soundPool.load(context, R.raw.c, 1);
        sd = soundPool.load(context, R.raw.d, 1);
        se = soundPool.load(context, R.raw.e, 1);
        sf = soundPool.load(context, R.raw.f, 1);
        sg = soundPool.load(context, R.raw.g, 1);
        scs = soundPool.load(context, R.raw.cs, 1);
        sds = soundPool.load(context, R.raw.eb, 1);
        sfs = soundPool.load(context, R.raw.fs, 1);
        sgs = soundPool.load(context, R.raw.gs, 1);
        sas = soundPool.load(context, R.raw.bb, 1);

        /**
         * Implements the action for the specified button.
         * Will Change red and play a sound if pressed
         * When unpressed will change key back to original color
         */
        a.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        soundPool.play(sa, 1,1,0,0,1);
                        a.setBackgroundColor(Color.RED);
                        a2.setBackgroundColor(Color.RED);
                        return false;
                    case MotionEvent.ACTION_UP:
                        a.setBackgroundResource(R.drawable.border);
                        a2.setBackgroundResource(R.drawable.border);
                        return false;
                }
                return false;
            }
        });

        b.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        soundPool.play(sb, 1,1,0,0,1);
                        b.setBackgroundColor(Color.RED);
                        b2.setBackgroundColor(Color.RED);
                        return false;
                    case MotionEvent.ACTION_UP:
                        b.setBackgroundResource(R.drawable.border);
                        b2.setBackgroundResource(R.drawable.border);
                        return false;
                }
                return false;
            }
        });

        c.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        soundPool.play(sc, 1,1,0,0,1);
                        c.setBackgroundColor(Color.RED);
                        c2.setBackgroundColor(Color.RED);
                        return false;
                    case MotionEvent.ACTION_UP:
                        c.setBackgroundResource(R.drawable.border);
                        c2.setBackgroundResource(R.drawable.border);
                        return false;
                }
                return false;
            }
        });

        d.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        soundPool.play(sd, 1,1,0,0,1);
                        d.setBackgroundColor(Color.RED);
                        d2.setBackgroundColor(Color.RED);
                        return false;
                    case MotionEvent.ACTION_UP:
                        d.setBackgroundResource(R.drawable.border);
                        d2.setBackgroundResource(R.drawable.border);
                        return false;
                }
                return false;
            }
        });

        e.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        soundPool.play(se, 1,1,0,0,1);
                        e.setBackgroundColor(Color.RED);
                        e2.setBackgroundColor(Color.RED);
                        return false;
                    case MotionEvent.ACTION_UP:
                        e.setBackgroundResource(R.drawable.border);
                        e2.setBackgroundResource(R.drawable.border);
                        return false;
                }
                return false;
            }
        });

        f.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        soundPool.play(sf, 1,1,0,0,1);
                        f.setBackgroundColor(Color.RED);
                        f2.setBackgroundColor(Color.RED);
                        return false;
                    case MotionEvent.ACTION_UP:
                        f.setBackgroundResource(R.drawable.border);
                        f2.setBackgroundResource(R.drawable.border);
                        return false;
                }
                return false;
            }
        });

        g.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        soundPool.play(sg, 1,1,0,0,1);
                        g.setBackgroundColor(Color.RED);
                        g2.setBackgroundColor(Color.RED);
                        return false;
                    case MotionEvent.ACTION_UP:
                        g.setBackgroundResource(R.drawable.border);
                        g2.setBackgroundResource(R.drawable.border);
                        return false;
                }
                return false;
            }
        });


        cs.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        soundPool.play(scs, 1,1,0,0,1);
                        cs.setBackgroundColor(Color.RED);
                        return false;
                    case MotionEvent.ACTION_UP:
                        cs.setBackgroundResource(R.color.black);
                        return false;
                }
                return false;
            }
        });

        ds.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        soundPool.play(sds, 1,1,0,0,1);
                        ds.setBackgroundColor(Color.RED);
                        return false;
                    case MotionEvent.ACTION_UP:
                        ds.setBackgroundResource(R.color.black);
                        return false;
                }
                return false;
            }
        });

        fs.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        soundPool.play(sfs, 1,1,0,0,1);
                        fs.setBackgroundColor(Color.RED);
                        return false;
                    case MotionEvent.ACTION_UP:
                        fs.setBackgroundResource(R.color.black);
                        return false;
                }
                return false;
            }
        });

        gs.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        soundPool.play(sgs, 1,1,0,0,1);
                        gs.setBackgroundColor(Color.RED);
                        return false;
                    case MotionEvent.ACTION_UP:
                        gs.setBackgroundResource(R.color.black);
                        return false;
                }
                return false;
            }
        });

        as.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        soundPool.play(sas, 1,1,0,0,1);
                        as.setBackgroundColor(Color.RED);
                        return false;
                    case MotionEvent.ACTION_UP:
                        as.setBackgroundResource(R.color.black);
                        return false;
                }
                return false;
            }
        });


    }//end createPool


}//end KeySound2

