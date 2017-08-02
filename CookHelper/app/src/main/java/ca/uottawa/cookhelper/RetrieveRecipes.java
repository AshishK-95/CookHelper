
package ca.uottawa.cookhelper;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ca.uottawa.cookhelper.Recipe;

public class RetrieveRecipes {
    public static void saveRecipes(ArrayList<Recipe> recipes, Context ctx) {
        String recipeJSON = serializeRecipes(recipes);
        try {
            FileOutputStream outputStream = ctx.openFileOutput("recipes.json", ctx.MODE_PRIVATE);
            outputStream.write(recipeJSON.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Recipe> loadRecipes(Context ctx) {
        StringBuilder recipeJSON = new StringBuilder();
        try {
            FileInputStream inputStream = ctx.openFileInput("recipes.json");
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while(null != (line = r.readLine())) {
                recipeJSON.append(line);
            }
            r.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deserializeRecipes(recipeJSON.toString());
    }

    /**
     * Convert from ArrayList of Recipe to JSON
     * @param recipes ArrayList to convert
     * @return the converted JSON
     */
    public static String serializeRecipes(ArrayList<Recipe> recipes) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(recipes, new TypeToken<ArrayList<Recipe>>(){}.getType());
    }

    /**
     * converts from JSON to ArrayList of Recipe
     * @param stuff JSON to convert from
     * @return the converted ArrayList
     */
    public static ArrayList<Recipe> deserializeRecipes(String stuff) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(stuff, new TypeToken<ArrayList<Recipe>>(){}.getType());
    }

    //Same as serializeRecipes however serializes one Recipe
    public static String serializeRecipe(Recipe recipe) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(recipe, Recipe.class);
    }

    //Same as deserializeRecipes however deserializes to one Recipe
    public static Recipe deserializeRecipe(String jsonString) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, Recipe.class);
    }

    public static String serializeIngredients(ArrayList<Ingredient> ingredients) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(ingredients, new TypeToken<ArrayList<Ingredient>>(){}.getType());
    }
    public static ArrayList<Ingredient> deserializeIngredients(String stuff) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(stuff, new TypeToken<ArrayList<Ingredient>>(){}.getType());
    }
    public static String serializeIngredient(Ingredient ingredient) {
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(ingredient, Ingredient.class);
        return s;
    }
    public static Ingredient deserializeIngredient(String jsonString) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, Ingredient.class);
    }

    public static String serializeSteps(ArrayList<Step> steps) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(steps, new TypeToken<ArrayList<Step>>(){}.getType());
    }
    public static ArrayList<Step> deserializeSteps(String stuff) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(stuff, new TypeToken<ArrayList<Step>>(){}.getType());
    }
    public static String serializeStep(Step step) {
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(step, Step.class);
        return s;
    }
    public static Step deserializeStep(String jsonString) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, Step.class);
    }

}