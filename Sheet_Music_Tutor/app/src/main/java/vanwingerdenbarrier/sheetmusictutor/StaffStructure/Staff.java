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
            bars.add(new Bar());
        }
    }

    /**
     * inserts a given note into the given location
     * @param note the note to insert
     * @param barLocation the location to insert to
     */
    public void insertNote(Note note, int barLocation){
        Bar current = bars.get(barLocation);
        if(current.getLastNoteIndex() < 16){
            bars.get(barLocation).getBeats().get(current.getLastNoteIndex()).insertNote(note);

            switch (note.getDuration()){
                case WHOLE: current.incrementNoteIndex(16);
                    break;
                case HALF: current.incrementNoteIndex(8);
                    break;
                case QUARTER: current.incrementNoteIndex(4);
                    break;
                case EIGHTH: current.incrementNoteIndex(2);
                    break;
                case SIXTEENTH: current.incrementNoteIndex(1);
                    break;
            }
        }
    }

    /**
     * returns the list of notes a the given location
     * @param barLocation the bar to look in
     * @param beatLocation the beat to look in
     * @return the note list for the beat
     */
    public ArrayList<Note> getNoteList(int barLocation, int beatLocation){
        if(barLocation < bars.size()){
            if(beatLocation < bars.get(barLocation).getBeats().size()){
                return bars.get(barLocation).getBeats().get(beatLocation).getNotes();
            }
        }
        /* Returns an empty list if the notelist is out of bounds*/
        return new ArrayList<>();
    }

    /**
     * returns the number of bars in the staff
     * @return the number of bars in the staff
     */
    public int getNumOfBars(){
        return bars.size();
    }

    /**
     * returns the beat index for the given bar location
     * @param barlocation the bar location to query
     * @return the beat index for the give bar location
     */
    public int getCurrentBeatIndex(int barlocation){
        return bars.get(barlocation).getLastNoteIndex();
    }

    /**
     * returns the current clef type
     * @return the current clef type
     */
    public Clef getClef(){
        return clef;
    }

    /**
     * finds the location of a note in the staff
     * @param noteToFind the note to find
     * @return the x and y coordinates of the note in the staff
     */
    public float[] findNoteLocation(Note noteToFind){
        float[] location = null;
        for(Bar bar : bars){
            for(Beat beat : bar.getBeats()){
                for(Note note : beat.getNotes()){
                    if(note.getTone() == noteToFind.getTone()
                            && note.getPitch() == noteToFind.getPitch()){
                        location = new float[2];
                        location[0] = note.getX();
                        location[1] = note.getY();
                        return location;
                    }
                }
            }
        }
        return location;
    }
}
