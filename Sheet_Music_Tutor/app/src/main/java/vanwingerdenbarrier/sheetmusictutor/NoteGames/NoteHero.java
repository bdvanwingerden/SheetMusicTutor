package vanwingerdenbarrier.sheetmusictutor.NoteGames;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vanwingerdenbarrier.sheetmusictutor.Game.QuestionDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.User;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;

/**
 * Fragment that contains the note Hero game and handles any logic for the game
 */
public class NoteHero extends Fragment {
    /*handler used for animation */
    final Handler handler = new Handler();
    /*used to draw and track note positions */
    DrawNoteGame drawNoteGame;
    /*callback to the gameactivity class */
    QuestionDisplay.Display callback;
    /*the viewgroup where the drawnote defense is drawn*/
    ViewGroup staff;

    int prevScore;

    /* allows us to create and animate any number of notes */
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(drawNoteGame.isDone){
                UserList userList = new UserList(getContext());
                User current = userList.findCurrent();
                current.setHero_level(current.getHero_level() + drawNoteGame.currentLives - 2);
                userList.updateUser(current, "hero_level", current.getHero_level());

                current.setNumQuestionsCorrect(current.getNumQuestionsCorrect()
                        + drawNoteGame.currentScore - prevScore);

                current.setNumQuestionsAttempted(current.getNumQuestionsAttempted()
                        + drawNoteGame.attempts);

                if(current.getNumQuestionsCorrect()
                        >= current.getNumPointsNeeded()){
                    userList.levelUpUser();
                    userList.addUserPointsNeeded();
                }

                userList.updateUser(current, "attempts", current.getNumQuestionsAttempted());
                userList.updateUser(current, "correct", current.getNumQuestionsCorrect());

                callback.questionPressed(null, drawNoteGame.currentScore, drawNoteGame.currentLives);
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
                new UserList(getContext()).findCurrent().getHero_level(), 1);

        Bundle args = getArguments();

        prevScore = args.getInt("score");

        drawNoteGame.setLivesAndScore(args.getInt("lives"), args.getInt("score"));

        staff.addView(drawNoteGame);

        AlertDialog alertDialog = new AlertDialog.Builder(this.getContext()).create();
        alertDialog.setTitle("Note Hero");
        alertDialog.setMessage("Play the notes when they are lined up with the Green line!");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        dialogInterface.dismiss();
                        runnable.run();
                    }
                });
        alertDialog.setCancelable(false);


        if(new UserList(this.getContext()).findCurrent().getHero_level() <= 1){
            alertDialog.show();
        }else{
            runnable.run();
        }

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
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }

    /**
     * executes onDetach ending the handlers loop
     */
    @Override
    public void onDetach() {
        super.onDetach();
        handler.sendEmptyMessage(0);
    }

    /**
     * handles playing notes
     *
     * @param note the note that is played
     */
    public void playNote(Note note) {
        drawNoteGame.playNote(note);
    }

}
