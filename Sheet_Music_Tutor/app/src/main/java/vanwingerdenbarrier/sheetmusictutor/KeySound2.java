package vanwingerdenbarrier.sheetmusictutor;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.View;
import android.widget.Button;

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
    Button a,b,c,d,e,f,g,cs,ds,fs,gs,as;

    /**Context of class that will be implementing sounds into buttons*/
    Context context;

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
                     Button fs,Button gs, Button as, Context context){
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

    }


    /**
     * Creates a sound pool then loads in the sounds from .wav files for each sound
     */
    public void createPool(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        }
        else{
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
         * Implements the specified button's action which will be to play a note
         */
        a.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sa, 1,1,0,0,1);
            }
        });

        /**
         * Implements the specified button's action which will be to play a note
         */
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sb, 1,1,0,0,1);
            }
        });

        /**
         * Implements the specified button's action which will be to play a note
         */
        c.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sc, 1,1,0,0,1);
            }
        });

        /**
         * Implements the specified button's action which will be to play a note
         */
        d.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sd, 1,1,0,0,1);
            }
        });

        /**
         * Implements the specified button's action which will be to play a note
         */
        e.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(se, 1,1,0,0,1);
            }
        });

        /**
         * Implements the specified button's action which will be to play a note
         */
        f.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sf, 1,1,0,0,1);
            }
        });

        /**
         * Implements the specified button's action which will be to play a note
         */
        g.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sg, 1,1,0,0,1);
            }
        });

        /**
         * Implements the specified button's action which will be to play a note
         */
        cs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(scs, 1,1,0,0,1);
            }
        });

        /**
         * Implements the specified button's action which will be to play a note
         */
        ds.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sds, 1,1,0,0,1);
            }
        });

        /**
         * Implements the specified button's action which will be to play a note
         */
        fs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sfs, 1,1,0,0,1);
            }
        });

        /**
         * Implements the specified button's action which will be to play a note
         */
        gs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sgs, 1,1,0,0,1);
            }
        });

        /**
         * Implements the specified button's action which will be to play a note
         */
        as.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                soundPool.play(sas, 1,1,0,0,1);
            }
        });

    }//end createPool


}//end KeySound2

