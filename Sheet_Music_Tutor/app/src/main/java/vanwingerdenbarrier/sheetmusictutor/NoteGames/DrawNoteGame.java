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
     *
     */
    AnimatedNote spaceship;

    /**
     * contains any shots made from the spaceship
     */
    LinkedList<AnimatedNote> shot;

    /**
     * how many frames the spaceship is given to travel between destinations
     */
    static final int travelFrames = 10;

    /**
     * handles all our toasty toasts
     */
    Toast toasty = Toast.makeText(this.getContext(), "",Toast.LENGTH_SHORT);

    /**
     * set to true when the game is over
     */
    public boolean isDone;

    /**
     * contains all the drawables indicating the number of lives remaining
     */
    public Drawable[] lives;

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

        isDone = false;

        onFieldNotes = new LinkedList<>();

        this.currentDifficulty = currentDifficulty;
        currentScore = 0;
        currentLives = 4;
        lives = new Drawable[currentLives-1];

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

        spaceship = new AnimatedNote(Tone.NOTONE, 0, false);
        shot = new LinkedList<AnimatedNote>();

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
            if(onFieldNotes.size() < 1) {
                drawShip(canvas);
            }
            if (onFieldNotes.size() < random.nextInt(4) + 1 && currentLives > 0) {
                for (int i = random.nextInt(3); i > 0; i--) {
                    addNote(false, getRandomNote(false),
                            random.nextInt(8) + 1);
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
        updateLives();
        updateScore();
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

        horMargin += horMargin;


        note.setX(horMargin);
        note.setY(locateNote(note));
        Drawable noteShape;

        /**
         * drawing the note head
         */
        if(gameMode == 1) {
            noteShape = getResources()
                    .getDrawable(R.drawable.q_note_head, null);
        }else{
            noteShape = getResources().getDrawable(R.drawable.ic_001_ufo, null);
        }
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

                if (note.turnsSinceHit >= travelFrames && (note.getY() < spaceship.getY() + noteWidth
               && note.getY() > spaceship.getY() - noteWidth)) {

                    paint.setColor(Color.RED);
                    Paint temp1 = new Paint();
                    temp1.setColor(Color.RED);
                    temp1.setStrokeWidth(noteWidth/4);
                    canvas.drawLine(spaceship.getX(), spaceship.getY(),
                            note.getX(),note.getY(), temp1);
                    note.setVerSpeed(9);
                    paint.setColor(Color.BLACK);
                    spaceship.getNoteShape().draw(canvas);
                }

                note.setX(note.horSpeed + note.getX());
                note.setY(note.verSpeed + note.getY());

                if(note != spaceship){
                    note.noteShape.setBounds((note.getX() - noteWidth), (note.getY() - noteHeight)
                            , note.getX() + noteWidth, note.getY() + noteHeight);
                }else{
                    spaceship.noteShape.setBounds(spaceship.getX() - 3*noteWidth,
                            spaceship.getY() - 2*noteHeight,
                            spaceship.getX(),
                            (int) spaceship.getY() + 2*noteHeight);
                }

                if(spaceship.getTarget() == note && (spaceship.getY() >= (note.getY() - noteHeight/4))
                        &&  (spaceship.getY() <= (note.getY() + noteHeight/4))){
                    note.setDestroyed(getContext());
                    currentScore++;
                    spaceship.setVerSpeed(0);
                    spaceship.setTarget(new AnimatedNote(Tone.NOTONE, 0, false));
                }

                note.noteShape.draw(canvas);
            }
            if ((note.getX() >= size.x - 100 || note.getY() > size.y || note.getY() < 0)
                    && note != spaceship) {

                if(!note.isDestroyed && gameMode == 0){
                    note.setDestroyed(this.getContext());
                    currentLives--;
                }else if(!note.isPlayed && gameMode == 1){
                    note.setIsPlayed();
                    currentLives--;
                }

                note.setIsPlayed();
                onFieldNotes.remove(note);
            }
            if((note.getY() > size.y - size.y/2 || note.getY() < 0)
                    && note == spaceship){
                spaceship.verSpeed = 0;
            }
            if (currentLives == 0) {
                temp.clear();
                isDone = true;
            }else if(currentScore >= 20){
                temp.clear();
                isDone = true;
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
        while(tempTone == Tone.NOTONE){
            tempTone = Tone.values()[random.nextInt(Tone.values().length)];
        }

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
    public void fire(Note noteToFireAt) {

        LinkedList<AnimatedNote> tempList = (LinkedList<AnimatedNote>) onFieldNotes.clone();

        for (AnimatedNote note : tempList) {
            if (note.getTone().equals(noteToFireAt.getTone())
                    && !note.isDestroyed) {

                spaceship.setTarget(getClosestUnplayedTone(note.getTone()));

                if(spaceship.getY() > note.getY()) {
                    spaceship.setVerSpeed((note.getY()-spaceship.getY())/travelFrames);
                }else if(spaceship.getY() < note.getY()){
                    spaceship.setVerSpeed(-(spaceship.getY()-note.getY())/travelFrames);
                }else{

                }

            }
        }

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
                currentScore++;

            } else if (noteDist < noteWidth && noteDist > -(noteWidth * 2)) {
                played = getFirstUnplayed();
                drawResult(getResources().getDrawable(R.drawable.ic_slow, null), played);
                played.setIsPlayed();
                currentLives--;
            }
            else if (noteDist > noteWidth && noteDist < -(noteWidth * 2)) {
                played = getFirstUnplayed();
                drawResult(getResources().getDrawable(R.drawable.ic_slow, null), played);
                played.setIsPlayed();
                currentLives--;
            }else{
                toasty.setText("Too Soon!");
                toasty.show();
            }
        } else {
            if(getFirstUnplayed().getX() > goalPos && getFirstUnplayed() != null){
                played = getFirstUnplayed();
                drawResult(getResources().getDrawable(R.drawable.ic_miss, null), played);
                played.setIsPlayed();
                currentLives--;
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
       return new AnimatedNote(Tone.NOTONE, 0, false);
    }

    /**
     * gets the unplayed note of a given tone closest to the ege of the screen
     * @param tone the tone to search for
     * @return the AnimatedNote that was found
     */
    public AnimatedNote getClosestUnplayedTone(Tone tone){
        AnimatedNote closest = null;
        for(AnimatedNote note: onFieldNotes) {
            if (note.getTone() == tone && !note.isDestroyed) {
                if(closest == null){
                    closest = note;
                }else{
                    if(closest.getX() < note.getX()){
                        closest = note;
                    }
                }
            }
        }
        return  closest;
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

    /**
     * creates an AnimatedNote and sets its drawable shape to a spaceship then initiates any other
     * variables for the spaceship
     * @param canvas the canvas to draw the ship onto
     */
    public void drawShip(Canvas canvas){
        Drawable ship = getResources().getDrawable(R.drawable.ic_002_spaceship, null);
        spaceship.setNoteShape(ship);
        spaceship.setIsPlayed();
        spaceship.setHorSpeed(0);
        spaceship.setVerSpeed(0);
        spaceship.setX(size.x);
        spaceship.setY((int) lineArray[9]);
        spaceship.noteShape.setBounds(spaceship.getX() - 3*noteWidth, spaceship.getY() - 2*noteHeight,
                spaceship.getX(),(int) spaceship.getY() + 2*noteHeight);
        spaceship.getNoteShape().draw(canvas);
        onFieldNotes.add(spaceship);
    }

    /**
     * draws the number of remaining lives that the player has
     */
    public void updateLives(){

        for(int i = 0; i < lives.length; i++){
            if(i < currentLives-1) {
                lives[i] = getResources().getDrawable(R.drawable.ic_life);
            }else{
                lives[i] = getResources().getDrawable(R.drawable.ic_lost_life);
            }
        }

        int x = size.x;
        int y = 0;

        for(Drawable life : lives){
            life.setBounds(x - 2*noteWidth, y, x, y + 2* noteWidth);
            life.draw(canvas);
            x -= 3*noteWidth;
        }

    }

    /**
     * draws the current score of the user that is playing
     */
    public void updateScore(){
        paint.setTextSize(2*noteWidth);
        canvas.drawText("Score : " + currentScore, size.x- 14*noteWidth,
                paint.getTextSize(), paint);
    }

}

