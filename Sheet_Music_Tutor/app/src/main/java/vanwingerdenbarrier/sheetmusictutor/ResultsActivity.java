package vanwingerdenbarrier.sheetmusictutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Doriangh4 on 11/29/2017.
 */

public class ResultsActivity extends AppCompatActivity{

    private TextView correctView;
    private TextView percentView;
    private TextView pointsView;


    ProgressBar prg;
    private int percentage;
    private int numQuestions;
    private int correct;
    private int score;
    private int pointsPossible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        correctView = (TextView) findViewById(R.id.correct);
        percentView = (TextView) findViewById(R.id.percent);
        pointsView = (TextView) findViewById(R.id.points);



        if( getIntent().getExtras() != null){
            percentage = getIntent().getIntExtra("percent",0);
            numQuestions = getIntent().getIntExtra("numQuestions",0);
            correct = getIntent().getIntExtra("correct",0);
            score = getIntent().getIntExtra("score",0);
            pointsPossible = getIntent().getIntExtra("points",0);
        }


        correctView.setText("Correct: "+correct+"/"+numQuestions);
        pointsView.setText("Points: "+score+"/"+pointsPossible);
        percentView.setText("Score: "+percentage+"%");
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

    /**
     * Returns a specific quote based on your score
     * @param numPercent users score
     * @return
     */
    public String quoteResult(int numPercent){

        String quote;

        if(numPercent >= 90)
            quote = "Great Job! You're a Master!";
        else if(numPercent >= 80)
            quote = "Not bad...but a little more practice wont hurt";
        else if(numPercent >= 70)
            quote = "Not bad...but a little more practice wont hurt";
        else if(numPercent >= 60)
            quote = "You should definitely give the quiz another try!";
        else
            quote = "Try going over the lesson again. Then give the quiz another try";

        return quote;
    }



}
