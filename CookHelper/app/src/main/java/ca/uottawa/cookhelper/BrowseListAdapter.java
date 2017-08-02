package ca.uottawa.cookhelper;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AshishK on 2016-11-27.
 */
public class BrowseListAdapter extends BaseAdapter{

    private Activity activity;
    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private BrowseFragment browseFragment;
    private ViewRecipeFragment viewRecipeFragment;
    HashMap<String,Integer> cuisine = new HashMap<String,Integer>();

    private String[] cuisineArray;
    int[] cuisinePics = {R.drawable.american, R.drawable.british, R.drawable.caribbean, R.drawable.chinese,
            R.drawable.french, R.drawable.greek, R.drawable.indian, R.drawable.italian,
            R.drawable.japanese, R.drawable.mediterranean, R.drawable.mexican, R.drawable.moroccan,
            R.drawable.spanish, R.drawable.thai, R.drawable.turkish, R.drawable.vietnamese};

    private static LayoutInflater inflater = null;

    public BrowseListAdapter(Activity activity, ArrayList<Recipe> recipes, BrowseFragment browseFragment) {

        this.activity = activity;
        this.recipes = recipes;
        this.browseFragment = browseFragment;
        viewRecipeFragment = new ViewRecipeFragment();
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        cuisineArray = activity.getResources().getStringArray(R.array.cuisine_types);


        for (int i = 0; i < cuisineArray.length; i++) {
            cuisine.put(cuisineArray[i], i);
        }
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int index) {
        return recipes.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(final int index, View convertView, ViewGroup parent) {

        final int id = index;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.browselist_row, null);
        }

        //Customize individual recipe item
        TextView recipeTitle = (TextView) convertView.findViewById(R.id.browseRecipeTitle);
        recipeTitle.setText(recipes.get(index).getTitle());
        recipeTitle.setTextColor(Color.WHITE);
        HomePage.font.setFontOf(recipeTitle, 3);

        //Insert pictures of each course item
        ImageView recipePic = (ImageView) convertView.findViewById(R.id.browseRecipePic);
        recipePic.setImageResource(cuisinePics[(getCuisineCode(recipes.get(index)))]);
        recipePic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("recipeObj", recipes.get(id));
                bundle.putInt("recipePic", cuisinePics[(getCuisineCode(recipes.get(index)))]);
                viewRecipeFragment.setArguments(bundle);

                FragmentTransaction transaction = browseFragment.getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,viewRecipeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return convertView;
    }


    private int getCuisineCode(Recipe recipe) {
        return cuisine.get(recipe.getCuisine());
    }
}
