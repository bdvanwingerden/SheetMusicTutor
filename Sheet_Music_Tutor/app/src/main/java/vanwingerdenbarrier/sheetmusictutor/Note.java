package vanwingerdenbarrier.sheetmusictutor;

import android.graphics.Canvas;

/**
 * Contains the Individual values for each not in a given sheet
 * Created by Bronson VanWingerden on 9/15/2017.
 */

public class Note {
    /**
     * tone represents the letter value of the note
     */
    Tone tone;

    /**
     * int pitch represents the pitch of the note for example A5 or B1
     * most commonly in treble clef either 4 or 5 and 2 or 3 in bass clef
     */
    int pitch;

    /**
     * the duration of the note in number of beats so 1 quarter note would equal 1 in 4/4 and half
     * notes would equal 2 and whole notes would equal 4
     */
    int duration;

    /**
     * Enumeration for Tone to ensure correct notes are selected
     */

    public Note(Tone tone, int pitch, int duration){
        this.tone = tone;
        this.pitch = pitch;
        this.duration = duration;
    }

    public Tone getTone() {
        return tone;
    }

    public int getPitch() {
        return pitch;
    }

    public int getDuration() {
        return duration;
    }
}

