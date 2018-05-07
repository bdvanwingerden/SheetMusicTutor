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

    /* the switch corresponding to the show keyboard labels preference */
    Switch showKeys;
    /* the switch corresponding to the combo mode random or ordered preference */
    Switch comboPref;

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
                    list.toggleShowKey(true);
                }else{
                    list.toggleShowKey(false);
                }
            }
        });

        comboPref = (Switch)findViewById(R.id.comboPref);
        if(new UserList(getBaseContext()).findCurrent().getComboPref()){
            comboPref.setChecked(true);
        }else{
            comboPref.setChecked(false);
        }

        comboPref.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserList list = new UserList(getBaseContext());

                if(isChecked){
                    list.toggleComboPref(true);
                }else{
                    list.toggleComboPref(false);
                }
            }
        });
        
    }

    public void userMenu(View v) {
        Intent userMenu = new Intent(this, UserMenu.class);
        this.startActivity(userMenu);
    }
}
