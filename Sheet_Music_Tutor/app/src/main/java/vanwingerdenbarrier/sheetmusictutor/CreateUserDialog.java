package vanwingerdenbarrier.sheetmusictutor;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Basic Dialog that appears when the user attempts to add a new user
 *
 * @author Bronson VanWingerden
 * @author Dorian Barrier
 */
public class CreateUserDialog extends DialogFragment implements View.OnClickListener{

    /**
     * the accept button
     */
    Button accept;

    /**
     * the EditText field containing the name to add
     */
    EditText nameToAdd;

    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_user_dialog, container, false);

        accept = (Button) view.findViewById(R.id.acceptUserButton);
        nameToAdd = (EditText) view.findViewById(R.id.toAddName);
        accept.setOnClickListener(this);
        return view;
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    /**
     * calls UserMenus onAccept button
     */
    public void onClick(View view){
        switch(view.getId()){
            case R.id.acceptUserButton:
                ((UserMenu)(CreateUserDialog.this.getActivity())).onAcceptDialog(view,
                        nameToAdd.getText().toString());
                break;
            case R.id.toAddName:
                EditText nameField = (EditText) view.findViewById(view.getId());
                nameField.getText().clear();
                break;
        }

    }
}