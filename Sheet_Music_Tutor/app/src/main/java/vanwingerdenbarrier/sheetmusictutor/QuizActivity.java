package vanwingerdenbarrier.sheetmusictutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class QuizActivity extends AppCompatActivity {

    private QuestionStorage questionLibrary = new QuestionStorage();

    /**Attach these variable to XML Buttons*/
    private TextView scoreView;
    private TextView questionView;
    private Button buttonChoice1;
    private Button buttonChoice2;
    private Button buttonChoice3;

    /**Use to compare user answer to correct answer*/
    private String answer;

    /**Most possible points you can get*/
    private int pointsPossible = 0;

    /**Score based on number of attempts*/
    private float percentage;

    private int difficulty;

    /**Keep track of users current score*/
    private int score = 0;

    /**Keeps track of the current question number*/
    private int questionNumber = 0;



    private View.OnClickListener vl = new View.OnClickListener(){
        @Override
        public void onClick(View view){

            if (view instanceof  Button) {
                Button button = (Button) view;
                //My logic for Button goes here
                if (button.getText().equals(answer)) {
                    score += difficulty;
                    updateScore(score);
                    if(questionNumber < 4)
                        updateQuestion();
                    //this line of code is optional
                    Toast.makeText(QuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();

                } else {


                    if(difficulty > 1){
                        difficulty--;
                        Toast.makeText(QuizActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                    }
                    else if(difficulty == 1){
                        Toast.makeText(QuizActivity.this, "No More Attempts!", Toast.LENGTH_SHORT).show();
                        if(questionNumber < 4)
                            updateQuestion();
                    }

                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        scoreView = (TextView)findViewById(R.id.score);
        questionView = (TextView)findViewById(R.id.question);
        buttonChoice1 = (Button)findViewById(R.id.choice1);
        buttonChoice2 = (Button)findViewById(R.id.choice2);
        buttonChoice3 = (Button)findViewById(R.id.choice3);

        questionLibrary.initialQuestions(getApplicationContext());
        updateQuestion();//??To get initial question from array to display
        updateScore(score);

        //Start button listener for Button1
        buttonChoice1.setOnClickListener(vl);
        //End button listener for Button1

        //Start button listener for Button2
        buttonChoice2.setOnClickListener(vl);
        //End button listener for Button2

        //Start button listener for Button3
        buttonChoice3.setOnClickListener(vl);
        //End button listener for Button3
    }

    /**
     * Updates the questionNumber and corresponding question and answers
     * Updates text in xml
     */
    public void updateQuestion(){
        pointsPossible+=difficulty;
        questionView.setText(questionLibrary.getQuestion(questionNumber));
        buttonChoice1.setText(questionLibrary.getChoice(questionNumber,1));
        buttonChoice2.setText(questionLibrary.getChoice(questionNumber,2));
        buttonChoice3.setText(questionLibrary.getChoice(questionNumber,3));

        String diff = questionLibrary.getDifficultyScore(questionNumber);
        difficulty = Integer.parseInt(diff);
       //adds difficulty to number of possible points
        answer = questionLibrary.getCorrectAnswer(questionNumber);
        questionNumber++;
    }

    /**
     * Updates the current score
     * @param point or score
     */
    public void updateScore(int point){
        scoreView.setText("" + score);
    }

    public void resultsButton(View v){
        Intent results = new Intent(this, ResultsActivity.class);

        /**
         * changing score here
         */
        pointsPossible = 5;

        percentage = ( (float) score/ (float) pointsPossible)*100;


        Toast.makeText(QuizActivity.this, "Percent: "+percentage, Toast.LENGTH_SHORT).show();

        //System.out.println("Percentage 1: "+percentage);


        results.putExtra("key1",(int) percentage);

        this.startActivity(results);
    }



}//end Class QuizActivity


