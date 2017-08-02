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
public class AddRecipe3Fragment extends Fragment implements
        View.OnClickListener,
        AddDirectionListAdapter.AddDirectionsInterface {

    private AddRecipe4Fragment addRecipe4Fragment;
    private int[] imageViewFieldIDs = {R.id.continueField, R.id.cancelField};
    private ImageView[] imageViewObject = new ImageView[imageViewFieldIDs.length];
    private LinkedList<String> directions;
    private LinkedList<Step> objectDirection = new LinkedList<>();
    private AddDirectionListAdapter mAdapter;
    Activity activity;
    private boolean editFlag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.addrecipe3_view, container, false);
        directions = new LinkedList<>();
        editFlag = getArguments().getBoolean("idFlag");
        if (editFlag) {
            ArrayList<Step> list;
            list = (ArrayList<Step>) getArguments().getSerializable("direction_list");

            for (int i = 0; i < list.size(); i++) {
                directions.add(i, list.get(i).getDescription());
            }
        }
        else {
            directions = new LinkedList<>();
            directions.add(0, null);
        }


        addRecipe4Fragment = new AddRecipe4Fragment();
        activity = getActivity();
        ListView mAddDirectionsList = (ListView) view.findViewById(R.id.addDirectionsList);
        mAdapter = new AddDirectionListAdapter(activity, directions, this);
        mAddDirectionsList.setAdapter(mAdapter);

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
                    if (!directions.contains(null) && !hasEmptyStrings(directions)) {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        //Creating Step objects based off the UI
                        for (int i = 0; i < directions.size(); i++){
                            objectDirection.add(i, new Step(null,null,directions.get(i)));
                        }
                        this.getArguments().putSerializable("directions",objectDirection);
                        //Sending data to the next fragment
                        addRecipe4Fragment.setArguments(this.getArguments());
                        transaction.replace(R.id.fragment_container, addRecipe4Fragment);
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
                if (directions.size() == 0) {
                    directions.add(null);
                    mAdapter.notifyDataSetChanged();
                }
                //user can only add new directions if ALL of the previous fields have data
                else if (!directions.contains(null) && !hasEmptyStrings(directions)) {
                    directions.add(null);
                    mAdapter.notifyDataSetChanged();
                    System.out.println(directions);
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
        directions.remove(index);
        mAdapter.notifyDataSetChanged();
        System.out.println(directions);
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


