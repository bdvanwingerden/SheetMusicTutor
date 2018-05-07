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
     * the number of points needed to progress to the next level
     */
    private int numPointsNeeded;

    /**
     * the users current note hero level
     */
    private int hero_level;

    /**
     * the users current note defense levevl
     */
    private int defense_level;

    /**
     * the users current quiz level
     */
    private int quiz_level;

    /**
     * the users current show key status
     */
    private boolean show_key;

    /**
     * the users current combo mode preference
     */
    private boolean combo_pref;

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
        this.show_key = true;
        this.combo_pref = true;
    }

    /**
     * returns the current note hero level
     * @return the current note hero level
     */
    public int getHero_level() {
        return hero_level;
    }

    /**
     * sets the current hero level
     * @param hero_level the level to set it to
     */
    public void setHero_level(int hero_level) {
        this.hero_level = hero_level;
    }

    /**
     * returns the current note defense level
     * @return the current note defense level
     */
    public int getDefense_level() {
        return defense_level;
    }

    /**
     * sets the note defense level
     * @param defense_level the level to set the note defense difficulty to
     */
    public void setDefense_level(int defense_level) {
        this.defense_level = defense_level;
    }

    /**
     * gets the current quiz level
     * @return the current quiz level
     */
    public int getQuiz_level() {
        return quiz_level;
    }

    /**
     * sets the current quiz level
     * @param quiz_level the level to set the quiz to
     */
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
                int defense_level, int quiz_level, int combo_pref, int show_key) {
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
        this.combo_pref = combo_pref != 0;
        this.show_key = show_key != 0;
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

    /**
     * returns the current number of questions attempted
     * @return the current number of questions attempted
     */
    public int getNumQuestionsAttempted() {
        return numQuestionsAttempted;
    }

    /**
     * returns the current number of questions correct
     * @return the current number of correctly answered questions
     */
    public int getNumQuestionsCorrect() {
        return numQuestionsCorrect;
    }

    /**
     * gets the users current level
     * @return the users current level
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * sets the user ID
     * @param newId the id to set the user id to
     */
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

    /**
     * sets the current number of questions attempted
     * @param numQuestionsAttempted the number of questions attempted
     */
    public void setNumQuestionsAttempted(int numQuestionsAttempted) {
        this.numQuestionsAttempted = numQuestionsAttempted;
    }

    /**
     * sets the current number of questions correct
     * @param numQuestionsCorrect the number of qustions correct
     */
    public void setNumQuestionsCorrect(int numQuestionsCorrect) {
        this.numQuestionsCorrect = numQuestionsCorrect;
    }

    /**
     * sets the users current level
     * @param currentLevel the level to set the current to
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * sets the current number of points needed to level up
     * @param numPointsNeeded the number to set  the numPoints needed to
     */
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

    /**
     * gets the number of points needed to level up
     * @return the number of points needed to level
     */
    public int getNumPointsNeeded() {
        return numPointsNeeded;
    }

    /**
     * returns the boolean value of show key
     * @return whether or not the user wants key labels on
     */
    public boolean isShowing_key() {
        return show_key;
    }

    /**
     * returns the show key value as an integer and allows us to more easily save the preference into
     * the database
     * @return
     */
    public int showKeyToInt(){
        if(show_key){
            return 1;
        }
        return 0;
    }

    /**
     * sets the show key preference
     * @param show_key the users preference to show or not show key
     */
    public void setShow_key(boolean show_key) {
        this.show_key = show_key;
    }


    /**
     * returns the combo preference as an int so it can be more easily stored in the database
     * @return the integer representation of the users preference
     */
    public int comboPrefToInt(){
        if(combo_pref){
            return 1;
        }
        return 0;
    }

    /**
     * sets the combo mode behavior preference
     * @param combo_pref sets the behavior to random if true and ordered if false
     */
    public void setCombo_pref(boolean combo_pref) {
        this.combo_pref = combo_pref;
    }

    /**
     * returns the combo mode behavior preference
     * @return the combo mode bahavior preference
     */
    public boolean getComboPref(){
        return combo_pref;
    }


}


