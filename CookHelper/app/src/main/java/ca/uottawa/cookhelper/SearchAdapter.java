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
import android.widget.TextView;
import java.util.LinkedList;

/**
 * Created by AshishK on 2016-11-28.
 */
public class SearchAdapter extends BaseAdapter implements
        View.OnClickListener,
        View.OnKeyListener,
        View.OnFocusChangeListener {

    private Activity activity;
    private LinkedList<String> category, searchCriteria;
    private static LayoutInflater inflater = null;
    private SearchInterface listener;
    private String[] categoryTypes;
    private TextView temp;


    public interface SearchInterface {
        void onDeleteButtonClick(int index);
        void onCategoryButtonClick(int index, String id);
    }
    public SearchAdapter(Activity activity, LinkedList<String> category,
                         LinkedList<String> searchCriteria, SearchInterface listener) {

        this.activity = activity;
        this.category = category;
        this.searchCriteria = searchCriteria;
        this.listener = listener;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        categoryTypes = activity.getResources().getStringArray(R.array.category_types);
    }

    @Override
    public int getCount() {
        return searchCriteria.size();
    }

    @Override
    public Object getItem(int index) {
        return searchCriteria.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(final int index, View convertView, ViewGroup parent) {

        final TextView categoryText;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.search_row, null);
        }

        //Customize the category item
        categoryText = (TextView) convertView.findViewById(R.id.category);
        categoryText.setHint("Category");
        categoryText.setText(category.get(index));
        categoryText.setTextColor(Color.BLACK);
        HomePage.font.setFontOf(categoryText, 2);
        categoryText.setContentDescription(Integer.toString(index));

        categoryText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                listener.onCategoryButtonClick(index, categoryText.getContentDescription().toString());
            }
        });

        //Customize the search item
        final EditText searchEditText = (EditText) convertView.findViewById(R.id.searchCriteria);
        searchEditText.setHint("Search...");
        searchEditText.setOnFocusChangeListener(this);
        searchEditText.setOnKeyListener(this);
        searchEditText.setTextColor(Color.BLACK);
        searchEditText.setText(searchCriteria.get(index));
        HomePage.font.setFontOf(searchEditText, 2);
        searchEditText.setContentDescription(Integer.toString(index));

        //Add onClickListener to 'delete' icon
        ImageView deleteIcon = (ImageView) convertView.findViewById(R.id.cancelSearchField);
        deleteIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                System.out.println("Delete index is " + index);
                listener.onDeleteButtonClick(index);
            }
        });

        return convertView;
    }

    //capture input if user presses enter after inputting category/search criteria
    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {

        TextView categoryEditText = (TextView) view;
        EditText editText = (EditText) view;

        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
            hideKeyboard();

            switch (view.getId()) {

                case R.id.category:
                    category.set(Integer.valueOf(editText.getContentDescription().toString()), categoryEditText.getText().toString());
                    break;

                case R.id.searchCriteria:
                    searchCriteria.set(Integer.valueOf(editText.getContentDescription().toString()), editText.getText().toString());
                    break;

            }
            notifyDataSetChanged();
            System.out.println(category);
            System.out.println(searchCriteria);
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

    //capture input of category/search criteria after user switches to next edit text field without press enter
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        TextView categoryEditText = (TextView) view;
        EditText editText = (EditText) view;

        switch (view.getId()) {
            case R.id.category:
                if (!hasFocus) {
                    if (category.size() != 0) {
                        category.set(Integer.valueOf(editText.getContentDescription().toString()), categoryEditText.getText().toString());
                    }
                }
                break;

            case R.id.searchCriteria:
                if (!hasFocus) {
                    if (searchCriteria.size() != 0) {
                        searchCriteria.set(Integer.valueOf(editText.getContentDescription().toString()), editText.getText().toString());
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {

        if (view instanceof TextView) {

            switch (view.getId()) {

            }
        }
    }

    public LinkedList<String> getCategory() {
        return category;
    }

    public LinkedList<String> getSearchCriteria() {
        return searchCriteria;
    }
}
