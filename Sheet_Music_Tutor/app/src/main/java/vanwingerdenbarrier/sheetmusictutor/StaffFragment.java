package vanwingerdenbarrier.sheetmusictutor;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import vanwingerdenbarrier.sheetmusictutor.Drawing.DrawStaff;

public class StaffFragment extends Fragment implements View.OnClickListener {

    DrawStaff drawStaff;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity();

        /**
         * sets the fragments view
         */
        drawStaff = new DrawStaff(this.getContext());
        drawStaff.setOnClickListener(this);

        return drawStaff;
    }

    @Override
    public void onClick(View v){
        v.invalidate();
        drawStaff.changeColor();
    }


}
