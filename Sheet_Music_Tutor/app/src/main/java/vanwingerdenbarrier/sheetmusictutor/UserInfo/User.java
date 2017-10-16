package vanwingerdenbarrier.sheetmusictutor.UserInfo;

/**
 * Contains all the information for a single user
 */

public class User {

    private static int STARTING_ACCURACY = 50;
    private static int STARTING_LEVEL = 1;

    private int ID;

    private String name;

    private int selectedDifficulty;

    private int noteAccuracy;

    private int timingAccuracy;

    private int durationAccuracy;

    private int currentLevel;

    /**
     * 0 if the user is not the current user & 1 if the user is a current user
     */
    private int isCurrent;

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
        isCurrent = 0;
    }

    /**
     * constructor for re-adding current userLinkedList
     * @param ID
     * @param name
     * @param selectedDifficulty
     * @param noteAccuracy
     * @param timingAccuracy
     * @param durationAccuracy
     * @param currentLevel
     * @param isCurrent
     */
    public User(int ID, String name, int selectedDifficulty, int noteAccuracy,
                int timingAccuracy, int durationAccuracy, int currentLevel, int isCurrent) {
        this.ID = ID;
        this.name = name;
        this.selectedDifficulty = selectedDifficulty;
        this.noteAccuracy = noteAccuracy;
        this.timingAccuracy = timingAccuracy;
        this.durationAccuracy = durationAccuracy;
        this.currentLevel = currentLevel;
        this.isCurrent = isCurrent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSelectedDifficulty() {
        return selectedDifficulty;
    }

    public void setSelectedDifficulty(int selectedDifficulty) {
        this.selectedDifficulty = selectedDifficulty;
    }

    public int getNoteAccuracy() {
        return noteAccuracy;
    }

    public void setNoteAccuracy(int noteAccuracy) {
        this.noteAccuracy = noteAccuracy;
    }

    public int getTimingAccuracy() {
        return timingAccuracy;
    }

    public void setTimingAccuracy(int timingAccuracy) {
        this.timingAccuracy = timingAccuracy;
    }

    public int getDurationAccuracy() {
        return durationAccuracy;
    }

    public void setDurationAccuracy(int durationAccuracy) {
        this.durationAccuracy = durationAccuracy;
    }

    public int getID() {
        return ID;
    }

    public int getIsCurrent(){
        return isCurrent;
    }

    public void setIsCurrent(int isCurrent){
        this.isCurrent = isCurrent;
    }

    public String toSting(){
        return (ID + " " + name + " " + selectedDifficulty + " " + noteAccuracy + " "
                + timingAccuracy + " " + durationAccuracy + " " + currentLevel + " "
                + isCurrent);
    }
}


