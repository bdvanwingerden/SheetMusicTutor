package vanwingerdenbarrier.sheetmusictutor.NoteGames;

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
    DrawNoteGame drawNoteGame;
    /*callback to the gameactivity class */
    QuestionDisplay.Display callback;
    /*the viewgroup where the drawnote defense is drawn*/
    ViewGroup staff;
    /* handles any toasts*/
    Toast toasty;
    /* allows us to create and animate any number of notes */
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(drawNoteGame.isDone){
                callback.questionPressed(null);
            }else {
                staff.removeView(drawNoteGame);
                staff.addView(drawNoteGame);
                handler.postDelayed(runnable, 15);
            }
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

        drawNoteGame = new DrawNoteGame(this.getContext(),
                new UserList(getContext()).findCurrent().getCurrentLevel(), 0);

        staff.addView(drawNoteGame);

        runnable.run();

        return staff;
    }

    /**
     * initilizes the callback once it is attached
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (QuestionDisplay.Display) context;
            toasty = Toast.makeText(this.getContext(), "",Toast.LENGTH_SHORT);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }

    /**
     * ensures that the handlers loop is stopped when the screen is exited
     */
    @Override
    public void onDetach() {
        super.onDetach();
        handler.sendEmptyMessage(0);
    }

    /**
     * Accepts a note and creates a toast
     * @param noteToFireAt
     */
    public void fireNote(Note noteToFireAt) {
        drawNoteGame.fire(noteToFireAt);
        //toasty.show();
    }
}
