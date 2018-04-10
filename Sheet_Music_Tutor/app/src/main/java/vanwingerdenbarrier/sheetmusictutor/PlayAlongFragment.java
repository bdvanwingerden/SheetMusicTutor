package vanwingerdenbarrier.sheetmusictutor;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vanwingerdenbarrier.sheetmusictutor.Game.QuestionDisplay;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Tone;


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

    ImageView[] arrowTracker;

    /**Array of notes in twinkle twinkle little star*/
    String[] twinkle = {"G","G","D","D","E","E","D","C"};

    /**Array of notes in twinkle twinkle little star*/
    String[] spider = {"G","C","C","C","D","E","E","E"};

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

    /**Number of lives a player has*/
    int attempts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_play_along, container, false);

        Bundle b = getArguments();

        songType = b.getInt("songType");

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


        notePointer = 0;

        currentNote = 0;

        attempts = 3;

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
            checkCorrect = "AS";
        else if(tmpTone.equals(Tone.C) && isSharp)
            checkCorrect = "CS";
        else if(tmpTone.equals(Tone.D) && isSharp)
            checkCorrect = "DS";
        else if(tmpTone.equals(Tone.F) && isSharp)
            checkCorrect = "FS";
        else if(tmpTone.equals(Tone.G) && isSharp)
            checkCorrect = "GS";

        checkCorrectHelper(checkCorrect);

    }//end checkIfCorrect

    /**
     * Helper to check if answer is correct
     * main game logic is here
     */
    private void checkCorrectHelper(String checkCorrect){
        //may need to add curr later

        if(correct.equals(checkCorrect)){//right
            arrowTracker[currentNote].setImageResource(R.drawable.arrow_purple);
            currentNote++;
            correct = currentNotes[currentNote];
            arrowTracker[currentNote].setImageResource(R.drawable.arrowgreen);
        }else{//wrong
            if(attempts > 0){
                attempts--;
                decrementLife(attempts);
            }else{//attempts == 0
                //reveal text, but change red
            }
        }

    }//end checkCorrectHelper()

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
