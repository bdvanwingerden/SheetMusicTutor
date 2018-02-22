package vanwingerdenbarrier.sheetmusictutor.NoteDefense;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vanwingerdenbarrier.sheetmusictutor.Game.QuestionDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;


public class NoteDefense extends Fragment {
    final Handler handler = new Handler();
    DrawNoteDefense drawNoteDefense;
    QuestionDisplay.Display callback;
    ViewGroup staff;
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            staff.removeView(drawNoteDefense);
            staff.addView(drawNoteDefense);
            handler.postDelayed(runnable, 33L);  // 1 second delay
        }
    };

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity();

        staff = (ViewGroup) inflater.inflate(R.layout.fragment_staff,
                container, false);

        drawNoteDefense = new DrawNoteDefense(this.getContext(),
                new UserList(getContext()).findCurrent().getCurrentLevel());

        staff.addView(drawNoteDefense);

        runnable.run();

        return staff;
    }

    @Override
    public void onStart() {
        super.onStart();

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
