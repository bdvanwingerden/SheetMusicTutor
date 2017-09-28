package vanwingerdenbarrier.sheetmusictutor;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * creates the staff
 */

public class Staff{

    int numBars;
    int numBeats;

    /**
     * contains the current staffs clef
     */
    Clef clef;

    /**
     * contains the time signature
     * timeSig[0] contains the top number &
     * timeSig[1] contains the bottom number
     */
    int[] timeSig = new int[2];

    /**
     * contains all the bars in the staff
     */
    ArrayList<ArrayList<ArrayList<Note>>> staff;


    public Staff(Clef clef, int numBars, int timeSig1, int timeSig2){
        this.numBars = numBars;
        this.numBeats = timeSig1;
        this.clef = clef;
        this.timeSig[0] = timeSig1;
        this.timeSig[1] = timeSig2;
        this.staff = new ArrayList<>(numBars);
        int numBeats = (timeSig1 * (timeSig2/4));

        for(int i = 0; i < numBars; i++){
            ArrayList<ArrayList<Note>> tempBar = new ArrayList<>(0);
            for(int k = 0; k < numBeats; k++){
                tempBar.add(new ArrayList<Note>(0));
            }
            staff.add(tempBar);
        }

    }

    /**
     * inserts a note into the Staff
     * @param barLocation the bar to insert into
     * @param beatLocation the beat to insert into
     * @param tone the tone of the note to insert
     * @param pitch the pitch i.e. a number cooresponding to how high or low the note is ussually
     *              4 or 5
     * @param duration the duration the note for example whole = 1 half = 2, quarter = 4
     *                 eighth = 8 etc
     */
    public void insertNote(int barLocation, int beatLocation, Tone tone, int pitch, int duration){
        staff.get(barLocation).get(beatLocation).add(new Note(tone, pitch, duration));
    }

    public ArrayList<Note> getNoteList(int barLocation, int beatLocation){
        return staff.get(barLocation).get(beatLocation);
    }

}
