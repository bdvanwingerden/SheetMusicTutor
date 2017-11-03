package vanwingerdenbarrier.sheetmusictutor;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vanwingerdenbarrier.sheetmusictutor.UserInfo.User;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;

public class UserMenu extends AppCompatActivity {
    private UserList users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        users = new UserList();
        createButtons();
    }

    //TODO create makeButton method to minimize repeated code
    public void createButtons(){
        final ViewGroup linearLayout = (ViewGroup) findViewById(R.id.UserListLayout);

        for(User u : users.getUserList()){
            Button tempButton = new Button(this);
            tempButton.setText(u.getName());
            tempButton.setId(u.getID());
            tempButton.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
            , ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(tempButton);
        }

        Button addUser = new Button(this);
        addUser.setText("Add User");
        addUser.setBackgroundColor(Color.GREEN);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User tempUser = new User(users.getUserList().size(), "Ted", 2);
                users.addUser(tempUser);
                linearLayout.addView(addUserButton(tempUser));
            }
        });

        ViewGroup linearLayout2 = (ViewGroup) findViewById(R.id.AddRem);

        addUser.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout2.addView(addUser);

        Button remUser = new Button(this);
        remUser.setText("Remove User");
        remUser.setBackgroundColor(Color.RED);

        remUser.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout2.addView(remUser);
    }

    //TODO Implement this
    private Button addUserButton(User u){
        Button tempButton = new Button(this);
        tempButton.setText(u.getName());
        tempButton.setId(u.getID());
        tempButton.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));

        return tempButton;
    }


}
