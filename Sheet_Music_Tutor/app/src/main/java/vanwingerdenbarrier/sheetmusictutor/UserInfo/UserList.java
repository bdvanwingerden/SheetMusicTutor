package vanwingerdenbarrier.sheetmusictutor.UserInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;

import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.ATTEMPTS;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.CORRECT;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.CURRENT_LEVEL;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.DEFENSE_LEVEL;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.HERO_LEVEL;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.IS_CURRENT;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.KEY_ID;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.NAME;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.NUM_POINTS_NEEDED;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.QUIZ_LEVEL;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.USER_TABLE;

/**
 * Contains and creates a list of users
 */

public class UserList {

    /**
     * linked list containing all users
     */
    private ArrayList<User> userLinkedList;

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
     * adds a user to the app used to rebuild the app
     * @param ID the users ID
     * @param name the users name
     */
    public void addUser(int ID, String name, int numQuestionsAttempted, int numQuestionsCorrect,
                        int currentLevel,int numPointsNeeded, int isCurrent, int hero_level,
                        int defense_level, int quiz_level){
        userLinkedList.add(new User(ID, name, numQuestionsAttempted, numQuestionsCorrect,
                currentLevel, numPointsNeeded, isCurrent, hero_level, defense_level, quiz_level));
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
                userLinkedList.add(new User(c.getInt(c.getColumnIndex(UserDB.KEY_ID)),
                        c.getString(c.getColumnIndex(UserDB.NAME)),
                        c.getInt(c.getColumnIndex(UserDB.ATTEMPTS)),
                        c.getInt(c.getColumnIndex(UserDB.CORRECT)),
                        c.getInt(c.getColumnIndex(UserDB.CURRENT_LEVEL)),
                        c.getInt(c.getColumnIndex(UserDB.NUM_POINTS_NEEDED)),
                        c.getInt(c.getColumnIndex(UserDB.IS_CURRENT)),
                        c.getInt(c.getColumnIndex(UserDB.HERO_LEVEL)),
                        c.getInt(c.getColumnIndex(UserDB.DEFENSE_LEVEL)),
                        c.getInt(c.getColumnIndex(UserDB.QUIZ_LEVEL))));
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

    public void addUserAttempt(){
        User user = findCurrent();
        int newValue = user.getNumQuestionsAttempted()+1;
        user.setNumQuestionsAttempted(newValue);
        updateUser(user, ATTEMPTS, newValue);
    }

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
        int newValue = user.getNumPointsNeeded()+8;
        user.setNumPointsNeeded(newValue);
        updateUser(user, NUM_POINTS_NEEDED, newValue);
    }

    public void levelUpUser(){
        User user = findCurrent();
        int newValue = user.getCurrentLevel()+1;
        user.setCurrentLevel(newValue);
        updateUser(user, CURRENT_LEVEL, newValue);
    }

    public void updateUser(User user, String field, int newValue){
        SQLiteDatabase db = userDB.getWritableDatabase();
        db.execSQL("UPDATE " + USER_TABLE +
                " SET " + field + "="+ newValue +
                " WHERE id=" + user.getID());
        db.close();
    }

    public void emptyUserList() {
        SQLiteDatabase db = userDB.getWritableDatabase();
        db.execSQL("DELETE FROM " + USER_TABLE);
        db.close();
    }

}
