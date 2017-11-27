package vanwingerdenbarrier.sheetmusictutor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * @author Bronson VanWingerden
 * the game activity screen to display the Staff Fragment and the Key Fragment
 */
public class GameActivity extends FragmentActivity implements QuestionDisplay.Display{

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    /**
     * Observery method hopefully?
     */
    public void printMessage(String s) {
        System.out.println(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO NOTELISTENER INTERFACE CREATING & PASSING HERE
        //OBSERVER PATTERN WITH MULTIPLE OBSERVERS
        //LOOK AT JAVA OBSERVER
        fragmentManager = getSupportFragmentManager();
        addQuestion(new StaffFragment());
        addAnswer(new KeyFragment());
        setContentView(R.layout.activity_game);
    }

    public void addQuestion(Fragment fragment){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.question_holder, fragment);
        fragmentTransaction.commit();
    }

    public void addAnswer(Fragment fragment){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.answer_holder, fragment);
        fragmentTransaction.commit();
    }
}
