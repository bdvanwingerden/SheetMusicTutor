package vanwingerdenbarrier.sheetmusictutor.StaffStructure;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vanwingerdenbarrier.sheetmusictutor.Game.QuestionDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;

public class StaffFragment extends Fragment implements QuestionDisplay {

    DrawStaff drawStaff;
    Display callback;
    ViewGroup staff;
    int score;
    long illumDelay = 200; /* delay in milliseconds?*/

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity();

        score = 0;

        staff = (ViewGroup) inflater.inflate(R.layout.fragment_staff,
                container, false);

        /** setting the difficulty level to the users current level */
        drawStaff = new DrawStaff(this.getContext(),
                new UserList(getContext()).findCurrent().getCurrentLevel());

        staff.addView(drawStaff);

        staff.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {

                float[] location = new float[2];
                location[0] = event.getX();
                location[1] = event.getY();

                colorNoteOnStaff(location, event);

                return true;
            }
        });

        AlertDialog alertDialog = new AlertDialog.Builder(this.getContext()).create();
        alertDialog.setTitle("Staff");
        alertDialog.setMessage("Play the note on the staff that the pointer is pointing at!");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        dialogInterface.dismiss();
                    }
                });
        alertDialog.setCancelable(false);

        if(new UserList(this.getContext()).findCurrent().getCurrentLevel() <= 1) {
            alertDialog.show();
        }

        return staff;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (Display) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void colorNoteOnStaff(final float[] location, MotionEvent event){
        Handler handler = new Handler();

        if (event.getAction() == MotionEvent.ACTION_DOWN && location != null) {
            drawStaff.reDraw(true, location[0], location[1]);

            staff.removeAllViews();
            staff.addView(drawStaff);

            /* delays the unillumination of the note after the user has lifted up*/
            handler.postDelayed(new Runnable() {
                public void run() {
                    drawStaff.reDraw(false,location[0],location[1]);
                    staff.removeAllViews();
                    staff.addView(drawStaff);
                }
            }, illumDelay);

        }
    }

    /** finds the first note matching the key pressed */
    public float[] getNoteLocation(Note noteToFind){
        return drawStaff.getCurrentStaff().findNoteLocation(noteToFind);
    }

    /**
     * returns the note at the current location of the pointer
     * @param noteToFind the note to check for
     * @return array
     */
    public float[] getNoteAtCurrentLocation(Note noteToFind){
        float[] location = null;
        UserList userList = new UserList(this.getContext());

        ArrayList<Note> noteList = drawStaff.getCurrentStaff()
                .getNoteList(drawStaff.getCurrentBar(), drawStaff.getCurrentBeat());
        for(Note note : noteList){
            if (((note.getTone() == noteToFind.getTone()) && note.isSharp == noteToFind.isSharp)
                    && note.getPitch() == noteToFind.getPitch()) {

                //userList.addUserCorrect();
                score++;
                drawStaff.addPoint();
                if(userList.findCurrent().getNumPointsNeeded()
                        >= userList.findCurrent().getNumQuestionsCorrect()){
                    userList.addUserPointsNeeded();
                    userList.levelUpUser();
                }

                    location = new float[2];
                    location[0] = note.getX();
                    location[1] = note.getY();

                    drawStaff.incrementPointer();
                    if(drawStaff.getCurrentBeat() >= 16){

                        drawStaff.lastClickX = 0;
                        drawStaff.lastClickY = 0;
                        //drawStaff = new DrawStaff(this.getContext());
                        callback.questionPressed(null, score, 1); // ENDS this question
                        //TODO Count score for staff mode
                    }
                    return location;


            }else{
                drawStaff.wrong();
            }
        }


        return location;
    }


}
