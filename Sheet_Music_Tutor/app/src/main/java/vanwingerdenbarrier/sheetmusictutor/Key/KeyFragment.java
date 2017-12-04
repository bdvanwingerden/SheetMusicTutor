package vanwingerdenbarrier.sheetmusictutor.Key;
/**
 * Created by Dorian Barrier 9/28/17
 * Class that implements the keyboard view
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vanwingerdenbarrier.sheetmusictutor.Game.AnswerDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Duration;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Tone;


public class KeyFragment extends Fragment implements AnswerDisplay {

    Display callback;

    private SoundPool soundPool;

    /*Ints to represent each sound*/
    private int sa,sb,sc,sd,se,sf,sg,scs,sds,sfs,sgs,sas;

    /**Context of class that will be implementing sounds into buttons*/
    private Context context;

    private View view;

    /**Button corresponding to notes*/
    private Button a,b,c,d,e,f,g,cs,ds,fs,gs,as,a2,b2,c2,d2,e2,f2,g2;

    /**
     * Finds the reference to the buttons
     * calls keysound2 to implement sound
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.keyboard_fragment, container, false);

        context = this.getContext();
        a  = view.findViewById(R.id.a);
        a2 = view.findViewById(R.id.a2);
        as = view.findViewById(R.id.as);
        b  = view.findViewById(R.id.b);
        b2 = view.findViewById(R.id.b2);
        c  = view.findViewById(R.id.c);
        cs = view.findViewById(R.id.cs);
        c2 = view.findViewById(R.id.c2);
        d  = view.findViewById(R.id.d);
        ds = view.findViewById(R.id.ds);
        d2 = view.findViewById(R.id.d2);
        e  = view.findViewById(R.id.e);
        e2 = view.findViewById(R.id.e2);
        f  = view.findViewById(R.id.f);
        fs = view.findViewById(R.id.fs);
        f2 = view.findViewById(R.id.f2);
        g  = view.findViewById(R.id.g);
        gs = view.findViewById(R.id.gs);
        g2 = view.findViewById(R.id.g2);

        createPool();

        return view;
    }//end onCreate

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
                        callback.answerPressed(new Note(Tone.A,5, Duration.QUARTER), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        a.setBackgroundResource(R.drawable.border);
                        a2.setBackgroundResource(R.drawable.border);
                        callback.answerPressed(new Note(Tone.A, 5, Duration.QUARTER), event);
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
                        callback.answerPressed(new Note(Tone.B,5, Duration.QUARTER), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        b.setBackgroundResource(R.drawable.border);
                        b2.setBackgroundResource(R.drawable.border);
                        callback.answerPressed(new Note(Tone.B, 5, Duration.QUARTER), event);
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
                        callback.answerPressed(new Note(Tone.C,5, Duration.QUARTER), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        c.setBackgroundResource(R.drawable.border);
                        c2.setBackgroundResource(R.drawable.border);
                        callback.answerPressed(new Note(Tone.C, 5, Duration.QUARTER), event);
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
                        callback.answerPressed(new Note(Tone.D,5, Duration.QUARTER), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        d.setBackgroundResource(R.drawable.border);
                        d2.setBackgroundResource(R.drawable.border);
                        callback.answerPressed(new Note(Tone.D, 5, Duration.QUARTER), event);
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
                        callback.answerPressed(new Note(Tone.E,5, Duration.QUARTER), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        e.setBackgroundResource(R.drawable.border);
                        e2.setBackgroundResource(R.drawable.border);
                        callback.answerPressed(new Note(Tone.E, 5, Duration.QUARTER), event);
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
                        callback.answerPressed(new Note(Tone.F,5, Duration.QUARTER), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        f.setBackgroundResource(R.drawable.border);
                        f2.setBackgroundResource(R.drawable.border);
                        callback.answerPressed(new Note(Tone.F, 5, Duration.QUARTER), event);
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
                        callback.answerPressed(new Note(Tone.G,5, Duration.QUARTER), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        g.setBackgroundResource(R.drawable.border);
                        g2.setBackgroundResource(R.drawable.border);
                        callback.answerPressed(new Note(Tone.G, 5, Duration.QUARTER), event);
                        return false;
                }
                return false;
            }
        });

        //TODO ADD SHARPS

    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callback = (Display) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

}//end class keyboard


