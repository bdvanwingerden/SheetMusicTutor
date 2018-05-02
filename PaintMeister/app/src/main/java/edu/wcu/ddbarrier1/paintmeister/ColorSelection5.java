package edu.wcu.ddbarrier1.paintmeister;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
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

/**
 *
 * Class that implements the color selection activity
 *
 * @Author - Dorian Barrier
 */
public class ColorSelection5 extends AppCompatActivity{

    /**Array of possible colors a user may select*/
    String[] COLORS = {"Red","Blue","Green","Yellow","Purple","Gold","Black","Teal"};

    /**Tag for shared preferences*/
    public static final String MYPREFERENCES = "MyPrefs2" ;

    /**Tag to send/retrieve brush color via shared pref*/
    public static final String BRUSHCOLOR = "Color" ;

    /**Shared pref object*/
    SharedPreferences sharedPreferences;

    /**
     * OnCreate to initialize custom list view and its onClick
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_selection_list);

        sharedPreferences = getSharedPreferences(MYPREFERENCES,MODE_PRIVATE);

        ListView listView = (ListView) findViewById(R.id.ListView1);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

        //OnItemClickListener to invoke alert dialog for each item in listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i = new Intent(getBaseContext(), Touch.class);

                //Determine what color to set brush
                switch (position) {
                    case 0:
                        Canvas3.currentColor = Color.RED;
                        saveColor(Color.RED);
                        break;
                    case 1:
                        Canvas3.currentColor = Color.BLUE;
                        saveColor(Color.BLUE);
                        break;
                    case 2:
                        Canvas3.currentColor = Color.GREEN;
                        saveColor(Color.GREEN);
                        break;
                    case 3:
                        Canvas3.currentColor = Color.YELLOW;
                        saveColor(Color.YELLOW);
                        break;
                    case 4:
                        Canvas3.currentColor = Color.rgb(98,51,147);
                        saveColor(Color.rgb(98,51,147));
                        break;
                    case 5:
                        Canvas3.currentColor = Color.rgb(193,168,117);
                        saveColor(Color.rgb(193,168,117));
                        break;
                    case 6:
                        Canvas3.currentColor = Color.BLACK;
                        saveColor(Color.BLACK);
                        break;
                    case 7:
                        Canvas3.currentColor = Color.rgb(0,120,140);
                        saveColor(Color.rgb(0,120,140));
                        break;
                    default:
                }

                getBaseContext().startActivity(i);

            }
        });//end onItemClick

    }//end onCreate

    /**
     * save the current color in shared preferences when a new color is selected
     * @param theColor
     */
    private void saveColor(int theColor){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(BRUSHCOLOR,theColor);
        editor.commit();
    }//end saveColor()

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
            return COLORS.length;
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
            view = getLayoutInflater().inflate(R.layout.activity_custom_list_view, null);

            TextView tv = (TextView) view.findViewById(R.id.colorName);

            tv.setText(COLORS[i]);

            return view;
        }
    }//end class CustomAdapter


}//end ColorSelection
