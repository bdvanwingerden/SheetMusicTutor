package edu.wcu.ddbarrier1.paintmeister;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Class to implement the main menu functionality
 *
 * @Author - Dorian Barrier
 */
public class MainMenu1 extends AppCompatActivity implements View.OnClickListener{

    /**If false go to enter new drawing screen. True proceed */
    public static boolean name = false;

    /**Takes user to new painting screen*/
    Button btn1;

    /**Takes user to load painting screen*/
    Button btn2;

    /**Takes user to options screen*/
    Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu1);

        btn1 = (Button) findViewById(R.id.newBtn);
        btn2 = (Button) findViewById(R.id.loadBtn);
        btn3 = (Button) findViewById(R.id.optBtn);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

    }//end onCreate()

    /**
     * OnClick for menu buttons
     * @param view
     */
    @Override
    public void onClick(View view) {

        Intent i = null;

        switch(view.getId()){
            case R.id.newBtn:
                i = new Intent(this, Name2.class);
                break;
            case R.id.loadBtn:
                i = new Intent(this, Load6.class);
                break;
            case R.id.optBtn:
                i = new Intent(this, Options4.class);
                break;
        }

        this.startActivity(i);

    }//end onClick()

}//end MainMenu1
