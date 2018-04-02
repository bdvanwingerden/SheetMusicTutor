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

    public void comboGame(View v){
        Intent game = new Intent(this, GameActivity.class);
        /* game type 2 represents the combo game mode */

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

    public void guessNoteGame(View v){
        Intent game = new Intent(this, GameActivity.class);
        game.putExtra("gameType", 5);
        this.startActivity(game);
    }
}
