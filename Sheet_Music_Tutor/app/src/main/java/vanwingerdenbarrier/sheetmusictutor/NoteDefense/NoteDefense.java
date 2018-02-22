package vanwingerdenbarrier.sheetmusictutor.NoteDefense;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vanwingerdenbarrier.sheetmusictutor.Game.QuestionDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;


public class NoteDefense extends Fragment {
    DrawNoteDefense drawNoteDefense;
    QuestionDisplay.Display callback;
    ViewGroup staff;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity();

        staff = (ViewGroup) inflater.inflate(R.layout.fragment_staff,
                container, false);

        /** setting the difficulty level to the users current level */
        drawNoteDefense = new DrawNoteDefense(this.getContext(),
                new UserList(getContext()).findCurrent().getCurrentLevel());

        staff.addView(drawNoteDefense);

        return staff;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (QuestionDisplay.Display) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
