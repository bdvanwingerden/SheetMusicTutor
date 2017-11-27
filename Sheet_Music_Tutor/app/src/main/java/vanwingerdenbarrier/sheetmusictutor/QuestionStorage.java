package vanwingerdenbarrier.sheetmusictutor;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Doriangh4 on 11/6/2017.
 */
public class QuestionStorage {

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

    /**
     *
     * @param a - list index
     * @return questions based on list index
     */
    public String getCorrectAnswer(int a){
        return list.get(a).getAnswer();
    }//end getCorrectAnswer

    public String getDifficultyScore(int a){
        return list.get(a).getDifficulty();
    }//end getDifficultyScore


    public void initialQuestions(Context context){
        myDatabaseHelper = new DatabaseHelper(context);
        list = myDatabaseHelper.getAllQuestionsList();//get all questions/choices/answers from database

        if(list.isEmpty()){
            myDatabaseHelper.addFirstQuestion(new Question("This Acronym will help you",new String[]{"EVERY GOOD BOY DESERVES FOOD","EDDIE ATE DYNAMITE GOODBYE EDDIE","FACE"},"5","EVERY GOOD BOY DESERVES FOOD"));
            myDatabaseHelper.addFirstQuestion(new Question("2. What up",new String[]{"1","answer","3"},"3","answer"));
            myDatabaseHelper.addFirstQuestion(new Question("3. Sup",new String[]{"1","2","answer"},"2","answer"));
            myDatabaseHelper.addFirstQuestion(new Question("4. Dorian",new String[]{"answer","2","3"},"1","answer"));

            list = myDatabaseHelper.getAllQuestionsList();//get list from database again
        }
    }

}
