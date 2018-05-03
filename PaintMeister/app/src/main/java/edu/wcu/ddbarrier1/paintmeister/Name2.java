package edu.wcu.ddbarrier1.paintmeister;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/***
 * Implements functionality for Name Class
 * This classed is used to name a newly created drawing
 *
 * @Author - Dorian Barrier
 */
public class Name2 extends AppCompatActivity {

    /**Suggested name of painting*/
    String paintName;

    /**text view to enter painting name*/
    TextView tv1;

    /**Edit text to enter painting name*/
    EditText et1;

    /**Button to confirm painting name*/
    Button b1;

    /**List of taken names*/
    static ArrayList<String> nameList = new ArrayList<>();

    /***
     * On Create to initialize default values
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name2);

        paintName = "";

        tv1 = (TextView) findViewById(R.id.enterName);
        et1 = (EditText) findViewById(R.id.editName);
        b1 = (Button) findViewById(R.id.okNameBtn);

    }

    /**
     * Sets the paintings name when ok is pressed
     * Alert dialog if no alpha character is set or name is taken
     *
     * @param view
     */
    public void okPressed(View view){

        int id = view.getId();

        if(id == R.id.okNameBtn){
            String tempName = et1.getText().toString();

            if(checkAlpha(tempName) && !nameList.contains(tempName)) {
                paintName = tempName;
                Canvas3.paintName = paintName;
                Intent backHome = new Intent(this, Touch.class);
                this.startActivity(backHome);
            }
            else{
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Error!");
                alertDialog.setMessage("The Entered Name is either take or contains non-alphabetic" +
                        " characters!");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                dialogInterface.dismiss();
                            }
                        });

                alertDialog.show();
            }
        }

    }//end okPressed()

    /**
     * Helper to check if the entered name only contains letters
     * @param s - name being checked
     * @return - true if does contain, false if not
     */
    public static boolean checkAlpha(String s){
        for(int i=0; i < s.length(); i++){
            char ch = s.charAt(i);
            if (Character.isLetter(ch)) {
                continue;
            }
            return false;
        }
        return true;
    }//end checkAlpha()
}
