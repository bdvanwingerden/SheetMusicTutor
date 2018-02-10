package vanwingerdenbarrier.sheetmusictutor.Game;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;

import vanwingerdenbarrier.sheetmusictutor.Key.KeyFragment;
import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.StaffFragment;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;

/**
 * @author Bronson VanWingerden
 * the game activity screen to display the Staff Fragment and the Key Fragment
 */
public class GameActivity extends FragmentActivity
        implements QuestionDisplay.Display, AnswerDisplay.Display {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment currentQuestion;
    Fragment currentAnswer;

    /**
     * indicates the current game mode
     * 0 = quiz,
     * 1 = staff,
     * 2 = combo
     */
    int mode;

    /**
     * rounds
     */

    int rounds;

    /**
     * Observery method hopefully?
     */
    public void questionPressed(Note note) {
        endQuestion();
    }

    public void answerPressed(Note note, MotionEvent event) {

        if (currentQuestion instanceof StaffFragment) {
            ((StaffFragment) currentQuestion)
                    .colorNoteOnStaff(((StaffFragment) currentQuestion)
                            .getNoteAtCurrentLocation(note), event);
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                new UserList(this.getApplicationContext()).addUserAttempt(this.getApplicationContext());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rounds = 10;

        int gameType = getIntent().getIntExtra("gameType", -1);

        if (gameType == 1) {
            System.out.println("AAA GAMETYPE = STAFF" + gameType);
        } else if (gameType == 2) {
            System.out.println("AAA GAMETYPE = COMBO" + gameType);
        } else {
            System.out.println("AAA GAMETYPE = NOTFOUND" + gameType);
        }

        mode = gameType;

        fragmentManager = getSupportFragmentManager();

        Fragment staffFrag = new StaffFragment();
        Fragment keyFrag = new KeyFragment();

        addQuestion(staffFrag);
        addAnswer(keyFrag);
        setContentView(R.layout.activity_game);
    }

    public void addQuestion(Fragment fragment) {
        currentQuestion = fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.question_holder, fragment);
        fragmentTransaction.commit();

    }

    public void addAnswer(Fragment fragment) {
        currentAnswer = fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.answer_holder, fragment);
        fragmentTransaction.commit();
    }

    public void endQuestion() {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Good Job!");
        alertDialog.setMessage("you scored xx!");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {

                        if(rounds  <= 1){
                            finish();
                        }else{
                            makeNextQuestion();
                        }

                        dialogInterface.dismiss();
                    }
                });

        alertDialog.show();
    }

    public void replaceQuestion(Fragment fragment){
        currentQuestion = fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.question_holder, fragment);
        fragmentTransaction.commit();
    }

    public void makeNextQuestion(){
        System.out.println(mode);
        if (mode == 1) {

            replaceQuestion(new StaffFragment());
            rounds--;

        }else if (mode == 2){


            rounds--;


        }else if(mode == 0){

        }
    }

}

