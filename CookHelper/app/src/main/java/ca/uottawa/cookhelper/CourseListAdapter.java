package ca.uottawa.cookhelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
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
public class CourseListAdapter extends BaseAdapter {

    Activity activity;
    String[] courseArray;
    int[] coursePicArray = {R.drawable.breakfast, R.drawable.brunch, R.drawable.drink, R.drawable.lunch,
                            R.drawable.snack, R.drawable.supper, R.drawable.dinner};

    private static LayoutInflater inflater = null;
    private CourseFragment courseFragment;
    private BrowseFragment browseFragment;
    private ArrayList<Recipe> recipesSortedByCourse;

    public CourseListAdapter(Activity activity, String[] courseArray, CourseFragment courseFragment) {
        this.activity = activity;
        this.courseArray = courseArray;
        this.courseFragment = courseFragment;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        browseFragment = new BrowseFragment();
    }

    @Override
    public int getCount() {
        return courseArray.length;
    }

    @Override
    public Object getItem(int index) {
        return courseArray[index];
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(final int index, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.courselist_row, null);
        }

        //Customize the course item
        TextView courseItem = (TextView) convertView.findViewById(R.id.courseItem);
        courseItem.setText(courseArray[index]);
        courseItem.setTextColor(Color.WHITE);
        HomePage.font.setFontOf(courseItem, 3);

        //Insert pictures of each course item
        ImageView coursePic = (ImageView) convertView.findViewById(R.id.coursePic);
        coursePic.setImageResource(coursePicArray[index]);

        coursePic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                recipesSortedByCourse = RetrieveRecipes.deserializeRecipes(RequestHandler.get("recipes?course=" + courseArray[index]));
                Bundle bundle = new Bundle();
                bundle.putSerializable("sort_course", recipesSortedByCourse);
                bundle.putString("flag", "course");
                browseFragment.setArguments(bundle);

                FragmentTransaction transaction = courseFragment.getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,browseFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return convertView;
    }
}
