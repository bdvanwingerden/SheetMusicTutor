package vanwingerdenbarrier.sheetmusictutor.NoteGames;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vanwingerdenbarrier.sheetmusictutor.Game.GameSelection;
import vanwingerdenbarrier.sheetmusictutor.Game.QuestionDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Tone;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.User;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;


/**
 *
 */
public class PlayAlongFragment extends Fragment implements QuestionDisplay{

    /*callback to the gameActivity class */
    QuestionDisplay.Display callback;

    int songType;

    /**The image views of the arrows*/
    ImageView a1,a2,a3,a4,a5,a6,a7,a8;

    TextView t1,t2,t3,t4,t5,t6,t7,t8;

    /**TextView for the score*/
    TextView scoreView;

    ImageView[] arrowTracker;

    /**Array of notes in twinkle twinkle little star*/
    String[] twinkle = {"G","G","D","D","E","E","D","C",
            "C","B","B","A","A","G","D","D",
            "C","C","B","B","A","D","D","C",
            "C","B","B","A","G","G","D","E",
            "E","D","E","E","D","C","C","B",
            "B","A","A","G","","","",""};

    /**Array of notes in the itsy bitsy spider*/
    String[] spider = {"G","C","C","C","D","E","E","E",
            "D","C","D","E","C","E","E","F",
            "G","F","E","F","G","E","C","C",
            "D","E","E","D","C","D","E","C",
            "G","G","C","C","C","D","E","E",
            "E","D","C","D","E","C","","",};

    /***Array of notes in row row row your boat*/
    String[] row = {"D","D","D","E","F#","F#","E","F#",
            "G","A","D","D","D","A","A","A",
            "F#","F#","F#","D","D","D","A","G",
            "F#","E","D","","","","","",};

    /**The current notes to be displayed in the for play along*/
    String[] currentNotes = {"","","","","","","",""};

    /**Image view for first life*/
    ImageView life1;

    /**Image view for second life*/
    ImageView life2;

    /**Image view for third life*/
    ImageView life3;

    //If song does fill all of eight put empty strings

    /**Current spot in the array being evaluated*/
    int notePointer;

    /**Current note being played*/
    int currentNote;

    /**Current note to be played*/
    String correct;

    /**Once 8 correct answers adds point to stats*/
    int addCorrect;

    /**Number of lives a player has*/
    int lives;

    /**Current score for the game*/
    private int score;

    /**True if user has not leveled up during a quiz session. False if they have leveled up*/
    boolean levelUp = true;

    /**list of users*/
    UserList userList;

    /**Curerent User*/
    User current;

