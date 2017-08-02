package ca.uottawa.cookhelper;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by AshishK on 2016-11-25.
 */
public class CategoryDialog extends DialogFragment {

    private CategoryDialogListener mListener;

    public interface CategoryDialogListener {
        void onCategoryTypeClick(int id);
    }

    @Override
    public void onCreate(Bundle  savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mListener = (CategoryDialogListener) getTargetFragment();
        }
        catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement CategoryDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder categoryDialog = new AlertDialog.Builder(getActivity());

        categoryDialog.setTitle("Category")

                .setItems(R.array.category_types, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onCategoryTypeClick(id);
                    }
                });

        return categoryDialog.create();
    }
}
