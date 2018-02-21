package vanwingerdenbarrier.sheetmusictutor.StaffStructure;


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
     * the duration of this note
     */
    Duration duration;

    /**
     * the x location of this note set to 0 until changed
     */
    int x;

    /**
     * the y location of this note set to 0 until changed
     */
    int y;

    /** whether or not the note is about to be redrawn*/
    boolean drawn;

    boolean isSharp;

    /**
     * public constructor for note that sets the tone pitch and duration
     * @param tone the tone for the note
     * @param pitch the pitch for the note
     * @param duration the duration of the note
     */
    public Note(Tone tone, int pitch, Duration duration, boolean isSharp) {
        this.tone = tone;
        this.pitch = pitch;
        this.duration = duration;
        this.x = 0;
        this.y = 0;
        this.isSharp = isSharp;
        drawn = false;
    }

    /**
     * gets the notes tone
     * @return the notes tone
     */
    public Tone getTone() {
        return tone;
    }

    /**
     * gets the notes pitch
     * @return the notes pitch
     */
    public int getPitch() {
        return pitch;
    }

    /**
     * gets the notes duration
     * @return the notes duration
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * gets the notes x location
     * @return the notes x location
     */
    public int getX() {
        return x;
    }

    /**
     * sets the notes x location
     * @param x the location to set x to
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * gets the notes y location
     * @return the notes y location
     */
    public int getY() {
        return y;
    }

    /**
     * sets the notes y location
     * @param y the location to set x to
     */
    public void setY(int y) {
        this.y = y;
    }
}

