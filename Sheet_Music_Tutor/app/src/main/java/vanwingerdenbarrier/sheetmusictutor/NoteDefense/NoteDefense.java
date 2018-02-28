package vanwingerdenbarrier.sheetmusictutor.NoteDefense;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import vanwingerdenbarrier.sheetmusictutor.Game.QuestionDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;

/**
 * Fragment that contains the note Defense game and handles any logic for the game
 */
public class NoteDefense extends Fragment {
    /*handler used for animation */
    final Handler handler = new Handler();
    /*used to draw and track note positions */
    DrawNoteDefense drawNoteDefense;
    /*callback to the gameactivity class */
    QuestionDisplay.Display callback;
    /*the viewgroup where the drawnote defense is drawn*/
    ViewGroup staff;
    /* allows us to create and animate any number of notes */
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            staff.removeView(drawNoteDefense);
            staff.addView(drawNoteDefense);
            handler.postDelayed(runnable, 15);
        }
    };

    /**
     * inflates the view to fit its calling container
     *
     * @param inflater           inflates the view
     * @param container          the size to inflate
     * @param savedInstanceState
     * @return the view created for the game
     */
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

    /**
     * initilizes the callback once it is attached
     * @param context
     */
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
        handler.sendEmptyMessage(0);
    }

    public void fireNote(Note noteToFireAt) {
        int hit = drawNoteDefense.fire(noteToFireAt);
        Toast fireResult;
        if (hit == 0) {
            fireResult = Toast.makeText(this.getContext(), "MISS", Toast.LENGTH_SHORT);
            fireResult.show();
        } else {
            fireResult = Toast.makeText(this.getContext(), "HIT", Toast.LENGTH_SHORT);
            fireResult.show();
        }
    }
}
