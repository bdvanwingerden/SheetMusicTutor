package vanwingerdenbarrier.sheetmusictutor;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.User;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;

/**
 * Created by Doriangh4 on 3/11/2018.
 */
public class AchievementsListView extends AppCompatActivity{

    /**Index of the points object in the Achievement array*/
    private final int POINTSINDEX = 0;

    /**Number of correct answers needed to obtain baby achievement*/
    private final int BABY = 1;

    /**Number of correct answers needed to obtain baby achievement*/
    private final int BALANCE = 4;

    /**Number of correct answers needed to obtain rookie no more and blind ninja achievement*/
    private final int ROOKIE_NINJA = 8;

    /**Number of correct answers needed to obtain hard rocker achievement*/
    private final int ROCKER = 16;

    /**Number of correct answers needed to obtain note-meister achievement*/
    private final int MEISTER = 5;

    /**Percentage to set progress bar to when all criteria has been met*/
    private final int ACHIEVED = 100;

    /**Current user*/
    User current;

    /**Number of questions a users has attempted. Used for achievement calculations*/
    int attempted;

    /**Total number of points a user has scored*/
    int totalPoints;

    /**Number of achievements the user has obtained*/
    int achievementCount;

    /**List of achievement objects parsed from xml*/
    ArrayList<Achievement> achievements;

    int[] IMAGES = {R.drawable.baby,R.drawable.balance,R.drawable.rookie,R.drawable.ninja,
            R.drawable.rocker,R.drawable.notem};

    String[] NAMES = {"BABY STEPS 0/1","FINDING BALANCE 0/4","ROOKIE NO MORE 0/8","HARD ROCKER 0/16",
            "BLIND NINJA 0/8","NOTE-MEISTER 0/5"};

    String[] DESCRIPTIONS = {"BABY","BALANCE","ROOKIE","ROCKER","NINJA","NOTE"};

    int[] PROGRESS = {0,0,0,0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements_list);

        ListView listView = (ListView) findViewById(R.id.ListView1);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

        current = new UserList(getBaseContext()).findCurrent();


        achievementCount = 0;//save this in database later, rather than resetting every time
        attempted = current.getNumQuestionsAttempted();
        totalPoints = current.getNumQuestionsCorrect();

        parseXML();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //final TextView mTextView = (TextView)view;

                AlertDialog alertDialog = new AlertDialog.Builder(AchievementsListView.this).create();

