package vanwingerdenbarrier.sheetmusictutor.Quiz;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vanwingerdenbarrier.sheetmusictutor.Game.AnswerDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;


public class QuizAnswerFragment extends Fragment implements AnswerDisplay{
    Display callback;
    View view;
    QuestionStorage qS;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        qS = new QuestionStorage();
        qS.initialQuestions(this.getContext());
        view = inflater.inflate(R.layout.fragment_quiz_answer, container, false);

        createAnswerButtons(view, 0);

        return view;
    }

    public void createAnswerButtons(View view, int index){
        LinearLayout linearLayout = view.findViewById(R.id.linearLayout);
        for(String answer : qS.getChoices(index)){
        Button tempButton = new Button(view.getContext());
        tempButton.setText(answer);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                callback.answerPressed(button.getText(), null);
            }
        });
        tempButton.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT));
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
