package vanwingerdenbarrier.sheetmusictutor.UserInfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import vanwingerdenbarrier.sheetmusictutor.Game.Question;

import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.ATTEMPTS;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.CORRECT;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.CURRENT_LEVEL;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.IS_CURRENT;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.KEY_ID;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.NAME;
import static vanwingerdenbarrier.sheetmusictutor.UserInfo.UserDB.NUM_POINTS_NEEDED;
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

    private int id;

    /**
     * public constructor that initializes the linked list and then reads in the stored info in
     * the csv
     * @param context
     */
    public UserList(Context context){
        userDB = new UserDB(context);
        id = 0;
        this.userLinkedList = new ArrayList<>();
        readUserList(context);

    }

    /**
     * adds a user to the app used to rebuild the app
     * @param ID the users ID
     * @param name the users name
     */
    public void addUser(int ID, String name, int numQuestionsAttempted, int numQuestionsCorrect,
                        int currentLevel, int numPointsNeeded, int isCurrent){
        userLinkedList.add(new User(ID, name, numQuestionsAttempted, numQuestionsCorrect,
                currentLevel, numPointsNeeded, isCurrent));
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
        cv.put(NUM_POINTS_NEEDED, u.getNumPointsNeeded());
        cv.put(IS_CURRENT, u.isCurrent());

        db.insert(USER_TABLE, null, cv);
        db.close();
    }

    /**
     * removes a user from the app using their ID as the unique Identifier & also the users location
     * in the linked list
     * @param toRemove the user to remove
     */
    public void removeUser(User toRemove){
        userLinkedList.remove(toRemove.getID());
        SQLiteDatabase db = userDB.getWritableDatabase();
        db.delete(USER_TABLE, KEY_ID + "=" + toRemove.getID(), null);
        db.close();
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
                        c.getInt(c.getColumnIndex(UserDB.IS_CURRENT))));
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

        userLinkedList.remove(user.getID());
        userLinkedList.add(user.getID(), new User(user.getID(),user.getName(),
                user.getNumQuestionsAttempted()+1,
                user.getNumQuestionsCorrect(),
                user.getCurrentLevel(),user.getNumPointsNeeded(), 1));
        updateUser(user);
    }

    public void addUserCorrect(){
        User user = findCurrent();

        userLinkedList.remove(user.getID());
        userLinkedList.add(user.getID(), new User(user.getID(),user.getName(),
                user.getNumQuestionsAttempted(),
                user.getNumQuestionsCorrect()+1,
                user.getCurrentLevel(), user.getNumPointsNeeded(), 1));

        System.out.println("ADDING "  + (user.getNumQuestionsCorrect()+1));

        updateUser(user);
    }

    /**
     * Adding 8 since 8 correct answers between levels
     */
    public void addUserPointsNeeded(){
        User user = findCurrent();

        userLinkedList.remove(user.getID());
        userLinkedList.add(user.getID(), new User(user.getID(),user.getName(),
                user.getNumQuestionsAttempted(),
                user.getNumQuestionsCorrect(),
                user.getCurrentLevel(), user.getNumPointsNeeded()+8, 1));

        updateUser(user);
    }

    public void levelUpUser(){
        User user = findCurrent();
        userLinkedList.remove(user.getID());
        userLinkedList.add(user.getID(), new User(user.getID(),user.getName(),
                user.getNumQuestionsAttempted(),
                user.getNumQuestionsCorrect(),
                user.getCurrentLevel()+1, user.getNumPointsNeeded(), 1));

        updateUser(user);
    }

    public void updateUser(User user){
        removeUser(user);
        addUser(user);
    }

}
