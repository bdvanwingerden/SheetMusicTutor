package vanwingerdenbarrier.sheetmusictutor.StaffStructure;

import java.util.ArrayList;

/**
 * Contains a note ArrayList that holds the notes for a beat
 *
 * @author Bronson VanWingerden
 */
public class Beat {

    /**
     * number of notes in the beat
     */
    int numOfNotes;

    /**
     * ArrayList containing the list of notes
     */
    ArrayList<Note> notes;

    /**
     * public constructor for beat
     */
    public Beat() {
        this.numOfNotes = 0;
        notes = new ArrayList<>();
    }

    /**
     * inserts a note into the current beat
     * @param note the note to insert
     */
    public void insertNote(Note note){
        numOfNotes++;
        notes.add(note);
    }

    /**
     * returns all the current notes in this beat
     * @return the list of current notes in this beat
     */
    public ArrayList<Note> getNotes() {
        return notes;
    }
}
