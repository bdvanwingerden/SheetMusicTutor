package vanwingerdenbarrier.sheetmusictutor.Quiz;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import vanwingerdenbarrier.sheetmusictutor.Game.QuestionDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.DrawStaff;

public class QuizQuestionFragment extends Fragment implements QuestionDisplay{

    Display callback;
    ViewGroup view;
    QuestionStorage qS;
    String correctAnswer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity();

        qS = new QuestionStorage();
        qS.initialQuestions(this.getContext());

        view = (ViewGroup) inflater.inflate(R.layout.fragment_quiz_question,
                container, false);

        TextView question = (TextView) view.findViewById(R.id.questionString);
        question.setText(qS.getQuestion(0));
        correctAnswer = qS.getCorrectAnswer(0);


        return view;
    }

    public void checkIfCorrect(String answer){

        if(answer.equals(correctAnswer)){
            Toast.makeText(this.getContext(),"Correct", Toast.LENGTH_SHORT);
            callback.questionPressed();
        }else{
            Toast.makeText(this.getContext(),"Incorrect, Try Again", Toast.LENGTH_SHORT);
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
