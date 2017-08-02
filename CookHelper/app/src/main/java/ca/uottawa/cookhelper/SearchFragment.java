package ca.uottawa.cookhelper;

import android.app.Activity;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by AshishK on 2016-11-23.
 */
public class SearchFragment extends Fragment implements View.OnClickListener,
        SearchAdapter.SearchInterface,
        CategoryDialog.CategoryDialogListener{

    private LinkedList<String> category;
    private LinkedList<String> searchCriteria;
    private String[] categoryTypes;
    private SearchAdapter mAdapter;
    private CategoryDialog categoryDialog = new CategoryDialog();
    Activity activity;
    private String id;
    private TextView search;
    private BrowseFragment browseFragment = new BrowseFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_view, container, false);

        category = new LinkedList<>();
        categoryTypes = getActivity().getResources().getStringArray(R.array.category_types);
        searchCriteria = new LinkedList<>();
        activity = getActivity();
        category.add(0, null);
        searchCriteria.add(0, null);
        ListView mSearchList = (ListView) view.findViewById(R.id.searchListView);
        mAdapter = new SearchAdapter(activity, category, searchCriteria, this);
        mSearchList.setAdapter(mAdapter);
        setHasOptionsMenu(true);

        search = (TextView) view.findViewById(R.id.searchButton);
        search.setOnClickListener(this);
        HomePage.font.setFontOf(search, 0);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.searchButton:
                if (!category.contains(null) && !searchCriteria.contains(null) &&
                        !hasEmptyStrings(category) && !hasEmptyStrings(searchCriteria) &&
                        category.size() != 0 && searchCriteria.size() !=0) {
                    String query = generateQuery(formatListForQuery(category), searchCriteria);
                    System.out.println(query);

                    ArrayList<Recipe> searchedRecipes = RetrieveRecipes.deserializeRecipes(RequestHandler.get("recipes?" + query));
                    Bundle bundle = new Bundle();
                    bundle.putString("flag", "search");
                    bundle.putSerializable("search_recipe", searchedRecipes);
                    browseFragment.setArguments(bundle);

                    FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container,browseFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }
                else {
                    displayErrorMessage("incomplete");
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

                //allow user to add new search field if there are no inputs on the screen currently
                if (category.size() == 0 && searchCriteria.size() == 0) {
                    category.add(null);
                    searchCriteria.add(null);
                    mAdapter.notifyDataSetChanged();
                }
                //user can only add new search fields if ALL of the previous fields have data
                else if (!category.contains(null) &&
                        !searchCriteria.contains(null) &&
                        !hasEmptyStrings(category) &&
                        !hasEmptyStrings(searchCriteria)) {
                    category.add(null);
                    searchCriteria.add(null);
                    mAdapter.notifyDataSetChanged();
                    System.out.println(searchCriteria);
                    System.out.println(category);
                }
                //display error message if there exists incomplete fields
                else {
                    if (category.contains(null) || searchCriteria.contains(null) ||
                            hasEmptyStrings(category) || hasEmptyStrings(searchCriteria) ) {
                        displayErrorMessage("incomplete");
                    }

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDeleteButtonClick(int index) {
        category.remove(index);
        searchCriteria.remove(index);
        mAdapter.notifyDataSetChanged();
        System.out.println(category);
        System.out.println(searchCriteria);
    }

    @Override
    public void onCategoryButtonClick(int index, String id) {
        this.id = id;
        categoryDialog.setTargetFragment(this, 0);
        categoryDialog.show(this.getFragmentManager(), "Category");
    }

    private boolean hasEmptyStrings(LinkedList<String> list) {
        for (String s: list) {
            if (s.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void displayErrorMessage(String s) {
        if (s.equals("incomplete")) {
            Context context = getActivity().getApplicationContext();
            CharSequence text = "Incomplete field(s)";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
        }
    }

    @Override
    public void onCategoryTypeClick(int index) {
        category.set(Integer.valueOf(id), categoryTypes[index]);
        mAdapter.notifyDataSetChanged();
    }

    //this method is in charge of generating a query for the database in order to search for recipes
    private String generateQuery(LinkedList<String> category, LinkedList<String> searchCriteria) {
        HashMap<String, String> map = new HashMap<>();

        for (int i = 0; i < category.size(); i++) {
            if (map.get(category.get(i)) == null) {
                map.put(category.get(i), "");
            }

            if (map.get(category.get(i)).equals("")) {
                map.put(category.get(i), map.get(category.get(i)) + searchCriteria.get(i));
            }
            else {
                map.put(category.get(i), map.get(category.get(i)) + " or " + searchCriteria.get(i));
            }
        }

        String query = "";

        for (Map.Entry<String, String> e: map.entrySet()) {
            query += e.getKey() + "= " + e.getValue() + "&";
        }
        query = query.toLowerCase();
        return query;
    }

    public LinkedList<String> formatListForQuery(LinkedList<String> list) {
        LinkedList<String> temp = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i)) {
                case "Title":
                    temp.addLast("title");
                    break;
                case "Ingredient":
                    temp.addLast("ingredient.title");
                    break;
                case "Cuisine":
                    temp.addLast("cuisine");
                    break;
                case "Course":
                    temp.addLast("course");
                    break;
                case "Prep Time":
                    temp.addLast("prep_time");
                    break;
                case "Cook Time":
                    temp.addLast("cook_time");
                    break;
                case "Calories":
                    temp.addLast("calories");
                    break;
                case "Serving Size":
                    temp.addLast("serving_size");
                    break;
            }
        }

        return temp;
    }
}
