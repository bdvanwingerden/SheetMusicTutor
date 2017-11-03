package vanwingerdenbarrier.sheetmusictutor.UserInfo;
import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

    public UserList(){
        this.userLinkedList = new LinkedList<>();

            //this means the UserDB is empty
            //so we add a new user
        userLinkedList.add(new User(userLinkedList.size(),"Bill", 2));
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

    public void addUser(User u){
        userLinkedList.add(u);
    }

    /**
     * removes a user from the app using the ID as a unique identifier
     * @param ID corresponding to the user to remove
     */
    public void removeUser(int ID){
        userLinkedList.remove(ID);
    }

    public LinkedList<User> getUserList(){
        return userLinkedList;
    }

    /**TODO figure out this things purpose */
    public void updateDB() {

    }
}
