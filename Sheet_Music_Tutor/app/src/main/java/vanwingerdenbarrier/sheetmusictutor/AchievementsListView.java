package vanwingerdenbarrier.sheetmusictutor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import vanwingerdenbarrier.sheetmusictutor.R;

/**
 * Created by Doriangh4 on 3/11/2018.
 */

public class AchievementsListView extends AppCompatActivity{

    ProgressBar progressBar;

    int[] IMAGES = {R.drawable.baby,R.drawable.balance,R.drawable.rookie,R.drawable.rocker,
            R.drawable.baby,R.drawable.baby};

    String[] NAMES = {"BABY STEPS 1/1","FINDING BALANCE 4/4","ROOKIE NO MORE 4/8","HARD ROCKER 0/16",
            "BLIND NINJA 8/8","NOTE-MEISTER 4/5"};

    String[] DESCRIPTIONS = {"BABY","BALANCE","ROOKIE","ROCKER","NINJA","NOTE"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements_list);

        ListView listView = (ListView) findViewById(R.id.ListView1);
        
        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);


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
            TextView textView_description = (TextView) view.findViewById(R.id.textView_description);

            imageView.setImageResource(IMAGES[i]);
            textView_name.setText(NAMES[i]);
            textView_description.setText(DESCRIPTIONS[i]);

            return view;
        }
    }//end class CustomAdapter

}
