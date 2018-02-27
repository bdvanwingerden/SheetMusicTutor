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

import java.util.LinkedList;
import java.util.Random;

import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Tone;

/**
 * Public class that handles the drawing and position tracking of notes in NoteDefense
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
     */
    Random random;

    /**
     * creates an array to contain the x and y coordinates for the line since each line only has
     * a pair of x,y coordinates corresponding to the start point and end point in the form
     * [xn, yn, xn+1, yn+1]
     */
    float[] lineArray;

    /**
     * the space between the lines of the staff
     */
    int spaceBetween;

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
    LinkedList<AnimatedNote> onFieldNotes;

    /**
     * the current difficulty of the staff
     */
    int currentDifficulty;

    /**
     * the users current score & remaining lives
     */
    int currentScore;
    int currentLives;

    /** the canvas we are drawing onto */
    Canvas canvas;

    /**
     * public constructor to create a DrawStaff object
     * sets up paint and also gets the size of the current display
     *
     * @param context
     */
    public DrawNoteDefense(final Context context, int currentDifficulty) {
        super(context);

        onFieldNotes = new LinkedList<>();

        this.currentDifficulty = currentDifficulty;
        currentScore = 0;
        currentLives = 4;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        paint = new Paint();
        random = new Random();
        size = new Point();

        display.getSize(size);

        horMargin = 0;
        /*middle of screen vermarginATM*/
        verMargin = size.x / 8;


        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(horMargin / 3);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        /* ensures the horizontal margin is back at the start*/
        horMargin = 0;
        this.canvas = canvas;
        drawStaff(canvas);
        if (onFieldNotes.size() < random.nextInt(5) && currentLives > 0) {
            for (int i = random.nextInt(4); i > 0; i--) {
                addNote(false, getRandomNote(false),
                        random.nextInt(19) + 1);
            }
        }
        updateNotesOnField();
    }

    /**
     * draws the lines of the staff on the staff
     *
     * @param canvas the canvas to draw on
     */
    private void drawStaff(Canvas canvas) {
        lineArray = new float[20];

        spaceBetween = ((verMargin / 3));
        paint.setStrokeWidth(spaceBetween / 20);

        int position = verMargin;
        int right = size.x;
        int left = horMargin;


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
     */
    public void addNote(boolean labels, AnimatedNote note, int speed) {

        noteHeight = (spaceBetween - (int) paint.getStrokeWidth() / 2) / 2;
        noteWidth = noteHeight + (noteHeight / 3);

        paint.setStrokeWidth(noteHeight / 4);

        horMargin += horMargin;


        note.setX(horMargin);
        note.setY(locateNote(note));

        /**
         * drawing the note head
         */
        Drawable noteShape = getResources()
                .getDrawable(R.drawable.q_note_head, null);
        noteShape.setBounds((note.getX() - noteWidth), (note.getY() - noteHeight)
                , note.getX() + noteWidth, note.getY() + noteHeight);

        note.setNoteShape(noteShape);
        onFieldNotes.add(note);
        note.setSpeed(speed);
    }

    /**
     * increments the notes based on their current speed
     */
    public void updateNotesOnField() {
        LinkedList<AnimatedNote> temp = (LinkedList<AnimatedNote>) onFieldNotes.clone();

        for (AnimatedNote note : temp) {

            if (!note.isDestroyed && currentLives > 0) {

                note.setX(note.speed + note.getX());
                note.noteShape.setBounds((note.getX() - noteWidth), (note.getY() - noteHeight)
                        , note.getX() + noteWidth, note.getY() + noteHeight);
                note.noteShape.draw(canvas);
            }
            if (note.getX() >= size.x - 100) {
                note.isDestroyed = true;
                onFieldNotes.remove(note);
            }
            if (currentLives == 0) {
                temp.clear();
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

    public AnimatedNote getRandomNote(boolean sharpsAllowed) {
        Tone tempTone = Tone.values()[random.nextInt(Tone.values().length)];

        int tempPitch = 5;
        if (tempTone == Tone.E || tempTone == Tone.F) {
            if (random.nextInt(2) == 0) {
                tempPitch = 4;
            }
        } else if (tempTone == Tone.G) {
            tempPitch = 4;
        }

        boolean isSharp = false;
        if (sharpsAllowed) {
            if (random.nextBoolean()) {
                isSharp = true;
            }
        }

        return (new AnimatedNote(tempTone, tempPitch, isSharp));
    }

}

