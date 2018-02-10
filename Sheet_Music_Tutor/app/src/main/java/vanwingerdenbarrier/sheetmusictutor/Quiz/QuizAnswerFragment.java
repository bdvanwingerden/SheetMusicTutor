package vanwingerdenbarrier.sheetmusictutor.Quiz;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vanwingerdenbarrier.sheetmusictutor.Game.AnswerDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;


public class QuizAnswerFragment extends Fragment implements AnswerDisplay{
    Display callback;
    ViewGroup quizAnswer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        quizAnswer = (ViewGroup) inflater.inflate(R.layout.keyboard_fragment, container, false);

        return quizAnswer;
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
