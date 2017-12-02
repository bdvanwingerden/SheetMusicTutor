package vanwingerdenbarrier.sheetmusictutor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Staff;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.User;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;

/**
 * @author Bronson VanWingerden
 * the game activity screen to display the Staff Fragment and the Key Fragment
 */
public class GameActivity extends FragmentActivity
        implements QuestionDisplay.Display, AnswerDisplay.Display{

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment currentQuestion;
    Fragment currentAnswer;

    /**
     * Observery method hopefully?
     */
    public void questionPressed(Note note) {

    }

    public void answerPressed(Note note, MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            new UserList(this.getApplicationContext()).addUserCorrect(this.getApplicationContext());
        }
        if(currentQuestion instanceof StaffFragment){
            ((StaffFragment) currentQuestion)
                    .colorNoteOnStaff(((StaffFragment) currentQuestion)
                            .getNoteAtCurrentLocation(note),event);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();

        addQuestion(new StaffFragment());
        addAnswer(new KeyFragment());
        setContentView(R.layout.activity_game);
    }

    public void addQuestion(Fragment fragment){
        currentQuestion = fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.question_holder, fragment);
        fragmentTransaction.commit();
    }

    public void addAnswer(Fragment fragment){
        currentAnswer = fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.answer_holder, fragment);
        fragmentTransaction.commit();
    }
}
