package vanwingerdenbarrier.sheetmusictutor.NoteGames;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Duration;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Tone;

/**
 * Extension of note that allows for more animated note behavior
 */

public class AnimatedNote extends Note {
    /* boolean that returns true if the note is destroyed */
    Boolean isDestroyed;
    /* boolean that returns true if the note has been played*/
    Boolean isPlayed;
    /*the shape of this note */
    Drawable noteShape;
    /* the  horizontal traversal Speed of this note */
    int horSpeed;
    /* the  vertical traversal Speed of this note */
    int verSpeed;

    int turnsSinceHit;


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
        verSpeed = 0;
        turnsSinceHit = 0;
    }

    public AnimatedNote(Note note) {
        super(note.getTone(), note.getPitch(), Duration.QUARTER, note.isSharp());
        isDestroyed = false;
        verSpeed = 0;
    }

    /**
     * sets the drawable shape of this note
     *
     * @param noteShape the drawable shape of this note
     */
    public void setNoteShape(Drawable noteShape) {
        this.noteShape = noteShape;
    }

    /**
     * sets the horSpeed of this note
     *
     * @param horSpeed the horSpeed of this note
     */
    public void setHorSpeed(int horSpeed) {
        this.horSpeed = horSpeed;
    }

    @Override
    public void setX(int x) {
        if (isDestroyed) {
            turnsSinceHit++;
        }
        super.setX(x);
    }

    public void setVerSpeed(int verSpeed) {
        this.verSpeed = verSpeed;
    }

    public void setDestroyed(Context context) {
        isDestroyed = true;
        noteShape = ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_pow, null);
        noteShape.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
    }
}
