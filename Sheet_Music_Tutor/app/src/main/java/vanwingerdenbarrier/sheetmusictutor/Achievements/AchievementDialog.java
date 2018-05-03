package vanwingerdenbarrier.sheetmusictutor.Achievements;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

/**
 * Created by Doriangh4 on 2/27/2018.
 */

public class AchievementDialog extends AppCompatDialogFragment {

    /**Determines the correct dialog to display*/
    String toDisplay;

    public AchievementDialog(){
        this.setArguments(new Bundle());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Information").setMessage("this is a dialog")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();
    }
}
