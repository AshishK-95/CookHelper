package ca.uottawa.cookhelper;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Harrison on 2016-12-26.
 */
public class HelpFragment extends Fragment implements View.OnClickListener {

    public static HelpFragment newInstance() {
        return new HelpFragment();
    }

    public HelpFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.help_view, container, false);
        Button button = (Button) view.findViewById(R.id.back_button);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view instanceof Button) {
            switch (view.getId()) {
                case R.id.back_button:
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, MainMenu.browseFragment).commit();
            }
        }
    }
}
