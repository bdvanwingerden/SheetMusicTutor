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
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;


public class KeyFragment extends Fragment implements AnswerDisplay {

    Display callback;

    private SoundPool soundPool;

    /*Ints to represent each sound*/
    private int a4, b4, c5, d5, e5, e4, f5, f4, g4, cs5, ds5, fs4, gs4, as4, fs5;

    /**Context of class that will be implementing sounds into buttons*/
    private Context context;

    private View view;

    /**Button corresponding to notes*/
    private Button a,b,c,d,e,f,g,cs,ds,fs,gs,as,a2,b2,c2,d2,e2,f2,g2, e3, e32, f3, f32, f3s;

    Bundle args;

    boolean showKeys;

    /**
     * Finds the reference to the buttons
     * calls keysound2 to implement sound
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        showKeys = new UserList(getContext()).findCurrent().isShowing_key();

        args = getArguments();

        view = inflater.inflate(R.layout.keyboard_fragment, container, false);

        context = this.getContext();

        a = view.findViewById(R.id.a5);
        a2 = view.findViewById(R.id.a5u);
        as = view.findViewById(R.id.as5);
        b = view.findViewById(R.id.b5);
        b2 = view.findViewById(R.id.b5u);
        c = view.findViewById(R.id.c5);
        cs = view.findViewById(R.id.cs5);
        c2 = view.findViewById(R.id.c5u);
        d = view.findViewById(R.id.d5);
        ds = view.findViewById(R.id.ds5);
        d2 = view.findViewById(R.id.d5u);
        e = view.findViewById(R.id.e4);
        e2 = view.findViewById(R.id.e4u);
        f = view.findViewById(R.id.f4);
        fs = view.findViewById(R.id.fs4);
        f2 = view.findViewById(R.id.f4u);
        g = view.findViewById(R.id.g4);
        gs = view.findViewById(R.id.gs4);
        g2 = view.findViewById(R.id.g4u);
        e3 = view.findViewById(R.id.e5);
        e32 = view.findViewById(R.id.e5u);
        f3 = view.findViewById(R.id.f5);
        f3s = view.findViewById(R.id.fs5);
        f32 = view.findViewById(R.id.f5u);

        createPool();

        showLabels(showKeys);

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
        if(args.getInt("mode") == 0 || args.getInt("mode") == 2) {
            a4 = soundPool.load(context, R.raw.a4, 1);
            b4 = soundPool.load(context, R.raw.b4, 1);
            c5 = soundPool.load(context, R.raw.c5, 1);
            d5 = soundPool.load(context, R.raw.d5, 1);
            e5 = soundPool.load(context, R.raw.e5, 1);
            f5 = soundPool.load(context, R.raw.f5, 1);
            e4 = soundPool.load(context, R.raw.e4, 1);
            f4 = soundPool.load(context, R.raw.f4, 1);
            g4 = soundPool.load(context, R.raw.g4, 1);
            as4 = soundPool.load(context, R.raw.as4, 1);
            cs5 = soundPool.load(context, R.raw.cs5, 1);
            ds5 = soundPool.load(context, R.raw.ds5, 1);
            fs4 = soundPool.load(context, R.raw.fs4, 1);
            gs4 = soundPool.load(context, R.raw.gs4, 1);
            fs5 = soundPool.load(context, R.raw.fs5, 1 );

        }else{
            a4 = soundPool.load(context, R.raw.la5, 1);
            as4 = soundPool.load(context, R.raw.las5, 1);
            b4 = soundPool.load(context, R.raw.lb5, 1);
            c5 = soundPool.load(context, R.raw.lc5, 1);
            cs5 = soundPool.load(context, R.raw.lcs5, 1);
            d5 = soundPool.load(context, R.raw.ld5, 1);
            ds5 = soundPool.load(context, R.raw.lds5, 1);
            e4 = soundPool.load(context, R.raw.le4, 1);
            f4 = soundPool.load(context, R.raw.lf4, 1);
            fs4 = soundPool.load(context, R.raw.lfs4, 1);
            g4 = soundPool.load(context, R.raw.lg4, 1);
            gs4 = soundPool.load(context, R.raw.lgs4, 1);
            e5 = soundPool.load(context, R.raw.le5, 1);
            f5 = soundPool.load(context, R.raw.lf5, 1);
            fs5 = soundPool.load(context, R.raw.lfs5, 1);
        }

        if(args.getInt("mode") == 2)
            hideNoteText();//hid note assist text
        else
            setNoteText();//show note assist text

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
                        soundPool.play(a4, 1,1,0,0,1);
                        a.setBackgroundColor(Color.RED);
                        a2.setBackgroundColor(Color.RED);
                        callback.answerPressed(new Note(Tone.A, 5, Duration.QUARTER, false), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        a.setBackgroundResource(R.drawable.border);
                        a2.setBackgroundResource(R.drawable.border);
                       // callback.answerPressed(new Note(Tone.A, 5, Duration.QUARTER), event);
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
                        soundPool.play(b4, 1,1,0,0,1);
                        b.setBackgroundColor(Color.RED);
                        b2.setBackgroundColor(Color.RED);
                        callback.answerPressed(new Note(Tone.B, 5, Duration.QUARTER, false), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        b.setBackgroundResource(R.drawable.border);
                        b2.setBackgroundResource(R.drawable.border);
                        //callback.answerPressed(new Note(Tone.B, 5, Duration.QUARTER), event);
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
                        soundPool.play(c5, 1,1,0,0,1);
                        c.setBackgroundColor(Color.RED);
                        c2.setBackgroundColor(Color.RED);
                        callback.answerPressed(new Note(Tone.C, 5, Duration.QUARTER, false), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        c.setBackgroundResource(R.drawable.border);
                        c2.setBackgroundResource(R.drawable.border);
                        //callback.answerPressed(new Note(Tone.C, 5, Duration.QUARTER), event);
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
                        soundPool.play(d5, 1,1,0,0,1);
                        d.setBackgroundColor(Color.RED);
                        d2.setBackgroundColor(Color.RED);
                        callback.answerPressed(new Note(Tone.D, 5, Duration.QUARTER, false), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        d.setBackgroundResource(R.drawable.border);
                        d2.setBackgroundResource(R.drawable.border);
                        //callback.answerPressed(new Note(Tone.D, 5, Duration.QUARTER), event);
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
                        soundPool.play(e4, 1,1,0,0,1);
                        e.setBackgroundColor(Color.RED);
                        e2.setBackgroundColor(Color.RED);
                        callback.answerPressed(new Note(Tone.E, 4, Duration.QUARTER, false), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        e.setBackgroundResource(R.drawable.border);
                        e2.setBackgroundResource(R.drawable.border);
                        //callback.answerPressed(new Note(Tone.E, 5, Duration.QUARTER), event);
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
                        soundPool.play(f4, 1,1,0,0,1);
                        f.setBackgroundColor(Color.RED);
                        f2.setBackgroundColor(Color.RED);
                        callback.answerPressed(new Note(Tone.F, 4, Duration.QUARTER, false), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        f.setBackgroundResource(R.drawable.border);
                        f2.setBackgroundResource(R.drawable.border);
                        //callback.answerPressed(new Note(Tone.F, 5, Duration.QUARTER), event);
                        return false;
                }
                return false;
            }
        });

        e3.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        soundPool.play(e5, 1,1,0,0,1);
                        e3.setBackgroundColor(Color.RED);
                        e32.setBackgroundColor(Color.RED);
                        callback.answerPressed(new Note(Tone.E, 5, Duration.QUARTER, false), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        e3.setBackgroundResource(R.drawable.border);
                        e32.setBackgroundResource(R.drawable.border);
                        //callback.answerPressed(new Note(Tone.E, 5, Duration.QUARTER), event);
                        return false;
                }
                return false;
            }
        });

        f3.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        soundPool.play(f5, 1,1,0,0,1);
                        f3.setBackgroundColor(Color.RED);
                        f32.setBackgroundColor(Color.RED);
                        callback.answerPressed(new Note(Tone.F, 5, Duration.QUARTER, false), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        f3.setBackgroundResource(R.drawable.border);
                        f32.setBackgroundResource(R.drawable.border);
                        //callback.answerPressed(new Note(Tone.F, 5, Duration.QUARTER), event);
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
                        soundPool.play(g4, 1,1,0,0,1);
                        g.setBackgroundColor(Color.RED);
                        g2.setBackgroundColor(Color.RED);
                        callback.answerPressed(new Note(Tone.G, 4, Duration.QUARTER, false), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        g.setBackgroundResource(R.drawable.border);
                        g2.setBackgroundResource(R.drawable.border);
                        //callback.answerPressed(new Note(Tone.G, 5, Duration.QUARTER), event);
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
                        soundPool.play(cs5, 1,1,0,0,1);
                        cs.setBackgroundColor(Color.RED);
                        callback.answerPressed(new Note(Tone.C, 5, Duration.QUARTER, true), event);
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
                        soundPool.play(ds5, 1,1,0,0,1);
                        ds.setBackgroundColor(Color.RED);
                        callback.answerPressed(new Note(Tone.D, 5, Duration.QUARTER, true), event);
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
                        soundPool.play(fs4, 1,1,0,0,1);
                        callback.answerPressed(new Note(Tone.F, 4, Duration.QUARTER, true), event);
                        fs.setBackgroundColor(Color.RED);
                        return false;
                    case MotionEvent.ACTION_UP:
                        fs.setBackgroundResource(R.color.black);
                        return false;
                }
                return false;
            }
        });

        f3s.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        soundPool.play(fs5, 1,1,0,0,1);
                        callback.answerPressed(new Note(Tone.F, 5, Duration.QUARTER, true), event);
                        f3s.setBackgroundColor(Color.RED);
                        return false;
                    case MotionEvent.ACTION_UP:
                        f3s.setBackgroundResource(R.color.black);
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
                        soundPool.play(gs4, 1,1,0,0,1);
                        gs.setBackgroundColor(Color.RED);
                        callback.answerPressed(new Note(Tone.G, 4, Duration.QUARTER, true), event);
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
                        soundPool.play(as4, 1,1,0,0,1);
                        as.setBackgroundColor(Color.RED);
                        callback.answerPressed(new Note(Tone.A, 5, Duration.QUARTER, true), event);
                        return false;
                    case MotionEvent.ACTION_UP:
                        as.setBackgroundResource(R.color.black);
                        return false;
                }
                return false;
            }
        });

    }

    /**
     * Hides all the notes text on the keyboard
     */
     private void hideNoteText(){

     a.setText(" ");
     as.setText(" ");
     b.setText(" ");
     c.setText(" ");
     cs.setText(" ");
     d.setText(" ");
     ds.setText(" ");
     e.setText(" ");
     f.setText(" ");
     fs.setText(" ");
     g.setText(" ");
     gs.setText(" ");
     e3.setText(" ");
     f3.setText(" ");
     f3s.setText(" ");
     }//end hideNoteText

    /**
     * Sets the text for to asist the player in identifying notes
     */
    private void setNoteText(){

        a.setText("A4");
        as.setText("A#4");
        b.setText("B4");
        c.setText("C5");
        cs.setText("C#5");
        d.setText("D5");
        ds.setText("D#5");
        e.setText("E4");
        f.setText("F4");
        fs.setText("F#4");
        g.setText("G4");
        gs.setText("G#4");
        e3.setText("E5");
        f3.setText("F5");
        f3s.setText("F#5");

    }//end setNoteText


    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callback = (Display) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    /**
     * removes the labels from the keys if showKeys is false
     * @param showKeys boolean determining whether or not to show labels on the keys
     */
    public void showLabels(Boolean showKeys){
        if(!showKeys){
            a.setText("");
            as.setText("");
            b.setText("");
            c.setText("");
            cs.setText("");
            d.setText("");
            ds.setText("");
            e.setText("");
            f.setText("");
            fs.setText("");
            g.setText("");
            gs.setText("");
            e3.setText("");
            f3.setText("");
            f3s.setText("");
        }
    }


}//end class keyboard


