package vanwingerdenbarrier.sheetmusictutor.Game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import vanwingerdenbarrier.sheetmusictutor.Quiz.QuizActivity;
import vanwingerdenbarrier.sheetmusictutor.R;

public class GameSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);
    }

    public void staffGame(View v){
        Intent game = new Intent(this, GameActivity.class);
        /* game type 1 represents the staff game mode */
        game.putExtra("gameType", 1);
        this.startActivity(game);
    }

    public void quizGame(View v){
        Intent game = new Intent(this, QuizActivity.class);
        this.startActivity(game);
    }

    public void noteDefenseGame(View v) {
        Intent game = new Intent(this, GameActivity.class);
        game.putExtra("gameType", 3);
        this.startActivity(game);
    }

    public void noteHeroGame(View v) {
        Intent game = new Intent(this, GameActivity.class);
        game.putExtra("gameType", 4);
        this.startActivity(game);
    }

    /**
     * on click method to take user to know your keyboard game mode
     * @param v - instance of the button
     */
    public void knowKeyboardGame(View v){
        Intent game = new Intent(this, GameActivity.class);
        game.putExtra("gameType", 6);
        this.startActivity(game);
    }//end knowKeyboardGame()

    /**
     * on click method to take user to play along game mode
     * @param v - instance of the button
     */
    public void playAlongGame(View v){
        Intent game = new Intent(this, GameActivity.class);
        game.putExtra("gameType", 7);
        this.startActivity(game);
    }//end knowKeyboardGame()

}//end Class GameSelection