    /**
     * used to determine if we are playing the game in combo or practice mode
     */
    int mode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_play_along, container, false);

        userList = new UserList(getActivity());
        current = new UserList(getActivity()).findCurrent();

        Bundle args = getArguments();

        songType = args.getInt("songType");
        mode = args.getInt("mode");

        life1 = (ImageView) view.findViewById(R.id.kLife1);
        life2 = (ImageView) view.findViewById(R.id.kLife2);
        life3 = (ImageView) view.findViewById(R.id.kLife3);

        a1 = (ImageView) view.findViewById(R.id.imageView4);
        a2 = (ImageView) view.findViewById(R.id.imageView6);
        a3 = (ImageView) view.findViewById(R.id.imageView7);
        a4 = (ImageView) view.findViewById(R.id.imageView8);
        a5 = (ImageView) view.findViewById(R.id.imageView9);
        a6 = (ImageView) view.findViewById(R.id.imageView10);
        a7 = (ImageView) view.findViewById(R.id.imageView11);
        a8 = (ImageView) view.findViewById(R.id.imageView12);

        arrowTracker = new ImageView[]{a1,a2,a3,a4,a5,a6,a7,a8};

        t1 = (TextView) view.findViewById(R.id.textView6);
        t2 = (TextView) view.findViewById(R.id.textView7);
        t3 = (TextView) view.findViewById(R.id.textView8);
        t4 = (TextView) view.findViewById(R.id.textView11);
        t5 = (TextView) view.findViewById(R.id.textView12);
        t6 = (TextView) view.findViewById(R.id.textView13);
        t7 = (TextView) view.findViewById(R.id.textView14);
        t8 = (TextView) view.findViewById(R.id.textView15);

        scoreView = (TextView) view.findViewById(R.id.scoreP);


        notePointer = 0;

        currentNote = 0;

        lives = args.getInt("lives");

        score = args.getInt("score");

        addCorrect = 0;

        setNotes(setSong());

        correct = currentNotes[currentNote];

        // Inflate the layout for this fragment
        return view;
    }


    /**
     * Set the song to be played
     */
    private String[] setSong(){

        String[] songArray = null;

        if(songType == 0){
            songArray = twinkle;
        }
        else if(songType == 1){
            songArray = spider;
        }
        else if(songType == 2){
            songArray = row;
        }

        return songArray;
    }

    /**
     * Set the next 8 notes to be played on display
     */
    private void setNotes(String[] songArray){

        for(int i = 0; i < currentNotes.length; i++){
            currentNotes[i] = songArray[notePointer];
            notePointer++;
        }

        t1.setText(currentNotes[0]);
        t2.setText(currentNotes[1]);
        t3.setText(currentNotes[2]);
        t4.setText(currentNotes[3]);
        t5.setText(currentNotes[4]);
        t6.setText(currentNotes[5]);
        t7.setText(currentNotes[6]);
        t8.setText(currentNotes[7]);

        //display notes here
    }//end

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
        //may need to add curr later

        if(correct.equals(checkCorrect)){//right
            addCorrect++;
            if(addCorrect == 8){
                score++;
                scoreView.setText("Score: "+score);
                addPoint();
                addCorrect = 0;
            }
            arrowTracker[currentNote].setImageResource(R.drawable.arrow_purple);
            currentNote++;
            if(currentNote < 8 && !currentNotes[currentNote].equals("")) {
                correct = currentNotes[currentNote];
                arrowTracker[currentNote].setImageResource(R.drawable.arrowgreen);
            }
            else if(currentNote == 8 || currentNotes[currentNote].equals("")){
                if(setSong().length == notePointer){//no more notes
                    finishPlayAlong();
                }else{
                    currentNote = 0;//reset currentNote
                    setNotes(setSong());
                    correct = currentNotes[currentNote];
                    arrowTracker[currentNote].setImageResource(R.drawable.arrowgreen);
                }
            }
        } else {//wrong
            if (lives > 1) {
                lives--;
                userList.addUserAttempt();
                decrementLife(lives);
            } else {//lives == 0
                finishPlayAlong();
            }
        }
    }//end checkCorrectHelper()

    /**
     * Add a point to the users overall stats once they have played 8 notes correctly
     */
    private void addPoint(){
        userList.addUserCorrect();
        userList.addUserAttempt();

        //This if levels up the user if they have reached the number of points needed
        if(current.getNumPointsNeeded() == current.getNumQuestionsCorrect()  && levelUp == true){
            levelUp = false;//So that we don't level up the user more than once by mistake
            userList.levelUpUser();
            userList.addUserPointsNeeded();//increment points needed to level up
        }
    }

    /**
     * Show the alert dialog with the correct results
     * redirect to home screen
     */
    private void finishPlayAlong() {
        if (mode == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Game Over!");
            alertDialog.setMessage(scoreDialog());
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int j) {

                            dialogInterface.dismiss();

                            Intent game = new Intent(getContext(), GameSelection.class);
                            game.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            getContext().startActivity(game);

                            try {
                                finalize();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }

                        }
                    });

            alertDialog.show();
        } else {
            callback.questionPressed(null, score, lives); // ENDS this question
        }
    }//end finishPlayAlong

    /**
     * Sets the best quote based on the player's performance
     */
    private String scoreDialog(){
        String quote = "";

        if(score <= 2)
            quote += "Score: "+score+"\nVery Poor. Get to studying!";
        else if(score <= 4)
            quote += "Score: "+score+"\nYou can do better than that!";
        else if(score <= 6)
            quote += "Score: "+score+"\nNot bad! Now master it";
        else
            quote += "Score: "+score+"\nGreat! You really do know you're keyboard.";

        return quote;
    }

    /***
     * Takes away a life when incorrect guess is made
     * @param life - which life is being taken away
     */
    private void decrementLife(int life){
        if(life == 2)
            life1.setImageResource(R.drawable.ic_lost_life);
        else if(life == 1)
            life2.setImageResource(R.drawable.ic_lost_life);
        else if(life == 0)
            life3.setImageResource(R.drawable.ic_lost_life);

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
            callback = (QuestionDisplay.Display) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }//end onAttach
}
