package vanwingerdenbarrier.sheetmusictutor;

/**
 * Created by Doriangh4 on 11/6/2017.
 */

public class Question {

    private String question;
    private String[] choice = new String[3];//4 for this particular type of question
    private String difficulty;
    private String answer;

    public Question(){

    }

    public Question(String question, String[] choices,String difficulty, String answer){
        this.question = question;
        this.choice[0] = choices[0];
        this.choice[1] = choices[1];
        this.choice[2] = choices[2];
        this.difficulty = difficulty;
        this.answer = answer;
    }

    public String getQuestion(){
        return question;
    }//end getQuestion

    public String getChoice(int i){
        return choice[i];
    }//end getChoice

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }//end setAnswer

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty){
        this.difficulty = difficulty;
    }

    public void setChoice(int i, String choice){
        this.choice[i] = choice;
    }//end setChoice

    public void setQuestion(String question){
        this.question = question;
    }//end setQuestions

}//end class Question
