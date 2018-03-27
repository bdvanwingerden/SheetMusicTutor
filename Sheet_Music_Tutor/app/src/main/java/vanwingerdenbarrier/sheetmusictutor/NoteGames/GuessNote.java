package vanwingerdenbarrier.sheetmusictutor.NoteGames;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import vanwingerdenbarrier.sheetmusictutor.Game.AnswerDisplay;

import vanwingerdenbarrier.sheetmusictutor.R;


/**
 * Created by Doriangh4 on 3/25/2018.
 */

public class GuessNote extends android.support.v4.app.Fragment implements AnswerDisplay {

    View view;

    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12;

    Button[] buttons = {b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12};

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

        return view;
    }//end onCreate()

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





}//end class GuessNote
