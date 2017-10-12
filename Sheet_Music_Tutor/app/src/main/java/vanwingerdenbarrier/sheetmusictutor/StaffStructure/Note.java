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
     * the duration of the note in number of beats so 1 quarter note would equal 1 in 4/4 and half
     * notes would equal 2 and whole notes would equal 4
     */
    int duration;

    /**
     * the x location of this note set to 0 until changed
     */
    int x;

    /**
     * the y location of this note set to 0 until changed
     */
    int y;

    public Note(Tone tone, int pitch, int duration){
        this.tone = tone;
        this.pitch = pitch;
        this.duration = duration;
        this.x = 0;
        this.y = 0;
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

    /**
     * decrements the remaining duration for the current note
     */
    public void decDuration(){
        duration--;
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

