package edu.wcu.ddbarrier1.paintmeister;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * Class to implement the main painting functionality
 *
 * @Author - Dorian Barrier
 */
public class Canvas3 extends View implements View.OnTouchListener {

    /**Name of current drawing*/
    static String paintName = "";

    /**ArrayList of a stroke painted*/
    ArrayList<Line> lines;

    /**ArrayList of ArrayList<Line> which each represent a stroke*/
    static ArrayList<ArrayList<Line>> strokes = new ArrayList<>();

    /**List of Paint object for each stroke. Index in paintList matches index of stroke in strokes
     * Array list
     * */
    static ArrayList<Paint> paintList = new ArrayList<>();

    /**Last x coordinate*/
    static float lastX;

    /**Last Y coordinate*/
    static float lastY;

    /**Current color of brush*/
    static int currentColor = Color.BLACK;

    /**Current width of brush*/
    static float currentWidth = 0.0f;

    /**Paint object*/
    Paint paint;

    /**
     * Sets the criteria for a paint brush
     * @param context
     */
    public Canvas3(Context context, AttributeSet attrs) {
        super(context,attrs);

        this.setOnTouchListener(this);
    }

    /**
     * Creates a new paint for a stroke give a color and width
     * @param color - color of current stroke
     * @param width - width of current stroke
     * @return
     */
    static Paint paintFactory(int color, float width){
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(width);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setPathEffect(new CornerPathEffect(10) );
        paint.setAntiAlias(true);
        return paint;
    }

    /**
     * Draw the canvas items
     */
    protected void onDraw(Canvas canvas) {
        if(strokes != null){
            for(int i = 0; i < strokes.size(); i++){
                for(Line l : strokes.get(i)){
                    canvas.drawLine(l.x1, l.y1, l.x2, l.y2, paintList.get(i));
                }
            }
        }
    }//end onDraw()


    /**
     * Takes the x and y of each touch movement and creates a new line that will then be
     * made into a stroke once an UP_EVENT is called
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {


        float x = event.getX();
        float y = event.getY();

        Line line;

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:

                if(lines == null)
                    lines = new ArrayList<>();
                lastX = x;
                lastY = y;

                break;
            case MotionEvent.ACTION_MOVE:
                //addLine(x,y,lines);
                line = new Line(x, y, lastX, lastY);
                lines.add(line);

                lastX = x;
                lastY = y;

                break;
            case MotionEvent.ACTION_UP:

                line = new Line(x, y, lastX, lastY);
                lines.add(line);

                lastX = x;
                lastY = y;

                strokes.add(lines);

                paint = paintFactory(currentColor, currentWidth);
                paintList.add(paint);

                break;
            default:
        }

        this.invalidate();

        return true;
    }//end onTouch()

}//end Canvas3
