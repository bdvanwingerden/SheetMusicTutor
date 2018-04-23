package vanwingerdenbarrier.sheetmusictutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import vanwingerdenbarrier.sheetmusictutor.UserInfo.User;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserMenu;

public class OptionsActivity extends AppCompatActivity {

    Switch showKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_options);
    }

    @Override
    protected void onStart(){
        super.onStart();
        showKeys = (Switch)findViewById(R.id.keyLabels);
        if(new UserList(getBaseContext()).findCurrent().isShowing_key()){
            showKeys.setChecked(true);
        }else{
            showKeys.setChecked(false);
        }
        showKeys.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserList list = new UserList(getBaseContext());

                if(isChecked){
                    System.out.println("A TURNING KEYS ON");
                    list.toggleShowKey(true);
                }else{
                    System.out.println("A TURNING KEYS OFF");
                    list.toggleShowKey(false);
                }
            }
        });
    }

    public void userMenu(View v) {
        Intent userMenu = new Intent(this, UserMenu.class);
        this.startActivity(userMenu);
    }
}
