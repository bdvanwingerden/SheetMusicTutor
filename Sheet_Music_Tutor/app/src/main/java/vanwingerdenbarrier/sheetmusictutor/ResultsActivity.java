package vanwingerdenbarrier.sheetmusictutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import vanwingerdenbarrier.sheetmusictutor.Quiz.QuizActivity;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.User;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;

/**
 * Class to handle writing results to results screen
 * Created by Doriangh4 on 11/29/2017.
 */
public class ResultsActivity extends AppCompatActivity{

    /**Attached to XML to display title*/
    private TextView titleView;

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

    /**if true return quiz results; False return users stats*/
    private boolean isQuiz;

    /**Quote to critique user's results*/
    private String quote;

    User current;

    /**
     * Acquires the fields passed from the class that called results
     * calls the method that actualy writes the results to the screen
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_results);

        current = new UserList(getBaseContext()).findCurrent();

        titleView = (TextView) findViewById(R.id.title);
        correctView = (TextView) findViewById(R.id.correct);
        percentView = (TextView) findViewById(R.id.percent);
        pointsView = (TextView) findViewById(R.id.points);
        quoteView = (TextView) findViewById(R.id.quote);
        prg = (ProgressBar) findViewById(R.id.ProgressBar);


        if( getIntent().getExtras() != null){
            percentage = getIntent().getIntExtra("percent",0);
            numQuestions = getIntent().getIntExtra("numQuestions",0);
            correct = getIntent().getIntExtra("correct",0);
            score = getIntent().getIntExtra("score",0);
            pointsPossible = getIntent().getIntExtra("points",0);
            isQuiz = getIntent().getBooleanExtra("isQuiz",true);
        }

        writeResults(isQuiz);


    }

    /**
     * Restarts quiz when pressed
     * @param v
     */
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

    /**
     * Determine if returning quiz screen or user stats screen
     * if true return quiz results
     * if false return user stats
     * @param resultType
     */
    public void writeResults(boolean resultType){

        if(resultType){//if writing quiz
            titleView.setText("Quiz Results");
            correctView.setText("Correct: "+correct+"/"+numQuestions);
            quoteView.setText(quoteResult(percentage));
            pointsView.setText("Points: "+score+"/"+pointsPossible);
            percentView.setText("Score: "+percentage+"%");
            prg.setProgress(percentage);
        }
        else if(!resultType){//if writing user stats
            titleView.setText(current.getName()+"'s Stats");
            correctView.setText("Correct Overall: "+correct+"/"+numQuestions);
            quoteView.setText(quoteLevel(score));
            pointsView.setText("Level Progress: "+correct+"/"+pointsPossible);
            percentView.setText("Level Progress(%): "+percentage+"%");
            prg.setProgress(percentage);
        }
    }

    /**
     * Gives user a title based on their level
     * @param level - user level
     * @return
     */
    public String quoteLevel(int level){

        if(level <= 1)
            quote = "Novice (LVL 1)";
        else if(level <= 2)
            quote = "Apprentice (LVL 2)";
        else if(level <= 3)
            quote = "Adept (LVL 3)";
        else if(level <= 4)
            quote = "Expert (LVL 4)";
        else
            quote = "Master (LVL 5)";

        return quote;
    }

    /**
     * Starts the quiz game when button is pressed
     * @param v
     */
    public void quizGame(View v){
        Intent game = new Intent(this, QuizActivity.class);
        this.startActivity(game);
    }


}
