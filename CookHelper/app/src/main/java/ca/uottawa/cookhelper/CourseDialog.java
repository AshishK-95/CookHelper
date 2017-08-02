package ca.uottawa.cookhelper;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;


/**
 * Created by AshishK on 2016-11-25.
 */
public class CourseDialog extends DialogFragment {

    private CourseDialogListener mListener;

    public interface CourseDialogListener {
        void onCourseTypeClick(int id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mListener = (CourseDialogListener) getTargetFragment();
        }
        catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement CourseDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder courseDialog = new AlertDialog.Builder(getActivity());

        courseDialog.setTitle("Course Type")

                .setItems(R.array.course_types, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onCourseTypeClick(id);
                    }
                });

        return courseDialog.create();
    }
}
