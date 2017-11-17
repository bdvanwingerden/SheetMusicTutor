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


    Duration duration;

    int remainingDuration;

    /**
     * the x location of this note set to 0 until changed
     */
    int x;

    /**
     * the y location of this note set to 0 until changed
     */
    int y;

    boolean drawn;

    public Note(Tone tone, int pitch, Duration duration){
        this.tone = tone;
        this.pitch = pitch;
        this.duration = duration;
        this.x = 0;
        this.y = 0;
        drawn = false;
    }

    public Tone getTone() {
        return tone;
    }

    public int getPitch() {
        return pitch;
    }

    public Duration getDuration() {
        return duration;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

