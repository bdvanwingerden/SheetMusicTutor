package vanwingerdenbarrier.sheetmusictutor;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vanwingerdenbarrier.sheetmusictutor.UserInfo.User;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserMenu;

public class Achievements extends AppCompatActivity {

    /**Number of correct answers needed to obtain baby achievement*/
    private final int BABY = 1;

    /**Number of correct answers needed to obtain baby achievement*/
    private final int BALANCE = 4;

    /**Number of correct answers needed to obtain rookie no more and blind ninja achievement*/
    private final int ROOKIE_NINJA = 8;

    /**Number of correct answers needed to obtain hard rocker achievement*/
    private final int ROCKER = 16;

    /**Number of correct answers needed to obtain note-meister achievement*/
    private final int MEISTER = 5;

    /**First Achievement button*/
    Button a1;

    /**second Achievement button*/
    Button a2;

    /**third Achievement button*/
    Button a3;

    /**fourth Achievement button*/
    Button a4;

    /**Fifth Achievement button*/
    Button a5;

    /**Sixth Achievement button*/
    Button a6;

    /**Current user*/
    User current;

    /**Number of questions a users has attempted. Used for achievement calculations*/
    int attempted;

    /**Number of achievements the user has obtained*/
    int achievementCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        current = new UserList(getBaseContext()).findCurrent();

        a1 = (Button) findViewById(R.id.buttonA1);
        a2 = (Button) findViewById(R.id.buttonA2);
        a3 = (Button) findViewById(R.id.buttonA3);
        a4 = (Button) findViewById(R.id.buttonA4);
        a5 = (Button) findViewById(R.id.buttonA5);
        a6 = (Button) findViewById(R.id.buttonA6);

        achievementCount = 0;//save this in database later, rather than resetting every time
        attempted = current.getNumQuestionsAttempted();

        babySteps();
        findingBalance();
        rookieNoMore();
        blindNinja();
        hardRocker();
        noteMeister();


    }

    /**
     * Will change the button to reflect the user having reached the goal need to
     * Obtain the baby achievement
     */
    public void babySteps(){
        if(current.getNumQuestionsCorrect() >= BABY) {
            a1.setText("Baby Steps 1/1");
            a1.setBackground(getResources().getDrawable(R.drawable.round_button_achievement));
            achievementCount++;
        }
    }//end baby()

    /**
     * Will change the button to reflect the user having reached the goal need to
     * Obtain the findingBalance achievement
     */
    public void findingBalance(){
        if(current.getNumQuestionsCorrect() >= BALANCE) {
            a2.setText("FINDING BALANCE 4/4");
            a2.setBackground(getResources().getDrawable(R.drawable.round_button_achievement));
            achievementCount++;
        }
        else{
            a2.setText("FINDING BALANCE "+attempted+"/4");
        }
    }//end findingBalance()

    /**
     * Will change the button to reflect the user having reached the goal need to
     * Obtain the findingBalance achievement
     *
     * User has attempted at least 8 questions without getting a question wrong
     * Could add blind ninja 2 where he has to answer at least 16(Lvl 3) questions without misses
     *
     */
    public void rookieNoMore(){
        if(current.getNumQuestionsCorrect() == current.getNumQuestionsAttempted() && current.getNumQuestionsAttempted() >= ROOKIE_NINJA) {
            a3.setText("ROOKIE NO MORE 8/8");
            a3.setBackground(getResources().getDrawable(R.drawable.round_button_achievement));
            achievementCount++;
        }
        else{
            a3.setText("ROOKIE NO MORE "+attempted+"/8");
        }
    }//end rookieNoMore()

    /**
     * Will change the button to reflect the user having reached the goal need to
     * Obtain the blind ninja achievement
     */
    public void blindNinja(){
        //!!!!Will Change to Reflect Missed Questions!!!!\\
        if(current.getNumQuestionsCorrect() >= ROOKIE_NINJA) {
            a5.setText("BLIND NINJA 8/8");
            a5.setBackground(getResources().getDrawable(R.drawable.round_button_achievement));
            achievementCount++;
        }
        else{
            a5.setText("BLIND NINJA "+attempted+"/8");
        }
    }//end blindNinja()

    /**
     * Will change the button to reflect the user having reached the goal need to
     * Obtain the hard rocker achievement
     */
    public void hardRocker(){
        if(current.getNumQuestionsCorrect() >= ROCKER) {
            a4.setText("HARD ROCKER 16/16");
            a4.setBackground(getResources().getDrawable(R.drawable.round_button_achievement));
            achievementCount++;
        }
        else{
            a4.setText("HARD ROCKER "+attempted+"/16");
        }
    }//end hardRocker()

    /**
     * Will change the button to reflect the user having reached the goal need to
     * Obtain the note-meister achievement
     */
    public void noteMeister(){
        if(achievementCount == MEISTER) {
            a6.setText("NOTE-MEISTER 5/5");
            a6.setBackground(getResources().getDrawable(R.drawable.round_button_achievement));
            achievementCount++;
        }
        else{
            a6.setText("NOTE-MEISTER "+achievementCount+"/5");
        }
    }//end noteMeister()

    /**
     * Use this for all Achievements
     * @param view
     */
    public void achievementButton(View view){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        switch(view.getId()){
            case R.id.buttonA1:
                alertDialog.setTitle("Achievement Description (Baby Steps)");
                alertDialog.setMessage("\"One Baby Step at a Time\" \n Get one question " +
                        "correct to get obtain this achievement");
                break;
            case R.id.buttonA2:
                alertDialog.setTitle("Achievement Description (Finding Balance)");
                alertDialog.setMessage("Finish your first quiz to obtain this achievement");
                break;
            case R.id.buttonA3:
                alertDialog.setTitle("Achievement Description (Rookie No More)");
                alertDialog.setMessage("Obtain this achievement when you reach level 2");
                break;
            case R.id.buttonA4:
                alertDialog.setTitle("Achievement Description (Hard Rocker)");
                alertDialog.setMessage("Obtain this achievement when you reach level 3");
                break;
            case R.id.buttonA5:
                alertDialog.setTitle("Achievement Description (Blind Ninja)");
                alertDialog.setMessage("Get at least 8 questions correct without incorrect answers to get obtain this achievement");
                break;
            case R.id.buttonA6:
                alertDialog.setTitle("Achievement Description (Note-Meister)");
                alertDialog.setMessage("The achievement you obtain when you collect every other achievement");
                break;
        }
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        dialogInterface.dismiss();
                    }
                });

        alertDialog.show();
    }

}//end class Achievements
