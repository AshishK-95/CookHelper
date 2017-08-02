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
public class AddIngredientListAdapter extends BaseAdapter implements
        View.OnKeyListener,
        View.OnFocusChangeListener {

    private Activity activity;
    private LinkedList<String> ingredients, quantity;
    private static LayoutInflater inflater = null;
    private AddIngredientsInterface listener;

    public AddIngredientListAdapter(Activity activity, LinkedList<String> ingredients,  LinkedList<String> quantity, AddIngredientsInterface listener) {
        this.activity = activity;
        this.ingredients = ingredients;
        this.quantity = quantity;
        this.listener = listener;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //This interface is used to communicate back to the host fragment (AddRecipe2Fragment)
    public interface AddIngredientsInterface {
        void onDeleteButtonClick(int index);
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int index) {
        return ingredients.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(final int index, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.addrecipe2row_view, null);
        }

        //Customize the ingredient item
        final EditText ingredient = (EditText) convertView.findViewById(R.id.ingredientrow);
        ingredient.setHint("Ingredient");
        ingredient.setHintTextColor(Color.WHITE);
        ingredient.setTextColor(Color.WHITE);
        ingredient.setText(ingredients.get(index));
        ingredient.setOnFocusChangeListener(this);
        ingredient.setOnKeyListener(this);
        ingredient.setContentDescription(Integer.toString(index)); //add index to identify ingredient
        HomePage.font.setFontOf(ingredient, 2);

        //Customize the quantity item
        final EditText quantityEditTextView = (EditText) convertView.findViewById(R.id.quantity);
        quantityEditTextView.setHint("Quantity");
        quantityEditTextView.setHintTextColor(Color.WHITE);
        quantityEditTextView.setTextColor(Color.WHITE);
        quantityEditTextView.setText(quantity.get(index));
        quantityEditTextView.setOnFocusChangeListener(this);
        quantityEditTextView.setOnKeyListener(this);
        quantityEditTextView.setContentDescription(Integer.toString(index)); //add index to identify quantity
        HomePage.font.setFontOf(quantityEditTextView, 2);

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

    //capture input if user presses enter after inputting ingredient/quantity
    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {

        EditText editText = (EditText) view;

        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
            hideKeyboard();

            switch (view.getId()) {

                case R.id.ingredientrow:
                    ingredients.set(Integer.valueOf(editText.getContentDescription().toString()), editText.getText().toString());
                    break;

                case R.id.quantity:
                    quantity.set(Integer.valueOf(editText.getContentDescription().toString()), editText.getText().toString());
                    break;
            }
            notifyDataSetChanged();
            System.out.println(ingredients);
            System.out.println(quantity);
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

    //capture input of ingredient/quantity after user switches to next edit text field without press enter
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        EditText editText = (EditText) view;

        switch (view.getId()) {
            case R.id.ingredientrow:
                if (!hasFocus) {
                    if (ingredients.size() != 0) {
                        ingredients.set(Integer.valueOf(editText.getContentDescription().toString()), editText.getText().toString());
                    }
                }
                break;

            case R.id.quantity:
                if (!hasFocus) {
                    if (quantity.size() != 0) {
                        quantity.set(Integer.valueOf(editText.getContentDescription().toString()), editText.getText().toString());
                    }
                }
                break;
        }
    }
}
