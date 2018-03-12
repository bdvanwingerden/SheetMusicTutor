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

    /**
     * Creates intent that goes to Achievments activity
     * @param v
     */
    public void userAchievements(View v){
        Intent userAchievments = new Intent(this,AchievementsListView.class);
        this.startActivity(userAchievments);
    }//end


}
