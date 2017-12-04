package vanwingerdenbarrier.sheetmusictutor.Drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Clef;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Duration;
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
    Staff currentStaff;

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

    int currentBar;
    int currentBeat;

    /**
     * the size of the text to display the note name within the note
     * TODO set this based on the scale of the screen
     */
    int textSize = 80;

    /** true if there is a currently clicked note */
    boolean noteClicked;

    /**x & y of last clicked note */
    float lastClickY;
    float lastClickX;

    int noteHeight;
    int noteWidth;
    int horMargin;
    int verMargin;

    LinkedList<Note> nextToPlay;

    /**
     * public constructor to create a DrawStaff object
     * sets up paint and also gets the size of the current display
     *
     * @param context
     */
    public DrawStaff(final Context context) {
        super(context);

        nextToPlay = new LinkedList<Note>();

        noteClicked = false;
        horMargin = 300;
        verMargin = 200;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        populateStaff();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        horMargin = 200;
        drawStaff(canvas);
        drawClef(canvas);
        drawGuides(canvas, 8);
        drawNotes(canvas);
        drawPointer(canvas, currentBar, currentBeat);
        if(noteClicked){
            Note tempNote = getClickedNote(lastClickX, lastClickY);
            if(tempNote != null){
                selectNote(tempNote, canvas);
            }
        }
    }

    public Note reDraw(boolean noteClicked, float lastClickX, float lastClickY){
        this.noteClicked = noteClicked;
        this.lastClickX = lastClickX;
        this.lastClickY = lastClickY;
        return getClickedNote(lastClickX,lastClickY);
    }

    public void incrementPointer(){
        currentBeat++;
        while(currentStaff.getNoteList(currentBar,currentBeat).isEmpty() && currentBeat <= 16){
            currentBeat++;
        }
    }

    private void drawGuides(Canvas canvas, int guideDivision){
        float top = (spaceBetween - paint.getStrokeWidth()) * 2;
        float bottom = (spaceBetween * 6) - paint.getStrokeWidth();
        paint.setStrokeWidth(paint.getStrokeWidth()*2);
        paint.setColor(Color.parseColor("#DCDCDC"));

        for(int i = 0; i < bars; i++){
            for(int j = 0; j < beats; j+=(beats/guideDivision)) {

                int location = (((size.x - horMargin) / beats) * j + horMargin);

                canvas.drawLine(location, top,
                        location, bottom, paint);
            }
        }

        paint.setStrokeWidth(paint.getStrokeWidth()/2);
        paint.setColor(Color.BLACK);
    }


    private void drawClef(Canvas canvas){
        Drawable clef =  getResources().getDrawable(R.drawable.t_clef);
        int numberOflines = 7;
        int left = 0;
        int right = horMargin + 100;
        int top = spaceBetween + (spaceBetween/2);
        int bottom = (spaceBetween * 6) + (spaceBetween/2);

        if(currentStaff.getClef() == Clef.BASS){
            clef = getResources().getDrawable(R.drawable.b_clef);
            numberOflines = 5;
        }

        clef.setBounds(left, top ,right , bottom);

        horMargin += horMargin;

        clef.draw(canvas);
        canvas.drawLine(right, (spaceBetween - paint.getStrokeWidth()) * 2 ,
                right, (spaceBetween * 6) - paint.getStrokeWidth() , paint);

        right += 20;

        canvas.drawLine(right, (spaceBetween - paint.getStrokeWidth()) * 2 ,
                right, (spaceBetween * 6) - paint.getStrokeWidth() , paint);

    }

    /**
     * draws the lines of the staff on the staff
     *
     * @param canvas the canvas to draw on
     */
    private void drawStaff(Canvas canvas) {
        lineArray = new float[20];

        spaceBetween = ((size.y / 7) - 100)/bars;
        paint.setStrokeWidth(spaceBetween / 20);

        int position = verMargin;
        int right = size.x - 40;
        int left = 40;


        for (int i = 0; i < 20; i += 4) {

            //setting start x position
            lineArray[i] = left;

            //setting start y position
            lineArray[i + 1] = position;

            //setting end x position which should match x start to create a horizontal line
            lineArray[i + 2] = right;

            //setting end y position
            lineArray[i + 3] = position;

            position += spaceBetween;
        }

        canvas.drawLines(lineArray, paint);
    }

    /**
     * creates and populates a new staff object for the current staff
     */
    private void populateStaff() {
        // creates an empty staff consisting of 1 bar of 4/4 music
        int[] timeSig = new int[2];
        timeSig[0] = 4;
        timeSig[1] = 4;


        currentStaff = new Staff(Clef.TREBLE, 1, timeSig);
        bars = currentStaff.getNumOfBars();
        beats = 16;

        for (int i = 0; i < bars; i++) {
            while (currentStaff.getCurrentBeatIndex(i) < beats) {
                /**
                 * gets us a random tone to draw temporary method for testing
                 */
                Tone tempTone = Tone.values()[random.nextInt(Tone.values().length)];
                int tempPitch = 5;
                if (tempTone == Tone.E || tempTone == Tone.F) {
                    if (random.nextInt(2) == 0) {
                        tempPitch = 4;
                    }
                } else if (tempTone == Tone.G) {
                    tempPitch = 4;
                }

                /**
                 * temporary random assignment of notes
                 */
                Note tempNote = new Note(tempTone, tempPitch, Duration.QUARTER);
                currentStaff.insertNote(tempNote, i);

                // UNCOMMENT ME TO SHOW MULTIPLE NOTES PER BEAT
                //currentStaff.insertNote(i,j, Tone.A, 5, beats);
            }
        }
    }

    /**
     * draws all notes in the currentStaff onto visualization of the staff
     *
     * @param canvas the canvas to draw onto
     */
    private void drawNotes(Canvas canvas) {

        noteHeight = (spaceBetween - (int) paint.getStrokeWidth() / 2) / 2;
        noteWidth = noteHeight + 10;

        paint.setStrokeWidth(noteHeight / 4);

        for (int i = 0; i < bars; i++) {
            for (int j = 0; j < beats; j++) {
                ArrayList<Note> tempList = currentStaff.getNoteList(i, j);
                for (Note note : tempList) {

                    note.setX((((size.x - horMargin) / beats) * j + horMargin));
                    note.setY(locateNote(note));

                    /**
                     * drawing the note head
                     */
                    Drawable noteShape = getNoteShape(note);
                    noteShape.setBounds(note.getX() - noteWidth, note.getY() - noteHeight
                            , note.getX() + noteWidth, note.getY() + noteHeight);
                    noteShape.draw(canvas);

                    /**
                     * drawing the stem
                     */
                    if (note.getDuration() != Duration.WHOLE) {
                        canvas.drawLine(note.getX() + (noteWidth - paint.getStrokeWidth() / 2) - 2
                                , note.getY() - 15
                                , note.getX() + (noteWidth - paint.getStrokeWidth() / 2) - 2,
                                note.getY() - spaceBetween * 2, paint);

                    }

                    paint.setColor(Color.WHITE);
                    //TODO Change constant 40 to figure out the center of a note
                    canvas.drawText(note.getTone().toString(), note.getX(), note.getY() + 40, paint);
                    paint.setColor(Color.BLACK);

                }
            }
        }
    }

    /**
     * determines the notes location based on its tone and pitch
     *
     * @param note the Note object to find a place on the staff
     * @return returns the notes float y - location on the staff, the x-axis is handled elsewhere
     */
    private int locateNote(Note note) {
        float noteLocation = 0;

        /**
         * big ole if/else to catch certain tones/pitches
         * lineArray[1 + 4x] gives me the x height of a the desired line
         * then subtracting spaceBetween/2 will put the note directly between 2 lines
         * TODO expand to support all notes & more pitches
         */
        if (note.getTone() == Tone.E && note.getPitch() == 4) {
            noteLocation = lineArray[17];
        } else if (note.getTone() == Tone.F && note.getPitch() == 4) {
            noteLocation = lineArray[17] - spaceBetween / 2;
        } else if (note.getTone() == Tone.G && note.getPitch() == 4) {
            noteLocation = lineArray[13];
        } else if (note.getTone() == Tone.A && note.getPitch() == 5) {
            noteLocation = lineArray[13] - spaceBetween / 2;
        } else if (note.getTone() == Tone.B && note.getPitch() == 5) {
            noteLocation = lineArray[9];
        } else if (note.getTone() == Tone.C && note.getPitch() == 5) {
            noteLocation = lineArray[9] - spaceBetween / 2;
        } else if (note.getTone() == Tone.D && note.getPitch() == 5) {
            noteLocation = lineArray[5];
        } else if (note.getTone() == Tone.E && note.getPitch() == 5) {
            noteLocation = lineArray[5] - spaceBetween / 2;
        } else if (note.getTone() == Tone.F && note.getPitch() == 5) {
            noteLocation = lineArray[1];
        }

        return (int) noteLocation;
    }

    private Drawable getNoteShape(Note note) {
        Drawable noteShape = null;

        switch (note.getDuration()) {
            case WHOLE:
                noteShape = getResources()
                        .getDrawable(R.drawable.whole, null);
                break;

            case HALF:
                noteShape = getResources()
                        .getDrawable(R.drawable.h_note_head, null);
                break;

            case QUARTER:
                noteShape = getResources()
                        .getDrawable(R.drawable.q_note_head, null);
                break;

            case EIGHTH:
                noteShape = getResources()
                        .getDrawable(R.drawable.q_note_head, null);
                break;

            case SIXTEENTH:
                noteShape = getResources()
                        .getDrawable(R.drawable.q_note_head, null);
                break;
        }


        return noteShape;
    }

    public Note getClickedNote(float x, float y){
        Note locatedNote = null;

        for(int i = 0; i < bars; i++) {
            for(int j = 0; j < beats; j++)
            for (Note temp : currentStaff.getNoteList(i,j)){

                if((temp.getX() <= x+(spaceBetween) && temp.getX() >= x-(spaceBetween))
                        &&(temp.getY() <= y+(spaceBetween) && temp.getY() >= y-(spaceBetween))){

                    locatedNote = temp;

                }
            }
        }

        return locatedNote;
    }

    public void selectNote(Note temp, Canvas canvas){
        paint.setColor(Color.RED);

        Drawable noteShape = getNoteShape(temp);
        noteShape.mutate();
        noteShape.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

        noteShape.setBounds(temp.getX() - noteWidth, temp.getY() - noteHeight
                , temp.getX() + noteWidth, temp.getY() + noteHeight);
        noteShape.draw(canvas);

        /**
         * drawing the stem
         */
        if (temp.getDuration() != Duration.WHOLE) {
            canvas.drawLine(temp.getX() + (noteWidth - paint.getStrokeWidth() / 2) - 2
                    , temp.getY() - 15
                    , temp.getX() + (noteWidth - paint.getStrokeWidth() / 2) - 2,
                    temp.getY() - spaceBetween * 2, paint);

        }

        paint.setColor(Color.WHITE);
        //TODO Change constant 40 to figure out the center of a note
        canvas.drawText(temp.getTone().toString(), temp.getX(), temp.getY() + 40, paint);
        paint.setColor(Color.BLACK);
    }

    public Staff getCurrentStaff(){
        return currentStaff;
    }

    public void drawPointer(Canvas canvas, int barLocation, int beatLocation){
        Drawable arrow = getResources().getDrawable(R.drawable.arrowgreen);
        for(Note note : currentStaff.getNoteList(barLocation, beatLocation)){

            arrow.setBounds(note.getX() - noteWidth,
                            spaceBetween*6,
                            note.getX() + noteWidth,
                            spaceBetween*7);
        }

        arrow.draw(canvas);
    }

    public LinkedList<Note> getNextToPlay(){
        return nextToPlay;
    }

    public int getCurrentBar(){
        return currentBar;
    }

    public int getCurrentBeat(){
        return currentBeat;
    }

}



