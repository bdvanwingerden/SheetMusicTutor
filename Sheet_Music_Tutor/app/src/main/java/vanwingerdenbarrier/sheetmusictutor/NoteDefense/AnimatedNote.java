package vanwingerdenbarrier.sheetmusictutor.NoteDefense;

import android.graphics.drawable.Drawable;

import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Duration;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Tone;

/**
 * Extension of note that allows for more animated note behavior
 */

public class AnimatedNote extends Note {
    /* boolean that returns true if the note is destroyed */
    Boolean isDestroyed;
    /*the shape of this note */
    Drawable noteShape;
    /* the traversal speed of this note */
    int speed;

    /**
     * public constructor that calls super and simply sets Duration to quarter because it is not needed
     * in Note defense
     *
     * @param tone    the note of the note to create
     * @param pitch   the pitch of the note to create
     * @param isSharp true if the note is sharp
     */
    public AnimatedNote(Tone tone, int pitch, boolean isSharp) {
        super(tone, pitch, Duration.QUARTER, isSharp);
        isDestroyed = false;
    }

    /**
     * sets the drawable shape of this note
     * @param noteShape the drawable shape of this note
     */
    public void setNoteShape(Drawable noteShape) {
        this.noteShape = noteShape;
    }

    /**
     * sets the speed of this note
     * @param speed the speed of this note
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
