package vanwingerdenbarrier.sheetmusictutor.Quiz;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vanwingerdenbarrier.sheetmusictutor.Game.AnswerDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;

/**
 * fragment that displays the multiple choices for a question and sends the selected answer to be checked
 * by the fragment holding the question
 */
public class QuizAnswerFragment extends Fragment implements AnswerDisplay{
    Display callback;
    View view;
    QuestionStorage qS;
    LinearLayout linearLayout;
    static int currentQuestion = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        qS = new QuestionStorage();
        qS.initialQuestions(this.getContext());
        view = inflater.inflate(R.layout.fragment_quiz_answer, container, false);
        linearLayout = view.findViewById(R.id.linearLayout);
        linearLayout.setGravity(Gravity.CENTER);
        createAnswerButtons(view, currentQuestion);

        return view;
    }

    /**
     * sets the current question number to display the answer for
     * @param questionNum
     */
    public void setQuestion(int questionNum){
        currentQuestion = questionNum;
    }

    /**
     * creates buttons based on the currently asked question
     * @param view the view containing the buttons
     * @param index the index of the current question
     */
    public void createAnswerButtons(View view, int index){

        int buttonStyle = R.style.button_style;

        for(String answer : qS.getChoices(index)){
        Button tempButton = new Button(this.getContext());
        tempButton.setText(answer);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                callback.answerPressed(button.getText(), null);
            }
        });
        tempButton.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                    , ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout.addView(tempButton);
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callback = (Display) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
