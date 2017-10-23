package vanwingerdenbarrier.sheetmusictutor;
/**
 * Created by Dorian Barrier 9/28/17
 * Class that implements the keyboard view
 */
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;


public class Keyboard extends AppCompatActivity {

    /*Buttons that correspond with each sound*/
    Button a,b,c,d,e,f,g,cs,ds,fs,gs,as;


    /**Passes context for THIS keyboard class*/
    private Context mContext;

    /**Instance of KeySoundAPI*/
    KeySound2 keysound;

    /**
     * Finds the reference to the buttons
     * calls keysound2 to implement sound
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mContext = this;

        //Factory method that does this
        a = (Button) findViewById(R.id.a);
        b = (Button) findViewById(R.id.b);
        c = (Button) findViewById(R.id.c);
        d = (Button) findViewById(R.id.d);
        e = (Button) findViewById(R.id.e);
        f = (Button) findViewById(R.id.f);
        g = (Button) findViewById(R.id.g);
        cs = (Button) findViewById(R.id.cs);
        ds = (Button) findViewById(R.id.ds);
        as = (Button) findViewById(R.id.as);
        fs = (Button) findViewById(R.id.fs);
        gs = (Button) findViewById(R.id.gs);

        //create instance of KeySound2 API and pass buttons and context
        keysound = new KeySound2(a,b,c,d,e,f,g,cs,ds,fs,gs,as, mContext);
        keysound.createPool();

        //Call method that loads in files
    }//end onCreate

}//end class keyboard


