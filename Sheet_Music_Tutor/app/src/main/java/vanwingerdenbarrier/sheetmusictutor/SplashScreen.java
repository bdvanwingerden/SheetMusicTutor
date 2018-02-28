package vanwingerdenbarrier.sheetmusictutor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent menuIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(menuIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
