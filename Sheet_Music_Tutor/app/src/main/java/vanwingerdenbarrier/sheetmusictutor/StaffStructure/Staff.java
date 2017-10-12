package vanwingerdenbarrier.sheetmusictutor.StaffStructure;

import java.util.ArrayList;

/**
 * @author Bronson VanWingerden
 * creates a staff structure to be the backbone of the game activity
 */
public class Staff{

    /**
     * the clef of the current Staff
     */
    Clef clef;

    /**
     * number of bars in the staff
     */
    int numOfBars;

    /**
     * contains all the bars in the staff
     */
    ArrayList<Bar> bars;

    /**
     * array containing the time signature where timeSig[0] is the top number & timeSig[1] is the
     * bottom number
     */
    int[] timeSig = new int[2];

    public Staff(Clef clef, int numOfBars, int[] timeSig) {
        this.clef = clef;
        this.numOfBars = numOfBars;
        this.timeSig = timeSig;
        bars = new ArrayList<>();

        for(int i = 0; i < numOfBars; i++){
            bars.add(new Bar(timeSig));
        }
    }

    public void insertNote(Note note, int barLocation, int beatLocation){
        bars.get(barLocation).getBeats().get(beatLocation).insertNote(note);
    }

    public ArrayList<Note> getNoteList(int barLocation, int beatLocation){
        return bars.get(barLocation).getBeats().get(beatLocation).getNotes();
    }

    public int getNumOfBars(){
        return bars.size();
    }

    public int getNumOfBeats(int barLocation){
        return bars.get(barLocation).getNumOfBeats();
    }
}
