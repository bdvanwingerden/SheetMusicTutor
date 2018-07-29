package vanwingerdenbarrier.sheetmusictutor;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import vanwingerdenbarrier.sheetmusictutor.Achievements.ResultsActivity;
import vanwingerdenbarrier.sheetmusictutor.Game.GameActivity;
import vanwingerdenbarrier.sheetmusictutor.Game.GameSelection;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.User;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserMenu;

/**
 * @author Bronson VanWingerden
 * the main menu activity
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        if(new UserList(this).findCurrent() == null){

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Welcome To Sheet Music Tutor!");
            alertDialog.setMessage("Please Create a user to get started!");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int j) {
                            dialogInterface.dismiss();
                            Intent userMenu = new Intent(getApplicationContext(), UserMenu.class);
                            userMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            userMenu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(userMenu);
                        }
                    });

            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }

        View animatedBG = findViewById(R.id.mainlayout);
    }

    /**
     * when the start button is pressed this automatically starts combo mode and gets right into the
     * game
     *
     * @param v
     */
    public void startButton(View v){
        Intent game = new Intent(this, GameActivity.class);
        game.putExtra("gameType", 2);
        if (new UserList(this).findCurrent() == null) {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("No Current User found");
            alertDialog.setMessage("Please Select a User");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int j) {
                            dialogInterface.dismiss();
                            Intent userMenu = new Intent(getApplicationContext(), UserMenu.class);
                            userMenu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(userMenu);
                        }
                    });

            alertDialog.show();
        } else {
            this.startActivity(game);
        }
    }//end startButton()

    public void chooseModeButton(View v) {
        Intent game = new Intent(this, GameSelection.class);
        if(new UserList(this).findCurrent() == null){

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("No Current User found");
            alertDialog.setMessage("Please Select a User");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int j) {
                            dialogInterface.dismiss();
                            Intent userMenu = new Intent(getApplicationContext(), UserMenu.class);
                            userMenu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(userMenu);
                        }
                    });

            alertDialog.show();
        }else {
            this.startActivity(game);
        }
    }//end startButton()

    public void optionsButton(View v){
        Intent optionsMenu = new Intent(this, OptionsActivity.class);
        if (new UserList(this).findCurrent() == null) {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("No Current User found");
            alertDialog.setMessage("Please Select a User");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int j) {
                            dialogInterface.dismiss();
                            Intent userMenu = new Intent(getApplicationContext(), UserMenu.class);
                            getApplicationContext().startActivity(userMenu);
                        }
                    });

            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        } else {
            this.startActivity(optionsMenu);
        }
    }


    public void statsButton(View v) {

        User current = new UserList(getBaseContext()).findCurrent();

        if(new UserList(this).findCurrent() == null){

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("No Current User found");
            alertDialog.setMessage("Please Select a User");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int j) {
                            dialogInterface.dismiss();
                            Intent userMenu = new Intent(getApplicationContext(), UserMenu.class);
                            getApplicationContext().startActivity(userMenu);
                        }
                    });

            alertDialog.show();
        }else {

            boolean isQuiz = false;

            float percentage = ( (float) current.getNumQuestionsCorrect()/ (float) current.getNumPointsNeeded())*100;

            Intent stats = new Intent(this, ResultsActivity.class);

            stats.putExtra("percent",(int) percentage);//random number for now(Level progress)
            stats.putExtra("correct",current.getNumQuestionsCorrect());
            stats.putExtra("numQuestions",current.getNumQuestionsAttempted());
            stats.putExtra("score",current.getCurrentLevel());//random number for now(Score)
            stats.putExtra("points",current.getNumPointsNeeded());
            stats.putExtra("isQuiz",isQuiz);

            this.startActivity(stats);
        }
    }//end method()

}
