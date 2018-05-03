package edu.wcu.ddbarrier1.paintmeister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/***
 * Class to implement the options menu for the app
 *
 * @Author - Dorian Barrier
 *
 */
public class Options4 extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener{

    /**Current size of the stroke*/
    static int percentWidth = 0;

    /**String that show size of stroke*/
    static String percentText = "Width 0%";

    /**Text view displaying the size of current stroke*/
    private TextView textView;

    /**Progress bar showing size of current stroke*/
    private ProgressBar pb;

    /**SeekBar to set size of each stroke*/
    private SeekBar sb;

    /**Button to apply changes to app*/
    Button applyBtn;

    /**Tag for shared preferences*/
    public static final String MYPREFERENCES = "MyPrefs2" ;

    /**Tag to get width from shared pref*/
    public static final String WIDTH = "Width" ;

    /**Shared preferences object*/
    SharedPreferences sharedPreferences;

    /***
     * OnCreate to set default variables and obtain values from shared preferences
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options4);

        sharedPreferences = getSharedPreferences(MYPREFERENCES,MODE_PRIVATE);

        //Get width from shared pref
        if(sharedPreferences.contains(WIDTH)){
            percentWidth = sharedPreferences.getInt(WIDTH,0);
            Canvas3.currentWidth = percentWidth;
            percentText = "Width - "+percentWidth+"%";
        }

        textView = (TextView) findViewById(R.id.textView1);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        sb = (SeekBar) findViewById(R.id.seekBar);

        textView.setText(percentText);

        pb.setProgress(percentWidth);
        sb.setProgress(percentWidth);
        textView.setText(percentText);

        applyBtn = (Button) findViewById(R.id.applyBtn);
        applyBtn.setOnClickListener(this);

        sb.setOnSeekBarChangeListener(this);

    }//end onCreate()


    /**
     * Changes the width when the SeekBar's position is changed
     * @param seekBar - seekbar to track
     * @param width - width of the stroke
     * @param b
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int width, boolean b) {
        percentWidth = width;
        percentText = "Width - "+width+"%";
        Canvas3.currentWidth = width;
        pb.setProgress(width);
        textView.setText(percentText);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**
     * OnClick for apply changes button
     * Commits changes to shared pref
     * @param view - apply changes button
     */
    @Override
    public void onClick(View view) {

        Intent i = new Intent(this, Touch.class);

        switch(view.getId()){
            case R.id.applyBtn:

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt(WIDTH,percentWidth);
                editor.commit();
                Toast.makeText(this, "Changes Applied!", Toast.LENGTH_LONG).show();
                break;
        }
        this.startActivity(i);

    }
}//end class Options4
