package vanwingerdenbarrier.sheetmusictutor.Drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Clef;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Staff;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Tone;

/**
 * @author Bronson VanWingerden
 * Creates a staff then draws notes on it using the Staff structure
 */
public class DrawStaff extends AppCompatImageView {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    /**
     * stores the dimension of the current display
     */
    Point size;

    /**
     * the paint to use throughout DrawStaff, what fills objects
     */
    Paint paint = new Paint();

    /**
     * a temporary random integer to allow testing of our other methods
     * //TODO implement random based on users level/selected difficulty
     */
    Random random = new Random();

    /**
     * creates an array to contain the x and y coordinates for the line since each line only has
     * a pair of x,y coordinates corresponding to the start point and end point in the form
     * [xn, yn, xn+1, yn+1]
     */
    float[] lineArray;

    /**
     * the current staff structure that we are working with
     */
    Staff currentStaff = null;

    /**
     * the space between the lines of the staff
     */
    int spaceBetween;

    /**
     * temporary variable to set the number of bars used for testing
     * TODO generate bar length dynamically
     */
    int bars = 1;

    /**
     * temporary variable to set the number of beats used for testing
     * TODO generate beat number dynamically
     */
    int beats = 4;

    /**
     * the subdivision of beats per bar representing the bottom number of the time signature
     * for example if beats =4 and subdivision =4 then we are using 4/4 timing
     */
    int subdivision = 4;

    /**
     * the size of the text to display the note name within the note
     * TODO set this based on the scale of the screen
     */
    int textSize = 100;

