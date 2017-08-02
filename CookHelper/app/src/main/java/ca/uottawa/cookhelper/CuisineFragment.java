package ca.uottawa.cookhelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by AshishK on 2016-11-23.
 */
public class CuisineFragment extends Fragment {

    public static String[] cuisineArray;
    private ListView mCuisineList;
    private CuisineListAdapter mAdapter;

    public static CuisineFragment newInstance() {
        return new CuisineFragment();
    }

    public CuisineFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cuisine_view, container, false);
        cuisineArray = getResources().getStringArray(R.array.cuisine_types);
        Activity activity = getActivity();

        //creates cuisine list
        mCuisineList = (ListView) view.findViewById(R.id.cuisineList);
        mAdapter = new CuisineListAdapter(activity, cuisineArray, this);
        mCuisineList.setAdapter(mAdapter);

        return view;
    }
}

