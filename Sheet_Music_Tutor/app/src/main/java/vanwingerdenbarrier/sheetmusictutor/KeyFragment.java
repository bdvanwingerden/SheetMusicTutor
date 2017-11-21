package vanwingerdenbarrier.sheetmusictutor;
/**
 * Created by Dorian Barrier 9/28/17
 * Class that implements the keyboard view
 */
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vanwingerdenbarrier.sheetmusictutor.Key.KeySound2;


public class KeyFragment extends Fragment {

    /*Buttons that correspond with each sound*/
    Button a,b,c,d,e,f,g,cs,ds,fs,gs,as,a2,b2,c2,d2,e2,f2,g2;

    /**Instance of KeySoundAPI*/
    KeySound2 keysound;

    /**
     * Finds the reference to the buttons
     * calls keysound2 to implement sound
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.keyboard_fragment, container, false);
    }//end onCreate

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        View v = getView();

        //Factory method that does this
        a = (Button) v.findViewById(R.id.a);
        b = (Button) v.findViewById(R.id.b);
        c = (Button) v.findViewById(R.id.c);
        d = (Button) v.findViewById(R.id.d);
        e = (Button) v.findViewById(R.id.e);
        f = (Button) v.findViewById(R.id.f);
        g = (Button) v.findViewById(R.id.g);
        cs = (Button) v.findViewById(R.id.cs);
        ds = (Button) v.findViewById(R.id.ds);
        as = (Button) v.findViewById(R.id.as);
        fs = (Button) v.findViewById(R.id.fs);
        gs = (Button) v.findViewById(R.id.gs);
        a2 = (Button) v.findViewById(R.id.a2);
        b2 = (Button) v.findViewById(R.id.b2);
        c2 = (Button) v.findViewById(R.id.c2);
        d2 = (Button) v.findViewById(R.id.d2);
        e2 = (Button) v.findViewById(R.id.e2);
        f2 = (Button) v.findViewById(R.id.f2);
        g2 = (Button) v.findViewById(R.id.g2);

        //create instance of KeySound2 API and pass buttons and context
        keysound = new KeySound2(a,b,c,d,e,f,g,cs,ds,fs,gs,as,a2,b2,c2,d2,e2,f2,g2,getActivity());
        keysound.createPool();

    }

}//end class keyboard


