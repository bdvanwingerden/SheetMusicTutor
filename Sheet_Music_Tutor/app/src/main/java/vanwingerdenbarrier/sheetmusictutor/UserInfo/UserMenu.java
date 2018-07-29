package vanwingerdenbarrier.sheetmusictutor.UserInfo;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.LinkedList;

import vanwingerdenbarrier.sheetmusictutor.R;


/**
 * Android Activity that builds a user menu then facilitates the addition and removal of users
 * TODO FIX INDEXING
 * @author Bronson VanWingerden
 * @author Dorian Barrier
 */
public class UserMenu extends AppCompatActivity implements View.OnClickListener {
    /**
     * Will contain the fragment to prompt the user to enter their name
     */
    DialogFragment createUserDialog;

    /**
     * contains the current list of users
     */
    private UserList users;

    LinkedList<Button> buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        users = new UserList(this);

        buttonList = new LinkedList<>();

        createButtons();
        if(users.findCurrent() == null){
            createUserDialog = new CreateUserDialog();
            if (users.getUserList().size() == 0) {
                createUserDialog.show(getFragmentManager(), "createUserDialog");
            }
        }
    }

    /**
     * Creates a Button for all current users in the users list and then creates the Add & remove
     * Buttons & corresponding dialogs to confirm deletion of a user
     */
    public void createButtons() {
        final ViewGroup linearLayout = findViewById(R.id.UserListLayout);
        int i = 0;
        boolean currentSet = false;
        for (User u : users.getUserList()) {
            Button tempButton = new Button(this);
            tempButton.setText(u.getName());
            tempButton.setId(i);
            u.setId(i);
            users.updateUser(u, UserDB.KEY_ID, i);
            users.getUserList().get(i).setId(i);

            tempButton.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT));
            tempButton.setOnClickListener(this);

            if (u.isCurrent() && !currentSet) {
                currentSet = true;
                tempButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.DARKEN);
            } else if (u.isCurrent() && currentSet) {
                u.swapCurrent();
            }
            linearLayout.addView(tempButton);
            buttonList.add(i, tempButton);
            i++;
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

        ViewGroup linearLayout2 = findViewById(R.id.AddRem);

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
                                    users.removeUser(u);
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

        User user = new User(users.getUserList().size(), name, 1);
        users.addUser(user);
        createUserDialog.dismiss();

        createButtons();
        onClick(buttonList.getLast());
    }


    /**
     * onClick used for selecting the current user
     * @param view
     */
    public void onClick(View view) {
        Button bt = (Button) view;

        bt.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.DARKEN);
        User currentUser = users.findCurrent();

        if (currentUser != null) {
            currentUser.swapCurrent();//make not current anymore
            Button currentButton = findViewById(currentUser.getID());
            currentButton.getBackground().clearColorFilter();
            users.updateUser(currentUser, UserDB.IS_CURRENT, 0);
        }

        User newCurrent = users.getUserList().get(view.getId());
        newCurrent.swapCurrent();
        users.updateUser(newCurrent, UserDB.IS_CURRENT, 1);

        recreate();
    }

    public void goBack(){
        if(users.getUserList().size() == 1){
            finish();
        }
    }
}
