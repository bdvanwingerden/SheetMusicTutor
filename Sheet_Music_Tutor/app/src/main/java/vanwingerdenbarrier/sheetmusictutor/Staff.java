package vanwingerdenbarrier.sheetmusictutor;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * creates the staff
 */

public class Staff{
    private enum Clef{
        TREBLE,BASS
    }

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
        this.clef = clef;
        this.timeSig[0] = timeSig1;
        this.timeSig[1] = timeSig2;
        this.staff = new ArrayList<>(numBars);
        int numBeats = (timeSig1 * (timeSig2/4));
        staff = new ArrayList<>(0);

        for(int i = 0; i < numBars; i++){
            ArrayList<ArrayList<Note>> tempBar = new ArrayList<>(0);
            for(int k = 0; k < numBeats; k++){
                tempBar.add(new ArrayList<Note>(0));
            }
            staff.add(tempBar);

        }

    }

    public void insertNote(int beatLocation, int barLocation, Tone tone, int pitch, int duration){

        staff.get(barLocation).get(beatLocation).add(new Note(tone, pitch, duration));
    }

}
