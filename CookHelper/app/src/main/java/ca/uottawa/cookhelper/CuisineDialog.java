package ca.uottawa.cookhelper;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;


/**
 * Created by AshishK on 2016-11-25.
 */
public class CuisineDialog extends DialogFragment {

    private CuisineDialogListener mListener;

    public interface CuisineDialogListener {
        void onCuisineTypeClick(int id);
    }

    @Override
    public void onCreate(Bundle  savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mListener = (CuisineDialogListener) getTargetFragment();
        }
        catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement CuisineDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder cuisineDialog = new AlertDialog.Builder(getActivity());

        cuisineDialog.setTitle("Cuisine Type")

                .setItems(R.array.cuisine_types, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onCuisineTypeClick(id);

                    }
                });

        return cuisineDialog.create();
    }
}
