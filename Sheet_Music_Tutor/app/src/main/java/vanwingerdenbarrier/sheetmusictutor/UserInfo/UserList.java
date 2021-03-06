package vanwingerdenbarrier.sheetmusictutor.UserInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;

import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.ATTEMPTS;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.COMBO_PREF;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.CORRECT;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.CURRENT_LEVEL;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.DEFENSE_LEVEL;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.HERO_LEVEL;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.IS_CURRENT;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.KEY_ID;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.NAME;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.NUM_POINTS_NEEDED;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.QUIZ_LEVEL;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.SHOW_KEY;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.USER_TABLE;

/**
 * Contains and creates a list of users
 */

public class UserList {

    /**
     * linked list containing all users
     */
    public ArrayList<User> userLinkedList;

    private SQLiteOpenHelper userDB;

    /**
     * public constructor that initializes the linked list and then reads in the stored info in
     * the csv
     * @param context
     */
    public UserList(Context context){
        userDB = new UserDB(context);
        this.userLinkedList = new ArrayList<>();
        //emptyUserList();
        readUserList(context);
    }

    /**
     * simplified add user that takes a premade user and adds it to the app
     * @param u the user to add
     */
    public void addUser(User u){
        userLinkedList.add(u);

        SQLiteDatabase db = userDB.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID, u.getID());
        cv.put(NAME, u.getName());
        cv.put(ATTEMPTS, u.getNumQuestionsAttempted());
        cv.put(CORRECT, u.getNumQuestionsCorrect());
        cv.put(CURRENT_LEVEL, u.getCurrentLevel());
        cv.put(IS_CURRENT, u.isCurrent());
        cv.put(HERO_LEVEL, u.getHero_level());
        cv.put(DEFENSE_LEVEL, u.getDefense_level());
        cv.put(QUIZ_LEVEL, u.getQuiz_level());
        cv.put(NUM_POINTS_NEEDED, u.getNumPointsNeeded());
        cv.put(COMBO_PREF, u.comboPrefToInt());
        cv.put(SHOW_KEY, u.showKeyToInt());

        db.insert(USER_TABLE, null, cv);
        db.close();
        cv.clear();
    }

    /**
     * removes a user from the app using their ID as the unique Identifier & also the users location
     * in the linked list
     * @param toRemove the user to remove
     */
    public void removeUser(User toRemove){
        userLinkedList.remove(toRemove);
        emptyUserList();
        int i = 0;
        ArrayList<User> temp = (ArrayList<User>) userLinkedList.clone();
        for(User u : temp){
            u.setId(i);
            addUser(u);
            i++;
        }
    }


    /**
     * returns the current list of users, useful for creating buttons in the UserMenu
     * @return
     */
    public ArrayList<User> getUserList(){
        return userLinkedList;
    }

    /**
     * Reads in the currently saved information in the userData "CSV" and then adds it into a new
     * linked list to ensure the most current user info is stored
     * @param context current app context
     */
    public void readUserList(Context context){
        SQLiteDatabase db = userDB.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + UserDB.USER_TABLE;
        Cursor c = db.rawQuery(selectQuery, null);

        // Looping through all records and adding to list
        if(c.moveToFirst()){
            do{
                userLinkedList.add(new User(
                        c.getInt(c.getColumnIndex(UserDB.KEY_ID)),
                        c.getString(c.getColumnIndex(UserDB.NAME)),
                        c.getInt(c.getColumnIndex(UserDB.ATTEMPTS)),
                        c.getInt(c.getColumnIndex(UserDB.CORRECT)),
                        c.getInt(c.getColumnIndex(UserDB.CURRENT_LEVEL)),
                        c.getInt(c.getColumnIndex(UserDB.NUM_POINTS_NEEDED)),
                        c.getInt(c.getColumnIndex(UserDB.IS_CURRENT)),
                        c.getInt(c.getColumnIndex(UserDB.HERO_LEVEL)),
                        c.getInt(c.getColumnIndex(UserDB.DEFENSE_LEVEL)),
                        c.getInt(c.getColumnIndex(UserDB.QUIZ_LEVEL)),
                        c.getInt(c.getColumnIndex(UserDB.COMBO_PREF)),
                        c.getInt(c.getColumnIndex(UserDB.SHOW_KEY))
                ));

            }while (c.moveToNext());
        }

        c.close();
        db.close();
    }

    /**
     * finds the current current user
     * @return
     */
    public User findCurrent(){
        User tempUser = null;
        for(User u : userLinkedList){
            if(u.isCurrent()){
                tempUser = u;
            }
        }

        return tempUser;
    }

    /**
     * adds a user attempt to the DB and list
     */
    public void addUserAttempt(){
        User user = findCurrent();
        int newValue = user.getNumQuestionsAttempted()+1;
        user.setNumQuestionsAttempted(newValue);
        updateUser(user, ATTEMPTS, newValue);
    }

    /**
     * increments the number of correct answers
     */
    public void addUserCorrect(){
        User user = findCurrent();
        int newValue = user.getNumQuestionsCorrect()+1;
        user.setNumQuestionsCorrect(newValue);
        updateUser(user, CORRECT, newValue);
    }

    /**
     * Adding 8 since 8 correct answers between levels
     */
    public void addUserPointsNeeded(){
        User user = findCurrent();
        int levelIncrement = 8 + (2*user.getCurrentLevel());
        int newValue = user.getNumPointsNeeded()+levelIncrement;
        user.setNumPointsNeeded(newValue);

        while(newValue < user.getNumQuestionsCorrect()){
            newValue+= 8 + (2*user.getCurrentLevel());
            levelUpUser();
        }

        updateUser(user, NUM_POINTS_NEEDED, newValue);
    }

    /**
     * levels the user up
     */
    public void levelUpUser(){
        User user = findCurrent();
        int newValue = user.getCurrentLevel()+1;
        user.setCurrentLevel(newValue);
        updateUser(user, CURRENT_LEVEL, newValue);
    }

    /**
     * toggles show keyboard labels
     * @param show true shows key and false hides key labels
     */
    public void toggleShowKey(boolean show){
        User user = findCurrent();
        int newValue = 0;
        if(show != user.isShowing_key()){
            if(show){
                newValue = 1;
            }
            user.setShow_key(show);
            updateUser(user, SHOW_KEY, newValue);
        }
    }

    /**
     * toggles the combo mode cycle
     * @param show true randomizes games that appear and false plays them in order
     */
    public void toggleComboPref(boolean show){
        User user = findCurrent();
        int newValue = 0;
        if(show != user.getComboPref()){
            if(show){
                newValue = 1;
            }
            user.setCombo_pref(show);
            updateUser(user, COMBO_PREF, newValue);
        }
    }

    /**
     * updates a given field of a user to a give value in the DB
     * @param user the user to update
     * @param field the field to update
     * @param newValue the value to update the field to
     */
    public void updateUser(User user, String field, int newValue){
        SQLiteDatabase db = userDB.getWritableDatabase();
        db.execSQL("UPDATE " + USER_TABLE +
                " SET " + field + "="+ newValue +
                " WHERE id=" + user.getID());
        db.close();
    }

    /**
     * completely empties the user list
     */
    public void emptyUserList() {
        SQLiteDatabase db = userDB.getWritableDatabase();
        db.execSQL("DELETE FROM " + USER_TABLE);
        db.close();
    }

}
