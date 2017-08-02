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
public class CourseFragment extends Fragment {

    private String[] courseArray;
    private ListView mCourseList;
    private CourseListAdapter mAdapter;

    public static CourseFragment newInstance() {
        return new CourseFragment();
    }

    public CourseFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.course_view, container, false);
        courseArray = getResources().getStringArray(R.array.course_types);
        Activity activity = getActivity();

        //creates course list
        mCourseList = (ListView) view.findViewById(R.id.courseList);
        mAdapter = new CourseListAdapter(activity, courseArray, this);
        mCourseList.setAdapter(mAdapter);

        return view;
    }
}
