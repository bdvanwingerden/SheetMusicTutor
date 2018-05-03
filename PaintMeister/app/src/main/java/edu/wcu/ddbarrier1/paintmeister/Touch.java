package edu.wcu.ddbarrier1.paintmeister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 *
 * Implements the touch canvas as well as the save and load features
 *
 * @Author Dorian Barrier
 */
public class Touch extends AppCompatActivity {

    /**
     * The view that holds the canvas
     */
    View touchCanvas;

    /**Paint object*/
    Paint paint;

    /**Shared preferences tag*/
    public static final String MYPREFERENCES = "MyPrefs2";

    /**Tag to obtain brush color from shared pref*/
    public static final String BRUSHCOLOR = "Color" ;

    /**Tag to obtain brush cwidth from shared pref*/
    public static final String WIDTH = "Width" ;

    /**Shared Pref Object*/
    SharedPreferences sharedPreferences;

    /**
     *
     * OnCreate Set values for Touch activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas3);

        touchCanvas = this.findViewById(R.id.view1);

        sharedPreferences = getSharedPreferences(MYPREFERENCES,MODE_PRIVATE);

        if(sharedPreferences.contains(BRUSHCOLOR)){
            Canvas3.currentColor = sharedPreferences.getInt(BRUSHCOLOR,0);
        }

        if(sharedPreferences.contains(WIDTH)){
            Canvas3.currentWidth = sharedPreferences.getInt(WIDTH,0);
        }

        Bundle extras = getIntent().getExtras();
        String loadIn;//filename to load in
        //Set file to load in from load activity
        if(extras != null){
            loadIn = extras.getString("LoadIn");
            load(loadIn);
        }

    }//end onCreate

    /**
     * Inflates the options tool bar
     *
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.canvas_menu, menu);
        return true;

    }//end onCreateOptionsMenu

    /**
     * Implements the functionality for the tool bar
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;

        switch (item.getItemId()) {
            case R.id.action_save:
                save();
                saveFile();
                Canvas3.strokes.clear();
                Canvas3.paintList.clear();
                touchCanvas.invalidate();
                Toast.makeText(this, "Save Was Selected", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_color:
                i = new Intent(this, ColorSelection5.class);
                this.startActivity(i);
                break;
            case R.id.action_options:
                i = new Intent(this, Options4.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                this.startActivity(i);
                break;
            case R.id.action_load:
                i = new Intent(this, Load6.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                this.startActivity(i);
                break;
            case R.id.action_maine_menu:
                i = new Intent(this, MainMenu1.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                this.startActivity(i);
                break;
            case R.id.action_new:
                Canvas3.strokes.clear();
                Canvas3.paintList.clear();
                touchCanvas.invalidate();
                i = new Intent(this, Name2.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                this.startActivity(i);
                break;
            case R.id.action_clear_canvas:
                Canvas3.strokes.clear();
                Canvas3.paintList.clear();
                touchCanvas.invalidate();
                break;

        }

        return super.onOptionsItemSelected(item);
    }//end method

    /**
     * Save the current files to be loaded to shared preferences when a new drawing is saved
     */
    public void saveFile(){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Load_size",Load6.FILES.size());//number of loaded files
        for(int i = 0; i < Load6.FILES.size();i++){
            editor.remove("File"+i);
            editor.putString("File"+i,Load6.FILES.get(i));
        }

        editor.apply();
    }//end saveFile()

    /**
     * Save the coordinate drawn to a csv file and place in internal memory
     */
    public void save(){

        String filename = Canvas3.paintName+".csv";

        //Name is now taken once saved
        if(!Name2.nameList.contains(Canvas3.paintName)){
            Name2.nameList.add(Canvas3.paintName);
        }

        if(!Load6.FILES.contains(Canvas3.paintName))
            Load6.FILES.add(Canvas3.paintName);

        FileOutputStream outputStream;

        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

            for(int i = 0; i < Canvas3.strokes.size(); i++){

                outputStream.write((Canvas3.paintList.get(i).getColor()+",").getBytes());
                outputStream.write((Canvas3.paintList.get(i).getStrokeWidth()+",").getBytes());

                for(int j = 0; j < Canvas3.strokes.get(i).size(); j++){
                    outputStream.write((Canvas3.strokes.get(i).get(j).x1+",").getBytes());
                    outputStream.write((Canvas3.strokes.get(i).get(j).y1+",").getBytes());
                    outputStream.write((Canvas3.strokes.get(i).get(j).x2+",").getBytes());

                    if(j != Canvas3.strokes.get(i).size()-1)
                        outputStream.write((Canvas3.strokes.get(i).get(j).y2+",").getBytes());
                    else
                        outputStream.write((Canvas3.strokes.get(i).get(j).y2+"\n").getBytes());
                }

            }
        }
        catch(FileNotFoundException e){
            Toast.makeText(this, "Error saving file", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        catch(IOException e){
            Toast.makeText(this, "Error saving file", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        catch (Exception e) {
            Toast.makeText(this, "Error saving file", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        Intent i = new Intent(this, Name2.class);
        this.startActivity(i);

    }//end save()

    /**
     * Load in coordinates from internal memory and parse the csv file
     * then redraw the screen parsed coordinates
     */
    public void load(String loadFile){

        Canvas3.strokes.clear();
        Canvas3.paintList.clear();

        String filename = loadFile+".csv";

        File fileIn = new File(this.getFilesDir(), filename);

        try{

            BufferedReader br = new BufferedReader(new FileReader(fileIn));
            String line;

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                paint = Canvas3.paintFactory(Integer.parseInt(tokens[0]),Float.parseFloat(tokens[1]));
                Canvas3.paintList.add(paint);

                ArrayList<Line> lines = new ArrayList<>();
                for(int i = 2; i < tokens.length; i+=4){

                    float x1 = Float.parseFloat(tokens[i]);
                    float y1 = Float.parseFloat(tokens[i+1]);
                    float x2 = Float.parseFloat(tokens[i+2]);
                    float y2 = Float.parseFloat(tokens[i+3]);

                    Line line1 = new Line(x1, y1, x2, y2);
                    lines.add(line1);

                }

                Canvas3.strokes.add(lines);

            }//end while



            br.close();
        }//end try
        catch (FileNotFoundException e){
            Toast.makeText(this, "There was no data to load", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        catch (IOException e) {
            Toast.makeText(this, "Error loading file", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }//end catch

        touchCanvas.invalidate();

    }//end load

}//end touch class
