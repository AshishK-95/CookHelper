package ca.uottawa.cookhelper;

import android.app.Activity;
import android.content.Context;
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

/**
 * Created by AshishK on 2016-11-27.
 */
public class CuisineListAdapter extends BaseAdapter {

    Activity activity;
    String[] cuisineArray;
    public static int[] cuisinePicArray = {R.drawable.american, R.drawable.british, R.drawable.caribbean, R.drawable.chinese,
                            R.drawable.french, R.drawable.greek, R.drawable.indian, R.drawable.italian,
                            R.drawable.japanese, R.drawable.mediterranean, R.drawable.mexican, R.drawable.moroccan,
                            R.drawable.spanish, R.drawable.thai, R.drawable.turkish, R.drawable.vietnamese};

    private static LayoutInflater inflater = null;
    private ArrayList<Recipe> recipesSortedByCuisine;
    private CuisineFragment cuisineFragment;
    private BrowseFragment browseFragment;

    public CuisineListAdapter(Activity activity, String[] cuisineArray, CuisineFragment cuisineFragment) {
        this.activity = activity;
        this.cuisineArray = cuisineArray;
        this.cuisineFragment = cuisineFragment;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        browseFragment = new BrowseFragment();
    }

    @Override
    public int getCount() {
        return cuisineArray.length;
    }

    @Override
    public Object getItem(int index) {
        return cuisineArray[index];
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(final int index, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cuisinelist_row, null);
        }

        //Customize the cuisine item
        TextView cuisineItem = (TextView) convertView.findViewById(R.id.cuisineItem);
        cuisineItem.setText(cuisineArray[index]);
        cuisineItem.setTextColor(Color.WHITE);
        HomePage.font.setFontOf(cuisineItem, 3);

        //Insert picture for each cuisine item
        ImageView cuisinePic = (ImageView) convertView.findViewById(R.id.cuisinePic);
        cuisinePic.setImageResource(cuisinePicArray[index]);

        cuisinePic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                recipesSortedByCuisine = RetrieveRecipes.deserializeRecipes(RequestHandler.get("recipes?cuisine=" + cuisineArray[index]));
                Bundle bundle = new Bundle();
                bundle.putSerializable("sort_cuisine", recipesSortedByCuisine);
                bundle.putString("flag", "cuisine");
                browseFragment.setArguments(bundle);

                FragmentTransaction transaction = cuisineFragment.getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,browseFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return convertView;
    }
}
