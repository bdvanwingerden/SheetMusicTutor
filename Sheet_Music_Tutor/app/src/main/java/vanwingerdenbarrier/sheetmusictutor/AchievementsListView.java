package vanwingerdenbarrier.sheetmusictutor;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import vanwingerdenbarrier.sheetmusictutor.R;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.User;
import vanwingerdenbarrier.sheetmusictutor.UserInfo.UserList;

/**
 * Created by Doriangh4 on 3/11/2018.
 */

public class AchievementsListView extends AppCompatActivity{

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

    /**Current user*/
    User current;

    /**Number of questions a users has attempted. Used for achievement calculations*/
    int attempted;

    /**Number of achievements the user has obtained*/
    int achievementCount;

    int[] IMAGES = {R.drawable.baby,R.drawable.balance,R.drawable.rookie,R.drawable.rocker,
            R.drawable.baby,R.drawable.baby};

    String[] NAMES = {"BABY STEPS 1/1","FINDING BALANCE 4/4","ROOKIE NO MORE 4/8","HARD ROCKER 0/16",
            "BLIND NINJA 8/8","NOTE-MEISTER 4/5"};

    String[] DESCRIPTIONS = {"BABY","BALANCE","ROOKIE","ROCKER","NINJA","NOTE"};

    int[] PROGRESS = {0,25,50,100,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements_list);

        ListView listView = (ListView) findViewById(R.id.ListView1);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

        current = new UserList(getBaseContext()).findCurrent();


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

    class CustomAdapter extends BaseAdapter {

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
            TextView textView_name = (TextView) view.findViewById(R.id.textView_name);
            //TextView textView_description = (TextView) view.findViewById(R.id.textView_description);

            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.achievement_progress);
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBarA);
            progressBar.setProgress(PROGRESS[i]);
            progressBar.setMax(100);
            progressBar.setProgressDrawable(drawable);

            imageView.setImageResource(IMAGES[i]);
            textView_name.setText(NAMES[i]);
            //textView_description.setText(DESCRIPTIONS[i]);

            return view;
        }
    }//end class CustomAdapter

}
