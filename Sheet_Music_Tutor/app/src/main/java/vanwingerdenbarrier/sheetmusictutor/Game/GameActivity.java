package vanwingerdenbarrier.sheetmusictutor.Game;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;

import java.util.Random;

import vanwingerdenbarrier.sheetmusictutor.Achievements.ResultsActivity;
import vanwingerdenbarrier.sheetmusictutor.Key.KeyFragment;
import vanwingerdenbarrier.sheetmusictutor.MainActivity;
import vanwingerdenbarrier.sheetmusictutor.NoteGames.GuessNote;
import vanwingerdenbarrier.sheetmusictutor.NoteGames.KnowYourKeyboardFragment;
import vanwingerdenbarrier.sheetmusictutor.NoteGames.NoteDefense;
import vanwingerdenbarrier.sheetmusictutor.NoteGames.NoteHero;
import vanwingerdenbarrier.sheetmusictutor.NoteGames.PlayAlongFragment;
import vanwingerdenbarrier.sheetmusictutor.NoteGames.SongSelectionFragment;
import vanwingerdenbarrier.sheetmusictutor.Quiz.QuizAnswerFragment;
import vanwingerdenbarrier.sheetmusictutor.Quiz.QuizQuestionFragment;
import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.Note;
import vanwingerdenbarrier.sheetmusictutor.StaffStructure.StaffFragment;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.User;
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

    int previousCorrect;
    int previousAttempts;

    /**
     * indicates the current game mode
     * 0 = quiz,
     * 1 = staff,
     * 2 = combo
     * etc
     */
    int mode;

    int currentGame;

    int currentLives;
    int currentScore;

    /**
     * rounds
     */

    int rounds;

    /**
     * Allows us to pass information between our fragments if object is null then the question is done
     * and the score and remaining lives are passed via score and lives if applicable
     */
    public void questionPressed(Object correct, int score, int lives) {

        if(correct == null){
            endQuestion(score, lives);
        }else if(currentAnswer instanceof QuizAnswerFragment){
            ((QuizAnswerFragment) currentAnswer).setQuestion((int)correct);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.answer_holder, currentAnswer);
            fragmentTransaction.commit();
        }else if(correct instanceof String){

        }

    }

    /**
     * Allows us to pass info between fragments
     * @param answer
     * @param event
     */
    public void answerPressed(Object answer, MotionEvent event) {
        if (currentQuestion instanceof StaffFragment && event != null) {
            ((StaffFragment) currentQuestion)
                    .colorNoteOnStaff(((StaffFragment) currentQuestion)
                            .getNoteAtCurrentLocation((Note) answer), event);
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                new UserList(this.getApplicationContext()).addUserAttempt();
            }
        } else if(currentQuestion instanceof QuizQuestionFragment){

            ((QuizQuestionFragment) currentQuestion).checkIfCorrect((String)answer);
        } else if (currentQuestion instanceof NoteDefense && event != null) {
            ((NoteDefense) currentQuestion).fireNote((Note) answer);
        } else if (currentQuestion instanceof NoteHero && event != null) {
            ((NoteHero) currentQuestion).playNote((Note) answer);
        } else if (currentQuestion instanceof GuessNote){

            ((GuessNote) currentQuestion).checkIfCorrect((String)answer);
            new UserList(this.getApplicationContext()).addUserAttempt();
        } else if (currentQuestion instanceof KnowYourKeyboardFragment && event != null) {
            ((KnowYourKeyboardFragment) currentQuestion).checkIfCorrect((Note) answer);
        }
        else if (currentQuestion instanceof PlayAlongFragment && event != null) {
            ((PlayAlongFragment) currentQuestion).checkIfCorrect((Note) answer);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);//set media volume control

        rounds = 5;

        int gameType = getIntent().getIntExtra("gameType", -1);
        int songType = getIntent().getIntExtra("songType", -1);

        mode = gameType;

        currentLives = 4;
        currentScore = 0;

         User temp = new UserList(getBaseContext()).findCurrent();
        previousCorrect =  temp.getNumQuestionsCorrect();
        previousAttempts = temp.getNumQuestionsAttempted();


        fragmentManager = getSupportFragmentManager();

        if (gameType == 1 || gameType == 2) {
            currentGame = 0;
            addQuestion(setFragmentArgs(new StaffFragment(), 0, currentLives, currentScore));
            replaceAnswer(setFragmentArgs(new KeyFragment(), 0, currentLives, currentScore));
        } else if (gameType == 3) {
            addQuestion(setFragmentArgs(new NoteDefense(), 0, currentLives, currentScore));
            replaceAnswer(setFragmentArgs(new KeyFragment(), 1, currentLives, currentScore));
        } else if (gameType == 4) {
            addQuestion(setFragmentArgs(new NoteHero(), 0, currentLives, currentScore));
            replaceAnswer(setFragmentArgs(new KeyFragment(), 0, currentLives, currentScore));
        } else if (gameType == 5) {
            rounds = 0;
            replaceQuestion(setFragmentArgs(new QuizQuestionFragment(), 0, currentLives, currentScore));
            replaceAnswer(new QuizAnswerFragment());
        } else if (gameType == 6) {
            rounds = 0;
            addQuestion(
                    setFragmentArgs(new KnowYourKeyboardFragment(), 0, currentLives, currentScore));
            replaceAnswer(setFragmentArgs(new KeyFragment(), 2, currentLives, currentScore));
        } else if (gameType == 7) {//song selection
            addQuestion(new SongSelectionFragment());
            replaceAnswer(setFragmentArgs(new KeyFragment(), 2, currentLives, currentScore));
        } else if (gameType == 8) {//sing along

            rounds = 0;
            PlayAlongFragment playFrag = new PlayAlongFragment();
            setPlayAlongArgs(playFrag, 0, currentLives, currentScore, songType);
            addQuestion(playFrag);
            replaceAnswer(setFragmentArgs(new KeyFragment(), 2, currentLives, currentScore));
        } else {
            System.out.println("GAMETYPE NOTFOUND" + gameType);
        }


        setContentView(R.layout.activity_game);
    }

    /**
     * adds the passed fragment to the current question holder
     * @param fragment the fragment to add
     */
    public void addQuestion(Fragment fragment) {
        currentQuestion = fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setRetainInstance(true);
        fragmentTransaction.add(R.id.question_holder, fragment);
        fragmentTransaction.commit();

    }

    /**
     * adds the passed fragment to the current answer holder
     * @param fragment the fragment to add
     */
    public void addAnswer(Fragment fragment) {
        currentAnswer = fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setRetainInstance(true);
        fragmentTransaction.add(R.id.answer_holder, fragment);
        fragmentTransaction.commit();
    }

    /**
     * ends the current question
     */
    public void endQuestion(int score, final int lives) {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        currentScore = score;
        currentLives = lives;

        if (mode == 5) {
            alertDialog.setTitle("Good Job!");
            alertDialog.setMessage("You got the correct answer!");
        } else if (lives > 0) {
            System.out.println("TEST " + lives);
            alertDialog.setTitle("Good Job!");
            alertDialog.setMessage("Current score:" + score + "!");

        }else{
            alertDialog.setTitle("Too Bad!");
            alertDialog.setMessage("You ran out of lives!!");
            alertDialog.setMessage("You scored:" + score + "!");

        }

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {

                        if (rounds <= 1 || lives <= 0) {
                            //finish();
                            sendResults();
                        } else {
                            makeNextQuestion();
                        }

                        dialogInterface.dismiss();
                    }
                });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    /**
     * Once quiz completes send results to results screen
     */
    public void sendResults(){

        if (mode != 5) {
            User current = new UserList(getBaseContext()).findCurrent();

            boolean isQuiz = true;

            float percentage = ((float) current.getNumQuestionsCorrect() - previousCorrect)
                    / ((float) current.getNumQuestionsAttempted() - previousAttempts) * 100;

            Intent stats = new Intent(this, ResultsActivity.class);

            stats.putExtra("percent", (int) percentage);//random number for now(Level progress)
            stats.putExtra("correct", current.getNumQuestionsCorrect() - previousCorrect);
            stats.putExtra("numQuestions", current.getNumQuestionsAttempted() - previousAttempts);
            stats.putExtra("score", current.getNumQuestionsCorrect());//random number for now(Score)
            stats.putExtra("points", current.getNumPointsNeeded());
            stats.putExtra("isQuiz", isQuiz);

            stats.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.startActivity(stats);
        } else {
            this.startActivity(new Intent(this, MainActivity.class));
        }
    }//end sendResults

    /**
     * replaces the question with a new question fragment
     * @param fragment
     */
    public void replaceQuestion(Fragment fragment){
        currentQuestion = fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setRetainInstance(true);
        fragmentTransaction.replace(R.id.question_holder, fragment);
        fragmentTransaction.commit();
    }

    /**
     * replaces the answer witha new question fragment
     * @param fragment
     */
    public void replaceAnswer(Fragment fragment){
        currentAnswer = fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setRetainInstance(true);
        fragmentTransaction.replace(R.id.answer_holder, fragment);
        fragmentTransaction.commit();
    }

    /**
     * creates the next question
     */
    public void makeNextQuestion(){
        if (mode == 1) {
            replaceQuestion(setFragmentArgs(new StaffFragment(), 0, currentLives, currentScore));
            rounds--;

        }else if (mode == 2){

            int next;

            if (!new UserList(getBaseContext()).findCurrent().getComboPref()) {
                Random rand = new Random();
                next = rand.nextInt(5);
                currentGame = next;
            }else{
                currentGame++;
                next = currentGame;
                if (currentGame > 4) {
                    currentGame = 0;
                    next = currentGame;
                }
            }

            if(next == 0){
                replaceQuestion(setFragmentArgs(new StaffFragment(), 0, currentLives, currentScore));
                replaceAnswer(setFragmentArgs(new KeyFragment(), 0, currentLives, currentScore));
            }else if(next == 1){
                replaceQuestion(setFragmentArgs(new QuizQuestionFragment(), 0, currentLives, currentScore));
                replaceAnswer(new QuizAnswerFragment());
            }else if(next == 2){
                replaceQuestion(setFragmentArgs(new NoteDefense(), 0, currentLives, currentScore));
                replaceAnswer(setFragmentArgs(new KeyFragment(), 1, currentLives,currentScore));
            }else if(next == 3){
                replaceQuestion(setFragmentArgs(new NoteHero(), 0, currentLives, currentScore));
                replaceAnswer(setFragmentArgs(new KeyFragment(), 0, currentLives, currentScore));
            }else if(next == 4){
                replaceQuestion(
                        setFragmentArgs(new KnowYourKeyboardFragment(), 1, currentLives, currentScore));

                replaceAnswer(setFragmentArgs(new KeyFragment(), 2, currentLives, currentScore));
            }else if(next == 5){
                replaceQuestion(setPlayAlongArgs(new PlayAlongFragment(),
                        1, currentLives, currentScore, new Random().nextInt(3)));
                replaceAnswer(setFragmentArgs(new KeyFragment(), 2, currentLives, currentScore));
            }

            rounds--;

        } else if (mode == 3) {
            replaceQuestion(setFragmentArgs(new NoteDefense(), 0, currentLives, currentScore));
            rounds--;
        } else if (mode == 4) {
            replaceQuestion(setFragmentArgs(new NoteHero(), 0, currentLives, currentScore));
            rounds--;
        }else{
            rounds = 0;
        }
    }

    /**
     * handles setting the mode argument for a given fragment
     * @param fragment the fragment to give args to
     * @param mode the argument to give to the fragment
     * @return the fragment with the arguments loaded
     */
    public Fragment setFragmentArgs(Fragment fragment, int mode, int lives, int score){
        Bundle args = new Bundle();
        args.putInt("mode", mode);
        args.putInt("lives", lives);
        args.putInt("score", score);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment setPlayAlongArgs(PlayAlongFragment fragment, int mode, int lives, int score, int song) {
        Bundle args = new Bundle();
        args.putInt("mode", mode);
        args.putInt("lives", lives);
        args.putInt("score", score);
        args.putInt("songType", song);
        fragment.setArguments(args);
        return fragment;
    }

}

