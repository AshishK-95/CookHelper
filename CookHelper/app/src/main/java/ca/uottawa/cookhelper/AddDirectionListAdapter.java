package ca.uottawa.cookhelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.LinkedList;

/**
 * Created by AshishK on 2016-11-28.
 */
public class AddDirectionListAdapter extends BaseAdapter implements
        View.OnKeyListener,
        View.OnFocusChangeListener {

    private Activity activity;
    private LinkedList<String> directions;
    private static LayoutInflater inflater = null;
    private AddDirectionsInterface listener;

    public AddDirectionListAdapter(Activity activity, LinkedList<String> directions, AddDirectionsInterface listener) {
        this.activity = activity;
        this.directions = directions;
        this.listener = listener;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //This interface is used to communicate back to the host fragment (AddRecipe3Fragment)
    public interface AddDirectionsInterface {
        void onDeleteButtonClick(int index);
    }

    @Override
    public int getCount() {
        return directions.size();
    }

    @Override
    public Object getItem(int index) {
        return directions.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(final int index, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.addrecipe3row_view, null);
        }

        //Customize the direction item
        final EditText direction = (EditText) convertView.findViewById(R.id.directionRow);
        direction.setHint("Direction");
        direction.setHintTextColor(Color.WHITE);
        direction.setTextColor(Color.WHITE);
        direction.setText(directions.get(index));
        direction.setOnFocusChangeListener(this);
        direction.setOnKeyListener(this);
        direction.setContentDescription(Integer.toString(index)); //add index to identify direction
        HomePage.font.setFontOf(direction, 2);

        //Add onClickListener to 'delete' icon
        ImageView deleteIcon = (ImageView) convertView.findViewById(R.id.cancelField);
        deleteIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                System.out.println("Delete index is " + index);
                listener.onDeleteButtonClick(index);
            }
        });
        return convertView;
    }

    //capture input if user presses enter after inputting direction
    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {

        EditText editText = (EditText) view;

        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
            hideKeyboard();

            switch (view.getId()) {

                case R.id.directionRow:
                    directions.set(Integer.valueOf(editText.getContentDescription().toString()), editText.getText().toString());
                    break;
            }
            notifyDataSetChanged();
            System.out.println(directions);
        }
        return false;
    }

    private void hideKeyboard() {
        View tempview = activity.getCurrentFocus();
        if (tempview != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(tempview.getWindowToken(), 0);
        }
    }

    //capture input of direction after user switches to next edit text field without press enter
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        EditText editText = (EditText) view;

        switch (view.getId()) {
            case R.id.directionRow:
                if (!hasFocus) {
                    if (directions.size() != 0) {
                        directions.set(Integer.valueOf(editText.getContentDescription().toString()), editText.getText().toString());
                    }
                }
                break;
        }
    }
}
