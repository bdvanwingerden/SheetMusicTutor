package vanwingerdenbarrier.sheetmusictutor.NoteGames;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Random;
import java.util.zip.CheckedOutputStream;

import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Tone;

/**
 * Public class that handles the drawing and position tracking of notes in NoteDefense
 */
public class DrawNoteGame extends AppCompatImageView {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    final int shotSpeedDivision = 10;
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
    /**
     * the canvas we are drawing onto
     */
    Canvas canvas;
    /**
     * the gamemode to draw 0 for note defense & 1 for note hero
     */
    int gameMode;

    /**
     * the position of the goal (if drawn) in Note Hero
     */
    int goalPos;

    /**
     * handles all our toasty toasts
     */
    Toast toasty = Toast.makeText(this.getContext(), "",Toast.LENGTH_SHORT);

    /**
     * public constructor to create a DrawStaff object
     * sets up paint and also gets the size of the current display
     *
     * @param context
     * @param gameMode          0 = NoteDefense, 1 = NoteHero
     * @param currentDifficulty
     */
    public DrawNoteGame(final Context context, int currentDifficulty, int gameMode) {
        super(context);

        onFieldNotes = new LinkedList<>();

        this.currentDifficulty = currentDifficulty;
        currentScore = 0;
        currentLives = 4;
        this.gameMode = gameMode;

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

    /**
     * initializes and handles the drawing of the specified game mode on the given canvas
     * @param canvas the canvas to draw on
     */
    @Override
    protected void onDraw(final Canvas canvas) {
        /* ensures the horizontal margin is back at the start*/
        horMargin = 0;
        this.canvas = canvas;
        drawStaff(canvas);

        if (gameMode == 0) {
            if (onFieldNotes.size() < random.nextInt(5) && currentLives > 0) {
                for (int i = random.nextInt(4); i > 0; i--) {
                    addNote(false, getRandomNote(false),
                            random.nextInt(15) + 1);
                }
            }
        } else if (gameMode == 1) {
            int tempo = 3;

            if (onFieldNotes.size() < 5) {
                if (onFieldNotes.isEmpty()) {
                    addNote(false, getRandomNote(true), tempo);
                } else if (onFieldNotes.peekLast().getX() == ((size.x / 4) - ((size.x / 4) % tempo))) {
                    addNote(false, getRandomNote(true), tempo);
                }
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

        if (gameMode == 1) {
            goalPos = ((size.x / 4) * 3);
            canvas.drawLine(goalPos, lineArray[1], goalPos, lineArray[19], paint);
        }
    }

    /**
     * draws all notes in the currentStaff onto visualization of the staff
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

        if (note.isSharp()) {
            Drawable sharpShape = getResources().getDrawable(R.drawable.sharp, null);

            Drawable[] layers = new Drawable[2];
            layers[0] = noteShape;
            layers[1] = sharpShape;
            LayerDrawable tempShape = new LayerDrawable(layers);
            tempShape.setLayerInset(0, noteWidth,0,-noteWidth,0);
            tempShape.setLayerInset(1,-noteWidth,0, noteWidth,0);
            noteShape = tempShape;
        }

        note.setNoteShape(noteShape);
        onFieldNotes.add(note);
        note.setHorSpeed(speed);
    }

    /**
     * increments the notes based on their current speed
     */
    public void updateNotesOnField() {
        LinkedList<AnimatedNote> temp = (LinkedList<AnimatedNote>) onFieldNotes.clone();

        for (AnimatedNote note : temp) {

            if (currentLives > 0) {

                if (note.turnsSinceHit == shotSpeedDivision) {
                    note.setVerSpeed(9);
                }

                note.setX(note.horSpeed + note.getX());
                note.setY(note.verSpeed + note.getY());
                note.noteShape.setBounds((note.getX() - noteWidth), (note.getY() - noteHeight)
                        , note.getX() + noteWidth, note.getY() + noteHeight);

                note.noteShape.draw(canvas);
            }
            if (note.getX() >= size.x - 100 || note.getY() > size.y || note.getY() < 0) {
                note.isDestroyed = true;
                note.setIsPlayed();
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

    /**
     * returns a random note with or without sharps
     * @param sharpsAllowed allows for sharps to be randomly selected
     * @return the note that was randomly selected
     */
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

    /**
     * handles a user firing at a note
     *
     * @param noteToFireAt
     */
    public int fire(Note noteToFireAt) {
        int hit = 0;
        LinkedList<AnimatedNote> tempList = (LinkedList<AnimatedNote>) onFieldNotes.clone();
        for (AnimatedNote note : tempList) {
            if (note.getTone().equals(noteToFireAt.getTone()) && note.getPitch() == noteToFireAt.getPitch()
                    && !note.isDestroyed) {
                note.setDestroyed(this.getContext());
                hit++;

                AnimatedNote shot = new AnimatedNote(Tone.A, 0, false);
                shot.setNoteShape(getResources().getDrawable(R.drawable.arrowgreen));
                shot.setX(size.x / 2);
                shot.setY(0);

                int verSpeed = (note.getY() / shotSpeedDivision);
                shot.setVerSpeed(verSpeed);
                int horSpeed = ((note.horSpeed) + (shotSpeedDivision * note.horSpeed)) / shotSpeedDivision;

                if (note.getX() < shot.getX()) {
                    shot.setHorSpeed(-horSpeed);
                } else {
                    shot.setHorSpeed(horSpeed);
                }

                System.out.println("Shot " + shot.verSpeed + " " + horSpeed);

                onFieldNotes.add(shot);
            }
        }

        if (hit == 0) {
            AnimatedNote shot = new AnimatedNote(Tone.A, 0, false);
            shot.setHorSpeed(30);
            shot.setVerSpeed(-random.nextInt(30));
            shot.setNoteShape(getResources().getDrawable(R.drawable.arrowgreen));
            shot.setX(size.x / 2);
            shot.setY((size.y / 2) + 40);
            onFieldNotes.add(shot);
        }

        return hit;
    }

    /**
     * handles note hero note timing functionality
     * @param note the note that the user played
     */
    public void playNote(Note note) {
        AnimatedNote played;
        toasty.setGravity(Gravity.RIGHT, 0, 0);

        if (getFirstUnplayed().getTone().equals(note.getTone())
                && getFirstUnplayed().isSharp() == note.isSharp()) {


            int noteDist = goalPos - getFirstUnplayed().getX();

            if (noteDist < noteWidth * 2 && noteDist > -noteWidth) {
                played = getFirstUnplayed();
                drawResult(getResources().getDrawable(R.drawable.ic_perfect, null), played);
                played.setIsPlayed();

            } else if (noteDist < noteWidth && noteDist > -(noteWidth * 2)) {
                played = getFirstUnplayed();
                drawResult(getResources().getDrawable(R.drawable.ic_slow, null), played);
                played.setIsPlayed();
            }
            else if (noteDist > noteWidth && noteDist < -(noteWidth * 2)) {
                played = getFirstUnplayed();
                drawResult(getResources().getDrawable(R.drawable.ic_slow, null), played);
                played.setIsPlayed();
            }else{
                toasty.setText("Too Soon!");
                toasty.show();
            }
        } else {
            if(getFirstUnplayed().getX() > goalPos && getFirstUnplayed() != null){
                played = getFirstUnplayed();
                drawResult(getResources().getDrawable(R.drawable.ic_miss, null), played);
                played.setIsPlayed();
            }
        }
    }

    /**
     * returns the first unplayed note in the onFieldNotes list
     * @return returns the first unplayed note or a note that is not part of the list if there is no
     * unplayed note found
     */
    public AnimatedNote getFirstUnplayed(){
       for(AnimatedNote note : onFieldNotes){
           if(!note.isPlayed){
               return note;
           }
       }
       return new AnimatedNote(Tone.A, 20, false);
    }

    /**
     * changes the drawable of an animated note and attempts to grow it
     * @param drawable the drawable to change the ntoe to
     * @param note the note to change the drawable of
     */
    public void drawResult(Drawable drawable, AnimatedNote note){
        drawable.setBounds(note.getX() - noteWidth, (note.getY() -  10 * noteHeight)
                , note.getX() + 20 * noteWidth, note.getY() +  10 *noteHeight);
        note.setNoteShape(drawable);
    }
}

