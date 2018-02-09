package vanwingerdenbarrier.sheetmusictutor.StaffStructure;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Timer;

import vanwingerdenbarrier.sheetmusictutor.Game.QuestionDisplay;
import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;

public class StaffFragment extends Fragment implements QuestionDisplay {

    DrawStaff drawStaff;
    Display callback;
    ViewGroup staff;
    long illumDelay = 200; /* delay in milliseconds?*/

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity();

        staff = (ViewGroup) inflater.inflate(R.layout.fragment_staff,
                container, false);

        drawStaff = new DrawStaff(this.getContext());
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

        return staff;
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

    public float[] getNoteAtCurrentLocation(Note noteToFind){
        float[] location = null;
        UserList userList = new UserList(this.getContext());

        ArrayList<Note> noteList = drawStaff.getCurrentStaff()
                .getNoteList(drawStaff.getCurrentBar(), drawStaff.getCurrentBeat());
        for(Note note : noteList){
            if(note.getTone() == noteToFind.getTone()) {
                userList.addUserCorrect(this.getContext());
                if(userList.findCurrent().getNumPointsNeeded()
                        == userList.findCurrent().getNumQuestionsCorrect()){
                    userList.addUserPointsNeeded(this.getContext());
                    userList.levelUpUser(this.getContext());
                }

                    location = new float[2];
                    location[0] = note.getX();
                    location[1] = note.getY();

                    drawStaff.incrementPointer();
                    if(drawStaff.getCurrentBeat() >= 16){

                        drawStaff = new DrawStaff(this.getContext());
                        drawStaff.lastClickX = 0;
                        drawStaff.lastClickY = 0;
                        staff.removeAllViews();
                        staff.addView(drawStaff);
                    }
                    return location;


            }
        }
        return location;
    }


}
