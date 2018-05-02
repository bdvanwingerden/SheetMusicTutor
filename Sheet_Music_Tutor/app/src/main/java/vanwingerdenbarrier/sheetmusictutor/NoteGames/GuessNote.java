package vanwingerdenbarrier.sheetmusictutor.NoteGames;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import vanwingerdenbarrier.sheetmusictutor.Game.QuestionDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;


/**
 * Created by Doriangh4 on 3/25/2018.
 */

public class GuessNote extends android.support.v4.app.Fragment implements QuestionDisplay, View.OnClickListener {

    /**Current round*/
    int round;

    private SoundPool soundPool;

    /*Ints to represent each sound*/
    private int sa,sb,sc,sd,se,sf,sg,scs,sds,sfs,sgs,sas;

    /**Context of class that will be implementing sounds into buttons*/
    private Context context;

    View view;

    /**Button corresponding to notes*/
    private Button a,b,c,d,e,f,g,cs,ds,fs,gs,as;

   // Button[] buttons = {b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12};

    /*callback to the gameactivity class */
    Display callback;

    /**
     * inflates the view to fit its calling container
     *
     * @param inflater           inflates the view
     * @param container          the size to inflate
     * @param savedInstanceState
     * @return the view created for the game
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity();

        view = (ViewGroup) inflater.inflate(R.layout.fragment_guess_note,
                container, false);

        context = this.getContext();
        a  = view.findViewById(R.id.a1);
        as = view.findViewById(R.id.as2);
        b  = view.findViewById(R.id.b1);
        c  = view.findViewById(R.id.c1);
        cs = view.findViewById(R.id.cs2);
        d  = view.findViewById(R.id.d1);
        ds = view.findViewById(R.id.ds2);
        e  = view.findViewById(R.id.e1);
        f  = view.findViewById(R.id.f1);
        fs = view.findViewById(R.id.fs2);
        g  = view.findViewById(R.id.g1);
        gs = view.findViewById(R.id.gs2);

        a.setOnClickListener(this);
        as.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        cs.setOnClickListener(this);
        d.setOnClickListener(this);
        ds.setOnClickListener(this);
        e.setOnClickListener(this);
        f.setOnClickListener(this);
        fs.setOnClickListener(this);
        g.setOnClickListener(this);
        gs.setOnClickListener(this);

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Ear Trainer!");
        alertDialog.setMessage("Correctly guess the note and you get a point!");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        dialogInterface.dismiss();
                    }
                });

        alertDialog.setCancelable(false);

        alertDialog.show();

        createPool();

        round = 1;

        return view;
    }//end onCreate()

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
        sa = soundPool.load(context, R.raw.a5, 1);
        sb = soundPool.load(context, R.raw.b5, 1);
        sc = soundPool.load(context, R.raw.c5, 1);
        sd = soundPool.load(context, R.raw.d5, 1);
        se = soundPool.load(context, R.raw.e4, 1);
        sf = soundPool.load(context, R.raw.f4, 1);
        sg = soundPool.load(context, R.raw.g4, 1);
        scs = soundPool.load(context, R.raw.cs5, 1);
        sds = soundPool.load(context, R.raw.ds5, 1);
        sfs = soundPool.load(context, R.raw.fs4, 1);
        sgs = soundPool.load(context, R.raw.gs4, 1);
        sas = soundPool.load(context, R.raw.as5, 1);
    }


    /**
     * initilizes the callback once it is attached
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (Display) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch(v.getId()){
            case R.id.cs2:
                soundPool.play(scs, 1,1,0,0,1);
                break;
            case R.id.ds2:
                soundPool.play(sds, 1,1,0,0,1);
                break;
            case R.id.fs2:
                soundPool.play(sfs, 1,1,0,0,1);
                break;
            case R.id.gs2:
                soundPool.play(sgs, 1,1,0,0,1);
                break;
            case R.id.as2:
                soundPool.play(sas, 1,1,0,0,1);
                break;
            case R.id.a1:
                soundPool.play(sa, 1,1,0,0,1);
                break;
            case R.id.b1:
                soundPool.play(sb, 1,1,0,0,1);
                break;
            case R.id.c1:
                soundPool.play(sc, 1,1,0,0,1);
                break;
            case R.id.d1:
                soundPool.play(sd, 1,1,0,0,1);
                break;
            case R.id.e1:
                soundPool.play(se, 1,1,0,0,1);
                break;
            case R.id.f1:
                soundPool.play(sf, 1,1,0,0,1);
                break;
            case R.id.g1:
                soundPool.play(sg, 1,1,0,0,1);
                break;
        }

    }

    /**
     * checks if a given String is the correct answer to the current problem
     * @param answer the answer to check
     */
    public void checkIfCorrect(String answer){

        if(answer.equals("A")){//correct
            Toast.makeText(this.getContext(),"Correct", Toast.LENGTH_SHORT);
            setTextVisible(1);
        }else if(answer.equals("B")){//incorrect
            Toast.makeText(this.getContext(),"Incorrect, Try Again", Toast.LENGTH_SHORT);
            setTextVisible(2);
        }

        round++;
    }

    /**
     * Set the text visible once the round is over
     * @param color - if 1 turn green if 2 turn red
     */
    public void setTextVisible(int color){

        if(round == 1){
            if(color == 1) {
                cs.setText("CS");
                cs.setTextColor(getResources().getColor(R.color.achievement_green));
            }
            else if(color == 2) {
                cs.setText("CS");
                cs.setTextColor(getResources().getColor(R.color.key_focused));
            }
        }else if(round == 2){
            if(color == 1) {
                ds.setText("DS");
                ds.setTextColor(getResources().getColor(R.color.achievement_green));
            }
            else if(color == 2) {
                ds.setText("DS");
                ds.setTextColor(getResources().getColor(R.color.key_focused));
            }
        }
        else if(round == 3){
            if(color == 1) {
                fs.setText("FS");
                fs.setTextColor(getResources().getColor(R.color.achievement_green));
            }
            else if(color == 2) {
                fs.setText("FS");
                fs.setTextColor(getResources().getColor(R.color.key_focused));
            }
        }
        else if(round == 4){
            if(color == 1) {
                gs.setText("GS");
                gs.setTextColor(getResources().getColor(R.color.achievement_green));
            }
            else if(color == 2) {
                gs.setText("GS");
                gs.setTextColor(getResources().getColor(R.color.key_focused));
            }
        }
        else if(round == 5){
            if(color == 1) {
                as.setText("AS");
                as.setTextColor(getResources().getColor(R.color.achievement_green));
            }
            else if(color == 2) {
                as.setText("AS");
                as.setTextColor(getResources().getColor(R.color.key_focused));
            }
            //when incrementing round change text
        }
        else if(round == 2){

        }
        else if(round == 2){

        }
        else if(round == 2){

        }
        else if(round == 2){

        }
        else if(round == 2){

        }
        else if(round == 2){

        }
        else if(round == 2){

        }

    }//end setTextVisible


}//end class GuessNote
