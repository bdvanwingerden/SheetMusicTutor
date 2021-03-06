package vanwingerdenbarrier.sheetmusictutor.NoteGames;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import vanwingerdenbarrier.sheetmusictutor.Game.QuestionDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Tone;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.User;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;


public class KnowYourKeyboardFragment extends Fragment implements QuestionDisplay{

    /**Button for the speech*/
    Button speechBtn;

    /**Text view for the score*/
    TextView scoreView;

    /**Image view for first life*/
    ImageView life1;

    /**Image view for second life*/
    ImageView life2;

    /**Image view for third life*/
    ImageView life3;

    /*callback to the gameActivity class */
    QuestionDisplay.Display callback;

    /**Button corresponding to notes*/
    private Button a,b,c,d,e,f,g,cs,ds,fs,gs,as;

    /**Array of strings representing which note to play*/
    private String[] notes = {"A", "B", "C", "D", "E", "F", "G", "A#", "C#", "D#", "F#", "G#"};

    /**The current view*/
    View view;

    /**
     * Current mode used to determine if we are in Combo mode or practice mode
     */
    int mode;

    /**Current score for the game*/
    private int score;

    /**String that represents the correct note*/
    private String correct;

    /**The current index being tracked in the arrays*/
    private int curr;

    /**list of users*/
    UserList userList;

    /**Curerent User*/
    User current;
    /**
     * Number of times to ask user to identify not
     */
    int numQuestions;
    /**
     * Current lives for the game
     */
    private int lives;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_know_your_keyboard, container, false);

        userList = new UserList(getActivity());
        current = new UserList(getActivity()).findCurrent();

        speechBtn = (Button) view.findViewById(R.id.button16);

        scoreView = (TextView) view.findViewById(R.id.scoreK);

        life1 = (ImageView) view.findViewById(R.id.kLife1);
        life2 = (ImageView) view.findViewById(R.id.kLife2);
        life3 = (ImageView) view.findViewById(R.id.kLife3);

        a = view.findViewById(R.id.a5);
        as = view.findViewById(R.id.as5);
        b = view.findViewById(R.id.b5);
        c = view.findViewById(R.id.c5);
        cs = view.findViewById(R.id.cs5);
        d = view.findViewById(R.id.d5);
        ds = view.findViewById(R.id.ds5);
        e = view.findViewById(R.id.e4);
        f = view.findViewById(R.id.f4);
        fs = view.findViewById(R.id.fs4);
        g = view.findViewById(R.id.g4);
        gs = view.findViewById(R.id.gs4);

        shuffle();//shuffle index of notesIndex array

        Bundle args = getArguments();

        mode = args.getInt("mode");

        lives = args.getInt("lives");

        score = args.getInt("score");

        scoreView.setText("score :" + score);

        decrementLife(lives);

        curr = 0;

        if (mode == 0) {
            numQuestions = 11;
        } else {
            //TODO Change to get current know your note level
            numQuestions = userList.findCurrent().getCurrentLevel();
        }

        correct = notes[curr];

        speechBtn.setText("     Identify The Note: " + correct + "   ");//set initial note

        //TODO add Know your keyboard section to DB

        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Know Your Keyboard!");
        alertDialog.setMessage("Dave will tell which notes to play on the keyboard. " +
                "If you guess correctly Dave will reward you with a point. If not you lose a life!");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        dialogInterface.dismiss();
                    }
                });

        alertDialog.setCancelable(false);

        if (userList.findCurrent().getCurrentLevel() < 2) {
            alertDialog.show();
        }
        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Helper for shuffle method.
     * Used to shuffle two indexes around in the array
     *
     * @param one
     * @param two
     */
    private void swap(int one, int two){
        String tmp = notes[one];
        notes[one] = notes[two];
        notes[two] = tmp;
    }//end swap()

    /**
     * Uses the Fisher-Yates shuffle algorithm in order
     * to ensure that the order at which notes are displayed is random.
     */
    public void shuffle(){

        Random r = new Random();

        for(int i = 0; i < notes.length; i++){
            int index = r.nextInt(notes.length);
            swap(i,index);
        }
    }//end shuffle()

    /**
     * Checks if the correct answer has been selected
     */
    public void checkIfCorrect(Note answer){
        Note tmpNote = answer;
        Tone tmpTone = tmpNote.getTone();
        boolean isSharp = tmpNote.isSharp();
        String checkCorrect = "";

        //Normal Keys
        if(tmpTone.equals(Tone.A) && !isSharp)
            checkCorrect = "A";
        else if(tmpTone.equals(Tone.B) && !isSharp)
            checkCorrect = "B";
        else if(tmpTone.equals(Tone.C) && !isSharp)
            checkCorrect = "C";
        else if(tmpTone.equals(Tone.D) && !isSharp)
            checkCorrect = "D";
        else if(tmpTone.equals(Tone.E) && !isSharp)
            checkCorrect = "E";
        else if(tmpTone.equals(Tone.F) && !isSharp)
            checkCorrect = "F";
        else if(tmpTone.equals(Tone.G) && !isSharp)
            checkCorrect = "G";

        //Sharp Keys
        else if(tmpTone.equals(Tone.A) && isSharp)
            checkCorrect = "A#";
        else if(tmpTone.equals(Tone.C) && isSharp)
            checkCorrect = "C#";
        else if(tmpTone.equals(Tone.D) && isSharp)
            checkCorrect = "D#";
        else if(tmpTone.equals(Tone.F) && isSharp)
            checkCorrect = "F#";
        else if(tmpTone.equals(Tone.G) && isSharp)
            checkCorrect = "G#";

        checkCorrectHelper(checkCorrect);

    }//end checkIfCorrect

    /**
     * Helper to check if answer is correct
     * main game logic is here
     */
    private void checkCorrectHelper(String checkCorrect){


        if(correct.equals(checkCorrect)){
            addPoint();
            score++;//user score as well
            scoreView.setText("Score: "+score);
            curr++;
            correct = notes[curr];
        } else {//wrong
            if(lives > 0){
                lives--;
                userList.addUserAttempt();
                decrementLife(lives);
            }
        }

        if(curr < numQuestions && lives > 0){
            speechBtn.setText("     Identify The Note: " + correct + "   ");
        } else if (curr == numQuestions || lives == 0) {
                callback.questionPressed(null, score, lives);
        }

    }//end checkCorrectHelper()

    /**
     * Add a point to the users overall stats once they have played 8 notes correctly
     */
    private void addPoint(){
        userList.addUserCorrect();
        userList.addUserAttempt();


        //This if levels up the user if they have reached the number of points needed
        if (userList.findCurrent().getNumPointsNeeded()
                <= userList.findCurrent().getNumQuestionsCorrect()) {

            userList.addUserPointsNeeded();//increment points needed to level up
            userList.levelUpUser();
        }
    }

    /***
     * Takes away a life when incorrect guess is made
     * @param life - which life is being taken away
     */
    private void decrementLife(int life){
        if (life == 3) {
            life1.setImageResource(R.drawable.ic_lost_life);
        } else if (life == 2) {
            life1.setImageResource(R.drawable.ic_lost_life);
            life2.setImageResource(R.drawable.ic_lost_life);
        } else if (life == 1) {
            life1.setImageResource(R.drawable.ic_lost_life);
            life2.setImageResource(R.drawable.ic_lost_life);
            life3.setImageResource(R.drawable.ic_lost_life);
        }
    }//end decrementLife()

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
    }//end onAttach


}
