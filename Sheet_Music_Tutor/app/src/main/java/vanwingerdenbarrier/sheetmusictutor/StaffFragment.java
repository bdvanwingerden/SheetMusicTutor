package vanwingerdenbarrier.sheetmusictutor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StaffFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity();

        /**
         * sets the fragments view
         */
        return new DrawStaff(this.getContext());
    }


}
