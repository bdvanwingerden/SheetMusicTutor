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
    /* Keeps track of the number of turns since the note was hit */
    int turnsSinceHit;
    /*target used for spaceship*/
    AnimatedNote target;


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
        isPlayed = false;
        verSpeed = 0;
        turnsSinceHit = 0;
        target = null;
    }

    /**
     * public constructor that accepts a Note and creates an animated note
     * @param note the note to create an animated note based off of
     */
    public AnimatedNote(Note note) {
        super(note.getTone(), note.getPitch(), Duration.QUARTER, note.isSharp());
        isDestroyed = false;
        verSpeed = 0;
        target = null;
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

    /**
     * sets x to the desired location and if the AnimatedNote is destroyed keeps track of how many
     * times the note is updated after the note is destroyed
     * @param x the location to set x to
     */
    @Override
    public void setX(int x) {
        if (isDestroyed) {
            turnsSinceHit++;
        }
        super.setX(x);
    }

    /**
     * sets the vertical speed of this note which is zero by default
     * @param verSpeed the vertical speed to set this Animated notes vertical speed to
     */
    public void setVerSpeed(int verSpeed) {
        this.verSpeed = verSpeed;
    }

    /**
     * sets the note as destroyed by changing its drawable to an exploded note
     * @param context the context to set the drawable in
     */
    public void setDestroyed(Context context) {
        isDestroyed = true;
        noteShape = ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_pow, null);
        noteShape.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * sets the note as played
     */
    public void setIsPlayed(){
        isPlayed = true;
    }

    /**
     * returns the drawable shape of the note
     * @return
     */
    public Drawable getNoteShape(){
        return  noteShape;
    }

    /**
     * sets the target of this note if it is a spaceship
     * @param target the target to shoot at
     */
    public void setTarget(AnimatedNote target){
        this.target = target;
    }

    /**
     * stores and returns the target of this note if it is a spaceship object
     * @return the target note
     */
    public AnimatedNote getTarget(){
        return target;
    }

}
