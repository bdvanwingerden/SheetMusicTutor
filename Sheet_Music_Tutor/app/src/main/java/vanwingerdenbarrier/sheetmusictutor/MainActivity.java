package vanwingerdenbarrier.sheetmusictutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


/**
 * @author Bronson VanWingerden
 * the main menu activity
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * when the start button is pressed this method calls the the Game Activity
     * TODO add a game type selection screen before activity
     * @param v
     */
    public void startButton(View v){
        Intent game = new Intent(this, GameActivity.class);
        this.startActivity(game);
    }

    public void optionsButton(View v){
        Intent optionsMenu = new Intent(this, OptionsActivity.class);
        this.startActivity(optionsMenu);
    }


    public void quizButton(View v) {
        Intent quiz = new Intent(this, QuizActivity.class);
        this.startActivity(quiz);
    }
}
