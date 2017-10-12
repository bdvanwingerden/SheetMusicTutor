package vanwingerdenbarrier.sheetmusictutor.StaffStructure;

import java.util.ArrayList;

/**
 * Contains a note ArrayList that holds the notes for a beat
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

    public Beat() {
        this.numOfNotes = 0;
        notes = new ArrayList<>();
    }

    public void insertNote(Note note){
        numOfNotes++;
        notes.add(note);
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }
}
