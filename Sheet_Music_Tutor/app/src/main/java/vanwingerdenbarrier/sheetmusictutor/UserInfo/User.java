package vanwingerdenbarrier.sheetmusictutor.UserInfo;

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
     * the users selected difficulty
     */
    private int selectedDifficulty;

    /**
     * the percentage corresponding to the number of correct note guesses vs total questions
     */
    private int noteAccuracy;

    /**
     * the percent of the time notes were hit on time
     */
    private int timingAccuracy;

    /**
     * the percent of the time the duration of the note was held correctly
     */
    private int durationAccuracy;

    /**
     * the users current level
     */
    private int currentLevel;

    /**
     * 0 if the user is not the current user & 1 if the user is a current user
     */
    private boolean isCurrent;

    /**
     * default constructor for a new user
     * @param name
     * @param ID
     * @param selectedDifficulty
     */
    public User(int ID, String name, int selectedDifficulty) {
        this.name = name;
        this.ID = ID;
        this.selectedDifficulty = selectedDifficulty;
        this.noteAccuracy = STARTING_ACCURACY;
        this.timingAccuracy = STARTING_ACCURACY;
        this.durationAccuracy = STARTING_ACCURACY;
        this.currentLevel = STARTING_LEVEL;
        isCurrent = false;
    }

    /**
     * constructor for re-adding current userLinkedList
     * @param ID the users ID
     * @param name the users name
     * @param selectedDifficulty the users selected difficulty
     * @param noteAccuracy the users current noteAccuracy
     * @param timingAccuracy the users current timingAccuracy
     * @param durationAccuracy the users current durationAccuracy
     * @param currentLevel the users current Level
     * @param isCurrent the boolean corresponding to whether or not the user is the current user
     */
    public User(int ID, String name, int selectedDifficulty, int noteAccuracy,
                int timingAccuracy, int durationAccuracy, int currentLevel, boolean isCurrent) {
        this.ID = ID;
        this.name = name;
        this.selectedDifficulty = selectedDifficulty;
        this.noteAccuracy = noteAccuracy;
        this.timingAccuracy = timingAccuracy;
        this.durationAccuracy = durationAccuracy;
        this.currentLevel = currentLevel;
        this.isCurrent = isCurrent;
    }

    /**
     * simplified constructor
     * @param ID users ID
     * @param name users name
     * @param selectedDifficulty the selected users difficulty
     * @param isCurrent whether or not the user is the current user
     */
    public User(int ID, String name, int selectedDifficulty, boolean isCurrent){
        this.ID = ID;
        this.name = name;
        this.selectedDifficulty = selectedDifficulty;
        this.isCurrent = isCurrent;
        this.noteAccuracy = STARTING_ACCURACY;
        this.timingAccuracy = STARTING_ACCURACY;
        this.durationAccuracy = STARTING_ACCURACY;
        this.currentLevel = STARTING_LEVEL;

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
     * returns the users selected difficulty
     * @return
     */
    public int getSelectedDifficulty() {
        return selectedDifficulty;
    }

    /**
     * sets the users selected difficulty
     * @param selectedDifficulty
     */
    public void setSelectedDifficulty(int selectedDifficulty) {
        this.selectedDifficulty = selectedDifficulty;
    }

    /**
     * gets the users current note accuracy
     * @return noteAccuracy
     */
    public int getNoteAccuracy() {
        return noteAccuracy;
    }

    /**
     * used to update the users current accuracy should only be used by quiz methods
     * @param noteAccuracy
     */
    public void setNoteAccuracy(int noteAccuracy) {
        this.noteAccuracy = noteAccuracy;
    }

    /**
     * gets the users current timing accuracy
     * @return timingAccuracy
     */
    public int getTimingAccuracy() {
        return timingAccuracy;
    }

    /**
     * used to update the users current accuracy should only be used by quiz methods
     * @param timingAccuracy
     */
    public void setTimingAccuracy(int timingAccuracy) {
        this.timingAccuracy = timingAccuracy;
    }

    /**
     * gets the users current duration accuracy
     * @return durationAccuracy
     */
    public int getDurationAccuracy() {
        return durationAccuracy;
    }

    /**
     * used to update the users current accuracy should only be used by quiz methods
     * @param durationAccuracy
     */
    public void setDurationAccuracy(int durationAccuracy) {
        this.durationAccuracy = durationAccuracy;
    }

    /**
     * returns the current users ID
     * @return
     */
    public int getID() {
        return ID;
    }

    /**
     * returns true if this user is the currently selected user
     * @return
     */
    public boolean isCurrent(){
        return isCurrent;
    }

    /**
     * swaps the users current status to the opposite of what it was
     */
    public void swapCurrent(){
        isCurrent = !isCurrent;
    }

    /**
     * prints the current users info in a CSV friendly format
     * @return
     */
    public String toCSV(){
        return (ID + "," + name + "," + selectedDifficulty + "," + noteAccuracy + ","
                + timingAccuracy + "," + durationAccuracy + "," + currentLevel + ","
                + isCurrent + ",");
    }
}


