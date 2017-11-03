package vanwingerdenbarrier.sheetmusictutor;

/**
 * Created by Doriangh4 on 10/20/2017.
 */

public class QuestionLibrary {

    /*Array contains every question. Index Corresponds to the question number-1*/
    private String questions [] = {
            "This acronym will help you remember the notes along the LINES in the TREBLE clef",
            "This acronym will help you remember the notes along the SPACES in the TREBLE clef",
            "The term ________ denotes when to play each note",
            "The term ________ denotes which notes to play."
    };
    /*2d array containing the choices a question may have
    * The first index corresponds to the question
    * Second index corresponds to the answer chosen by the user*/
    private String choices [][] = {
            {"Every Good Boy Deserves Food","Eddie Ate Dynamite Goodbye Eddie","FACE"},
            {"Every Good Boy Deserves Food","Eddie Ate Dynamite Goodbye Eddie","FACE"},
            {"Pitch","Rhythm","Time Signature"},
            {"Pitch","Rhythm","Time Signature"}
    };

    /*Contains an array of answers. Array index corresponds to the question number-1*/
    private String correctAnswers[] = {"Every Good Boy Deserves Food","FACE","Rhythm","Pitch"};

    /**
     * Retrieve a specific question
     * @param a question to get
     * @return the question specified
     */
    public String getQuestions(int a){
        String question = questions[a];
        return question;
    }//end getQuestions

    /**
     * Retrieve a specific choice
     * @param a the question to get
     * @return choice specified
     */
    public String getChoice1(int a){
        String choice0 = choices[a][0];
        return choice0;
    }//end getChoice1

    /**
     * Retrieve a specific choice
     * @param a the question to get
     * @return choice specified
     */
    public String getChoice2(int a){
        String choice1 = choices[a][1];
        return choice1;
    }//endGetChoice2

    /**
     * Retrieve a specific choice
     * @param a the question to get
     * @return choice specified
     */
    public String getChoice3(int a){
        String choice2 = choices[a][2];
        return choice2;
    }//end getChoice3

    /**
     * get the correct answer for the question specified
     * @param a the question to get answer from
     * @return the correct answer
     */
    public String getCorrectAnswer(int a){
        String answer = correctAnswers[a];
        return answer;
    }//end getCorrectAnswer

}//end QuestionLibrary

