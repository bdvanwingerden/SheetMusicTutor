package vanwingerdenbarrier.sheetmusictutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import vanwingerdenbarrier.sheetmusictutor.UserInfo.User;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;

/**
 * Created by Doriangh4 on 11/29/2017.
 */

public class ResultsActivity extends AppCompatActivity{

    /**Attached to XML to display number of correct answers*/
    private TextView correctView;

    /**Attached to XML to display users accuracy*/
    private TextView percentView;

    /**Attached to XML to display number of points possible*/
    private TextView pointsView;

    /**Attached to XML to display correct quote*/
    private TextView quoteView;

    /**Attached to XML to update progress bar according to percentage*/
    private ProgressBar prg;

    /**Percent of questions right*/
    private int percentage;

    /**Total number of questions*/
    private int numQuestions;

    /**Total number of questions user got correct*/
    private int correct;

    /**Score or number of points user has collected*/
    private int score;

    /**Max number of points a user may receive during quiz*/
    private int pointsPossible;

    /**Quote to critique user's results*/
    private String quote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User current = new UserList(getBaseContext()).findCurrent();

        setContentView(R.layout.activity_results);
        correctView = (TextView) findViewById(R.id.correct);
        percentView = (TextView) findViewById(R.id.percent);
        pointsView = (TextView) findViewById(R.id.points);
        quoteView = (TextView) findViewById(R.id.quote);



        if( getIntent().getExtras() != null){
            percentage = getIntent().getIntExtra("percent",0);
            numQuestions = getIntent().getIntExtra("numQuestions",0);
            correct = getIntent().getIntExtra("correct",0);
            score = getIntent().getIntExtra("score",0);
            pointsPossible = getIntent().getIntExtra("points",0);
        }

        quoteView.setText(quoteResult(percentage));

        correctView.setText("Correct: "+current.getNumQuestionsCorrect()+"/"+current.getNumQuestionsAttempted()
        );
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

        if(numPercent >= 90)
            quote = "Great Job! You're a Master!";
        else if(numPercent >= 80)
            quote = "Good, but you can do better!";
        else if(numPercent >= 70)
            quote = "Not bad...but a little more practice wont hurt";
        else if(numPercent >= 60)
            quote = "You should definitely give the quiz another try!";
        else
            quote = "Try going over the lesson again.";

        return quote;
    }

    public void quizGame(View v){
        Intent game = new Intent(this, QuizActivity.class);
        this.startActivity(game);
    }


}
