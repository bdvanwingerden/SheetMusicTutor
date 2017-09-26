package vanwingerdenbarrier.sheetmusictutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startButton(View v){
        Intent game = new Intent(this, GameActivity.class);
        this.startActivity(game);
    }

    //TODO create options menu basic layout
    public void optionsButton(View v){}

    //TODO create stats menu basic layout
    public void statsButton(View v){}
}
