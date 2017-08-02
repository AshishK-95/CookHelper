package ca.uottawa.cookhelper;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AshishK on 2016-11-23.
 */
public class BrowseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ArrayList<Recipe> recipes;
        ListView mBrowseList;
        BrowseListAdapter mAdapter;
        View view = inflater.inflate(R.layout.browse_view, container, false);
        Activity activity = getActivity();
        Bundle bundle = getArguments();

        if (bundle == null) { //bundle is always null on startup
            recipes = RetrieveRecipes.deserializeRecipes(RequestHandler.get("recipes"));
        }
        else {
            //bundle contains arguments from either course or cuisine screen
            //recipes is sorted by either cuisine type or course type

            if (bundle.getString("flag").equals("cuisine")) {
                recipes = (ArrayList<Recipe>) bundle.getSerializable("sort_cuisine");
            }
            else if (bundle.getString("flag").equals("course")) {
                recipes = (ArrayList<Recipe>) bundle.getSerializable("sort_course");
            }
            else if (bundle.getString("flag").equals("search")) {
                recipes = (ArrayList<Recipe>) bundle.get("search_recipe");
            }
            else {
                recipes = RetrieveRecipes.deserializeRecipes(RequestHandler.get("recipes")); //dont sort by any specific criteria
            }
        }

        //creates recipes list
        mBrowseList = (ListView) view.findViewById(R.id.browseList);
        mAdapter = new BrowseListAdapter(activity, recipes, this);
        mBrowseList.setAdapter(mAdapter);

        return view;
    }
}
