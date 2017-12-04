package vanwingerdenbarrier.sheetmusictutor.UserInfo;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Contains and creates a list of users
 */

public class UserList {

    /**
     * linked list containing all users
     */
    private ArrayList<User> userLinkedList;

    /**
     * public constructor that initializes the linked list and then reads in the stored info in
     * the csv
     * @param context
     */
    public UserList(Context context){
        this.userLinkedList = new ArrayList<>();
        readUserList(context);
        //emptyUserList(context);
    }

    /**
     * adds a user to the app used to rebuild the app
     * @param ID the users ID
     * @param name the users name
     */
    public void addUser(int ID, String name, int numQuestionsAttempted, int numQuestionsCorrect,
                        int currentLevel, int numPointsNeeded, boolean isCurrent){
        userLinkedList.add(new User(ID, name, numQuestionsAttempted, numQuestionsCorrect,
                currentLevel, numPointsNeeded, isCurrent));
    }

    /**
     * simplified add user that takes a premade user and adds it to the app
     * @param u the user to add
     * @param context current app context
     */
    public void addUser(User u, Context context){
        userLinkedList.add(u);
        writeUserList(context);
    }

    /**
     * removes a user from the app using their ID as the unique Identifier & also the users location
     * in the linked list
     * @param context current app context
     * @param toRemove the user to remove
     */
    public void removeUser(Context context, User toRemove){
        userLinkedList.remove(toRemove.getID());
        emptyUserList(context);
        writeUserList(context);
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
    public void readUserList(Context context) {
        String userDataFileName = "userData";
        String userDataFilePath = context.getFilesDir() + "/" + userDataFileName;
        FileInputStream fileInputStream = null;

        try{
            fileInputStream = context.openFileInput(userDataFileName);
        }catch (FileNotFoundException e){
            File userData = new File(userDataFilePath);

            try{
                userData.createNewFile();
                fileInputStream = context.openFileInput(userDataFileName);
            } catch (Exception e1) {
                System.out.println("The userData file could not be created\n" + e1.getMessage());
                System.exit(1);
            }
        }

        Scanner scanFile = new Scanner(fileInputStream);
        scanFile.useDelimiter(",");

        while(scanFile.hasNext()){
            addUser(scanFile.nextInt(), scanFile.next(), scanFile.nextInt(), scanFile.nextInt()
                    , scanFile.nextInt(), scanFile.nextInt(), scanFile.nextBoolean());
        }

        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * deletes and creates a new userData file
     * @param context
     */
    public void emptyUserList(Context context){
        String userDataFileName = "userData";
        String userDataFilePath = context.getFilesDir() + "/" + userDataFileName;

        File newUserData = new File (userDataFilePath);
        newUserData.delete();
    }

    /**
     * writes all the data currently stored in the userLinkedList to the userData CSV
     * @param context
     */
    public void writeUserList(Context context){
        String userDataFileName = "userData";
        String userDataFilePath = context.getFilesDir() + "/" + userDataFileName;
        FileOutputStream fileOutputStream = null;

        try{
            fileOutputStream = context.openFileOutput(userDataFileName, Context.MODE_PRIVATE);
        }catch (FileNotFoundException e){
            File userData = new File(userDataFilePath);
            try{
                userData.createNewFile();
                fileOutputStream = context.openFileOutput(userDataFileName
                        , Context.MODE_PRIVATE);
            } catch (Exception e1) {
                System.out.println("The userData file could not be created\n" + e1.getMessage());
                System.exit(1);
            }
        }

        int newID = 0;

        for(User u : userLinkedList){
            u.setId(newID);
            String tempString = u.toCSV();
            newID++;
            try {
                fileOutputStream.write(tempString.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public void addUserAttempt(Context context){
        User user = findCurrent();

        userLinkedList.remove(user.getID());
        userLinkedList.add(user.getID(), new User(user.getID(),user.getName(),
                user.getNumQuestionsAttempted()+1,
                user.getNumQuestionsCorrect(),
                user.getCurrentLevel(),user.getNumPointsNeeded(), true));

        writeUserList(context);
    }

    public void addUserCorrect(Context context){
        User user = findCurrent();

        userLinkedList.remove(user.getID());
        userLinkedList.add(user.getID(), new User(user.getID(),user.getName(),
                user.getNumQuestionsAttempted(),
                user.getNumQuestionsCorrect()+1,
                user.getCurrentLevel(), user.getNumPointsNeeded(), true));

        writeUserList(context);
    }

<<<<<<< HEAD
    /**
     * Adding 8 since 8 correct answers between levels
     * @param context
     */
    public void addUserPointsNeeded(Context context){
        User user = findCurrent();

        userLinkedList.remove(user.getID());
        userLinkedList.add(user.getID(), new User(user.getID(),user.getName(),
                user.getNumQuestionsAttempted(),
                user.getNumQuestionsCorrect(),
                user.getCurrentLevel(), user.getNumPointsNeeded()+8,true));

        writeUserList(context);
    }

=======
    public void levelUpUser(Context context){
        User user = findCurrent();
        userLinkedList.remove(user.getID());
        userLinkedList.add(user.getID(), new User(user.getID(),user.getName(),
                user.getNumQuestionsAttempted(),
                user.getNumQuestionsCorrect(),
                user.getCurrentLevel()+1, user.getNumPointsNeeded(),true));

        writeUserList(context);
    }

}
