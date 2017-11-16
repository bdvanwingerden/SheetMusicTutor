package vanwingerdenbarrier.sheetmusictutor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vanwingerdenbarrier.sheetmusictutor.Drawing.DrawStaff;

public class StaffFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity();

        ViewGroup staff = (ViewGroup) inflater.inflate(R.layout.fragment_staff, container, false);

        staff.addView(new DrawStaff(this.getContext()));

        return staff;
    }


}
