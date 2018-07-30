package vanwingerdenbarrier.sheetmusictutor.Achievements;

import android.content.DialogInterface;
import android.content.Intent;
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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import vanwingerdenbarrier.sheetmusictutor.MainActivity;
import vanwingerdenbarrier.sheetmusictutor.Quiz.QuizActivity;
import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.User;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;

/**
 * Class to handle writing results to results screen
 * Created by Doriangh4 on 11/29/2017.
 */
public class ResultsActivity extends AppCompatActivity{

    /**
     * Index of the points object in the Achievement array
     */
    private final int POINTSINDEX = 0;

    /**
     * Number of correct answers needed to obtain baby achievement
     */
    private final int BABY = 1;

    /**
     * Number of correct answers needed to obtain baby achievement
     */
    private final int BALANCE = 4;

    /**
     * Number of correct answers needed to obtain rookie no more and blind ninja achievement
     */
    private final int ROOKIE_NINJA = 8;

    /**
     * Number of correct answers needed to obtain hard rocker achievement
     */
    private final int ROCKER = 16;

    /**
     * Number of achievements needed to obtain note-meister achievement
     */
    private final int MEISTER = 5;

    /**
     * Percentage to set progress bar to when all criteria has been met
     */
    private final int ACHIEVED = 100;

    /**
     * Current user
     */
    User current;

    /**
     * Number of questions a users has attempted. Used for achievement calculations
     */
    int attempted;

    /**
     * Total number of points a user has scored
     */
    int totalPoints;

    /**
     * Number of achievements the user has obtained
     */
    int achievementCount;

    /**
     * List of achievement objects parsed from xml
     */
    ArrayList<Achievement> achievements;
    /**
     * Names of the image files being used in the achievements list view
     */
    int[] IMAGES = {R.drawable.baby, R.drawable.balance, R.drawable.rookie, R.drawable.ninja,
            R.drawable.rocker, R.drawable.notem};
    int lockIcon = R.drawable.lock;
    /**
     * Names of each particular achievement. Get From XML!!!!
     */
    String[] NAMES = {"Achievement 1. 0/1", "Achievement 2. 0/4",
            "Achievement 3. 0/8", "Achievement 4. 0/16",
            "Achievement 5. 0/8", "Achievement 6. 0/5"};
    /**
     * Starting progress for each achievement
     */
    int[] PROGRESS = {0, 0, 0, 0, 0, 0};
    private boolean[] achieved = {false, false, false, false, false, false};


    /**Attached to XML to display title*/
    private TextView titleView;

    /**Attached to XML to display number of correct answers*/
    private TextView correctView;

    /**Attached to XML to display users accuracy*/
    private TextView percentView;

    /**Attached to XML to display number of points possible*/
    private TextView pointsView;

    /**Attached to XML to display correct quote*/
    private TextView quoteView;

    /**Attached to XML to update progress bar according to percentage*/
    private ProgressBar prg;

    /**Percent of questions right*/
    private int percentage;

    /**Total number of questions*/
    private int numQuestions;

    /**Total number of questions user got correct*/
    private int correct;

    /**Score or number of points user has collected*/
    private int score;

    /**Max number of points a user may receive during quiz*/
    private int pointsPossible;

    /**if true return quiz results; False return users stats*/
    private boolean isQuiz;

    /**Quote to critique user's results*/
    private String quote;

