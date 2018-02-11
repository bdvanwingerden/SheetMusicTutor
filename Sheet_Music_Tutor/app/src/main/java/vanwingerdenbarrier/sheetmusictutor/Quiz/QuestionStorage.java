package vanwingerdenbarrier.sheetmusictutor.Quiz;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import vanwingerdenbarrier.sheetmusictutor.Game.Question;
import vanwingerdenbarrier.sheetmusictutor.Quiz.DatabaseHelper;

/**
 * Created by Doriangh4 on 11/6/2017.
 */
public class QuestionStorage {

    public QuestionStorage() {
    }

    //Declare list of Question objects
    List<Question> list = new ArrayList<>();
    DatabaseHelper myDatabaseHelper;

    /**
     * Returns number of questions in list
     * @return number of questions in list
     */
    public int getLength(){
        return list.size();
    }//end getLength

    public String getQuestion(int a){
        return list.get(a).getQuestion();
    }//end getQuestion

    /**
     *
     * Returns a single multiple choice item for question based on list index
     * @param index question number
     * @param num
     * @return single multiple choice item in the list - 1,2,3,or 4
     */
    public String getChoice(int index, int num){
        return list.get(index).getChoice(num-1);
    }//end get choice

    public String[] getChoices(int index){
        return list.get(index).getChoices();
    }

    /**
     *
     * @param a - list index
     * @return questions based on list index
     */
    public String getCorrectAnswer(int a){
        return list.get(a).getAnswer();
    }//end getCorrectAnswer

    /**
     * Gets the difficulty of teh question from the user.
     * @param a which question
     * @return
     */
    public String getDifficultyScore(int a){
        return list.get(a).getDifficulty();
    }//end getDifficultyScore


    /**
     * Writes the initial questions to the database. Only called once the user initially creates
     * the database. After this point database entries may be made to the file manually
     * @param context
     */
    public void initialQuestions(Context context){
        myDatabaseHelper = new DatabaseHelper(context);
        list = myDatabaseHelper.getAllQuestionsList();//get all questions/choices/answers from database

        if(list.isEmpty()){
            myDatabaseHelper.addFirstQuestion(new Question("This acronym will help you remember the notes along the LINES in the TREBLE clef",
                    new String[]{"EVERY GOOD BOY DESERVES FOOD","EDDIE ATE DYNAMITE GOODBYE EDDIE","FACE"},
                    "5","EVERY GOOD BOY DESERVES FOOD"));
            myDatabaseHelper.addFirstQuestion(new Question("This acronym will help you remember the notes along the SPACES in the TREBLE clef",
                    new String[]{"Every Good Boy Deserves Food","Eddie Ate Dynamite Goodbye Eddie","FACE"},
                    "3","FACE"));
            myDatabaseHelper.addFirstQuestion(new Question("The term ________ denotes when to play each note",
                    new String[]{"Pitch","Rhythm","Time Signature"},"2","Rhythm"));
            myDatabaseHelper.addFirstQuestion(new Question("The term ________ denotes which notes to play.",
                    new String[]{"Pitch","Rhythm","Time Signature"},"1","Pitch"));

            list = myDatabaseHelper.getAllQuestionsList();//get list from database again
        }
    }

}