    /**
     * public constructor to create a DrawStaff object
     * sets up paint and also gets the size of the current display
     * @param context
     */
    public DrawStaff(Context context) {
        super(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
    }

    @Override
    protected void onDraw(Canvas canvas){

        drawStaff(canvas);
        if(currentStaff == null){
            populateStaff();
        }
        drawNotes(canvas);
    }

    /**
     * draws the lines of the staff on the staff
     * @param canvas the canvas to draw on
     */
    private void drawStaff(Canvas canvas){
        lineArray = new float[20];

        int margin = 150;
        spaceBetween = (size.y/7) - 60;
        paint.setStrokeWidth(spaceBetween/20);

        int position = margin;
        int right = size.x;
        int left = 0;


        for(int i = 0; i < 20; i+=4) {

            //setting start x position
            lineArray[i] = left;

            //setting start y position
            lineArray[i+1] = position;

            //setting end x position which should match x start to create a horizontal line
            lineArray[i+2] = right;

            //setting end y position
            lineArray[i+3] = position;

            position += spaceBetween;
        }

        canvas.drawLines(lineArray, paint);
    }

    /**
     * creates and populates a new staff object for the current staff
     */
    private void populateStaff(){
        // creates an empty staff consisting of 1 bar of 4/4 music
        int[] timeSig = new int[2];
        timeSig[0] = 4;
        timeSig[1] = 4;


        currentStaff = new Staff(Clef.TREBLE, 1, timeSig);
        bars  = currentStaff.getNumOfBars();
        beats = currentStaff.getNumOfBeats(0);

        for(int i = 0; i < bars; i++){
            for(int j = 0; j < beats; j++){
                /**
                 * gets us a random tone to draw temporary method for testing
                 */
                Tone tempTone = Tone.values()[random.nextInt(Tone.values().length)];
                int tempPitch = 5;
                if(tempTone == Tone.E || tempTone == Tone.F){
                    if(random.nextInt(2) == 0){
                        tempPitch = 4;
                    }
                }else if(tempTone == Tone.G){
                    tempPitch = 4;
                }

                /**
                 * temporary random assignment of notes
                 */
                Note tempNote = new Note(tempTone, tempPitch, 4);
                currentStaff.insertNote(tempNote, i, j);

                // UNCOMMENT ME TO SHOW MULTIPLE NOTES PER BEAT
                //currentStaff.insertNote(i,j, Tone.A, 5, beats);
            }
        }
    }

    /**
     * draws all notes in the currentStaff onto visualization of the staff
     * @param canvas the canvas to draw onto
     */
    private void drawNotes(Canvas canvas){
        int noteHeight = (spaceBetween - (int)paint.getStrokeWidth()/2)/2;
        int noteWidth = noteHeight + 10;

        paint.setStrokeWidth(noteHeight/4);

        for(int i = 0; i < bars; i++){
            for(int j = 0; j < beats; j++){
                ArrayList<Note> tempList = currentStaff.getNoteList(i,j);
                for(Note note : tempList){
                    /*TODO replace magic number 100 with dynamic variable
                    * 100 represents the left margin of the notes
                    */
                    note.setX((size.x/beats) * j + 100 );
                    note.setY(locateNote(note));

                    /**
                     * drawing the note head
                     */
                    Drawable noteShape = getResources()
                            .getDrawable(R.drawable.q_note_head, null);
                    noteShape.setBounds(note.getX() - noteWidth, note.getY() - noteHeight
                                   ,note.getX() + noteWidth, note.getY() + noteHeight);
                    noteShape.draw(canvas);

                    /**
                     * drawing the stem
                     */

                    canvas.drawLine(note.getX() + (noteWidth - paint.getStrokeWidth()/2) - 2
                            , note.getY() - 15
                            ,note.getX() + (noteWidth - paint.getStrokeWidth()/2) - 2,
                            note.getY() - spaceBetween*2, paint);

                    paint.setColor(Color.WHITE);
                    //TODO Change constant 40 to figure out the center of a note
                    canvas.drawText(note.getTone().toString(),note.getX(),note.getY() + 40, paint);
                    paint.setColor(Color.BLACK);

                    /**
                     * TODO implement duration of notes
                     * accounts for not duration
                     */
                    if(note.getDuration() < beats) {
                        j += beats / note.getDuration();
                    }
                }
            }
        }
    }

    /**
     * determines the notes location based on its tone and pitch
     * @param note the Note object to find a place on the staff
     * @return returns the notes float y - location on the staff, the x-axis is handled elsewhere
     */
    private int locateNote(Note note){
        float noteLocation = 0;

        /**
         * big ole if/else to catch certain tones/pitches
         * lineArray[1 + 4x] gives me the x height of a the desired line
         * then subtracting spaceBetween/2 will put the note directly between 2 lines
         * TODO expand to support all notes & more pitches
         */
        if(note.getTone() == Tone.E && note.getPitch() == 4){
            noteLocation = lineArray[17];
        }else if(note.getTone() == Tone.F && note.getPitch() == 4){
            noteLocation = lineArray[17] - spaceBetween/2;
        }else if(note.getTone() == Tone.G && note.getPitch() == 4){
            noteLocation = lineArray[13];
        }else if(note.getTone() == Tone.A && note.getPitch() == 5){
            noteLocation = lineArray[13] - spaceBetween/2;
        }else if(note.getTone() == Tone.B && note.getPitch() == 5){
            noteLocation = lineArray[9];
        }else if(note.getTone() == Tone.C && note.getPitch() == 5){
            noteLocation = lineArray[9] - spaceBetween/2;
        }else if(note.getTone() == Tone.D && note.getPitch() == 5){
            noteLocation = lineArray[5];
        }else if(note.getTone() == Tone.E && note.getPitch() == 5){
            noteLocation = lineArray[5] - spaceBetween/2;
        }else if(note.getTone() == Tone.F && note.getPitch() == 5){
            noteLocation = lineArray[1];
        }

        return (int)noteLocation;
    }

    public void changeColor(){
        if(paint.getColor() == Color.BLACK){
            paint.setColor(Color.MAGENTA);
        }else{
            paint.setColor(Color.BLACK);
        }
    }
}



