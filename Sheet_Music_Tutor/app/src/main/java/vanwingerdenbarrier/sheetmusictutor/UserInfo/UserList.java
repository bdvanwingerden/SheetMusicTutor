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

    public UserList(Context context) throws IOException {
        this.userLinkedList = new LinkedList<User>();

        userDB = new File(context.getFilesDir(), "userDB.txt");
        userDB.createNewFile();

        try {
            scanIn = new Scanner(userDB);
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage() + " UserDB not found");
            System.exit(1);
        }

        if(scanIn.hasNext()){
            //this means the UserDB has information from a previous session
            while(scanIn.hasNext()){

                //adds fields in this order ID, name, selectedDifficulty, noteAccuracy,
                //timingAccuracy, durationAccuracy, currentLevel, isCurrent
                //NOTE a new ID is generated at runtime to ensure it corresponds to the index in the
                //linked list
                userLinkedList.add(new User(
                        userLinkedList.size(),//ID
                        scanIn.next(),     //name
                        scanIn.nextInt(),  //selectedDifficulty
                        scanIn.nextInt(),  //noteAccuracy
                        scanIn.nextInt(),  //timingAccuracy
                        scanIn.nextInt(),  //durationAccuracy
                        scanIn.nextInt(),  //currentLevel
                        scanIn.nextInt()));//isCurrent
            }
        }else{
            //this means the UserDB is empty
            //so we add a new user
            userLinkedList.add(new User(userLinkedList.size(),"new user", 2));
        }

        updateDB();
    }

    public void addUser(int ID, String name, int selectedDifficulty){
        userLinkedList.add(new User(ID,name,selectedDifficulty));
        updateDB();
    }

    public void removeUser(int ID){
        userLinkedList.remove(ID);
        updateDB();
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