    /**
     * Acquires the fields passed from the class that called results
     * calls the method that actualy writes the results to the screen
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_results);

        current = new UserList(getBaseContext()).findCurrent();

        titleView = (TextView) findViewById(R.id.title);
        correctView = (TextView) findViewById(R.id.correct);
        percentView = (TextView) findViewById(R.id.percent);
        pointsView = (TextView) findViewById(R.id.points);
        quoteView = (TextView) findViewById(R.id.quote);
        //prg = (ProgressBar) findViewById(R.id.ProgressBar);


        if( getIntent().getExtras() != null){
            percentage = getIntent().getIntExtra("percent",0);
            numQuestions = getIntent().getIntExtra("numQuestions",0);
            correct = getIntent().getIntExtra("correct",0);
            score = getIntent().getIntExtra("score", 0);
            pointsPossible = getIntent().getIntExtra("points", 0);
            isQuiz = getIntent().getBooleanExtra("isQuiz", true);
        }

        writeResults(isQuiz);

        ListView listView = (ListView) findViewById(R.id.AcheivementList);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

        achievementCount = 0;//save this in database later, rather than resetting every time
        attempted = current.getNumQuestionsAttempted();
        totalPoints = current.getNumQuestionsCorrect();

        parseXML();//step 1 of parsing

        //OnItemClickListener to invoke alert dialog for each item in listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                AlertDialog alertDialog = new AlertDialog.Builder(ResultsActivity.this).create();

                //Take these strings from the XML in future versions
                switch (position) {
                    case 0:
                        if (achieved[0] == true) {
                            alertDialog.setTitle("Achievement Description (Baby Steps)");
                            alertDialog.setMessage("\"One Baby Step at a Time\" \n Get one question " +
                                    "correct to get obtain this achievement");
                        } else
                            genericDialog(alertDialog);
                        break;
                    case 1:
                        if (achieved[1] == true) {
                            alertDialog.setTitle("Achievement Description (Finding Balance)");
                            alertDialog.setMessage("Finish your first quiz to obtain this achievement");
                        } else
                            genericDialog(alertDialog);
                        break;
                    case 2:
                        if (achieved[2] == true) {
                            alertDialog.setTitle("Achievement Description (Rookie No More)");
                            alertDialog.setMessage("Obtain this achievement when you reach level 2");
                        } else
                            genericDialog(alertDialog);
                        break;
                    case 3:
                        if (achieved[3] == true) {
                            alertDialog.setTitle("Achievement Description (Hard Rocker)");
                            alertDialog.setMessage("Obtain this achievement when you reach level 3");
                        } else
                            genericDialog(alertDialog);
                        break;
                    case 4:
                        if (achieved[4] == true) {
                            alertDialog.setTitle("Achievement Description (Blind Ninja)");
                            alertDialog.setMessage("Get at least 8 questions correct without incorrect answers to get obtain this achievement");
                        } else
                            genericDialog(alertDialog);
                        break;
                    case 5:
                        if (achieved[5] == true) {
                            alertDialog.setTitle("Achievement Description (Note-Meister)");
                            alertDialog.setMessage("The achievement you obtain when you collect every other achievement");
                        } else
                            genericDialog(alertDialog);
                        break;
                    case 6:
                        break;
                    default:
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

    }


    /**
     * basic dialog for when an achievement hasnt been met yet
     */
    private void genericDialog(AlertDialog alertDialog) {

        alertDialog.setTitle("Hold You're Horses!");
        alertDialog.setMessage("The achievement will be revealed when you meet the criteria");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        dialogInterface.dismiss();
                    }
                });

        alertDialog.setCancelable(false);

        alertDialog.show();

    }//end genericDialog()

    /**
     * Obtains the xml file that will be parsed
     */
    private void parseXML() {

        XmlPullParserFactory parserFactory;

        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open("achievement_data.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            processParsing(parser);//step 1 of parsing

        } catch (XmlPullParserException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }//end parseXML()

    /**
     * Process the XML file
     *
     * @param parser
     * @throws IOException
     * @throws XmlPullParserException
     */
    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException {

        //Initialize the array that will hold the objects created from the parsed achievemement_data.xml
        achievements = new ArrayList<>();

        int eventType = parser.getEventType();
        Achievement currentAchievement = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if ("achievement".equals(eltName)) {//if null
                        currentAchievement = new Achievement();
                        achievements.add(currentAchievement);
                    } else if (currentAchievement != null) {
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

    }//end processParsing()


    /**
     * Restarts quiz when pressed
     * @param v
     */
    public void doneButton(View v) {
        Intent home = new Intent(this, MainActivity.class);
        this.startActivity(home);
        finish();
    }

    /**
     * Returns a specific quote based on your score
     * @param numPercent users score
     * @return
     */
    public String quoteResult(int numPercent){

        if(numPercent >= 90)
            quote = "Great Job! You're a Master!";
        else if(numPercent >= 80)
            quote = "Good, but you can do better!";
        else if(numPercent >= 70)
            quote = "Not bad...but a little more practice wont hurt";
        else if(numPercent >= 60)
            quote = "You should definitely give the quiz another try!";
        else
            quote = "Try going over the lesson again.";

        return quote;
    }

    /**
     * Determine if returning quiz screen or user stats screen
     * if true return quiz results
     * if false return user stats
     * @param resultType
     */
    public void writeResults(boolean resultType){

        if(resultType){//if writing quiz
            titleView.setText("Quiz Results");
            correctView.setText("Correct: "+correct+"/"+numQuestions);
            quoteView.setText(quoteResult(percentage));
            pointsView.setText("Level Progress: "+score+"/"+pointsPossible);
            percentView.setText("Percent Correct: "+percentage+"%");
        }
        else if(!resultType){//if writing user stats
            titleView.setText(current.getName()+"'s Stats");
            correctView.setText("Correct Overall: "+correct+"/"+numQuestions);
            quoteView.setText(quoteLevel(score));
            pointsView.setText("Level Progress: "+correct+"/"+pointsPossible);
            percentView.setText("Level Progress(%): "+percentage+"%");
        }
    }

    /**
     * Gives user a title based on their level
     * @param level - user level
     * @return
     */
    public String quoteLevel(int level){

        quote = "Level "+level;

        /*
        if(level <= 1)
            quote = "Novice (LVL 1)";
        else if(level <= 2)
            quote = "Apprentice (LVL 2)";
        else if(level <= 3)
            quote = "Adept (LVL 3)";
        else if(level <= 4)
            quote = "Expert (LVL 4)";
        else
            quote = "Master (LVL 5)";
         **/

        return quote;
    }

    /**
     * Starts the quiz game when button is pressed
     * @param v
     */
    public void quizGame(View v) {
        Intent game = new Intent(this, QuizActivity.class);
        this.startActivity(game);
    }

    class CustomAdapter extends BaseAdapter {

        ProgressBar progressBar;
        TextView textView_name;

        /**
         * Return the number of items to be displayed in the list view
         *
         * @return
         */
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

        /**
         * Inflates the actual view and assigns values to all of the appropriate attributes
         *
         * @param i         - index of listView or Count
         * @param view      - current view
         * @param viewGroup
         * @return
         */
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.acivity_cutsom_list_view, null);

            ImageView imageView = (ImageView) view.findViewById(R.id.imageView3);
            textView_name = (TextView) view.findViewById(R.id.textView_name);

            Resources res = getResources();//Create a new resource
            Drawable drawable = res.getDrawable(R.drawable.achievement_progress);//obtain the appropriate drawable
            Drawable drawableGreen = res.getDrawable(R.drawable.achievement_drawable_green);

            progressBar = (ProgressBar) view.findViewById(R.id.progressBarA);
            progressBar.setProgress(PROGRESS[i]);
            progressBar.setMax(100);
            progressBar.setProgressDrawable(drawable);

            imageView.setImageResource(lockIcon);
            textView_name.setText(NAMES[i]);

            //When to call each method at a given position
            if (i == 0)
                babySteps(drawableGreen, imageView, i);
            else if (i == 1)
                findingBalance(drawableGreen, imageView, i);
            else if (i == 2)
                rookieNoMore(drawableGreen, imageView, i);
            else if (i == 3)
                hardRocker(drawableGreen, imageView, i);
            else if (i == 4)
                blindNinja(drawableGreen, imageView, i);
            else if (i == 5)
                noteMeister(drawableGreen, imageView, i);

            return view;
        }

        /**
         * Will change the button to reflect the user having reached the goal needed to
         * Obtain the baby achievement
         *
         * @param drawable  - passes the drawable for a green progress bar
         * @param imageView - the list views ImageView
         * @param position  - position of the list view
         */
        public void babySteps(Drawable drawable, ImageView imageView, int position) {

            int baby = Integer.parseInt(achievements.get(0).points);

            if (current.getNumQuestionsCorrect() >= baby) {
                progressBar.setProgress(ACHIEVED);
                textView_name.setText("Baby Steps 1/1");
                progressBar.setProgressDrawable(drawable);
                imageView.setImageResource(IMAGES[position]);
                if (achieved[position] == false)
                    achievementCount++;
                achieved[position] = true;
            }
        }//end baby()

        /**
         * Will change the button to reflect the user having reached the goal need to
         * Obtain the findingBalance achievement
         */
        public void findingBalance(Drawable drawable, ImageView imageView, int position) {

            int balance = Integer.parseInt(achievements.get(1).points);

            float percentage = ((float) current.getNumQuestionsCorrect() / (float) balance) * 100;
            progressBar.setProgress((int) percentage);

            if (current.getNumQuestionsCorrect() >= balance) {
                //progressBar.setProgress(ACHIEVED);
                textView_name.setText("FINDING BALANCE 4/4");
                progressBar.setProgressDrawable(drawable);
                imageView.setImageResource(IMAGES[position]);
                if (achieved[position] == false)
                    achievementCount++;
                achieved[position] = true;
            } else {
                textView_name.setText("Achievement 2) " + totalPoints + "/4");
            }
        }//end findingBalance()

        /**
         * Will change the button to reflect the user having reached the goal need to
         * Obtain the findingBalance achievement
         * <p>
         * User has attempted at least 8 questions without getting a question wrong
         * Could add blind ninja 2 where he has to answer at least 16(Lvl 3) questions without misses
         */
        public void rookieNoMore(Drawable drawable, ImageView imageView, int position) {

            int rookie = Integer.parseInt(achievements.get(2).points);
            Log.d("Rookie", rookie + "");

            float percentage = ((float) current.getNumQuestionsCorrect() / (float) rookie) * 100;
            progressBar.setProgress((int) percentage);

            if (current.getNumQuestionsCorrect() >= rookie) {

                textView_name.setText("ROOKIE NO MORE 8/8");
                progressBar.setProgressDrawable(drawable);
                imageView.setImageResource(IMAGES[position]);
                if (achieved[position] == false)
                    achievementCount++;
                achieved[position] = true;
            } else {
                textView_name.setText("Achievement 3) " + totalPoints + "/8");
            }
        }//end rookieNoMore()

        /**
         * Will change the button to reflect the user having reached the goal need to
         * Obtain the blind ninja achievement
         */
        public void blindNinja(Drawable drawable, ImageView imageView, int position) {
            //!!!!Will Change to Reflect Missed Questions!!!!\\
            int ninja = Integer.parseInt(achievements.get(3).points);

            float percentage = ((float) current.getNumQuestionsCorrect() / (float) ninja) * 100;
            progressBar.setProgress((int) percentage);

            if (current.getNumQuestionsCorrect() >= ninja) {
                textView_name.setText("BLIND NINJA 8/8");
                progressBar.setProgressDrawable(drawable);
                imageView.setImageResource(IMAGES[position]);
                if (achieved[position] == false)
                    achievementCount++;
                achieved[position] = true;
            } else {
                textView_name.setText("Achievement 4) " + totalPoints + "/8");
            }
        }//end blindNinja()

        /**
         * Will change the button to reflect the user having reached the goal need to
         * Obtain the hard rocker achievement
         */
        public void hardRocker(Drawable drawable, ImageView imageView, int position) {

            int rocker = Integer.parseInt(achievements.get(4).points);

            float percentage = ((float) current.getNumQuestionsCorrect() / (float) rocker) * 100;
            progressBar.setProgress((int) percentage);

            if (current.getNumQuestionsCorrect() >= rocker) {
                textView_name.setText("HARD ROCKER 16/16");
                progressBar.setProgressDrawable(drawable);
                imageView.setImageResource(IMAGES[position]);
                if (achieved[position] == false)
                    achievementCount++;
                achieved[position] = true;
            } else {
                textView_name.setText("Achievement 5) " + totalPoints + "/16");
            }
        }//end hardRocker()

        /**
         * Will change the button to reflect the user having reached the goal need to
         * Obtain the note-meister achievement
         */
        public void noteMeister(Drawable drawable, ImageView imageView, int position) {

            int note = Integer.parseInt(achievements.get(5).points);

            float percentage = ((float) achievementCount / (float) note) * 100;
            progressBar.setProgress((int) percentage);

            if (achievementCount == note) {
                textView_name.setText("NOTE-MEISTER 5/5");
                progressBar.setProgressDrawable(drawable);
                imageView.setImageResource(IMAGES[position]);
            } else{
                textView_name.setText("Achievement 6) "+achievementCount+"/5");
            }
        }//end noteMeister()

    }//end class CustomAdapter



}
