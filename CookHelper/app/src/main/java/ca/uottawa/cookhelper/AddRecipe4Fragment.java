package ca.uottawa.cookhelper;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by AshishK on 2016-11-27.
 */
public class AddRecipe4Fragment extends Fragment implements View.OnClickListener {


    private int[] textViewFieldIDs = {R.id.saveField};
    private TextView[] textViewObject = new TextView[textViewFieldIDs.length];
    private Recipe recipe;
    private LinkedList<Ingredient> ingredients;
    private LinkedList<Step> steps;
    private ArrayList<Recipe> recipes;
    private boolean editFlag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.addrecipe4_view, container, false);

        for (int k = 0; k < textViewFieldIDs.length; k++) {
            textViewObject[k] = (TextView) view.findViewById(textViewFieldIDs[k]);
            textViewObject[k].setOnClickListener(this);
        }

        //creating recipe from all the bundled information being passed between the add recipe fragments
        recipe = new Recipe();
        recipe.setTitle(this.getArguments().getString("name"));
        recipe.setCuisine(this.getArguments().getString("cuisine"));
        recipe.setCourse(this.getArguments().getString("course"));
        recipe.setServingSize(this.getArguments().getInt("servingSize"));
        recipe.setCalories(this.getArguments().getInt("calories"));
        recipe.setPrepTime((int)this.getArguments().getFloat("prepTime"));
        recipe.setCookTime((int)this.getArguments().getFloat("cookTime"));
        recipe.setPrepTimeUnit(this.getArguments().getString("prepTimeUnit"));
        recipe.setCookTimeUnit(this.getArguments().getString("cookTimeUnit"));
        ingredients = (LinkedList<Ingredient>)getArguments().getSerializable("ingredients");
        steps = (LinkedList<Step>)getArguments().getSerializable("directions");

        editFlag = this.getArguments().getBoolean("idFlag");

        //Setting the id of the recipe to the selected recipe if editing was chosen
        if (editFlag) {
            recipe.setId(this.getArguments().getInt("id"));
        }
        return view;
    }

    @Override
    public void onClick(View view) {

        if (view instanceof TextView) {
            switch (view.getId()) {
                case R.id.saveField:

                    //Posting the new recipe
                    RequestHandler.post("recipes", RetrieveRecipes.serializeRecipe(recipe));

                    recipes = RetrieveRecipes.deserializeRecipes(RequestHandler.get("recipes"));

                    //Determining whether to set the recipe to the latest added recipe or the selected recipe
                    if (editFlag==false){
                        recipe = recipes.get(recipes.size()-1);
                    }


                    int first, last, sFirst, sLast; //positions to iterate through for ingredients and steps

                    ArrayList<Ingredient> ing = RetrieveRecipes.deserializeIngredients(RequestHandler.get("ingredients"));
                    ArrayList<Step> step = RetrieveRecipes.deserializeSteps(RequestHandler.get("steps"));

                    if (ing.size() == 0){
                        first = 0;
                    }
                    else{
                        first = ing.size();
                    }

                    if (step.size() == 0){
                        sFirst = 0;
                    }
                    else{
                        sFirst = step.size();
                    }

                    //linking the ingredient to the recipe and adding it to the database
                    for (int i = 0; i < ingredients.size(); i++){
                        ingredients.get(i).setRecipe_id(recipe.getId());

                        RequestHandler.post("ingredients", RetrieveRecipes.serializeIngredient(ingredients.get(i)));

                    }
                    //linking the ingredient to the recipe and adding it to the database
                    for (int i = 0; i < steps.size(); i++){

                        steps.get(i).setRecipe_id(recipe.getId());

                        RequestHandler.post("steps", RetrieveRecipes.serializeStep(steps.get(i)));
                    }

                    ing = RetrieveRecipes.deserializeIngredients(RequestHandler.get("ingredients"));
                    ArrayList<Integer> ingSetter = new ArrayList<>();

                    step = RetrieveRecipes.deserializeSteps(RequestHandler.get("steps"));
                    ArrayList<Integer> stepSetter = new ArrayList<>();

                    last = ing.size();

                    for (int i = first; i < last; i++){
                        ingSetter.add(ing.get(i).getId());
                    }

                    recipe.setIngredients(ingSetter);

                    sLast = step.size();

                    for (int i = sFirst; i < sLast; i++){
                        stepSetter.add(step.get(i).getId());
                    }

                    recipe.setSteps(stepSetter);

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, MainMenu.browseFragment);
                    transaction.commit();
                    break;
            }
        }
    }
}