package vanwingerdenbarrier.sheetmusictutor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;

import java.util.ArrayList;

/**
 * Created by Bronson VanWingerden on 9/26/2017.
 */
public class DrawStaff extends View {


    /**
     * stores the dimension of the current display
     */
    Point size;
    Paint paint = new Paint();

    /**
     * creates an array to contain the x and y coordinates for the line since each line only has
     * a pair of x,y coordinates corresponding to the start point and end point in the form
     * [xn, yn, xn+1, yn+1]
     */
    float[] lineArray;
    Staff currentStaff;
    int bars = 1;
    int beats = 4;
    int noteDiameter = 100;
    int subdivision =4;


    public DrawStaff(Context context) {
        super(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        size = new Point();
        display.getSize(size);
    }

    @Override
    protected void onDraw(Canvas canvas){
        drawStaff(canvas);
        populateStaff();
        drawNotes(canvas);
    }

    private void drawStaff(Canvas canvas){
        paint.setStrokeWidth(size.y/50);
        paint.setColor(Color.BLACK);

        lineArray = new float[20];

        int margin = 40;
        int spaceBetween = (size.y/5);
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

    private void populateStaff(){
        // creates an empty staff consisting of 1 bar of 4/4 music
        currentStaff = new Staff(Clef.TREBLE, 1, 4, 4);

        currentStaff.insertNote(bars-1, 0, Tone.E, 4, 4);
        currentStaff.insertNote(bars-1, 1, Tone.F, 4, 4);
        currentStaff.insertNote(bars-1, 2, Tone.E, 4, 4);
        currentStaff.insertNote(bars-1, 3, Tone.F, 4, 4);
    }

    private void drawNotes(Canvas canvas){
        for(int i = 0; i < bars; i++){
            for(int j = 0; j < beats; j++){
                ArrayList<Note> tempList = currentStaff.getNoteList(i,j);
                for(Note note : tempList){
                    float xPos = ((size.x/beats) * j) + 250;
                    canvas.drawCircle(xPos,locateNote(note), noteDiameter, paint);
                }
            }
        }
    }

    private float locateNote(Note note){
        float noteLocation = 0;

        if(note.tone == Tone.E && note.pitch == 4){
            noteLocation = ((size.y/5) * 4) + paint.getStrokeWidth();
        }else if(note.tone == Tone.F && note.pitch == 4){
            noteLocation = ((size.y/5) * 3) + paint.getStrokeWidth() - noteDiameter;
        }

        return noteLocation;
    }

    public float[] getLineArray(){
        return lineArray;
    }
}



