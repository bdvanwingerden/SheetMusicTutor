package vanwingerdenbarrier.sheetmusictutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by Doriangh4 on 11/29/2017.
 */

public class ResultsActivity extends AppCompatActivity{

    ProgressBar prg;
    int percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        if( getIntent().getExtras() != null){
            percentage = getIntent().getIntExtra("key1",0);
        }

        //System.out.println("Percentage 2: "+percentage);
        prg = (ProgressBar) findViewById(R.id.ProgressBar);
        prg.setProgress(percentage);
    }

    public void menuButton(View v){
        Intent menu = new Intent(this, MainActivity.class);
        this.startActivity(menu);
    }

    public void quizButton(View v) {
        Intent quiz = new Intent(this, QuizActivity.class);
        this.startActivity(quiz);
    }



}
