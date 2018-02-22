package vanwingerdenbarrier.sheetmusictutor.NoteDefense;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Duration;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Staff;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Tone;

/**
 * Created by bvanwingerden on 2/21/18.
 */

public class DrawNoteDefense extends AppCompatImageView {
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
    Paint paint;

    /**
     * a temporary random integer to allow testing of our other methods
     * //TODO implement random based on users level/selected difficulty
     */
    Random random;

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

    /**
     * current beat and bar that we have indexed to in the staff
     */
    int currentBar;
    int currentBeat;


    /**
     * true if there is a currently clicked note
     */
    boolean noteClicked;

    /**
     * x & y of last clicked note
     */
    float lastClickY;
    float lastClickX;

    /**
     * Dimension of the note
     */
    int noteHeight;
    int noteWidth;

    /**
     * the horizontal and vertical margins
     */
    int horMargin;
    int verMargin;

    /**
     * a list containing all the next note to play  is a list so we can implement chords later
     */
    LinkedList<Note> nextToPlay;

    /**
     * the current difficulty of the staff
     */
    int currentDifficulty;

    /**
     * public constructor to create a DrawStaff object
     * sets up paint and also gets the size of the current display
     *
     * @param context
     */
    public DrawNoteDefense(final Context context, int currentDifficulty) {
        super(context);

        nextToPlay = new LinkedList<>();

        noteClicked = false;

        this.currentDifficulty = currentDifficulty;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        paint = new Paint();
        random = new Random();
        size = new Point();
        /* hard coding the horizontal and vertical margins */

        display.getSize(size);

        horMargin = size.y / 6;
        /*middle of screen vermarginATM*/
        verMargin = size.x / 8;


        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(horMargin / 3);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        /* ensures the horizontal margin is back at the start*/
        horMargin = size.y / 6;
        drawStaff(canvas);
    }

    /**
     * draws the lines of the staff on the staff
     *
     * @param canvas the canvas to draw on
     */
    private void drawStaff(Canvas canvas) {
        lineArray = new float[20];

        spaceBetween = ((verMargin / 3)) / bars;
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
     * draws all notes in the currentStaff onto visualization of the staff
     *
     * @param canvas the canvas to draw onto
     */
    private void drawNotes(Canvas canvas, boolean labels) {

        noteHeight = (spaceBetween - (int) paint.getStrokeWidth() / 2) / 2;
        noteWidth = noteHeight + (noteHeight / 3);

        paint.setStrokeWidth(noteHeight / 4);

        horMargin += horMargin;

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

                    /**
                     * drawing sharp if the note is sharp
                     */
                    if (note.isSharp()) {
                        Drawable sharpShape = getResources().getDrawable(R.drawable.sharp, null);
                        sharpShape.setBounds(note.getX() - 3 * (noteWidth), note.getY() - noteHeight
                                , note.getX() - noteWidth, note.getY() + noteHeight);
                        sharpShape.draw(canvas);
                    }

                    if (labels) {
                        paint.setColor(Color.WHITE);
                        //TODO Change constant 40 to figure out the center of a note
                        canvas.drawText(note.getTone().toString(), note.getX(), note.getY() + noteWidth / 2, paint);
                        paint.setColor(Color.BLACK);
                    }
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

    /**
     * gets the note shape of the requested note
     *
     * @param note the note to find the shape of
     * @return the drawable to draw for the given note
     */
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


}

