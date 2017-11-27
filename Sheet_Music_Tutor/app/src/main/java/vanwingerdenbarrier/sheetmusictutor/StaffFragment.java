package vanwingerdenbarrier.sheetmusictutor;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import vanwingerdenbarrier.sheetmusictutor.Drawing.DrawStaff;

public class StaffFragment extends Fragment implements QuestionDisplay {

    DrawStaff drawStaff;
    Display callback;
    ViewGroup staff;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity();

         staff = (ViewGroup) inflater.inflate(R.layout.fragment_staff,
                container, false);
         staff.setWillNotDraw(false);

         drawStaff = new DrawStaff(this.getContext());

        staff.addView(new DrawStaff(this.getContext()));

        staff.setOnTouchListener(new  View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    drawStaff.reDraw(true, event.getX(), event.getY());
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    // sets both lastClickX & Y back to default
                    drawStaff.reDraw(false, 0,0);
                }

                staff.removeAllViews();
                staff.addView(drawStaff);
                System.out.println("fragment ontouch");
                return true;
            }
        });

        return staff;
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);

        try {
            callback = (Display) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

}
