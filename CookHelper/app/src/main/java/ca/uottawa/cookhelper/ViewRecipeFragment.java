package ca.uottawa.cookhelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by AshishK on 2016-11-28.
 */
public class ViewRecipeFragment extends Fragment {

    private Recipe recipe;
    private AddRecipeFragment addRecipeFragment = new AddRecipeFragment();
    private ArrayList<Ingredient> ing = new ArrayList<>();
    private ArrayList<Step> dir = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ArrayList tempIng, tempDir;
        ListView mIngredientsList, mDirectionsList;
        IngredientListAdapter mIngredientAdapter;
        DirectionListAdapter mDirectionAdapter;

        View view = inflater.inflate(R.layout.viewrecipe_view, container, false);
        Activity activity= getActivity();
        recipe = (Recipe) getArguments().getSerializable("recipeObj"); //get recipe object that was clicked

        //update recipe title field
        TextView recipeTitle = (TextView) view.findViewById(R.id.recipeName);
        recipeTitle.setText(recipe.getTitle());
        HomePage.font.setFontOf(recipeTitle, 3);

        //update cuisine/course field
        TextView cuisineCourseField = (TextView) view.findViewById(R.id.cuisinecourseTextView);
        cuisineCourseField.setText(recipe.getCuisine() + " | " + recipe.getCourse());
        HomePage.font.setFontOf(cuisineCourseField, 3);

        //update calories field
        TextView caloriesField = (TextView) view.findViewById(R.id.calories);
        caloriesField.setText(String.valueOf(recipe.getCalories()));

        //update prep time field
        TextView prepTimeField = (TextView) view.findViewById(R.id.prepTime);
        prepTimeField.setText(String.valueOf(recipe.getPrepTime()));

        //update cook time field
        TextView cookTimeField = (TextView) view.findViewById(R.id.cookTime);
        cookTimeField.setText(String.valueOf(recipe.getCookTime()));

        //update serving size field
        TextView servingSizeField = (TextView) view.findViewById(R.id.servingSize);
        servingSizeField.setText(String.valueOf(recipe.getServingSize()));

        //update prepTimeUnit
        TextView prepTimeUnit = (TextView) view.findViewById(R.id.cookTimeUnit);
        prepTimeUnit.setText(recipe.getCookTimeUnit());

        //update cookTimeUnit
        TextView cookTimeUnit = (TextView) view.findViewById(R.id.prepTimeUnit);
        cookTimeUnit.setText(recipe.getPrepTimeUnit());

        //update background image
        ImageView image = (ImageView) view.findViewById(R.id.recipePic);
        image.setImageResource(getArguments().getInt("recipePic"));

        //creates ingredient list
        tempIng = recipe.getIngredients();
        ArrayList<String>  ingredients = new ArrayList<>();

        for (int i = 0; i < tempIng.size(); i++) {
            ing.add(RetrieveRecipes.deserializeIngredient(RequestHandler.get("ingredients/" + tempIng.get(i))));
            ingredients.add(ing.get(i).getQuantity() + " " + ing.get(i).getName());
        }


        mIngredientsList = (ListView) view.findViewById(R.id.ingredientsList);
        mIngredientAdapter = new IngredientListAdapter(activity, ingredients);
        mIngredientAdapter.setListViewHeightBasedOnChildren(mIngredientsList);
        mIngredientsList.setAdapter(mIngredientAdapter);

        //create direction list
        tempDir = recipe.getDirections();
        ArrayList<String> directions = new ArrayList<>();

        for (int i = 0; i < tempDir.size(); i++) {
            dir.add(RetrieveRecipes.deserializeStep(RequestHandler.get("steps/" + tempDir.get(i))));
            directions.add(dir.get(i).getDescription());
        }

        mDirectionsList = (ListView) view.findViewById(R.id.directionsList);
        mDirectionAdapter = new DirectionListAdapter(activity, directions);
        mDirectionAdapter.setListViewHeightBasedOnChildren(mDirectionsList);
        mDirectionsList.setAdapter(mDirectionAdapter);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_search).setVisible(false);
        inflater.inflate(R.menu.edit, menu);
        inflater.inflate(R.menu.delete, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.action_edit:
                Bundle bundle = new Bundle();
                //make bundle to send arguments for the addRecipeFragment to use
                bundle.putSerializable("edit_recipe", recipe);
                bundle.putSerializable("ingredient_list", ing);
                bundle.putSerializable("direction_list",  dir);
                addRecipeFragment.setArguments(bundle);
                transaction.replace(R.id.fragment_container, addRecipeFragment);
                transaction.commit();
                break;

            case R.id.action_delete:
                //deletes the recipe then returns to the browse fragment
                RequestHandler.delete("recipes/" + recipe.getId());
                transaction.replace(R.id.fragment_container, MainMenu.browseFragment);
                transaction.commit();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

}
