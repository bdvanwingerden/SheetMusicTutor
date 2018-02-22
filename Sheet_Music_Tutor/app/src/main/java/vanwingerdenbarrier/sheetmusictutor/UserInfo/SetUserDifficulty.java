package vanwingerdenbarrier.sheetmusictutor.UserInfo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vanwingerdenbarrier.sheetmusictutor.R;


/**
 * Class that prompts user to select difficulty when
 * they create a new user
 *
 * @author Bronson VanWingerden
 * @author Dorian Barrier
 */

public class SetUserDifficulty extends DialogFragment implements View.OnClickListener{

    /**Sets the users starting level to easy*/
    Button easyBtn;

    /**Sets the users starting level to medium*/
    Button mediumBtn;


    /**Sets the users starting level to hard*/
    Button hardBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_difficulty, container, false);

        easyBtn = (Button) view.findViewById(R.id.easy_button);
        mediumBtn = (Button) view.findViewById(R.id.medium_button);
        hardBtn = (Button) view.findViewById(R.id.hard_button);

        easyBtn.setOnClickListener(this);
        mediumBtn.setOnClickListener(this);
        hardBtn.setOnClickListener(this);

        return view;
    }

    /**
     *When medium or hard difficulty are selected redirect to userMenu
     * Class where the method that actually sets the users stats given their difficulty
     *
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.easy_button:
                Toast.makeText(getActivity(), "Easy button clicked!",
                        Toast.LENGTH_LONG).show();
                dismiss();
                break;
            case R.id.medium_button:
                Toast.makeText(getActivity(), "Medium button clicked!",
                        Toast.LENGTH_LONG).show();
                ((UserMenu)(SetUserDifficulty.this.getActivity())).onSelectDiffDialog(view,1);
                dismiss();
                break;
            case R.id.hard_button:
                Toast.makeText(getActivity(), "Hard button clicked!",
                        Toast.LENGTH_LONG).show();
                ((UserMenu)(SetUserDifficulty.this.getActivity())).onSelectDiffDialog(view,2);
                dismiss();//dismiss the dialog box
                break;
        }
    }//end onClick



}//end SetUserDifficulty
