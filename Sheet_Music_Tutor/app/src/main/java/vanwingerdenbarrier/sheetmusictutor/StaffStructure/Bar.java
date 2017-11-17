package vanwingerdenbarrier.sheetmusictutor.StaffStructure;

import java.util.ArrayList;

/**
 * Created by Bronson VanWingerden on 10/12/2017.
 */

public class Bar {
    int noteIndex;

    ArrayList<Beat> beats;

    public Bar() {
        this.noteIndex = 0;
        beats = new ArrayList<>();

        for (int i = 0; i < 16; i++){
            beats.add(new Beat());
        }
    }

    public ArrayList<Beat> getBeats() {
        return beats;
    }

    public int getLastNoteIndex(){
        return noteIndex;
    }

    public void incrementNoteIndex(int index){
        noteIndex += index;
    }
}
