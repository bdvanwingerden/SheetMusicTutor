package edu.wcu.ddbarrier1.paintmeister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *
 * Defines the implementation for the load screen
 *
 * @Author - Dorian Barrier
 */
public class Load6 extends AppCompatActivity {

    /**Name of the files saved internally before .csv is added*/
    static ArrayList<String> FILES = new ArrayList<>();

    /**Tag for shared preferences*/
    public static final String MYPREFERENCES = "MyPrefs2" ;

    /**Shared Pref object*/
    SharedPreferences sharedPreferences;

    /**
     * On Create to get file names from shared pref as well as implementing the custom list view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_painting_list);

        ListView listView = (ListView) findViewById(R.id.ListView2);

        Load6.CustomAdapter customAdapter = new Load6.CustomAdapter();

        listView.setAdapter(customAdapter);

        sharedPreferences = getSharedPreferences(MYPREFERENCES,MODE_PRIVATE);

        //Reload the files from shared pref
        if(sharedPreferences.contains("Load_size")){
            FILES.clear();
            int size = sharedPreferences.getInt("Load_size",0);

            for(int i = 0; i < size;i++){
                FILES.add(sharedPreferences.getString("File"+i,null));
            }
        }

        //OnItemClickListener to invoke alert dialog for each item in listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String newName = FILES.get(position);
                Intent i = new Intent(getBaseContext(), Touch.class);
                i.putExtra("LoadIn",newName);
                Canvas3.paintName = newName;
                getBaseContext().startActivity(i);

            }
        });//end onItemClick


    }//end onCreate

    /**
     * Class that provide the implementation for the CustomAdapter that is used with the listView
     */
    class CustomAdapter extends BaseAdapter {

        /**
         * Return the number of items to be displayed in the list view
         *
         * @return
         */
        @Override
        public int getCount() {
            return FILES.size();
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
            view = getLayoutInflater().inflate(R.layout.activity_custom_load_list_view, null);

            TextView tv = (TextView) view.findViewById(R.id.loadName);

            tv.setText(FILES.get(i));

            return view;
        }
    }//end class CustomAdapter

}//end class Load6
