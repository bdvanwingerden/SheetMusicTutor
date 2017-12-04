package vanwingerdenbarrier.sheetmusictutor.Game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        this.startActivity(game);
    }

    public void quizGame(View v){
        Intent game = new Intent(this, QuizActivity.class);
        this.startActivity(game);
    }
}
