package vanwingerdenbarrier.sheetmusictutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.io.IOException;

/**
 * @author Bronson VanWingerden
 * the main menu activity
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * when the start button is pressed this method calls the the Game Activity
     * TODO add a game type selection screen before activity
     * @param v
     */
    public void startButton(View v) throws IOException {
        Intent game = new Intent(this, GameActivity.class);
        this.startActivity(game);
    }

    //TODO create options menu basic layout
    public void optionsButton(View v){}

    //TODO create stats menu basic layout
    public void statsButton(View v){}
}
