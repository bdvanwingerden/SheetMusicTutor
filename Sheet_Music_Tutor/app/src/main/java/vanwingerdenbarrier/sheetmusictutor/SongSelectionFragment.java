package vanwingerdenbarrier.sheetmusictutor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import vanwingerdenbarrier.sheetmusictutor.Game.GameActivity;


public class SongSelectionFragment extends Fragment {

    /**Names of songs that can be selected*/
    String[] SONG_NAMES = {"1)Twinkle Twinkle Little Star","2)Itsy Bitsy Spider"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_song_selection, container, false);

        final ListView listView = (ListView) v.findViewById(R.id.ListView2);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Play Along With Dave!");
        alertDialog.setMessage("Pick a Song and Play the piano along with Dave as he tells you which notes to play!");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        dialogInterface.dismiss();
                    }
                });

        alertDialog.setCancelable(false);


        alertDialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent nextActivity = new Intent(getActivity(), GameActivity.class);
                nextActivity.putExtra("gameType", 8);
                switch (position) {
                    case 0:
                        nextActivity.putExtra("songType", 0);//twinkle twinkle
                        break;
                    case 1:
                        nextActivity.putExtra("songType", 1);//Spider
                        break;
                    default:
                }

                getActivity().startActivity(nextActivity);

            }
        });//end onItemClick

        // Inflate the layout for this fragment
        return v;
    }

    /**
     * Class to create a custom adapter for the wins list view
     */
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return SONG_NAMES.length;
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
            view = getLayoutInflater().inflate(R.layout.activity_sing_along_list,null);

            TextView tv1 = (TextView) view.findViewById(R.id.songTextView);

            tv1.setText(SONG_NAMES[i]);

            return view;
        }
    }//end class BaseAdapter




}
