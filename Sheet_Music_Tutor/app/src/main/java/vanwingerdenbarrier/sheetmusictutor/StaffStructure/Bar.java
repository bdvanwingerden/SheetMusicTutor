package vanwingerdenbarrier.sheetmusictutor.StaffStructure;

import java.util.ArrayList;

/**
 * Contains a Bar for a Staff and holds an Arraylist of beats
 *
 * @author Bronson VanWingerden
 */

public class Bar {
    /** the current index of the note*/
    int noteIndex;

    /** the arraylist of beats contained in this bar */
    ArrayList<Beat> beats;

    /**
     * public constructor for Bar
     */
    public Bar() {
        this.noteIndex = 0;
        beats = new ArrayList<>();

        for (int i = 0; i < 16; i++){
            beats.add(new Beat());
        }
    }

    /**
     * gets the ArrayList containing the beats in this bar
     * @return the beats in this bar
     */
    public ArrayList<Beat> getBeats() {
        return beats;
    }

    /**
     * gets the index of the last note played
     * @return the index of the last note played
     */
    public int getLastNoteIndex(){
        return noteIndex;
    }

    /**
     * increments the note index
     * @param index the number to increment the note index by (useful for alternate time signature
     */
    public void incrementNoteIndex(int index){
        noteIndex += index;
    }
}
