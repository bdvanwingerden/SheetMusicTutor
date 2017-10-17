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

    LinkedList<User> userLinkedList;
    User currentUser;
    Scanner scanIn;
    File userDB;

    public UserList(){
        this.userLinkedList = new LinkedList<>();

        //updateDB();
    }

    public LinkedList<User> getUserList(){
        /**
         * returning a clone of the userlist so it cannont be modified.
         */
        return (LinkedList<User>) userLinkedList.clone();
    }

    public void addUser(int ID, String name, int selectedDifficulty){
        userLinkedList.add(new User(ID,name,selectedDifficulty));
        //updateDB();
    }

    public void removeUser(int ID){
        userLinkedList.remove(ID);
        //updateDB();
    }

    public void updateDB() {
        BufferedWriter bw = null;
        FileWriter fw = null;
        String userInfo = "";

        for (User user : userLinkedList) {
            userInfo += (user.toSting() + "\n");
        }

        try {

            fw = new FileWriter(userDB);
            bw = new BufferedWriter(fw);
            bw.write(userInfo);

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();
            }

        }
    }
}
