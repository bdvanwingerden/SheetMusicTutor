package vanwingerdenbarrier.sheetmusictutor.UserInfo;
import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Contains and creates a list of users
 */

public class UserList {

    /**
     * linked list containing all users
     */
    private LinkedList<User> userLinkedList;

    /**
     * the current user
     */
    User currentUser;

    public UserList(Context context){
        this.userLinkedList = new LinkedList<>();
        readUserList(context);
    }

    /**
     * adds a user to the app
     * @param ID the users ID
     * @param name the users name
     * @param selectedDifficulty the users selected difficulty
     */
    public void addUser(int ID, String name, int selectedDifficulty){
        userLinkedList.add(new User(ID,name,selectedDifficulty));
    }

    public void addUser(User u, Context context){
        userLinkedList.add(u);
        writeUserList(context);
    }

    /**
     * removes a user from the app using the ID as a unique identifier
     * @param ID corresponding to the user to remove
     *           //TODO implement proper functionality
     */
    public void removeUser(Context context){
        userLinkedList = new LinkedList<>();
        emptyUserList(context);
    }

    public LinkedList<User> getUserList(){
        return userLinkedList;
    }

    /**TODO figure out this things purpose */
    public void readUserList(Context context) {
        String userDataFileName = "userData.txt";
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

        while(scanFile.hasNext()){
            addUser(scanFile.nextInt(), scanFile.next(), scanFile.nextInt());
        }

        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void emptyUserList(Context context){
        String userDataFileName = "userData.txt";
        String userDataFilePath = context.getFilesDir() + "/" + userDataFileName;

        File newUserData = new File (userDataFilePath);
        newUserData.delete();

    }

    public void writeUserList(Context context){
        String userDataFileName = "userData.txt";
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

        for(User u : userLinkedList){
            String tempString = u.getID() + " " + u.getName() + " "  + u.getSelectedDifficulty()
                    + "\n";
            try {
                fileOutputStream.write(tempString.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