                switch (position) {
                    case 0:
                        alertDialog.setTitle("Achievement Description (Baby Steps)");
                        alertDialog.setMessage("\"One Baby Step at a Time\" \n Get one question " +
                                "correct to get obtain this achievement");
                        break;
                    case 1:
                        alertDialog.setTitle("Achievement Description (Finding Balance)");
                        alertDialog.setMessage("Finish your first quiz to obtain this achievement");
                        break;
                    case 2:
                        alertDialog.setTitle("Achievement Description (Rookie No More)");
                        alertDialog.setMessage("Obtain this achievement when you reach level 2");
                        break;
                    case 3:
                        alertDialog.setTitle("Achievement Description (Hard Rocker)");
                        alertDialog.setMessage("Obtain this achievement when you reach level 3");
                        break;
                    case 4:
                        alertDialog.setTitle("Achievement Description (Blind Ninja)");
                        alertDialog.setMessage("Get at least 8 questions correct without incorrect answers to get obtain this achievement");
                        break;
                    case 5:
                        alertDialog.setTitle("Achievement Description (Note-Meister)");
                        alertDialog.setMessage("The achievement you obtain when you collect every other achievement");
                        break;
                    case 6:
                        break;
                    default:
                        // Nothing do!
                }
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                dialogInterface.dismiss();
                            }
                        });

                alertDialog.show();

            }
        });//end onItemClick

    }//end onCreate

    private void parseXML(){
        XmlPullParserFactory parserFactory;

        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open("achievement_data.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            processParsing(parser);

        }
        catch(XmlPullParserException e){

        } catch (IOException e) {
            e.printStackTrace();
        }
    }//end parseXML()

    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException{

        achievements = new ArrayList<>();
        int eventType = parser.getEventType();
        Achievement currentAchievement = null;

        while(eventType != XmlPullParser.END_DOCUMENT){
            String eltName = null;

            switch(eventType){
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if ("achievement".equals(eltName)) {//if null
                        currentAchievement = new Achievement();
                        achievements.add(currentAchievement);
                    }else if(currentAchievement != null) {
                        if ("name".equals(eltName)) {
                            currentAchievement.name = parser.nextText();
                        } else if ("points".equals(eltName)) {
                            currentAchievement.points = parser.nextText();
                        }
                    }
                    break;
            }

            eventType = parser.next();
        }

        printAchievements(achievements);

    }//end processParsing()

    private void printAchievements(ArrayList<Achievement> achievements){
        StringBuilder builder = new StringBuilder();

        for(Achievement achievement: achievements){
            builder.append(achievement.name).append("\n").append(achievement.points);
        }

        Log.i("mytag",achievements.get(0).name);
    }//end printAchievements()

    class CustomAdapter extends BaseAdapter {

        ProgressBar progressBar;
        TextView textView_name;

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.acivity_cutsom_list_view,null);

            ImageView imageView = (ImageView) view.findViewById(R.id.imageView3);
            textView_name = (TextView) view.findViewById(R.id.textView_name);
            //TextView textView_description = (TextView) view.findViewById(R.id.textView_description);

            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.achievement_progress);
            Drawable drawableGreen = res.getDrawable(R.drawable.achievement_drawable_green);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBarA);
            progressBar.setProgress(PROGRESS[i]);
            progressBar.setMax(100);
            progressBar.setProgressDrawable(drawable);

            imageView.setImageResource(IMAGES[i]);
            textView_name.setText(NAMES[i]);
            //textView_description.setText(DESCRIPTIONS[i]);
            //When to call each method at a given position
            if(i == 0)
                babySteps(drawableGreen);
            else if(i == 1)
                findingBalance(drawableGreen);
            else if(i == 2)
                rookieNoMore(drawableGreen);
            else if(i == 3)
                blindNinja(drawableGreen);
            else if(i == 4)
                hardRocker(drawableGreen);
            else if(i == 5)
                noteMeister(drawableGreen);

            return view;
        }

        /**
         * Will change the button to reflect the user having reached the goal needed to
         * Obtain the baby achievement
         */
        public void babySteps(Drawable drawable){
            int baby = Integer.parseInt(achievements.get(0).points);
            if(current.getNumQuestionsCorrect() >= baby) {
                progressBar.setProgress(ACHIEVED);
                textView_name.setText("Baby Steps 1/1");
                progressBar.setProgressDrawable(drawable);
                achievementCount++;
            }
        }//end baby()

        /**
         * Will change the button to reflect the user having reached the goal need to
         * Obtain the findingBalance achievement
         */
        public void findingBalance(Drawable drawable){

            int balance = Integer.parseInt(achievements.get(1).points);

            float percentage = ( (float) current.getNumQuestionsCorrect()/ (float) balance)*100;
            progressBar.setProgress((int) percentage);

            if(current.getNumQuestionsCorrect() >= balance) {
                //progressBar.setProgress(ACHIEVED);
                textView_name.setText("FINDING BALANCE 4/4");
                progressBar.setProgressDrawable(drawable);
                achievementCount++;
            }
            else{
                textView_name.setText("FINDING BALANCE "+attempted+"/4");
            }
        }//end findingBalance()

        /**
         * Will change the button to reflect the user having reached the goal need to
         * Obtain the findingBalance achievement
         *
         * User has attempted at least 8 questions without getting a question wrong
         * Could add blind ninja 2 where he has to answer at least 16(Lvl 3) questions without misses
         *
         */
        public void rookieNoMore(Drawable drawable){

            int rookie = Integer.parseInt(achievements.get(2).points);

            float percentage = ( (float) current.getNumQuestionsCorrect()/ (float) rookie)*100;
            progressBar.setProgress((int) percentage);

            if(current.getNumQuestionsCorrect() == current.getNumQuestionsAttempted() &&
                    current.getNumQuestionsAttempted() >= rookie) {

                textView_name.setText("ROOKIE NO MORE 8/8");
                progressBar.setProgressDrawable(drawable);
                achievementCount++;
            }
            else{
                textView_name.setText("ROOKIE NO MORE "+attempted+"/8");
            }
        }//end rookieNoMore()

        /**
         * Will change the button to reflect the user having reached the goal need to
         * Obtain the blind ninja achievement
         */
        public void blindNinja(Drawable drawable){
            //!!!!Will Change to Reflect Missed Questions!!!!\\
            int ninja = Integer.parseInt(achievements.get(3).points);

            float percentage = ( (float) current.getNumQuestionsCorrect()/ (float) ninja)*100;
            progressBar.setProgress((int) percentage);

            if(current.getNumQuestionsCorrect() >= ninja) {
                textView_name.setText("BLIND NINJA 8/8");
                progressBar.setProgressDrawable(drawable);
                achievementCount++;
            }
            else{
                textView_name.setText("BLIND NINJA "+attempted+"/8");
            }
        }//end blindNinja()

        /**
         * Will change the button to reflect the user having reached the goal need to
         * Obtain the hard rocker achievement
         */
        public void hardRocker(Drawable drawable){

            int rocker = Integer.parseInt(achievements.get(4).points);

            float percentage = ( (float) current.getNumQuestionsCorrect()/ (float) rocker)*100;
            progressBar.setProgress((int) percentage);

            if(current.getNumQuestionsCorrect() >= rocker) {
                textView_name.setText("HARD ROCKER 16/16");
                progressBar.setProgressDrawable(drawable);
                achievementCount++;
            }
            else{
                textView_name.setText("HARD ROCKER "+attempted+"/16");
            }
        }//end hardRocker()

        /**
         * Will change the button to reflect the user having reached the goal need to
         * Obtain the note-meister achievement
         */
        public void noteMeister(Drawable drawable){

            int note = Integer.parseInt(achievements.get(5).points);

            float percentage = ( (float) achievementCount/ (float) note)*100;
            progressBar.setProgress((int) percentage);

            if(achievementCount == note) {
                textView_name.setText("NOTE-MEISTER 5/5");
                progressBar.setProgressDrawable(drawable);
                achievementCount++;
            }
            else{
                textView_name.setText("NOTE-MEISTER "+achievementCount+"/5");
            }
        }//end noteMeister()

    }//end class CustomAdapter

}
