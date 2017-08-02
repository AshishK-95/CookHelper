package ca.uottawa.cookhelper;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by AshishK on 2016-11-27.
 */
public class AddRecipe2Fragment extends Fragment implements
        View.OnClickListener,
        AddIngredientListAdapter.AddIngredientsInterface {

    private AddRecipe3Fragment addRecipe3Fragment;
    private int[] imageViewFieldIDs = {R.id.continueField, R.id.cancelField};
    private ImageView[] imageViewObject = new ImageView[imageViewFieldIDs.length];
    private LinkedList<String> ingredient;
    private LinkedList<String> quantity;
    private AddIngredientListAdapter mAdapter;
    private Activity activity;
    private LinkedList<Ingredient> objectIngredients = new LinkedList<>();
    private boolean editFlag = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.addrecipe2_view, container, false);
        ingredient = new LinkedList<>();
        quantity = new LinkedList<>();

        //editFlag is in charge of determining whether the user wants to edit the recipe or not
        editFlag = getArguments().getBoolean("idFlag");
        if (editFlag) { //if true, we are in edit-mode
            ArrayList<Ingredient> list;
            list = (ArrayList<Ingredient>) getArguments().getSerializable("ingredient_list");

            for (int i = 0; i < list.size(); i++) {
                ingredient.add(i, list.get(i).getName());
                quantity.add(i, list.get(i).getQuantity());
            }
        }
        else { //if false, user is adding a new recipe
            ingredient = new LinkedList<>();
            quantity = new LinkedList<>();
            ingredient.add(0, null); //provide first input box for ingredient
            quantity.add(0, null); //provide first input box for quantity
        }

        addRecipe3Fragment = new AddRecipe3Fragment();
        activity = getActivity();
        ListView mAddIngredientsList = (ListView) view.findViewById(R.id.addIngredientsList);
        mAdapter = new AddIngredientListAdapter(activity, ingredient, quantity, this);
        mAddIngredientsList.setAdapter(mAdapter);

        for (int k = 0; k < imageViewFieldIDs.length; k++) {
            imageViewObject[k] = (ImageView) view.findViewById(imageViewFieldIDs[k]);
            imageViewObject[k].setOnClickListener(this);
        }

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onClick(View view) {

        if (view instanceof ImageView) {
            switch (view.getId()) {
                case R.id.continueField:
                    if (!quantity.contains(null) &&
                            !ingredient.contains(null) &&
                            !hasEmptyStrings(quantity) &&
                            !hasEmptyStrings(ingredient)) {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        //Creating Ingredient objects based off of the UI
                        for (int i = 0; i < ingredient.size(); i++){
                            objectIngredients.add(i, new Ingredient(null,null,ingredient.get(i),quantity.get(i)));
                        }
                        this.getArguments().putSerializable("ingredients",objectIngredients);
                        //Sending data to the next fragment
                        addRecipe3Fragment.setArguments(this.getArguments());
                        transaction.replace(R.id.fragment_container, addRecipe3Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    else {
                        displayErrorMessage();
                    }
                    break;

                case R.id.cancelField:
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, MainMenu.browseFragment).commit();
                    break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_search).setVisible(false);
        inflater.inflate(R.menu.add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:

                //allow user to add recipe if there are no inputs on the screen currently
                if (quantity.size() == 0 && ingredient.size() == 0) {
                    quantity.add(null);
                    ingredient.add(null);
                    mAdapter.notifyDataSetChanged();
                }
                //user can only add new ingredients if ALL of the previous fields have data
                else if (!quantity.contains(null) &&
                        !ingredient.contains(null) &&
                        !hasEmptyStrings(quantity) &&
                        !hasEmptyStrings(ingredient)) {
                    quantity.add(null);
                    ingredient.add(null);
                    mAdapter.notifyDataSetChanged();
                    System.out.println(ingredient);
                    System.out.println(quantity);
                }
                //display error message if there exists incomplete fields
                else {
                    displayErrorMessage();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDeleteButtonClick(int index) {
        ingredient.remove(index);
        quantity.remove(index);
        mAdapter.notifyDataSetChanged();
        System.out.println(ingredient);
        System.out.println(quantity);
    }

    private boolean hasEmptyStrings(LinkedList<String> list) {
        for (String s: list) {
            if (s.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void displayErrorMessage() {
        Context context = getActivity().getApplicationContext();
        CharSequence text = "Incomplete field(s)";
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();
    }
}


