package vanwingerdenbarrier.sheetmusictutor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import vanwingerdenbarrier.sheetmusictutor.Quiz.QuestionLibrary;

public class QuizActivity extends AppCompatActivity {

    private QuestionLibrary questionLibrary = new QuestionLibrary();

    /**Attach these variable to XML Buttons*/
    private TextView scoreView;
    private TextView questionView;
    private Button buttonChoice1;
    private Button buttonChoice2;
    private Button buttonChoice3;

    /**Use to compare user answer to correct answer*/
    private String answer;

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
                if (buttonChoice1.getText() == answer) {
                    score += 1;
                    updateScore(score);
                    updateQuestion();
                    //this line of code is optional
                    Toast.makeText(QuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(QuizActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
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

        //Add method to write initial score.
        updateQuestion();//??To get initial question from array to display

        //Start button listener for Button1
        buttonChoice1.setOnClickListener(vl);
        //End button listener for Button1

        //Start button listener for Button2
        buttonChoice2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(buttonChoice2.getText() == answer){
                    score += 1;
                    updateScore(score);
                    updateQuestion();
                    //this line of code is optional
                    Toast.makeText(QuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(QuizActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //End button listener for Button2

        //Start button listener for Button3
        buttonChoice3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes here
                if(buttonChoice3.getText() == answer){
                    score += 1;
                    updateScore(score);
                    updateQuestion();
                    //this line of code is optional
                    Toast.makeText(QuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(QuizActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //End button listener for Button3
    }

    /**
     * Updates the questionNumber and corresponding question and answers
     * Updates text in xml
     */
    public void updateQuestion(){
        questionView.setText(questionLibrary.getQuestions(questionNumber));
        buttonChoice1.setText(questionLibrary.getChoice1(questionNumber));
        buttonChoice2.setText(questionLibrary.getChoice2(questionNumber));
        buttonChoice3.setText(questionLibrary.getChoice3(questionNumber));

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
}//end Class QuizActivity
