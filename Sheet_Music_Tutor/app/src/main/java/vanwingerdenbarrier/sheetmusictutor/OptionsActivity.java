package vanwingerdenbarrier.sheetmusictutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserMenu;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public void userMenu(View v){
        Intent userMenu = new Intent(this, UserMenu.class);
        this.startActivity(userMenu);
    }


}
