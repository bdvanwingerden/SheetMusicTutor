package vanwingerdenbarrier.sheetmusictutor.UserInfo;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vanwingerdenbarrier.sheetmusictutor.R;


/**
 * Android Activity that builds a user menu then facilitates the addition and removal of users
 *
 * @author Bronson VanWingerden
 * @author Dorian Barrier
 */
public class    UserMenu extends AppCompatActivity implements View.OnClickListener {
    /**
     * contains the current list of users
     */
    private UserList users;

    /**
     * Will contain the fragment to prompt the user to enter their name
     */
    DialogFragment createUserDialog;

    /**Will contain the fragment that prompts the user to select a starting difficulty*/
    DialogFragment setUserDifficultyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        users = new UserList(this);
        createButtons();
        if(users.findCurrent() == null){
            createUserDialog = new CreateUserDialog();
            createUserDialog.show(getFragmentManager(), "createUserDialog");
        }
    }

    /**
     * Creates a Button for all current users in the users list and then creates the Add & remove
     * Buttons & corresponding dialogs to confirm deletion of a user
     */
    public void createButtons() {
        final ViewGroup linearLayout = (ViewGroup) findViewById(R.id.UserListLayout);
        //
        for (User u : users.getUserList()) {
            Button tempButton = new Button(this);
            tempButton.setText(u.getName());
            tempButton.setId(u.getID());
            tempButton.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT));
            tempButton.setOnClickListener(this);
            if (u.isCurrent()) {
                tempButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.DARKEN);
            }
            linearLayout.addView(tempButton);
        }

        Button addUser = new Button(this);
        addUser.setText("Add User");
        addUser.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.DARKEN);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUserDialog = new CreateUserDialog();
                createUserDialog.show(getFragmentManager(), "createUserDialog");
            }
        });

        ViewGroup linearLayout2 = (ViewGroup) findViewById(R.id.AddRem);

        addUser.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout2.addView(addUser);

        Button remUser = new Button(this);
        remUser.setText("Remove User");
        remUser.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.DARKEN);
        remUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final User tempUser = users.findCurrent();
                AlertDialog alertDialog;

                if (tempUser == null) {
                    alertDialog = new AlertDialog.Builder(UserMenu.this).create();
                    alertDialog.setTitle("No User Selected");
                    alertDialog.setMessage("Please select a user to remove");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    alertDialog = new AlertDialog.Builder(UserMenu.this).create();
                    alertDialog.setTitle("Delete?");
                    alertDialog.setMessage("Are you sure you want to delete " + tempUser.getName());
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {
                                    dialogInterface.dismiss();
                                }
                            });

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Continue",
                            new DialogInterface.OnClickListener() {
                                User u = tempUser;

                                public void onClick(DialogInterface dialog, int i) {
                                    users.removeUser(getApplicationContext(), u);
                                    recreate();
                                }
                            });
                    alertDialog.show();
                }
            }
        });

        remUser.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout2.addView(remUser);
    }

    /**
     * called when CreateUserDialogs accept button is pressed this creates a new user then adds them
     * to screen then calls recreate to redraw the list of users
     * @param view
     * @param name
     */
    public void onAcceptDialog(View view, String name) {
        System.out.println("NAME IS ADDED IS -------> " + name);
        if(users.findCurrent() != null){
            users.findCurrent().swapCurrent();
        }
        User user = new User(users.getUserList().size(), name, true);
        users.addUser(user, this);
        createUserDialog.dismiss();

        setUserDifficultyDialog = new SetUserDifficulty();
        setUserDifficultyDialog.show(getFragmentManager(), "setUserDifficultyDialog");

        recreate();
        //finish();
    }

    /**
     *
     * Sets the users difficulty
     *
     * @param view
     * @param dif - if 1 set to medium difficulty. 2 set to hard difficulty
     */
    public void onSelectDiffDialog(View view, int dif){

        if(dif == 1){
            for(int x = 0; x < 8; x++) {
                users.addUserCorrect(this);
                users.addUserAttempt(this);
            }
            users.levelUpUser(this);
            users.addUserPointsNeeded(this);
        }
        else if(dif == 2){

            for(int x = 0; x < 16; x++) {
                users.addUserCorrect(this);
                users.addUserAttempt(this);
            }
            for(int y = 0; y < 2; y++){
                users.levelUpUser(this);
                users.addUserPointsNeeded(this);
            }//end for

        }//end else if

    }//end onSelectDiffDialog


    /**
     * onClick used for selecting the current user
     * @param view
     */
    public void onClick(View view) {
        Button bt = (Button) view;
        bt.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.DARKEN);
        User currentUser = users.findCurrent();
        if (currentUser != null) {
            currentUser.swapCurrent();
            Button currentButton = (Button) findViewById(currentUser.getID());
            currentButton.getBackground().clearColorFilter();
        }

        users.getUserList().get(view.getId()).swapCurrent();
        users.emptyUserList(getApplicationContext());
        users.writeUserList(getApplicationContext());
    }

    /**
     * debugging method that makes it easy to delete the db without altering code
     * @param v
     */
    public void killDB(View v){
        new UserList(this).emptyUserList(this);
        recreate();
    }
}
