package vanwingerdenbarrier.sheetmusictutor.NoteGames;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import vanwingerdenbarrier.sheetmusictutor.Game.AnswerDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;

/**
 * Created by Doriangh4 on 3/25/2018.
 */
public class GuessNoteText extends Fragment implements View.OnClickListener, AnswerDisplay{

    /**Current score for this particular game*/
    int score;

    /**
     * Number of lives for each guess
     */
    int attempts;

    /**Writes options based on which not we are on*/
    int round;

    Display callback;

    View view;

    /*callback to the gameactivity class */

    /**Options buttons*/
    Button o1,o2,o3,o4,o5;

    TextView attemptsText;
    TextView scoreText;

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

        view = (ViewGroup) inflater.inflate(R.layout.fragment_guess_note_text,
                container, false);

        o1 = (Button) view.findViewById(R.id.option1);
        o2 = (Button) view.findViewById(R.id.option2);
        o3 = (Button) view.findViewById(R.id.option3);
        o4 = (Button) view.findViewById(R.id.option4);
        o5 = (Button) view.findViewById(R.id.option5);

        o1.setOnClickListener(this);
        o2.setOnClickListener(this);
        o3.setOnClickListener(this);
        o4.setOnClickListener(this);
        o5.setOnClickListener(this);

        o1.setText("CS");
        o2.setText("DS");
        o3.setText("FS");
        o4.setText("GS");
        o5.setText("AS");

        attemptsText = (TextView) view.findViewById(R.id.tryGuess);
        scoreText = (TextView) view.findViewById(R.id.scoreGuess);


        round = 1;

        score = 0;

        attempts = 0;


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

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(v.getId()) {
            case R.id.option1:
                getAnswer(1);
                break;
            case R.id.option2:
                getAnswer(2);
                break;
            case R.id.option3:
                getAnswer(3);
                break;
            case R.id.option4:
                getAnswer(4);
                break;
            case R.id.option5:
                getAnswer(5);
                break;
        }
    }//end onClick

    public void getAnswer(int answer){

        if(round == 1){
            if(answer == 1){
                correct();
            }else{
                if(attempts == 2){
                    noMoreAttempts();
                }else {
                    incorrect();
                }
            }
        }else if(round == 2){
            if(answer == 2){
                correct();
            }else{
                if(attempts == 2){
                    noMoreAttempts();
                }else {
                    incorrect();
                }
            }
        }
        else if(round == 3){
            if(answer == 3){
                correct();
            }else{
                if(attempts == 2){
                    noMoreAttempts();
                }else {
                    incorrect();
                }
            }
        }
        else if(round == 4){
            if(answer == 4){
                correct();
            }else{
                if(attempts == 2){
                    noMoreAttempts();
                }else {
                    incorrect();
                }
            }
        }
        else if(round == 5){
            if(answer == 5){
                correct();
            }else{
                if(attempts == 2){
                    noMoreAttempts();
                }else {
                    incorrect();
                }
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
    }//end getAnswer

    /**
     * Combine this with getAnswer later. Nvm Doesnt work the same
     * @param answer
     */
    public void answerHelper(int answer){
        if(answer == round){
            correct();
        }else{
            if(attempts == 2){
                noMoreAttempts();
            }else {
                incorrect();
            }
        }
    }//end answerHelper

    /**Do this if correct answer*/
    public void correct(){
        callback.answerPressed("A",null);
        score++;
        round++;
        scoreText.setText("Score "+score);
    }

    public void noMoreAttempts(){
        round++;
        attempts = 0;
        attemptsText.setText("Tries "+attempts+"/3");
        callback.answerPressed("B",null);
        //set text and round
        //Don't set text in sharp rounds
    }

    public void incorrect(){
        attempts++;
        attemptsText.setText("Tries "+attempts+"/3");
    }//end incorrect

}//end class GuessNote

