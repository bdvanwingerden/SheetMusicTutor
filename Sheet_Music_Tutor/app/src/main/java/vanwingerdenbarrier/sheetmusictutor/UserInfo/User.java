package vanwingerdenbarrier.sheetmusictutor.UserInfo;

import android.content.Context;

/**
 * Contains all the information for a single user
 *
 * @author Bronson VanWingerden
 * @author Dorian Barrier
 */
public class User {

    /**
     * the initial accuracy for a user to start at
     */
    private static int STARTING_ACCURACY = 50;

    /**
     * the default first level set to one for obvious reasons
     */
    private static int STARTING_LEVEL = 1;

    /**
     * the Users ID number to allow for multiple accounts with the same name
     */
    private int ID;

    /**
     * the users selected name
     */
    private String name;

    /**
     * number of questions attempted
     */
    private int numQuestionsAttempted;

    /**
     * number of correct answers
     */
    private int numQuestionsCorrect;

    /**
     * the users current level
     */
    private int currentLevel;

    /**
     * 0 if the user is not the current user & 1 if the user is a current user
     */
    private int isCurrent;

    /**
     *
     */

    private int numPointsNeeded;

    private int hero_level;
    private int defense_level;
    private int quiz_level;

    /**
     * default constructor for a new user
     * @param name
     * @param ID
     */
    public User(int ID, String name, int isCurrent) {
        this.name = name;
        this.ID = ID;
        this.numQuestionsAttempted = 0;
        this.numQuestionsCorrect = 0;
        this.numPointsNeeded = 8;
        this.currentLevel = STARTING_LEVEL;
        this.isCurrent = isCurrent;
        this.hero_level = 1;
        this.defense_level = 1;
        this.quiz_level = 1;
    }

    public int getHero_level() {
        return hero_level;
    }

    public void setHero_level(int hero_level) {
        this.hero_level = hero_level;
    }

    public int getDefense_level() {
        return defense_level;
    }

    public void setDefense_level(int defense_level) {
        this.defense_level = defense_level;
    }

    public int getQuiz_level() {
        return quiz_level;
    }

    public void setQuiz_level(int quiz_level) {
        this.quiz_level = quiz_level;
    }

    /**
     * constructor for re-adding current userLinkedList
     * @param ID the users ID
     * @param name the users name
     * @param currentLevel the users current Level
     * @param isCurrent the boolean corresponding to whether or not the user is the current user
     */
    public User(int ID, String name, int numQuestionsAttempted, int numQuestionsCorrect,
                int currentLevel,int numPointsNeeded, int isCurrent, int hero_level,
                int defense_level, int quiz_level) {
        this.ID = ID;
        this.name = name;
        this.numQuestionsAttempted = numQuestionsAttempted;
        this.numQuestionsCorrect = numQuestionsCorrect;
        this.currentLevel = currentLevel;
        this.numPointsNeeded = numPointsNeeded;
        this.isCurrent = isCurrent;
        this.hero_level = hero_level;
        this.defense_level = defense_level;
        this.quiz_level = quiz_level;
    }

    /**
     * getter that returns the users name
     * @return the users name
     */
    public String getName() {
        return name;
    }

    /**
     * setter that allows the user to change their name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the current users ID
     * @return
     */
    public int getID() {
        return ID;
    }

    public int getNumQuestionsAttempted() {
        return numQuestionsAttempted;
    }

    public int getNumQuestionsCorrect() {
        return numQuestionsCorrect;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setId(int newId){
        this.ID = newId;

    }

    /**
     * returns true if this user is the currently selected user
     * @return
     */
    public boolean isCurrent() {
        if (isCurrent == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setNumQuestionsAttempted(int numQuestionsAttempted) {
        this.numQuestionsAttempted = numQuestionsAttempted;
    }

    public void setNumQuestionsCorrect(int numQuestionsCorrect) {
        this.numQuestionsCorrect = numQuestionsCorrect;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setNumPointsNeeded(int numPointsNeeded) {
        this.numPointsNeeded = numPointsNeeded;
    }

    /**
     * swaps the users current status to the opposite of what it was
     */
    public void swapCurrent(){
        if(isCurrent == 0){
            isCurrent = 1;
        }else{
            isCurrent = 0;
        }
    }

    public int getNumPointsNeeded() {
        return numPointsNeeded;
    }

    /**
     * prints the current users info in a CSV friendly format
     * @return
     */
    public String toCSV(){
        return (ID + "," + name + "," + numQuestionsAttempted + "," + numQuestionsCorrect + ","
                + currentLevel + "," + numPointsNeeded + "," + isCurrent + ",");
    }
}


