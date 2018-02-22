package vanwingerdenbarrier.sheetmusictutor.NoteDefense;

import android.graphics.drawable.Drawable;

import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Duration;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Tone;

/**
 * Extension of note that allows for more animated note behavior
 */

public class AnimatedNote extends Note {
    Boolean isDestroyed;
    Drawable noteShape;
    int speed;

    public AnimatedNote(Tone tone, int pitch, boolean isSharp) {
        super(tone, pitch, Duration.QUARTER, isSharp);
        isDestroyed = false;
    }

    public void setNoteShape(Drawable noteShape) {
        this.noteShape = noteShape;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
