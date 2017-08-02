package ca.uottawa.cookhelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by AshishK on 2016-11-23.
 */
public class DraftsFragment extends Fragment {
    public static DraftsFragment newInstance() {
        return new DraftsFragment();
    }

    public DraftsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.drafts_view, container, false);
    }
}
