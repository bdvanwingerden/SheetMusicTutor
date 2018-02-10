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

import vanwingerdenbarrier.sheetmusictutor.Game.QuestionDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.DrawStaff;

public class QuizQuestionFragment extends Fragment implements QuestionDisplay{

    Display callback;
    ViewGroup quizQuestion;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity();

        quizQuestion = (ViewGroup) inflater.inflate(R.layout.fragment_quiz_question,
                container, false);


        //staff.addView(drawStaff);


        return quizQuestion;
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
