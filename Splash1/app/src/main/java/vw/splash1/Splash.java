package vw.splash1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class Splash extends Activity {

    final int PAUSE = 5000;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    protected void onStart(){
        super.onStart();
        handler = new Handler();
    }

    public void intensifyEurobeat(View v){
        Intent menu = new Intent(this, MainActivity.class);
        this.startActivity(menu);
    }

    final Runnable runner = new Runnable() {
        @Override
        public void run() {
            gotoNextScreen();
        }
    };

    private void gotoNextScreen(){
        Intent menu = new Intent(this, MainActivity.class);
        this.startActivity(menu);
    }
}
