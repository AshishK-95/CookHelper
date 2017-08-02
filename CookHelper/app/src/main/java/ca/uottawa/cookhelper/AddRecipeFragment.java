package ca.uottawa.cookhelper;

import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;


/**
 * Created by AshishK on 2016-11-23.
 */


public class AddRecipeFragment extends Fragment implements
        View.OnClickListener,
        View.OnFocusChangeListener,
        View.OnKeyListener,
        CuisineDialog.CuisineDialogListener,
        CourseDialog.CourseDialogListener {

    private int[] editTextFieldIds = {R.id.nameField, R.id.caloriesField, R.id.prepTimeField, R.id.cookTimeField, R.id.servingField};
    private EditText[] editTextObject = new EditText[editTextFieldIds.length];

    private String[] editRecipeFields = new String[9];
    private boolean editFlag = false;

    private int[] textViewFieldIDs = {R.id.cuisineField, R.id.courseField, R.id.timeUnit1, R.id.timeUnit2};
    private TextView[] textViewObject = new TextView[textViewFieldIDs.length];

    private int[] imageViewFieldIDs = {R.id.continueField, R.id.cancelField};
    private ImageView[] imageViewObject = new ImageView[imageViewFieldIDs.length];


    private String[] cuisineTypes, courseTypes;
    private TextView temp;
    private int id;

    private CuisineDialog cuisine_dialog = new CuisineDialog();
    private CourseDialog course_dialog = new CourseDialog();

    private AddRecipe2Fragment addRecipe2Fragment;

    private String name = null;
    private String cuisine = null;
    private String course = null;
    private String timeUnitPrep = "min";
    private String timeUnitCook = "min";
    private int servingSize = -1;
    private int calories = -1;
    private float prepTime = -1;
    private float cookTime = -1;




    public static AddRecipeFragment newInstance() {
        return new AddRecipeFragment();
    }

    public AddRecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.addrecipe_view, container, false);

        if (getArguments() != null) {
            if (getArguments().getSerializable("edit_recipe") != null) {
                Recipe recipe = (Recipe) getArguments().getSerializable("edit_recipe");
                recipe.setId(recipe.getId());
                id = recipe.getId();
                editRecipeFields[0] = name = recipe.getTitle();
                editRecipeFields[5] = cuisine = recipe.getCuisine();
                editRecipeFields[6] = course = recipe.getCourse();
                editRecipeFields[7] = timeUnitPrep = recipe.getPrepTimeUnit();
                editRecipeFields[8] = timeUnitCook = recipe.getCookTimeUnit();
                servingSize = recipe.getServingSize();
                editRecipeFields[4] = "Serves " + Integer.toString(servingSize);
                calories = recipe.getCalories();
                editRecipeFields[1] = Integer.toString(calories) + " calories";
                prepTime = recipe.getPrepTime();
                editRecipeFields[2] = "Prep for " + Float.toString(prepTime);
                cookTime = recipe.getCookTime();
                editRecipeFields[3] = "Cook for " + Float.toString(cookTime);
                editFlag = true;
            }
        }

        addRecipe2Fragment = new AddRecipe2Fragment();

        int c = 0;
        for (int i = 0; i < editTextFieldIds.length; i++) {
            editTextObject[i] = (EditText) view.findViewById(editTextFieldIds[i]);
            editTextObject[i].setOnFocusChangeListener(this);
            editTextObject[i].setOnKeyListener(this);
            HomePage.font.setFontOf(editTextObject[i], 2);

            if (editFlag) {
                editTextObject[i].setText(editRecipeFields[c]);
                c++;
            }
        }


        for (int j = 0; j < textViewFieldIDs.length; j++) {
            textViewObject[j] = (TextView) view.findViewById(textViewFieldIDs[j]);
            textViewObject[j].setOnClickListener(this);
            HomePage.font.setFontOf(textViewObject[j], 2);

            if (textViewFieldIDs[j] == R.id.timeUnit1) {
                textViewObject[j].setText(timeUnitPrep);
            }

            if (textViewFieldIDs[j] == R.id.timeUnit2) {
                textViewObject[j].setText(timeUnitCook);
            }

            if (editFlag) {
                textViewObject[j].setText(editRecipeFields[c]);
                c++;
            }
        }

        for (int k = 0; k < imageViewFieldIDs.length; k++) {
            imageViewObject[k] = (ImageView) view.findViewById(imageViewFieldIDs[k]);
            imageViewObject[k].setOnClickListener(this);
        }

        cuisineTypes = getResources().getStringArray(R.array.cuisine_types);
        courseTypes = getResources().getStringArray(R.array.course_types);

        return view;
    }

    @Override
    public void onClick(View view) {

        if (view instanceof  TextView) {

            temp = (TextView) view;
            //view.requestFocus();

            switch (view.getId()) {
                case R.id.cuisineField:
                    makeAllFieldsBlackExcept(view.getId());
                    hideKeyboard();
                    showDialog("cuisine");
                    break;

                case R.id.courseField:
                    makeAllFieldsBlackExcept(view.getId());
                    hideKeyboard();
                    showDialog("course");
                    break;

                case R.id.timeUnit1:
                    hideKeyboard();

                    if (temp.getText() == "min") {
                        timeUnitPrep = "hr";
                        temp.setText(timeUnitPrep);
                    }
                    else {
                        timeUnitPrep = "min";
                        temp.setText(timeUnitPrep);
                    }
                    break;

                case R.id.timeUnit2:
                    hideKeyboard();

                    if (temp.getText() == "min") {
                        timeUnitCook = "hr";
                        temp.setText(timeUnitCook);
                    }
                    else {
                        timeUnitCook = "min";
                        temp.setText(timeUnitCook);
                    }
                    break;
            }
        }

        if (view instanceof ImageView) {
            switch (view.getId()) {
                case R.id.continueField:
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    if (verifyInputs(name, cuisine, course, servingSize, calories, prepTime, cookTime)) {
                        Bundle bundle = createBundle(name, cuisine, course, servingSize, calories, prepTime,  timeUnitPrep, cookTime, timeUnitCook);
                        addRecipe2Fragment.setArguments(bundle);
                        transaction.replace(R.id.fragment_container, addRecipe2Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }
                    else {
                        Context context = getActivity().getApplicationContext();
                        CharSequence text = "Invalid input(s)";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(context, text, duration).show();
                    }
                    break;

                case R.id.cancelField:
                    nullAllRecipeFields();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, MainMenu.browseFragment).commit();
                    break;
            }
        }
    }


    @Override
    public void onFocusChange(View view, boolean hasFocus) {

        makeAllFieldsBlackExcept(view.getId());
        EditText editText = (EditText) view;

        switch (view.getId()) {
            case R.id.nameField:
                if (!hasFocus) {
                    if (editText.getText().toString().matches("")) {
                        editText.setHint("Name");
                    }
                    else {
                        handleInput(editText, R.id.nameField);
                    }
                }
                else {
                    preserveInput(editText, R.id.nameField);
                }
                break;

            case R.id.caloriesField:
                if (!hasFocus) {
                    if (editText.getText().toString().matches("")) {
                        editText.setHint("Calories");
                    }
                    else {
                        handleInput(editText, R.id.caloriesField);
                    }
                }
                else {
                    preserveInput(editText, R.id.caloriesField);
                }
                break;

            case R.id.prepTimeField:
                if (!hasFocus) {
                    if (editText.getText().toString().matches("")) {
                        editText.setHint("Preparation Time");
                    }
                    else {
                        handleInput(editText, R.id.prepTimeField);
                    }
                }
                else {
                    preserveInput(editText, R.id.prepTimeField);
                }
                break;

            case R.id.cookTimeField:
                if (!hasFocus) {
                    if (editText.getText().toString().matches("")) {
                        editText.setHint("Cook Time");
                    }
                    else {
                        handleInput(editText, R.id.cookTimeField);
                    }
                }
                else {
                    preserveInput(editText, R.id.cookTimeField);
                }
                break;

            case R.id.servingField:
                if (!hasFocus) {

                    if (editText.getText().toString().matches("")) {
                        editText.setHint("Serving Size");
                    }
                    else {
                        handleInput(editText, R.id.servingField);
                    }
                }
                else {
                    preserveInput(editText, R.id.servingField);
                }
                break;
        }
    }


    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {

        EditText editText = (EditText) view;

        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
            editText.setFocusableInTouchMode(false);
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(true);
            editText.setFocusable(true);
            hideKeyboard();
        }

        return false;
    }


    private void makeAllFieldsBlackExcept(int id) {


        for (int i = 0; i < editTextObject.length; i++) {
            if (editTextObject[i].getId() == id) {
                editTextObject[i].setBackgroundResource(R.color.orange);
            } else {
                editTextObject[i].setBackgroundResource(R.color.black);
            }
        }

        for (int j = 0; j < textViewObject.length; j++) {
            if (textViewObject[j].getId() == id) {
                textViewObject[j].setBackgroundResource(R.color.orange);
            } else {
                textViewObject[j].setBackgroundResource(R.color.black);
            }
        }
    }

    @Override
    public void onCuisineTypeClick(int id) {
        cuisine = cuisineTypes[id];
        temp.setText(cuisineTypes[id]);
    }

    @Override
    public void onCourseTypeClick(int id) {
        course = courseTypes[id];
        temp.setText(courseTypes[id]);
    }

    private void showDialog(String id) {
        if (id == "cuisine") {
            cuisine_dialog.setTargetFragment(this, 0);
            cuisine_dialog.show(this.getFragmentManager(), "Cuisine");
        }
        else if (id == "course") {
            course_dialog.setTargetFragment(this, 0);
            course_dialog.show(this.getFragmentManager(), "Course");
        }
    }

    private void hideKeyboard() {

        View tempview = getActivity().getCurrentFocus();
        if (tempview != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(tempview.getWindowToken(), 0);
        }
    }

    private void handleInput(EditText editTextObject, int id) {

        String s;
        switch (id) {
            case R.id.nameField:
                name = editTextObject.getText().toString();
                break;
            case R.id.caloriesField:
                calories = Integer.parseInt(editTextObject.getText().toString());
                s = calories + " calories";
                editTextObject.setText(s);
                break;
            case R.id.prepTimeField:
                prepTime = Float.valueOf(editTextObject.getText().toString());
                s = "Prep for " + prepTime;
                editTextObject.setText(s);
                break;
            case R.id.cookTimeField:
                cookTime = Float.valueOf(editTextObject.getText().toString());
                s = "Cook for " + cookTime;
                editTextObject.setText(s);
                break;
            case R.id.servingField:
                servingSize = Integer.parseInt(editTextObject.getText().toString());
                s = "Serves " + servingSize;
                editTextObject.setText(s);
        }
    }

    private void preserveInput(EditText editTextObject, int id) {

        switch (id) {
            case R.id.caloriesField:
                editTextObject.setText("");
                break;
            case R.id.prepTimeField:
                editTextObject.setText("");
                break;
            case R.id.cookTimeField:
                editTextObject.setText("");
                break;
            case R.id.servingField:
                editTextObject.setText("");
                break;

        }
    }

    private Bundle createBundle(String name, String cuisine, String course, int servingSize, int calories, float prepTime, String prepTimeUnit, float cookTime, String cookTimeUnit) {
        Bundle bundle;

        if (getArguments() == null) {
            bundle = new Bundle();
        }
        else {
            bundle = getArguments();
        }

        bundle.putBoolean("idFlag", editFlag);
        bundle.putInt("id", id);
        bundle.putString("name", name);
        bundle.putString("cuisine", cuisine);
        bundle.putString("course", course);
        bundle.putInt("servingSize", servingSize);
        bundle.putInt("calories", calories);
        bundle.putFloat("prepTime", prepTime);
        bundle.putString("prepTimeUnit", prepTimeUnit);
        bundle.putFloat("cookTime", cookTime);
        bundle.putString("cookTimeUnit", cookTimeUnit);
        return bundle;
    }

    private void nullAllRecipeFields() {
        name = null;
        cuisine = null;
        course = null;
        servingSize = 0;
        calories = 0;
        prepTime = 0;
        cookTime = 0;
    }

    private boolean verifyInputs(String name, String cuisine, String course, int servingSize, int calories, float prepTime, float cookTime) {

        if (name != null && cuisine != null && course != null && prepTime > 0 && cookTime > 0 && calories > 0 && servingSize > 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
